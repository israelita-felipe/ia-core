package com.ia.core.quartz.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.ia.core.quartz.model.periodicidade.converter.DayOfWeekConverter;
import java.time.DateTimeException;
import java.time.DayOfWeek;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DayOfWeekConverterTest {

    private final DayOfWeekConverter converter = new DayOfWeekConverter();

    @Test
    @DisplayName("convertToDatabaseColumn null dayOfWeek returns null")
    void convertToDatabaseColumn_nullReturnsNull() {
        Integer result = converter.convertToDatabaseColumn(null);
        assertThat(result).isNull();
    }

    @Test
    @DisplayName("convertToDatabaseColumn dayOfWeek returns correct numeric value")
    void convertToDatabaseColumn_dayOfWeekReturnsNumeric() {
        for (DayOfWeek day : DayOfWeek.values()) {
            Integer dbVal = converter.convertToDatabaseColumn(day);
            assertThat(dbVal).isNotNull();
            assertThat(dbVal).isEqualTo(day.getValue());
        }
    }

    @Test
    @DisplayName("convertToEntityAttribute null integer returns null")
    void convertToEntityAttribute_nullReturnsNull() {
        DayOfWeek result = converter.convertToEntityAttribute(null);
        assertThat(result).isNull();
    }

    @Test
    @DisplayName("convertToEntityAttribute invalid integer throws DateTimeException")
    void convertToEntityAttribute_invalidIntegerThrows() {
        assertThatThrownBy(() -> converter.convertToEntityAttribute(0))
            .isInstanceOf(DateTimeException.class);
    }

    @Test
    @DisplayName("convertToEntityAttribute valid integer returns DayOfWeek")
    void convertToEntityAttribute_validIntegerReturnsDayOfWeek() {
        for (DayOfWeek day : DayOfWeek.values()) {
            Integer num = day.getValue();
            DayOfWeek converted = converter.convertToEntityAttribute(num);
            assertThat(converted).isEqualTo(day);
        }
    }
}
