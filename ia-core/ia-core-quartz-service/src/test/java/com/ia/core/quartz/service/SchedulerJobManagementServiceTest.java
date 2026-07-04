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

import com.ia.core.quartz.model.scheduler.SchedulerConfig;
import com.ia.core.quartz.service.model.scheduler.dto.SchedulerConfigDTO;
import com.ia.core.quartz.service.SchedulerConfigRepository;
import com.ia.core.quartz.service.SchedulerJobSchedulingService;
import com.ia.core.quartz.service.SchedulerConfigServiceConfig;
import com.ia.core.service.mapper.BaseEntityMapper;

import java.util.Collections;
import java.util.List;

class SchedulerJobManagementServiceTest {

    @Mock
    private SchedulerJobSchedulingService schedulerJobSchedulingService;

    @Mock
    private SchedulerConfigServiceConfig config;

    @Mock
    private SchedulerConfigRepository schedulerConfigRepository;

    @Mock
    private BaseEntityMapper<SchedulerConfig, SchedulerConfigDTO> mapper;

    @InjectMocks
    private SchedulerJobManagementService schedulerJobManagementService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(config.getRepository()).thenReturn(schedulerConfigRepository);
        when(config.getMapper()).thenReturn(mapper);
    }

    @Test
    void testIniciarJobs_Success() throws Exception {
        // Arrange
        SchedulerConfig entity = new SchedulerConfig();
        entity.setId(1L);
        SchedulerConfigDTO dto = new SchedulerConfigDTO();
        dto.setId(1L);

        when(schedulerConfigRepository.findAllActiveWithPeriodicidade(true))
            .thenReturn(List.of(entity));
        when(mapper.toDTO(entity)).thenReturn(dto);

        // Act
        schedulerJobManagementService.iniciarJobs();

        // Assert
        verify(schedulerJobSchedulingService, times(1)).agendarJobs(List.of(dto));
        assertThat(schedulerJobManagementService).isNotNull();
    }

    @Test
    void testIniciarJobs_EmptyList() throws Exception {
        // Arrange
        when(schedulerConfigRepository.findAllActiveWithPeriodicidade(true))
            .thenReturn(Collections.emptyList());

        // Act
        schedulerJobManagementService.iniciarJobs();

        // Assert
        verify(schedulerJobSchedulingService, times(1)).agendarJobs(Collections.emptyList());
        assertThat(schedulerJobManagementService).isNotNull();
    }

    @Test
    void testVerificarAtualizacoes_Success() throws Exception {
        // Arrange
        SchedulerConfig entity = new SchedulerConfig();
        entity.setId(1L);
        SchedulerConfigDTO dto = new SchedulerConfigDTO();
        dto.setId(1L);

        when(schedulerConfigRepository.findAllActiveWithPeriodicidade(true))
            .thenReturn(List.of(entity));
        when(mapper.toDTO(entity)).thenReturn(dto);

        // Act
        schedulerJobManagementService.verificarAtualizacoes();

        // Assert
        verify(schedulerJobSchedulingService, times(1)).updateJobs(List.of(dto));
        assertThat(schedulerJobManagementService).isNotNull();
    }

    @Test
    void testAgendarJob_Success() throws Exception {
        // Arrange
        SchedulerConfigDTO dto = new SchedulerConfigDTO();
        dto.setId(1L);

        // Act
        schedulerJobManagementService.agendarJob(dto);

        // Assert
        verify(schedulerJobSchedulingService, times(1)).agendarJob(dto);
        assertThat(schedulerJobManagementService).isNotNull();
    }

    @Test
    void testAgendarJob_NullConfig() {
        // Act & Assert
        assertThrows(NullPointerException.class, () -> schedulerJobManagementService.agendarJob(null));
    }

    @Test
    void testCancelarJob_Success() throws Exception {
        // Arrange
        SchedulerConfigDTO dto = new SchedulerConfigDTO();
        dto.setId(1L);

        // Act
        schedulerJobManagementService.cancelarJob(dto);

        // Assert
        verify(schedulerJobSchedulingService, times(1)).cancelarJob(dto);
        assertThat(schedulerJobManagementService).isNotNull();
    }

    @Test
    void testCancelarJob_NullConfig() {
        // Act & Assert
        assertThrows(NullPointerException.class, () -> schedulerJobManagementService.cancelarJob(null));
    }

    @Test
    void testCancelAllJobs_Success() throws SchedulerException {
        // Arrange
        Scheduler quartzScheduler = mock(Scheduler.class);
        when(config.getQuartzScheduler()).thenReturn(quartzScheduler);

        // Act
        schedulerJobManagementService.cancelAllJobs();

        // Assert
        verify(quartzScheduler, times(1)).clear();
        assertThat(schedulerJobManagementService).isNotNull();
    }

    @Test
    void testUpdateJobs_Success() throws Exception {
        // Arrange
        SchedulerConfig entity = new SchedulerConfig();
        entity.setId(1L);
        SchedulerConfigDTO dto = new SchedulerConfigDTO();
        dto.setId(1L);

        when(schedulerConfigRepository.findAllActiveWithPeriodicidade(true))
            .thenReturn(List.of(entity));
        when(mapper.toDTO(entity)).thenReturn(dto);

        // Act
        schedulerJobManagementService.updateJobs();

        // Assert
        verify(schedulerJobSchedulingService, times(1)).updateJobs(List.of(dto));
        assertThat(schedulerJobManagementService).isNotNull();
    }
}
