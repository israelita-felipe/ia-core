package com.ia.core.quartz.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobKey;
import org.quartz.JobListener;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.quartz.TriggerListener;
import org.quartz.impl.matchers.GroupMatcher;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.ia.core.quartz.model.scheduler.SchedulerConfig;
import com.ia.core.quartz.service.model.job.QuartzJobDTO;
import com.ia.core.quartz.service.model.job.QuartzJobInstanceDTO;
import com.ia.core.quartz.service.model.job.QuartzJobTriggerDTO;
import com.ia.core.quartz.service.model.job.QuartzJobUseCase;
import com.ia.core.quartz.service.model.periodicidade.dto.PeriodicidadeDTO;
import com.ia.core.quartz.service.model.scheduler.SchedulerConfigDTO;
import com.ia.core.quartz.service.model.scheduler.SchedulerConfigTranslator;
import com.ia.core.quartz.service.model.scheduler.SchedulerUseCase;
import com.ia.core.quartz.service.model.scheduler.triggers.SchedulerConfigTriggerDTO;
import com.ia.core.security.service.DefaultSecuredBaseService;
import com.ia.core.service.dto.request.SearchRequestDTO;
import com.ia.core.service.exception.ServiceException;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

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
  extends DefaultSecuredBaseService<SchedulerConfig, SchedulerConfigDTO>
  implements SchedulerUseCase, QuartzJobUseCase {

  /**
   * Construtor do serviço de configuração do scheduler.
   *
   * @param config Configuração do serviço contendo dependências necessárias
   */
  public SchedulerConfigService(SchedulerConfigServiceConfig config) {
    super(config);
  }

  /**
   * Inicializa o serviço após construção do bean. Configura listeners e agenda
   * jobs ativos.
   */
  @PostConstruct
  @Override
  public void baseServiceInit() {
    super.baseServiceInit();
    criarListeners();
    agendarJobs();
  }

  /**
   * Cria e registra os listeners para jobs e triggers no scheduler Quartz.
   */
  public void criarListeners() {
    try {
      Scheduler scheduler = getConfig().getQuartzScheduler();
      scheduler.getListenerManager().addJobListener(criarJobListener());
      scheduler.getListenerManager()
          .addTriggerListener(criarTriggerListener());
      log.info("Listeners de job e trigger registrados com sucesso");
    } catch (SchedulerException e) {
      log.error("Erro ao registrar listeners no scheduler: {}",
                e.getLocalizedMessage(), e);
    }
  }

  /**
   * Cria uma instância do listener de jobs.
   *
   * @return Instância de JobListener configurada
   */
  public JobListener criarJobListener() {
    return new JobsListener();
  }

  /**
   * Cria uma instância do listener de triggers.
   *
   * @return Instância de TriggerListener configurada
   */
  public TriggerListener criarTriggerListener() {
    return new TriggersListener();
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
    Map<Class<? extends AbstractJob>, String> jobClasses = new HashMap<>();
    Reflections reflections = new Reflections("com.ia", Scanners.SubTypes);

    reflections.getSubTypesOf(AbstractJob.class).forEach(jobClass -> {
      String translation = getTranslator()
          .getTranslation(jobClass.getName());
      jobClasses.put(jobClass, translation);
      log.debug("Classe de job encontrada: {} -> {}",
                jobClass.getSimpleName(), translation);
    });

    log.info("Total de classes de job disponíveis: {}", jobClasses.size());
    return jobClasses;
  }

  /**
   * Busca uma configuração de scheduler pelo ID, incluindo informações dos
   * triggers.
   *
   * @param id ID da configuração
   * @return DTO da configuração com triggers preenchidos
   */
  @Override
  public SchedulerConfigDTO find(Long id) {
    return onTransaction(() -> {
      SchedulerConfigDTO schedulerConfigDTO = super.find(id);
      processarTriggers(schedulerConfigDTO);
      return schedulerConfigDTO;
    });
  }

  /**
   * Busca todas as configurações de scheduler, incluindo informações dos
   * triggers.
   *
   * @param requestDTO Critérios de busca
   * @return Página de DTOs com triggers preenchidos
   */
  @Override
  public Page<SchedulerConfigDTO> findAll(SearchRequestDTO requestDTO) {
    return onTransaction(() -> {
      Page<SchedulerConfigDTO> all = super.findAll(requestDTO);
      all.forEach(this::processarTriggers);
      return all;
    });
  }

  /**
   * Processa e preenche as informações dos triggers associados a uma
   * configuração.
   * <p>
   * Recupera informações do scheduler Quartz como próximo tempo de execução,
   * estado do trigger, prioridade, etc.
   *
   * @param schedulerConfigDTO DTO da configuração a ser preenchido
   */
  protected void processarTriggers(SchedulerConfigDTO schedulerConfigDTO) {
    try {
      Scheduler scheduler = getConfig().getQuartzScheduler();
      JobKey jobKey = JobKey.jobKey(obterNomeJob(schedulerConfigDTO),
                                    obterGrupo(schedulerConfigDTO));

      List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);

      for (Trigger trigger : triggers) {
        SchedulerConfigTriggerDTO triggerDTO = construirTriggerDTO(scheduler,
                                                                   trigger,
                                                                   schedulerConfigDTO
                                                                       .getPeriodicidade()
                                                                       .getZoneIdValue());
        schedulerConfigDTO.getTriggers().add(triggerDTO);
      }

      log.debug("Processados {} triggers para o job {}", triggers.size(),
                jobKey.getName());
    } catch (SchedulerException e) {
      log.error("Erro ao processar triggers para a configuração {}: {}",
                schedulerConfigDTO.getId(), e.getLocalizedMessage(), e);
    }
  }

  /**
   * Constrói um DTO de trigger a partir das informações do scheduler Quartz.
   *
   * @param scheduler Instância do scheduler Quartz
   * @param trigger   Trigger do Quartz
   * @return DTO preenchido com as informações do trigger
   * @throws SchedulerException Caso ocorra erro no scheduler
   */
  private SchedulerConfigTriggerDTO construirTriggerDTO(Scheduler scheduler,
                                                        Trigger trigger,
                                                        ZoneId zoneId)
    throws SchedulerException {
    SchedulerConfigTriggerDTO triggerDTO = SchedulerConfigTriggerDTO
        .builder().build();

    triggerDTO.setCalendarName(trigger.getCalendarName());
    triggerDTO.setDescription(trigger.getDescription());
    triggerDTO.setJobData(trigger.getJobDataMap());
    triggerDTO.setJobGroup(trigger.getJobKey().getGroup());
    triggerDTO.setJobName(trigger.getJobKey().getName());
    triggerDTO.setSchedulerName(scheduler.getSchedulerName());
    triggerDTO.setTriggerGroup(trigger.getKey().getGroup());
    triggerDTO.setTriggerName(trigger.getKey().getName());
    triggerDTO.setTriggerState(scheduler.getTriggerState(trigger.getKey())
        .name());
    triggerDTO.setTriggerType(trigger.getClass().getName());

    // Processar campos de data/hora
    processarCamposTemporais(triggerDTO, trigger, zoneId);

    // Processar campos numéricos
    processarCamposNumericos(triggerDTO, trigger);

    return triggerDTO;
  }

  /**
   * Processa e converte campos de data/hora do trigger para o DTO.
   *
   * @param triggerDTO {@link SchedulerConfigTriggerDTO}
   * @param trigger    {@link Trigger}
   */
  private void processarCamposTemporais(SchedulerConfigTriggerDTO triggerDTO,
                                        Trigger trigger, ZoneId zoneId) {

    if (trigger.getEndTime() != null) {
      triggerDTO.setEndTime(LocalDateTime
          .ofInstant(trigger.getEndTime().toInstant(), zoneId));
    }
    if (trigger.getNextFireTime() != null) {
      triggerDTO.setNextFireTime(LocalDateTime
          .ofInstant(trigger.getNextFireTime().toInstant(), zoneId));
    }
    if (trigger.getPreviousFireTime() != null) {
      triggerDTO.setPrevFireTime(LocalDateTime
          .ofInstant(trigger.getPreviousFireTime().toInstant(), zoneId));
    }
    if (trigger.getStartTime() != null) {
      triggerDTO.setStartTime(LocalDateTime
          .ofInstant(trigger.getStartTime().toInstant(), zoneId));
    }
  }

  /**
   * Processa e converte campos numéricos do trigger para o DTO.
   *
   * @param triggerDTO {@link SchedulerConfigTriggerDTO}
   * @param trigger    {@link Trigger}
   */
  private void processarCamposNumericos(SchedulerConfigTriggerDTO triggerDTO,
                                        Trigger trigger) {
    triggerDTO
        .setMisFireInstr(Long.valueOf(trigger.getMisfireInstruction()));
    triggerDTO.setPriority(Long.valueOf(trigger.getPriority()));
  }

  /**
   * Obtém o nome do job baseado no ID da configuração.
   *
   * @param schedulerConfigDTO DTO da configuração
   * @return Nome do job
   */
  protected String obterNomeJob(SchedulerConfigDTO schedulerConfigDTO) {
    return schedulerConfigDTO.getId().toString();
  }

  /**
   * Obtém o grupo do job/trigger.
   *
   * @param config DTO da configuração
   * @return Nome do grupo
   */
  protected String obterGrupo(SchedulerConfigDTO config) {
    return getGrupoPadrao();
  }

  /**
   * Obtém o grupo padrão para jobs e triggers.
   *
   * @return Nome do grupo padrão
   */
  protected String getGrupoPadrao() {
    return SchedulerConfigServiceConfig.DEFAULT_JOB_GROUP;
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
  @Override
  public SchedulerConfigDTO save(SchedulerConfigDTO toSave)
    throws ServiceException {
    ServiceException ex = new ServiceException();
    SchedulerConfigDTO savedEntity = onTransaction(() -> {
      SchedulerConfigDTO salvo = null;
      try {
        boolean isNovo = toSave.getId() == null;
        salvo = super.save(toSave);

        if (isNovo && salvo.getPeriodicidade().getAtivo()) {
          agendarJob(salvo);
          log.info("Novo job agendado com ID: {}", salvo.getId());
        } else if (!isNovo) {
          if (salvo.getPeriodicidade().getAtivo()) {
            atualizarJob(salvo);
            log.info("Job atualizado com ID: {}", salvo.getId());
          } else {
            cancelarJob(salvo);
            log.info("Job cancelado com ID: {}", salvo.getId());
          }
        }
      } catch (Exception e) {
        ex.add(e);
        log.error("Erro ao sincronizar job com scheduler para configuração {}: {}",
                  toSave, e.getLocalizedMessage());
      }
      return salvo;
    });
    checkErrors(ex);
    return savedEntity;
  }

  /**
   * Exclui uma configuração de scheduler e cancela o job correspondente se
   * estiver ativo.
   *
   * @param id ID da configuração a ser excluída
   * @throws ServiceException se ocorrer erro durante o cancelamento
   */
  @Override
  public void delete(Long id)
    throws ServiceException {
    ServiceException ex = new ServiceException();
    onTransaction(() -> {
      try {
        SchedulerConfigDTO schedulerConfigDTO = find(id);
        super.delete(id);

        if (schedulerConfigDTO != null) {
          cancelarJob(schedulerConfigDTO);
          log.info("Job cancelado durante exclusão da configuração: {}",
                   id);
        }
      } catch (Exception e) {
        ex.add(e);
      }
      return id;
    });
    checkErrors(ex);
  }

  @Override
  public SchedulerConfigServiceConfig getConfig() {
    return (SchedulerConfigServiceConfig) super.getConfig();
  }

  @Override
  public String getFunctionalityTypeName() {
    return SchedulerConfigTranslator.SCHEDULER_CONFIG;
  }

  // Métodos do UseCase - delegam para métodos existentes

  /**
   * Inicia todos os jobs ativos. Delegado para {@link #agendarJobs()}.
   */
  @Override
  public void iniciarJobs() {
    agendarJobs();
  }

  /**
   * Busca jobs ativos. Delegado para {@link #findAllActive(boolean)}.
   *
   * @return lista de jobs ativos
   */
  @Override
  public List<SchedulerConfigDTO> findAtivos() {
    return findAllActive(true);
  }

  /**
   * Verifica atualizações nos jobs. Delegado para {@link #updateJobs()}.
   */
  @Override
  public void verificarAtualizacoes() {
    updateJobs();
  }

  /**
   * Busca todas as configurações filtradas por status ativo/inativo. Usa
   * EntityGraph para evitar N+1 queries ao carregar periodicidade.
   *
   * @param active true para ativas, false para inativas
   * @return Lista de DTOs filtrados
   */
  public List<SchedulerConfigDTO> findAllActive(boolean active) {
    return onTransaction(() -> getConfig().getRepository()
        .findAllActiveWithPeriodicidade(active).stream()
        .map(getMapper()::toDTO).toList());
  }

  /**
   * Agenda todos os jobs ativos ao inicializar a aplicação.
   */
  public void agendarJobs() {
    try {
      List<SchedulerConfigDTO> configs = findAllActive(true);
      log.info("Iniciando agendamento de {} jobs ativos", configs.size());

      for (SchedulerConfigDTO config : configs) {
        agendarJob(config);
      }

      log.info("Agendamento de jobs concluído com sucesso");
    } catch (SchedulerException | ClassNotFoundException e) {
      log.error("Erro durante o agendamento automático de jobs: {}",
                e.getLocalizedMessage(), e);
    }
  }

  /**
   * Agenda um job individual no scheduler Quartz.
   *
   * @param config Configuração do job a ser agendado
   * @throws SchedulerException     se ocorrer erro no scheduler
   * @throws ClassNotFoundException se a classe do job não for encontrada
   */
  private void agendarJob(SchedulerConfigDTO config)
    throws SchedulerException, ClassNotFoundException {
    JobDetail jobDetail = criarDetalhesJob(config);
    Trigger trigger = criarTrigger(config);
    TriggerKey triggerKey = trigger.getKey();

    if (getConfig().getQuartzScheduler().checkExists(triggerKey)) {
      log.warn("Trigger já existe para o job {}. Ignorando agendamento.",
               config.getId());
    } else {
      getConfig().getQuartzScheduler().scheduleJob(jobDetail, trigger);
      log.info("Job agendado com ID: {}", config.getId());
    }
  }

  /**
   * Cria os detalhes do job para o scheduler Quartz.
   *
   * @param config Configuração do job
   * @return JobDetail configurado
   * @throws ClassNotFoundException se a classe do job não for encontrada
   */
  @SuppressWarnings("unchecked")
  protected JobDetail criarDetalhesJob(SchedulerConfigDTO config)
    throws ClassNotFoundException {
    return JobBuilder
        .newJob((Class<? extends Job>) config.getJobClassNameAsClass())
        .withIdentity(obterNomeJob(config), obterGrupo(config)).build();
  }

  /**
   * Cria um trigger para o job baseado na periodicidade configurada.
   *
   * @param config Configuração do job
   * @return Trigger configurado
   */
  protected Trigger criarTrigger(SchedulerConfigDTO config) {
    PeriodicidadeTrigger trigger = new PeriodicidadeTrigger(config
        .getPeriodicidade());
    trigger.setKey(obterChaveTrigger(config));
    return trigger;
  }

  /**
   * Obtém o nome do trigger baseado no ID da configuração.
   *
   * @param config Configuração do job
   * @return Nome do trigger
   */
  protected String obterNomeTrigger(SchedulerConfigDTO config) {
    return config.getId() + obterSufixoTrigger();
  }

  /**
   * Obtém o sufixo padrão para nomes de triggers.
   *
   * @return Sufixo do trigger
   */
  protected String obterSufixoTrigger() {
    return SchedulerConfigServiceConfig.TRIGGER_SUFIX;
  }

  /**
   * Atualiza todos os jobs ativos verificando mudanças na periodicidade.
   */
  public void updateJobs() {
    try {
      List<SchedulerConfigDTO> configs = findAllActive(true);
      log.info("Verificando atualizações para {} jobs ativos",
               configs.size());

      for (SchedulerConfigDTO config : configs) {
        TriggerKey triggerKey = obterChaveTrigger(config);

        if (getConfig().getQuartzScheduler().checkExists(triggerKey)) {
          if (verificarMudancaPeriodicidade(config, triggerKey)) {
            atualizarJob(config);
          }
        } else {
          agendarJob(config);
        }
      }
    } catch (SchedulerException | ClassNotFoundException e) {
      log.error("Erro durante a atualização de jobs: {}",
                e.getLocalizedMessage(), e);
    }
  }

  /**
   * Verifica se houve mudança na periodicidade que necessite atualização do
   * trigger.
   *
   * @param config     {@link SchedulerConfigDTO}
   * @param triggerKey {@link TriggerKey}
   * @return se houve mudanças na periodicidade
   * @throws SchedulerException caso ocorra erro de scheduler
   */
  private boolean verificarMudancaPeriodicidade(SchedulerConfigDTO config,
                                                TriggerKey triggerKey)
    throws SchedulerException {
    PeriodicidadeDTO novaExpressaoCron = config.getPeriodicidade();
    PeriodicidadeTrigger triggerAntigo = (PeriodicidadeTrigger) getConfig()
        .getQuartzScheduler().getTrigger(triggerKey);
    PeriodicidadeDTO expressaoCronAntiga = triggerAntigo.getPeriodicidade();

    if (expressaoCronAntiga.compareTo(novaExpressaoCron) != 0) {
      log.info("Detectada mudança na periodicidade do job {}. Atualizando...",
               config.getId());
      return true;
    }
    return false;
  }

  /**
   * Obtém a chave do trigger para uma configuração.
   *
   * @param config Configuração do job
   * @return Chave do trigger
   */
  protected TriggerKey obterChaveTrigger(SchedulerConfigDTO config) {
    return TriggerKey.triggerKey(obterNomeTrigger(config),
                                 obterGrupo(config));
  }

  /**
   * Atualiza um job individual no scheduler Quartz.
   *
   * @param config Configuração atualizada do job
   * @throws SchedulerException     se ocorrer erro no scheduler
   * @throws ClassNotFoundException caso a classe do job não seja encontrada
   */
  private void atualizarJob(SchedulerConfigDTO config)
    throws SchedulerException, ClassNotFoundException {
    Trigger novoTrigger = criarTrigger(config);
    if (getConfig().getQuartzScheduler()
        .checkExists(novoTrigger.getKey())) {
      getConfig().getQuartzScheduler().rescheduleJob(novoTrigger.getKey(),
                                                     novoTrigger);
      log.info("Job reagenado com nova periodicidade: {}", config.getId());
    } else {
      agendarJob(config);
    }
  }

  /**
   * Cancela todos os jobs do scheduler Quartz.
   */
  public void cancelAllJobs() {
    try {
      getConfig().getQuartzScheduler().clear();
      log.info("Todos os jobs agendados foram cancelados");
    } catch (SchedulerException e) {
      log.error("Erro ao cancelar todos os jobs: {}",
                e.getLocalizedMessage(), e);
    }
  }

  /**
   * Cancela um job individual no scheduler Quartz.
   *
   * @param config Configuração do job a ser cancelado
   */
  public void cancelarJob(SchedulerConfigDTO config) {
    try {
      JobKey jobKey = JobKey.jobKey(obterNomeJob(config),
                                    obterGrupo(config));
      getConfig().getQuartzScheduler().deleteJob(jobKey);
      log.info("Job cancelado: {}", config.getId());
    } catch (SchedulerException e) {
      log.error("Erro ao cancelar o job {}: {}", config.getId(),
                e.getLocalizedMessage(), e);
    }
  }

  // ========================================================================
  // Métodos da interface QuartzJobUseCase
  // ========================================================================

  @Override
  public List<QuartzJobDTO> findAllJobs() {
    List<QuartzJobDTO> jobs = new ArrayList<>();
    try {
      Scheduler scheduler = getConfig().getQuartzScheduler();
      List<String> jobGroups = scheduler.getJobGroupNames();

      for (String group : jobGroups) {
        Set<JobKey> jobKeys = scheduler
            .getJobKeys(GroupMatcher.jobGroupEquals(group));

        for (JobKey jobKey : jobKeys) {
          try {
            JobDetail jobDetail = scheduler.getJobDetail(jobKey);
            if (jobDetail != null) {
              QuartzJobDTO dto = buildJobDTO(jobDetail, scheduler);
              jobs.add(dto);
            }
          } catch (SchedulerException e) {
            log.error("Erro ao buscar detalhes do job {}: {}", jobKey,
                      e.getMessage());
          }
        }
      }
      log.info("Encontrados {} jobs no scheduler", jobs.size());
    } catch (SchedulerException e) {
      log.error("Erro ao listar jobs: {}", e.getMessage(), e);
    }
    return jobs;
  }

  @Override
  public QuartzJobDTO findJob(String jobName, String jobGroup) {
    try {
      Scheduler scheduler = getConfig().getQuartzScheduler();
      JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
      JobDetail jobDetail = scheduler.getJobDetail(jobKey);

      if (jobDetail != null) {
        return buildJobDTO(jobDetail, scheduler);
      }
    } catch (SchedulerException e) {
      log.error("Erro ao buscar job {}/{}: {}", jobName, jobGroup,
                e.getMessage(), e);
    }
    return null;
  }

  @Override
  public boolean pauseJob(String jobName, String jobGroup) {
    try {
      Scheduler scheduler = getConfig().getQuartzScheduler();
      JobKey jobKey = JobKey.jobKey(jobName, jobGroup);

      if (scheduler.checkExists(jobKey)) {
        scheduler.pauseJob(jobKey);
        log.info("Job pausado: {}/{}", jobName, jobGroup);
        return true;
      } else {
        log.warn("Job nao encontrado para pausar: {}/{}", jobName,
                 jobGroup);
      }
    } catch (SchedulerException e) {
      log.error("Erro ao pausar job {}/{}: {}", jobName, jobGroup,
                e.getMessage(), e);
    }
    return false;
  }

  @Override
  public boolean resumeJob(String jobName, String jobGroup) {
    try {
      Scheduler scheduler = getConfig().getQuartzScheduler();
      JobKey jobKey = JobKey.jobKey(jobName, jobGroup);

      if (scheduler.checkExists(jobKey)) {
        scheduler.resumeJob(jobKey);
        log.info("Job resumido: {}/{}", jobName, jobGroup);
        return true;
      } else {
        log.warn("Job nao encontrado para resumir: {}/{}", jobName,
                 jobGroup);
      }
    } catch (SchedulerException e) {
      log.error("Erro ao resumir job {}/{}: {}", jobName, jobGroup,
                e.getMessage(), e);
    }
    return false;
  }

  @Override
  public boolean deleteJob(String jobName, String jobGroup) {
    try {
      Scheduler scheduler = getConfig().getQuartzScheduler();
      JobKey jobKey = JobKey.jobKey(jobName, jobGroup);

      if (scheduler.checkExists(jobKey)) {
        boolean deleted = scheduler.deleteJob(jobKey);
        if (deleted) {
          log.info("Job removido: {}/{}", jobName, jobGroup);
        }
        return deleted;
      } else {
        log.warn("Job nao encontrado para remover: {}/{}", jobName,
                 jobGroup);
      }
    } catch (SchedulerException e) {
      log.error("Erro ao remover job {}/{}: {}", jobName, jobGroup,
                e.getMessage(), e);
    }
    return false;
  }

  @Override
  public boolean triggerJob(String jobName, String jobGroup) {
    try {
      Scheduler scheduler = getConfig().getQuartzScheduler();
      JobKey jobKey = JobKey.jobKey(jobName, jobGroup);

      if (scheduler.checkExists(jobKey)) {
        scheduler.triggerJob(jobKey);
        log.info("Job disparado: {}/{}", jobName, jobGroup);
        return true;
      } else {
        log.warn("Job nao encontrado para disparar: {}/{}", jobName,
                 jobGroup);
      }
    } catch (SchedulerException e) {
      log.error("Erro ao disparar job {}/{}: {}", jobName, jobGroup,
                e.getMessage(), e);
    }
    return false;
  }

  @Override
  public List<QuartzJobTriggerDTO> findTriggersOfJob(String jobName,
                                                     String jobGroup) {
    List<QuartzJobTriggerDTO> triggers = new ArrayList<>();
    try {
      Scheduler scheduler = getConfig().getQuartzScheduler();
      JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
      List<? extends Trigger> triggerList = scheduler
          .getTriggersOfJob(jobKey);

      ZoneId zoneId = ZoneId.systemDefault();

      for (Trigger trigger : triggerList) {
        QuartzJobTriggerDTO dto = buildTriggerDTO(trigger, scheduler,
                                                  zoneId);
        triggers.add(dto);
      }
    } catch (SchedulerException e) {
      log.error("Erro ao buscar triggers do job {}/{}: {}", jobName,
                jobGroup, e.getMessage(), e);
    }
    return triggers;
  }

  @Override
  public List<QuartzJobInstanceDTO> findCurrentlyExecutingJobs() {
    List<QuartzJobInstanceDTO> instances = new ArrayList<>();
    try {
      Scheduler scheduler = getConfig().getQuartzScheduler();
      List<JobExecutionContext> executingJobs = scheduler
          .getCurrentlyExecutingJobs();

      ZoneId zoneId = ZoneId.systemDefault();

      for (JobExecutionContext context : executingJobs) {
        QuartzJobInstanceDTO dto = buildInstanceDTO(context, zoneId);
        instances.add(dto);
      }
    } catch (SchedulerException e) {
      log.error("Erro ao buscar jobs em execucao: {}", e.getMessage(), e);
    }
    return instances;
  }

  @Override
  public boolean pauseTrigger(String triggerName, String triggerGroup) {
    try {
      Scheduler scheduler = getConfig().getQuartzScheduler();
      TriggerKey triggerKey = TriggerKey.triggerKey(triggerName,
                                                    triggerGroup);

      if (scheduler.checkExists(triggerKey)) {
        scheduler.pauseTrigger(triggerKey);
        log.info("Trigger pausado: {}/{}", triggerName, triggerGroup);
        return true;
      }
    } catch (SchedulerException e) {
      log.error("Erro ao pausar trigger {}/{}: {}", triggerName,
                triggerGroup, e.getMessage(), e);
    }
    return false;
  }

  @Override
  public boolean resumeTrigger(String triggerName, String triggerGroup) {
    try {
      Scheduler scheduler = getConfig().getQuartzScheduler();
      TriggerKey triggerKey = TriggerKey.triggerKey(triggerName,
                                                    triggerGroup);

      if (scheduler.checkExists(triggerKey)) {
        scheduler.resumeTrigger(triggerKey);
        log.info("Trigger resumido: {}/{}", triggerName, triggerGroup);
        return true;
      }
    } catch (SchedulerException e) {
      log.error("Erro ao resumir trigger {}/{}: {}", triggerName,
                triggerGroup, e.getMessage(), e);
    }
    return false;
  }

  /**
   * Constrói um DTO de job a partir do JobDetail do Quartz.
   *
   * @param jobDetail Detalhes do job
   * @param scheduler Scheduler para buscar informacoes de estado
   * @return DTO preenchido
   */
  private QuartzJobDTO buildJobDTO(JobDetail jobDetail,
                                   Scheduler scheduler) {
    QuartzJobTriggerDTO last = findTriggersOfJob(jobDetail.getKey()
        .getName(), jobDetail.getKey().getGroup()).getLast();
    QuartzJobDTO dto = QuartzJobDTO.builder()
        .jobState(last != null ? last.getTriggerState() : null)
        .jobName(jobDetail.getKey().getName())
        .jobGroup(jobDetail.getKey().getGroup())
        .description(jobDetail.getDescription())
        .jobClassName(jobDetail.getJobClass().getName())
        .durable(jobDetail.isDurable())
        .requestsRecovery(jobDetail.requestsRecovery())
        .jobData(jobDetail.getJobDataMap()).build();

    try {
      List<? extends Trigger> triggers = scheduler
          .getTriggersOfJob(jobDetail.getKey());
      if (!triggers.isEmpty()) {
        Trigger trigger = triggers.get(0);
        dto.setNextExecutionTime(trigger.getNextFireTime() != null ? trigger
            .getNextFireTime().toInstant().atZone(ZoneId.systemDefault())
            .toLocalDateTime() : null);
      }
    } catch (SchedulerException e) {
      log.debug("Erro ao buscar triggers para job {}: {}",
                jobDetail.getKey(), e.getMessage());
    }

    return dto;
  }

  /**
   * Constrói um DTO de trigger a partir do Trigger do Quartz.
   *
   * @param trigger   Trigger do Quartz
   * @param scheduler Scheduler para buscar informacoes de estado
   * @param zoneId    ZoneId para conversao de datas
   * @return DTO preenchido
   */
  private QuartzJobTriggerDTO buildTriggerDTO(Trigger trigger,
                                              Scheduler scheduler,
                                              ZoneId zoneId) {
    QuartzJobTriggerDTO dto = QuartzJobTriggerDTO.builder()
        .triggerName(trigger.getKey().getName())
        .triggerGroup(trigger.getKey().getGroup())
        .jobName(trigger.getJobKey().getName())
        .jobGroup(trigger.getJobKey().getGroup())
        .description(trigger.getDescription())
        .triggerType(trigger.getClass().getName())
        .calendarName(trigger.getCalendarName())
        .priority(trigger.getPriority())
        .misFireInstr((long) trigger.getMisfireInstruction())
        .jobData(trigger.getJobDataMap()).build();

    if (trigger.getNextFireTime() != null) {
      dto.setNextFireTime(trigger.getNextFireTime().toInstant()
          .atZone(zoneId).toLocalDateTime());
    }
    if (trigger.getPreviousFireTime() != null) {
      dto.setPrevFireTime(trigger.getPreviousFireTime().toInstant()
          .atZone(zoneId).toLocalDateTime());
    }
    if (trigger.getStartTime() != null) {
      dto.setStartTime(trigger.getStartTime().toInstant().atZone(zoneId)
          .toLocalDateTime());
    }
    if (trigger.getEndTime() != null) {
      dto.setEndTime(trigger.getEndTime().toInstant().atZone(zoneId)
          .toLocalDateTime());
    }
    if (trigger.getFinalFireTime() != null) {
      dto.setFinalFireTime(trigger.getFinalFireTime().toInstant()
          .atZone(zoneId).toLocalDateTime());
    }

    try {
      dto.setTriggerState(scheduler.getTriggerState(trigger.getKey())
          .name());
    } catch (SchedulerException e) {
      log.debug("Erro ao buscar estado do trigger {}: {}", trigger.getKey(),
                e.getMessage());
    }

    return dto;
  }

  /**
   * Constrói um DTO de instância de execucao a partir do contexto de execucao.
   *
   * @param context Contexto de execucao do job
   * @param zoneId  ZoneId para conversao de datas
   * @return DTO preenchido
   */
  private QuartzJobInstanceDTO buildInstanceDTO(JobExecutionContext context,
                                                ZoneId zoneId) {
    return QuartzJobInstanceDTO.builder()
        .instanceId(context.getFireInstanceId())
        .jobName(context.getJobDetail().getKey().getName())
        .jobGroup(context.getJobDetail().getKey().getGroup())
        .triggerName(context.getTrigger().getKey().getName())
        .triggerGroup(context.getTrigger().getKey().getGroup())
        .fireTime(context.getFireTime().toInstant().atZone(zoneId)
            .toLocalDateTime())
        .scheduledFireTime(context.getScheduledFireTime() != null ? context
            .getScheduledFireTime().toInstant().atZone(zoneId)
            .toLocalDateTime() : null)
        .jobDataMap(context.getJobDetail().getJobDataMap())
        .recovered(context.isRecovering()).build();
  }

}
