# Caso de Teste: AgenteTest

## Descrição
Testa a entidade Agente do modelo de domínio LLM, verificando a construção correta de objetos, validações, e comportamentos da entidade.

## Classe Testada
`com.ia.core.llm.model.agente.Agente`

## Fluxo do Teste
1. Dado um objeto Agente com todos os campos preenchidos
2. Quando o objeto é construído e validado
3. Então deve manter a integridade dos dados
4. E deve permitir adicionar e remover ferramentas e habilidades

## Cenários
- Construção de Agente com builder pattern
- Validação de campos obrigatórios
- Adição de ferramentas (tools) à coleção
- Remoção de ferramentas da coleção
- Adição de habilidades (skills) à coleção
- Remoção de habilidades da coleção
- Verificação de campos de identificação
- Verificação de campos de configuração (temperature, maxTokens)

## Tipo de Teste
Unitário

## Dependências
- Lombok para builder pattern
- Validações de bean validation

## Aderência a ADRs

Este caso de teste foi atualizado para servir como documento vivo de rastreabilidade entre teste, CDU, domínio e decisões arquiteturais do `ia-core`.

### Metadados de contexto

| Campo | Valor |
|-------|-------|
| Componente | `AgenteTest` |
| Camada | Classe de Teste |
| Tipo de teste | Unitário |
| Domínio | Agente / Conversação |
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
