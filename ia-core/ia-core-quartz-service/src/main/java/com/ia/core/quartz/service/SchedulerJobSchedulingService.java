package com.ia.core.quartz.service;

import com.ia.core.quartz.service.model.periodicidade.dto.PeriodicidadeDTO;
import com.ia.core.quartz.service.model.scheduler.dto.SchedulerConfigDTO;
import com.ia.core.quartz.service.model.scheduler.dto.triggers.SchedulerConfigTriggerDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Objects;

/**
 * Serviço para agendamento e gerenciamento de jobs no scheduler Quartz.
 * <p>
 * Responsável por criar, atualizar e cancelar jobs no scheduler,
 * bem como processar informações de triggers.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SchedulerJobSchedulingService {

    private final SchedulerConfigServiceConfig config;

    /**
     * Agenda um job no scheduler Quartz.
     *
     * @param schedulerConfigDTO DTO da configuração do job a ser agendado
     * @throws SchedulerException     caso ocorra erro no agendamento
     * @throws ClassNotFoundException caso a classe do job não seja encontrada
     */
public void agendarJob(SchedulerConfigDTO schedulerConfigDTO)
    throws SchedulerException, ClassNotFoundException {
    Objects.requireNonNull(schedulerConfigDTO, "schedulerConfigDTO não pode ser null");
    Scheduler scheduler = config.getQuartzScheduler();
    JobDetail jobDetail = criarJobDetail(schedulerConfigDTO);
    JobKey jobKey = obterJobKey(schedulerConfigDTO);
    Trigger trigger = criarTrigger(schedulerConfigDTO);
    TriggerKey triggerKey = trigger.getKey();

    log.debug("Tentando agendar jobKey={} triggerKey={}", jobKey.getName(), triggerKey.getName());
    log.debug("Trigger jobKey={}", trigger.getJobKey());

    boolean jobExists = scheduler.checkExists(jobKey);
    boolean triggerExists = scheduler.checkExists(triggerKey);

    log.debug("jobExists={} triggerExists={}", jobExists, triggerExists);

    if (jobExists && !triggerExists) {
      scheduler.deleteJob(jobKey);
      log.info("Job órfão removido antes do novo agendamento: {}", schedulerConfigDTO.getId());
    }

    if (triggerExists) {
      log.warn("Trigger já existe para {}. Ignorando agendamento.",
               schedulerConfigDTO.getId());
    } else {
      scheduler.scheduleJob(jobDetail, trigger);
      log.info("Job agendado com sucesso: {}", schedulerConfigDTO.getId());
    }
  }

    /**
     * Atualiza um job existente no scheduler Quartz.
     *
     * @param schedulerConfigDTO DTO da configuração do job a ser atualizado
     * @throws SchedulerException caso ocorra erro na atualização
     */
    public void atualizarJob(SchedulerConfigDTO schedulerConfigDTO) throws SchedulerException {
        Objects.requireNonNull(schedulerConfigDTO, "schedulerConfigDTO não pode ser null");
        Scheduler scheduler = config.getQuartzScheduler();
        JobKey jobKey = obterJobKey(schedulerConfigDTO);
        TriggerKey triggerKey = obterChaveTrigger(schedulerConfigDTO);

        if (scheduler.checkExists(jobKey) && scheduler.checkExists(triggerKey)) {
            Trigger newTrigger = criarTrigger(schedulerConfigDTO);
            scheduler.rescheduleJob(triggerKey, newTrigger);
            log.info("Job atualizado com sucesso: {}", schedulerConfigDTO.getId());
        }
    }

    /**
     * Cancela um job no scheduler Quartz.
     *
     * @param schedulerConfigDTO DTO da configuração do job a ser cancelado
     * @throws SchedulerException caso ocorra erro no cancelamento
     */
    public void cancelarJob(SchedulerConfigDTO schedulerConfigDTO) throws SchedulerException {
        Objects.requireNonNull(schedulerConfigDTO, "schedulerConfigDTO não pode ser null");
        Scheduler scheduler = config.getQuartzScheduler();
        JobKey jobKey = obterJobKey(schedulerConfigDTO);

        if (scheduler.checkExists(jobKey)) {
            scheduler.deleteJob(jobKey);
            log.info("Job cancelado com sucesso: {}", schedulerConfigDTO.getId());
        }
    }

    /**
     * Agenda todos os jobs ativos.
     *
     * @param schedulerConfigs Lista de configurações de jobs ativos
     */
    public void agendarJobs(List<SchedulerConfigDTO> schedulerConfigs) {
        Objects.requireNonNull(schedulerConfigs, "schedulerConfigs não pode ser null");
        schedulerConfigs.forEach(config -> {
            try {
                agendarJob(config);
            } catch (SchedulerException | ClassNotFoundException e) {
                log.error("Erro ao agendar job {}: {}", config.getId(), e.getLocalizedMessage(), e);
            }
        });
    }

    /**
     * Atualiza todos os jobs ativos verificando mudanças na periodicidade.
     *
     * @param schedulerConfigs Lista de configurações de jobs ativos
     */
    public void updateJobs(List<SchedulerConfigDTO> schedulerConfigs) {
        Objects.requireNonNull(schedulerConfigs, "schedulerConfigs não pode ser null");
        for (SchedulerConfigDTO schedulerConfig : schedulerConfigs) {
            try {
                Scheduler scheduler = config.getQuartzScheduler();
                JobKey jobKey = obterJobKey(schedulerConfig);
                TriggerKey triggerKey = obterChaveTrigger(schedulerConfig);

                boolean jobExists = scheduler.checkExists(jobKey);
                boolean triggerExists = scheduler.checkExists(triggerKey);

                if (triggerExists && jobExists) {
                    if (verificarMudancaPeriodicidade(schedulerConfig, triggerKey, scheduler)) {
                        atualizarJob(schedulerConfig);
                    }
                } else {
                    if (jobExists) {
                        scheduler.deleteJob(jobKey);
                        log.info("Job antigo removido antes do novo agendamento: {}", schedulerConfig.getId());
                    }
                    agendarJob(schedulerConfig);
                }
            } catch (SchedulerException | ClassNotFoundException e) {
                log.error("Erro durante a atualização do job {}: {}", schedulerConfig.getId(), e.getLocalizedMessage(), e);
            }
        }
    }

    /**
     * Processa e preenche as informações dos triggers associados a uma configuração.
     * <p>
     * Recupera informações do scheduler Quartz como próximo tempo de execução,
     * estado do trigger, prioridade, etc.
     *
     * @param schedulerConfigDTO DTO da configuração a ser preenchido
     */
    public void processarTriggers(SchedulerConfigDTO schedulerConfigDTO) {
        Objects.requireNonNull(schedulerConfigDTO, "schedulerConfigDTO não pode ser null");
        try {
            Scheduler scheduler = config.getQuartzScheduler();
            JobKey jobKey = obterJobKey(schedulerConfigDTO);

            if (!scheduler.checkExists(jobKey)) {
                log.warn("Job não encontrado para processar triggers: {}", jobKey.getName());
                return;
            }

            List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);

            for (Trigger trigger : triggers) {
                SchedulerConfigTriggerDTO triggerDTO = construirTriggerDTO(scheduler,
                    trigger,
                    schedulerConfigDTO
                        .getPeriodicidade()
                        .getZoneIdValue());
                schedulerConfigDTO.getTriggers().add(triggerDTO);
            }

            log.debug("Processados {} triggers para o job {}", triggers.size(), jobKey.getName());
        } catch (SchedulerException e) {
            log.error("Erro ao processar triggers para a configuração {}: {}",
                schedulerConfigDTO.getId(), e.getLocalizedMessage(), e);
        }
    }

    /**
     * Cria um JobDetail para o scheduler Quartz.
     *
     * @param schedulerConfigDTO DTO da configuração
     * @return JobDetail configurado
     * @throws ClassNotFoundException caso a classe do job não seja encontrada
     */
    @SuppressWarnings("unchecked")
    private JobDetail criarJobDetail(SchedulerConfigDTO schedulerConfigDTO)
        throws ClassNotFoundException {
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("schedulerConfigId", schedulerConfigDTO.getId());

        return JobBuilder
            .newJob((Class<? extends Job>) schedulerConfigDTO.getJobClassNameAsClass())
            .withIdentity(obterNomeJob(schedulerConfigDTO), obterGrupo(schedulerConfigDTO))
            .usingJobData(jobDataMap)
            .build();
    }

    /**
     * Cria um Trigger para o scheduler Quartz.
     *
     * @param schedulerConfigDTO DTO da configuração
     * @return Trigger configurado
     */
    private Trigger criarTrigger(SchedulerConfigDTO schedulerConfigDTO) {
        TriggerKey triggerKey = obterChaveTrigger(schedulerConfigDTO);
        JobKey jobKey = obterJobKey(schedulerConfigDTO);
        PeriodicidadeTrigger trigger = new PeriodicidadeTrigger(schedulerConfigDTO.getPeriodicidade());
        trigger.setJobKey(jobKey);
        trigger.setKey(triggerKey);
        log.debug("Trigger criado com key={} jobKey={}", triggerKey.getName(), jobKey.getName());
        return trigger;
    }

    /**
     * Constrói um DTO de trigger a partir das informações do scheduler Quartz.
     *
     * @param scheduler Instância do scheduler Quartz
     * @param trigger   Trigger do Quartz
     * @param zoneId    ZoneId para conversão de datas
     * @return DTO preenchido com as informações do trigger
     * @throws SchedulerException Caso ocorra erro no scheduler
     */
    private SchedulerConfigTriggerDTO construirTriggerDTO(Scheduler scheduler,
                                                          Trigger trigger,
                                                          ZoneId zoneId)
        throws SchedulerException {
        SchedulerConfigTriggerDTO triggerDTO = SchedulerConfigTriggerDTO.builder().build();

        triggerDTO.setCalendarName(trigger.getCalendarName());
        triggerDTO.setDescription(trigger.getDescription());
        triggerDTO.setJobData(trigger.getJobDataMap());
        triggerDTO.setJobGroup(trigger.getJobKey().getGroup());
        triggerDTO.setJobName(trigger.getJobKey().getName());
        triggerDTO.setSchedulerName(scheduler.getSchedulerName());
        triggerDTO.setTriggerGroup(trigger.getKey().getGroup());
        triggerDTO.setTriggerName(trigger.getKey().getName());
        triggerDTO.setTriggerState(scheduler.getTriggerState(trigger.getKey()).name());
        triggerDTO.setTriggerType(trigger.getClass().getName());

        processarCamposTemporais(triggerDTO, trigger, zoneId);
        processarCamposNumericos(triggerDTO, trigger);

        return triggerDTO;
    }

    /**
     * Processa e converte campos de data/hora do trigger para o DTO.
     *
     * @param triggerDTO {@link SchedulerConfigTriggerDTO}
     * @param trigger    {@link Trigger}
     * @param zoneId     ZoneId para conversão
     */
    private void processarCamposTemporais(SchedulerConfigTriggerDTO triggerDTO,
                                          Trigger trigger, ZoneId zoneId) {
        if (trigger.getEndTime() != null) {
            triggerDTO.setEndTime(LocalDateTime.ofInstant(trigger.getEndTime().toInstant(), zoneId));
        }
        if (trigger.getNextFireTime() != null) {
            triggerDTO.setNextFireTime(LocalDateTime.ofInstant(trigger.getNextFireTime().toInstant(), zoneId));
        }
        if (trigger.getPreviousFireTime() != null) {
            triggerDTO.setPrevFireTime(LocalDateTime.ofInstant(trigger.getPreviousFireTime().toInstant(), zoneId));
        }
        if (trigger.getStartTime() != null) {
            triggerDTO.setStartTime(LocalDateTime.ofInstant(trigger.getStartTime().toInstant(), zoneId));
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
        triggerDTO.setMisFireInstr(Long.valueOf(trigger.getMisfireInstruction()));
        triggerDTO.setPriority(Long.valueOf(trigger.getPriority()));
    }

    /**
     * Verifica se houve mudança na periodicidade que necessite atualização do trigger.
     *
     * @param config     {@link SchedulerConfigDTO}
     * @param triggerKey {@link TriggerKey}
     * @param scheduler  {@link Scheduler}
     * @return se houve mudanças na periodicidade
     * @throws SchedulerException caso ocorra erro de scheduler
     */
    private boolean verificarMudancaPeriodicidade(SchedulerConfigDTO config,
                                                  TriggerKey triggerKey,
                                                  Scheduler scheduler)
        throws SchedulerException {
        PeriodicidadeDTO novaExpressaoCron = config.getPeriodicidade();
        Trigger triggerAntigo = scheduler.getTrigger(triggerKey);
        if (triggerAntigo == null) {
            return true;
        }
        PeriodicidadeDTO expressaoCronAntiga = ((PeriodicidadeTrigger) triggerAntigo).getPeriodicidade();

        if (expressaoCronAntiga.compareTo(novaExpressaoCron) != 0) {
            log.info("Detectada mudança na periodicidade do job {}. Atualizando...", config.getId());
            return true;
        }
        return false;
    }

    /**
     * Obtém o nome do job baseado no ID da configuração.
     *
     * @param schedulerConfigDTO DTO da configuração
     * @return Nome do job
     */
    private String obterNomeJob(SchedulerConfigDTO schedulerConfigDTO) {
        return schedulerConfigDTO.getId().toString();
    }

    /**
     * Obtém o grupo do job/trigger.
     *
     * @param schedulerConfigDTO DTO da configuração
     * @return Nome do grupo
     */
    private String obterGrupo(SchedulerConfigDTO schedulerConfigDTO) {
        return SchedulerConfigServiceConfig.DEFAULT_JOB_GROUP;
    }

    /**
     * Obtém a JobKey para o job.
     *
     * @param schedulerConfigDTO DTO da configuração
     * @return JobKey
     */
    private JobKey obterJobKey(SchedulerConfigDTO schedulerConfigDTO) {
        return JobKey.jobKey(obterNomeJob(schedulerConfigDTO), obterGrupo(schedulerConfigDTO));
    }

    /**
     * Obtém a chave do trigger para uma configuração.
     *
     * @param config Configuração do job
     * @return TriggerKey
     */
    private TriggerKey obterChaveTrigger(SchedulerConfigDTO config) {
        return TriggerKey.triggerKey(obterNomeTrigger(config), obterGrupo(config));
    }

    /**
     * Obtém o nome do trigger baseado no ID da configuração.
     *
     * @param config Configuração do job
     * @return Nome do trigger
     */
    private String obterNomeTrigger(SchedulerConfigDTO config) {
        return config.getId() + obterSufixoTrigger();
    }

    /**
     * Obtém o sufixo padrão para nomes de triggers.
     *
     * @return Sufixo do trigger
     */
    private String obterSufixoTrigger() {
        return SchedulerConfigServiceConfig.TRIGGER_SUFIX;
    }
}
