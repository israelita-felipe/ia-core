package com.ia.core.quartz.model.periodicidade.converter;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes para a classe MonthConverter.
 */
@DisplayName("Testes de MonthConverter")
class MonthConverterTest {

    private final MonthConverter converter = new MonthConverter();

    @Test
    @DisplayName("CT001 - Verificar conversão para banco de dados")
    void testConversaoParaBancoDeDados() {
        assertEquals(1, converter.convertToDatabaseColumn(Month.JANUARY));
        assertEquals(2, converter.convertToDatabaseColumn(Month.FEBRUARY));
        assertEquals(3, converter.convertToDatabaseColumn(Month.MARCH));
        assertEquals(4, converter.convertToDatabaseColumn(Month.APRIL));
        assertEquals(5, converter.convertToDatabaseColumn(Month.MAY));
        assertEquals(6, converter.convertToDatabaseColumn(Month.JUNE));
        assertEquals(7, converter.convertToDatabaseColumn(Month.JULY));
        assertEquals(8, converter.convertToDatabaseColumn(Month.AUGUST));
        assertEquals(9, converter.convertToDatabaseColumn(Month.SEPTEMBER));
        assertEquals(10, converter.convertToDatabaseColumn(Month.OCTOBER));
        assertEquals(11, converter.convertToDatabaseColumn(Month.NOVEMBER));
        assertEquals(12, converter.convertToDatabaseColumn(Month.DECEMBER));
        assertNull(converter.convertToDatabaseColumn(null));
    }

    @Test
    @DisplayName("CT002 - Verificar conversão para entidade")
    void testConversaoParaEntidade() {
        assertEquals(Month.JANUARY, converter.convertToEntityAttribute(1));
        assertEquals(Month.FEBRUARY, converter.convertToEntityAttribute(2));
        assertEquals(Month.MARCH, converter.convertToEntityAttribute(3));
        assertEquals(Month.APRIL, converter.convertToEntityAttribute(4));
        assertEquals(Month.MAY, converter.convertToEntityAttribute(5));
        assertEquals(Month.JUNE, converter.convertToEntityAttribute(6));
        assertEquals(Month.JULY, converter.convertToEntityAttribute(7));
        assertEquals(Month.AUGUST, converter.convertToEntityAttribute(8));
        assertEquals(Month.SEPTEMBER, converter.convertToEntityAttribute(9));
        assertEquals(Month.OCTOBER, converter.convertToEntityAttribute(10));
        assertEquals(Month.NOVEMBER, converter.convertToEntityAttribute(11));
        assertEquals(Month.DECEMBER, converter.convertToEntityAttribute(12));
        assertNull(converter.convertToEntityAttribute(null));
    }
}
