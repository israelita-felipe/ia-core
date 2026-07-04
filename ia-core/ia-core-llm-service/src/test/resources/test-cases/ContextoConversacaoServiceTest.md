# Caso de Teste: ContextoConversacaoServiceTest

## Descrição
Testa o serviço ContextoConversacaoService responsável por gerenciar ontologias de contexto de conversação, incluindo criação, atualização, e recuperação de contextos.

## Classe Testada
`com.ia.core.llm.service.agente.ContextoConversacaoService`

## Fluxo do Teste
1. Dado um ContextoConversacaoService configurado com mocks de dependências
2. Quando métodos de gerenciamento de contexto são chamados
3. Então deve processar as requisições corretamente
4. E deve interagir adequadamente com repository e outros serviços

## Cenários
- createContextOntology com dados válidos
- getContextOntology com sessionId existente
- getContextOntology com sessionId inexistente
- updateContextOntology com axioma válido
- updateContextOntology com sessionId inexistente
- deleteContextOntology com sessionId existente
- existsContextOntology com sessionId existente
- existsContextOntology com sessionId inexistente
- Verificação de persistência de contextos
- Verificação de atualização de ontologia durante conversação

## Tipo de Teste
Unitário

## Desafios Específicos
- Mocking de ContextoConversacaoRepository e dependências complexas
- Verificação de integração com serviços OWL
- Teste de fluxo de atualização dinâmica de ontologia

## Estratégia de Mocking
- Usar @Mock para repository e dependências
- Verificar interações com verify()
- Testar diferentes variações de operações CRUD

## Aderência a ADRs

Este caso de teste foi atualizado para servir como documento vivo de rastreabilidade entre teste, CDU, domínio e decisões arquiteturais do `ia-core`.

### Metadados de contexto

| Campo | Valor |
|-------|-------|
| Componente | `ContextoConversacaoServiceTest` |
| Camada | Service |
| Tipo de teste | Unitário de Serviço |
| Domínio | Agente / Conversação |
| CDU relacionada | CDU-LLM-002: Conversação com Chat LLM |

### Matriz de conformidade

| ADR | Tema | Aplicabilidade | Critério de conformidade |
|-----|------|----------------|--------------------------|
| ADR-010 | Padrões de Nomenclatura | Obrigatório | Verificar nomes de classes, DTOs, pacotes, métodos, variáveis e arquivos em PascalCase/camelCase conforme o papel do componente. |
| ADR-012 | Padrões de Teste Automatizado | Obrigatório | Cobrir cenário feliz, entradas inválidas, exceções, mocks/stubs, asserts claros e rastreabilidade com CDU/ADR; para componentes LLM, evitar chamadas externas não controladas. |
| ADR-050 | Diretrizes Gerais de Padronização | Obrigatório | Manter UTF-8, linguagem normativa clara, padrões de integração AI e referências técnicas vigentes. |
| ADR-052 | MADR e Linguagem Normativa | Obrigatório | Usar decisão explícita, contexto, critérios de aceitação e termos normativos sem ambiguidade. |
| ADR-011 | Exception Handling | Aplicável | Validar exceções de domínio, validação, códigos de erro e mensagens i18n quando houver falha. |
| ADR-018 | Business Rule Chain | Quando houver regras | Cobrir ordem de regras, curto-circuito e acumulação de erros. |
| ADR-019 | Service Validator | Quando houver validação | Cobrir validadores registrados, integração com Jakarta Validation e falha controlada. |

### Critérios de aceitação obrigatórios

- [ ] O caso informa objetivo, classe/componente testado, tipo de teste, domínio e CDU relacionado.
- [ ] O fluxo cobre cenário feliz, entradas inválidas, exceções e dependências relevantes.
- [ ] Os nomes de classes, métodos, arquivos e mensagens seguem ADR-010.
- [ ] Os asserts são explícitos, legíveis e preferencialmente usam AssertJ/JUnit 5 conforme ADR-012.
- [ ] Mocks, stubs e verificações de interação são documentados sem expor dados sensíveis.
- [ ] O documento está em UTF-8 e usa linguagem clara e consistente com ADR-050/ADR-052.
- [ ] Validar exceções de domínio, validação e tratamento centralizado quando aplicável.
- [ ] Quando houver regras de negócio, validar ordem da cadeia e acumulação de erros.
- [ ] Quando houver ServiceValidator, validar reuso, registro temporário e falha controlada.

### Evidências esperadas

- Cenário feliz documentado com Given/When/Then ou fluxo equivalente.
- Cenários negativos e exceções documentados quando aplicáveis.
- Dependências, mocks, stubs e pré-condições explicitados.
- Resultados esperados verificáveis por teste automatizado.
- Rastreabilidade com CDU, domínio, classe/componente e ADRs aplicáveis.

### Referências ADR

- [ADR-010 - Padrões de Nomenclatura](../../../../ADR/010-nomenclature-standards.md).
- [ADR-012 - Padrões de Teste Automatizado](../../../../ADR/012-testing-patterns.md).
- [ADR-050 - Diretrizes Gerais de Padronização](../../../../ADR/050-standardization-guidelines.md).
- [ADR-052 - MADR e Linguagem Normativa](../../../../ADR/052-adopt-madr-and-rfc-normative-language-for-adrs.md).
- [ADR-011 - Exception Handling](../../../../ADR/011-exception-handling-patterns.md).
- [ADR-018 - Business Rule Chain](../../../../ADR/018-use-business-rule-chain-pattern.md).
- [ADR-019 - Service Validator](../../../../ADR/019-use-service-validator-pattern.md).
