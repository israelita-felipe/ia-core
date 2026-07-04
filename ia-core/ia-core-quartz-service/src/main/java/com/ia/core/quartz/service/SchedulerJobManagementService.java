package com.ia.core.quartz.service;

import com.ia.core.quartz.service.model.scheduler.dto.SchedulerConfigDTO;
import com.ia.core.service.annotations.TransactionalWrite;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.SchedulerException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * Serviço para gerenciamento do ciclo de vida de jobs no scheduler Quartz.
 * <p>
 * Responsável por operações de início, atualização e cancelamento de jobs,
 * incluindo o agendamento automático de jobs ativos na inicialização.
 * </p>
 *
 * @author Israel Araújo
 * @see SchedulerJobSchedulingService
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SchedulerJobManagementService {

  private final SchedulerJobSchedulingService schedulingService;
  private final SchedulerConfigServiceConfig config;

  /**
   * Inicia todos os jobs ativos do sistema.
   * <p>
   * Busca todas as configurações de scheduler ativas e agenda
   * os jobs correspondentes no Quartz.
   * </p>
   */
  @TransactionalWrite
  public void iniciarJobs() {
    try {
      List<SchedulerConfigDTO> configs = findAllActive(true);
      log.info("Iniciando agendamento de {} jobs ativos", configs.size());
      schedulingService.agendarJobs(configs);
      log.info("Agendamento de jobs concluído com sucesso");
    } catch (Exception e) {
      log.error("Erro durante o agendamento automático de jobs: {}",
                e.getLocalizedMessage(), e);
    }
  }

  /**
   * Verifica atualizações nos jobs ativos.
   * <p>
   * Compara a periodicidade configurada com a do scheduler e
   * atualiza jobs que sofreram alterações.
   * </p>
   */
  public void verificarAtualizacoes() {
    try {
      List<SchedulerConfigDTO> configs = findAllActive(true);
      log.info("Verificando atualizações para {} jobs ativos", configs.size());
      schedulingService.updateJobs(configs);
    } catch (Exception e) {
      log.error("Erro durante a atualização de jobs: {}", e.getLocalizedMessage(), e);
    }
  }

  /**
   * Agenda um job individual no scheduler Quartz.
   *
   * @param config Configuração do job a ser agendado
   * @throws SchedulerException     se ocorrer erro no scheduler
   * @throws ClassNotFoundException se a classe do job não for encontrada
   */
  public void agendarJob(SchedulerConfigDTO config)
    throws SchedulerException, ClassNotFoundException {
    Objects.requireNonNull(config, "config não pode ser null");
    schedulingService.agendarJob(config);
  }

  /**
   * Atualiza todos os jobs ativos verificando mudanças na periodicidade.
   */
  public void updateJobs() {
    try {
      List<SchedulerConfigDTO> configs = findAllActive(true);
      schedulingService.updateJobs(configs);
    } catch (Exception e) {
      log.error("Erro durante a atualização de jobs: {}", e.getLocalizedMessage(), e);
    }
  }

  /**
   * Cancela todos os jobs do scheduler Quartz.
   */
  public void cancelAllJobs() {
    try {
      config.getQuartzScheduler().clear();
      log.info("Todos os jobs agendados foram cancelados");
    } catch (SchedulerException e) {
      log.error("Erro ao cancelar todos os jobs: {}",
                e.getLocalizedMessage(), e);
    }
  }

  /**
   * Cancela um job individual no scheduler Quartz.
   *
   * @param schedulerConfigDTO Configuração do job a ser cancelado
   */
  public void cancelarJob(SchedulerConfigDTO schedulerConfigDTO) {
    Objects.requireNonNull(schedulerConfigDTO, "schedulerConfigDTO não pode ser null");
    try {
      schedulingService.cancelarJob(schedulerConfigDTO);
      log.info("Job cancelado: {}", schedulerConfigDTO.getId());
    } catch (SchedulerException e) {
      log.error("Erro ao cancelar o job {}: {}", schedulerConfigDTO.getId(),
                e.getLocalizedMessage(), e);
    }
  }

  /**
   * Busca todas as configurações filtradas por status ativo/inativo.
   *
   * @param active true para ativas, false para inativas
   * @return Lista de DTOs filtrados
   */
  private List<SchedulerConfigDTO> findAllActive(boolean active) {
    return config.getRepository()
        .findAllActiveWithPeriodicidade(active).stream()
        .map(this.config.getMapper()::toDTO)
        .toList();
  }

}
