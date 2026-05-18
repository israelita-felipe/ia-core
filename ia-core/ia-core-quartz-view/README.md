# ia-core-quartz-view

## 📋 Descrição

Interface web com Vaadin para gerenciamento de jobs agendados com Quartz. Permite criar, pausar, executar e monitorar tarefas.

## 🏗️ Estrutura

```
ia-core-quartz-view/
├── src/main/java/
│   └── com/ia/core/quartz/view/
│       ├── job/                   # Views de job
│       ├── execution/             # Histórico de execação
│       └── component/             # Componentes
└── pom.xml
```

## 🔑 Responsabilidades

- **JobListView**: Lista de jobs agendados
- **JobFormView**: Criar/editar job com cron expression validator
- **ExecutionHistoryView**: Histórico de execuções
- **JobStatusMonitor**: Dashboard de status dos jobs
- **CronExpressionHelper**: Validador e explicador de cron

## 🛠️ Tecnologias

- Vaadin
- Quartz Scheduler
- Cron Utils

## 💡 Componentes

- `JobGrid`: Grid de jobs com ações
- `CronField`: Input validado de expressão cron
- `ExecutionTimeline`: Cronograma visual de execuções
- `JobStatusIndicator`: Indicador de saúde do job


