# Regras de Negócio - Módulo Scheduler (Quartz)

## Visão Geral
Este documento define as regras de negócio implementadas no módulo de Agendamento (Quartz) do ia-core-apps, especificamente para as entidades SchedulerConfig e SchedulerConfigTrigger do ia-core-quartz-model.

## Referência
- **CDU**: CDU014-Manter-Scheduler
- **Service**: ia-core-quartz-service
- **Módulo**: ia-core-quartz-model

## Regras de Negócio

### SCH_001 - SchedulerPeriodicidadeRFC5545Rule
- **Nome**: Scheduler Periodicidade RFC5545
- **Descrição**: Garante que a periodicidade siga o padrão RFC5545
- **Critérios**:
  - Periodicidade deve ser válida segundo RFC5545
  - RRULE é usado para definição de recorrências
- **Severidade**: ERRO
- **Referência CDU**: RN001

### SCH_002 - SchedulerDataInicioObrigatoriaRule
- **Nome**: Scheduler Data Início Obrigatória
- **Descrição**: Garante que a data de início seja informada
- **Critérios**:
  - Data de início é obrigatória
  - Data de início deve ser válida
- **Severidade**: ERRO
- **Referência CDU**: RN002

### SCH_003 - SchedulerJobImplementadoRule
- **Nome**: Scheduler Job Implementado
- **Descrição**: Garante que o job esteja implementado no sistema
- **Critérios**:
  - Job deve existir no classpath
  - Job deve ter construtor padrão
- **Severidade**: ERRO
- **Referência CDU**: RN003

### SCH_004 - SchedulerExecucoesFalhasRegistradasRule
- **Nome**: Scheduler Execuções Falhas Registradas
- **Descrição**: Garante que execuções falhadas sejam registradas
- **Critérios**:
  - Falhas são registradas no histórico
  - Erros são logados para análise
- **Severidade**: INFO
- **Referência CDU**: RN004

## Padrão de Implementação

As regras de negócio devem seguir o padrão `BusinessRule<T>`:

```java
@ValidatorScope
public class SchedulerJobClassValidaRule implements BusinessRule<SchedulerConfigDTO> {
    private static final String CODE = "SCH_001";
    private final Translator translator;

    @Override
    public String getCode() {
        return CODE;
    }

    @Override
    public void validate(SchedulerConfigDTO config, ValidationResult result) {
        if (config.getJobClassName() == null || config.getJobClassName().trim().isEmpty()) {
            result.addError("jobClassName",
                translator.getMessage("scheduler.jobClassName.obrigatorio"));
        }
    }
}
```

## Referências

- **CDU**: Manter-Quartz (CDU011)
- **CDU**: Manter-Scheduler (CDU014)
- **ADR-054**: Usar RN para Documentação de Regras de Negócio
- **Documentação do Quartz**: https://www.quartz-scheduler.org/documentation/
- **Service Base**: `com.ia.core.service.rules.BusinessRule`
