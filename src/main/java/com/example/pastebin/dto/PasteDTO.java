package com.example.pastebin.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
public class PasteDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String link;
    private String title;
    private String content;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String access;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Instant pubDate;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Instant validity;

    public PasteDTO(String link, String title, String content, String access, Instant pubDate, Instant validity) {
        this.link = link;
        this.title = title;
        this.content = content;
        this.access = access;
        this.pubDate = pubDate;
        this.validity = validity;
    }
}
