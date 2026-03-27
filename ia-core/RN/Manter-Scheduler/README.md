# Regras de Negócio - Módulo Scheduler (Quartz)

## Visão Geral
Este documento define as regras de negócio para o módulo de Agendamento (Quartz) do ia-core-apps.

## Entidades

### SchedulerConfig
Configuração de agendamento de tarefas (jobs) no Quartz.

#### Regras Implementadas
*Nenhuma regra implementada atualmente - necessário implementar*

#### Regras a Implementar

##### SCH_001 - SchedulerJobClassValidaRule
- **Nome**: Scheduler Job Class Válida
- **Descrição**: Verifica se a classe de job especificada existe e implementa a interface correta
- **Critérios**:
  - Classe deve existir no classpath
  - Deve implementar a interface `Job` do Quartz
  - Deve ter construtor sem argumentos
- **Severidade**: ERRO

##### SCH_002 - SchedulerNomeUnicoRule
- **Nome**: Scheduler Nome Único
- **Descrição**: Garante que o nome do job seja único no sistema
- **Critérios**:
  - Nome do job não pode ser duplicado
  - Comparação case-insensitive
- **Severidade**: ERRO

##### SCH_003 - SchedulerPeriodicidadeValidaRule
- **Nome**: Scheduler Periodicidade Válida
- **Descrição**: Valida os parâmetros de periodicidade do agendamento
- **Critérios**:
  - Para periodicidade DIARIA: hora válida (0-23)
  - Para periodicidade SEMANAL: dia da semana válido (1-7)
  - Para periodicidade MENSAL: dia do mês válido (1-31)
  - Data de início não pode ser anterior à data atual
- **Severidade**: ERRO

##### SCH_004 - SchedulerDataFuturaRule
- **Nome**: Scheduler Data Futura
- **Descrição**: Garante que a data de início seja válida
- **Critérios**:
  - Data de início deve ser futura ou atual
  - Data fim deve ser maior que data início (se especificada)
- **Severidade**: ERRO

##### SCH_005 - SchedulerJobAtivoRule
- **Nome**: Scheduler Job Ativo
- **Descrição**: Controla ativação/desativação de jobs
- **Critérios**:
  - Não pode desativar job em execução
  - Ao ativar, agenda imediatamente se data início for anterior à atual
- **Severidade**: AVISO

##### SCH_006 - SchedulerOverlapRule
- **Nome**: Scheduler Execução Simultânea
- **Descrição**: Controla se job pode ter execuções sobrepostas
- **Critérios**:
  - Se `allowConcurrent` = false, não inicia nova execução se anterior ainda estiver em andamento
  - Implementar via configuração `JobConcurrentExecutionDisallowed`
- **Severidade**: AVISO

### Periodicidade
Definição de periodicidade para execução de jobs.

#### Regras a Implementar

##### PER_001 - PeriodicidadeIntervaloMinimoRule
- **Nome**: Periodicidade Intervalo Mínimo
- **Descrição**: Garante intervalo mínimo entre execuções
- **Critérios**:
  - Intervalo mínimo de 1 minuto para agendamentos repetitiveis
  - Para jobs de longa duração, sugerir intervalo maior
- **Severidade**: AVISO

##### PER_002 - PeriodicidadeHoraValidaRule
- **Nome**: Periodicidade Hora Válida
- **Descrição**: Valida hora para execução de jobs diários
- **Critérios**:
  - Hora deve estar no formato HH:mm válido
  - Parafusos de horário de verão devem ser considerados
- **Severidade**: ERRO

## Padrão de Implementação

As regras de negócio devem seguir o padrão `BusinessRule<T>`:

```java
public class SchedulerJobClassValidaRule implements BusinessRule<SchedulerConfigDTO> {
    @Override
    public String getCode() {
        return "SCH_001";
    }

    @Override
    public void validate(SchedulerConfigDTO config, ValidationResult result) {
        // Implementação
    }
}
```

## Referências

- Documentação do Quartz: https://www.quartz-scheduler.org/documentation/
- Periodicidade RFC5545: `docs/PERIODICIDADE_RFC5545_USO.md`
- Service Base: `com.ia.core.service.rules.BusinessRule`