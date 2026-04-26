package com.ia.core.quartz.service.model.scheduler;

import com.ia.core.quartz.service.model.scheduler.dto.SchedulerConfigDTO;
import com.ia.core.service.usecase.CrudUseCase;

import java.util.List;

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
