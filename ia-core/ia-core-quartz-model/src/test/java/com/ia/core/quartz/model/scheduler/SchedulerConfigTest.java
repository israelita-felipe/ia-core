package com.ia.core.quartz.model.scheduler;

import com.ia.core.model.BaseEntity;
import com.ia.core.quartz.model.periodicidade.Periodicidade;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes para a classe SchedulerConfig.
 */
@DisplayName("Testes de SchedulerConfig")
class SchedulerConfigTest {

    @Test
    @DisplayName("CT001 - Verificar construtor padrão")
    void testConstrutorPadrao() {
        SchedulerConfig config = new SchedulerConfig();

        assertNotNull(config.getPeriodicidade());
    }

    @Test
    @DisplayName("CT002 - Verificar construtor completo com builder")
    void testConstrutorCompletoBuilder() {
        Periodicidade periodicidade = new Periodicidade();

        SchedulerConfig config = SchedulerConfig.builder()
            .jobClassName("com.example.MyJob")
            .periodicidade(periodicidade)
            .build();

        assertEquals("com.example.MyJob", config.getJobClassName());
        assertEquals(periodicidade, config.getPeriodicidade());
    }

    @Test
    @DisplayName("CT003 - Verificar constantes TABLE_NAME e SCHEMA_NAME")
    void testConstantes() {
        assertEquals("QRTZ_SCHEDULER_CONFIG", SchedulerConfig.TABLE_NAME);
        assertEquals("QUARTZ", SchedulerConfig.SCHEMA_NAME);
    }

    @Test
    @DisplayName("CT004 - Verificar herança de BaseEntity")
    void testHerancaBaseEntity() {
        SchedulerConfig config = new SchedulerConfig();
        assertTrue(config instanceof BaseEntity);
    }

    @Test
    @DisplayName("CT005 - Verificar builder com periodicidade configurada")
    void testBuilderComPeriodicidade() {
        Periodicidade periodicidade = Periodicidade.builder()
            .zoneId("UTC")
            .build();

        SchedulerConfig config = SchedulerConfig.builder()
            .jobClassName("com.example.MyJob")
            .periodicidade(periodicidade)
            .build();

        assertEquals(periodicidade, config.getPeriodicidade());
    }

    @Test
    @DisplayName("CT006 - Verificar valor padrão de periodicidade")
    void testPeriodicidadePadrao() {
        SchedulerConfig config = SchedulerConfig.builder()
            .jobClassName("com.example.MyJob")
            .build();

        assertNotNull(config.getPeriodicidade());
    }

    @Test
    @DisplayName("CT007 - Verificar builder sem periodicidade usa padrão")
    void testBuilderSemPeriodicidade() {
        SchedulerConfig config = SchedulerConfig.builder()
            .jobClassName("com.example.MyJob")
            .build();

        assertNotNull(config.getPeriodicidade());
        assertTrue(config.getPeriodicidade().getAtivo());
    }

    @Test
    @DisplayName("CT008 - Verificar setter de jobClassName")
    void testSetterJobClassName() {
        SchedulerConfig config = new SchedulerConfig();
        config.setJobClassName("com.example.NewJob");

        assertEquals("com.example.NewJob", config.getJobClassName());
    }

    @Test
    @DisplayName("CT009 - Verificar setter de periodicidade")
    void testSetterPeriodicidade() {
        SchedulerConfig config = new SchedulerConfig();
        Periodicidade periodicidade = Periodicidade.builder()
            .zoneId("America/Sao_Paulo")
            .build();

        config.setPeriodicidade(periodicidade);

        assertEquals(periodicidade, config.getPeriodicidade());
    }

    @Test
    @DisplayName("CT010 - Verificar construtor com todos os parâmetros")
    void testConstrutorTodosParametros() {
        Periodicidade periodicidade = Periodicidade.builder()
            .zoneId("UTC")
            .build();

        SchedulerConfig config = new SchedulerConfig("com.example.MyJob", periodicidade);

        assertEquals("com.example.MyJob", config.getJobClassName());
        assertEquals(periodicidade, config.getPeriodicidade());
    }

    @Test
    @DisplayName("CT011 - Verificar builder com jobClassName null")
    void testBuilderComJobClassNameNull() {
        SchedulerConfig config = SchedulerConfig.builder()
            .jobClassName(null)
            .build();

        assertNull(config.getJobClassName());
    }

    @Test
    @DisplayName("CT012 - Verificar builder com periodicidade null")
    void testBuilderComPeriodicidadeNull() {
        SchedulerConfig config = SchedulerConfig.builder()
            .periodicidade(null)
            .build();

        assertNull(config.getPeriodicidade());
    }

    @Test
    @DisplayName("CT013 - Verificar builder com ambos campos null")
    void testBuilderComAmbosNull() {
        SchedulerConfig config = SchedulerConfig.builder()
            .jobClassName(null)
            .periodicidade(null)
            .build();

        assertNull(config.getJobClassName());
        assertNull(config.getPeriodicidade());
    }

    @Test
    @DisplayName("CT014 - Verificar builder com jobClassName vazio")
    void testBuilderComJobClassNameVazio() {
        SchedulerConfig config = SchedulerConfig.builder()
            .jobClassName("")
            .build();

        assertEquals("", config.getJobClassName());
    }

    @Test
    @DisplayName("CT015 - Verificar builder com jobClassName longo")
    void testBuilderComJobClassNameLongo() {
        String longClassName = "com.example.very.long.package.name.for.testing.purposes.MyJobClass";
        SchedulerConfig config = SchedulerConfig.builder()
            .jobClassName(longClassName)
            .build();

        assertEquals(longClassName, config.getJobClassName());
    }

    @Test
    @DisplayName("CT016 - Verificar setter de jobClassName null")
    void testSetterJobClassNameNull() {
        SchedulerConfig config = new SchedulerConfig();
        config.setJobClassName(null);

        assertNull(config.getJobClassName());
    }

    @Test
    @DisplayName("CT017 - Verificar setter de periodicidade null")
    void testSetterPeriodicidadeNull() {
        SchedulerConfig config = new SchedulerConfig();
        config.setPeriodicidade(null);

        assertNull(config.getPeriodicidade());
    }

    @Test
    @DisplayName("CT018 - Verificar múltiplas chamadas de setters")
    void testMultiplasChamadasSetters() {
        SchedulerConfig config = new SchedulerConfig();
        Periodicidade periodicidade1 = Periodicidade.builder()
            .zoneId("UTC")
            .build();
        Periodicidade periodicidade2 = Periodicidade.builder()
            .zoneId("America/Sao_Paulo")
            .build();

        config.setJobClassName("com.example.Job1");
        config.setPeriodicidade(periodicidade1);
        assertEquals("com.example.Job1", config.getJobClassName());
        assertEquals(periodicidade1, config.getPeriodicidade());

        config.setJobClassName("com.example.Job2");
        config.setPeriodicidade(periodicidade2);
        assertEquals("com.example.Job2", config.getJobClassName());
        assertEquals(periodicidade2, config.getPeriodicidade());
    }

    @Test
    @DisplayName("CT019 - Verificar builder com BaseEntity fields")
    void testBuilderComBaseEntityFields() {
        SchedulerConfig config = SchedulerConfig.builder()
            .jobClassName("com.example.MyJob")
            .build();

        assertNotNull(config);
        assertNotNull(config.getPeriodicidade());
    }

    @Test
    @DisplayName("CT020 - Verificar equals com BaseEntity")
    void testEqualsComBaseEntity() {
        SchedulerConfig config1 = SchedulerConfig.builder()
            .jobClassName("com.example.MyJob")
            .build();
        SchedulerConfig config2 = SchedulerConfig.builder()
            .jobClassName("com.example.MyJob")
            .build();

        assertEquals(config1, config1);
        assertNotEquals(config1, config2);
    }

    @Test
    @DisplayName("CT021 - Verificar hashCode com BaseEntity")
    void testHashCodeComBaseEntity() {
        SchedulerConfig config = SchedulerConfig.builder()
            .jobClassName("com.example.MyJob")
            .build();

        assertNotNull(config.hashCode());
    }

    @Test
    @DisplayName("CT022 - Verificar canEqual com BaseEntity")
    void testCanEqualComBaseEntity() {
        SchedulerConfig config1 = SchedulerConfig.builder()
            .jobClassName("com.example.MyJob")
            .build();
        SchedulerConfig config2 = SchedulerConfig.builder()
            .jobClassName("com.example.MyJob")
            .build();

        assertTrue(config1.canEqual(config2));
        assertFalse(config1.canEqual("not a SchedulerConfig"));
    }

    @Test
    @DisplayName("CT023 - Verificar toString")
    void testToString() {
        SchedulerConfig config = SchedulerConfig.builder()
            .jobClassName("com.example.MyJob")
            .build();

        assertNotNull(config.toString());
    }

    @Test
    @DisplayName("CT024 - Verificar builder com periodicidade customizada")
    void testBuilderComPeriodicidadeCustomizada() {
        Periodicidade periodicidade = Periodicidade.builder()
            .zoneId("Europe/London")
            .ativo(false)
            .build();

        SchedulerConfig config = SchedulerConfig.builder()
            .jobClassName("com.example.MyJob")
            .periodicidade(periodicidade)
            .build();

        assertEquals(periodicidade, config.getPeriodicidade());
        assertEquals("Europe/London", config.getPeriodicidade().getZoneId());
        assertFalse(config.getPeriodicidade().getAtivo());
    }

    @Test
    @DisplayName("CT025 - Verificar construtor no-args")
    void testConstrutorNoArgs() {
        SchedulerConfig config = new SchedulerConfig();

        assertNotNull(config);
        assertNotNull(config.getPeriodicidade());
    }

    @Test
    @DisplayName("CT026 - Verificar construtor all-args com null")
    void testConstrutorAllArgsComNull() {
        SchedulerConfig config = new SchedulerConfig(null, null);

        assertNull(config.getJobClassName());
        assertNull(config.getPeriodicidade());
    }
}
