package com.example.pastebin.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetPastaDTO {
    private String link;
    private String title;
    private String content;

    public GetPastaDTO(String link, String title, String content) {
        this.link = "http://my-awesome-pastebin.tld/" + link;
        this.title = title;
        this.content = content;
    }
}
