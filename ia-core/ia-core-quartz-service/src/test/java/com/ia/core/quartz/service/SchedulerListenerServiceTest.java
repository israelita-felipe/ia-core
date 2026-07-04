package com.ia.core.quartz.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.quartz.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SchedulerListenerServiceTest {

    @Mock
    private Scheduler scheduler;

    @Mock
    private ListenerManager listenerManager;

    @InjectMocks
    private SchedulerListenerService listenerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        try {
            when(scheduler.getListenerManager()).thenReturn(listenerManager);
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testCriarListeners_Success() throws SchedulerException {
        // Act
        listenerService.criarListeners(scheduler);

        // Assert
        verify(scheduler, times(2)).getListenerManager();
        verify(listenerManager, times(1)).addJobListener(any(JobListener.class));
        verify(listenerManager, times(1)).addTriggerListener(any(TriggerListener.class));
    }

    @Test
    void testCriarListeners_NullScheduler() {
        // Act & Assert
        assertThrows(NullPointerException.class, () -> listenerService.criarListeners(null));
    }

    @Test
    void testCriarListeners_SchedulerException() throws SchedulerException {
        // Arrange
        doThrow(new RuntimeException("Test error")).when(listenerManager).addJobListener(any(JobListener.class));

        // Act
        listenerService.criarListeners(scheduler);

        // Assert - Should not throw, just log error
        // When addJobListener throws, the code doesn't continue to addTriggerListener
        verify(scheduler, times(1)).getListenerManager();
        verify(listenerManager, times(1)).addJobListener(any(JobListener.class));
        verify(listenerManager, never()).addTriggerListener(any(TriggerListener.class));
    }

    @Test
    void testCriarJobListener() {
        // Act
        JobListener listener = listenerService.criarJobListener();

        // Assert
        assertNotNull(listener);
        assertTrue(listener instanceof JobsListener);
    }

    @Test
    void testCriarTriggerListener() {
        // Act
        TriggerListener listener = listenerService.criarTriggerListener();

        // Assert
        assertNotNull(listener);
        assertTrue(listener instanceof TriggersListener);
    }
}
