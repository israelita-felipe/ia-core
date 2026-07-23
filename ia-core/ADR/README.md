# Architectural Decision Records (ADR)

Este diretório contém os **Architectural Decision Records** do projeto, documentando as principais decisões de arquitetura tomadas durante o desenvolvimento.

**Importante**: Os ADRs documentam decisões aplicadas aos módulos do **ia-core**, que funcionam como bibliotecas/framework e não podem compor uma aplicação por si só. Esses módulos são base/framework para construção de outras aplicações, padronizando o desenvolvimento e abstraindo padrões de desenvolvimento. Uma aplicação real é composta pela combinação de múltiplos módulos ia-core mais código específico do domínio da aplicação.

## Índice de ADRs

### Padrões de Desenvolvimento (001-099)

| # | Título | Status | Descrição |
|---|--------|--------|-----------|
| ADR-001 | [Usar MapStruct para Mapeamento](001-use-mapstruct-for-mapping.md) | ✅ Aceito | Biblioteca de mapeamento DTO-Entidade |
| ADR-002 | [Usar Specification para Filtros](002-use-specification-for-filtering.md) | ✅ Aceito | Padrão para filtros dinâmicos |
| ADR-003 | [Usar Translator para i18n](003-use-translator-for-i18n.md) | ✅ Aceito | Classes para internacionalização |
| ADR-004 | [Usar ServiceConfig para DI](004-use-serviceconfig-for-di.md) | ✅ Aceito | Configuração de injeção de dependências |
| ADR-005 | [Usar Domain Events](005-use-domain-events.md) | ✅ Aceito | Comunicação desacoplada via eventos |
| ADR-010 | [Padrões de Nomenclatura](010-nomenclature-standards.md) | ✅ Aceito | Convenções de nomes |
| ADR-011 | [Exception Handling Patterns](011-exception-handling-patterns.md) | ✅ Aceito | Tratamento de exceções padronizado |
| ADR-012 | [Testing Patterns](012-testing-patterns.md) | ✅ Aceito | Estratégia de testes automatizados |
| ADR-013 | [Logging e Monitoring](013-logging-monitoring-patterns.md) | ✅ Aceito | Logging, correlation ID e monitoramento |
| ADR-014 | [Javadoc Standards](014-javadoc-standards.md) | ✅ Aceito | Documentação de código |
| ADR-015 | [Usar TSID para Identidade](015-use-tsid-for-entity-identity.md) | ✅ Aceito | IDs ordenáveis e distribuídos |
| ADR-016 | [Best Practices de ADRs](016-best-practices-adr.md) | ✅ Aceito | Manutenção de ADRs |
| ADR-017 | [Usar Flyway para Migrações](017-use-flyway-for-database-migrations.md) | ✅ Aceito | Versionamento de schema |
| ADR-018 | [Usar Business Rule Chain](018-use-business-rule-chain-pattern.md) | ✅ Aceito | Validações de negócio composáveis |
| ADR-019 | [Usar Service Validator](019-use-service-validator-pattern.md) | ✅ Aceito | Validações de serviço dinâmicas |
| ADR-020 | [Usar SuperBuilder para JPA](020-use-superbuilder-for-jpa-entities.md) | ✅ Aceito | Builder com herança para entidades |
| ADR-021 | [Usar Lombok para Boilerplate](021-use-lombok-for-boilerplate-reduction.md) | ✅ Aceito | Redução de código repetitivo |
| ADR-022 | [Usar Java 21 como Base](022-use-java-21-as-base-version.md) | ✅ Aceito | Versão LTS do Java |
| ADR-023 | [Usar Builder Pattern com Lombok](023-use-builder-pattern-with-lombok.md) | ✅ Aceito | Criação fluente de objetos |
| ADR-024 | [Versionamento de API via URL](024-use-api-versioning-via-url-path.md) | ✅ Aceito | Versionamento explícito |
| ADR-025 | [Usar Resilience4j](025-use-resilience4j-for-resilience-patterns.md) | ✅ Aceito | Circuit breaker, retry, rate limiter |
| ADR-026 | [Usar HasVersion para Versionamento](026-use-has-version-for-entity-versioning.md) | ✅ Aceito | Controle de versão de entidades |
| ADR-028 | [Usar JWT para Autenticação](028-use-jwt-for-stateless-authentication.md) | ✅ Aceito | Autenticação stateless |
| ADR-035 | [Code Review Guidelines](035-establish-code-review-guidelines.md) | ✅ Aceito | Processo de code review |
| ADR-036 | [Conventional Commits](036-use-conventional-commits.md) | ✅ Aceito | Padrão de mensagens de commit |
| ADR-037 | [Semantic Versioning](037-use-semantic-versioning.md) | ✅ Aceito | Controle de versão SemVer |
| ADR-039 | [Testes E2E Vaadin](039-testes-e2e-vaadin.md) | ✅ Aceito | Testes de interface gráfica |
| ADR-040 | [Usar Classe CAMPOS Aninhada para Constantes](040-use-nested-campos-class-for-dto-field-constants.md) | ✅ Aceito | Constantes de campos em DTOs |
| ADR-041 | [Implementar Análise de Pontos de Função](041-implement-function-point-analysis.md) | ✅ Proposto | Medição funcional objetiva |
| ADR-042 | [Usar OAuth 2.0 para Refresh Token Flow](042-use-oauth-2-0-for-refresh-token-flow.md) | ✅ Aceito | Fluxo de refresh token com OAuth 2.0 |
| ADR-043 | [Usar JWT Tokens para Autenticação](043-use-jwt-tokens-for-authentication.md) | ✅ Aceito | Autenticação com JWT tokens |
| ADR-044 | [Usar HTTP/1.1 para Comunicação REST](044-use-http-1-1-for-rest-communication.md) | ✅ Aceito | Comunicação HTTP/1.1 alinhada a RFC 9110-9113 |
| ADR-045 | [Usar iCalendar/iTIP para Agendamento](045-use-icalendar-itip-for-scheduling.md) | ✅ Aceito | Calendário e transporte independente |
| ADR-047 | [Usar UTF-8, Tags de Idioma e Web Linking para NLP](047-use-utf-8-language-tags-web-linking-for-nlp.md) | ✅ Aceito | UTF-8, BCP 47 e RFC 8288 |
| ADR-050 | [Diretrizes Gerais de Padronização do Projeto](050-standardization-guidelines.md) | ✅ Aceito | Consolidação de padrões e RFCs |
| ADR-051 | [Usar Semântica HTTP RFC 9110 para APIs REST](051-use-http-semantics-rfc7231.md) | ✅ Aceito | Semântica HTTP moderna |
| ADR-052 | [Adotar MADR e Linguagem Normativa RFC 2119/8174 nos ADRs](052-adopt-madr-and-rfc-normative-language-for-adrs.md) | ✅ Aceito | Formato MADR e RFCs de linguagem normativa |
| ADR-053 | [Usar CDU para Documentação de Casos de Uso](053-use-cdu-for-use-case-documentation.md) | ✅ Aceito | Documentação de casos de uso |
| ADR-054 | [Usar RN para Documentação de Regras de Negócio](054-use-rn-for-business-rules.md) | ✅ Aceito | Documentação de regras de negócio |
| ADR-055 | [Padrão de Help Online em Vaadin](055-use-help-content-pattern.md) | ✅ Aceito | Sistema de ajuda contextual em Vaadin |

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
| ADR-044 | [Usar HTTP/1.1 para Comunicação REST](044-use-http-1-1-for-rest-communication.md) | ✅ Aceito | Comunicação HTTP/1.1 alinhada a RFC 9110-9113 |
| ADR-045 | [Usar iCalendar/iTIP para Agendamento](045-use-icalendar-itip-for-scheduling.md) | ✅ Aceito | Calendário e transporte independente |
| ADR-047 | [Usar UTF-8, Tags de Idioma e Web Linking para NLP](047-use-utf-8-language-tags-web-linking-for-nlp.md) | ✅ Aceito | UTF-8, BCP 47 e RFC 8288 |
| ADR-060 | [Configuração Extensível Modular](060-modular-extensible-configuration.md) | ✅ Implementado | Configuração centralizada por plugin |
| ADR-061 | [Padronização de ConfigurationProvider / ConfigurationRegistry](061-configuration-provider-registry-pattern.md) | ✅ Implementado | Contratos para configuração modular |
| ADR-062 | [Padronização de MóduloProperties e MóduloPropertiesConstants](062-module-properties-pattern.md) | ✅ Implementado | Alinhamento a namespaces Spring nativos |

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
| ADR-041 | [Implementar Análise de Pontos de Função](041-implement-function-point-analysis.md) | ✅ Proposto | Medição funcional objetiva |
| ADR-050 | [Diretrizes Gerais de Padronização do Projeto](050-standardization-guidelines.md) | ✅ Aceito | Consolidação de padrões e RFCs |
| ADR-051 | [Usar Semântica HTTP RFC 9110 para APIs REST](051-use-http-semantics-rfc7231.md) | ✅ Aceito | Semântica HTTP moderna |
| ADR-052 | [Adotar MADR e Linguagem Normativa RFC 2119/8174 nos ADRs](052-adopt-madr-and-rfc-normative-language-for-adrs.md) | ✅ Aceito | Formato MADR e RFCs de linguagem normativa |

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

## Formato ADR Recomendado

O template acima continua válido. Para novos ADRs e revisões relevantes, usar também as práticas de MADR:

- front matter YAML opcional com `status`, `date`, `decision-makers`, `consulted` e `informed`;
- `Context and Problem Statement`;
- `Decision Drivers`;
- `Considered Options`;
- `Decision Outcome`;
- `Consequences`;
- `Confirmation`.

As palavras-chave `MUST`, `MUST NOT`, `REQUIRED`, `SHALL`, `SHALL NOT`, `SHOULD`, `SHOULD NOT`, `RECOMMENDED`, `MAY` e `OPTIONAL` devem ser interpretadas conforme RFC 2119 e RFC 8174 quando aparecerem em maiúsculas.

## Referências Externas de ADR/RFC

- [`referencias/adr-rfc-standards-ia-core.md`](referencias/adr-rfc-standards-ia-core.md) consolida fontes pesquisadas em fóruns, documentação, sites de padronização e arXiv.
- MADR: https://adr.github.io/madr/
- Nygard — Documenting Architecture Decisions: https://cognitect.com/blog/2011/11/15/documenting-architecture-decisions
- RFC 2119 — Key words for use in RFCs to Indicate Requirement Levels: https://www.rfc-editor.org/rfc/rfc2119
- RFC 8174 — Ambiguity of Uppercase vs Lowercase in RFC 2119 Key Words: https://www.rfc-editor.org/rfc/rfc8174

## Como Criar um Novo ADR

1. Copie este template para um novo arquivo
2. Preencha todas as seções
3. Use MADR quando a decisão exigir opções, drivers ou confirmação explícita
4. Execute o código para verificar conformidade
5. Commit com mensagem: `docs: Add ADR-XXX: Título`

## Revisões de ADRs

ADRs podem ser revisados se:
- Nova evidência surgir
- Requisitos mudarem
- Impactos negativos forem maiores que o esperado

Para revisar, crie um novo ADR referenciando o anterior.

---

**Última Atualização:** 2026-07-22
