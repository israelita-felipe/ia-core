# Casos de Teste Quartz por Classe e Camada

## Resumo

- **Casos de teste Markdown:** 192
- **Classes cobertas:** 92
- **Casos de contrato Java:** 13 classes de teste executadas por Maven
- **Camadas cobertas:** Model, Repository, Mapper, ServiceModel, Service, API/REST e View/Client

Este diretório contém casos de teste documentados para classes, DTOs e modelos dos módulos `ia-core-quartz-*`, organizados por camada da stack:

- Model
- Repository
- Mapper
- ServiceModel
- Service
- API/REST
- View/Client

## Matriz de cobertura

| Classe | Domínio | CDU | Casos presentes | Lacunas |
|--------|---------|-----|-----------------|---------|
| AbstractJob | Jobs | [CDU/Manter-Quartz: Manter Quartz](../../../../CDU/Manter-Quartz/README.md) | 1/1 | Completo |
| CoreQuartzConfig | Quartz | [CDU/Manter-Quartz: Manter Quartz](../../../../CDU/Manter-Quartz/README.md) | 1/1 | Completo |
| CoreSpringBeanJobFactory | Jobs | [CDU/Manter-Quartz: Manter Quartz](../../../../CDU/Manter-Quartz/README.md) | 1/1 | Completo |
| DateSetView | Periodicidade/Recorrência | [CDU/Manter-Quartz: Manter Quartz](../../../../CDU/Manter-Quartz/README.md) | 1/1 | Completo |
| DateTimeAdapter | ServiceModel | [CDU/Manter-Quartz: Manter Quartz](../../../../CDU/Manter-Quartz/README.md) | 1/1 | Completo |
| DayOfWeekConverter | Periodicidade/Recorrência | [CDU/Manter-Quartz: Manter Quartz](../../../../CDU/Manter-Quartz/README.md) | 1/1 | Completo |
| ExclusaoRecorrencia | Periodicidade/Recorrência | [CDU/Manter-Quartz: Manter Quartz](../../../../CDU/Manter-Quartz/README.md) | 7/7 | Completo |
| ExclusaoRecorrenciaDTO | ServiceModel | [CDU/Manter-Quartz: Manter Quartz](../../../../CDU/Manter-Quartz/README.md) | 7/7 | Completo |
| Frequencia | Periodicidade/Recorrência | [CDU/Manter-Quartz: Manter Quartz](../../../../CDU/Manter-Quartz/README.md) | 1/1 | Completo |
| FrequenciaConverter | Periodicidade/Recorrência | [CDU/Manter-Quartz: Manter Quartz](../../../../CDU/Manter-Quartz/README.md) | 1/1 | Completo |
| ICalendarSerializer | ServiceModel | [CDU/Manter-Quartz: Manter Quartz](../../../../CDU/Manter-Quartz/README.md) | 1/1 | Completo |
| IntegerSetView | Periodicidade/Recorrência | [CDU/Manter-Quartz: Manter Quartz](../../../../CDU/Manter-Quartz/README.md) | 1/1 | Completo |
| IntervaloTemporal | Periodicidade/Recorrência | [CDU/Manter-Quartz: Manter Quartz](../../../../CDU/Manter-Quartz/README.md) | 7/7 | Completo |
| IntervaloTemporalDTO | ServiceModel | [CDU/Manter-Quartz: Manter Quartz](../../../../CDU/Manter-Quartz/README.md) | 7/7 | Completo |
| IntervaloTemporalFormView | Periodicidade/Recorrência | [CDU/Manter-Quartz: Manter Quartz](../../../../CDU/Manter-Quartz/README.md) | 1/1 | Completo |
| IntervaloTemporalFormViewModel | Periodicidade/Recorrência | [CDU/Manter-Quartz: Manter Quartz](../../../../CDU/Manter-Quartz/README.md) | 1/1 | Completo |
| IntervaloTemporalFormViewModelConfig | Periodicidade/Recorrência | [CDU/Manter-Quartz: Manter Quartz](../../../../CDU/Manter-Quartz/README.md) | 1/1 | Completo |
| JobSchedulerChecker | Jobs | [CDU/Manter-Quartz: Manter Quartz](../../../../CDU/Manter-Quartz/README.md) | 1/1 | Completo |
| JobsListener | Jobs | [CDU/Manter-Quartz: Manter Quartz](../../../../CDU/Manter-Quartz/README.md) | 1/1 | Completo |
| LibRecurOccurrenceCalculator | ServiceModel | [CDU/Manter-Quartz: Manter Quartz](../../../../CDU/Manter-Quartz/README.md) | 1/1 | Completo |
| MonthConverter | Periodicidade/Recorrência | [CDU/Manter-Quartz: Manter Quartz](../../../../CDU/Manter-Quartz/README.md) | 1/1 | Completo |
| OccurrenceCalculator | ServiceModel | [CDU/Manter-Quartz: Manter Quartz](../../../../CDU/Manter-Quartz/README.md) | 1/1 | Completo |
| OccurrenceCalculatorRunner | ServiceModel | [CDU/Manter-Quartz: Manter Quartz](../../../../CDU/Manter-Quartz/README.md) | 1/1 | Completo |
| Periodicidade | Periodicidade/Recorrência | [CDU/Manter-Quartz: Manter Quartz](../../../../CDU/Manter-Quartz/README.md) | 7/7 | Completo |
| PeriodicidadeDTO | ServiceModel | [CDU/Manter-Quartz: Manter Quartz](../../../../CDU/Manter-Quartz/README.md) | 7/7 | Completo |
| PeriodicidadeFormView | Periodicidade/Recorrência | [CDU/Manter-Quartz: Manter Quartz](../../../../CDU/Manter-Quartz/README.md) | 1/1 | Completo |
| PeriodicidadeFormViewModel | Periodicidade/Recorrência | [CDU/Manter-Quartz: Manter Quartz](../../../../CDU/Manter-Quartz/README.md) | 1/1 | Completo |
| PeriodicidadeFormViewModelConfig | Periodicidade/Recorrência | [CDU/Manter-Quartz: Manter Quartz](../../../../CDU/Manter-Quartz/README.md) | 1/1 | Completo |
| PeriodicidadeFormatter | ServiceModel | [CDU/Manter-Quartz: Manter Quartz](../../../../CDU/Manter-Quartz/README.md) | 1/1 | Completo |
| PeriodicidadeMapper | Periodicidade/Recorrência | [CDU/Manter-Quartz: Manter Quartz](../../../../CDU/Manter-Quartz/README.md) | 2/2 | Completo |
| PeriodicidadeRepository | Periodicidade/Recorrência | [CDU/Manter-Quartz: Manter Quartz](../../../../CDU/Manter-Quartz/README.md) | 1/1 | Completo |
| PeriodicidadeScheduleBuilder | Periodicidade/Recorrência | [CDU/Manter-Quartz: Manter Quartz](../../../../CDU/Manter-Quartz/README.md) | 2/2 | Completo |
| PeriodicidadeSearchRequest | ServiceModel | [CDU/Manter-Quartz: Manter Quartz](../../../../CDU/Manter-Quartz/README.md) | 7/7 | Completo |
| PeriodicidadeTranslator | ServiceModel | [CDU/Manter-Quartz: Manter Quartz](../../../../CDU/Manter-Quartz/README.md) | 1/1 | Completo |
| PeriodicidadeTrigger | Periodicidade/Recorrência | [CDU/Manter-Quartz: Manter Quartz](../../../../CDU/Manter-Quartz/README.md) | 1/1 | Completo |
| QuartzClient | Quartz | [CDU/Manter-Quartz: Manter Quartz](../../../../CDU/Manter-Quartz/README.md) | 1/1 | Completo |
| QuartzJobClient | Jobs | [CDU/Manter-Quartz: Manter Quartz](../../../../CDU/Manter-Quartz/README.md) | 1/1 | Completo |
| QuartzJobDTO | ServiceModel | [CDU/Manter-Quartz: Manter Quartz](../../../../CDU/Manter-Quartz/README.md) | 7/7 | Completo |
| QuartzJobFormView | Jobs | [CDU/Manter-Quartz: Manter Quartz](../../../../CDU/Manter-Quartz/README.md) | 1/1 | Completo |
| QuartzJobFormViewModel | Jobs | [CDU/Manter-Quartz: Manter Quartz](../../../../CDU/Manter-Quartz/README.md) | 1/1 | Completo |
| QuartzJobFormViewModelConfig | Jobs | [CDU/Manter-Quartz: Manter Quartz](../../../../CDU/Manter-Quartz/README.md) | 1/1 | Completo |
| QuartzJobInstanceDTO | ServiceModel | [CDU/Manter-Quartz: Manter Quartz](../../../../CDU/Manter-Quartz/README.md) | 7/7 | Completo |
| QuartzJobInstanceSearchRequest | ServiceModel | [CDU/Manter-Quartz: Manter Quartz](../../../../CDU/Manter-Quartz/README.md) | 7/7 | Completo |
| QuartzJobListView | Jobs | [CDU/Manter-Quartz: Manter Quartz](../../../../CDU/Manter-Quartz/README.md) | 1/1 | Completo |
| QuartzJobManager | Jobs | [CDU/Manter-Quartz: Manter Quartz](../../../../CDU/Manter-Quartz/README.md) | 1/1 | Completo |
| QuartzJobManagerConfig | Jobs | [CDU/Manter-Quartz: Manter Quartz](../../../../CDU/Manter-Quartz/README.md) | 1/1 | Completo |
| QuartzJobPageView | Jobs | [CDU/Manter-Quartz: Manter Quartz](../../../../CDU/Manter-Quartz/README.md) | 1/1 | Completo |
| QuartzJobPageViewModel | Jobs | [CDU/Manter-Quartz: Manter Quartz](../../../../CDU/Manter-Quartz/README.md) | 1/1 | Completo |
| QuartzJobPageViewModelConfig | Jobs | [CDU/Manter-Quartz: Manter Quartz](../../../../CDU/Manter-Quartz/README.md) | 1/1 | Completo |
| QuartzJobSearchRequest | ServiceModel | [CDU/Manter-Quartz: Manter Quartz](../../../../CDU/Manter-Quartz/README.md) | 7/7 | Completo |
| QuartzJobTranslator | ServiceModel | [CDU/Manter-Quartz: Manter Quartz](../../../../CDU/Manter-Quartz/README.md) | 1/1 | Completo |
| QuartzJobTriggerDTO | ServiceModel | [CDU/Manter-Quartz: Manter Quartz](../../../../CDU/Manter-Quartz/README.md) | 7/7 | Completo |
| QuartzJobTriggerSearchRequest | ServiceModel | [CDU/Manter-Quartz: Manter Quartz](../../../../CDU/Manter-Quartz/README.md) | 7/7 | Completo |
| QuartzJobUseCase | ServiceModel | [CDU/Manter-Quartz: Manter Quartz](../../../../CDU/Manter-Quartz/README.md) | 1/1 | Completo |
| QuartzManager | Quartz | [CDU/Manter-Quartz: Manter Quartz](../../../../CDU/Manter-Quartz/README.md) | 1/1 | Completo |
| QuartzManagerConfig | Quartz | [CDU/Manter-Quartz: Manter Quartz](../../../../CDU/Manter-Quartz/README.md) | 1/1 | Completo |
| QuartzModel | Quartz | [CDU/Manter-Quartz: Manter Quartz](../../../../CDU/Manter-Quartz/README.md) | 1/1 | Completo |
| RRuleValidator | ServiceModel | [CDU/Manter-Quartz: Manter Quartz](../../../../CDU/Manter-Quartz/README.md) | 1/1 | Completo |
| Recorrencia | Periodicidade/Recorrência | [CDU/Manter-Quartz: Manter Quartz](../../../../CDU/Manter-Quartz/README.md) | 7/7 | Completo |
| RecorrenciaDTO | ServiceModel | [CDU/Manter-Quartz: Manter Quartz](../../../../CDU/Manter-Quartz/README.md) | 7/7 | Completo |
| RecorrenciaFormView | Periodicidade/Recorrência | [CDU/Manter-Quartz: Manter Quartz](../../../../CDU/Manter-Quartz/README.md) | 1/1 | Completo |
| RecorrenciaFormViewModel | Periodicidade/Recorrência | [CDU/Manter-Quartz: Manter Quartz](../../../../CDU/Manter-Quartz/README.md) | 1/1 | Completo |
| RecorrenciaFormViewModelConfig | Periodicidade/Recorrência | [CDU/Manter-Quartz: Manter Quartz](../../../../CDU/Manter-Quartz/README.md) | 1/1 | Completo |
| RecurrenceRuleAdapter | ServiceModel | [CDU/Manter-Quartz: Manter Quartz](../../../../CDU/Manter-Quartz/README.md) | 1/1 | Completo |
| SchedulerConfig | Agendamento | [CDU/Manter-Quartz: Manter Quartz](../../../../CDU/Manter-Quartz/README.md) | 7/7 | Completo |
| SchedulerConfigDTO | ServiceModel | [CDU/Manter-Quartz: Manter Quartz](../../../../CDU/Manter-Quartz/README.md) | 7/7 | Completo |
| SchedulerConfigFormView | Agendamento | [CDU/Manter-Quartz: Manter Quartz](../../../../CDU/Manter-Quartz/README.md) | 1/1 | Completo |
| SchedulerConfigFormViewModel | Agendamento | [CDU/Manter-Quartz: Manter Quartz](../../../../CDU/Manter-Quartz/README.md) | 1/1 | Completo |
| SchedulerConfigFormViewModelConfig | Agendamento | [CDU/Manter-Quartz: Manter Quartz](../../../../CDU/Manter-Quartz/README.md) | 1/1 | Completo |
| SchedulerConfigListView | Agendamento | [CDU/Manter-Quartz: Manter Quartz](../../../../CDU/Manter-Quartz/README.md) | 1/1 | Completo |
| SchedulerConfigMapper | Agendamento | [CDU/Manter-Quartz: Manter Quartz](../../../../CDU/Manter-Quartz/README.md) | 2/2 | Completo |
| SchedulerConfigPageView | Agendamento | [CDU/Manter-Quartz: Manter Quartz](../../../../CDU/Manter-Quartz/README.md) | 1/1 | Completo |
| SchedulerConfigPageViewModel | Agendamento | [CDU/Manter-Quartz: Manter Quartz](../../../../CDU/Manter-Quartz/README.md) | 1/1 | Completo |
| SchedulerConfigPageViewModelConfig | Agendamento | [CDU/Manter-Quartz: Manter Quartz](../../../../CDU/Manter-Quartz/README.md) | 1/1 | Completo |
| SchedulerConfigRepository | Agendamento | [CDU/Manter-Quartz: Manter Quartz](../../../../CDU/Manter-Quartz/README.md) | 1/1 | Completo |
| SchedulerConfigSearchRequest | ServiceModel | [CDU/Manter-Quartz: Manter Quartz](../../../../CDU/Manter-Quartz/README.md) | 7/7 | Completo |
| SchedulerConfigService | Agendamento | [CDU/Manter-Quartz: Manter Quartz](../../../../CDU/Manter-Quartz/README.md) | 1/1 | Completo |
| SchedulerConfigServiceConfig | Agendamento | [CDU/Manter-Quartz: Manter Quartz](../../../../CDU/Manter-Quartz/README.md) | 1/1 | Completo |
| SchedulerConfigSummary | Agendamento | [CDU/Manter-Quartz: Manter Quartz](../../../../CDU/Manter-Quartz/README.md) | 1/1 | Completo |
| SchedulerConfigTranslator | ServiceModel | [CDU/Manter-Quartz: Manter Quartz](../../../../CDU/Manter-Quartz/README.md) | 1/1 | Completo |
| SchedulerConfigTrigger | Agendamento | [CDU/Manter-Quartz: Manter Quartz](../../../../CDU/Manter-Quartz/README.md) | 7/7 | Completo |
| SchedulerConfigTriggerCollectionPageView | Agendamento | [CDU/Manter-Quartz: Manter Quartz](../../../../CDU/Manter-Quartz/README.md) | 1/1 | Completo |
| SchedulerConfigTriggerCollectionPageViewModel | Agendamento | [CDU/Manter-Quartz: Manter Quartz](../../../../CDU/Manter-Quartz/README.md) | 1/1 | Completo |
| SchedulerConfigTriggerCollectionPageViewModelConfig | Agendamento | [CDU/Manter-Quartz: Manter Quartz](../../../../CDU/Manter-Quartz/README.md) | 1/1 | Completo |
| SchedulerConfigTriggerDTO | ServiceModel | [CDU/Manter-Quartz: Manter Quartz](../../../../CDU/Manter-Quartz/README.md) | 7/7 | Completo |
| SchedulerConfigTriggerFormView | Agendamento | [CDU/Manter-Quartz: Manter Quartz](../../../../CDU/Manter-Quartz/README.md) | 1/1 | Completo |
| SchedulerConfigTriggerListView | Agendamento | [CDU/Manter-Quartz: Manter Quartz](../../../../CDU/Manter-Quartz/README.md) | 1/1 | Completo |
| SchedulerConfigTriggerSearchRequest | ServiceModel | [CDU/Manter-Quartz: Manter Quartz](../../../../CDU/Manter-Quartz/README.md) | 7/7 | Completo |
| SchedulerConfigTriggerTranslator | ServiceModel | [CDU/Manter-Quartz: Manter Quartz](../../../../CDU/Manter-Quartz/README.md) | 1/1 | Completo |
| SchedulerRegistry | Agendamento | [CDU/Manter-Quartz: Manter Quartz](../../../../CDU/Manter-Quartz/README.md) | 1/1 | Completo |
| SchedulerUseCase | ServiceModel | [CDU/Manter-Quartz: Manter Quartz](../../../../CDU/Manter-Quartz/README.md) | 1/1 | Completo |
| TriggersListener | Quartz | [CDU/Manter-Quartz: Manter Quartz](../../../../CDU/Manter-Quartz/README.md) | 1/1 | Completo |

## Padrão de nomenclatura

Os arquivos seguem o padrão:

```text
<NomeClasse>-<Camada>-Layer.md
```

Exemplo: `QuartzJobDTO-ServiceModel-Layer.md`.

## Aderência a ADRs

Todos os casos devem conter a seção `## Aderência a ADRs`, com metadados, matriz de conformidade, critérios de aceitação, evidências esperadas e referências ADR.

## Referências

- [ADR-010 - Padrões de Nomenclatura](../../../../ADR/010-nomenclature-standards.md)
- [ADR-012 - Padrões de Teste Automatizado](../../../../ADR/012-testing-patterns.md)
- [ADR-050 - Diretrizes Gerais de Padronização](../../../../ADR/050-standardization-guidelines.md)
- [ADR-052 - MADR e Linguagem Normativa](../../../../ADR/052-adopt-madr-and-rfc-normative-language-for-adrs.md)
