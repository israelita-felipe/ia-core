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
 * Testes para a classe ExclusaoRecorrencia.
 */
@DisplayName("Testes de ExclusaoRecorrencia")
class ExclusaoRecorrenciaTest {

    @Test
    @DisplayName("CT001 - Verificar construtor padrão")
    void testConstrutorPadrao() {
        ExclusaoRecorrencia exclusao = new ExclusaoRecorrencia();

        assertNull(exclusao.getFrequency());
        assertNull(exclusao.getIntervalValue());
        assertTrue(exclusao.getByDay().isEmpty());
        assertTrue(exclusao.getByMonthDay().isEmpty());
        assertTrue(exclusao.getByMonth().isEmpty());
        assertNull(exclusao.getBySetPosition());
        assertNull(exclusao.getUntilDate());
        assertNull(exclusao.getCountLimit());
        assertNull(exclusao.getWeekStartDay());
        assertTrue(exclusao.getByYearDay().isEmpty());
        assertTrue(exclusao.getByWeekNo().isEmpty());
        assertTrue(exclusao.getByHour().isEmpty());
        assertTrue(exclusao.getByMinute().isEmpty());
        assertTrue(exclusao.getBySecond().isEmpty());
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

        ExclusaoRecorrencia exclusao = new ExclusaoRecorrencia();
        exclusao.setFrequency(Frequencia.DIARIAMENTE);
        exclusao.setIntervalValue(1);
        exclusao.setByDay(byDay);
        exclusao.setByMonthDay(byMonthDay);
        exclusao.setByMonth(byMonth);
        exclusao.setBySetPosition(1);
        exclusao.setUntilDate(LocalDate.of(2024, 12, 31));
        exclusao.setCountLimit(100);
        exclusao.setWeekStartDay(DayOfWeek.MONDAY);
        exclusao.setByYearDay(byYearDay);
        exclusao.setByWeekNo(byWeekNo);
        exclusao.setByHour(byHour);
        exclusao.setByMinute(byMinute);
        exclusao.setBySecond(bySecond);

        assertEquals(Frequencia.DIARIAMENTE, exclusao.getFrequency());
        assertEquals(1, exclusao.getIntervalValue());
        assertEquals(byDay, exclusao.getByDay());
        assertEquals(byMonthDay, exclusao.getByMonthDay());
        assertEquals(byMonth, exclusao.getByMonth());
        assertEquals(1, exclusao.getBySetPosition());
        assertEquals(LocalDate.of(2024, 12, 31), exclusao.getUntilDate());
        assertEquals(100, exclusao.getCountLimit());
        assertEquals(DayOfWeek.MONDAY, exclusao.getWeekStartDay());
        assertEquals(byYearDay, exclusao.getByYearDay());
        assertEquals(byWeekNo, exclusao.getByWeekNo());
        assertEquals(byHour, exclusao.getByHour());
        assertEquals(byMinute, exclusao.getByMinute());
        assertEquals(bySecond, exclusao.getBySecond());
    }

    @Test
    @DisplayName("CT003 - Verificar setters de campos individuais")
    void testSettersCamposIndividuais() {
        ExclusaoRecorrencia exclusao = new ExclusaoRecorrencia();

        exclusao.setFrequency(Frequencia.ANUALMENTE);
        exclusao.setIntervalValue(4);
        exclusao.setUntilDate(LocalDate.of(2025, 12, 31));
        exclusao.setCountLimit(200);
        exclusao.setWeekStartDay(DayOfWeek.SATURDAY);

        assertEquals(Frequencia.ANUALMENTE, exclusao.getFrequency());
        assertEquals(4, exclusao.getIntervalValue());
        assertEquals(LocalDate.of(2025, 12, 31), exclusao.getUntilDate());
        assertEquals(200, exclusao.getCountLimit());
        assertEquals(DayOfWeek.SATURDAY, exclusao.getWeekStartDay());
    }

    @Test
    @DisplayName("CT004 - Verificar setters de coleções")
    void testSettersColecoes() {
        ExclusaoRecorrencia exclusao = new ExclusaoRecorrencia();

        Set<DayOfWeek> byDay = new HashSet<>();
        byDay.add(DayOfWeek.WEDNESDAY);
        byDay.add(DayOfWeek.FRIDAY);
        exclusao.setByDay(byDay);

        Set<Integer> byMonthDay = new HashSet<>();
        byMonthDay.add(20);
        exclusao.setByMonthDay(byMonthDay);

        Set<Month> byMonth = new HashSet<>();
        byMonth.add(Month.MARCH);
        exclusao.setByMonth(byMonth);

        assertEquals(byDay, exclusao.getByDay());
        assertEquals(byMonthDay, exclusao.getByMonthDay());
        assertEquals(byMonth, exclusao.getByMonth());
    }

    @Test
    @DisplayName("CT005 - Verificar setters de campos de hora")
    void testSettersCamposHora() {
        ExclusaoRecorrencia exclusao = new ExclusaoRecorrencia();

        Set<Integer> byHour = new HashSet<>();
        byHour.add(10);
        byHour.add(16);
        exclusao.setByHour(byHour);

        Set<Integer> byMinute = new HashSet<>();
        byMinute.add(45);
        exclusao.setByMinute(byMinute);

        Set<Integer> bySecond = new HashSet<>();
        bySecond.add(30);
        exclusao.setBySecond(bySecond);

        assertEquals(byHour, exclusao.getByHour());
        assertEquals(byMinute, exclusao.getByMinute());
        assertEquals(bySecond, exclusao.getBySecond());
    }

    @Test
    @DisplayName("CT006 - Verificar setters de campos avançados")
    void testSettersCamposAvancados() {
        ExclusaoRecorrencia exclusao = new ExclusaoRecorrencia();

        Set<Integer> byYearDay = new HashSet<>();
        byYearDay.add(200);
        exclusao.setByYearDay(byYearDay);

        Set<Integer> byWeekNo = new HashSet<>();
        byWeekNo.add(20);
        exclusao.setByWeekNo(byWeekNo);

        exclusao.setBySetPosition(3);

        assertEquals(byYearDay, exclusao.getByYearDay());
        assertEquals(byWeekNo, exclusao.getByWeekNo());
        assertEquals(3, exclusao.getBySetPosition());
    }

    @Test
    @DisplayName("CT007 - Verificar equals")
    void testEquals() {
        ExclusaoRecorrencia exclusao1 = new ExclusaoRecorrencia();
        ExclusaoRecorrencia exclusao2 = new ExclusaoRecorrencia();

        assertEquals(exclusao1, exclusao1);
        assertNotEquals(exclusao1, exclusao2);
    }

    @Test
    @DisplayName("CT008 - Verificar hashCode")
    void testHashCode() {
        ExclusaoRecorrencia exclusao = new ExclusaoRecorrencia();

        assertNotNull(exclusao.hashCode());
    }

    @Test
    @DisplayName("CT009 - Verificar toString")
    void testToString() {
        ExclusaoRecorrencia exclusao = new ExclusaoRecorrencia();

        assertNotNull(exclusao.toString());
    }

    @Test
    @DisplayName("CT010 - Verificar construtor no-args")
    void testConstrutorNoArgs() {
        ExclusaoRecorrencia exclusao = new ExclusaoRecorrencia();

        assertNotNull(exclusao);
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

        ExclusaoRecorrencia exclusao = ExclusaoRecorrencia.builder()
            .frequency(Frequencia.DIARIAMENTE)
            .intervalValue(1)
            .byDay(byDay)
            .byMonthDay(byMonthDay)
            .byMonth(byMonth)
            .bySetPosition(1)
            .untilDate(LocalDate.of(2024, 12, 31))
            .countLimit(100)
            .weekStartDay(DayOfWeek.MONDAY)
            .byYearDay(byYearDay)
            .byWeekNo(byWeekNo)
            .byHour(byHour)
            .byMinute(byMinute)
            .bySecond(bySecond)
            .build();

        assertEquals(Frequencia.DIARIAMENTE, exclusao.getFrequency());
        assertEquals(1, exclusao.getIntervalValue());
        assertEquals(byDay, exclusao.getByDay());
        assertEquals(byMonthDay, exclusao.getByMonthDay());
        assertEquals(byMonth, exclusao.getByMonth());
        assertEquals(1, exclusao.getBySetPosition());
        assertEquals(LocalDate.of(2024, 12, 31), exclusao.getUntilDate());
        assertEquals(100, exclusao.getCountLimit());
        assertEquals(DayOfWeek.MONDAY, exclusao.getWeekStartDay());
        assertEquals(byYearDay, exclusao.getByYearDay());
        assertEquals(byWeekNo, exclusao.getByWeekNo());
        assertEquals(byHour, exclusao.getByHour());
        assertEquals(byMinute, exclusao.getByMinute());
        assertEquals(bySecond, exclusao.getBySecond());
    }

    @Test
    @DisplayName("CT012 - Verificar builder com campos null")
    void testBuilderComCamposNull() {
        ExclusaoRecorrencia exclusao = ExclusaoRecorrencia.builder()
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

        assertNull(exclusao.getFrequency());
        assertNull(exclusao.getIntervalValue());
        assertNull(exclusao.getByDay());
        assertNull(exclusao.getByMonthDay());
        assertNull(exclusao.getByMonth());
        assertNull(exclusao.getBySetPosition());
        assertNull(exclusao.getUntilDate());
        assertNull(exclusao.getCountLimit());
        assertNull(exclusao.getWeekStartDay());
        assertNull(exclusao.getByYearDay());
        assertNull(exclusao.getByWeekNo());
        assertNull(exclusao.getByHour());
        assertNull(exclusao.getByMinute());
        assertNull(exclusao.getBySecond());
    }

    @Test
    @DisplayName("CT013 - Verificar builder com coleções vazias")
    void testBuilderComColecoesVazias() {
        ExclusaoRecorrencia exclusao = ExclusaoRecorrencia.builder()
            .byDay(new HashSet<>())
            .byMonthDay(new HashSet<>())
            .byMonth(new HashSet<>())
            .byYearDay(new HashSet<>())
            .byWeekNo(new HashSet<>())
            .byHour(new HashSet<>())
            .byMinute(new HashSet<>())
            .bySecond(new HashSet<>())
            .build();

        assertTrue(exclusao.getByDay().isEmpty());
        assertTrue(exclusao.getByMonthDay().isEmpty());
        assertTrue(exclusao.getByMonth().isEmpty());
        assertTrue(exclusao.getByYearDay().isEmpty());
        assertTrue(exclusao.getByWeekNo().isEmpty());
        assertTrue(exclusao.getByHour().isEmpty());
        assertTrue(exclusao.getByMinute().isEmpty());
        assertTrue(exclusao.getBySecond().isEmpty());
    }

    @Test
    @DisplayName("CT014 - Verificar setter com valor zero")
    void testSetterComValorZero() {
        ExclusaoRecorrencia exclusao = new ExclusaoRecorrencia();
        exclusao.setIntervalValue(0);
        exclusao.setCountLimit(0);
        exclusao.setBySetPosition(0);

        assertEquals(0, exclusao.getIntervalValue());
        assertEquals(0, exclusao.getCountLimit());
        assertEquals(0, exclusao.getBySetPosition());
    }

    @Test
    @DisplayName("CT015 - Verificar setter com valor negativo")
    void testSetterComValorNegativo() {
        ExclusaoRecorrencia exclusao = new ExclusaoRecorrencia();
        exclusao.setIntervalValue(-1);
        exclusao.setCountLimit(-1);
        exclusao.setBySetPosition(-1);

        assertEquals(-1, exclusao.getIntervalValue());
        assertEquals(-1, exclusao.getCountLimit());
        assertEquals(-1, exclusao.getBySetPosition());
    }
}
