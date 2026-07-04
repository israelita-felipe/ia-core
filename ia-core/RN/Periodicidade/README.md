# Regras de Negócio - Periodicidade

## Visão Geral

Este documento define as regras de negócio para o módulo de Periodicidade do ia-core-quartz-model. O módulo Periodicidade gerencia a definição de periodicidades para execução de tarefas agendadas, seguindo o padrão RFC 5545 para especificação de regras de recorrência.

## Referência

- **CDU**: CDU011-Manter-Quartz
- **CDU**: CDU014-Manter-Scheduler
- **Service**: ia-core-quartz-service
- **Módulo**: ia-core-quartz-model

## Regras de Negócio

### PER_001 - PeriodicidadeIntervaloBaseObrigatorioRule

- **Nome**: Periodicidade Intervalo Base Obrigatório
- **Descrição**: O intervalo base (IntervaloTemporal) é obrigatório para definir a periodicidade
- **Critérios**:
    - IntervaloBase não pode ser nulo
    - IntervaloBase deve ter data início válida
    - IntervaloBase deve ter hora início válida
- **Severidade**: ERRO
- **Referência CDU**: RN001

### PER_002 - PeriodicidadeZoneIdPadraoRule

- **Nome**: Periodicidade ZoneId Padrão
- **Descrição**: Define o fuso horário padrão quando não especificado
- **Critérios**:
    - Se ZoneId não for especificado, usar UTC como padrão
    - ZoneId deve ser válido (deve existir em ZoneId.getAvailableZoneIds())
- **Severidade**: AVISO
- **Referência CDU**: RN002

### PER_003 - PeriodicidadeAtivoPadraoRule

- **Nome**: Periodicidade Ativo Padrão
- **Descrição**: Define o status padrão de ativação para periodicidades
- **Critérios**:
    - Se ativo não for especificado, assume true como padrão
    - Periodicidades inativas não geram execuções
- **Severidade**: INFORMATIVO
- **Referência CDU**: RN003

### PER_004 - PeriodicidadeExceptionDatesValidasRule

- **Nome**: Periodicidade Exception Dates Válidas
- **Descrição**: Valida as datas de exceção da periodicidade
- **Critérios**:
    - ExceptionDates não pode ser nulo (pode ser vazio)
    - Cada data em ExceptionDates deve ser válida
    - Datas de exceção devem estar dentro do intervalo base
- **Severidade**: ERRO
- **Referência CDU**: RN004

### PER_005 - PeriodicidadeIncludeDatesValidasRule

- **Nome**: Periodicidade Include Dates Válidas
- **Descrição**: Valida as datas de inclusão da periodicidade
- **Critérios**:
    - IncludeDates não pode ser nulo (pode ser vazio)
    - Cada data em IncludeDates deve ser válida
    - Datas de inclusão devem estar dentro do intervalo base
- **Severidade**: ERRO
- **Referência CDU**: RN005

### PER_006 - PeriodicidadeRegraOpcionalRule

- **Nome**: Periodicidade Regra Opcional
- **Descrição**: A regra de recorrência (Recorrencia) é opcional
- **Critérios**:
    - Recorrencia pode ser nula
    - Se Recorrencia for nula, usa intervalo base simples
    - Se Recorrencia for especificada, deve ser válida
- **Severidade**: AVISO
- **Referência CDU**: RN006

### PER_007 - PeriodicidadeExclusaoRecorrenciaOpcionalRule

- **Nome**: Periodicidade Exclusão Recorrência Opcional
- **Descrição**: A exclusão de recorrência (ExclusaoRecorrencia) é opcional
- **Critérios**:
    - ExclusaoRecorrencia pode ser nula
    - Se ExclusaoRecorrencia for especificada, deve ser válida
    - ExclusaoRecorrencia só se aplica se Recorrencia estiver definida
- **Severidade**: AVISO
- **Referência CDU**: RN007

## Regras de Negócio - Recorrencia

### REC_001 - RecorrenciaFrequenciaObrigatoriaRule

- **Nome**: Recorrência Frequência Obrigatória
- **Descrição**: A frequência é obrigatória para definir a recorrência
- **Critérios**:
    - Frequencia não pode ser nula
    - Frequencia deve ser um valor válido do enum Frequencia
- **Severidade**: ERRO
- **Referência CDU**: RN008

### REC_002 - RecorrenciaIntervaloMinimoRule

- **Nome**: Recorrência Intervalo Mínimo
- **Descrição**: Define o intervalo mínimo entre recorrências
- **Critérios**:
    - Intervalo deve ser maior que 0
    - Intervalo mínimo depende da frequência (ex: 1 para DIARIA, 7 para SEMANAL)
- **Severidade**: ERRO
- **Referência CDU**: RN009

### REC_003 - RecorrenciaDiasSemanaValidosRule

- **Nome**: Recorrência Dias Semana Válidos
- **Descrição**: Valida os dias da semana especificados
- **Critérios**:
    - DiasSemana não pode ser nulo (pode ser vazio)
    - Cada dia em DiasSemana deve ser um valor válido do enum DayOfWeek
    - DiasSemana só se aplica se frequência for SEMANAL
- **Severidade**: ERRO
- **Referência CDU**: RN010

### REC_004 - RecorrenciaDiasMesValidosRule

- **Nome**: Recorrência Dias Mês Válidos
- **Descrição**: Valida os dias do mês especificados
- **Critérios**:
    - DiasMes não pode ser nulo (pode ser vazio)
    - Cada dia em DiasMes deve estar entre 1 e 31
    - DiasMes só se aplica se frequência for MENSAL
- **Severidade**: ERRO
- **Referência CDU**: RN011

### REC_005 - RecorrenciaMesesValidosRule

- **Nome**: Recorrência Meses Válidos
- **Descrição**: Valida os meses especificados
- **Critérios**:
    - Meses não pode ser nulo (pode ser vazio)
    - Cada mês em Meses deve ser um valor válido do enum Month
    - Meses só se aplica se frequência for ANUAL
- **Severidade**: ERRO
- **Referência CDU**: RN012

## Regras de Negócio - ExclusaoRecorrencia

### EXC_001 - ExclusaoRecorrenciaDatasValidasRule

- **Nome**: Exclusão Recorrência Datas Válidas
- **Descrição**: Valida as datas de exclusão da recorrência
- **Critérios**:
    - Datas não pode ser nulo (pode ser vazio)
    - Cada data em Datas deve ser válida
    - Datas de exclusão devem estar no futuro ou presente
- **Severidade**: ERRO
- **Referência CDU**: RN013

### EXC_002 - ExclusaoRecorrenciaMotivoOpcionalRule

- **Nome**: Exclusão Recorrência Motivo Opcional
- **Descrição**: O motivo da exclusão é opcional
- **Critérios**:
    - Motivo pode ser nulo ou vazio
    - Se especificado, não pode exceder 500 caracteres
- **Severidade**: AVISO
- **Referência CDU**: RN014

## Regras de Negócio - IntervaloTemporal

### INT_001 - IntervaloTemporalDataInicioObrigatoriaRule

- **Nome**: Intervalo Temporal Data Início Obrigatória
- **Descrição**: A data de início é obrigatória
- **Critérios**:
    - DataInicio não pode ser nula
    - DataInicio deve ser válida
- **Severidade**: ERRO
- **Referência CDU**: RN015

### INT_002 - IntervaloTemporalHoraInicioObrigatoriaRule

- **Nome**: Intervalo Temporal Hora Início Obrigatória
- **Descrição**: A hora de início é obrigatória
- **Critérios**:
    - HoraInicio não pode ser nula
    - HoraInicio deve ser válida
- **Severidade**: ERRO
- **Referência CDU**: RN016

### INT_003 - IntervaloTemporalDataFimOpcionalRule

- **Nome**: Intervalo Temporal Data Fim Opcional
- **Descrição**: A data de fim é opcional
- **Critérios**:
    - DataFim pode ser nula
    - Se especificada, deve ser posterior a DataInicio
    - Se especificada, HoraFim também deve ser especificada
- **Severidade**: AVISO
- **Referência CDU**: RN017

### INT_004 - IntervaloTemporalHoraFimOpcionalRule

- **Nome**: Intervalo Temporal Hora Fim Opcional
- **Descrição**: A hora de fim é opcional
- **Critérios**:
    - HoraFim pode ser nula
    - Se especificada, deve ser válida
    - Se DataFim for especificada, HoraFim também deve ser especificada
- **Severidade**: AVISO
- **Referência CDU**: RN018

## Padrão de Implementação

As regras de negócio devem seguir o padrão `BusinessRule<T>`:

```java
@ValidatorScope
public class PeriodicidadeIntervaloBaseObrigatorioRule implements BusinessRule<PeriodicidadeDTO> {
    private static final String CODE = "PER_001";
    private final Translator translator;

    @Override
    public String getCode() {
        return CODE;
    }

    @Override
    public void validate(PeriodicidadeDTO dto, ValidationResult result) {
        if (dto.getIntervaloBase() == null) {
            result.addError("intervaloBase",
                translator.getMessage("periodicidade.intervaloBase.obrigatorio"));
        }
    }
}
```

## Referências

- **CDU**: Manter-Quartz (CDU011)
- **CDU**: Manter-Scheduler (CDU014)
- **ADR-054**: Usar RN para Documentação de Regras de Negócio
- **RFC 5545**: Internet Calendaring and Scheduling Core Object Specification
- **Service Base**: `com.ia.core.service.rules.BusinessRule`
