# ia-core-quartz-service-model

## 📋 Descrição

Módulo com DTOs para o serviço de agendamento com Quartz. Define estruturas para criar, atualizar e monitorar jobs.

## 🏗️ Estrutura

```
ia-core-quartz-service-model/
├── src/main/java/
│   └── com/ia/core/quartz/service/dto/
│       ├── request/               # CreateJobRequest, UpdateJobRequest
│       ├── response/              # JobDetailDTO, ExecutionResultDTO
│       └── util/
└── pom.xml
```

## 🔑 Responsabilidades

- **CreateJobRequest**: Parâmetros para agendar novo job
- **JobDetailDTO**: Resposta com dados do job
- **ExecutionHistoryDTO**: Histórico de execuções
- **JobStatusDTO**: Status atual do job

## 💡 DTOs Principais

```java
@Data
@Builder
public class CreateJobRequest {

    @NotBlank
    private String jobName;

    @NotBlank
    private String jobGroup;

    @NotBlank
    @Pattern(regexp = "^(0|[1-9]|1[0-9]|2[0-3]) (0|[1-9]|[1-5][0-9]) ...$")
    private String cronExpression;

    @NotBlank
    private String jobType; // LOG_CLEANUP, REPORT_GENERATION, etc.

    private Map<String, Object> parameters;
}

@Data
public class JobDetailDTO {

    private Long id;

    private String jobName;

    private String jobGroup;

    private String jobType;

    private String cronExpression;

    private LocalDateTime nextExecutionTime;

    private LocalDateTime lastExecutionTime;

    private String status;
}
```


