# ADR-052: Adotar MADR e Linguagem Normativa RFC 2119/8174 nos ADRs

## Status

✅ Aceito

## Contexto

O projeto `ia-core` já possui um conjunto expressivo de ADRs no diretório [`ADR`](/), com decisões que cobrem padrões de desenvolvimento, arquitetura, segurança, qualidade, documentação e integração com IA. Entretanto, os ADRs atuais usam formatos variados: alguns seguem o template do [`README.md`](README.md), enquanto outros usam front matter YAML e seções mais enxutas, como [`051-use-http-semantics-rfc7231.md`](051-use-http-semantics-rfc7231.md).

Essa variação reduz a comparabilidade entre decisões, dificulta a revisão arquitetural e aumenta o risco de ADRs incompletos. Ao mesmo tempo, práticas modernas como MADR — Markdown Architectural Decision Records — e evidências recentes sobre geração e análise de ADRs com LLMs indicam que um formato mais explícito melhora rastreabilidade, adoção e automação.

## Decisão

Adotar, progressivamente, o formato MADR como referência para novos ADRs e revisões relevantes, mantendo compatibilidade com os ADRs existentes. A partir deste ADR:

1. Todo novo ADR deve conter metadados YAML opcionais com `status`, `date`, `decision-makers`, `consulted` e `informed`.
2. Todo novo ADR deve explicitar:
   - contexto e problema;
   - drivers da decisão;
   - opções consideradas;
   - resultado da decisão;
   - consequências positivas e negativas;
   - confirmação de implementação ou conformidade;
   - referências externas.
3. A linguagem normativa dos ADRs deve seguir RFC 2119 e RFC 8174:
   - `MUST`, `MUST NOT`, `REQUIRED`, `SHALL`, `SHALL NOT`: requisitos absolutos;
   - `SHOULD`, `SHOULD NOT`, `RECOMMENDED`, `NOT RECOMMENDED`: recomendações com possibilidade justificada de exceção;
   - `MAY`, `OPTIONAL`: comportamento opcional.
4. Quando um ADR referenciar RFCs técnicos, deve indicar se a RFC está vigente, obsoleta ou substituída por outra RFC.
5. ADRs existentes não precisam ser reescritos integralmente, mas devem ser enriquecidos quando houver atualização de padrões, RFCs obsoletos ou lacunas de rastreabilidade.

## Detalhes

### Template recomendado

Novos ADRs podem usar a estrutura abaixo, derivada de MADR e adaptada ao projeto `ia-core`:

```markdown
---
status: accepted
date: YYYY-MM-DD
decision-makers:
  - Nome
consulted:
  - Nome ou grupo
informed:
  - Nome ou grupo
---

# ADR-NNN: Título Descritivo

## Status

✅ Aceito

## Contexto e Problema

Descrição objetiva do problema, restrições e escopo.

## Drivers da Decisão

- Interoperabilidade
- Segurança
- Manutenibilidade
- Testabilidade
- Compatibilidade com Java/Spring/REST

## Opções Consideradas

- Opção 1
- Opção 2
- Opção 3

## Resultado da Decisão

Opção escolhida e justificativa.

## Consequências

### Positivas

- ...

### Negativas

- ...

## Confirmação de Implementação

Como a conformidade será validada: testes, ArchUnit, revisão de código, documentação, lint ou checklist.

## Referências

- Links para RFCs, MADR, artigos, ADRs relacionados e documentação oficial.
```

### Interpretação das palavras-chave

A adoção de RFC 2119 e RFC 8174 evita ambiguidade em decisões como:

- “Os arquivos de migração `MUST` ser codificados em UTF-8.”
- “Os ADRs `SHOULD` referenciar RFCs vigentes quando aplicável.”
- “Metadados YAML `MAY` ser usados para facilitar ferramentas de indexação.”

## Consequências

### Positivas

- Maior consistência entre ADRs novos e revisados.
- Melhor rastreabilidade entre decisão, responsáveis, consultados e impactos.
- Maior clareza sobre obrigatoriedade versus recomendação.
- Melhor preparo para automação com LLMs, lint e geração de índice.
- Redução do risco de documentação arquitetural incompleta.

### Negativas

- Pequeno aumento de overhead na criação de novos ADRs.
- Necessidade de revisar ADRs antigos quando forem usados como referência.
- Risco de burocratização se o template for aplicado sem critério.

## Confirmação de Implementação

- [`016-best-practices-adr.md`](016-best-practices-adr.md) foi enriquecido com MADR, RFC 2119, RFC 8174 e evidências de arXiv.
- [`050-standardization-guidelines.md`](050-standardization-guidelines.md) foi enriquecido com RFCs de linguagem normativa, HTTP, Web Linking e Problem Details.
- [`044-use-http-1-1-for-rest-communication.md`](044-use-http-1-1-for-rest-communication.md) foi atualizado de RFC 7230-7235 para RFC 9110-9113.
- [`047-use-utf-8-language-tags-web-linking-for-nlp.md`](047-use-utf-8-language-tags-web-linking-for-nlp.md) foi atualizado de RFC 5988 para RFC 8288.
- [`051-use-http-semantics-rfc7231.md`](051-use-http-semantics-rfc7231.md) foi reescrito para RFC 9110 e RFC 9112.
- O [`README.md`](README.md) foi atualizado com a nova referência e o índice de ADRs.

## Referências

1. MADR — Markdown Architectural Decision Records: https://adr.github.io/madr/
2. Michael Nygard — Documenting Architecture Decisions: https://cognitect.com/blog/2011/11/15/documenting-architecture-decisions
3. RFC 2119 — Key words for use in RFCs to Indicate Requirement Levels: https://www.rfc-editor.org/rfc/rfc2119
4. RFC 8174 — Ambiguity of Uppercase vs Lowercase in RFC 2119 Key Words: https://www.rfc-editor.org/rfc/rfc8174
5. arXiv 2604.27333 — One Size Fits All? An Empirical Comparison of ADR Templates regarding Comprehension, Usability, and Ease of Adoption: http://arxiv.org/abs/2604.27333
6. arXiv 2604.03826 — Context Matters: Evaluating Context Strategies for Automated ADR Generation Using LLMs: http://arxiv.org/abs/2604.03826
7. arXiv 2602.07609 — Evaluating Large Language Models for Detecting Architectural Decision Violations: http://arxiv.org/abs/2602.07609
8. arXiv 2602.04445 — AgenticAKM: Enroute to Agentic Architecture Knowledge Management: http://arxiv.org/abs/2602.04445
9. arXiv 2604.04990 — Architecture Without Architects: How AI Coding Agents Shape Software Architecture: http://arxiv.org/abs/2604.04990
10. arXiv 2604.20436 — Shift-Up: A Framework for Software Engineering Guardrails in AI-native Software Development: http://arxiv.org/abs/2604.20436

## Data

2026-06-11

## Revisores

- Architect
- Team Lead
- Documentation Specialist
