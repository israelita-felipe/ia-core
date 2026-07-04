package com.ia.core.quartz.model.periodicidade.converter;

import com.ia.core.quartz.model.periodicidade.Frequencia;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes para a classe FrequenciaConverter.
 */
@DisplayName("Testes de FrequenciaConverter")
class FrequenciaConverterTest {

    private final FrequenciaConverter converter = new FrequenciaConverter();

    @Test
    @DisplayName("CT001 - Verificar conversão para banco de dados")
    void testConversaoParaBancoDeDados() {
        assertEquals(1, converter.convertToDatabaseColumn(Frequencia.DIARIAMENTE));
        assertEquals(2, converter.convertToDatabaseColumn(Frequencia.SEMANALMENTE));
        assertEquals(3, converter.convertToDatabaseColumn(Frequencia.MENSALMENTE));
        assertEquals(4, converter.convertToDatabaseColumn(Frequencia.ANUALMENTE));
        assertNull(converter.convertToDatabaseColumn(null));
    }

    @Test
    @DisplayName("CT002 - Verificar conversão para entidade")
    void testConversaoParaEntidade() {
        assertEquals(Frequencia.DIARIAMENTE, converter.convertToEntityAttribute(1));
        assertEquals(Frequencia.SEMANALMENTE, converter.convertToEntityAttribute(2));
        assertEquals(Frequencia.MENSALMENTE, converter.convertToEntityAttribute(3));
        assertEquals(Frequencia.ANUALMENTE, converter.convertToEntityAttribute(4));
        assertNull(converter.convertToEntityAttribute(null));
        assertNull(converter.convertToEntityAttribute(99));
    }
}
