package com.example.pastebin;

import com.example.pastebin.dto.GetPastaDTO;
import com.example.pastebin.dto.ListPastaDTO;
import com.example.pastebin.dto.PasteDTO;
import com.example.pastebin.enums.Access;
import com.example.pastebin.model.Paste;
import com.example.pastebin.repository.PasteRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureJsonTesters
class PasteControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PasteRepository pasteRepository;

    @BeforeEach
    void setUp() {
        // очистить базу данных перед каждым тестом
        pasteRepository.deleteAll();
    }

    @Test
    void testCreatePaste() {
        for (int i = 0; i < 11; i++) {
            Paste paste = new Paste();
            paste.setTitle("title " + i);
            paste.setContent("content " + i);
            paste.setPubDate(Instant.now());
            paste.setAccess(Access.PUBLIC.getAccess());
            paste.setValidity(Instant.now().plus(1, ChronoUnit.DAYS));
            paste.setLink("http://my-awesome-pastebin.tld/" + RandomStringUtils.randomAlphabetic(8));
            Paste pasteDTO = pasteRepository.save(paste);
            new PasteDTO(pasteDTO.getLink(), pasteDTO.getTitle(), pasteDTO.getContent(), pasteDTO.getAccess(), pasteDTO.getPubDate(), pasteDTO.getValidity());

            ResponseEntity<PasteDTO> responseEntity = restTemplate.postForEntity(
                    "/my-awesome-pastebin.tld/create?access=PUBLIC&expirationTime=ONE_DAY",
                    pasteDTO,
                    PasteDTO.class);

            assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        }

        ResponseEntity<List<ListPastaDTO>> responseEntity = restTemplate.exchange(
                "/my-awesome-pastebin.tld/search",
                HttpMethod.GET,
                new HttpEntity<>(new HttpHeaders()),
                new ParameterizedTypeReference<List<ListPastaDTO>>() {
                }
        );
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        List<ListPastaDTO> responseList = responseEntity.getBody();
        assertNotNull(responseList);
        assertEquals(10, responseList.size());

        assertEquals("title 0", responseList.get(0).getTitle());
    }

    @Test
    void testGetByText() {
        Paste paste = new Paste();
        paste.setTitle("title");
        paste.setContent("content");
        paste.setPubDate(Instant.now());
        paste.setAccess(Access.PUBLIC.getAccess());
        paste.setValidity(Instant.now().plus(1, ChronoUnit.DAYS));
        paste.setLink("http://my-awesome-pastebin.tld/" + RandomStringUtils.randomAlphabetic(8));
        Paste pasteDTO = pasteRepository.save(paste);
        new PasteDTO(pasteDTO.getLink(), pasteDTO.getTitle(), pasteDTO.getContent(), pasteDTO.getAccess(), pasteDTO.getPubDate(), pasteDTO.getValidity());

        ResponseEntity<List<GetPastaDTO>> responseEntity = restTemplate.exchange(
                "/my-awesome-pastebin.tld/info/" + pasteDTO.getTitle(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<GetPastaDTO>>() {
                });

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        List<GetPastaDTO> pasteList = responseEntity.getBody();
        assertNotNull(pasteList);
        assertEquals(1, pasteList.size());
        GetPastaDTO returnedPaste = pasteList.get(0);
        assertNotNull(returnedPaste);
        assertEquals(pasteDTO.getLink(), returnedPaste.getLink());
        assertEquals(pasteDTO.getTitle(), returnedPaste.getTitle());
        assertEquals(pasteDTO.getContent(), returnedPaste.getContent());
    }

    @Test
    void testGetByHash() {
        Paste paste = new Paste();
        paste.setTitle("title");
        paste.setContent("content");
        paste.setPubDate(Instant.now());
        paste.setAccess(Access.PUBLIC.getAccess());
        paste.setValidity(Instant.now().plus(1, ChronoUnit.DAYS));
        paste.setLink("http://my-awesome-pastebin.tld/" + RandomStringUtils.randomAlphabetic(8));
        Paste pasteDTO = pasteRepository.save(paste);
        new PasteDTO(pasteDTO.getLink(), pasteDTO.getTitle(), pasteDTO.getContent(), pasteDTO.getAccess(), pasteDTO.getPubDate(), pasteDTO.getValidity());

        String hash = pasteDTO.getLink().substring(pasteDTO.getLink().lastIndexOf("/") + 1);

        ResponseEntity<ListPastaDTO> responseEntity = restTemplate.exchange(
                "/my-awesome-pastebin.tld/" + hash,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<ListPastaDTO>() {
                });

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        ListPastaDTO pasteT = responseEntity.getBody();
        assertNotNull(pasteT);
        assertEquals(pasteDTO.getLink(), pasteT.getLink());
        assertEquals(pasteDTO.getTitle(), pasteT.getTitle());
    }
}

