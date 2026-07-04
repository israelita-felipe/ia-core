# ADR-016: Melhores Práticas de ADRs e Manutenção

## Status

✅ Aceito

## Contexto

O projeto ia-core-apps utiliza ADRs (Architecture Decision Records) para documentar decisões arquiteturais. Este ADR estabelece diretrizes para criar, manter e evoluir ADRs, alinhando-se às melhores práticas da indústria.

## Decisão

Adotar as seguintes práticas para ADRs:

### 1. Estrutura de um ADR

Cada ADR deve conter:

```markdown
# ADR-NNN: Título Descritivo

## Status
✅ Aceito / 🔄 Proposto / ❌ Rejeitado / ⚠️ Deprecated

## Contexto
Descrição do problema ou situação que motivou a decisão.

## Decisão
Descrição clara da decisão tomada.

## Detalhes
- Justificativa técnica
- Alternativas consideradas
- Consequences (positivas e negativas)

## Implementação
- Status atual
- Links para código relevante
- Evidence de uso

## Referências
- Links para documentação externa
- Artigos técnicos
- Benchmarks

## Histórico
| Data | Alteração | Autor |
|------|-----------|-------|
| YYYY-MM-DD | Criação | Nome |

## Revisores
- Nome1
- Nome2
```

### 2. Categorias de ADRs

| Código | Categoria | Exemplos |
|--------|-----------|----------|
| 001-099 | Padrões de Desenvolvimento | MapStruct, Lombok, Java Version |
| 100-199 | Arquitetura de Dados | JPA, Hibernate, Flyway |
| 200-299 | Arquitetura de Aplicação | MVC, MVVM,microsservices |
| 300-399 | Segurança | Autenticação, Autorização, Criptografia |
| 400-499 | Infraestrutura | DevOps, CI/CD, Containers |
| 500-599 | Metodologia | Testes, Code Review, Documentação |

### 3. Ciclo de Vida de um ADR

```
🔄 Proposto → ✅ Aceito → 🔧 Implementado → ⚠️ Deprecated → 🗑️ Obsoleto
```

### 4. Critérios para um Bom ADR

1. **Título claro**: Deve descrever a decisão em poucas palavras
2. **Contexto completo**: Explicar o problema que motivou a decisão
3. **Decisão justificável**: A decisão deve ser fundamentada em evidências
4. **Consequências documentadas**: Incluir prós, contras e trade-offs
5. **Referências externas**: Links para fontes que validam a decisão
6. **Responsáveis e participantes**: Informar decisores, consultados e informados
7. **Confirmação de implementação**: Indicar como a decisão será validada no código, testes ou revisão

### 5. Linguagem Normativa dos ADRs

Os ADRs do `ia-core` devem usar as palavras-chave de RFC 2119 e RFC 8174 quando definirem obrigatoriedade, recomendação ou opcionalidade:

| Palavra-chave | Interpretação |
|---------------|---------------|
| `MUST`, `MUST NOT`, `REQUIRED`, `SHALL`, `SHALL NOT` | Requisito absoluto |
| `SHOULD`, `SHOULD NOT`, `RECOMMENDED`, `NOT RECOMMENDED` | Recomendação com possibilidade justificada de exceção |
| `MAY`, `OPTIONAL` | Comportamento opcional |

Quando aplicável, incluir a frase:

> As palavras-chave `MUST`, `MUST NOT`, `REQUIRED`, `SHALL`, `SHALL NOT`, `SHOULD`, `SHOULD NOT`, `RECOMMENDED`, `MAY` e `OPTIONAL` neste ADR são interpretadas conforme RFC 2119 e RFC 8174.

### 6. Formato MADR como Referência

O formato MADR — Markdown Architectural Decision Records — deve ser usado como referência para novos ADRs e revisões relevantes. Ele amplia o formato atual com campos como `decision-makers`, `consulted`, `informed`, `Decision Drivers`, `Considered Options`, `Decision Outcome` e `Confirmation`.

Para detalhes completos, ver [`052-adopt-madr-and-rfc-normative-language-for-adrs.md`](052-adopt-madr-and-rfc-normative-language-for-adrs.md) e [`referencias/adr-rfc-standards-ia-core.md`](referencias/adr-rfc-standards-ia-core.md).

### 7. Referências de Boas Práticas

#### Artigos e Documentação

1. **Michael Nygard - "Documenting Architecture Decisions"**
   - URL: https://cognitect.com/blog/2011/11/15/documenting-architecture-decisions
   - Referência original que popularizou o termo ADR

2. **MADR — Markdown Architectural Decision Records**
   - URL: https://adr.github.io/madr/
   - Template e regras práticas para ADRs em Markdown

3. **MADR Template**
   - URL: https://raw.githubusercontent.com/adr/madr/develop/template/adr-template.md
   - Template oficial usado como referência para o ADR-052

4. **ADR GitHub Template**
   - URL: https://github.com/joelparkerhenderson/architecture-decision-record
   - Coleção de templates e exemplos

5. **Thoughtworks Technology Radar**
   - URL: https://www.thoughtworks.com/radar
   - Referências de tecnologias e práticas

6. **Martin Fowler - ADRs**
   - URL: https://martinfowler.com/articles/agileArchitecture.html#DocumentingDecisions
   - Discussão sobre ADRs em contexto ágil

7. **Sustainable Architecture - ADRs**
   - URL: https://docs.aws.amazon.com/prescriptive-guidance/latest/sustainable-architecture-decision-records.pdf
   - AWS best practices

#### Livros

1. **"Building Evolutionary Architectures"** - Neal Ford, Rebecca Parsons, Patrick Kua
   - Chapter on ADRs and decision making

2. **"Fundamentals of Software Architecture"** - Mark Richards, Neal Ford
   - Section on architecture decision records

3. **"Software Architecture for Developers"** - Simon Brown
   - Guidance on technical leadership and ADRs

#### Artigos Acadêmicos Selecionados

1. **One Size Fits All? An Empirical Comparison of ADR Templates regarding Comprehension, Usability, and Ease of Adoption**
   - URL: http://arxiv.org/abs/2604.27333
   - Compara templates de ADR, incluindo Nygard e MADR

2. **Context Matters: Evaluating Context Strategies for Automated ADR Generation Using LLMs**
   - URL: http://arxiv.org/abs/2604.03826
   - Avalia estratégias de contexto para geração de ADRs com LLMs

3. **Evaluating Large Language Models for Detecting Architectural Decision Violations**
   - URL: http://arxiv.org/abs/2602.07609
   - Investiga detecção automatizada de violações de ADRs com LLMs

4. **AgenticAKM: Enroute to Agentic Architecture Knowledge Management**
   - URL: http://arxiv.org/abs/2602.04445
   - Propõe agentes para Architecture Knowledge Management

5. **Architecture Without Architects: How AI Coding Agents Shape Software Architecture**
   - URL: http://arxiv.org/abs/2604.04990
   - Discute decisões arquiteturais implícitas feitas por agentes de código

### 8. Ferramentas e Templates

| Ferramenta | Descrição | URL |
|------------|-----------|-----|
| adr-tools | Command line ADR management | https://github.com/npryce/adr-tools |
| ADR Log | Git-based ADR repository | https://github.com/joelparkerhenderson/adr-log |
| MADR | Template e práticas para ADRs em Markdown | https://adr.github.io/madr/ |
| markdownlint | Lint para Markdown usado pelo MADR | https://github.com/DavidAnson/markdownlint |
| Confluence ADR Template | Corporate template | Atlassian Marketplace |
| MkDocs ADR | Static site generator | https://github.com/mkdocs/mkdocs |

### 9. Padrões de Nomenclatura

- **Arquivo**: `NNN-title-lowercase-with-dashes.md`
- **ID**: `ADR-NNN`
- **Título**: PascalCase descritivo

## Consequências

### Positivas

- ✅ Decisões arquiteturais documentadas e rastreáveis
- ✅ Onboarding de novos membros facilitado
- ✅ Histórico de decisões disponível para auditoria
- ✅ Base para discussões técnicas estruturadas
- ✅ Alinhamento com práticas de mercado

### Negativas

- ❌ Overhead inicial para criar ADRs
- ❌ Risco de文档 burocrática se não for atualizado
- ❌ Requer disciplina da equipe para manter

## Status de Implementação

✅ IMPLEMENTADO

- 40 ADRs criados em 2026-06-11, excluindo `README.md`
- Estrutura padronizada
- Novo formato de referência baseado em MADR e RFC 2119/8174
- Referências externas consolidadas em [`referencias/adr-rfc-standards-ia-core.md`](referencias/adr-rfc-standards-ia-core.md)

## Referências

1. Nygard, M. (2011). Documenting Architecture Decisions. Cognitect Blog.
2. MADR. Markdown Architectural Decision Records. https://adr.github.io/madr/
3. MADR Template. https://raw.githubusercontent.com/adr/madr/develop/template/adr-template.md
4. Parker Henderson, J. Architecture Decision Record. GitHub Repository.
5. RFC 2119. Key words for use in RFCs to Indicate Requirement Levels. https://www.rfc-editor.org/rfc/rfc2119
6. RFC 8174. Ambiguity of Uppercase vs Lowercase in RFC 2119 Key Words. https://www.rfc-editor.org/rfc/rfc8174
7. Richards, M., Ford, N. (2020). Fundamentals of Software Architecture. O'Reilly.
8. AWS. (2024). Sustainable Architecture Decision Records. AWS Prescriptive Guidance.
9. Fowler, M. Agile Architecture. Martin Fowler's Bliki.
10. arXiv 2604.27333. One Size Fits All? An Empirical Comparison of ADR Templates. http://arxiv.org/abs/2604.27333

## Data

2026-03-26

## Revisores

- Architect
- Team Lead
- Senior Developers
