package com.ia.core.quartz.model.periodicidade;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes para a classe Recorrencia.
 */
@DisplayName("Testes de Recorrencia")
class RecorrenciaTest {

    @Test
    @DisplayName("CT001 - Verificar construtor padrão")
    void testConstrutorPadrao() {
        Recorrencia recorrencia = new Recorrencia();

        assertNull(recorrencia.getFrequency());
        assertNull(recorrencia.getIntervalValue());
        assertTrue(recorrencia.getByDay().isEmpty());
        assertTrue(recorrencia.getByMonthDay().isEmpty());
        assertTrue(recorrencia.getByMonth().isEmpty());
        assertTrue(recorrencia.getBySetPosition().isEmpty());
        assertNull(recorrencia.getUntilDate());
        assertNull(recorrencia.getCountLimit());
        assertNull(recorrencia.getWeekStartDay());
        assertTrue(recorrencia.getByYearDay().isEmpty());
        assertTrue(recorrencia.getByWeekNo().isEmpty());
        assertTrue(recorrencia.getByHour().isEmpty());
        assertTrue(recorrencia.getByMinute().isEmpty());
        assertTrue(recorrencia.getBySecond().isEmpty());
    }

    @Test
    @DisplayName("CT002 - Verificar construtor com todos os parâmetros")
    void testConstrutorTodosParametros() {
        Set<DayOfWeek> byDay = new HashSet<>();
        byDay.add(DayOfWeek.MONDAY);
        Set<Integer> byMonthDay = new HashSet<>();
        byMonthDay.add(1);
        Set<Month> byMonth = new HashSet<>();
        byMonth.add(Month.JANUARY);
        Set<Integer> bySetPosition = new HashSet<>();
        bySetPosition.add(1);
        Set<Integer> byYearDay = new HashSet<>();
        byYearDay.add(1);
        Set<Integer> byWeekNo = new HashSet<>();
        byWeekNo.add(1);
        Set<Integer> byHour = new HashSet<>();
        byHour.add(8);
        Set<Integer> byMinute = new HashSet<>();
        byMinute.add(0);
        Set<Integer> bySecond = new HashSet<>();
        bySecond.add(0);

        Recorrencia recorrencia = new Recorrencia();
        recorrencia.setFrequency(Frequencia.DIARIAMENTE);
        recorrencia.setIntervalValue(1);
        recorrencia.setByDay(byDay);
        recorrencia.setByMonthDay(byMonthDay);
        recorrencia.setByMonth(byMonth);
        recorrencia.setBySetPosition(bySetPosition);
        recorrencia.setUntilDate(LocalDate.of(2024, 12, 31));
        recorrencia.setCountLimit(100);
        recorrencia.setWeekStartDay(DayOfWeek.MONDAY);
        recorrencia.setByYearDay(byYearDay);
        recorrencia.setByWeekNo(byWeekNo);
        recorrencia.setByHour(byHour);
        recorrencia.setByMinute(byMinute);
        recorrencia.setBySecond(bySecond);

        assertEquals(Frequencia.DIARIAMENTE, recorrencia.getFrequency());
        assertEquals(1, recorrencia.getIntervalValue());
        assertEquals(byDay, recorrencia.getByDay());
        assertEquals(byMonthDay, recorrencia.getByMonthDay());
        assertEquals(byMonth, recorrencia.getByMonth());
        assertEquals(bySetPosition, recorrencia.getBySetPosition());
        assertEquals(LocalDate.of(2024, 12, 31), recorrencia.getUntilDate());
        assertEquals(100, recorrencia.getCountLimit());
        assertEquals(DayOfWeek.MONDAY, recorrencia.getWeekStartDay());
        assertEquals(byYearDay, recorrencia.getByYearDay());
        assertEquals(byWeekNo, recorrencia.getByWeekNo());
        assertEquals(byHour, recorrencia.getByHour());
        assertEquals(byMinute, recorrencia.getByMinute());
        assertEquals(bySecond, recorrencia.getBySecond());
    }

    @Test
    @DisplayName("CT003 - Verificar setters de campos individuais")
    void testSettersCamposIndividuais() {
        Recorrencia recorrencia = new Recorrencia();

        recorrencia.setFrequency(Frequencia.MENSALMENTE);
        recorrencia.setIntervalValue(3);
        recorrencia.setUntilDate(LocalDate.of(2025, 6, 30));
        recorrencia.setCountLimit(50);
        recorrencia.setWeekStartDay(DayOfWeek.SUNDAY);

        assertEquals(Frequencia.MENSALMENTE, recorrencia.getFrequency());
        assertEquals(3, recorrencia.getIntervalValue());
        assertEquals(LocalDate.of(2025, 6, 30), recorrencia.getUntilDate());
        assertEquals(50, recorrencia.getCountLimit());
        assertEquals(DayOfWeek.SUNDAY, recorrencia.getWeekStartDay());
    }

    @Test
    @DisplayName("CT004 - Verificar setters de coleções")
    void testSettersColecoes() {
        Recorrencia recorrencia = new Recorrencia();

        Set<DayOfWeek> byDay = new HashSet<>();
        byDay.add(DayOfWeek.TUESDAY);
        byDay.add(DayOfWeek.THURSDAY);
        recorrencia.setByDay(byDay);

        Set<Integer> byMonthDay = new HashSet<>();
        byMonthDay.add(15);
        recorrencia.setByMonthDay(byMonthDay);

        Set<Month> byMonth = new HashSet<>();
        byMonth.add(Month.FEBRUARY);
        recorrencia.setByMonth(byMonth);

        assertEquals(byDay, recorrencia.getByDay());
        assertEquals(byMonthDay, recorrencia.getByMonthDay());
        assertEquals(byMonth, recorrencia.getByMonth());
    }

    @Test
    @DisplayName("CT005 - Verificar setters de campos de hora")
    void testSettersCamposHora() {
        Recorrencia recorrencia = new Recorrencia();

        Set<Integer> byHour = new HashSet<>();
        byHour.add(9);
        byHour.add(14);
        recorrencia.setByHour(byHour);

        Set<Integer> byMinute = new HashSet<>();
        byMinute.add(30);
        recorrencia.setByMinute(byMinute);

        Set<Integer> bySecond = new HashSet<>();
        bySecond.add(0);
        recorrencia.setBySecond(bySecond);

        assertEquals(byHour, recorrencia.getByHour());
        assertEquals(byMinute, recorrencia.getByMinute());
        assertEquals(bySecond, recorrencia.getBySecond());
    }

    @Test
    @DisplayName("CT006 - Verificar setters de campos avançados")
    void testSettersCamposAvancados() {
        Recorrencia recorrencia = new Recorrencia();

        Set<Integer> byYearDay = new HashSet<>();
        byYearDay.add(100);
        recorrencia.setByYearDay(byYearDay);

        Set<Integer> byWeekNo = new HashSet<>();
        byWeekNo.add(10);
        recorrencia.setByWeekNo(byWeekNo);

        Set<Integer> bySetPosition = new HashSet<>();
        bySetPosition.add(2);
        recorrencia.setBySetPosition(bySetPosition);

        assertEquals(byYearDay, recorrencia.getByYearDay());
        assertEquals(byWeekNo, recorrencia.getByWeekNo());
        assertEquals(bySetPosition, recorrencia.getBySetPosition());
    }

    @Test
    @DisplayName("CT007 - Verificar equals")
    void testEquals() {
        Recorrencia recorrencia1 = new Recorrencia();
        Recorrencia recorrencia2 = new Recorrencia();

        assertEquals(recorrencia1, recorrencia1);
        assertNotEquals(recorrencia1, recorrencia2);
    }

    @Test
    @DisplayName("CT008 - Verificar hashCode")
    void testHashCode() {
        Recorrencia recorrencia = new Recorrencia();

        assertNotNull(recorrencia.hashCode());
    }

    @Test
    @DisplayName("CT009 - Verificar toString")
    void testToString() {
        Recorrencia recorrencia = new Recorrencia();

        assertNotNull(recorrencia.toString());
    }

    @Test
    @DisplayName("CT010 - Verificar construtor no-args")
    void testConstrutorNoArgs() {
        Recorrencia recorrencia = new Recorrencia();

        assertNotNull(recorrencia);
    }

    @Test
    @DisplayName("CT011 - Verificar builder com todos os campos")
    void testBuilderTodosCampos() {
        Set<DayOfWeek> byDay = new HashSet<>();
        byDay.add(DayOfWeek.MONDAY);
        Set<Integer> byMonthDay = new HashSet<>();
        byMonthDay.add(1);
        Set<Month> byMonth = new HashSet<>();
        byMonth.add(Month.JANUARY);
        Set<Integer> bySetPosition = new HashSet<>();
        bySetPosition.add(1);
        Set<Integer> byYearDay = new HashSet<>();
        byYearDay.add(1);
        Set<Integer> byWeekNo = new HashSet<>();
        byWeekNo.add(1);
        Set<Integer> byHour = new HashSet<>();
        byHour.add(8);
        Set<Integer> byMinute = new HashSet<>();
        byMinute.add(0);
        Set<Integer> bySecond = new HashSet<>();
        bySecond.add(0);

        Recorrencia recorrencia = Recorrencia.builder()
            .frequency(Frequencia.DIARIAMENTE)
            .intervalValue(1)
            .byDay(byDay)
            .byMonthDay(byMonthDay)
            .byMonth(byMonth)
            .bySetPosition(bySetPosition)
            .untilDate(LocalDate.of(2024, 12, 31))
            .countLimit(100)
            .weekStartDay(DayOfWeek.MONDAY)
            .byYearDay(byYearDay)
            .byWeekNo(byWeekNo)
            .byHour(byHour)
            .byMinute(byMinute)
            .bySecond(bySecond)
            .build();

        assertEquals(Frequencia.DIARIAMENTE, recorrencia.getFrequency());
        assertEquals(1, recorrencia.getIntervalValue());
        assertEquals(byDay, recorrencia.getByDay());
        assertEquals(byMonthDay, recorrencia.getByMonthDay());
        assertEquals(byMonth, recorrencia.getByMonth());
        assertEquals(bySetPosition, recorrencia.getBySetPosition());
        assertEquals(LocalDate.of(2024, 12, 31), recorrencia.getUntilDate());
        assertEquals(100, recorrencia.getCountLimit());
        assertEquals(DayOfWeek.MONDAY, recorrencia.getWeekStartDay());
        assertEquals(byYearDay, recorrencia.getByYearDay());
        assertEquals(byWeekNo, recorrencia.getByWeekNo());
        assertEquals(byHour, recorrencia.getByHour());
        assertEquals(byMinute, recorrencia.getByMinute());
        assertEquals(bySecond, recorrencia.getBySecond());
    }

    @Test
    @DisplayName("CT012 - Verificar builder com campos null")
    void testBuilderComCamposNull() {
        Recorrencia recorrencia = Recorrencia.builder()
            .frequency(null)
            .intervalValue(null)
            .byDay(null)
            .byMonthDay(null)
            .byMonth(null)
            .bySetPosition(null)
            .untilDate(null)
            .countLimit(null)
            .weekStartDay(null)
            .byYearDay(null)
            .byWeekNo(null)
            .byHour(null)
            .byMinute(null)
            .bySecond(null)
            .build();

        assertNull(recorrencia.getFrequency());
        assertNull(recorrencia.getIntervalValue());
        assertNull(recorrencia.getByDay());
        assertNull(recorrencia.getByMonthDay());
        assertNull(recorrencia.getByMonth());
        assertNull(recorrencia.getBySetPosition());
        assertNull(recorrencia.getUntilDate());
        assertNull(recorrencia.getCountLimit());
        assertNull(recorrencia.getWeekStartDay());
        assertNull(recorrencia.getByYearDay());
        assertNull(recorrencia.getByWeekNo());
        assertNull(recorrencia.getByHour());
        assertNull(recorrencia.getByMinute());
        assertNull(recorrencia.getBySecond());
    }

    @Test
    @DisplayName("CT013 - Verificar builder com coleções vazias")
    void testBuilderComColecoesVazias() {
        Recorrencia recorrencia = Recorrencia.builder()
            .byDay(new HashSet<>())
            .byMonthDay(new HashSet<>())
            .byMonth(new HashSet<>())
            .bySetPosition(new HashSet<>())
            .byYearDay(new HashSet<>())
            .byWeekNo(new HashSet<>())
            .byHour(new HashSet<>())
            .byMinute(new HashSet<>())
            .bySecond(new HashSet<>())
            .build();

        assertTrue(recorrencia.getByDay().isEmpty());
        assertTrue(recorrencia.getByMonthDay().isEmpty());
        assertTrue(recorrencia.getByMonth().isEmpty());
        assertTrue(recorrencia.getBySetPosition().isEmpty());
        assertTrue(recorrencia.getByYearDay().isEmpty());
        assertTrue(recorrencia.getByWeekNo().isEmpty());
        assertTrue(recorrencia.getByHour().isEmpty());
        assertTrue(recorrencia.getByMinute().isEmpty());
        assertTrue(recorrencia.getBySecond().isEmpty());
    }

    @Test
    @DisplayName("CT014 - Verificar setter com valor zero")
    void testSetterComValorZero() {
        Recorrencia recorrencia = new Recorrencia();
        recorrencia.setIntervalValue(0);
        recorrencia.setCountLimit(0);

        assertEquals(0, recorrencia.getIntervalValue());
        assertEquals(0, recorrencia.getCountLimit());
    }

    @Test
    @DisplayName("CT015 - Verificar setter com valor negativo")
    void testSetterComValorNegativo() {
        Recorrencia recorrencia = new Recorrencia();
        recorrencia.setIntervalValue(-1);
        recorrencia.setCountLimit(-1);

        assertEquals(-1, recorrencia.getIntervalValue());
        assertEquals(-1, recorrencia.getCountLimit());
    }
}
