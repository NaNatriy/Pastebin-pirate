package com.example.pastebin.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ListPastaDTO {
    private String link;
    private String title;

    public ListPastaDTO(String link, String title) {
        this.link = link;
        this.title = title;
    }

}
