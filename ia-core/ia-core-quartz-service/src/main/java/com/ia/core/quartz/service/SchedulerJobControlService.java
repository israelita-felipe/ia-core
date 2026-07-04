package com.ia.core.quartz.service;

import com.ia.core.quartz.service.model.job.QuartzJobUseCase;
import com.ia.core.quartz.service.model.job.dto.QuartzJobDTO;
import com.ia.core.quartz.service.model.job.dto.QuartzJobInstanceDTO;
import com.ia.core.quartz.service.model.job.dto.QuartzJobTriggerDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Serviço para controle de jobs no scheduler Quartz.
 *
 * Responsável por operações de controle como pausar, retomar, deletar,
 * disparar jobs e consultar informações de jobs.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SchedulerJobControlService implements QuartzJobUseCase {

  private final SchedulerConfigServiceConfig config;

  /**
   * Lista todos os jobs registrados no scheduler Quartz.
   *
   * @return Lista de DTOs com informações dos jobs
   */
  @Tool(description = "Lista todos os jobs registrados no scheduler Quartz, independentemente do grupo. " +
             "Retorna uma lista completa de jobs com informações detalhadas incluindo nome, grupo, " +
             "classe do job, estado atual, próxima execução e dados do job. " +
             "Útil para auditoria, monitoramento e diagnóstico do sistema de agendamento. " +
             "Inclui jobs de todos os grupos configurados no scheduler.")
  public List<QuartzJobDTO> findAllJobs() {
    List<QuartzJobDTO> jobs = new ArrayList<>();
    try {
      Scheduler scheduler = config.getQuartzScheduler();
      List<String> jobGroups = scheduler.getJobGroupNames();

      for (String group : jobGroups) {
        Set<JobKey> jobKeys = scheduler.getJobKeys(GroupMatcher.jobGroupEquals(group));

        for (JobKey jobKey : jobKeys) {
          try {
            JobDetail jobDetail = scheduler.getJobDetail(jobKey);
            if (jobDetail != null) {
              QuartzJobDTO dto = buildJobDTO(jobDetail, scheduler);
              jobs.add(dto);
            }
          } catch (SchedulerException e) {
            log.error("Erro ao buscar detalhes do job {}: {}", jobKey, e.getMessage());
          }
        }
      }
      log.info("Encontrados {} jobs no scheduler", jobs.size());
    } catch (SchedulerException e) {
      log.error("Erro ao listar jobs: {}", e.getMessage(), e);
    }
    return jobs;
  }

  /**
   * Busca um job específico no scheduler Quartz.
   *
   * @param jobName  Nome do job
   * @param jobGroup Grupo do job
   * @return DTO com informações do job ou null se não encontrado
   */
  @Tool(description = "Busca um job específico no scheduler Quartz pelo nome e grupo. " +
             "Retorna informações detalhadas do job incluindo estado atual, próxima execução, " +
             "classe do job, durabilidade e dados associados. " +
             "Útil para inspecionar configurações específicas de agendamento e diagnosticar problemas. " +
             "Retorna null se o job não for encontrado.")
  public QuartzJobDTO findJob(
          @ToolParam(description = "Nome único do job a ser buscado no scheduler (String, obrigatório). " +
                          "Identifica o job dentro do grupo especificado.", required = true) String jobName,
          @ToolParam(description = "Grupo do job a ser buscado (String, obrigatório). " +
                          "Define o grupo lógico ao qual o job pertence.", required = true) String jobGroup) {
    try {
      Scheduler scheduler = config.getQuartzScheduler();
      JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
      JobDetail jobDetail = scheduler.getJobDetail(jobKey);

      if (jobDetail != null) {
        return buildJobDTO(jobDetail, scheduler);
      }
    } catch (SchedulerException e) {
      log.error("Erro ao buscar job {}/{}: {}", jobName, jobGroup, e.getMessage(), e);
    }
    return null;
  }

  /**
   * Pausa um job específico no scheduler Quartz.
   *
   * @param jobName  Nome do job
   * @param jobGroup Grupo do job
   * @return true se o job foi pausado com sucesso
   */
  @Tool(description = "Pausa um job específico no scheduler Quartz, interrompendo temporariamente sua execução agendada. " +
             "O job permanece registrado no scheduler mas não será disparado até ser retomado. " +
             "Útil para manutenções ou quando necessário interromper temporariamente um processo automatizado. " +
             "Retorna true se o job foi pausado com sucesso, false se o job não foi encontrado.")
  public boolean pauseJob(
          @ToolParam(description = "Nome único do job a ser pausado no scheduler (String, obrigatório). " +
                          "Geralmente corresponde ao ID da configuração do scheduler.", required = true) String jobName,
          @ToolParam(description = "Grupo do job a ser pausado (String, obrigatório). " +
                          "Define o grupo lógico ao qual o job pertence para organização.", required = true) String jobGroup) {
    try {
      Scheduler scheduler = config.getQuartzScheduler();
      JobKey jobKey = JobKey.jobKey(jobName, jobGroup);

      if (scheduler.checkExists(jobKey)) {
        scheduler.pauseJob(jobKey);
        log.info("Job pausado: {}/{}", jobName, jobGroup);
        return true;
      } else {
        log.warn("Job nao encontrado para pausar: {}/{}", jobName, jobGroup);
      }
    } catch (SchedulerException e) {
      log.error("Erro ao pausar job {}/{}: {}", jobName, jobGroup, e.getMessage(), e);
    }
    return false;
  }

  /**
   * Retoma a execução de um job previamente pausado.
   *
   * @param jobName  Nome do job
   * @param jobGroup Grupo do job
   * @return true se o job foi retomado com sucesso
   */
  @Tool(description = "Retoma a execução de um job previamente pausado no scheduler Quartz. " +
             "Restaura o agendamento normal do job, permitindo que seja disparado novamente conforme sua periodicidade. " +
             "Útil para reativar processos automatizados após manutenção ou interrupção temporária. " +
             "Retorna true se o job foi retomado com sucesso, false se o job não foi encontrado.")
  public boolean resumeJob(
          @ToolParam(description = "Nome único do job a ser retomado no scheduler (String, obrigatório). " +
                          "Deve corresponder a um job previamente pausado.", required = true) String jobName,
          @ToolParam(description = "Grupo do job a ser retomado (String, obrigatório). " +
                          "Define o grupo lógico ao qual o job pertence.", required = true) String jobGroup) {
    try {
      Scheduler scheduler = config.getQuartzScheduler();
      JobKey jobKey = JobKey.jobKey(jobName, jobGroup);

      if (scheduler.checkExists(jobKey)) {
        scheduler.resumeJob(jobKey);
        log.info("Job resumido: {}/{}", jobName, jobGroup);
        return true;
      } else {
        log.warn("Job nao encontrado para resumir: {}/{}", jobName, jobGroup);
      }
    } catch (SchedulerException e) {
      log.error("Erro ao resumir job {}/{}: {}", jobName, jobGroup, e.getMessage(), e);
    }
    return false;
  }

  /**
   * Remove permanentemente um job do scheduler Quartz.
   *
   * @param jobName  Nome do job
   * @param jobGroup Grupo do job
   * @return true se o job foi removido com sucesso
   */
  @Tool(description = "Remove permanentemente um job do scheduler Quartz, incluindo todos os seus triggers associados. " +
             "Esta operação é irreversível e cancela todas as execuções futuras do job. " +
             "Útil para limpar jobs obsoletos ou incorretamente configurados. " +
             "Retorna true se o job foi removido com sucesso, false se o job não foi encontrado.")
  public boolean deleteJob(
          @ToolParam(description = "Nome único do job a ser removido do scheduler (String, obrigatório). " +
                          "Identifica o job que deve ser excluído permanentemente.", required = true) String jobName,
          @ToolParam(description = "Grupo do job a ser removido (String, obrigatório). " +
                          "Define o grupo lógico ao qual o job pertence.", required = true) String jobGroup) {
    try {
      Scheduler scheduler = config.getQuartzScheduler();
      JobKey jobKey = JobKey.jobKey(jobName, jobGroup);

      if (scheduler.checkExists(jobKey)) {
        boolean deleted = scheduler.deleteJob(jobKey);
        if (deleted) {
          log.info("Job removido: {}/{}", jobName, jobGroup);
        }
        return deleted;
      } else {
        log.warn("Job nao encontrado para remover: {}/{}", jobName, jobGroup);
      }
    } catch (SchedulerException e) {
      log.error("Erro ao remover job {}/{}: {}", jobName, jobGroup, e.getMessage(), e);
    }
    return false;
  }

  /**
   * Dispara imediatamente a execução de um job.
   *
   * @param jobName  Nome do job
   * @param jobGroup Grupo do job
   * @return true se o job foi disparado com sucesso
   */
  @Tool(description = "Dispara imediatamente a execução de um job no scheduler Quartz, ignorando seu agendamento normal. " +
             "Força a execução única do job independentemente de sua periodicidade configurada. " +
             "Útil para testes, execuções manuais ou quando necessário antecipar uma tarefa. " +
             "Não afeta o agendamento futuro do job. Retorna true se o job foi disparado com sucesso, false se o job não foi encontrado.")
  public boolean triggerJob(
          @ToolParam(description = "Nome único do job a ser disparado no scheduler (String, obrigatório). " +
                          "Identifica o job que deve ser executado imediatamente.", required = true) String jobName,
          @ToolParam(description = "Grupo do job a ser disparado (String, obrigatório). " +
                          "Define o grupo lógico ao qual o job pertence.", required = true) String jobGroup) {
    try {
      Scheduler scheduler = config.getQuartzScheduler();
      JobKey jobKey = JobKey.jobKey(jobName, jobGroup);

      if (scheduler.checkExists(jobKey)) {
        scheduler.triggerJob(jobKey);
        log.info("Job disparado: {}/{}", jobName, jobGroup);
        return true;
      } else {
        log.warn("Job nao encontrado para disparar: {}/{}", jobName, jobGroup);
      }
    } catch (SchedulerException e) {
      log.error("Erro ao disparar job {}/{}: {}", jobName, jobGroup, e.getMessage(), e);
    }
    return false;
  }

  /**
   * Busca todos os triggers associados a um job específico.
   *
   * @param jobName  Nome do job
   * @param jobGroup Grupo do job
   * @return Lista de DTOs com informações dos triggers
   */
  @Tool(description = "Busca todos os triggers associados a um job específico no scheduler Quartz. " +
             "Retorna uma lista de triggers com informações detalhadas incluindo estado, " +
             "próxima execução, execução anterior, prioridade e instruções de misfire. " +
             "Útil para entender o agendamento e histórico de execução de um job. " +
             "Um job pode ter múltiplos triggers com diferentes periodicidades.")
  public List<QuartzJobTriggerDTO> findTriggersOfJob(
          @ToolParam(description = "Nome único do job para buscar os triggers (String, obrigatório). " +
                          "Identifica o job cujos triggers devem ser listados.", required = true) String jobName,
          @ToolParam(description = "Grupo do job para buscar os triggers (String, obrigatório). " +
                          "Define o grupo lógico ao qual o job pertence.", required = true) String jobGroup) {
    List<QuartzJobTriggerDTO> triggers = new ArrayList<>();
    try {
      Scheduler scheduler = config.getQuartzScheduler();
      JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
      List<? extends Trigger> triggerList = scheduler.getTriggersOfJob(jobKey);

      ZoneId zoneId = ZoneId.systemDefault();

      for (Trigger trigger : triggerList) {
        QuartzJobTriggerDTO dto = buildTriggerDTO(trigger, scheduler, zoneId);
        triggers.add(dto);
      }
    } catch (SchedulerException e) {
      log.error("Erro ao buscar triggers do job {}/{}: {}", jobName, jobGroup, e.getMessage(), e);
    }
    return triggers;
  }

  /**
   * Lista todos os jobs que estão atualmente em execução.
   *
   * @return Lista de DTOs com informações dos jobs em execução
   */
  @Tool(description = "Lista todos os jobs que estão atualmente em execução no scheduler Quartz. " +
             "Retorna informações sobre cada instância em execução incluindo ID da instância, " +
             "tempo de disparo, tempo agendado, dados do job e se está em recuperação. " +
             "Útil para monitoramento em tempo real, diagnóstico de problemas de performance " +
             "e verificação se jobs estão travados ou demorando mais que o esperado.")
  public List<QuartzJobInstanceDTO> findCurrentlyExecutingJobs() {
    List<QuartzJobInstanceDTO> instances = new ArrayList<>();
    try {
      Scheduler scheduler = config.getQuartzScheduler();
      List<JobExecutionContext> executingJobs = scheduler.getCurrentlyExecutingJobs();

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

  /**
   * Pausa um trigger específico.
   *
   * @param triggerName  Nome do trigger
   * @param triggerGroup Grupo do trigger
   * @return true se o trigger foi pausado com sucesso
   */
  @Tool(description = "Pausa um trigger específico no scheduler Quartz, interrompendo temporariamente seu disparo. " +
             "O trigger permanece registrado mas não disparará o job até ser retomado. " +
             "Útil para interromper temporariamente um agendamento específico sem afetar outros triggers do mesmo job. " +
             "Retorna true se o trigger foi pausado com sucesso, false se o trigger não foi encontrado.")
  public boolean pauseTrigger(
          @ToolParam(description = "Nome único do trigger a ser pausado no scheduler (String, obrigatório). " +
                          "Identifica o trigger que deve ser interrompido temporariamente.", required = true) String triggerName,
          @ToolParam(description = "Grupo do trigger a ser pausado (String, obrigatório). " +
                          "Define o grupo lógico ao qual o trigger pertence.", required = true) String triggerGroup) {
    try {
      Scheduler scheduler = config.getQuartzScheduler();
      TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroup);

      if (scheduler.checkExists(triggerKey)) {
        scheduler.pauseTrigger(triggerKey);
        log.info("Trigger pausado: {}/{}", triggerName, triggerGroup);
        return true;
      }
    } catch (SchedulerException e) {
      log.error("Erro ao pausar trigger {}/{}: {}", triggerName, triggerGroup, e.getMessage(), e);
    }
    return false;
  }

  /**
   * Retoma a execução de um trigger previamente pausado.
   *
   * @param triggerName  Nome do trigger
   * @param triggerGroup Grupo do trigger
   * @return true se o trigger foi retomado com sucesso
   */
  @Tool(description = "Retoma a execução de um trigger previamente pausado no scheduler Quartz. " +
             "Restaura o agendamento normal do trigger, permitindo que dispare o job novamente conforme sua periodicidade. " +
             "Útil para reativar um agendamento específico após manutenção ou interrupção temporária. " +
             "Retorna true se o trigger foi retomado com sucesso, false se o trigger não foi encontrado.")
  public boolean resumeTrigger(
          @ToolParam(description = "Nome único do trigger a ser retomado no scheduler (String, obrigatório). " +
                          "Deve corresponder a um trigger previamente pausado.", required = true) String triggerName,
          @ToolParam(description = "Grupo do trigger a ser retomado (String, obrigatório). " +
                          "Define o grupo lógico ao qual o trigger pertence.", required = true) String triggerGroup) {
    try {
      Scheduler scheduler = config.getQuartzScheduler();
      TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroup);

      if (scheduler.checkExists(triggerKey)) {
        scheduler.resumeTrigger(triggerKey);
        log.info("Trigger retomado: {}/{}", triggerName, triggerGroup);
        return true;
      }
    } catch (SchedulerException e) {
      log.error("Erro ao retomar trigger {}/{}: {}", triggerName, triggerGroup, e.getMessage(), e);
    }
    return false;
  }

  /**
   * Constrói um DTO de job a partir do JobDetail do Quartz.
   *
   * @param jobDetail JobDetail do Quartz
   * @param scheduler Instância do scheduler
   * @return DTO preenchido com informações do job
   * @throws SchedulerException Caso ocorra erro no scheduler
   */
  private QuartzJobDTO buildJobDTO(JobDetail jobDetail, Scheduler scheduler)
    throws SchedulerException {
    QuartzJobDTO dto = new QuartzJobDTO();
    dto.setJobName(jobDetail.getKey().getName());
    dto.setJobGroup(jobDetail.getKey().getGroup());
    dto.setJobClassName(jobDetail.getJobClass().getName());
    dto.setDurable(jobDetail.isDurable());
    dto.setJobData(jobDetail.getJobDataMap());

    JobKey jobKey = jobDetail.getKey();
    List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);

    for (Trigger trigger : triggers) {
      QuartzJobTriggerDTO triggerDTO = buildTriggerDTO(trigger, scheduler, ZoneId.systemDefault());
      dto.getTriggers().add(triggerDTO);
    }

    return dto;
  }

  /**
   * Constrói um DTO de trigger a partir do Trigger do Quartz.
   *
   * @param trigger   Trigger do Quartz
   * @param scheduler Instância do scheduler
   * @param zoneId    ZoneId para conversão de datas
   * @return DTO preenchido com informações do trigger
   * @throws SchedulerException Caso ocorra erro no scheduler
   */
  private QuartzJobTriggerDTO buildTriggerDTO(Trigger trigger, Scheduler scheduler, ZoneId zoneId)
    throws SchedulerException {
    QuartzJobTriggerDTO dto = new QuartzJobTriggerDTO();
    dto.setTriggerName(trigger.getKey().getName());
    dto.setTriggerGroup(trigger.getKey().getGroup());
    dto.setTriggerState(scheduler.getTriggerState(trigger.getKey()).name());
    dto.setPriority(trigger.getPriority());
    dto.setMisFireInstr((long) trigger.getMisfireInstruction());

    if (trigger.getNextFireTime() != null) {
      dto.setNextFireTime(LocalDateTime.ofInstant(trigger.getNextFireTime().toInstant(), zoneId));
    }
    if (trigger.getPreviousFireTime() != null) {
      dto.setPrevFireTime(LocalDateTime.ofInstant(trigger.getPreviousFireTime().toInstant(), zoneId));
    }
    if (trigger.getStartTime() != null) {
      dto.setStartTime(LocalDateTime.ofInstant(trigger.getStartTime().toInstant(), zoneId));
    }
    if (trigger.getEndTime() != null) {
      dto.setEndTime(LocalDateTime.ofInstant(trigger.getEndTime().toInstant(), zoneId));
    }

    return dto;
  }

  /**
   * Constrói um DTO de instância de job a partir do JobExecutionContext.
   *
   * @param context Contexto de execução do job
   * @param zoneId  ZoneId para conversão de datas
   * @return DTO preenchido com informações da instância
   */
  private QuartzJobInstanceDTO buildInstanceDTO(JobExecutionContext context, ZoneId zoneId) {
    QuartzJobInstanceDTO dto = new QuartzJobInstanceDTO();
    dto.setInstanceId(context.getFireInstanceId());
    dto.setJobName(context.getJobDetail().getKey().getName());
    dto.setJobGroup(context.getJobDetail().getKey().getGroup());
    dto.setRecovered(context.isRecovering());

    if (context.getFireTime() != null) {
      dto.setFireTime(LocalDateTime.ofInstant(context.getFireTime().toInstant(), zoneId));
    }
    if (context.getScheduledFireTime() != null) {
      dto.setScheduledFireTime(LocalDateTime.ofInstant(context.getScheduledFireTime().toInstant(), zoneId));
    }

    return dto;
  }
}
