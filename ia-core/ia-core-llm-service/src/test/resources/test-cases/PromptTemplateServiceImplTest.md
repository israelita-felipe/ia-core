# Caso de Teste: PromptTemplateServiceImplTest

## Descrição
Testa o serviço PromptTemplateServiceImpl responsável por gerenciar templates de prompts, incluindo criação, processamento, e recuperação de templates.

## Classe Testada
`com.ia.core.llm.service.template.PromptTemplateServiceImpl`

## Fluxo do Teste
1. Dado um PromptTemplateServiceImpl configurado com mock de TemplateRepository
2. Quando métodos de criação e processamento de templates são chamados
3. Então deve processar os templates corretamente
4. E deve interagir adequadamente com TemplateRepository

## Cenários
- createSimplePrompt com texto básico
- createSystemPrompt com instruções de sistema
- createPromptTemplate com template completo
- getTemplateById com ID existente
- getTemplateById com ID inexistente
- processTemplate com parâmetros válidos
- processTemplate com template não encontrado
- Verificação de persistência de templates

## Tipo de Teste
Unitário

## Desafios Específicos
- Mocking de TemplateRepository
- Verificação de processamento de templates com parâmetros
- Teste de diferentes tipos de templates

## Estratégia de Mocking
- Usar @Mock para TemplateRepository
- Verificar interações com verify()
- Testar diferentes variações de templates

## Aderência a ADRs

Este caso de teste foi atualizado para servir como documento vivo de rastreabilidade entre teste, CDU, domínio e decisões arquiteturais do `ia-core`.

### Metadados de contexto

| Campo | Valor |
|-------|-------|
| Componente | `PromptTemplateServiceImplTest` |
| Camada | Service |
| Tipo de teste | Unitário de Serviço |
| Domínio | Template / Prompt |
| CDU relacionada | CDU-LLM-004: Manter Template |

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
