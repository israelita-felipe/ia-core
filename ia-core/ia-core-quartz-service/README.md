# ia-core-quartz-service

## 📋 Descrição

Módulo de serviço para agendamento de tarefas usando Quartz Scheduler. Fornece funcionalidades para criar, gerenciar e monitorar jobs agendados (execução em horários específicos, recorrências, etc.).

## 🏗️ Estrutura

```
ia-core-quartz-service/
├── src/main/java/
│   └── com/ia/core/quartz/service/
│       ├── SchedulerService.java   # Serviço de agendamento
│       ├── JobService.java         # Gerenciamento de jobs
│       ├── TriggerService.java     # Gerenciamento de triggers
│       ├── job/                    # Implementações de job
│       │   ├── BaseJob.java
│       │   └── CustomJob.java
│       ├── listener/               # Listeners de eventos
│       └── util/                   # Utilitários
└── pom.xml
```

## 🔑 Responsabilidades

- **SchedulerService**: Gerenciador principal do agendador
- **JobService**: CRUD de jobs (tarefas)
- **TriggerService**: Criação e configuração de triggers (cronogramas)
- **Job Execution Monitor**: Rastreia execução de jobs
- **Error Handling**: Trata erros e reexecuções
- **Persisting**: Salva jobs e triggers em BD (não apenas em memória)
- **Rate Limiting**: Evita sobrecarga

## 🛠️ Tecnologias Utilizadas

- **Quartz Scheduler**: Agendamento de tarefas Java
- **Spring Scheduling**: Integração com Spring
- **Spring Data JPA**: Persistência
- **Cron expressions**: Agendamento flexível
- **Jakarta Persistence**: Entidades JPA

## 📦 Dependências

- `ia-core-quartz-service-model` - DTOs
- `ia-core-service` - Serviços base
- `org.quartz-scheduler:quartz`
- `com.github.cron-utils:cron-utils` (validação de cron)

## 🔗 Relacionamentos

Depende de:
- `ia-core-quartz-service-model` - DTOs
- `ia-core-service` - Serviços base

Utilizado por:
- `ia-core-rest` - Endpoints para CRUD de jobs
- `ia-core-quartz-view` - Interface web para agendamento
- Tarefas periódicas da aplicação

## 💡 Padrões Implementados

- **Strategy Pattern**: Diferentes tipos de jobs
- **Factory Pattern**: Criação de triggers
- **Observer Pattern**: Listeners para eventos de job
- **Template Method**: Job base com hooks

## 🚀 Como Usar

### Criar um Job Personalizado

```java
/**
 * Job que executa tarefas de limpeza de logs
 */
public class LogCleanupJob implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDataMap dataMap = context.getJobDetail().getJobDataMap();
        Integer daysToKeep = (Integer) dataMap.get("daysToKeep");

        LocalDateTime cutoffDate = LocalDateTime.now().minusDays(daysToKeep);

        try {
            int deletedCount = logRepository.deleteBefore(cutoffDate);
            System.out.println("Logs deletados: " + deletedCount);
        } catch (Exception e) {
            throw new JobExecutionException("Erro ao limpar logs", e);
        }
    }
}

/**
 * Job que envia relatórios por email
 */
public class ReportGenerationJob implements Job {

    @Autowired
    private ReportService reportService;

    @Autowired
    private EmailService emailService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDataMap dataMap = context.getJobDetail().getJobDataMap();
        String reportType = (String) dataMap.get("reportType");
        String recipientEmail = (String) dataMap.get("email");

        try {
            byte[] reportPdf = reportService.generateReport(reportType);
            emailService.sendReport(recipientEmail, reportPdf);
        } catch (Exception e) {
            throw new JobExecutionException("Erro ao enviar relatório", e);
        }
    }
}
```

### Implementar SchedulerService

```java
@Service
@Transactional
public class SchedulerServiceImpl extends AbstractCrudService<JobDetail, Long>
    implements SchedulerService {

    @Autowired
    private Scheduler scheduler;

    @Autowired
    private TriggerRepository triggerRepository;

    /**
     * Agenda um novo job
     */
    public JobDetail scheduleJob(CreateJobRequest request) throws SchedulerException {
        // 1. Criar detalhe do job
        JobDetail jobDetail = JobBuilder.newJob(getJobClass(request.getJobType()))
            .withIdentity(request.getJobName(), request.getGroup())
            .withDescription(request.getDescription())
            .usingJobData("daysToKeep", request.getParameter("daysToKeep", Integer.class))
            .build();

        // 2. Criar trigger baseado em cron
        Trigger trigger = TriggerBuilder.newTrigger()
            .withIdentity(request.getJobName() + "_trigger", request.getGroup())
            .withSchedule(CronScheduleBuilder.cronSchedule(request.getCronExpression()))
            .build();

        // 3. Agendar
        scheduler.scheduleJob(jobDetail, trigger);

        // 4. Salvar na BD para persistência
        JobDetailEntity jobEntity = new JobDetailEntity();
        jobEntity.setJobName(request.getJobName());
        jobEntity.setJobGroup(request.getGroup());
        jobEntity.setJobType(request.getJobType());
        jobEntity.setCronExpression(request.getCronExpression());
        jobEntity.setStatus(JobStatus.SCHEDULED);
        jobEntity.setNextExecutionTime(getNextExecutionTime(trigger));

        return save(jobEntity);
    }

    /**
     * Remove um job agendado
     */
    public void deleteJob(String jobName, String group) throws SchedulerException {
        scheduler.deleteJob(new JobKey(jobName, group));

        jobRepository.deleteByJobNameAndJobGroup(jobName, group);
    }

    /**
     * Pausa um job
     */
    public void pauseJob(String jobName, String group) throws SchedulerException {
        scheduler.pauseJob(new JobKey(jobName, group));

        jobRepository.updateJobStatus(jobName, group, JobStatus.PAUSED);
    }

    /**
     * Retoma um job pausado
     */
    public void resumeJob(String jobName, String group) throws SchedulerException {
        scheduler.resumeJob(new JobKey(jobName, group));

        jobRepository.updateJobStatus(jobName, group, JobStatus.SCHEDULED);
    }

    /**
     * Executa um job imediatamente (disparar manualmente)
     */
    public void triggerJobNow(String jobName, String group) throws SchedulerException {
        scheduler.triggerJob(new JobKey(jobName, group));
    }

    /**
     * Lista todos os jobs
     */
    public List<JobDetailDTO> listAllJobs() throws SchedulerException {
        List<String> groupNames = scheduler.getJobGroupNames();
        List<JobDetailDTO> jobs = new ArrayList<>();

        for (String group : groupNames) {
            Set<JobKey> jobKeys = scheduler.getJobKeys(GroupMatcher.jobGroupEquals(group));

            for (JobKey jobKey : jobKeys) {
                JobDetail jobDetail = scheduler.getJobDetail(jobKey);
                List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);

                JobDetailDTO dto = new JobDetailDTO();
                dto.setName(jobKey.getName());
                dto.setGroup(jobKey.getGroup());
                dto.setDescription(jobDetail.getDescription());
                dto.setJobClass(jobDetail.getJobClass().getSimpleName());
                dto.setNextExecutionTime(triggers.isEmpty() ? null :
                    triggers.get(0).getNextFireTime());

                jobs.add(dto);
            }
        }

        return jobs;
    }

    /**
     * Obtém histórico de execução de um job
     */
    public List<JobExecutionHistory> getJobExecutionHistory(String jobName, String group) {
        return jobExecutionRepository.findByJobNameAndJobGroupOrderByExecutionTimeDesc(
            jobName,
            group,
            PageRequest.of(0, 50)
        ).getContent();
    }

    private Class<? extends Job> getJobClass(String jobType) {
        switch (jobType.toUpperCase()) {
            case "LOG_CLEANUP":
                return LogCleanupJob.class;
            case "REPORT_GENERATION":
                return ReportGenerationJob.class;
            default:
                throw new IllegalArgumentException("Job type não suportado: " + jobType);
        }
    }

    private LocalDateTime getNextExecutionTime(Trigger trigger) {
        Date nextFireTime = trigger.getNextFireTime();
        return nextFireTime != null ? nextFireTime.toInstant()
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime() : null;
    }
}
```

### Implementar Job Listener

```java
@Component
public class JobExecutionListener implements JobListener {

    @Autowired
    private JobExecutionRepository executionRepository;

    @Override
    public void jobToBeExecuted(JobExecutionContext context) {
        System.out.println("Job " + context.getJobDetail().getKey() + " vai executar");
    }

    @Override
    public void jobExecutionVetoed(JobExecutionContext context) {
        System.out.println("Job " + context.getJobDetail().getKey() + " foi vetado");
    }

    @Override
    public void jobWasExecuted(JobExecutionContext context,
                               JobExecutionException jobException) {
        String jobName = context.getJobDetail().getKey().getName();
        String jobGroup = context.getJobDetail().getKey().getGroup();
        LocalDateTime executionTime = LocalDateTime.now();
        long duration = context.getJobRunTime();
        boolean success = jobException == null;
        String error = jobException != null ? jobException.getMessage() : null;

        // Salvar histórico de execução
        JobExecutionHistory history = new JobExecutionHistory();
        history.setJobName(jobName);
        history.setJobGroup(jobGroup);
        history.setExecutionTime(executionTime);
        history.setDuration(duration);
        history.setSuccess(success);
        history.setErrorMessage(error);

        executionRepository.save(history);

        if (success) {
            System.out.println("Job " + jobName + " executou com sucesso em " + duration + "ms");
        } else {
            System.out.println("Job " + jobName + " falhou: " + error);
        }
    }

    @Override
    public String getName() {
        return "JobExecutionListener";
    }
}
```

### REST Controller para Jobs

```java
@RestController
@RequestMapping("/api/${api.version}/scheduler/jobs")
public class JobSchedulerController {

    @Autowired
    private SchedulerService schedulerService;

    @PostMapping
    public ResponseEntity<JobDetailDTO> createJob(@Valid @RequestBody CreateJobRequest request)
            throws SchedulerException {
        JobDetailEntity job = schedulerService.scheduleJob(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapToDTO(job));
    }

    @GetMapping
    public ResponseEntity<List<JobDetailDTO>> listJobs() throws SchedulerException {
        List<JobDetailDTO> jobs = schedulerService.listAllJobs();
        return ResponseEntity.ok(jobs);
    }

    @GetMapping("/{jobName}/{group}")
    public ResponseEntity<JobDetailDTO> getJob(
            @PathVariable String jobName,
            @PathVariable String group) throws SchedulerException {
        // ...
        return ResponseEntity.ok(/*...*/);
    }

    @PostMapping("/{jobName}/{group}/execute")
    public ResponseEntity<Void> executeJobNow(
            @PathVariable String jobName,
            @PathVariable String group) throws SchedulerException {
        schedulerService.triggerJobNow(jobName, group);
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/{jobName}/{group}/pause")
    public ResponseEntity<Void> pauseJob(
            @PathVariable String jobName,
            @PathVariable String group) throws SchedulerException {
        schedulerService.pauseJob(jobName, group);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{jobName}/{group}/resume")
    public ResponseEntity<Void> resumeJob(
            @PathVariable String jobName,
            @PathVariable String group) throws SchedulerException {
        schedulerService.resumeJob(jobName, group);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{jobName}/{group}")
    public ResponseEntity<Void> deleteJob(
            @PathVariable String jobName,
            @PathVariable String group) throws SchedulerException {
        schedulerService.deleteJob(jobName, group);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{jobName}/{group}/history")
    public ResponseEntity<List<JobExecutionHistory>> getJobHistory(
            @PathVariable String jobName,
            @PathVariable String group) {
        List<JobExecutionHistory> history =
            schedulerService.getJobExecutionHistory(jobName, group);
        return ResponseEntity.ok(history);
    }
}
```

## 📋 Expressões Cron Comuns

| Expressão Cron | Descrição |
|----------------|-----------|
| `0 0 * * *` | Diariamente à meia-noite |
| `0 */6 * * *` | A cada 6 horas |
| `0 0 * * 0` | Semanalmente aos domingos |
| `0 0 1 * *` | Mensalmente no primeiro dia |
| `0 9-17 * * *` | A cada hora de 9h às 17h |
| `*/15 * * * *` | A cada 15 minutos |

## ⚙️ Configuração

### application.yml

```yaml
spring:
  quartz:
    job-store-type: jdbc
    jdbc:
      initialize-schema: never
    properties:
      org.quartz.scheduler.instanceName: SpringQuartzScheduler
      org.quartz.threadPool.threadCount: 10
      org.quartz.dataSource.quartz.driver: com.mysql.jdbc.Driver
      org.quartz.dataSource.quartz.URL: jdbc:mysql://localhost:3306/quartz
```

## 🧪 Testes

```java
@SpringBootTest
public class SchedulerServiceTest {

    @Autowired
    private SchedulerService schedulerService;

    @Test
    public void testScheduleJob() throws SchedulerException {
        CreateJobRequest request = new CreateJobRequest();
        request.setJobName("test-job");
        request.setJobType("LOG_CLEANUP");
        request.setCronExpression("0 0 * * *");

        JobDetailEntity job = schedulerService.scheduleJob(request);

        assertNotNull(job.getId());
        assertEquals(JobStatus.SCHEDULED, job.getStatus());
    }
}
```

## 📝 Notas

- Jobs devem ser idempotentes (seguro executar múltiplas vezes)
- Usar persistência em BD para sobreviver a reinicializações
- Implementar tratamento de erro robusto
- Monitorar execução com logs e métricas

## 🔍 Referências

- [Quartz Scheduler Documentation](http://www.quartz-scheduler.org/)
- [Spring Scheduling](https://docs.spring.io/spring-framework/docs/current/reference/html/integration.html#scheduling)
- [Cron Expression Generator](https://www.crontabguru.com/)


