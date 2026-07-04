package com.ia.core.quartz.model.periodicidade;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes para o enum Frequencia.
 */
@DisplayName("Testes de Frequencia")
class FrequenciaTest {

    @Test
    @DisplayName("CT001 - Verificar valores do enum")
    void testValoresEnum() {
        assertEquals("DAILY", Frequencia.DIARIAMENTE.getRfcName());
        assertEquals(1, Frequencia.DIARIAMENTE.getCodigo());

        assertEquals("WEEKLY", Frequencia.SEMANALMENTE.getRfcName());
        assertEquals(2, Frequencia.SEMANALMENTE.getCodigo());

        assertEquals("MONTHLY", Frequencia.MENSALMENTE.getRfcName());
        assertEquals(3, Frequencia.MENSALMENTE.getCodigo());

        assertEquals("YEARLY", Frequencia.ANUALMENTE.getRfcName());
        assertEquals(4, Frequencia.ANUALMENTE.getCodigo());
    }

    @Test
    @DisplayName("CT002 - Verificar método of(int)")
    void testMetodoOf() {
        assertEquals(Frequencia.DIARIAMENTE, Frequencia.of(1));
        assertEquals(Frequencia.SEMANALMENTE, Frequencia.of(2));
        assertEquals(Frequencia.MENSALMENTE, Frequencia.of(3));
        assertEquals(Frequencia.ANUALMENTE, Frequencia.of(4));
        assertNull(Frequencia.of(99));
    }

    @Test
    @DisplayName("CT003 - Verificar método fromRfcName(String)")
    void testMetodoFromRfcName() {
        assertEquals(Frequencia.DIARIAMENTE, Frequencia.fromRfcName("DAILY"));
        assertEquals(Frequencia.SEMANALMENTE, Frequencia.fromRfcName("WEEKLY"));
        assertEquals(Frequencia.MENSALMENTE, Frequencia.fromRfcName("MONTHLY"));
        assertEquals(Frequencia.ANUALMENTE, Frequencia.fromRfcName("YEARLY"));
        assertEquals(Frequencia.DIARIAMENTE, Frequencia.fromRfcName("daily"));
        assertNull(Frequencia.fromRfcName(null));
        assertNull(Frequencia.fromRfcName("INVALID"));
    }
}
