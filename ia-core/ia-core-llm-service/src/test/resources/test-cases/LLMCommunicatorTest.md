# Caso de Teste: LLMCommunicatorTest

## Descrição
Testa o serviço LLMCommunicator responsável por enviar prompts para modelos LLM, verificando a integração com ChatModel, ChatMemory e VectorStoreOperations.

## Classe Testada
`com.ia.core.owl.service.LLMCommunicator`

## Fluxo do Teste
1. Dado um LLMCommunicator configurado com mocks de dependências
2. Quando métodos sendPrompt são chamados com diferentes tipos de entrada
3. Então deve processar os prompts corretamente
4. E deve interagir adequadamente com as dependências

## Cenários
- Envio de prompt como String
- Envio de prompt como Prompt
- Envio de prompt com ferramentas (tools)
- Verificação de configuração de URL base do Ollama
- Verificação de configuração de nome do modelo
- Interação com ChatMemory
- Interação com VectorStoreOperations

## Tipo de Teste
Unitário

## Desafios Específicos
- Mocking de ChatModel e suas dependências
- Verificação de construção correta de prompts
- Teste de integração com componentes Spring AI

## Estratégia de Mocking
- Usar @Mock para ChatModel, ChatMemory, VectorStoreOperations
- Usar ReflectionTestUtils para configurar campos privados (ollamaBaseUrl, modelName)
- Verificar interações com verify()

## Aderência a ADRs

Este caso de teste foi atualizado para servir como documento vivo de rastreabilidade entre teste, CDU, domínio e decisões arquiteturais do `ia-core`.

### Metadados de contexto

| Campo | Valor |
|-------|-------|
| Componente | `LLMCommunicatorTest` |
| Camada | Classe de Teste |
| Tipo de teste | Unitário |
| Domínio | LLM / Spring AI |
| CDU relacionada | CDU-LLM-002: Conversação com Chat LLM |

### Matriz de conformidade

| ADR | Tema | Aplicabilidade | Critério de conformidade |
|-----|------|----------------|--------------------------|
| ADR-010 | Padrões de Nomenclatura | Obrigatório | Verificar nomes de classes, DTOs, pacotes, métodos, variáveis e arquivos em PascalCase/camelCase conforme o papel do componente. |
| ADR-012 | Padrões de Teste Automatizado | Obrigatório | Cobrir cenário feliz, entradas inválidas, exceções, mocks/stubs, asserts claros e rastreabilidade com CDU/ADR; para componentes LLM, evitar chamadas externas não controladas. |
| ADR-050 | Diretrizes Gerais de Padronização | Obrigatório | Manter UTF-8, linguagem normativa clara, padrões de integração AI e referências técnicas vigentes. |
| ADR-052 | MADR e Linguagem Normativa | Obrigatório | Usar decisão explícita, contexto, critérios de aceitação e termos normativos sem ambiguidade. |

### Critérios de aceitação obrigatórios

- [ ] O caso informa objetivo, classe/componente testado, tipo de teste, domínio e CDU relacionado.
- [ ] O fluxo cobre cenário feliz, entradas inválidas, exceções e dependências relevantes.
- [ ] Os nomes de classes, métodos, arquivos e mensagens seguem ADR-010.
- [ ] Os asserts são explícitos, legíveis e preferencialmente usam AssertJ/JUnit 5 conforme ADR-012.
- [ ] Mocks, stubs e verificações de interação são documentados sem expor dados sensíveis.
- [ ] O documento está em UTF-8 e usa linguagem clara e consistente com ADR-050/ADR-052.
- [ ] Evitar chamadas reais a LLM externo em testes rápidos; usar mocks/stubs controlados.
- [ ] Quando a resposta do LLM for não determinística, validar propriedades semânticas ou estrutura esperada.

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
