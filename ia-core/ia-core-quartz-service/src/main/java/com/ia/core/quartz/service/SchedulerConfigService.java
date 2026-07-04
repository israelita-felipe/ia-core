package com.ia.core.quartz.service;

import com.ia.core.quartz.model.scheduler.SchedulerConfig;
import com.ia.core.quartz.service.model.scheduler.SchedulerUseCase;
import com.ia.core.quartz.service.model.scheduler.dto.SchedulerConfigDTO;
import com.ia.core.quartz.service.model.scheduler.dto.SchedulerConfigTranslator;
import com.ia.core.resilience4j.annotation.Resilient;
import com.ia.core.resilience4j.profile.ResilienceProfile;
import com.ia.core.security.service.CrudSecuredBaseService;
import com.ia.core.service.annotations.TransactionalReadOnly;
import com.ia.core.service.annotations.TransactionalWrite;
import com.ia.core.service.dto.request.SearchRequestDTO;
import com.ia.core.service.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobListener;
import org.quartz.SchedulerException;
import org.quartz.TriggerListener;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Serviço para gerenciamento de configurações do agendador Quartz. Responsável
 * por criar, agendar, atualizar e cancelar jobs dinamicamente.
 * <p>
 * Principais funcionalidades:
 * <ul>
 * <li>Agendamento automático de jobs ao inicializar a aplicação</li>
 * <li>Gerenciamento de listeners para jobs e triggers</li>
 * <li>Operações CRUD com sincronização automática com o scheduler Quartz</li>
 * <li>Descoberta dinâmica de classes de job disponíveis</li>
 * </ul>
 *
 * @author Israel Araújo
 * @see AbstractJob
 * @see SchedulerConfig
 * @see SchedulerConfigDTO
 */
@Slf4j
@Service
public class SchedulerConfigService
  extends CrudSecuredBaseService<SchedulerConfig, SchedulerConfigDTO>
  implements SchedulerUseCase {

  private final SchedulerListenerService listenerService;
  private final SchedulerJobDiscoveryService jobDiscoveryService;
  private final SchedulerJobSchedulingService schedulingService;
  private final SchedulerJobControlService jobControlService;
  private final SchedulerJobManagementService jobManagementService;

  /**
   * Construtor do serviço de configuração do scheduler.
   *
   * @param config Configuração do serviço contendo dependências necessárias
   */
  public SchedulerConfigService(SchedulerConfigServiceConfig config,
                                SchedulerListenerService listenerService,
                                SchedulerJobDiscoveryService jobDiscoveryService,
                                SchedulerJobSchedulingService schedulingService,
                                SchedulerJobControlService jobControlService,
                                SchedulerJobManagementService jobManagementService) {
    super(config);
    this.listenerService = listenerService;
    this.jobDiscoveryService = jobDiscoveryService;
    this.schedulingService = schedulingService;
    this.jobControlService = jobControlService;
    this.jobManagementService = jobManagementService;
  }



  /**
   * Cria e registra os listeners para jobs e triggers no scheduler Quartz.
   */
  public void criarListeners() {
    listenerService.criarListeners(getConfig().getQuartzScheduler());
  }

  /**
   * Cria uma instância do listener de jobs.
   *
   * @return Instância de JobListener configurada
   */
  public JobListener criarJobListener() {
    return listenerService.criarJobListener();
  }

  /**
   * Cria uma instância do listener de triggers.
   *
   * @return Instância de TriggerListener configurada
   */
  public TriggerListener criarTriggerListener() {
    return listenerService.criarTriggerListener();
  }

  /**
   * Descobre dinamicamente todas as classes de job disponíveis no pacote
   * "com.ia".
   * <p>
   * Utiliza reflections para encontrar todas as classes que estendem
   * AbstractJob e retorna um mapa com as classes e suas traduções.
   *
   * @return Mapa onde a chave é a classe do job e o valor é sua tradução
   */
  public Map<Class<? extends AbstractJob>, String> getAvaliableJobClasses() {
    return jobDiscoveryService.getAvailableJobClasses();
  }

    /**
     * Busca uma configuração de scheduler pelo ID, incluindo informações dos
     * triggers.
     *
     * @param id ID da configuração
     * @return DTO da configuração com triggers preenchidos
     */
    @TransactionalReadOnly
    @Override
    public SchedulerConfigDTO find(Long id) {
        SchedulerConfigDTO schedulerConfigDTO = super.find(id);
        schedulingService.processarTriggers(schedulerConfigDTO);
        return schedulerConfigDTO;
    }

  /**
   * Busca todas as configurações de scheduler, incluindo informações dos
   * triggers.
   *
   * @param requestDTO Critérios de busca
   * @return Página de DTOs com triggers preenchidos
   */
  @TransactionalReadOnly
  @Override
  public Page<SchedulerConfigDTO> findAll(SearchRequestDTO requestDTO) {
    Page<SchedulerConfigDTO> all = super.findAll(requestDTO);
    all.forEach(schedulingService::processarTriggers);
    return all;
  }

  /**
   * Salva uma configuração de scheduler e sincroniza com o Quartz.
   * <p>
   * Se for uma nova configuração ativa, agenda o job. Se for uma configuração
   * existente, atualiza ou cancela conforme a periodicidade.
   *
   * @param toSave DTO com os dados a serem salvos
   * @return DTO salvo
   * @throws ServiceException se ocorrer erro durante o agendamento
   */
  @TransactionalWrite
  @Override
  public SchedulerConfigDTO save(SchedulerConfigDTO toSave)
    throws ServiceException {
    ServiceException ex = new ServiceException();
    SchedulerConfigDTO salvo = null;
    try {
      boolean isNovo = toSave.getId() == null;
      salvo = super.save(toSave);

      if (isNovo && salvo.getPeriodicidade().getAtivo()) {
        schedulingService.agendarJob(salvo);
        log.info("Novo job agendado com ID: {}", salvo.getId());
      } else if (!isNovo) {
        if (salvo.getPeriodicidade().getAtivo()) {
          schedulingService.atualizarJob(salvo);
          log.info("Job atualizado com ID: {}", salvo.getId());
        } else {
          schedulingService.cancelarJob(salvo);
          log.info("Job cancelado com ID: {}", salvo.getId());
        }
      }
    } catch (Exception e) {
      ex.add(e);
      log.error("Erro ao sincronizar job com scheduler para configuração {}: {}",
                toSave, e.getLocalizedMessage());
    }
    throwIfHasErrors(ex);
    return salvo;
  }

  /**
   * Exclui uma configuração de scheduler e cancela o job correspondente se
   * estiver ativo.
   *
   * @param id ID da configuração a ser excluída
   * @throws ServiceException se ocorrer erro durante o cancelamento
   */
  @TransactionalWrite
  @Override
  public void delete(Long id)
    throws ServiceException {
    ServiceException ex = new ServiceException();
    try {
      SchedulerConfigDTO schedulerConfigDTO = find(id);
      super.delete(id);

      if (schedulerConfigDTO != null) {
        schedulingService.cancelarJob(schedulerConfigDTO);
        log.info("Job cancelado durante exclusão da configuração: {}", id);
      }
    } catch (Exception e) {
      ex.add(e);
    }
    throwIfHasErrors(ex);
  }

  @Override
  public SchedulerConfigServiceConfig getConfig() {
    return (SchedulerConfigServiceConfig) super.getConfig();
  }

  @Override
  public String getFunctionalityTypeName() {
    return SchedulerConfigTranslator.SCHEDULER_CONFIG;
  }

  // Métodos do UseCase - delegam para SchedulerJobManagementService

  /**
   * Inicia todos os jobs ativos.
   */
  @Override
  public void iniciarJobs() {
    jobManagementService.iniciarJobs();
  }

  /**
   * Busca jobs ativos.
   *
   * @return lista de jobs ativos
   */
  @Override
  @Tool(description = "Busca todas as configurações de scheduler ativas no sistema. " +
             "Retorna uma lista de configurações de agendamento que estão atualmente habilitadas para execução. " +
             "Cada configuração inclui informações sobre o job, periodicidade, triggers e estado atual. " +
             "Útil para visualizar quais jobs estão programados para execução automática.")
  @Resilient(ResilienceProfile.DATABASE)
  public List<SchedulerConfigDTO> findAtivos() {
    return findAllActive(true);
  }

  /**
   * Verifica atualizações nos jobs.
   */
  @Override
  public void verificarAtualizacoes() {
    jobManagementService.verificarAtualizacoes();
  }

  /**
   * Agenda um job individual no scheduler Quartz.
   *
   * @param config Configuração do job a ser agendado
   * @throws SchedulerException     se ocorrer erro no scheduler
   * @throws ClassNotFoundException se a classe do job não for encontrada
   */
  @TransactionalWrite
  public void agendarJob(SchedulerConfigDTO config)
    throws SchedulerException, ClassNotFoundException {
    jobManagementService.agendarJob(config);
  }

  /**
   * Cancela um job individual no scheduler Quartz.
   *
   * @param config Configuração do job a ser cancelado
   */
  public void cancelarJob(SchedulerConfigDTO config) {
    jobManagementService.cancelarJob(config);
  }

  /**
   * Busca todas as configurações filtradas por status ativo/inativo.
   *
   * @param active true para ativas, false para inativas
   * @return Lista de DTOs filtrados
   */
  @TransactionalReadOnly
  public List<SchedulerConfigDTO> findAllActive(boolean active) {
    return getConfig().getRepository()
        .findAllActiveWithPeriodicidade(active).stream().map(this::toDTO)
        .toList();
  }
}
