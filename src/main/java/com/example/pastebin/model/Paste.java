package com.example.pastebin.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.Instant;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Paste {
    @Id
    private String link;
    private String title;
    private String content;
    private String access;
    private Instant pubDate;
    private Instant validity;
}
