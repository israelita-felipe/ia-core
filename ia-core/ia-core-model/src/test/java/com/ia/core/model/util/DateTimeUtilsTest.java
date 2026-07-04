package com.ia.core.model.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("DateTimeUtils")
class DateTimeUtilsTest {

    @Test
    @DisplayName("DATE_TIME_FORMATTER deve formatar data e hora completa")
    void dateTimeFormatterDeveFormatarCorretamente() {
        LocalDateTime dt = LocalDateTime.of(2025, 3, 15, 14, 30, 45, 123_000_000);
        String formatted = dt.format(DateTimeUtils.DATE_TIME_FORMATTER);
        assertThat(formatted).isEqualTo("15/03/2025 14:30:45.123");
    }

    @Test
    @DisplayName("DATE_FORMATTER deve formatar apenas data")
    void dateFormatterDeveFormatarCorretamente() {
        LocalDate date = LocalDate.of(2025, 12, 25);
        String formatted = date.format(DateTimeUtils.DATE_FORMATTER);
        assertThat(formatted).isEqualTo("25/12/2025");
    }

    @Test
    @DisplayName("TIME_FORMATTER deve formatar apenas hora")
    void timeFormatterDeveFormatarCorretamente() {
        LocalTime time = LocalTime.of(8, 5, 30);
        String formatted = time.format(DateTimeUtils.TIME_FORMATTER);
        assertThat(formatted).isEqualTo("08:05:30");
    }

    @Test
    @DisplayName("DATE_FORMATTER deve fazer parse de data corretamente")
    void dateFormatterDeveParseCorretamente() {
        LocalDate parsed = LocalDate.parse("01/01/2025", DateTimeUtils.DATE_FORMATTER);
        assertThat(parsed).isEqualTo(LocalDate.of(2025, 1, 1));
    }

    @Test
    @DisplayName("TIME_FORMATTER deve fazer parse de hora corretamente")
    void timeFormatterDeveParseCorretamente() {
        LocalTime parsed = LocalTime.parse("23:59:59", DateTimeUtils.TIME_FORMATTER);
        assertThat(parsed).isEqualTo(LocalTime.of(23, 59, 59));
    }
}
