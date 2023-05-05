package com.example.pastebin.repository;

import com.example.pastebin.model.Paste;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface PasteRepository extends JpaRepository<Paste, String> {

    List<Paste> findTop10ByAccessOrderByPubDate(String access);

    @Query(value = "SELECT * FROM paste WHERE access = :access AND (LOWER(title) LIKE LOWER(CONCAT('%', :searchTerm, '%'))) OR access = :access AND (LOWER(content) LIKE LOWER(CONCAT('%', :searchTerm, '%')))", nativeQuery = true)
    List<Paste> findByAccessAndTitleContainingIgnoreCaseOrAccessAndContentContainingIgnoreCase(
            @Param("access") String access,
            @Param("searchTerm") String searchTerm
    );

    @Modifying
    @Query(value = "delete from Paste p where p.validity < now()")
    void deleteAll(Instant now);

    Optional<Paste> findByLinkEndingWith(String hash);
}
