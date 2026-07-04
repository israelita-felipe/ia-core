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

import com.ia.core.quartz.SchedulerRegistry;
import com.ia.core.quartz.service.SchedulerJobManagementService;
import com.ia.core.quartz.service.SchedulerListenerService;

class SchedulerRegistryTest {

    @Mock
    private Scheduler scheduler;

    @Mock
    private SchedulerJobManagementService jobManagementService;

    @Mock
    private SchedulerListenerService listenerService;

    @InjectMocks
    private SchedulerRegistry schedulerRegistry;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegistry_CallsListenersAndJobManagement() {
        // Act
        schedulerRegistry.registry();

        // Assert
        verify(listenerService, times(1)).criarListeners(scheduler);
        verify(jobManagementService, times(1)).iniciarJobs();
        assertThat(schedulerRegistry).isNotNull();
    }

    @Test
    void testRegistry_ListenerServiceCalled() {
        // Act
        schedulerRegistry.registry();

        // Assert
        verify(listenerService, times(1)).criarListeners(scheduler);
        verifyNoMoreInteractions(listenerService);
        assertThat(schedulerRegistry).isNotNull();
    }

    @Test
    void testRegistry_JobManagementServiceCalled() {
        // Act
        schedulerRegistry.registry();

        // Assert
        verify(jobManagementService, times(1)).iniciarJobs();
        verifyNoMoreInteractions(jobManagementService);
        assertThat(schedulerRegistry).isNotNull();
    }
}
