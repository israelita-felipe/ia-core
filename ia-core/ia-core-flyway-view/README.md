# ia-core-flyway-view

## 📋 Descrição

Interface web com Vaadin para gerenciamento de migrações de BD com Flyway. Monitora schema e histórico de mudanças.

## 🏗️ Estrutura

```
ia-core-flyway-view/
├── src/main/java/
│   └── com/ia/core/flyway/view/
│       ├── migration/             # Views de migração
│       ├── validation/            # Views de validação
│       └── component/             # Componentes
└── pom.xml
```

## 🔑 Responsabilidades

- **MigrationHistoryView**: Histórico de migrações
- **SchemaValidationView**: Validar BD
- **MigrationStatusView**: Status atual do schema
- **MigrationLogsView**: Logs de execução

## 🛠️ Tecnologias

- Vaadin
- Flyway
- Spring Boot

## 💡 Componentes

- `MigrationGrid`: Grid com histórico
- `SchemaInfoCard`: Informações do schema
- `ValidationReport`: Relatório de validação


