package com.example.pastebin.dto;

import com.example.pastebin.enums.Access;
import com.example.pastebin.model.Paste;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class PasteDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String link;
    private String title;
    private String content;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Access access;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Instant pubDate;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Instant validity;

    public PasteDTO toDto(Paste paste) {
        PasteDTO dto = new PasteDTO();
        dto.setTitle(paste.getTitle());
        dto.setContent(paste.getContent());
        dto.setLink(paste.getLink());
        dto.setAccess(access);
        dto.setPubDate(paste.getPubDate());
        dto.setValidity(validity);
        return dto;
    }
}
