package com.example.pastebin.repository;

import com.example.pastebin.model.Paste;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface PasteRepository extends JpaRepository<Paste, String> {

    List<Paste> findTop10ByAccessOrderByPubDate(String access);

    List<Paste> findByAccessAndTitleContainingIgnoreCaseOrAccessAndContentContainingIgnoreCase(String access, String title, String access2, String content);

    void deleteAllByValidityBefore(Instant now);









}
