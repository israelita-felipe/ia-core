package com.ia.core.quartz.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.quartz.JobExecutionContext;

import com.ia.core.quartz.service.model.job.dto.QuartzJobDTO;
import com.ia.core.quartz.service.model.job.dto.QuartzJobTriggerDTO;
import com.ia.core.quartz.service.model.job.dto.QuartzJobInstanceDTO;
import com.ia.core.quartz.service.SchedulerConfigServiceConfig;

import java.util.Collections;
import java.util.List;
import java.util.Set;

class SchedulerJobControlServiceTest {

    @Mock
    private SchedulerConfigServiceConfig config;

    @Mock
    private Scheduler scheduler;

    @InjectMocks
    private SchedulerJobControlService jobControlService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(config.getQuartzScheduler()).thenReturn(scheduler);
    }

    @Test
    void testFindAllJobs_EmptyScheduler() throws Exception {
        // Arrange
        when(scheduler.getJobGroupNames()).thenReturn(Collections.emptyList());

        // Act
        List<QuartzJobDTO> result = jobControlService.findAllJobs();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testFindJob_NotFound() throws Exception {
        // Arrange
        when(scheduler.getJobDetail(any(JobKey.class))).thenReturn(null);

        // Act
        QuartzJobDTO result = jobControlService.findJob("job1", "group1");

        // Assert
        assertNull(result);
    }

    @Test
    void testPauseJob_NotFound() throws Exception {
        // Arrange
        when(scheduler.checkExists(any(JobKey.class))).thenReturn(false);

        // Act
        boolean result = jobControlService.pauseJob("job1", "group1");

        // Assert
        assertFalse(result);
        verify(scheduler, never()).pauseJob(any(JobKey.class));
    }

    @Test
    void testResumeJob_NotFound() throws Exception {
        // Arrange
        when(scheduler.checkExists(any(JobKey.class))).thenReturn(false);

        // Act
        boolean result = jobControlService.resumeJob("job1", "group1");

        // Assert
        assertFalse(result);
        verify(scheduler, never()).resumeJob(any(JobKey.class));
    }

    @Test
    void testDeleteJob_NotFound() throws Exception {
        // Arrange
        when(scheduler.checkExists(any(JobKey.class))).thenReturn(false);

        // Act
        boolean result = jobControlService.deleteJob("job1", "group1");

        // Assert
        assertFalse(result);
        verify(scheduler, never()).deleteJob(any(JobKey.class));
    }

    @Test
    void testTriggerJob_NotFound() throws Exception {
        // Arrange
        when(scheduler.checkExists(any(JobKey.class))).thenReturn(false);

        // Act
        boolean result = jobControlService.triggerJob("job1", "group1");

        // Assert
        assertFalse(result);
        verify(scheduler, never()).triggerJob(any(JobKey.class));
    }

    @Test
    void testFindTriggersOfJob_Success() throws Exception {
        // Arrange
        JobKey jobKey = JobKey.jobKey("job1", "group1");
        Trigger trigger = mock(Trigger.class);
        when(trigger.getKey()).thenReturn(TriggerKey.triggerKey("trigger1", "group1"));
        doReturn((List<? extends Trigger>) Collections.singletonList(trigger)).when(scheduler).getTriggersOfJob(jobKey);
        when(scheduler.getTriggerState(any(TriggerKey.class))).thenReturn(Trigger.TriggerState.NORMAL);
        when(trigger.getNextFireTime()).thenReturn(null);
        when(trigger.getPreviousFireTime()).thenReturn(null);
        when(trigger.getStartTime()).thenReturn(null);
        when(trigger.getEndTime()).thenReturn(null);

        // Act
        List<QuartzJobTriggerDTO> result = jobControlService.findTriggersOfJob("job1", "group1");

        // Assert
        assertNotNull(result);
        verify(scheduler, times(1)).getTriggersOfJob(jobKey);
    }

    @Test
    void testFindCurrentlyExecutingJobs_Empty() throws Exception {
        // Arrange
        when(scheduler.getCurrentlyExecutingJobs()).thenReturn(Collections.emptyList());

        // Act
        List<QuartzJobInstanceDTO> result = jobControlService.findCurrentlyExecutingJobs();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testPauseTrigger_NotFound() throws Exception {
        // Arrange
        when(scheduler.checkExists(any(TriggerKey.class))).thenReturn(false);

        // Act
        boolean result = jobControlService.pauseTrigger("trigger1", "group1");

        // Assert
        assertFalse(result);
        verify(scheduler, never()).pauseTrigger(any(TriggerKey.class));
    }

    @Test
    void testResumeTrigger_NotFound() throws Exception {
        // Arrange
        when(scheduler.checkExists(any(TriggerKey.class))).thenReturn(false);

        // Act
        boolean result = jobControlService.resumeTrigger("trigger1", "group1");

        // Assert
        assertFalse(result);
        verify(scheduler, never()).resumeTrigger(any(TriggerKey.class));
    }
}
