package com.example.pastebin.enums;

import lombok.Getter;

import java.time.temporal.ChronoUnit;
@Getter
public enum ExpirationTime {
    TEN_MIN( 10, ChronoUnit.MINUTES),
    ONE_HOUR( 1, ChronoUnit.HOURS),
    TREE_HOUR( 3, ChronoUnit.HOURS),
    ONE_DAY(1, ChronoUnit.DAYS),
    ONE_WEEK(7, ChronoUnit.DAYS),
    ONE_MONTH(30, ChronoUnit.DAYS),
    UNLIMITED(9999999, ChronoUnit.DAYS);

    private final Integer time;
    private final ChronoUnit unit;

    ExpirationTime(Integer time, ChronoUnit unit) {
        this.time = time;
        this.unit = unit;
    }
}
