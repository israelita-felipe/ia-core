package com.ia.core.quartz.service.model.job;

import com.ia.core.quartz.service.model.job.dto.QuartzJobDTO;
import com.ia.core.quartz.service.model.job.dto.QuartzJobInstanceDTO;
import com.ia.core.quartz.service.model.job.dto.QuartzJobTriggerDTO;

import java.util.List;

/**
 * Interface de Use Case para manipulação direta de Jobs do Quartz.
 * <p>
 * Define as operações específicas para gerenciar jobs diretamente no scheduler
 * sem depender de configurações do SchedulerConfig.
 *
 * @author Israel Araújo
 */
public interface QuartzJobUseCase {

  /**
   * Lista todos os jobs do scheduler.
   *
   * @return lista de jobs
   */
  List<QuartzJobDTO> findAllJobs();

  /**
   * Busca um job específico pelo nome e grupo.
   *
   * @param jobName  Nome do job
   * @param jobGroup Grupo do job
   * @return job encontrado
   */
  QuartzJobDTO findJob(String jobName, String jobGroup);

  /**
   * Pausa um job.
   *
   * @param jobName  Nome do job
   * @param jobGroup Grupo do job
   * @return true se pausado com sucesso
   */
  boolean pauseJob(String jobName, String jobGroup);

  /**
   * Resume um job pausado.
   *
   * @param jobName  Nome do job
   * @param jobGroup Grupo do job
   * @return true se resumido com sucesso
   */
  boolean resumeJob(String jobName, String jobGroup);

  /**
   * Remove um job.
   *
   * @param jobName  Nome do job
   * @param jobGroup Grupo do job
   * @return true se removido com sucesso
   */
  boolean deleteJob(String jobName, String jobGroup);

  /**
   * Dispara um job para execução imediata.
   *
   * @param jobName  Nome do job
   * @param jobGroup Grupo do job
   * @return true se disparado com sucesso
   */
  boolean triggerJob(String jobName, String jobGroup);

  /**
   * Lista os triggers de um job.
   *
   * @param jobName  Nome do job
   * @param jobGroup Grupo do job
   * @return lista de triggers
   */
  List<QuartzJobTriggerDTO> findTriggersOfJob(String jobName,
                                              String jobGroup);

  /**
   * Lista os jobs em execução.
   *
   * @return lista de jobs em execução
   */
  List<QuartzJobInstanceDTO> findCurrentlyExecutingJobs();

  /**
   * Pausa um trigger.
   *
   * @param triggerName  Nome do trigger
   * @param triggerGroup Grupo do trigger
   * @return true se pausado com sucesso
   */
  boolean pauseTrigger(String triggerName, String triggerGroup);

  /**
   * Resume um trigger.
   *
   * @param triggerName  Nome do trigger
   * @param triggerGroup Grupo do trigger
   * @return true se resumido com sucesso
   */
  boolean resumeTrigger(String triggerName, String triggerGroup);
}
