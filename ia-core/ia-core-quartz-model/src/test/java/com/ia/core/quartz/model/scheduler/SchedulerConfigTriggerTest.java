package com.ia.core.quartz.model.scheduler;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes para a classe SchedulerConfigTrigger.
 */
@DisplayName("Testes de SchedulerConfigTrigger")
class SchedulerConfigTriggerTest {

    @Test
    @DisplayName("CT001 - Verificar construtor padrão")
    void testConstrutorPadrao() {
        SchedulerConfigTrigger trigger = new SchedulerConfigTrigger();
        assertNotNull(trigger);
    }

    @Test
    @DisplayName("CT002 - Verificar construtor completo com builder")
    void testConstrutorCompletoBuilder() {
        UUID triggerName = UUID.randomUUID();

        SchedulerConfigTrigger trigger = SchedulerConfigTrigger.builder()
            .triggerName(triggerName)
            .schedulerName("MyScheduler")
            .triggerGroup("DEFAULT")
            .jobName("MyJob")
            .jobGroup("DEFAULT")
            .description("Test trigger")
            .nextFireTime("2024-01-01 10:00:00")
            .prevFireTime("2024-01-01 09:00:00")
            .priority("5")
            .triggerState("NORMAL")
            .triggerType("CRON")
            .triggerStartTime("2024-01-01 00:00:00")
            .endTime("2024-12-31 23:59:59")
            .calendarName("Holidays")
            .misFireInstr("1")
            .jobData("{}")
            .build();

        assertEquals(triggerName, trigger.getTriggerName());
        assertEquals("MyScheduler", trigger.getSchedulerName());
        assertEquals("DEFAULT", trigger.getTriggerGroup());
        assertEquals("MyJob", trigger.getJobName());
        assertEquals("DEFAULT", trigger.getJobGroup());
        assertEquals("Test trigger", trigger.getDescription());
        assertEquals("2024-01-01 10:00:00", trigger.getNextFireTime());
        assertEquals("2024-01-01 09:00:00", trigger.getPrevFireTime());
        assertEquals("5", trigger.getPriority());
        assertEquals("NORMAL", trigger.getTriggerState());
        assertEquals("CRON", trigger.getTriggerType());
        assertEquals("2024-01-01 00:00:00", trigger.getTriggerStartTime());
        assertEquals("2024-12-31 23:59:59", trigger.getEndTime());
        assertEquals("Holidays", trigger.getCalendarName());
        assertEquals("1", trigger.getMisFireInstr());
        assertEquals("{}", trigger.getJobData());
    }

    @Test
    @DisplayName("CT003 - Verificar constantes TABLE_NAME e SCHEMA_NAME")
    void testConstantes() {
        assertEquals("QRTZ_TRIGGERS", SchedulerConfigTrigger.TABLE_NAME);
        assertEquals("QUARTZ", SchedulerConfigTrigger.SCHEMA_NAME);
    }

    @Test
    @DisplayName("CT004 - Verificar método compareTo")
    void testCompareTo() {
        UUID uuidA = UUID.fromString("00000000-0000-0000-0000-000000000001");
        UUID uuidB = UUID.fromString("00000000-0000-0000-0000-000000000002");

        SchedulerConfigTrigger triggerA = SchedulerConfigTrigger.builder()
            .triggerName(uuidA)
            .build();
        SchedulerConfigTrigger triggerB = SchedulerConfigTrigger.builder()
            .triggerName(uuidB)
            .build();
        SchedulerConfigTrigger triggerA2 = SchedulerConfigTrigger.builder()
            .triggerName(uuidA)
            .build();

        assertTrue(triggerA.compareTo(triggerB) < 0);
        assertTrue(triggerB.compareTo(triggerA) > 0);
        assertEquals(0, triggerA.compareTo(triggerA2));
    }

    @Test
    @DisplayName("CT005 - Verificar método compareTo com null")
    void testCompareToComNull() {
        UUID uuidA = UUID.fromString("00000000-0000-0000-0000-000000000001");

        SchedulerConfigTrigger triggerNull = SchedulerConfigTrigger.builder()
            .triggerName(null)
            .build();
        SchedulerConfigTrigger triggerA = SchedulerConfigTrigger.builder()
            .triggerName(uuidA)
            .build();
        SchedulerConfigTrigger triggerNull2 = SchedulerConfigTrigger.builder()
            .triggerName(null)
            .build();

        assertTrue(triggerNull.compareTo(triggerA) < 0);
        assertTrue(triggerA.compareTo(triggerNull) > 0);
        assertEquals(0, triggerNull.compareTo(triggerNull2));
    }

    @Test
    @DisplayName("CT006 - Verificar builder com campos parciais")
    void testBuilderCamposParciais() {
        UUID triggerName = UUID.randomUUID();

        SchedulerConfigTrigger trigger = SchedulerConfigTrigger.builder()
            .triggerName(triggerName)
            .schedulerName("MyScheduler")
            .build();

        assertEquals(triggerName, trigger.getTriggerName());
        assertEquals("MyScheduler", trigger.getSchedulerName());
    }

    @Test
    @DisplayName("CT007 - Verificar builder com todos os campos nulos")
    void testBuilderTodosCamposNull() {
        SchedulerConfigTrigger trigger = SchedulerConfigTrigger.builder()
            .triggerName(null)
            .schedulerName(null)
            .triggerGroup(null)
            .jobName(null)
            .jobGroup(null)
            .description(null)
            .nextFireTime(null)
            .prevFireTime(null)
            .priority(null)
            .triggerState(null)
            .triggerType(null)
            .triggerStartTime(null)
            .endTime(null)
            .calendarName(null)
            .misFireInstr(null)
            .jobData(null)
            .build();

        assertNull(trigger.getTriggerName());
        assertNull(trigger.getSchedulerName());
        assertNull(trigger.getTriggerGroup());
        assertNull(trigger.getJobName());
        assertNull(trigger.getJobGroup());
        assertNull(trigger.getDescription());
        assertNull(trigger.getNextFireTime());
        assertNull(trigger.getPrevFireTime());
        assertNull(trigger.getPriority());
        assertNull(trigger.getTriggerState());
        assertNull(trigger.getTriggerType());
        assertNull(trigger.getTriggerStartTime());
        assertNull(trigger.getEndTime());
        assertNull(trigger.getCalendarName());
        assertNull(trigger.getMisFireInstr());
        assertNull(trigger.getJobData());
    }

    @Test
    @DisplayName("CT008 - Verificar builder com strings vazias")
    void testBuilderStringsVazias() {
        SchedulerConfigTrigger trigger = SchedulerConfigTrigger.builder()
            .schedulerName("")
            .triggerGroup("")
            .jobName("")
            .jobGroup("")
            .description("")
            .nextFireTime("")
            .prevFireTime("")
            .priority("")
            .triggerState("")
            .triggerType("")
            .triggerStartTime("")
            .endTime("")
            .calendarName("")
            .misFireInstr("")
            .jobData("")
            .build();

        assertEquals("", trigger.getSchedulerName());
        assertEquals("", trigger.getTriggerGroup());
        assertEquals("", trigger.getJobName());
        assertEquals("", trigger.getJobGroup());
        assertEquals("", trigger.getDescription());
        assertEquals("", trigger.getNextFireTime());
        assertEquals("", trigger.getPrevFireTime());
        assertEquals("", trigger.getPriority());
        assertEquals("", trigger.getTriggerState());
        assertEquals("", trigger.getTriggerType());
        assertEquals("", trigger.getTriggerStartTime());
        assertEquals("", trigger.getEndTime());
        assertEquals("", trigger.getCalendarName());
        assertEquals("", trigger.getMisFireInstr());
        assertEquals("", trigger.getJobData());
    }

    @Test
    @DisplayName("CT009 - Verificar setter de triggerName")
    void testSetterTriggerName() {
        UUID triggerName = UUID.randomUUID();
        SchedulerConfigTrigger trigger = new SchedulerConfigTrigger();
        trigger.setTriggerName(triggerName);

        assertEquals(triggerName, trigger.getTriggerName());
    }

    @Test
    @DisplayName("CT010 - Verificar setter de schedulerName")
    void testSetterSchedulerName() {
        SchedulerConfigTrigger trigger = new SchedulerConfigTrigger();
        trigger.setSchedulerName("NewScheduler");

        assertEquals("NewScheduler", trigger.getSchedulerName());
    }

    @Test
    @DisplayName("CT011 - Verificar setter de triggerGroup")
    void testSetterTriggerGroup() {
        SchedulerConfigTrigger trigger = new SchedulerConfigTrigger();
        trigger.setTriggerGroup("CUSTOM");

        assertEquals("CUSTOM", trigger.getTriggerGroup());
    }

    @Test
    @DisplayName("CT012 - Verificar setter de jobName")
    void testSetterJobName() {
        SchedulerConfigTrigger trigger = new SchedulerConfigTrigger();
        trigger.setJobName("NewJob");

        assertEquals("NewJob", trigger.getJobName());
    }

    @Test
    @DisplayName("CT013 - Verificar setter de jobGroup")
    void testSetterJobGroup() {
        SchedulerConfigTrigger trigger = new SchedulerConfigTrigger();
        trigger.setJobGroup("CUSTOM");

        assertEquals("CUSTOM", trigger.getJobGroup());
    }

    @Test
    @DisplayName("CT014 - Verificar setter de description")
    void testSetterDescription() {
        SchedulerConfigTrigger trigger = new SchedulerConfigTrigger();
        trigger.setDescription("New description");

        assertEquals("New description", trigger.getDescription());
    }

    @Test
    @DisplayName("CT015 - Verificar setter de nextFireTime")
    void testSetterNextFireTime() {
        SchedulerConfigTrigger trigger = new SchedulerConfigTrigger();
        trigger.setNextFireTime("2025-01-01 10:00:00");

        assertEquals("2025-01-01 10:00:00", trigger.getNextFireTime());
    }

    @Test
    @DisplayName("CT016 - Verificar setter de prevFireTime")
    void testSetterPrevFireTime() {
        SchedulerConfigTrigger trigger = new SchedulerConfigTrigger();
        trigger.setPrevFireTime("2025-01-01 09:00:00");

        assertEquals("2025-01-01 09:00:00", trigger.getPrevFireTime());
    }

    @Test
    @DisplayName("CT017 - Verificar setter de priority")
    void testSetterPriority() {
        SchedulerConfigTrigger trigger = new SchedulerConfigTrigger();
        trigger.setPriority("10");

        assertEquals("10", trigger.getPriority());
    }

    @Test
    @DisplayName("CT018 - Verificar setter de triggerState")
    void testSetterTriggerState() {
        SchedulerConfigTrigger trigger = new SchedulerConfigTrigger();
        trigger.setTriggerState("PAUSED");

        assertEquals("PAUSED", trigger.getTriggerState());
    }

    @Test
    @DisplayName("CT019 - Verificar setter de triggerType")
    void testSetterTriggerType() {
        SchedulerConfigTrigger trigger = new SchedulerConfigTrigger();
        trigger.setTriggerType("SIMPLE");

        assertEquals("SIMPLE", trigger.getTriggerType());
    }

    @Test
    @DisplayName("CT020 - Verificar setter de triggerStartTime")
    void testSetterTriggerStartTime() {
        SchedulerConfigTrigger trigger = new SchedulerConfigTrigger();
        trigger.setTriggerStartTime("2025-01-01 00:00:00");

        assertEquals("2025-01-01 00:00:00", trigger.getTriggerStartTime());
    }

    @Test
    @DisplayName("CT021 - Verificar setter de endTime")
    void testSetterEndTime() {
        SchedulerConfigTrigger trigger = new SchedulerConfigTrigger();
        trigger.setEndTime("2025-12-31 23:59:59");

        assertEquals("2025-12-31 23:59:59", trigger.getEndTime());
    }

    @Test
    @DisplayName("CT022 - Verificar setter de calendarName")
    void testSetterCalendarName() {
        SchedulerConfigTrigger trigger = new SchedulerConfigTrigger();
        trigger.setCalendarName("WorkDays");

        assertEquals("WorkDays", trigger.getCalendarName());
    }

    @Test
    @DisplayName("CT023 - Verificar setter de misFireInstr")
    void testSetterMisFireInstr() {
        SchedulerConfigTrigger trigger = new SchedulerConfigTrigger();
        trigger.setMisFireInstr("2");

        assertEquals("2", trigger.getMisFireInstr());
    }

    @Test
    @DisplayName("CT024 - Verificar setter de jobData")
    void testSetterJobData() {
        SchedulerConfigTrigger trigger = new SchedulerConfigTrigger();
        trigger.setJobData("{\"key\":\"value\"}");

        assertEquals("{\"key\":\"value\"}", trigger.getJobData());
    }
}
