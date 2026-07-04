# Caso de Teste: TemplateParameterDTO - View/Client

## Descrição
Caso de teste para a camada view/client do DTO `TemplateParameterDTO`. Parâmetro de template usado para preenchimento dinâmico de prompts.

## Classe Testada
`com.ia.core.llm.view.template.TemplateManager / TemplateClient`

## Stack do DTO
| Camada | Componente | Status |
|--------|------------|--------|
| Model | `com.ia.core.llm.model.template.TemplateParameter` | Implementado |
| Repository | `Não implementado` | Não implementado |
| Mapper | `com.ia.core.llm.service.template.TemplateParameterMapper` | Implementado |
| ServiceModel | [`com.ia.core.llm.service.model...TemplateParameterDTO`](../../../../ia-core-llm-service-model/src/main/java/com/ia/core/llm/service/model/template/TemplateParameterDTO.java) | Implementado |
| Service | `com.ia.core.llm.service.template.TemplateService` | Implementado |
| API/REST | `com.ia.core.llm.rest.web.TemplateController` | Implementado |
| View/Client | `com.ia.core.llm.view.template.TemplateManager / TemplateClient` | Implementado |

## Objetivo
Documentar e validar a camada view/client de `TemplateParameterDTO` dentro da stack completa do `ia-core-llm-*`.

## Fluxo do Teste
1. Dado o contexto do DTO `TemplateParameterDTO` no domínio Template.
2. Quando a camada view/client recebe ou produz dados compatíveis com `TemplateParameterDTO`.
3. Então o comportamento deve preservar os campos `nome`.
4. E deve manter rastreabilidade com [CDU/Manter-Template: Manter Template](../../../../CDU/Manter-Template/README.md).
5. E deve registrar falhas, exceções ou lacunas de implementação sem expor dados sensíveis.

## Cenários

### Cenário 1: Cenário feliz da camada view/client
**Given**: Dados válidos para `TemplateParameterDTO` no contexto `Template`.
**When**: A camada view/client processa o DTO.
**Then**: Deve preservar os campos obrigatórios, valores padrão e regras de validação aplicáveis.

### Cenário 2: Dados inválidos ou incompletos
**Given**: Dados ausentes, nulos, vazios ou fora dos limites definidos pelo DTO.
**When**: A camada view/client valida ou transforma `TemplateParameterDTO`.
**Then**: Deve rejeitar a operação ou retornar erro estruturado quando aplicável.

### Cenário 3: Exceções e falhas esperadas
**Given**: Dependência indisponível, dado inexistente ou violação de regra de negócio.
**When**: A camada view/client executa o fluxo.
**Then**: Deve propagar exceção esperada ou mapear para resposta segura conforme ADR-011.

### Cenário 4: Evidência de cobertura da stack
**Given**: Caso de teste documentado para `TemplateParameterDTO`.
**When**: A stack é comparada com os módulos `ia-core-llm-*`.
**Then**: Cada camada existente deve ter caso de teste correspondente; camadas não implementadas devem permanecer como lacuna explícita.

## Dependências
- JUnit 5
- AssertJ
- Mockito quando houver mocks/stubs
- Spring Boot Test para API/REST quando aplicável
- MockMvc ou WebTestClient quando aplicável
- Dados de teste sem informação sensível

## Referências
- [CDU/Manter-Template: Manter Template](../../../../CDU/Manter-Template/README.md)
- ADR 010: Padrões de Nomenclatura
- ADR 012: Padrões de Teste Automatizado
- ADR 050: Diretrizes Gerais de Padronização
- ADR 052: MADR e Linguagem Normativa

## Aderência a ADRs

Este caso de teste foi gerado como documento vivo de rastreabilidade entre teste, CDU, domínio e decisões arquiteturais do `ia-core`.

### Metadados de contexto

| Campo | Valor |
|-------|-------|
| Componente | `TemplateParameterDTO - View/Client` |
| Camada | View/Client |
| Tipo de teste | Unitário/Integração por camada |
| Domínio | Template |
| CDU relacionada | [CDU/Manter-Template: Manter Template](../../../../CDU/Manter-Template/README.md) |
| Status da camada | Implementado |

### Matriz de conformidade

| ADR | Tema | Aplicabilidade | Critério de conformidade |
|-----|------|----------------|--------------------------|
| ADR-010 | Padrões de Nomenclatura | Obrigatório | Nomes de arquivos, classes, métodos e campos seguem ADR-010. |
| ADR-012 | Padrões de Teste Automatizado | Obrigatório | Cenário feliz, negativo, dependências, mocks e rastreabilidade documentados. |
| ADR-050 | Diretrizes Gerais de Padronização | Obrigatório | Documento UTF-8, claro e alinhado à padronização do ia-core. |
| ADR-052 | MADR e Linguagem Normativa | Obrigatório | Critérios objetivos e termos normativos sem ambiguidade. |
| ADR-051 | RFC 9110 HTTP Semantics | Quando há integração REST | Client/Manager deve preservar semântica HTTP e tratar falhas de rede. |
| ADR-011 | Exception Handling | Quando há integração REST | Erros de client devem ser convertidos para mensagens seguras e rastreáveis. |


### Critérios de aceitação obrigatórios

- [ ] O caso informa objetivo, classe/componente testado, tipo de teste, domínio e CDU relacionado.
- [ ] O fluxo cobre cenário feliz, entradas inválidas, exceções e dependências relevantes.
- [ ] Os nomes de classes, métodos, arquivos e mensagens seguem ADR-010.
- [ ] Os asserts são explícitos, legíveis e preferencialmente usam AssertJ/JUnit 5 conforme ADR-012.
- [ ] Mocks, stubs e verificações de interação são documentados sem expor dados sensíveis.
- [ ] O documento está em UTF-8 e usa linguagem clara e consistente com ADR-050/ADR-052.
- [ ] Validar integração client/manager, tratamento de erro de rede, timeout e resposta segura para a interface.
- [ ] Validar que a camada View não contém regras de negócio ou persistência direta.


### Evidências esperadas

- Cenário feliz documentado com Given/When/Then ou fluxo equivalente.
- Cenários negativos e exceções documentados quando aplicáveis.
- Dependências, mocks, stubs e pré-condições explicitados.
- Resultados esperados verificáveis por teste automatizado.
- Rastreabilidade com CDU, domínio, classe/componente e ADRs aplicáveis.
- Lacunas de implementação explicitadas quando repository, mapper, API ou view não existirem.

### Referências ADR

- [ADR-010 - Padrões de Nomenclatura](../../../../ADR/010-nomenclature-standards.md)
- [ADR-012 - Padrões de Teste Automatizado](../../../../ADR/012-testing-patterns.md)
- [ADR-050 - Diretrizes Gerais de Padronização](../../../../ADR/050-standardization-guidelines.md)
- [ADR-052 - MADR e Linguagem Normativa](../../../../ADR/052-adopt-madr-and-rfc-normative-language-for-adrs.md)
- [ADR-051 - RFC 9110 HTTP Semantics](../../../../ADR/051-use-http-semantics-rfc7231.md)
- [ADR-011 - Exception Handling](../../../../ADR/011-exception-handling-patterns.md)

### Referências do projeto

- CDU: [CDU/Manter-Template: Manter Template](../../../../CDU/Manter-Template/README.md)
- DTO: [`TemplateParameterDTO`](../../../../ia-core-llm-service-model/src/main/java/com/ia/core/llm/service/model/template/TemplateParameterDTO.java)
