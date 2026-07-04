package com.ia.core.quartz.model.periodicidade;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes para a classe IntervaloTemporal.
 */
@DisplayName("Testes de IntervaloTemporal")
class IntervaloTemporalTest {

    @Test
    @DisplayName("CT001 - Verificar construtor com data e hora")
    void testConstrutorDataHora() {
        LocalDate startDate = LocalDate.of(2024, 1, 1);
        LocalTime startTime = LocalTime.of(8, 0);
        LocalDate endDate = LocalDate.of(2024, 1, 2);
        LocalTime endTime = LocalTime.of(18, 0);

        IntervaloTemporal intervalo = new IntervaloTemporal(startDate, startTime, endDate, endTime);

        assertEquals(startDate, intervalo.getStartDate());
        assertEquals(startTime, intervalo.getStartTime());
        assertEquals(endDate, intervalo.getEndDate());
        assertEquals(endTime, intervalo.getEndTime());
    }

    @Test
    @DisplayName("CT002 - Verificar construtor para evento no mesmo dia")
    void testConstrutorMesmoDia() {
        LocalDate date = LocalDate.of(2024, 1, 1);
        LocalTime startTime = LocalTime.of(8, 0);
        LocalTime endTime = LocalTime.of(18, 0);

        IntervaloTemporal intervalo = new IntervaloTemporal(date, startTime, endTime);

        assertEquals(date, intervalo.getStartDate());
        assertEquals(startTime, intervalo.getStartTime());
        assertEquals(date, intervalo.getEndDate());
        assertEquals(endTime, intervalo.getEndTime());
    }

    @Test
    @DisplayName("CT003 - Verificar construtor padrão")
    void testConstrutorPadrao() {
        IntervaloTemporal intervalo = new IntervaloTemporal();

        assertNull(intervalo.getStartDate());
        assertNull(intervalo.getStartTime());
        assertNull(intervalo.getEndDate());
        assertNull(intervalo.getEndTime());
    }

}
