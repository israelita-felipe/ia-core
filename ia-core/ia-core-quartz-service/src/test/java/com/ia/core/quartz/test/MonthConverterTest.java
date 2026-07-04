package com.ia.core.quartz.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.ia.core.quartz.model.periodicidade.converter.MonthConverter;
import java.time.DateTimeException;
import java.time.Month;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MonthConverterTest {

    private final MonthConverter converter = new MonthConverter();

    @Test
    @DisplayName("convertToDatabaseColumn null month returns null")
    void convertToDatabaseColumn_nullReturnsNull() {
        Integer result = converter.convertToDatabaseColumn(null);
        assertThat(result).isNull();
    }

    @Test
    @DisplayName("convertToDatabaseColumn month returns correct numeric value")
    void convertToDatabaseColumn_monthReturnsNumeric() {
        for (Month month : Month.values()) {
            Integer dbVal = converter.convertToDatabaseColumn(month);
            assertThat(dbVal).isNotNull();
            assertThat(dbVal).isEqualTo(month.getValue());
        }
    }

    @Test
    @DisplayName("convertToEntityAttribute null integer returns null")
    void convertToEntityAttribute_nullReturnsNull() {
        Month result = converter.convertToEntityAttribute(null);
        assertThat(result).isNull();
    }

    @Test
    @DisplayName("convertToEntityAttribute invalid integer throws DateTimeException")
    void convertToEntityAttribute_invalidIntegerThrows() {
        assertThatThrownBy(() -> converter.convertToEntityAttribute(0))
            .isInstanceOf(DateTimeException.class);
    }

    @Test
    @DisplayName("convertToEntityAttribute valid integer returns Month")
    void convertToEntityAttribute_validIntegerReturnsMonth() {
        for (Month month : Month.values()) {
            Integer num = month.getValue();
            Month converted = converter.convertToEntityAttribute(num);
            assertThat(converted).isEqualTo(month);
        }
    }
}
