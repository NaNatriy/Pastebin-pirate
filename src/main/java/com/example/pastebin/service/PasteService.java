package com.example.pastebin.service;

import com.example.pastebin.dto.GetPastaDTO;
import com.example.pastebin.dto.ListPastaDTO;
import com.example.pastebin.dto.PasteDTO;
import com.example.pastebin.enums.Access;
import com.example.pastebin.enums.ExpirationTime;
import com.example.pastebin.model.Paste;
import com.example.pastebin.repository.PasteRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PasteService {
    private final PasteRepository pasteRepository;

    public PasteService(PasteRepository pasteRepository) {
        this.pasteRepository = pasteRepository;
    }

    public PasteDTO create(PasteDTO dto, Access access, ExpirationTime time) {
        Paste paste = new Paste();
        paste.setTitle(dto.getTitle());
        paste.setContent(dto.getContent());
        paste.setPubDate(Instant.now());
        paste.setAccess(access.getAccess());
        paste.setValidity(Instant.now().plus(time.getTime(), time.getUnit()));
        paste.setLink(RandomStringUtils.randomAlphabetic(8));
        Paste newPaste = pasteRepository.save(paste);
        return new PasteDTO(newPaste.getLink(), newPaste.getTitle(), newPaste.getContent(), newPaste.getAccess(), newPaste.getPubDate(), newPaste.getValidity());
    }

    public List<ListPastaDTO> getLastTenPast() {
        List<Paste> pastes = pasteRepository.findTop10ByAccessOrderByPubDate("public");
        List<ListPastaDTO> result = new ArrayList<>();
        for (Paste p : pastes) {
            if (p.getValidity().isAfter(Instant.now())) {
                ListPastaDTO pastaDTO = new ListPastaDTO(p.getLink(), p.getTitle());
                result.add(pastaDTO);
            }
        }
        return result;
    }

    public List<GetPastaDTO> getByTitleAndContent(String searchTerm) {
        List<Paste> pastes = pasteRepository.findByAccessAndTitleContainingIgnoreCaseOrAccessAndContentContainingIgnoreCase("public", searchTerm);
        List<GetPastaDTO> result = new ArrayList<>();
        for (Paste p : pastes) {
            if (p.getValidity().isAfter(Instant.now())) {
                GetPastaDTO pastaDTO = new GetPastaDTO(p.getLink(), p.getTitle(), p.getContent());
                result.add(pastaDTO);
            }
        }
        return result;
    }

    public ListPastaDTO getByHash(String link) {
        String hash = link.substring(link.lastIndexOf("/") + 1);
        Optional<Paste> optionalPaste = pasteRepository.findByLinkEndingWith(hash);
        Paste paste = optionalPaste.get();
        return new ListPastaDTO(paste.getLink(), paste.getTitle());
    }
}
