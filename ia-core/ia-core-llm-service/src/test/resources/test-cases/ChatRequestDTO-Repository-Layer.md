# Caso de Teste: ChatRequestDTO - Repositório

## Descrição
Caso de teste para a camada repositório do DTO `ChatRequestDTO`. Solicitação de mensagem enviada ao agente LLM dentro de uma sessão.

## Classe Testada
`com.ia.core.llm.service.chat.ChatSessionRepository`

## Stack do DTO
| Camada | Componente | Status |
|--------|------------|--------|
| Model | `com.ia.core.llm.service.model.chat.ChatRequestDTO` | Implementado |
| Repository | `com.ia.core.llm.service.chat.ChatSessionRepository` | Implementado |
| Mapper | `Não implementado` | Não implementado |
| ServiceModel | [`com.ia.core.llm.service.model...ChatRequestDTO`](../../../../ia-core-llm-service-model/src/main/java/com/ia/core/llm/service/model/chat/ChatRequestDTO.java) | Implementado |
| Service | `com.ia.core.llm.service.chat.ChatService` | Implementado |
| API/REST | `com.ia.core.llm.rest.web.ChatSessionController` | Implementado |
| View/Client | `com.ia.core.llm.view.chat.ChatManager / ChatClient` | Implementado |

## Objetivo
Documentar e validar a camada repositório de `ChatRequestDTO` dentro da stack completa do `ia-core-llm-*`.

## Fluxo do Teste
1. Dado o contexto do DTO `ChatRequestDTO` no domínio Conversação Chat.
2. Quando a camada repositório recebe ou produz dados compatíveis com `ChatRequestDTO`.
3. Então o comportamento deve preservar os campos `promptId`, `request`, `text`, `sessionId`.
4. E deve manter rastreabilidade com [CDU/Conversacao-Chat: Conversação Chat](../../../../CDU/Conversacao-Chat/README.md).
5. E deve registrar falhas, exceções ou lacunas de implementação sem expor dados sensíveis.

## Cenários

### Cenário 1: Cenário feliz da camada repositório
**Given**: Dados válidos para `ChatRequestDTO` no contexto `Conversação Chat`.
**When**: A camada repositório processa o DTO.
**Then**: Deve preservar os campos obrigatórios, valores padrão e regras de validação aplicáveis.

### Cenário 2: Dados inválidos ou incompletos
**Given**: Dados ausentes, nulos, vazios ou fora dos limites definidos pelo DTO.
**When**: A camada repositório valida ou transforma `ChatRequestDTO`.
**Then**: Deve rejeitar a operação ou retornar erro estruturado quando aplicável.

### Cenário 3: Exceções e falhas esperadas
**Given**: Dependência indisponível, dado inexistente ou violação de regra de negócio.
**When**: A camada repositório executa o fluxo.
**Then**: Deve propagar exceção esperada ou mapear para resposta segura conforme ADR-011.

### Cenário 4: Evidência de cobertura da stack
**Given**: Caso de teste documentado para `ChatRequestDTO`.
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
- [CDU/Conversacao-Chat: Conversação Chat](../../../../CDU/Conversacao-Chat/README.md)
- ADR 010: Padrões de Nomenclatura
- ADR 012: Padrões de Teste Automatizado
- ADR 050: Diretrizes Gerais de Padronização
- ADR 052: MADR e Linguagem Normativa

## Aderência a ADRs

Este caso de teste foi gerado como documento vivo de rastreabilidade entre teste, CDU, domínio e decisões arquiteturais do `ia-core`.

### Metadados de contexto

| Campo | Valor |
|-------|-------|
| Componente | `ChatRequestDTO - Repositório` |
| Camada | Repositório |
| Tipo de teste | Unitário/Integração por camada |
| Domínio | Conversação Chat |
| CDU relacionada | [CDU/Conversacao-Chat: Conversação Chat](../../../../CDU/Conversacao-Chat/README.md) |
| Status da camada | Implementado |

### Matriz de conformidade

| ADR | Tema | Aplicabilidade | Critério de conformidade |
|-----|------|----------------|--------------------------|
| ADR-010 | Padrões de Nomenclatura | Obrigatório | Nomes de arquivos, classes, métodos e campos seguem ADR-010. |
| ADR-012 | Padrões de Teste Automatizado | Obrigatório | Cenário feliz, negativo, dependências, mocks e rastreabilidade documentados. |
| ADR-050 | Diretrizes Gerais de Padronização | Obrigatório | Documento UTF-8, claro e alinhado à padronização do ia-core. |
| ADR-052 | MADR e Linguagem Normativa | Obrigatório | Critérios objetivos e termos normativos sem ambiguidade. |
| ADR-017 | Flyway/Migrações | Quando persistido | Consultas e evolução de schema devem ser rastreáveis a migrações quando aplicável. |


### Critérios de aceitação obrigatórios

- [ ] O caso informa objetivo, classe/componente testado, tipo de teste, domínio e CDU relacionado.
- [ ] O fluxo cobre cenário feliz, entradas inválidas, exceções e dependências relevantes.
- [ ] Os nomes de classes, métodos, arquivos e mensagens seguem ADR-010.
- [ ] Os asserts são explícitos, legíveis e preferencialmente usam AssertJ/JUnit 5 conforme ADR-012.
- [ ] Mocks, stubs e verificações de interação são documentados sem expor dados sensíveis.
- [ ] O documento está em UTF-8 e usa linguagem clara e consistente com ADR-050/ADR-052.
- [ ] Validar consultas, paginação, filtros, ausência de dados e persistência quando houver repositório.
- [ ] Documentar lacuna quando repositório específico ainda não existir.


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
- [ADR-017 - Flyway/Migrações](../../../../ADR/017-use-flyway-for-database-migrations.md)

### Referências do projeto

- CDU: [CDU/Conversacao-Chat: Conversação Chat](../../../../CDU/Conversacao-Chat/README.md)
- DTO: [`ChatRequestDTO`](../../../../ia-core-llm-service-model/src/main/java/com/ia/core/llm/service/model/chat/ChatRequestDTO.java)
