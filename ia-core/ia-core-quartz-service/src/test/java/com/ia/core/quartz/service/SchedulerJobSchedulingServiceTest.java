package com.ia.core.quartz.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.JobKey;
import org.quartz.TriggerKey;

import com.ia.core.quartz.service.model.scheduler.dto.SchedulerConfigDTO;
import com.ia.core.quartz.service.model.periodicidade.dto.IntervaloTemporalDTO;
import com.ia.core.quartz.service.model.periodicidade.dto.PeriodicidadeDTO;
import com.ia.core.quartz.service.SchedulerConfigServiceConfig;
import com.ia.core.quartz.service.JobSchedulerChecker;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;

class SchedulerJobSchedulingServiceTest {

    @Mock
    private SchedulerConfigServiceConfig config;

    @Mock
    private Scheduler scheduler;

    @InjectMocks
    private SchedulerJobSchedulingService schedulingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(config.getQuartzScheduler()).thenReturn(scheduler);
    }

    @Test
    void testAgendarJob_TriggerAlreadyExists() throws Exception {
        // Arrange
        SchedulerConfigDTO dto = createSchedulerConfigDTO(1L);
        dto.setPeriodicidade(PeriodicidadeDTO.builder()
            .intervaloBase(IntervaloTemporalDTO.builder()
                .startDate(LocalDate.now())
                .startTime(LocalTime.now())
                .build())
            .build());
        when(scheduler.checkExists(any(TriggerKey.class))).thenReturn(true);
        // Mock getQuartzScheduler to return a mock scheduler that won't throw
        when(config.getQuartzScheduler()).thenReturn(scheduler);

        // Act
        schedulingService.agendarJob(dto);

        // Assert
        verify(scheduler, never()).scheduleJob(any(), any());
        assertThat(scheduler).isNotNull();
    }

    @Test
    void testAgendarJob_NullConfig() {
        // Act & Assert
        assertThrows(NullPointerException.class, () -> schedulingService.agendarJob(null));
    }

    @Test
    void testAtualizarJob_JobNotExists() throws Exception {
        // Arrange
        SchedulerConfigDTO dto = createSchedulerConfigDTO(1L);
        when(scheduler.checkExists(any(JobKey.class))).thenReturn(false);

        // Act
        schedulingService.atualizarJob(dto);

        // Assert
        verify(scheduler, never()).rescheduleJob(any(TriggerKey.class), any());
        assertThat(scheduler).isNotNull();
    }

    @Test
    void testCancelarJob_Success() throws Exception {
        // Arrange
        SchedulerConfigDTO dto = createSchedulerConfigDTO(1L);
        when(scheduler.checkExists(any(JobKey.class))).thenReturn(true);

        // Act
        schedulingService.cancelarJob(dto);

        // Assert
        verify(scheduler, times(1)).deleteJob(any(JobKey.class));
        assertThat(scheduler).isNotNull();
    }

    @Test
    void testCancelarJob_JobNotExists() throws Exception {
        // Arrange
        SchedulerConfigDTO dto = createSchedulerConfigDTO(1L);
        when(scheduler.checkExists(any(JobKey.class))).thenReturn(false);

        // Act
        schedulingService.cancelarJob(dto);

        // Assert
        verify(scheduler, never()).deleteJob(any(JobKey.class));
        assertThat(scheduler).isNotNull();
    }

    @Test
    void testAgendarJobs_NullList() {
        // Act & Assert
        assertThrows(NullPointerException.class, () -> schedulingService.agendarJobs(null));
        assertThat(schedulingService).isNotNull();
    }

    @Test
    void testUpdateJobs_NullList() {
        // Act & Assert
        assertThrows(NullPointerException.class, () -> schedulingService.updateJobs(null));
        assertThat(schedulingService).isNotNull();
    }

    @Test
    void testProcessarTriggers_NullConfig() {
        // Act & Assert
        assertThrows(NullPointerException.class, () -> schedulingService.processarTriggers(null));
        assertThat(schedulingService).isNotNull();
    }

    private SchedulerConfigDTO createSchedulerConfigDTO(Long id) {
        SchedulerConfigDTO dto = new SchedulerConfigDTO();
        dto.setId(id);
        dto.setJobClassName(JobSchedulerChecker.class.getName());
        dto.setPeriodicidade(PeriodicidadeDTO.builder()
            .intervaloBase(IntervaloTemporalDTO.builder()
                .startDate(LocalDate.now())
                .startTime(LocalTime.now())
                .build())
            .build());
        return dto;
    }
}
