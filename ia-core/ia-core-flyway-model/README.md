# ia-core-flyway-model

## 📋 Descrição

Módulo que define entidades para rastreamento de migrações de banco de dados e histórico de mudanças de schema com Flyway.

## 🏗️ Estrutura

```
ia-core-flyway-model/
├── src/main/java/
│   └── com/ia/core/flyway/model/
│       ├── MigrationHistory.java  # Arquivo histórico
│       ├── SchemaVersion.java    # Versão do schema
│       └── dto/                   # DTOs
└── pom.xml
```

## 🔑 Responsabilidades

- **MigrationHistory**: Registra cada migração executada
- **SchemaVersion**: Controla versão atual do BD
- **Validação de esquema**: Verifica integridade

## 🛠️ Tecnologias

- Spring Data JPA
- Flyway
- Jakarta Persistence

## 💡 Características

- Auditoria completa de mudanças
- Versioning de schema
- Rastreamento de quem/quando executou
- Suporte para rollback


