package com.ia.core.quartz.model;

import com.ia.core.quartz.model.periodicidade.Frequencia;
import com.ia.core.quartz.model.periodicidade.IntervaloTemporal;
import com.ia.core.quartz.service.model.periodicidade.dto.IntervaloTemporalDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("unit")
@DisplayName("Quartz value object contract tests")
class QuartzValueObjectContractTest {

  @Nested
  @DisplayName("Frequencia")
  class FrequenciaTests {

    @Test
    @DisplayName("Should map frequency to RFC 5545 names")
    void shouldMapFrequencyToRfcNames() {
      assertThat(Frequencia.DIARIAMENTE.getRfcName()).isEqualTo("DAILY");
      assertThat(Frequencia.SEMANALMENTE.getRfcName()).isEqualTo("WEEKLY");
      assertThat(Frequencia.MENSALMENTE.getRfcName()).isEqualTo("MONTHLY");
      assertThat(Frequencia.ANUALMENTE.getRfcName()).isEqualTo("YEARLY");
    }

    @Test
    @DisplayName("Should map frequency code and lookup helpers")
    void shouldMapFrequencyCodeAndLookupHelpers() {
      assertThat(Frequencia.of(3)).isEqualTo(Frequencia.MENSALMENTE);
      assertThat(Frequencia.fromRfcName("weekly")).isEqualTo(Frequencia.SEMANALMENTE);
      assertThat(Frequencia.fromRfcName(null)).isNull();
      assertThat(Frequencia.of(99)).isNull();
    }
  }

  @Nested
  @DisplayName("IntervaloTemporalDTO")
  class IntervaloTemporalDTOTests {

    @Test
    @DisplayName("Should calculate duration when start and end are present")
    void shouldCalculateDurationWhenStartAndEndArePresent() {
      IntervaloTemporalDTO dto = IntervaloTemporalDTO.builder()
          .startDate(LocalDate.of(2026, 1, 1))
          .startTime(LocalTime.of(9, 0))
          .endDate(LocalDate.of(2026, 1, 1))
          .endTime(LocalTime.of(10, 30)).build();

      Duration duration = Duration.between(
          LocalDateTime.of(dto.getStartDate(), dto.getStartTime()),
          LocalDateTime.of(dto.getEndDate(), dto.getEndTime()));
      assertThat(duration.toMinutes()).isEqualTo(90);
    }

    @Test
    @DisplayName("Should return zero duration when start is missing")
    void shouldReturnZeroDurationWhenStartIsMissing() {
      IntervaloTemporalDTO dto = IntervaloTemporalDTO.builder()
          .endDate(LocalDate.of(2026, 1, 1))
          .endTime(LocalTime.of(10, 30)).build();

      assertThat(dto.getStartDate()).isNull();
      assertThat(dto.getStartTime()).isNull();
      assertThat(dto.getEndDate()).isNotNull();
      assertThat(dto.getEndTime()).isNotNull();
    }

    @Test
    @DisplayName("Should compare intervals by start and end components")
    void shouldCompareIntervalsByStartAndEndComponents() {
      IntervaloTemporalDTO first = IntervaloTemporalDTO.builder()
          .startDate(LocalDate.of(2026, 1, 1))
          .startTime(LocalTime.of(9, 0))
          .endDate(LocalDate.of(2026, 1, 1))
          .endTime(LocalTime.of(10, 0)).build();
      IntervaloTemporalDTO second = IntervaloTemporalDTO.builder()
          .startDate(LocalDate.of(2026, 1, 2))
          .startTime(LocalTime.of(9, 0))
          .endDate(LocalDate.of(2026, 1, 2))
          .endTime(LocalTime.of(10, 0)).build();

      assertThat(first.compareTo(second)).isNegative();
      assertThat(first.intersects(second)).isFalse();
    }

    @Test
    @DisplayName("Should detect interval intersection")
    void shouldDetectIntervalIntersection() {
      IntervaloTemporalDTO first = IntervaloTemporalDTO.builder()
          .startDate(LocalDate.of(2026, 1, 1))
          .startTime(LocalTime.of(9, 0))
          .endDate(LocalDate.of(2026, 1, 1))
          .endTime(LocalTime.of(12, 0)).build();
      IntervaloTemporalDTO second = IntervaloTemporalDTO.builder()
          .startDate(LocalDate.of(2026, 1, 1))
          .startTime(LocalTime.of(11, 0))
          .endDate(LocalDate.of(2026, 1, 1))
          .endTime(LocalTime.of(13, 0)).build();

      assertThat(first.intersects(second)).isTrue();
    }
  }

  @Nested
  @DisplayName("Model converters")
  class ConverterTests {

    @Test
    @DisplayName("Should expose temporal model helpers")
    void shouldExposeTemporalModelHelpers() {
      IntervaloTemporal sameDay = new IntervaloTemporal(LocalDate.of(2026, 1, 1),
          LocalTime.of(9, 0), LocalTime.of(10, 0));
      IntervaloTemporal multiDay = new IntervaloTemporal(LocalDate.of(2026, 1, 1),
          LocalTime.of(23, 0), LocalDate.of(2026, 1, 2), LocalTime.of(1, 0));

      assertThat(sameDay.getStartDate()).isEqualTo(LocalDate.of(2026, 1, 1));
      assertThat(sameDay.getEndDate()).isEqualTo(LocalDate.of(2026, 1, 1));
      assertThat(multiDay.getEndDate()).isEqualTo(LocalDate.of(2026, 1, 2));
      assertThat(DayOfWeek.MONDAY).isEqualTo(DayOfWeek.MONDAY);
      assertThat(Month.JANUARY).isEqualTo(Month.JANUARY);
    }
  }
}
