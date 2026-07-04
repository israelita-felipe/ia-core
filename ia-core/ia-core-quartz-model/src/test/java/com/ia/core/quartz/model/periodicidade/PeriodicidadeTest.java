package com.ia.core.quartz.model.periodicidade;

import com.ia.core.model.BaseEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes para a classe Periodicidade.
 */
@DisplayName("Testes de Periodicidade")
class PeriodicidadeTest {

    @Test
    @DisplayName("CT001 - Verificar construtor padrão")
    void testConstrutorPadrao() {
        Periodicidade periodicidade = new Periodicidade();

        assertNotNull(periodicidade.getIntervaloBase());
        assertNotNull(periodicidade.getRegra());
        assertNotNull(periodicidade.getExclusaoRecorrencia());
        assertNotNull(periodicidade.getZoneId());
        assertNotNull(periodicidade.getExceptionDates());
        assertNotNull(periodicidade.getIncludeDates());
        assertTrue(periodicidade.getAtivo());
    }

    @Test
    @DisplayName("CT002 - Verificar construtor completo com builder")
    void testConstrutorCompletoBuilder() {
        IntervaloTemporal intervalo = new IntervaloTemporal(
            LocalDate.of(2024, 1, 1), LocalTime.of(10, 0),
            LocalDate.of(2024, 1, 1), LocalTime.of(11, 0)
        );
        Recorrencia regra = new Recorrencia();
        ExclusaoRecorrencia exclusao = new ExclusaoRecorrencia();
        Set<LocalDate> exceptionDates = new HashSet<>();
        exceptionDates.add(LocalDate.of(2024, 12, 25));
        Set<LocalDate> includeDates = new HashSet<>();
        includeDates.add(LocalDate.of(2024, 1, 1));

        Periodicidade periodicidade = Periodicidade.builder()
            .intervaloBase(intervalo)
            .regra(regra)
            .exclusaoRecorrencia(exclusao)
            .zoneId("America/Sao_Paulo")
            .exceptionDates(exceptionDates)
            .includeDates(includeDates)
            .ativo(false)
            .build();

        assertEquals(intervalo, periodicidade.getIntervaloBase());
        assertEquals(regra, periodicidade.getRegra());
        assertEquals(exclusao, periodicidade.getExclusaoRecorrencia());
        assertEquals("America/Sao_Paulo", periodicidade.getZoneId());
        assertEquals(exceptionDates, periodicidade.getExceptionDates());
        assertEquals(includeDates, periodicidade.getIncludeDates());
        assertFalse(periodicidade.getAtivo());
    }

    @Test
    @DisplayName("CT003 - Verificar constantes TABLE_NAME e SCHEMA_NAME")
    void testConstantes() {
        assertEquals("QRTZ_PERIODICIDADE", Periodicidade.TABLE_NAME);
        assertEquals("QUARTZ", Periodicidade.SCHEMA_NAME);
    }

    @Test
    @DisplayName("CT004 - Verificar herança de BaseEntity")
    void testHerancaBaseEntity() {
        Periodicidade periodicidade = new Periodicidade();
        assertTrue(periodicidade instanceof BaseEntity);
    }

    @Test
    @DisplayName("CT005 - Verificar valor padrão de zoneId")
    void testZoneIdPadrao() {
        Periodicidade periodicidade = new Periodicidade();
        assertEquals(ZoneId.systemDefault().getId(), periodicidade.getZoneId());
    }

    @Test
    @DisplayName("CT006 - Verificar builder com valores parciais")
    void testBuilderValoresParciais() {
        Periodicidade periodicidade = Periodicidade.builder()
            .intervaloBase(new IntervaloTemporal(
                LocalDate.of(2024, 1, 1), LocalTime.of(10, 0),
                LocalDate.of(2024, 1, 1), LocalTime.of(11, 0)
            ))
            .build();

        assertNotNull(periodicidade.getIntervaloBase());
        assertNotNull(periodicidade.getRegra());
        assertNotNull(periodicidade.getExclusaoRecorrencia());
        assertNotNull(periodicidade.getZoneId());
        assertNotNull(periodicidade.getExceptionDates());
        assertNotNull(periodicidade.getIncludeDates());
        assertTrue(periodicidade.getAtivo());
    }

    @Test
    @DisplayName("CT007 - Verificar builder com exclusaoRecorrencia configurada")
    void testBuilderComExclusaoRecorrencia() {
        ExclusaoRecorrencia exclusao = new ExclusaoRecorrencia();

        Periodicidade periodicidade = Periodicidade.builder()
            .exclusaoRecorrencia(exclusao)
            .build();

        assertEquals(exclusao, periodicidade.getExclusaoRecorrencia());
    }

    @Test
    @DisplayName("CT008 - Verificar builder com exceptionDates")
    void testBuilderComExceptionDates() {
        Set<LocalDate> exceptionDates = new HashSet<>();
        exceptionDates.add(LocalDate.of(2024, 12, 25));
        exceptionDates.add(LocalDate.of(2024, 1, 1));

        Periodicidade periodicidade = Periodicidade.builder()
            .exceptionDates(exceptionDates)
            .build();

        assertEquals(2, periodicidade.getExceptionDates().size());
        assertTrue(periodicidade.getExceptionDates().contains(LocalDate.of(2024, 12, 25)));
    }

    @Test
    @DisplayName("CT009 - Verificar builder com includeDates")
    void testBuilderComIncludeDates() {
        Set<LocalDate> includeDates = new HashSet<>();
        includeDates.add(LocalDate.of(2024, 6, 15));
        includeDates.add(LocalDate.of(2024, 7, 20));

        Periodicidade periodicidade = Periodicidade.builder()
            .includeDates(includeDates)
            .build();

        assertEquals(2, periodicidade.getIncludeDates().size());
        assertTrue(periodicidade.getIncludeDates().contains(LocalDate.of(2024, 6, 15)));
    }

    @Test
    @DisplayName("CT010 - Verificar builder com ativo false")
    void testBuilderComAtivoFalse() {
        Periodicidade periodicidade = Periodicidade.builder()
            .ativo(false)
            .build();

        assertFalse(periodicidade.getAtivo());
    }

    @Test
    @DisplayName("CT011 - Verificar builder com zoneId customizado")
    void testBuilderComZoneIdCustomizado() {
        Periodicidade periodicidade = Periodicidade.builder()
            .zoneId("UTC")
            .build();

        assertEquals("UTC", periodicidade.getZoneId());
    }

    @Test
    @DisplayName("CT012 - Verificar modificação de exceptionDates após criação")
    void testModificacaoExceptionDates() {
        Periodicidade periodicidade = new Periodicidade();
        Set<LocalDate> exceptionDates = new HashSet<>();
        exceptionDates.add(LocalDate.of(2024, 12, 25));
        periodicidade.setExceptionDates(exceptionDates);

        assertEquals(1, periodicidade.getExceptionDates().size());
    }

    @Test
    @DisplayName("CT013 - Verificar modificação de includeDates após criação")
    void testModificacaoIncludeDates() {
        Periodicidade periodicidade = new Periodicidade();
        Set<LocalDate> includeDates = new HashSet<>();
        includeDates.add(LocalDate.of(2024, 6, 15));
        periodicidade.setIncludeDates(includeDates);

        assertEquals(1, periodicidade.getIncludeDates().size());
    }

    @Test
    @DisplayName("CT014 - Verificar builder com todos os campos")
    void testBuilderTodosCampos() {
        IntervaloTemporal intervalo = new IntervaloTemporal(
            LocalDate.of(2024, 1, 1), LocalTime.of(10, 0),
            LocalDate.of(2024, 1, 1), LocalTime.of(11, 0)
        );
        Recorrencia regra = new Recorrencia();
        ExclusaoRecorrencia exclusao = new ExclusaoRecorrencia();
        Set<LocalDate> exceptionDates = new HashSet<>();
        exceptionDates.add(LocalDate.of(2024, 12, 25));
        Set<LocalDate> includeDates = new HashSet<>();
        includeDates.add(LocalDate.of(2024, 1, 1));

        Periodicidade periodicidade = Periodicidade.builder()
            .intervaloBase(intervalo)
            .regra(regra)
            .exclusaoRecorrencia(exclusao)
            .zoneId("America/Sao_Paulo")
            .exceptionDates(exceptionDates)
            .includeDates(includeDates)
            .ativo(true)
            .build();

        assertEquals(intervalo, periodicidade.getIntervaloBase());
        assertEquals(regra, periodicidade.getRegra());
        assertEquals(exclusao, periodicidade.getExclusaoRecorrencia());
        assertEquals("America/Sao_Paulo", periodicidade.getZoneId());
        assertEquals(exceptionDates, periodicidade.getExceptionDates());
        assertEquals(includeDates, periodicidade.getIncludeDates());
        assertTrue(periodicidade.getAtivo());
    }

    @Test
    @DisplayName("CT015 - Verificar builder com regra e exclusao")
    void testBuilderComRegraEExclusao() {
        Recorrencia regra = new Recorrencia();
        regra.setFrequency(Frequencia.DIARIAMENTE);
        regra.setIntervalValue(1);

        ExclusaoRecorrencia exclusao = new ExclusaoRecorrencia();
        exclusao.setFrequency(Frequencia.SEMANALMENTE);
        exclusao.setIntervalValue(2);

        Periodicidade periodicidade = Periodicidade.builder()
            .regra(regra)
            .exclusaoRecorrencia(exclusao)
            .build();

        assertEquals(regra, periodicidade.getRegra());
        assertEquals(exclusao, periodicidade.getExclusaoRecorrencia());
    }

    @Test
    @DisplayName("CT016 - Verificar modificação de regra após criação")
    void testModificacaoRegra() {
        Periodicidade periodicidade = new Periodicidade();
        Recorrencia regra = new Recorrencia();
        regra.setFrequency(Frequencia.MENSALMENTE);
        periodicidade.setRegra(regra);

        assertEquals(regra, periodicidade.getRegra());
        assertEquals(Frequencia.MENSALMENTE, periodicidade.getRegra().getFrequency());
    }

    @Test
    @DisplayName("CT017 - Verificar modificação de zoneId após criação")
    void testModificacaoZoneId() {
        Periodicidade periodicidade = new Periodicidade();
        periodicidade.setZoneId("America/Sao_Paulo");

        assertEquals("America/Sao_Paulo", periodicidade.getZoneId());
    }

    @Test
    @DisplayName("CT018 - Verificar modificação de ativo após criação")
    void testModificacaoAtivo() {
        Periodicidade periodicidade = new Periodicidade();
        periodicidade.setAtivo(false);

        assertFalse(periodicidade.getAtivo());
    }

    @Test
    @DisplayName("CT019 - Verificar builder com intervaloBase null")
    void testBuilderComIntervaloBaseNull() {
        Periodicidade periodicidade = Periodicidade.builder()
            .intervaloBase(null)
            .build();

        assertNull(periodicidade.getIntervaloBase());
    }

    @Test
    @DisplayName("CT020 - Verificar construtor com todos os parâmetros")
    void testConstrutorTodosParametros() {
        IntervaloTemporal intervalo = new IntervaloTemporal(
            LocalDate.of(2024, 1, 1), LocalTime.of(10, 0),
            LocalDate.of(2024, 1, 1), LocalTime.of(11, 0)
        );
        Recorrencia regra = new Recorrencia();
        ExclusaoRecorrencia exclusao = new ExclusaoRecorrencia();
        Set<LocalDate> exceptionDates = new HashSet<>();
        exceptionDates.add(LocalDate.of(2024, 12, 25));
        Set<LocalDate> includeDates = new HashSet<>();
        includeDates.add(LocalDate.of(2024, 1, 1));

        Periodicidade periodicidade = new Periodicidade(
            intervalo, regra, exclusao, "America/Sao_Paulo",
            exceptionDates, includeDates, true
        );

        assertEquals(intervalo, periodicidade.getIntervaloBase());
        assertEquals(regra, periodicidade.getRegra());
        assertEquals(exclusao, periodicidade.getExclusaoRecorrencia());
        assertEquals("America/Sao_Paulo", periodicidade.getZoneId());
        assertEquals(exceptionDates, periodicidade.getExceptionDates());
        assertEquals(includeDates, periodicidade.getIncludeDates());
        assertTrue(periodicidade.getAtivo());
    }

    @Test
    @DisplayName("CT021 - Verificar modificação de intervaloBase após criação")
    void testModificacaoIntervaloBase() {
        Periodicidade periodicidade = new Periodicidade();
        IntervaloTemporal intervalo = new IntervaloTemporal(
            LocalDate.of(2024, 1, 1), LocalTime.of(10, 0),
            LocalDate.of(2024, 1, 1), LocalTime.of(11, 0)
        );
        periodicidade.setIntervaloBase(intervalo);

        assertEquals(intervalo, periodicidade.getIntervaloBase());
    }

    @Test
    @DisplayName("CT022 - Verificar modificação de exclusaoRecorrencia após criação")
    void testModificacaoExclusaoRecorrencia() {
        Periodicidade periodicidade = new Periodicidade();
        ExclusaoRecorrencia exclusao = new ExclusaoRecorrencia();
        exclusao.setFrequency(Frequencia.ANUALMENTE);
        periodicidade.setExclusaoRecorrencia(exclusao);

        assertEquals(exclusao, periodicidade.getExclusaoRecorrencia());
        assertEquals(Frequencia.ANUALMENTE, periodicidade.getExclusaoRecorrencia().getFrequency());
    }

    @Test
    @DisplayName("CT023 - Verificar builder com regra null")
    void testBuilderComRegraNull() {
        Periodicidade periodicidade = Periodicidade.builder()
            .regra(null)
            .build();

        assertNull(periodicidade.getRegra());
    }

    @Test
    @DisplayName("CT024 - Verificar builder com exclusaoRecorrencia null")
    void testBuilderComExclusaoRecorrenciaNull() {
        Periodicidade periodicidade = Periodicidade.builder()
            .exclusaoRecorrencia(null)
            .build();

        assertNull(periodicidade.getExclusaoRecorrencia());
    }

    @Test
    @DisplayName("CT025 - Verificar builder com exceptionDates null")
    void testBuilderComExceptionDatesNull() {
        Periodicidade periodicidade = Periodicidade.builder()
            .exceptionDates(null)
            .build();

        assertNull(periodicidade.getExceptionDates());
    }

    @Test
    @DisplayName("CT026 - Verificar builder com includeDates null")
    void testBuilderComIncludeDatesNull() {
        Periodicidade periodicidade = Periodicidade.builder()
            .includeDates(null)
            .build();

        assertNull(periodicidade.getIncludeDates());
    }

    @Test
    @DisplayName("CT027 - Verificar equals com BaseEntity")
    void testEqualsComBaseEntity() {
        Periodicidade periodicidade1 = Periodicidade.builder()
            .zoneId("UTC")
            .build();
        Periodicidade periodicidade2 = Periodicidade.builder()
            .zoneId("UTC")
            .build();

        assertEquals(periodicidade1, periodicidade1);
        assertNotEquals(periodicidade1, periodicidade2);
    }

    @Test
    @DisplayName("CT028 - Verificar hashCode com BaseEntity")
    void testHashCodeComBaseEntity() {
        Periodicidade periodicidade = Periodicidade.builder()
            .zoneId("UTC")
            .build();

        assertNotNull(periodicidade.hashCode());
    }

    @Test
    @DisplayName("CT029 - Verificar canEqual com BaseEntity")
    void testCanEqualComBaseEntity() {
        Periodicidade periodicidade1 = Periodicidade.builder()
            .zoneId("UTC")
            .build();
        Periodicidade periodicidade2 = Periodicidade.builder()
            .zoneId("UTC")
            .build();

        assertTrue(periodicidade1.canEqual(periodicidade2));
        assertFalse(periodicidade1.canEqual("not a Periodicidade"));
    }

    @Test
    @DisplayName("CT030 - Verificar toString")
    void testToString() {
        Periodicidade periodicidade = Periodicidade.builder()
            .zoneId("UTC")
            .build();

        assertNotNull(periodicidade.toString());
    }

    @Test
    @DisplayName("CT031 - Verificar builder com BaseEntity fields")
    void testBuilderComBaseEntityFields() {
        Periodicidade periodicidade = Periodicidade.builder()
            .zoneId("UTC")
            .build();

        assertNotNull(periodicidade);
        assertNotNull(periodicidade.getIntervaloBase());
        assertNotNull(periodicidade.getRegra());
        assertNotNull(periodicidade.getExclusaoRecorrencia());
    }

    @Test
    @DisplayName("CT032 - Verificar construtor no-args")
    void testConstrutorNoArgs() {
        Periodicidade periodicidade = new Periodicidade();

        assertNotNull(periodicidade);
        assertNotNull(periodicidade.getIntervaloBase());
        assertNotNull(periodicidade.getRegra());
        assertNotNull(periodicidade.getExclusaoRecorrencia());
    }

    @Test
    @DisplayName("CT033 - Verificar construtor all-args com null")
    void testConstrutorAllArgsComNull() {
        Periodicidade periodicidade = new Periodicidade(
            null, null, null, null, null, null, null
        );

        assertNull(periodicidade.getIntervaloBase());
        assertNull(periodicidade.getRegra());
        assertNull(periodicidade.getExclusaoRecorrencia());
        assertNull(periodicidade.getZoneId());
        assertNull(periodicidade.getExceptionDates());
        assertNull(periodicidade.getIncludeDates());
        assertNull(periodicidade.getAtivo());
    }

    @Test
    @DisplayName("CT034 - Verificar builder com ativo true")
    void testBuilderComAtivoTrue() {
        Periodicidade periodicidade = Periodicidade.builder()
            .ativo(Boolean.TRUE)
            .build();

        assertEquals(Boolean.TRUE, periodicidade.getAtivo());
    }

    @Test
    @DisplayName("CT035 - Verificar setter com ativo null")
    void testSetterComAtivoNull() {
        Periodicidade periodicidade = new Periodicidade();
        periodicidade.setAtivo(null);

        assertNull(periodicidade.getAtivo());
    }

    @Test
    @DisplayName("CT036 - Verificar equals com mesma instância")
    void testEqualsComMesmaInstancia() {
        Periodicidade periodicidade = Periodicidade.builder()
            .zoneId("UTC")
            .build();

        assertEquals(periodicidade, periodicidade);
    }

    @Test
    @DisplayName("CT037 - Verificar equals com null")
    void testEqualsComNull() {
        Periodicidade periodicidade = Periodicidade.builder()
            .zoneId("UTC")
            .build();

        assertNotEquals(periodicidade, null);
    }

    @Test
    @DisplayName("CT038 - Verificar equals com classe diferente")
    void testEqualsComClasseDiferente() {
        Periodicidade periodicidade = Periodicidade.builder()
            .zoneId("UTC")
            .build();

        assertNotEquals(periodicidade, "not a Periodicidade");
    }

    @Test
    @DisplayName("CT039 - Verificar hashCode consistente")
    void testHashCodeConsistente() {
        Periodicidade periodicidade1 = Periodicidade.builder()
            .zoneId("UTC")
            .build();
        Periodicidade periodicidade2 = Periodicidade.builder()
            .zoneId("UTC")
            .build();

        assertEquals(periodicidade1.hashCode(), periodicidade1.hashCode());
    }

    @Test
    @DisplayName("CT040 - Verificar hashCode com campos diferentes")
    void testHashCodeComCamposDiferentes() {
        Periodicidade periodicidade1 = Periodicidade.builder()
            .zoneId("UTC")
            .build();
        Periodicidade periodicidade2 = Periodicidade.builder()
            .zoneId("America/Sao_Paulo")
            .build();

        assertNotEquals(periodicidade1.hashCode(), periodicidade2.hashCode());
    }

    @Test
    @DisplayName("CT041 - Verificar canEqual com null")
    void testCanEqualComNull() {
        Periodicidade periodicidade = Periodicidade.builder()
            .zoneId("UTC")
            .build();

        assertFalse(periodicidade.canEqual(null));
    }

    @Test
    @DisplayName("CT042 - Verificar canEqual com mesma classe")
    void testCanEqualComMesmaClasse() {
        Periodicidade periodicidade1 = Periodicidade.builder()
            .zoneId("UTC")
            .build();
        Periodicidade periodicidade2 = Periodicidade.builder()
            .zoneId("UTC")
            .build();

        assertTrue(periodicidade1.canEqual(periodicidade2));
    }

    @Test
    @DisplayName("CT043 - Verificar builder com múltiplas chamadas de mesmo campo")
    void testBuilderComMultiplasChamadasMesmoCampo() {
        Periodicidade periodicidade = Periodicidade.builder()
            .zoneId("UTC")
            .zoneId("America/Sao_Paulo")
            .build();

        assertEquals("America/Sao_Paulo", periodicidade.getZoneId());
    }

    @Test
    @DisplayName("CT044 - Verificar setter com coleções vazias")
    void testSetterComColecoesVazias() {
        Periodicidade periodicidade = new Periodicidade();
        periodicidade.setExceptionDates(new HashSet<>());
        periodicidade.setIncludeDates(new HashSet<>());

        assertTrue(periodicidade.getExceptionDates().isEmpty());
        assertTrue(periodicidade.getIncludeDates().isEmpty());
    }

    @Test
    @DisplayName("CT046 - Verificar setter com zoneId vazio")
    void testSetterComZoneIdVazio() {
        Periodicidade periodicidade = new Periodicidade();
        periodicidade.setZoneId("");

        assertEquals("", periodicidade.getZoneId());
    }

    @Test
    @DisplayName("CT047 - Verificar builder com todos os campos nulos")
    void testBuilderComTodosCamposNulos() {
        Periodicidade periodicidade = Periodicidade.builder()
            .intervaloBase(null)
            .regra(null)
            .exclusaoRecorrencia(null)
            .zoneId(null)
            .exceptionDates(null)
            .includeDates(null)
            .ativo(null)
            .build();

        assertNull(periodicidade.getIntervaloBase());
        assertNull(periodicidade.getRegra());
        assertNull(periodicidade.getExclusaoRecorrencia());
        assertNull(periodicidade.getZoneId());
        assertNull(periodicidade.getExceptionDates());
        assertNull(periodicidade.getIncludeDates());
        assertNull(periodicidade.getAtivo());
    }
}
