# Referências ADR/RFC para o Projeto ia-core

Data da pesquisa: 2026-06-11

## Resumo Executivo

Esta referência consolida fontes externas pesquisadas para padronizar e enriquecer os ADRs do projeto `ia-core`, considerando os ADRs existentes em [`ADR`](/home/israel/git/ia-core-apps/ia-core/ADR). A principal conclusão é: **não há uma RFC da IETF que padronize o formato ADR**. ADR é uma prática de documentação arquitetural baseada em convenções comunitárias, especialmente Nygard, MADR e templates open source. As RFCs aplicáveis aos ADRs do `ia-core` servem para:

1. padronizar a linguagem normativa dos ADRs — RFC 2119 e RFC 8174;
2. corrigir referências técnicas obsoletas — por exemplo, RFC 7231 substituída por RFC 9110;
3. padronizar protocolos citados pelos ADRs — HTTP, JSON, OAuth/JWT, Web Linking, UTF-8, linguagem e calendário;
4. melhorar rastreabilidade e automação com templates MADR e evidências recentes de pesquisa acadêmica.

## Fontes Primárias de ADR

| Fonte | Tipo | Aplicação ao ia-core | URL |
|-------|------|----------------------|-----|
| Michael Nygard — Documenting Architecture Decisions | Artigo fundador | Origem histórica do termo ADR; justifica registrar contexto, decisão e consequências. | https://cognitect.com/blog/2011/11/15/documenting-architecture-decisions |
| MADR — Markdown Architectural Decision Records | Padrão comunitário | Template prático com status, contexto, drivers, opções, decisão, consequências e confirmação. | https://adr.github.io/madr/ |
| MADR Template | Template | Base para o novo formato recomendado em [`052-adopt-madr-and-rfc-normative-language-for-adrs.md`](../052-adopt-madr-and-rfc-normative-language-for-adrs.md). | https://raw.githubusercontent.com/adr/madr/develop/template/adr-template.md |
| Architecture Decision Record — Joel Parker Henderson | Repositório de templates | Referência complementar para formatos e convenções de diretório. | https://github.com/joelparkerhenderson/architecture-decision-record |
| arc42 template | Template de documentação de arquitetura | Útil para ADRs que impactam visão macro de arquitetura. | https://arc42.org/overview |
| C4 model | Modelo de visualização arquitetural | Complementa ADRs com diagramas de contexto, container, componente e código. | https://c4model.com/ |
| ISO/IEC/IEEE 42010 | Padrão de descrição de arquitetura | Referência para stakeholders, viewpoints e preocupações arquiteturais. | https://iso-architecture.org/ieee-1471/ |

## Fontes Acadêmicas arXiv Selecionadas

| Paper | Categoria | Aporte para ia-core | URL |
|-------|-----------|---------------------|-----|
| One Size Fits All? An Empirical Comparison of ADR Templates regarding Comprehension, Usability, and Ease of Adoption | cs.SE | Compara templates Tyree/Akerman, Nygard, arc42, Y-statements e MADR; útil para justificar a adoção de MADR. | http://arxiv.org/abs/2604.27333 |
| Context Matters: Evaluating Context Strategies for Automated ADR Generation Using LLMs | cs.SE | Mostra que estratégias de contexto histórico melhoram geração de ADRs com LLMs; útil para automação futura. | http://arxiv.org/abs/2604.03826 |
| Evaluating Large Language Models for Detecting Architectural Decision Violations | cs.SE, cs.AI | Evidência de que LLMs podem auxiliar na detecção de violações de ADRs; útil para guardrails. | http://arxiv.org/abs/2602.07609 |
| AgenticAKM: Enroute to Agentic Architecture Knowledge Management | cs.SE | Propõe agentes especializados para recuperar, organizar e documentar conhecimento arquitetural. | http://arxiv.org/abs/2602.04445 |
| Architecture Without Architects: How AI Coding Agents Shape Software Architecture | cs.SE, cs.AI | Mostra que agentes de código tomam decisões arquiteturais implícitas; reforça a necessidade de ADRs para IA-native development. | http://arxiv.org/abs/2604.04990 |
| Shift-Up: A Framework for Software Engineering Guardrails in AI-native Software Development | cs.SE, cs.AI | Propõe ADRs como guardrails estruturais para desenvolvimento com GenAI. | http://arxiv.org/abs/2604.20436 |

## RFCs de Linguagem Normativa para ADRs

Estas RFCs não padronizam ADR como formato, mas padronizam o significado de palavras como `MUST`, `SHOULD` e `MAY`, que devem ser usadas nos ADRs do `ia-core` para evitar ambiguidade.

| RFC | Título | Aplicação |
|-----|--------|-----------|
| RFC 2119 | Key words for use in RFCs to Indicate Requirement Levels | Define `MUST`, `SHOULD`, `MAY`, `REQUIRED`, `RECOMMENDED` e `OPTIONAL`. |
| RFC 8174 | Ambiguity of Uppercase vs Lowercase in RFC 2119 Key Words | Reforça que palavras-chave só têm significado normativo quando escritas em maiúsculas. |

Recomendação: todos os ADRs devem incluir a frase “As palavras-chave `MUST`, `MUST NOT`, `REQUIRED`, `SHALL`, `SHALL NOT`, `SHOULD`, `SHOULD NOT`, `RECOMMENDED`, `MAY` e `OPTIONAL` são interpretadas conforme RFC 2119 e RFC 8174.” quando houver requisitos normativos.

## RFCs Técnicos Relevantes aos ADRs Atuais

| Tema | RFC vigente | RFC obsoleta ou relacionada | ADRs impactados | Recomendação |
|------|-------------|-----------------------------|-----------------|--------------|
| HTTP semantics | RFC 9110 | RFC 7231 | [`044-use-http-1-1-for-rest-communication.md`](../044-use-http-1-1-for-rest-communication.md), [`051-use-http-semantics-rfc7231.md`](../051-use-http-semantics-rfc7231.md) | Atualizar referências de RFC 7231 para RFC 9110. |
| HTTP/1.1 message syntax and routing | RFC 9112 | RFC 7230 | [`044-use-http-1-1-for-rest-communication.md`](../044-use-http-1-1-for-rest-communication.md) | Atualizar RFC 7230 para RFC 9112. |
| HTTP caching | RFC 9111 | RFC 7234 | [`044-use-http-1-1-for-rest-communication.md`](../044-use-http-1-1-for-rest-communication.md) | Atualizar RFC 7234 para RFC 9111. |
| HTTP conditional requests | RFC 9110 | RFC 7232 | [`044-use-http-1-1-for-rest-communication.md`](../044-use-http-1-1-for-rest-communication.md) | Atualizar RFC 7232 para RFC 9110. |
| HTTP range requests | RFC 9110 | RFC 7233 | [`044-use-http-1-1-for-rest-communication.md`](../044-use-http-1-1-for-rest-communication.md) | Atualizar RFC 7233 para RFC 9110. |
| HTTP authentication | RFC 9110 | RFC 7235 | [`044-use-http-1-1-for-rest-communication.md`](../044-use-http-1-1-for-rest-communication.md), [`043-use-jwt-tokens-for-authentication.md`](../043-use-jwt-tokens-for-authentication.md) | Atualizar RFC 7235 para RFC 9110 e manter RFC 6750/9068 para bearer tokens. |
| HTTP/2 | RFC 9113 | RFC 7540 | [`044-use-http-1-1-for-rest-communication.md`](../044-use-http-1-1-for-rest-communication.md) | Manter HTTP/1.1 como decisão, mas documentar compatibilidade com HTTP/2 via RFC 9113 quando aplicável. |
| Web Linking | RFC 8288 | RFC 5988 | [`047-use-utf-8-language-tags-web-linking-for-nlp.md`](../047-use-utf-8-language-tags-web-linking-for-nlp.md), [`050-standardization-guidelines.md`](../050-standardization-guidelines.md) | Atualizar RFC 5988 para RFC 8288. |
| UTF-8 | RFC 3629 | RFC 2279 | [`003-use-translator-for-i18n.md`](../003-use-translator-for-i18n.md), [`010-nomenclature-standards.md`](../010-nomenclature-standards.md), [`017-use-flyway-for-database-migrations.md`](../017-use-flyway-for-database-migrations.md), [`047-use-utf-8-language-tags-web-linking-for-nlp.md`](../047-use-utf-8-language-tags-web-linking-for-nlp.md), [`050-standardization-guidelines.md`](../050-standardization-guidelines.md) | Manter RFC 3629 como referência vigente. |
| Language Tags | RFC 5646 | RFC 4646, RFC 4647 | [`003-use-translator-for-i18n.md`](../003-use-translator-for-i18n.md), [`047-use-utf-8-language-tags-web-linking-for-nlp.md`](../047-use-utf-8-language-tags-web-linking-for-nlp.md), [`050-standardization-guidelines.md`](../050-standardization-guidelines.md) | Manter RFC 5646; RFC 4646/4647 apenas como legado. |
| Problem Details for HTTP APIs | RFC 9457 | RFC 7807 | [`011-exception-handling-patterns.md`](../011-exception-handling-patterns.md) | Preferir RFC 9457 em novos desenvolvimentos; tratar RFC 7807 como legado. |
| JSON | RFC 8259 | RFC 7159 | DTOs e APIs REST | Preferir RFC 8259 como referência vigente para JSON. |
| JSON Patch | RFC 6902 | — | APIs de atualização parcial | Usar quando houver PATCH estruturado. |
| JSON Merge Patch | RFC 7396 | — | APIs de atualização parcial simples | Usar quando apropriado; documentar limitações. |
| OAuth 2.0 | RFC 6749 | RFC 6749 errata | [`042-use-oauth-2-0-for-refresh-token-flow.md`](../042-use-oauth-2-0-for-refresh-token-flow.md) | Manter RFC 6749 e complementar com RFC 8414, RFC 8705 e RFC 9700 quando houver descoberta, mTLS ou pushed authorization requests. |
| OAuth 2.0 Bearer Token Usage | RFC 6750 | — | [`042-use-oauth-2-0-for-refresh-token-flow.md`](../042-use-oauth-2-0-for-refresh-token-flow.md), [`043-use-jwt-tokens-for-authentication.md`](../043-use-jwt-tokens-for-authentication.md) | Manter RFC 6750 para bearer tokens. |
| JWT | RFC 7519 | — | [`028-use-jwt-for-stateless-authentication.md`](../028-use-jwt-for-stateless-authentication.md), [`043-use-jwt-tokens-for-authentication.md`](../043-use-jwt-tokens-for-authentication.md) | Manter RFC 7519. |
| JWT Profile for OAuth 2.0 Access Tokens | RFC 9068 | — | APIs que emitem access tokens JWT | Preferir RFC 9068 quando o access token for JWT. |
| iCalendar | RFC 5545 | RFC 2445 | [`045-use-icalendar-itip-for-scheduling.md`](../045-use-icalendar-itip-for-scheduling.md) | Manter RFC 5545. |
| iTIP | RFC 5546 | RFC 2446 | [`045-use-icalendar-itip-for-scheduling.md`](../045-use-icalendar-itip-for-scheduling.md) | Manter RFC 5546. |

## Recomendações de Aplicação ao ia-core

1. **Adotar MADR como template de referência** para novos ADRs e revisões relevantes, sem reescrever automaticamente todos os ADRs antigos.
2. **Usar RFC 2119/8174 para linguagem normativa** em decisões que definam obrigatoriedade.
3. **Atualizar referências HTTP obsoletas**:
   - RFC 7230 → RFC 9112;
   - RFC 7231 → RFC 9110;
   - RFC 7232 → RFC 9110;
   - RFC 7233 → RFC 9110;
   - RFC 7234 → RFC 9111;
   - RFC 7235 → RFC 9110;
   - RFC 5988 → RFC 8288;
   - RFC 7807 → RFC 9457.
4. **Manter RFCs vigentes já usadas**:
   - RFC 3629 para UTF-8;
   - RFC 5646 para tags de idioma;
   - RFC 5545/RFC 5546 para iCalendar/iTIP;
   - RFC 7519 para JWT;
   - RFC 6749/RFC 6750 para OAuth 2.0 e bearer tokens.
5. **Criar um processo de revisão de ADRs** com checklist:
   - status claro;
   - decisão objetiva;
   - alternativas consideradas;
   - consequências;
   - responsáveis/consultados/informados;
   - referências externas válidas;
   - RFCs vigentes ou obsoletas identificadas;
   - evidência de implementação.
6. **Avaliar automação futura com LLMs** para:
   - gerar rascunhos de ADRs a partir de issues/PRs;
   - detectar violações de ADRs em código;
   - sugerir referências RFC/MADR;
   - manter índice e links entre ADRs.

## ADRs Locais Considerados

Inventário resumido executado em 2026-06-11:

- 40 arquivos ADR `.md` no diretório `ADR`, excluindo `README.md`.
- ADRs com referências RFC existentes: [`003-use-translator-for-i18n.md`](../003-use-translator-for-i18n.md), [`010-nomenclature-standards.md`](../010-nomenclature-standards.md), [`011-exception-handling-patterns.md`](../011-exception-handling-patterns.md), [`017-use-flyway-for-database-migrations.md`](../017-use-flyway-for-database-migrations.md), [`028-use-jwt-for-stateless-authentication.md`](../028-use-jwt-for-stateless-authentication.md), [`042-use-oauth-2-0-for-refresh-token-flow.md`](../042-use-oauth-2-0-for-refresh-token-flow.md), [`043-use-jwt-tokens-for-authentication.md`](../043-use-jwt-tokens-for-authentication.md), [`044-use-http-1-1-for-rest-communication.md`](../044-use-http-1-1-for-rest-communication.md), [`045-use-icalendar-itip-for-scheduling.md`](../045-use-icalendar-itip-for-scheduling.md), [`047-use-utf-8-language-tags-web-linking-for-nlp.md`](../047-use-utf-8-language-tags-web-linking-for-nlp.md), [`050-standardization-guidelines.md`](../050-standardization-guidelines.md), [`051-use-http-semantics-rfc7231.md`](../051-use-http-semantics-rfc7231.md).
- ADRs de padronização e documentação mais diretamente impactados: [`016-best-practices-adr.md`](../016-best-practices-adr.md), [`050-standardization-guidelines.md`](../050-standardization-guidelines.md), [`051-use-http-semantics-rfc7231.md`](../051-use-http-semantics-rfc7231.md), [`052-adopt-madr-and-rfc-normative-language-for-adrs.md`](../052-adopt-madr-and-rfc-normative-language-for-adrs.md).

## Observação sobre “RFC que padronizam ADR”

A busca não identificou uma RFC da IETF que padronize ADR como formato documental. ADR é uma prática de arquitetura de software, não um protocolo da Internet. Portanto, a padronização recomendada para `ia-core` é híbrida:

- **MADR/Nygard** para formato de ADR;
- **RFC 2119/RFC 8174** para linguagem normativa;
- **RFCs técnicas específicas** para domínios citados pelos ADRs, como HTTP, JSON, OAuth, JWT, UTF-8, linguagem, calendário e Web Linking.
