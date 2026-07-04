# ia-core-quartz (ia-core-quartz-model)

## 📋 Descrição

Módulo que define as entidades e DTOs para o sistema de agendamento de tarefas usando Quartz Scheduler. Modelos para jobs, triggers e histórico de execução.

## 🏗️ Estrutura

```
ia-core-quartz/
├── src/main/java/
│   └── com/ia/core/quartz/model/
│       ├── JobDetail.java         # Entidade de job
│       ├── JobExecution.java      # Histórico de execução
│       ├── dto/                   # DTOs
│       └── enums/                 # Enums
└── pom.xml
```

## 🔑 Responsabilidades

- **JobDetail Entity**: Armazena configuração de jobs
- **JobExecution Entity**: Registra cada execução
- **DTOs**: CreateJobRequest, JobDetailDTO, ExecutionHistoryDTO
- **Enums**: JobStatus (SCHEDULED, PAUSED, FAILED), TriggerType

## 🛠️ Tecnologias

- Spring Data JPA
- Jakarta Persistence
- Quartz Scheduler
- Cron Utils (validação)

## 📦 Dependências

- `ia-core-model`
- `org.quartz-scheduler:quartz`

## 💡 Características

- Persistência de jobs em BD
- Rastreamento de execuções
- Suporte a cron expressions
- Histórico com tempo de execução

## 🚀 Exemplo

```java
@Entity
@Table(name = "jobs")
@Data
@Builder
public class JobDetail extends AbstractEntity {

    @Column(nullable = false)
    private String jobName;

    @Column(nullable = false)
    private String jobGroup;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private JobStatus status = JobStatus.SCHEDULED;

    @Column(nullable = false)
    private String cronExpression;

    @Column
    private LocalDateTime nextExecutionTime;

    @Column
    private LocalDateTime lastExecutionTime;
}

@Entity
@Table(name = "job_executions")
@Data
public class JobExecution extends AbstractEntity {

    @ManyToOne
    private JobDetail job;

    @Column(nullable = false)
    private LocalDateTime executionTime;

    @Column(nullable = false)
    private Long durationMs;

    @Column(nullable = false)
    private Boolean success;

    @Column(columnDefinition = "TEXT")
    private String errorMessage;
}
```


