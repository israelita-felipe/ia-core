package com.ia.core.quartz.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Testes para a classe QuartzModel.
 */
@DisplayName("Testes de QuartzModel")
class QuartzModelTest {

    @Test
    @DisplayName("CT001 - Verificar constante TABLE_PREFIX")
    void testTablePrefix() {
        assertEquals("QRTZ_", QuartzModel.TABLE_PREFIX);
    }

    @Test
    @DisplayName("CT002 - Verificar constante SCHEMA")
    void testSchema() {
        assertEquals("QUARTZ", QuartzModel.SCHEMA);
    }
}
