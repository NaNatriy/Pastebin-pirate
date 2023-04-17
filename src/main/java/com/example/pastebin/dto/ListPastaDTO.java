package com.example.pastebin.dto;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonRootName("pastes")
public class ListPastaDTO {
    private String link;
    private String title;

    public ListPastaDTO(String link, String title) {
        this.link = link;
        this.title = title;
    }

}
