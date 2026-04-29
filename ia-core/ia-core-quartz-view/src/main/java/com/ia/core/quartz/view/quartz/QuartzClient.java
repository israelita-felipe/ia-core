package com.ia.core.quartz.view.quartz;

import com.ia.core.quartz.service.model.job.dto.QuartzJobDTO;
import com.ia.core.quartz.service.model.job.dto.QuartzJobInstanceDTO;
import com.ia.core.quartz.service.model.job.dto.QuartzJobTriggerDTO;
import com.ia.core.quartz.service.model.scheduler.dto.SchedulerConfigDTO;
import com.ia.core.view.client.DefaultBaseClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

/**
 * Cliente Feign unificado para operações do Quartz (SchedulerConfig e Jobs).
 *
 * @author Israel Araújo
 */

@FeignClient(name = QuartzClient.NOME, url = QuartzClient.URL)
public interface QuartzClient
  extends DefaultBaseClient<SchedulerConfigDTO> {

  /**
   * Nome do cliente.
   */
  public static final String NOME = "quartz";
  /**
   * URL do cliente.
   */
  public static final String URL = "${feign.host}/api/${api.version}/${feign.url.quartz}";

  /**
   * Retorna as classes de tarefas disponíveis.
   *
   * @return Mapa com as classes de tarefas disponíveis
   */
  @GetMapping("/job-classes")
  public Map<String, String> getAvaliableJobClasses();

  // ========================================================================
  // Métodos de Jobs (antes em QuartzJobClient)
  // ========================================================================

  /**
   * Lista todos os jobs do scheduler.
   *
   * @return Lista de jobs
   */
  @GetMapping("/jobs")
  List<QuartzJobDTO> findAllJobs();

  /**
   * @return o número de todos o jobs
   */
  @GetMapping("/jobs/count")
  int countJobs();

  /**
   * Busca um job específico.
   *
   * @param jobName  Nome do job
   * @param jobGroup Grupo do job
   * @return Job encontrado
   */
  @GetMapping("/jobs/{jobGroup}/{jobName}")
  QuartzJobDTO findJob(@PathVariable("jobName") String jobName,
                       @PathVariable("jobGroup") String jobGroup);

  /**
   * Pausa um job.
   *
   * @param jobName  Nome do job
   * @param jobGroup Grupo do job
   * @return true se pausado com sucesso
   */
  @PostMapping("/jobs/{jobGroup}/{jobName}/pause")
  Boolean pauseJob(@PathVariable("jobName") String jobName,
                   @PathVariable("jobGroup") String jobGroup);

  /**
   * Resume um job pausado.
   *
   * @param jobName  Nome do job
   * @param jobGroup Grupo do job
   * @return true se resumido com sucesso
   */
  @PostMapping("/jobs/{jobGroup}/{jobName}/resume")
  Boolean resumeJob(@PathVariable("jobName") String jobName,
                    @PathVariable("jobGroup") String jobGroup);

  /**
   * Remove um job.
   *
   * @param jobName  Nome do job
   * @param jobGroup Grupo do job
   * @return true se removido com sucesso
   */
  @DeleteMapping("/jobs/{jobGroup}/{jobName}")
  Boolean deleteJob(@PathVariable("jobName") String jobName,
                    @PathVariable("jobGroup") String jobGroup);

  /**
   * Dispara um job para execução imediata.
   *
   * @param jobName  Nome do job
   * @param jobGroup Grupo do job
   * @return true se disparado com sucesso
   */
  @PostMapping("/jobs/{jobGroup}/{jobName}/trigger")
  Boolean triggerJob(@PathVariable("jobName") String jobName,
                     @PathVariable("jobGroup") String jobGroup);

  /**
   * Lista os triggers de um job.
   *
   * @param jobName  Nome do job
   * @param jobGroup Grupo do job
   * @return Lista de triggers
   */
  @GetMapping("/jobs/{jobGroup}/{jobName}/triggers")
  List<QuartzJobTriggerDTO> findTriggersOfJob(@PathVariable("jobName") String jobName,
                                              @PathVariable("jobGroup") String jobGroup);

  /**
   * Lista os jobs em execução.
   *
   * @return Lista de jobs em execução
   */
  @GetMapping("/jobs/executing")
  List<QuartzJobInstanceDTO> findCurrentlyExecutingJobs();

  /**
   * Pausa um trigger.
   *
   * @param triggerName  Nome do trigger
   * @param triggerGroup Grupo do trigger
   * @return true se pausado com sucesso
   */
  @PostMapping("/triggers/{triggerGroup}/{triggerName}/pause")
  Boolean pauseTrigger(@PathVariable("triggerName") String triggerName,
                       @PathVariable("triggerGroup") String triggerGroup);

  /**
   * Resume um trigger.
   *
   * @param triggerName  Nome do trigger
   * @param triggerGroup Grupo do trigger
   * @return true se resumido com sucesso
   */
  @PostMapping("/triggers/{triggerGroup}/{triggerName}/resume")
  Boolean resumeTrigger(@PathVariable("triggerName") String triggerName,
                        @PathVariable("triggerGroup") String triggerGroup);

}
