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
import java.util.List;
import java.util.stream.Collectors;

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
        paste.setLink("http://my-awesome-pastebin.tld/" + RandomStringUtils.randomAlphabetic(8) );
        Paste newPaste = pasteRepository.save(paste);
        return new PasteDTO(newPaste.getLink(), newPaste.getTitle(), newPaste.getContent(), newPaste.getAccess(), newPaste.getPubDate(), newPaste.getValidity() );
    }

    public List<ListPastaDTO> getLastTenPast() {
        List<Paste> toDelete = pasteRepository.findByValidityBefore(Instant.now());
        pasteRepository.deleteAll(toDelete);

        List<Paste> pastes = pasteRepository.findTop10ByAccessOrderByPubDate("public");
        return pastes.stream().map(p -> new ListPastaDTO(p.getLink(), p.getTitle())).collect(Collectors.toList());
    }

    public List<GetPastaDTO> getByTitleAndContent(String title, String content) {
        List<Paste> toDelete = pasteRepository.findByValidityBefore(Instant.now());
        pasteRepository.deleteAll(toDelete);

        List<Paste> pastes = pasteRepository.findByAccessAndTitleContainingIgnoreCaseOrAccessAndContentContainingIgnoreCase("public", title, "public", content);
        return pastes.stream().map(p -> new GetPastaDTO(p.getLink(), p.getTitle(), p.getContent())).collect(Collectors.toList());
    }
}
