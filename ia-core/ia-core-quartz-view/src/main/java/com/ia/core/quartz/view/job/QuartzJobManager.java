package com.ia.core.quartz.view.job;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ia.core.quartz.service.model.job.QuartzJobDTO;
import com.ia.core.quartz.service.model.job.QuartzJobInstanceDTO;
import com.ia.core.quartz.service.model.job.QuartzJobTriggerDTO;
import com.ia.core.quartz.service.model.job.QuartzJobUseCase;
import com.ia.core.quartz.service.model.job.QuartzJobTranslator;
import com.ia.core.security.view.manager.DefaultSecuredViewBaseManager;

/**
 * Manager para operações de Jobs do Quartz.
 * 
 * @author Israel Araújo
 */
@Service
public class QuartzJobManager extends DefaultSecuredViewBaseManager<QuartzJobDTO> implements QuartzJobUseCase {

  /**
   * @param config Configuração do manager
   */
  public QuartzJobManager(QuartzJobManagerConfig config) {
    super(config);
  }

  @Override
  public String getFunctionalityTypeName() {
    return QuartzJobTranslator.QUARTZ_JOB;
  }

  @SuppressWarnings("unchecked")
  @Override
  public QuartzJobClient getClient() {
    return (QuartzJobClient) super.getClient();
  }

  /**
   * Lista todos os jobs do scheduler.
   * 
   * @return Lista de jobs
   */
  public List<QuartzJobDTO> findAllJobs() {
    return getClient().findAllJobs();
  }

  /**
   * Busca um job específico.
   * 
   * @param jobName  Nome do job
   * @param jobGroup Grupo do job
   * @return Job encontrado
   */
  public QuartzJobDTO findJob(String jobName, String jobGroup) {
    return getClient().findJob(jobName, jobGroup);
  }

  /**
   * Pausa um job.
   * 
   * @param jobName  Nome do job
   * @param jobGroup Grupo do job
   * @return true se pausado com sucesso
   */
  public boolean pauseJob(String jobName, String jobGroup) {
    return getClient().pauseJob(jobName, jobGroup);
  }

  /**
   * Resume um job pausado.
   * 
   * @param jobName  Nome do job
   * @param jobGroup Grupo do job
   * @return true se resumido com sucesso
   */
  public boolean resumeJob(String jobName, String jobGroup) {
    return getClient().resumeJob(jobName, jobGroup);
  }

  /**
   * Remove um job.
   * 
   * @param jobName  Nome do job
   * @param jobGroup Grupo do job
   * @return true se removido com sucesso
   */
  public boolean deleteJob(String jobName, String jobGroup) {
    return getClient().deleteJob(jobName, jobGroup);
  }

  /**
   * Dispara um job para execução imediata.
   * 
   * @param jobName  Nome do job
   * @param jobGroup Grupo do job
   * @return true se disparado com sucesso
   */
  public boolean triggerJob(String jobName, String jobGroup) {
    return getClient().triggerJob(jobName, jobGroup);
  }

  /**
   * Lista os triggers de um job.
   * 
   * @param jobName  Nome do job
   * @param jobGroup Grupo do job
   * @return Lista de triggers
   */
  public List<QuartzJobTriggerDTO> findTriggersOfJob(String jobName, String jobGroup) {
    return getClient().findTriggersOfJob(jobName, jobGroup);
  }

  /**
   * Lista os jobs em execução.
   * 
   * @return Lista de jobs em execução
   */
  public List<QuartzJobInstanceDTO> findCurrentlyExecutingJobs() {
    return getClient().findCurrentlyExecutingJobs();
  }

  /**
   * Pausa um trigger.
   * 
   * @param triggerName  Nome do trigger
   * @param triggerGroup Grupo do trigger
   * @return true se pausado com sucesso
   */
  public boolean pauseTrigger(String triggerName, String triggerGroup) {
    return getClient().pauseTrigger(triggerName, triggerGroup);
  }

  /**
   * Resume um trigger.
   * 
   * @param triggerName  Nome do trigger
   * @param triggerGroup Grupo do trigger
   * @return true se resumido com sucesso
   */
  public boolean resumeTrigger(String triggerName, String triggerGroup) {
    return getClient().resumeTrigger(triggerName, triggerGroup);
  }
}
