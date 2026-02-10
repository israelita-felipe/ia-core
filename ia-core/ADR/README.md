# Architectural Decision Records (ADR)

Este diretório contém os **Architectural Decision Records** do projeto, documentando as principais decisões de arquitetura tomadas durante o desenvolvimento.

## Índice de ADRs

### ADRs do ia-core-apps (Framework)

| # | Título | Status | Descrição |
|---|--------|--------|-----------|
| ADR-001 | [Usar MapStruct para Mapeamento](001-use-mapstruct-for-mapping.md) | ✅ Aceito | Bibliotca de mapeamento DTO-Entidade |
| ADR-002 | [Usar Specification para Filtros](002-use-specification-for-filtering.md) | ✅ Aceito | Padrão para filtros dinâmicos |
| ADR-003 | [Usar Translator para i18n](003-use-translator-for-i18n.md) | ✅ Aceito | Classes para internacionalização |
| ADR-004 | [Usar ServiceConfig para DI](004-use-serviceconfig-for-di.md) | ✅ Aceito | Configuração de injeção de dependências |
| ADR-005 | [Usar Domain Events](005-use-domain-events.md) | ✅ Aceito | Comunicação desacoplada via eventos |

### ADRs do Biblia (Aplicação)

| # | Título | Status | Descrição |
|---|--------|--------|-----------|
| ADR-006 | [Usar EntityGraph para Performance](006-use-entitygraph-for-performance.md) | ✅ Aceito | Otimização N+1 queries |
| ADR-007 | [Usar BaseEntity para Padronização](007-use-baseentity-for-entity-standardization.md) | ✅ Aceito | Classe base para entidades |
| ADR-008 | [Arquitetura MVVM](008-mvvm-architecture-with-viewmodel.md) | ✅ Aceito | Padrão MVVM com ViewModel/Config |
| ADR-009 | [Paginação com ListBaseController](009-pagination-with-listbasecontroller.md) | ✅ Aceito | Paginação e filtros REST |
| ADR-010 | [Padrões de Nomenclatura](010-nomenclature-standards.md) | ✅ Aceito | Convenções de nomes |

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

**Última Atualização:** 2024-04-01
