package com.ia.core.quartz.service.model.scheduler;

import java.util.List;

import com.ia.core.service.usecase.CrudUseCase;

/**
 * Interface de Use Case para Scheduler (Agendamento).
 * <p>
 * Define as operações específicas do domínio de agendamento de jobs
 * conforme definido no caso de uso Manter-Scheduler.
 *
 * @author Israel Araújo
 */
public interface SchedulerUseCase extends CrudUseCase<SchedulerConfigDTO> {

  /**
   * Inicia todos os jobs ativos.
   */
  void iniciarJobs();

  /**
   * Para todos os jobs.
   */
  void pararJobs();

  /**
   * Pausa um job específico.
   *
   * @param jobId ID do job
   */
  void pausarJob(Long jobId);

  /**
   * Resume um job específico.
   *
   * @param jobId ID do job
   */
  void resumirJob(Long jobId);

  /**
   * Executa um job imediatamente.
   *
   * @param jobId ID do job
   */
  void executarJob(Long jobId);

  /**
   * Cancela um job.
   *
   * @param jobId ID do job
   */
  void cancelarJob(Long jobId);

  /**
   * Busca jobs ativos.
   *
   * @return lista de jobs ativos
   */
  List<SchedulerConfigDTO> findAtivos();

  /**
   * Verifica atualizações nos jobs.
   */
  void verificarAtualizacoes();
}
