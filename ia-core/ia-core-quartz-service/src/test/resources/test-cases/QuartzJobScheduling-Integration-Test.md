# Caso de Teste: Quartz Job Scheduling - Integração

## Descrição
Caso de teste de integração para validar fluxos completos de agendamento e execução de jobs no módulo Quartz.

## Componentes Testados
- `com.ia.core.quartz.service.job.QuartzJobService`
- `com.ia.core.quartz.service.scheduler.SchedulerConfigService`
- `com.ia.core.quartz.service.periodicidade.PeriodicidadeService`
- `com.ia.core.quartz.service.recorrencia.ExclusaoRecorrenciaService`
- `com.ia.core.quartz.service.model.job.dto.QuartzJobInstanceDTO`
- `com.ia.core.quartz.service.model.scheduler.dto.SchedulerConfigDTO`

## Stack do Quartz
| Camada | Componente | Status |
|--------|------------|--------|
| Model | QuartzJobInstance, SchedulerConfig, Periodicidade, ExclusaoRecorrencia | Implementado |
| Repository | QuartzJobInstanceRepository, SchedulerConfigRepository | Implementado |
| Mapper | QuartzJobMapper, SchedulerConfigMapper | Implementado |
| ServiceModel | QuartzJobInstanceDTO, SchedulerConfigDTO, PeriodicidadeDTO, ExclusaoRecorrenciaDTO | Implementado |
| Service | QuartzJobService, SchedulerConfigService, PeriodicidadeService, ExclusaoRecorrenciaService | Implementado |
| API/REST | Não implementado no módulo ia-core-quartz-rest | Não implementado |
| View/Client | SchedulerManager, JobManager | Implementado |

## Objetivo
Documentar e validar fluxos de integração de agendamento e execução de jobs, testando a interação entre múltiplos serviços e componentes da stack do `ia-core-quartz-*`.

## Fluxo do Teste
1. Dado o contexto de agendamento `Job Scheduling` no domínio `Manter Quartz`.
2. Quando fluxos de integração são executados envolvendo múltiplos serviços.
3. Então o comportamento deve validar a interação correta entre QuartzJobService, SchedulerConfigService, PeriodicidadeService e ExclusaoRecorrenciaService.
4. E deve manter rastreabilidade com [CDU/Manter-Quartz: Manter Quartz](../../../../CDU/Manter-Quartz/README.md).
5. E deve registrar falhas, exceções ou lacunas de implementação sem expor dados sensíveis.

## Cenários

### Cenário 1: Fluxo completo de criação e agendamento de job
**Given**: Uma configuração de scheduler válida e um job definido.
**When**: O job é criado e agendado com periodicidade.
**Then**: Deve criar a instância do job no banco de dados.
**And**: Deve agendar o job no Quartz Scheduler.
**And**: Deve configurar os triggers corretamente.
**And**: Deve registrar a operação de criação.

### Cenário 2: Fluxo de execução de job com periodicidade
**Given**: Um job agendado com periodicidade recorrente.
**When**: O scheduler executa o job.
**Then**: Deve executar a lógica do job corretamente.
**And**: Deve registrar a execução no banco de dados.
**And**: Deve calcular a próxima execução corretamente.
**And**: Deve respeitar exclusões de recorrência se configuradas.

### Cenário 3: Fluxo de exclusão de recorrência
**Given**: Um job agendado com periodicidade recorrente.
**When**: Uma exclusão de recorrência é adicionada.
**Then**: Deve registrar a exclusão no banco de dados.
**And**: Deve ajustar o agendamento para pular a data excluída.
**And**: Deve recalcular as próximas execuções.
**And**: O job não deve executar na data excluída.

### Cenário 4: Fluxo de pausa e retomada de job
**Given**: Um job agendado ativo.
**When**: O job é pausado.
**Then**: Deve atualizar o status do job para pausado no banco de dados.
**And**: Deve pausar o trigger no Quartz Scheduler.
**And**: O job não deve executar enquanto estiver pausado.
**When**: O job é retomado.
**Then**: Deve atualizar o status para ativo.
**And**: Deve retomar o trigger no Quartz Scheduler.
**And**: O job deve voltar a executar conforme agendamento.

### Cenário 5: Fluxo de atualização de configuração de scheduler
**Given**: Um scheduler configurado com jobs ativos.
**When**: A configuração do scheduler é atualizada.
**Then**: Deve atualizar a configuração no banco de dados.
**And**: Deve aplicar as mudanças no Quartz Scheduler.
**And**: Deve notificar jobs afetados pela mudança.
**And**: Deve registrar a operação de atualização.

### Cenário 6: Fluxo de cálculo de ocorrências com iCalendar
**Given**: Uma periodicidade configurada com regras iCalendar.
**When**: As ocorrências são calculadas para um intervalo de tempo.
**Then**: Deve usar o ICalendarSerializer corretamente.
**And**: Deve calcular todas as ocorrências no intervalo.
**And**: Deve respeitar regras complexas (RRULE, EXDATE, RDATE).
**And**: Deve considerar exclusões de recorrência.

### Cenário 7: Fluxo de tratamento de erro na execução de job
**Given**: Um job que pode falhar durante execução.
**When**: O job é executado e ocorre um erro.
**Then**: Deve capturar a exceção.
**And**: Deve registrar o erro no banco de dados.
**And**: Deve notificar o sistema de monitoramento.
**And**: Deve tentar reexecutar conforme política de retry configurada.

### Cenário 8: Fluxo de consulta de jobs com filtros
**Given**: Múltiplos jobs agendados com diferentes configurações.
**When**: É realizada uma consulta com filtros (status, periodicidade, intervalo de tempo).
**Then**: Deve retornar apenas os jobs que atendem aos filtros.
**And**: Deve incluir informações de agendamento e execução.
**And**: Deve permitir paginação dos resultados.

## Dependências
- JUnit 5
- AssertJ
- Spring Boot Test
- TestContainers para banco de dados real (PostgreSQL)
- Quartz Scheduler
- Bibliotecas lib-recur para cálculo de recorrência
- Dados de teste sem informação sensível

## Configuração de Teste
```java
@SpringBootTest
@Testcontainers
@ActiveProfiles("testcontainers")
class QuartzJobSchedulingIT {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    private QuartzJobService jobService;

    @Autowired
    private SchedulerConfigService schedulerConfigService;

    @Autowired
    private PeriodicidadeService periodicidadeService;

    @Autowired
    private ExclusaoRecorrenciaService exclusaoRecorrenciaService;
}
```

## Referências
- [CDU/Manter-Quartz: Manter Quartz](../../../../CDU/Manter-Quartz/README.md)
- ADR 010: Padrões de Nomenclatura
- ADR 012: Padrões de Teste Automatizado
- ADR 011: Exception Handling Patterns
- ADR 045: Usar iCalendar/iTIP para Agendamento
- ADR 050: Diretrizes Gerais de Padronização
- ADR 052: MADR e Linguagem Normativa

## Aderência a ADRs

Este caso de teste foi gerado como documento vivo de rastreabilidade entre teste, CDU, domínio e decisões arquiteturais do `ia-core`.

### Metadados de contexto

| Campo | Valor |
|-------|-------|
| Componente | `Quartz Job Scheduling - Integração` |
| Camada | Integração |
| Tipo de teste | Integração |
| Domínio | Agendamento e Execução de Jobs |
| CDU relacionada | [CDU/Manter-Quartz: Manter Quartz](../../../../CDU/Manter-Quartz/README.md) |
| Status da camada | Implementado |

### Matriz de conformidade

| ADR | Tema | Aplicabilidade | Critério de conformidade |
|-----|------|----------------|--------------------------|
| ADR-010 | Padrões de Nomenclatura | Obrigatório | Nomes de arquivos, classes, métodos e campos seguem ADR-010. |
| ADR-012 | Padrões de Teste Automatizado | Obrigatório | Cenário feliz, negativo, dependências, mocks e rastreabilidade documentados. |
| ADR-045 | Usar iCalendar/iTIP para Agendamento | Aplicável | Validar uso correto de iCalendar para recorrência. |
| ADR-050 | Diretrizes Gerais de Padronização | Obrigatório | Documento UTF-8, claro e alinhado à padronização do ia-core. |
| ADR-052 | MADR e Linguagem Normativa | Obrigatório | Critérios objetivos e termos normativos sem ambiguidade. |
| ADR-011 | Exception Handling | Aplicável | Validar exceções de domínio, validação, códigos de erro e mensagens i18n. |

### Critérios de aceitação obrigatórios

- [ ] O caso informa objetivo, componentes testados, tipo de teste, domínio e CDU relacionado.
- [ ] O fluxo cobre cenário feliz, entradas inválidas, exceções e dependências relevantes.
- [ ] Os nomes de classes, métodos, arquivos e mensagens seguem ADR-010.
- [ ] Os asserts são explícitos, legíveis e preferencialmente usam AssertJ/JUnit 5 conforme ADR-012.
- [ ] Teste usa TestContainers para banco de dados real conforme ADR-012.
- [ ] Teste valida interação entre múltiplos serviços.
- [ ] Teste valida uso de iCalendar para recorrência conforme ADR-045.
- [ ] O documento está em UTF-8 e usa linguagem clara e consistente com ADR-050/ADR-052.

### Evidências esperadas

- Cenário feliz documentado com Given/When/Then ou fluxo equivalente.
- Cenários negativos e exceções documentados quando aplicáveis.
- Dependências, mocks, stubs e pré-condições explicitados.
- Resultados esperados verificáveis por teste automatizado.
- Rastreabilidade com CDU, domínio, componentes e ADRs aplicáveis.
- Configuração de TestContainers documentada.
- Validação de iCalendar/lib-recur documentada.

### Referências ADR

- [ADR-010 - Padrões de Nomenclatura](../../../../ADR/010-nomenclature-standards.md)
- [ADR-011 - Exception Handling Patterns](../../../../ADR/011-exception-handling-patterns.md)
- [ADR-012 - Padrões de Teste Automatizado](../../../../ADR/012-testing-patterns.md)
- [ADR-045 - Usar iCalendar/iTIP para Agendamento](../../../../ADR/045-use-icalendar-itip-for-scheduling.md)
- [ADR-050 - Diretrizes Gerais de Padronização](../../../../ADR/050-standardization-guidelines.md)
- [ADR-052 - MADR e Linguagem Normativa](../../../../ADR/052-adopt-madr-and-rfc-normative-language-for-adrs.md)

### Referências do projeto

- CDU: [CDU/Manter-Quartz: Manter Quartz](../../../../CDU/Manter-Quartz/README.md)
- Classes: [`QuartzJobService`](../../../../ia-core-quartz-service/src/main/java/com/ia/core/quartz/service/job/QuartzJobService.java), [`SchedulerConfigService`](../../../../ia-core-quartz-service/src/main/java/com/ia/core/quartz/service/scheduler/SchedulerConfigService.java), [`PeriodicidadeService`](../../../../ia-core-quartz-service/src/main/java/com/ia/core/quartz/service/periodicidade/PeriodicidadeService.java)
