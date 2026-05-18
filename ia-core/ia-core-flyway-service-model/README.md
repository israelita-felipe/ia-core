# ia-core-flyway-service-model

## 📋 Descrição

DTOs para serviço de migrações de BD com Flyway. Define estruturas para requisições de migração e respostas de status.

## 🏗️ Estrutura

```
ia-core-flyway-service-model/
├── src/main/java/
│   └── com/ia/core/flyway/service/dto/
│       ├── request/               # MigrationRequest
│       ├── response/              # MigrationResult, ValidationResult
│       └── util/
└── pom.xml
```

## 🔑 Responsabilidades

- **MigrationResult**: Resultado de operação de migração
- **ValidationResult**: Resultado de validação de BD
- **MigrationHistoryDTO**: Registro de migrações
- **SchemaInfoDTO**: Informações do schema atual

## 💡 DTOs

```java
@Data
@Builder
public class MigrationResult {

    private Boolean success;

    private String message;

    private Integer migrationsApplied;

    private List<String> errors;
}

@Data
public class MigrationHistoryDTO {

    private String version;

    private String description;

    private LocalDateTime executedAt;

    private String executedBy;

    private Long executionTime;

    private Boolean success;
}
```


