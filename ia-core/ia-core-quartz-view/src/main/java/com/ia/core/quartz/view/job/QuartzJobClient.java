package com.ia.core.quartz.view.job;

import com.ia.core.quartz.service.model.job.dto.QuartzJobDTO;
import com.ia.core.quartz.service.model.job.dto.QuartzJobInstanceDTO;
import com.ia.core.quartz.service.model.job.dto.QuartzJobTriggerDTO;
import com.ia.core.view.client.DefaultBaseClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

/**
 * Cliente Feign para operações de Jobs do Quartz.
 *
 * @author Israel Araújo
 */
@FeignClient(name = QuartzJobClient.NOME, url = QuartzJobClient.URL)
public interface QuartzJobClient
  extends DefaultBaseClient<QuartzJobDTO> {

  /**
   * Nome do cliente.
   */
  String NOME = "quartz-job";

  /**
   * URL do cliente.
   */
  String URL = "${feign.host}/api/${api.version}/${feign.url.quartz-job}";

  /**
   * Lista todos os jobs do scheduler.
   *
   * @return Lista de jobs
   */
  @GetMapping
  List<QuartzJobDTO> findAllJobs();

  /**
   * Busca um job específico.
   *
   * @param jobName  Nome do job
   * @param jobGroup Grupo do job
   * @return Job encontrado
   */
  @GetMapping("/{jobGroup}/{jobName}")
  QuartzJobDTO findJob(@PathVariable("jobName") String jobName,
                       @PathVariable("jobGroup") String jobGroup);

  /**
   * Pausa um job.
   *
   * @param jobName  Nome do job
   * @param jobGroup Grupo do job
   * @return true se pausado com sucesso
   */
  @PostMapping("/{jobGroup}/{jobName}/pause")
  Boolean pauseJob(@PathVariable("jobName") String jobName,
                   @PathVariable("jobGroup") String jobGroup);

  /**
   * Resume um job pausado.
   *
   * @param jobName  Nome do job
   * @param jobGroup Grupo do job
   * @return true se resumido com sucesso
   */
  @PostMapping("/{jobGroup}/{jobName}/resume")
  Boolean resumeJob(@PathVariable("jobName") String jobName,
                    @PathVariable("jobGroup") String jobGroup);

  /**
   * Remove um job.
   *
   * @param jobName  Nome do job
   * @param jobGroup Grupo do job
   * @return true se removido com sucesso
   */
  @DeleteMapping("/{jobGroup}/{jobName}")
  Boolean deleteJob(@PathVariable("jobName") String jobName,
                    @PathVariable("jobGroup") String jobGroup);

  /**
   * Dispara um job para execução imediata.
   *
   * @param jobName  Nome do job
   * @param jobGroup Grupo do job
   * @return true se disparado com sucesso
   */
  @PostMapping("/{jobGroup}/{jobName}/trigger")
  Boolean triggerJob(@PathVariable("jobName") String jobName,
                     @PathVariable("jobGroup") String jobGroup);

  /**
   * Lista os triggers de um job.
   *
   * @param jobName  Nome do job
   * @param jobGroup Grupo do job
   * @return Lista de triggers
   */
  @GetMapping("/{jobGroup}/{jobName}/triggers")
  List<QuartzJobTriggerDTO> findTriggersOfJob(@PathVariable("jobName") String jobName,
                                              @PathVariable("jobGroup") String jobGroup);

  /**
   * Lista os jobs em execução.
   *
   * @return Lista de jobs em execução
   */
  @GetMapping("/executing")
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
