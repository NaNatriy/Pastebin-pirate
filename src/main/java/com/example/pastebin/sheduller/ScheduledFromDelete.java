package com.example.pastebin.sheduller;

import com.example.pastebin.repository.PasteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Component
@Slf4j
public class ScheduledFromDelete {
    private final PasteRepository pasteRepository;

    public ScheduledFromDelete(PasteRepository pasteRepository) {
        this.pasteRepository = pasteRepository;
    }


    @Scheduled(fixedDelayString = "${scheduled.deleteExpiredPastes.delay}")
    @Transactional
    public void deleteExpiredPastes() {
        Instant now = Instant.now();
        pasteRepository.deleteAll(now);
    }
}
