package com.ia.core.quartz.model.periodicidade.converter;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes para a classe DayOfWeekConverter.
 */
@DisplayName("Testes de DayOfWeekConverter")
class DayOfWeekConverterTest {

    private final DayOfWeekConverter converter = new DayOfWeekConverter();

    @Test
    @DisplayName("CT001 - Verificar conversão para banco de dados")
    void testConversaoParaBancoDeDados() {
        assertEquals(1, converter.convertToDatabaseColumn(DayOfWeek.MONDAY));
        assertEquals(2, converter.convertToDatabaseColumn(DayOfWeek.TUESDAY));
        assertEquals(3, converter.convertToDatabaseColumn(DayOfWeek.WEDNESDAY));
        assertEquals(4, converter.convertToDatabaseColumn(DayOfWeek.THURSDAY));
        assertEquals(5, converter.convertToDatabaseColumn(DayOfWeek.FRIDAY));
        assertEquals(6, converter.convertToDatabaseColumn(DayOfWeek.SATURDAY));
        assertEquals(7, converter.convertToDatabaseColumn(DayOfWeek.SUNDAY));
        assertNull(converter.convertToDatabaseColumn(null));
    }

    @Test
    @DisplayName("CT002 - Verificar conversão para entidade")
    void testConversaoParaEntidade() {
        assertEquals(DayOfWeek.MONDAY, converter.convertToEntityAttribute(1));
        assertEquals(DayOfWeek.TUESDAY, converter.convertToEntityAttribute(2));
        assertEquals(DayOfWeek.WEDNESDAY, converter.convertToEntityAttribute(3));
        assertEquals(DayOfWeek.THURSDAY, converter.convertToEntityAttribute(4));
        assertEquals(DayOfWeek.FRIDAY, converter.convertToEntityAttribute(5));
        assertEquals(DayOfWeek.SATURDAY, converter.convertToEntityAttribute(6));
        assertEquals(DayOfWeek.SUNDAY, converter.convertToEntityAttribute(7));
        assertNull(converter.convertToEntityAttribute(null));
    }
}
