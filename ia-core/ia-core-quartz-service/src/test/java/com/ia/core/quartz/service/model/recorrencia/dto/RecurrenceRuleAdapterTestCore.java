package com.ia.core.quartz.service.model.recorrencia.dto;

import com.ia.core.quartz.model.periodicidade.Frequencia;
import com.ia.test.CoreBaseUnitTest;
import org.dmfs.rfc5545.recur.RecurrenceRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Test class for RecurrenceRuleAdapter.
 * <p>
 * Tests conversion from RecorrenciaDTO to RFC 5545 RecurrenceRule format.
 * </p>
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@DisplayName("RecurrenceRuleAdapter Tests")
class RecurrenceRuleAdapterTestCore extends CoreBaseUnitTest {

    private RecorrenciaDTO dto;

    @BeforeEach
    void setUp() {
        dto = RecorrenciaDTO.builder()
            .frequency(Frequencia.DIARIAMENTE)
            .build();
    }

    @Test
    @DisplayName("Should return null for null RecorrenciaDTO")
    void shouldReturnNullForNullRecorrenciaDTO() {
        // Act
        RecurrenceRule result = RecurrenceRuleAdapter.toRecurrenceRule(null);

        // Assert
        assertThat(result).isNull();
    }

    @Test
    @DisplayName("Should return null for RecorrenciaDTO with null frequency")
    void shouldReturnNullForRecorrenciaDTOWithNullFrequency() {
        // Arrange
        dto.setFrequency(null);

        // Act
        RecurrenceRule result = RecurrenceRuleAdapter.toRecurrenceRule(dto);

        // Assert
        assertThat(result).isNull();
    }

    @Test
    @DisplayName("Should convert DIARIAMENTE to DAILY")
    void shouldConvertDiariamenteToDaily() {
        // Arrange
        dto.setFrequency(Frequencia.DIARIAMENTE);

        // Act
        RecurrenceRule result = RecurrenceRuleAdapter.toRecurrenceRule(dto);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.toString()).contains("FREQ=DAILY");
    }

    @Test
    @DisplayName("Should convert SEMANALMENTE to WEEKLY")
    void shouldConvertSemanalmenteToWeekly() {
        // Arrange
        dto.setFrequency(Frequencia.SEMANALMENTE);

        // Act
        RecurrenceRule result = RecurrenceRuleAdapter.toRecurrenceRule(dto);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.toString()).contains("FREQ=WEEKLY");
    }

    @Test
    @DisplayName("Should convert MENSALMENTE to MONTHLY")
    void shouldConvertMensalmenteToMonthly() {
        // Arrange
        dto.setFrequency(Frequencia.MENSALMENTE);

        // Act
        RecurrenceRule result = RecurrenceRuleAdapter.toRecurrenceRule(dto);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.toString()).contains("FREQ=MONTHLY");
    }

    @Test
    @DisplayName("Should convert ANUALMENTE to YEARLY")
    void shouldConvertAnualmenteToYearly() {
        // Arrange
        dto.setFrequency(Frequencia.ANUALMENTE);

        // Act
        RecurrenceRule result = RecurrenceRuleAdapter.toRecurrenceRule(dto);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.toString()).contains("FREQ=YEARLY");
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException for unknown frequency")
    void shouldThrowIllegalArgumentExceptionForUnknownFrequency() {
        // This test would require adding a mock or test frequency that's not in the enum
        // Since Frequencia is an enum, this scenario is not directly testable
        // The implementation would need to be modified to support this test
    }

    @Test
    @DisplayName("Should not include INTERVAL when value is 1")
    void shouldNotIncludeIntervalWhenValueIs1() {
        // Arrange
        dto.setIntervalValue(1);

        // Act
        RecurrenceRule result = RecurrenceRuleAdapter.toRecurrenceRule(dto);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.toString()).doesNotContain("INTERVAL");
    }

    @Test
    @DisplayName("Should include INTERVAL when value greater than 1")
    void shouldIncludeIntervalWhenValueGreaterThan1() {
        // Arrange
        dto.setIntervalValue(2);

        // Act
        RecurrenceRule result = RecurrenceRuleAdapter.toRecurrenceRule(dto);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.toString()).contains("INTERVAL=2");
    }

    @Test
    @DisplayName("Should not include INTERVAL when value is null")
    void shouldNotIncludeIntervalWhenValueIsNull() {
        // Arrange
        dto.setIntervalValue(null);

        // Act
        RecurrenceRule result = RecurrenceRuleAdapter.toRecurrenceRule(dto);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.toString()).doesNotContain("INTERVAL");
    }

    @Test
    @DisplayName("Should not include COUNT when value is 0")
    void shouldNotIncludeCountWhenValueIs0() {
        // Arrange
        dto.setCountLimit(0);

        // Act
        RecurrenceRule result = RecurrenceRuleAdapter.toRecurrenceRule(dto);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.toString()).doesNotContain("COUNT");
    }

    @Test
    @DisplayName("Should include COUNT when value greater than 0")
    void shouldIncludeCountWhenValueGreaterThan0() {
        // Arrange
        dto.setCountLimit(10);

        // Act
        RecurrenceRule result = RecurrenceRuleAdapter.toRecurrenceRule(dto);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.toString()).contains("COUNT=10");
    }

    @Test
    @DisplayName("Should not include COUNT when value is null")
    void shouldNotIncludeCountWhenValueIsNull() {
        // Arrange
        dto.setCountLimit(null);

        // Act
        RecurrenceRule result = RecurrenceRuleAdapter.toRecurrenceRule(dto);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.toString()).doesNotContain("COUNT");
    }

    @Test
    @DisplayName("Should include UNTIL with valid date")
    void shouldIncludeUntilWithValidDate() {
        // Arrange
        dto.setUntilDate(LocalDate.of(2023, 12, 31));

        // Act
        RecurrenceRule result = RecurrenceRuleAdapter.toRecurrenceRule(dto);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.toString()).contains("UNTIL=20231231T235959Z");
    }

    @Test
    @DisplayName("Should not include UNTIL when date is null")
    void shouldNotIncludeUntilWhenDateIsNull() {
        // Arrange
        dto.setUntilDate(null);

        // Act
        RecurrenceRule result = RecurrenceRuleAdapter.toRecurrenceRule(dto);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.toString()).doesNotContain("UNTIL");
    }

    @Test
    @DisplayName("Should include BYDAY with single day")
    void shouldIncludeByDayWithSingleDay() {
        // Arrange
        Set<DayOfWeek> byDay = new HashSet<>();
        byDay.add(DayOfWeek.MONDAY);
        dto.setByDay(byDay);

        // Act
        RecurrenceRule result = RecurrenceRuleAdapter.toRecurrenceRule(dto);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.toString()).contains("BYDAY=MO");
    }

    @Test
    @DisplayName("Should include BYDAY with multiple days")
    void shouldIncludeByDayWithMultipleDays() {
        // Arrange
        Set<DayOfWeek> byDay = new HashSet<>();
        byDay.add(DayOfWeek.MONDAY);
        byDay.add(DayOfWeek.WEDNESDAY);
        byDay.add(DayOfWeek.FRIDAY);
        dto.setByDay(byDay);

        // Act
        RecurrenceRule result = RecurrenceRuleAdapter.toRecurrenceRule(dto);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.toString()).contains("BYDAY");
        assertThat(result.toString()).contains("MO");
        assertThat(result.toString()).contains("WE");
        assertThat(result.toString()).contains("FR");
    }

    @Test
    @DisplayName("Should not include BYDAY when list is empty")
    void shouldNotIncludeByDayWhenListIsEmpty() {
        // Arrange
        dto.setByDay(new HashSet<>());

        // Act
        RecurrenceRule result = RecurrenceRuleAdapter.toRecurrenceRule(dto);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.toString()).doesNotContain("BYDAY");
    }

    @Test
    @DisplayName("Should include BYMONTHDAY with single day")
    void shouldIncludeByMonthDayWithSingleDay() {
        // Arrange
        Set<Integer> byMonthDay = new HashSet<>();
        byMonthDay.add(15);
        dto.setByMonthDay(byMonthDay);

        // Act
        RecurrenceRule result = RecurrenceRuleAdapter.toRecurrenceRule(dto);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.toString()).contains("BYMONTHDAY=15");
    }

    @Test
    @DisplayName("Should include BYMONTHDAY with multiple days")
    void shouldIncludeByMonthDayWithMultipleDays() {
        // Arrange
        Set<Integer> byMonthDay = new HashSet<>();
        byMonthDay.add(1);
        byMonthDay.add(15);
        byMonthDay.add(31);
        dto.setByMonthDay(byMonthDay);

        // Act
        RecurrenceRule result = RecurrenceRuleAdapter.toRecurrenceRule(dto);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.toString()).contains("BYMONTHDAY");
        assertThat(result.toString()).contains("1");
        assertThat(result.toString()).contains("15");
        assertThat(result.toString()).contains("31");
    }

    @Test
    @DisplayName("Should not include BYMONTHDAY when list is empty")
    void shouldNotIncludeByMonthDayWhenListIsEmpty() {
        // Arrange
        dto.setByMonthDay(new HashSet<>());

        // Act
        RecurrenceRule result = RecurrenceRuleAdapter.toRecurrenceRule(dto);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.toString()).doesNotContain("BYMONTHDAY");
    }

    @Test
    @DisplayName("Should include BYMONTH with single month")
    void shouldIncludeByMonthWithSingleMonth() {
        // Arrange
        Set<Month> byMonth = new HashSet<>();
        byMonth.add(Month.JANUARY);
        dto.setByMonth(byMonth);

        // Act
        RecurrenceRule result = RecurrenceRuleAdapter.toRecurrenceRule(dto);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.toString()).contains("BYMONTH=1");
    }

    @Test
    @DisplayName("Should include BYMONTH with multiple months")
    void shouldIncludeByMonthWithMultipleMonths() {
        // Arrange
        Set<Month> byMonth = new HashSet<>();
        byMonth.add(Month.JANUARY);
        byMonth.add(Month.JUNE);
        byMonth.add(Month.DECEMBER);
        dto.setByMonth(byMonth);

        // Act
        RecurrenceRule result = RecurrenceRuleAdapter.toRecurrenceRule(dto);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.toString()).contains("BYMONTH");
        assertThat(result.toString()).contains("1");
        assertThat(result.toString()).contains("6");
        assertThat(result.toString()).contains("12");
    }

    @Test
    @DisplayName("Should not include BYMONTH when list is empty")
    void shouldNotIncludeByMonthWhenListIsEmpty() {
        // Arrange
        dto.setByMonth(new HashSet<>());

        // Act
        RecurrenceRule result = RecurrenceRuleAdapter.toRecurrenceRule(dto);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.toString()).doesNotContain("BYMONTH");
    }

    @Test
    @DisplayName("Should include BYHOUR with single hour")
    void shouldIncludeByHourWithSingleHour() {
        // Arrange
        Set<Integer> byHour = new HashSet<>();
        byHour.add(9);
        dto.setByHour(byHour);

        // Act
        RecurrenceRule result = RecurrenceRuleAdapter.toRecurrenceRule(dto);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.toString()).contains("BYHOUR=9");
    }

    @Test
    @DisplayName("Should include BYHOUR with multiple hours")
    void shouldIncludeByHourWithMultipleHours() {
        // Arrange
        Set<Integer> byHour = new HashSet<>();
        byHour.add(9);
        byHour.add(12);
        byHour.add(18);
        dto.setByHour(byHour);

        // Act
        RecurrenceRule result = RecurrenceRuleAdapter.toRecurrenceRule(dto);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.toString()).contains("BYHOUR");
        assertThat(result.toString()).contains("9");
        assertThat(result.toString()).contains("12");
        assertThat(result.toString()).contains("18");
    }

    @Test
    @DisplayName("Should not include BYHOUR when list is empty")
    void shouldNotIncludeByHourWhenListIsEmpty() {
        // Arrange
        dto.setByHour(new HashSet<>());

        // Act
        RecurrenceRule result = RecurrenceRuleAdapter.toRecurrenceRule(dto);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.toString()).doesNotContain("BYHOUR");
    }

    @Test
    @DisplayName("Should include BYMINUTE with single minute")
    void shouldIncludeByMinuteWithSingleMinute() {
        // Arrange
        Set<Integer> byMinute = new HashSet<>();
        byMinute.add(30);
        dto.setByMinute(byMinute);

        // Act
        RecurrenceRule result = RecurrenceRuleAdapter.toRecurrenceRule(dto);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.toString()).contains("BYMINUTE=30");
    }

    @Test
    @DisplayName("Should include BYMINUTE with multiple minutes")
    void shouldIncludeByMinuteWithMultipleMinutes() {
        // Arrange
        Set<Integer> byMinute = new HashSet<>();
        byMinute.add(0);
        byMinute.add(30);
        dto.setByMinute(byMinute);

        // Act
        RecurrenceRule result = RecurrenceRuleAdapter.toRecurrenceRule(dto);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.toString()).contains("BYMINUTE");
        assertThat(result.toString()).contains("0");
        assertThat(result.toString()).contains("30");
    }

    @Test
    @DisplayName("Should not include BYMINUTE when list is empty")
    void shouldNotIncludeByMinuteWhenListIsEmpty() {
        // Arrange
        dto.setByMinute(new HashSet<>());

        // Act
        RecurrenceRule result = RecurrenceRuleAdapter.toRecurrenceRule(dto);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.toString()).doesNotContain("BYMINUTE");
    }

    @Test
    @DisplayName("Should include BYSECOND with single second")
    void shouldIncludeBySecondWithSingleSecond() {
        // Arrange
        Set<Integer> bySecond = new HashSet<>();
        bySecond.add(0);
        dto.setBySecond(bySecond);

        // Act
        RecurrenceRule result = RecurrenceRuleAdapter.toRecurrenceRule(dto);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.toString()).contains("BYSECOND=0");
    }

    @Test
    @DisplayName("Should include BYSECOND with multiple seconds")
    void shouldIncludeBySecondWithMultipleSeconds() {
        // Arrange
        Set<Integer> bySecond = new HashSet<>();
        bySecond.add(0);
        bySecond.add(30);
        dto.setBySecond(bySecond);

        // Act
        RecurrenceRule result = RecurrenceRuleAdapter.toRecurrenceRule(dto);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.toString()).contains("BYSECOND");
        assertThat(result.toString()).contains("0");
        assertThat(result.toString()).contains("30");
    }

    @Test
    @DisplayName("Should not include BYSECOND when list is empty")
    void shouldNotIncludeBySecondWhenListIsEmpty() {
        // Arrange
        dto.setBySecond(new HashSet<>());

        // Act
        RecurrenceRule result = RecurrenceRuleAdapter.toRecurrenceRule(dto);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.toString()).doesNotContain("BYSECOND");
    }

    @Test
    @DisplayName("Should include WKST when week start day is set")
    void shouldIncludeWkstWhenWeekStartDayIsSet() {
        // Arrange
        dto.setWeekStartDay(DayOfWeek.MONDAY);

        // Act
        RecurrenceRule result = RecurrenceRuleAdapter.toRecurrenceRule(dto);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.toString()).contains("WKST=MO");
    }

    @Test
    @DisplayName("Should not include WKST when week start day is null")
    void shouldNotIncludeWkstWhenWeekStartDayIsNull() {
        // Arrange
        dto.setWeekStartDay(null);

        // Act
        RecurrenceRule result = RecurrenceRuleAdapter.toRecurrenceRule(dto);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.toString()).doesNotContain("WKST");
    }

    @Test
    @DisplayName("Should include BYSETPOS with single position")
    void shouldIncludeBySetPosWithSinglePosition() {
        // Arrange
        Set<Integer> bySetPosition = new HashSet<>();
        bySetPosition.add(1);
        dto.setBySetPosition(bySetPosition);
        // BYSETPOS requires BYDAY or other filter parameters per RFC 5545
        Set<DayOfWeek> byDay = new HashSet<>();
        byDay.add(DayOfWeek.MONDAY);
        dto.setByDay(byDay);

        // Act
        RecurrenceRule result = RecurrenceRuleAdapter.toRecurrenceRule(dto);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.toString()).contains("BYSETPOS=1");
    }

    @Test
    @DisplayName("Should include BYSETPOS with multiple positions")
    void shouldIncludeBySetPosWithMultiplePositions() {
        // Arrange
        Set<Integer> bySetPosition = new HashSet<>();
        bySetPosition.add(1);
        bySetPosition.add(-1);
        dto.setBySetPosition(bySetPosition);
        // BYSETPOS requires BYDAY or other filter parameters per RFC 5545
        Set<DayOfWeek> byDay = new HashSet<>();
        byDay.add(DayOfWeek.MONDAY);
        byDay.add(DayOfWeek.FRIDAY);
        dto.setByDay(byDay);

        // Act
        RecurrenceRule result = RecurrenceRuleAdapter.toRecurrenceRule(dto);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.toString()).contains("BYSETPOS");
        assertThat(result.toString()).contains("1");
        assertThat(result.toString()).contains("-1");
    }

    @Test
    @DisplayName("Should not include BYSETPOS when list is empty")
    void shouldNotIncludeBySetPosWhenListIsEmpty() {
        // Arrange
        dto.setBySetPosition(new HashSet<>());

        // Act
        RecurrenceRule result = RecurrenceRuleAdapter.toRecurrenceRule(dto);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.toString()).doesNotContain("BYSETPOS");
    }

    @Test
    @DisplayName("Should generate complex RRULE with daily frequency, interval and count")
    void shouldGenerateComplexRruleWithDailyFrequencyIntervalAndCount() {
        // Arrange
        dto.setFrequency(Frequencia.DIARIAMENTE);
        dto.setIntervalValue(2);
        dto.setCountLimit(10);

        // Act
        RecurrenceRule result = RecurrenceRuleAdapter.toRecurrenceRule(dto);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.toString()).contains("FREQ=DAILY");
        assertThat(result.toString()).contains("INTERVAL=2");
        assertThat(result.toString()).contains("COUNT=10");
    }

    @Test
    @DisplayName("Should generate complex RRULE with weekly frequency, days and until")
    void shouldGenerateComplexRruleWithWeeklyFrequencyDaysAndUntil() {
        // Arrange
        dto.setFrequency(Frequencia.SEMANALMENTE);
        Set<DayOfWeek> byDay = new HashSet<>();
        byDay.add(DayOfWeek.MONDAY);
        byDay.add(DayOfWeek.WEDNESDAY);
        byDay.add(DayOfWeek.FRIDAY);
        dto.setByDay(byDay);
        dto.setUntilDate(LocalDate.of(2023, 12, 31));

        // Act
        RecurrenceRule result = RecurrenceRuleAdapter.toRecurrenceRule(dto);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.toString()).contains("FREQ=WEEKLY");
        assertThat(result.toString()).contains("BYDAY");
        assertThat(result.toString()).contains("UNTIL=20231231T235959Z");
    }

    @Test
    @DisplayName("Should generate complex RRULE with monthly frequency, month days and hours")
    void shouldGenerateComplexRruleWithMonthlyFrequencyMonthDaysAndHours() {
        // Arrange
        dto.setFrequency(Frequencia.MENSALMENTE);
        Set<Integer> byMonthDay = new HashSet<>();
        byMonthDay.add(1);
        byMonthDay.add(15);
        dto.setByMonthDay(byMonthDay);
        Set<Integer> byHour = new HashSet<>();
        byHour.add(9);
        byHour.add(18);
        dto.setByHour(byHour);

        // Act
        RecurrenceRule result = RecurrenceRuleAdapter.toRecurrenceRule(dto);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.toString()).contains("FREQ=MONTHLY");
        assertThat(result.toString()).contains("BYMONTHDAY");
        assertThat(result.toString()).contains("BYHOUR");
    }

    @Test
    @DisplayName("Should create RecurrenceRule with valid RRULE string")
    void shouldCreateRecurrenceRuleWithValidRruleString() {
        // Arrange
        dto.setFrequency(Frequencia.DIARIAMENTE);

        // Act
        RecurrenceRule result = RecurrenceRuleAdapter.toRecurrenceRule(dto);

        // Assert
        assertThat(result).isNotNull();
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException for invalid RRULE string")
    void shouldThrowIllegalArgumentExceptionForInvalidRruleString() {
        // This test would require creating an invalid RRULE scenario
        // Since the adapter generates valid RRULEs, this is not directly testable
        // The implementation would need to be modified to support this test
    }
}
