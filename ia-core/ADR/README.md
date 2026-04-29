# Architectural Decision Records (ADR)

Este diretório contém os **Architectural Decision Records** do projeto, documentando as principais decisões de arquitetura tomadas durante o desenvolvimento.

## Índice de ADRs

### Padrões de Desenvolvimento (001-099)

| # | Título | Status | Descrição |
|---|--------|--------|-----------|
| ADR-001 | [Usar MapStruct para Mapeamento](001-use-mapstruct-for-mapping.md) | ✅ Aceito | Biblioteca de mapeamento DTO-Entidade |
| ADR-002 | [Usar Specification para Filtros](002-use-specification-for-filtering.md) | ✅ Aceito | Padrão para filtros dinâmicos |
| ADR-003 | [Usar Translator para i18n](003-use-translator-for-i18n.md) | ✅ Aceito | Classes para internacionalização |
| ADR-004 | [Usar ServiceConfig para DI](004-use-serviceconfig-for-di.md) | ✅ Aceito | Configuração de injeção de dependências |
| ADR-005 | [Usar Domain Events](005-use-domain-events.md) | ✅ Aceito | Comunicação desacoplada via eventos |
| ADR-015 | [Usar TSID para Identidade](015-use-tsid-for-entity-identity.md) | ✅ Aceito | IDs ordenáveis e distribuídos |
| ADR-018 | [Usar Business Rule Chain](018-use-business-rule-chain-pattern.md) | ✅ Aceito | Validações de negócio composáveis |
| ADR-019 | [Usar Service Validator](019-use-service-validator-pattern.md) | ✅ Aceito | Validações de serviço dinâmicas |
| ADR-020 | [Usar SuperBuilder para JPA](020-use-superbuilder-for-jpa-entities.md) | ✅ Aceito | Builder com herança para entidades |
| ADR-021 | [Usar Lombok para Boilerplate](021-use-lombok-for-boilerplate-reduction.md) | ✅ Aceito | Redução de código repetitivo |
| ADR-022 | [Usar Java 21 como Base](022-use-java-21-as-base-version.md) | ✅ Aceito | Versão LTS do Java |
| ADR-023 | [Usar Builder Pattern com Lombok](023-use-builder-pattern-with-lombok.md) | ✅ Aceito | Criação fluente de objetos |
| ADR-026 | [Usar HasVersion para Versionamento](026-use-has-version-for-entity-versioning.md) | ✅ Aceito | Controle de versão de entidades |

### Arquitetura de Aplicação (100-299)

| # | Título | Status | Descrição |
|---|--------|--------|-----------|
| ADR-006 | [Usar EntityGraph para Performance](006-use-entitygraph-for-performance.md) | ✅ Aceito | Otimização N+1 queries |
| ADR-007 | [Usar BaseEntity para Padronização](007-use-baseentity-for-entity-standardization.md) | ✅ Aceito | Classe base para entidades |
| ADR-008 | [Arquitetura MVVM](008-mvvm-architecture-with-viewmodel.md) | ✅ Aceito | Padrão MVVM com ViewModel/Config |
| ADR-009 | [Paginação com ListBaseController](009-pagination-with-listbasecontroller.md) | ✅ Aceito | Paginação e filtros REST |
| ADR-017 | [Usar Flyway para Migrações](017-use-flyway-for-database-migrations.md) | ✅ Aceito | Versionamento de schema |
| ADR-024 | [Versionamento de API via URL](024-use-api-versioning-via-url-path.md) | ✅ Aceito | Versionamento explícito |
| ADR-025 | [Usar Resilience4j](025-use-resilience4j-for-resilience-patterns.md) | ✅ Aceito | Circuit breaker, retry, rate limiter |
| ADR-028 | [Usar JWT para Autenticação](028-use-jwt-for-stateless-authentication.md) | ✅ Aceito | Autenticação stateless |

### Qualidade e Metodologia (300-599)

| # | Título | Status | Descrição |
|---|--------|--------|-----------|
| ADR-010 | [Padrões de Nomenclatura](010-nomenclature-standards.md) | ✅ Aceito | Convenções de nomes |
| ADR-011 | [Exception Handling Patterns](011-exception-handling-patterns.md) | ✅ Aceito | Tratamento de exceções padronizado |
| ADR-012 | [Testing Patterns](012-testing-patterns.md) | ✅ Aceito | Estratégia de testes automatizados |
| ADR-013 | [Logging e Monitoring](013-logging-monitoring-patterns.md) | ✅ Aceito | Logging, correlation ID e monitoramento |
| ADR-014 | [Javadoc Standards](014-javadoc-standards.md) | ✅ Aceito | Documentação de código |
| ADR-016 | [Best Practices de ADRs](016-best-practices-adr.md) | ✅ Aceito | Manutenção de ADRs |
| ADR-035 | [Code Review Guidelines](035-establish-code-review-guidelines.md) | ✅ Aceito | Processo de code review |
| ADR-036 | [Conventional Commits](036-use-conventional-commits.md) | ✅ Aceito | Padrão de mensagens de commit |
| ADR-037 | [Semantic Versioning](037-use-semantic-versioning.md) | ✅ Aceito | Controle de versão SemVer |
| ADR-039 | [Testes E2E Vaadin](039-testes-e2e-vaadin.md) | ✅ Aceito | Testes de interface gráfica |

## Formato ADR

Cada ADR segue o formato:

```markdown
# ADR-XXX: Título da Decisão

## Status

✅ Aceito | ⏳ Pendente | ❌ Rejeitado

## Contexto

Descrição do problema ou situação que motivou a decisão.

## Decisão

A decisão tomada e sua justificativa.

## Detalhes

- Alternativas consideradas
- Critérios de decisão
- Implementação técnica

## Consequências

### Positivas
- ...

### Negativas
- ...

## Status de Implementação

✅ COMPLETO | 🔄 Em Andamento | ⏳ Pendente

## Data

YYYY-MM-DD

## Revisores

- Nome(s) do(s) revisor(es)

## Referências

1. Links e referências relacionadas.
```

## Como Criar um Novo ADR

1. Copie este template para um novo arquivo
2. Preencha todas as seções
3. Execute o código para verificar conformidade
4. Commit com mensagem: `docs: Add ADR-XXX: Título`

## Revisões de ADRs

ADRs podem ser revisados se:
- Nova evidência surgir
- Requisitos mudarem
- Impactos negativos forem maiores que o esperado

Para revisar, crie um novo ADR referenciando o anterior.

---

**Última Atualização:** 2026-04-28
