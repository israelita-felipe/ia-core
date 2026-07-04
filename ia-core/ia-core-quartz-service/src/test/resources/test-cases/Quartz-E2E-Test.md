# Caso de Teste: Quartz - End-to-End (E2E)

## Descrição
Caso de teste end-to-end para validar fluxos completos de agendamento e execução de jobs, simulando cenários reais de uso em produção com o Quartz Scheduler.

## Componentes Testados
- `com.ia.core.quartz.service.job.QuartzJobService`
- `com.ia.core.quartz.service.scheduler.SchedulerConfigService`
- `com.ia.core.quartz.service.periodicidade.PeriodicidadeService`
- `com.ia.core.quartz.service.recorrencia.ExclusaoRecorrenciaService`
- `com.ia.core.quartz.service.model.job.dto.QuartzJobInstanceDTO`
- `com.ia.core.quartz.service.model.scheduler.dto.SchedulerConfigDTO`
- `com.ia.core.quartz.service.model.periodicidade.dto.PeriodicidadeDTO`
- `com.ia.core.quartz.service.model.recorrencia.dto.ExclusaoRecorrenciaDTO`

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
Documentar e validar fluxos end-to-end de agendamento e execução de jobs, testando cenários completos que simulam uso real em produção, incluindo criação, execução, pausa, retomada, exclusão e monitoramento de jobs.

## Fluxo do Teste
1. Dado o contexto de agendamento completo no domínio `Manter Quartz`.
2. Quando fluxos end-to-end são executados simulando cenários reais.
3. Então o comportamento deve validar a interação correta entre todos os componentes da stack.
4. E deve manter rastreabilidade completa com [CDU/Manter-Quartz: Manter Quartz](../../../../CDU/Manter-Quartz/README.md).
5. E deve garantir consistência entre banco de dados e Quartz Scheduler.

## Cenários

### Cenário 1: Fluxo completo de ciclo de vida de job
**Given**: Um scheduler configurado e pronto.
**When**: Um job é criado, agendado, executado, pausado, retomado e excluído.
**Then**: Deve criar o job no banco de dados e no Quartz Scheduler.
**And**: Deve executar o job conforme agendamento.
**And**: Deve pausar e retomar o job corretamente.
**And**: Deve excluir o job do banco de dados e do Quartz Scheduler.
**And**: Deve manter consistência entre banco e scheduler durante todo o ciclo.

### Cenário 2: Fluxo completo de job com periodicidade complexa
**Given**: Um job com periodicidade complexa (regras iCalendar, exclusões, múltiplas ocorrências).
**When**: O job é agendado e executado ao longo do tempo.
**Then**: Deve calcular corretamente todas as ocorrências usando lib-recur.
**And**: Deve respeitar regras RRULE, EXDATE e RDATE.
**And**: Deve pular datas excluídas.
**And**: Deve registrar cada execução no banco de dados.
**And**: Deve calcular a próxima execução corretamente após cada execução.

### Cenário 3: Fluxo completo de gerenciamento de exclusões de recorrência
**Given**: Um job agendado com periodicidade recorrente.
**When**: Exclusões de recorrência são adicionadas, removidas e modificadas.
**Then**: Deve registrar cada exclusão no banco de dados.
**And**: Deve recalcular o agendamento após cada mudança.
**And**: Deve ajustar os triggers no Quartz Scheduler.
**And**: O job deve executar apenas nas datas não excluídas.

### Cenário 4: Fluxo completo de tratamento de falhas e retry
**Given**: Um job que pode falhar durante execução.
**When**: O job é executado e falha.
**Then**: Deve capturar a exceção e registrar o erro.
**And**: Deve tentar reexecutar conforme política de retry configurada.
**And**: Deve notificar o sistema de monitoramento.
**And**: Após máximas tentativas, deve marcar o job como falhado.
**And**: Deve permitir diagnóstico através de logs e histórico de execuções.

### Cenário 5: Fluxo completo de monitoramento e relatórios
**Given**: Múltiplos jobs agendados com diferentes configurações e status.
**When**: É solicitado um relatório de monitoramento com filtros e agregações.
**Then**: Deve retornar estatísticas de execução (sucesso, falha, pendente).
**And**: Deve permitir filtragem por período, status, tipo de job.
**And**: Deve calcular métricas (tempo médio de execução, taxa de sucesso).
**And**: Deve identificar jobs com problemas recorrentes.
**And**: Deve permitir exportação do relatório.

### Cenário 6: Fluxo completo de atualização dinâmica de configuração
**Given**: Um scheduler com múltiplos jobs ativos.
**When**: A configuração do scheduler é atualizada (pool size, timeout, políticas).
**Then**: Deve aplicar as mudanças sem interromper jobs em execução.
**And**: Deve notificar jobs afetados pela mudança.
**And**: Deve validar a nova configuração antes de aplicar.
**And**: Deve registrar a operação de atualização.
**And**: Jobs novos devem usar a nova configuração.

### Cenário 7: Fluxo completo de concorrência e paralelismo
**Given**: Múltiplos jobs configurados para executar simultaneamente.
**When**: O scheduler executa múltiplos jobs em paralelo.
**Then**: Deve respeitar os limites de pool de threads configurados.
**And**: Deve gerenciar corretamente recursos compartilhados.
**And**: Deve evitar deadlocks e race conditions.
**And**: Deve registrar corretamente cada execução mesmo em paralelo.
**And**: Deve garantir consistência no banco de dados.

### Cenário 8: Fluxo completo de migração e backup
**Given**: Um scheduler com configuração e jobs existentes.
**When**: É realizada uma migração ou backup do scheduler.
**Then**: Deve exportar toda a configuração e estado dos jobs.
**And**: Deve incluir periodicidade, exclusões e histórico de execuções.
**And**: Deve permitir importação em outro ambiente.
**And**: Deve validar a integridade dos dados exportados.
**And**: Deve restaurar o estado exato após importação.

## Dependências
- JUnit 5
- AssertJ
- Spring Boot Test
- TestContainers para banco de dados real (PostgreSQL)
- Quartz Scheduler
- Bibliotecas lib-recur para cálculo de recorrência
- Dados de teste sem informação sensível
- Ambiente de teste configurado para simular produção

## Configuração de Teste
```java
@SpringBootTest
@Testcontainers
@ActiveProfiles("testcontainers")
class QuartzE2ETest {

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

    @Autowired
    private Scheduler quartzScheduler;
}
```

## Estratégia de Execução
- Testes E2E devem ser executados manualmente ou em pipeline de CI/CD
- Devem usar banco de dados real via TestContainers
- Devem limpar o banco de dados e Quartz Scheduler antes de cada teste
- Devem validar estado final do banco de dados e do Quartz Scheduler
- Devem verificar consistência entre banco e scheduler
- Devem simular passagem de tempo para testes de periodicidade

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
| Componente | `Quartz - End-to-End` |
| Camada | E2E |
| Tipo de teste | End-to-End |
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
- [ ] O fluxo cobre cenários completos simulando uso real em produção.
- [ ] Os nomes de classes, métodos, arquivos e mensagens seguem ADR-010.
- [ ] Os asserts são explícitos, legíveis e preferencialmente usam AssertJ/JUnit 5 conforme ADR-012.
- [ ] Teste usa TestContainers para banco de dados real conforme ADR-012.
- [ ] Teste valida estado final do banco de dados e do Quartz Scheduler.
- [ ] Teste verifica consistência entre banco e scheduler.
- [ ] Teste valida uso de iCalendar/lib-recur conforme ADR-045.
- [ ] O documento está em UTF-8 e usa linguagem clara e consistente com ADR-050/ADR-052.

### Evidências esperadas

- Cenários completos documentados com Given/When/Then.
- Cenários negativos e exceções documentados quando aplicáveis.
- Dependências e pré-condições explicitados.
- Resultados esperados verificáveis por teste automatizado.
- Rastreabilidade com CDU, domínio, componentes e ADRs aplicáveis.
- Configuração de TestContainers documentada.
- Estratégia de execução definida.
- Validação de consistência entre banco e scheduler documentada.

### Referências ADR

- [ADR-010 - Padrões de Nomenclatura](../../../../ADR/010-nomenclature-standards.md)
- [ADR-011 - Exception Handling Patterns](../../../../ADR/011-exception-handling-patterns.md)
- [ADR-012 - Padrões de Teste Automatizado](../../../../ADR/012-testing-patterns.md)
- [ADR-045 - Usar iCalendar/iTIP para Agendamento](../../../../ADR/045-use-icalendar-itip-for-scheduling.md)
- [ADR-050 - Diretrizes Gerais de Padronização](../../../../ADR/050-standardization-guidelines.md)
- [ADR-052 - MADR e Linguagem Normativa](../../../../ADR/052-adopt-madr-and-rfc-normative-language-for-adrs.md)

### Referências do projeto

- CDU: [CDU/Manter-Quartz: Manter Quartz](../../../../CDU/Manter-Quartz/README.md)
- Classes: [`QuartzJobService`](../../../../ia-core-quartz-service/src/main/java/com/ia/core/quartz/service/job/QuartzJobService.java), [`SchedulerConfigService`](../../../../ia-core-quartz-service/src/main/java/com/ia/core/quartz/service/scheduler/SchedulerConfigService.java), [`PeriodicidadeService`](../../../../ia-core-quartz-service/src/main/java/com/ia/core/quartz/service/periodicidade/PeriodicidadeService.java), [`ExclusaoRecorrenciaService`](../../../../ia-core-quartz-service/src/main/java/com/ia/core/quartz/service/recorrencia/ExclusaoRecorrenciaService.java)
