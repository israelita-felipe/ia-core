package com.ia.core.quartz.view.job;

import com.ia.core.quartz.service.model.job.QuartzJobUseCase;
import com.ia.core.quartz.service.model.job.dto.QuartzJobDTO;
import com.ia.core.quartz.service.model.job.dto.QuartzJobInstanceDTO;
import com.ia.core.quartz.service.model.job.dto.QuartzJobTriggerDTO;
import com.ia.core.quartz.service.model.scheduler.dto.SchedulerConfigTranslator;
import com.ia.core.security.view.manager.DefaultSecuredViewBaseManager;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Manager para operações de Jobs do Quartz.
 *
 * @author Israel Araújo
 */

@Service
public class QuartzJobManager
  extends DefaultSecuredViewBaseManager<QuartzJobDTO>
  implements QuartzJobUseCase {

  /**
   * @param config Configuração do manager
   */
  public QuartzJobManager(QuartzJobManagerConfig config) {
    super(config);
  }

  @Override
  public String getFunctionalityTypeName() {
    return SchedulerConfigTranslator.SCHEDULER_CONFIG;
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
  @Override
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
  @Override
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
  @Override
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
  @Override
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
  @Override
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
  @Override
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
  @Override
  public List<QuartzJobTriggerDTO> findTriggersOfJob(String jobName,
                                                     String jobGroup) {
    return getClient().findTriggersOfJob(jobName, jobGroup);
  }

  /**
   * Lista os jobs em execução.
   *
   * @return Lista de jobs em execução
   */
  @Override
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
  @Override
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
  @Override
  public boolean resumeTrigger(String triggerName, String triggerGroup) {
    return getClient().resumeTrigger(triggerName, triggerGroup);
  }
}
