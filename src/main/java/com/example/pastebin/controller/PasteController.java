package com.example.pastebin.controller;

import com.example.pastebin.dto.GetPastaDTO;
import com.example.pastebin.dto.ListPastaDTO;
import com.example.pastebin.dto.PasteDTO;
import com.example.pastebin.enums.Access;
import com.example.pastebin.enums.ExpirationTime;
import com.example.pastebin.exception.ApiError;
import com.example.pastebin.service.PasteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/my-awesome-pastebin.tld")
public class PasteController {

    private final PasteService pasteService;

    public PasteController(PasteService pasteService) {
        this.pasteService = pasteService;
    }

    @PostMapping
    public ResponseEntity<PasteDTO> createPaste(@RequestBody PasteDTO pasteDTO,
                                                @RequestParam Access access,
                                                @RequestParam ExpirationTime expirationTime) {
        PasteDTO createdPaste = pasteService.create(pasteDTO, access, expirationTime);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPaste);
    }

    @GetMapping("/public")
    public List<ListPastaDTO> get10LastPaste() {
        return pasteService.getLastTenPast();
    }

    @GetMapping("/{text}")
    public List<GetPastaDTO> getById(@PathVariable String text) {
    return pasteService.getByTitleAndContent(text, text);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiError> handleException(RuntimeException ex) {
        ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiError);
    }

}