# Caso de Teste: Agent Session - Integração

## Descrição
Caso de teste de integração para validar fluxos completos de sessões de agentes LLM, incluindo criação, manutenção de contexto, e execução de conversações.

## Componentes Testados
- `com.ia.core.llm.service.agente.AgentOrchestratorService`
- `com.ia.core.llm.service.agente.session.AgentSessionService`
- `com.ia.core.llm.service.agente.ContextoConversacaoService`
- `com.ia.core.llm.service.chat.ChatService`
- `com.ia.core.llm.service.model.session.AgentSessionRequestDTO`
- `com.ia.core.llm.service.model.session.AgentSessionResponseDTO`
- `com.ia.core.llm.service.model.chat.ChatRequestDTO`
- `com.ia.core.llm.service.model.chat.ChatResponseDTO`

## Stack do DTO
| Camada | Componente | Status |
|--------|------------|--------|
| Model | AgentSession, ChatSession, ContextoConversacao | Implementado |
| Repository | AgentSessionRepository, ChatSessionRepository | Implementado |
| Mapper | AgentSessionMapper, ChatMapper | Não implementado |
| ServiceModel | AgentSessionRequestDTO, AgentSessionResponseDTO, ChatRequestDTO, ChatResponseDTO | Implementado |
| Service | AgentOrchestratorService, AgentSessionService, ContextoConversacaoService, ChatService | Implementado |
| API/REST | AgentSessionController | Implementado |
| View/Client | AgentSessionManager, AgentSessionClient | Implementado |

## Objetivo
Documentar e validar fluxos de integração de sessões de agentes LLM, testando a interação entre múltiplos serviços e componentes da stack do `ia-core-llm-*`.

## Fluxo do Teste
1. Dado o contexto de agentes LLM no domínio `Conversação com Chat LLM`.
2. Quando fluxos de integração são executados envolvendo múltiplos serviços.
3. Então o comportamento deve validar a interação correta entre AgentOrchestratorService, AgentSessionService, ContextoConversacaoService e ChatService.
4. E deve manter rastreabilidade com [CDU-LLM-002: Conversação com Chat LLM](../../../../CDU/LLM/CDU-LLM-002.md).
5. E deve registrar falhas, exceções ou lacunas de implementação sem expor dados sensíveis.

## Cenários

### Cenário 1: Fluxo completo de criação e manutenção de sessão de agente
**Given**: Um agente configurado com skills e templates.
**When**: Uma sessão de agente é criada e múltiplas mensagens são trocadas.
**Then**: Deve criar a sessão no banco de dados.
**And**: Deve manter o contexto da conversação.
**And**: Deve processar cada mensagem através do ChatService.
**And**: Deve atualizar o contexto após cada interação.
**And**: Deve preservar o histórico da conversação.

### Cenário 2: Fluxo de integração com ontologia
**Given**: Uma sessão de agente com ontologia configurada.
**When**: O agente precisa acessar informações da ontologia durante a conversação.
**Then**: Deve consultar a ontologia através do OntologyBuilderService.
**And**: Deve incorporar informações da ontologia no contexto.
**And**: Deve usar as informações para gerar respostas mais precisas.
**And**: Deve atualizar a ontologia se necessário.

### Cenário 3: Fluxo de uso de ferramentas (tools)
**Given**: Um agente configurado com ferramentas disponíveis.
**When**: Durante a conversação, o agente precisa usar uma ferramenta.
**Then**: Deve identificar a necessidade da ferramenta.
**And**: Deve invocar a ferramenta com os parâmetros corretos.
**And**: Deve processar o resultado da ferramenta.
**And**: Deve incorporar o resultado na resposta ao usuário.
**And**: Deve registrar o uso da ferramenta no contexto.

### Cenário 4: Fluxo de gerenciamento de contexto
**Given**: Uma sessão de agente com múltiplas interações.
**When**: O contexto da conversação é gerenciado.
**Then**: Deve manter o histórico de mensagens.
**And**: Deve resumir o contexto quando necessário.
**And**: Deve limitar o tamanho do contexto para evitar exceder limites do modelo.
**And**: Deve preservar informações críticas durante o resumo.
**And**: Deve permitir recuperação de contexto anterior.

### Cenário 5: Fluxo de tratamento de erro e fallback
**Given**: Uma sessão de agente em que o ChatService pode falhar.
**When**: O ChatService retorna erro ou timeout.
**Then**: Deve capturar a exceção.
**And**: Deve tentar recuperar usando estratégia de fallback.
**And**: Deve informar o usuário sobre o erro de forma apropriada.
**And**: Deve registrar o erro para diagnóstico.
**And**: Deve permitir retentativa da operação.

### Cenário 6: Fluxo de múltiplas sessões concorrentes
**Given**: Múltiplos usuários criando sessões de agente simultaneamente.
**When**: As sessões são criadas e mantidas em paralelo.
**Then**: Deve criar cada sessão independentemente.
**And**: Deve manter contextos separados para cada sessão.
**And**: Deve evitar conflitos entre sessões.
**And**: Deve gerenciar recursos adequadamente.
**And**: Deve garantir isolamento entre sessões.

### Cenário 7: Fluxo de encerramento e limpeza de sessão
**Given**: Uma sessão de agente ativa com contexto.
**When**: A sessão é encerrada.
**Then**: Deve finalizar a sessão no banco de dados.
**And**: Deve persistir o histórico da conversação.
**And**: Deve liberar recursos alocados.
**And**: Deve permitir recriação da sessão com contexto preservado.
**And**: Deve registrar o encerramento para auditoria.

### Cenário 8: Fluxo de integração com templates
**Given**: Um agente configurado com templates de prompt.
**When**: O agente precisa gerar prompts para o ChatService.
**Then**: Deve selecionar o template apropriado.
**And**: Deve preencher os parâmetros do template.
**And**: Deve validar o prompt gerado.
**And**: Deve usar o prompt no ChatService.
**And**: Deve registrar o template usado para análise.

## Dependências
- JUnit 5
- AssertJ
- Spring Boot Test
- TestContainers para banco de dados real (PostgreSQL)
- Spring AI (ChatModel mockado ou com Ollama)
- Dados de teste sem informação sensível
- Mock de ChatModel para evitar chamadas externas

## Configuração de Teste
```java
@SpringBootTest
@Testcontainers
@ActiveProfiles("testcontainers")
class AgentSessionIT {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @MockBean
    private ChatModel chatModel;

    @Autowired
    private AgentOrchestratorService agentOrchestratorService;

    @Autowired
    private AgentSessionService agentSessionService;

    @Autowired
    private ContextoConversacaoService contextoConversacaoService;

    @Autowired
    private ChatService chatService;
}
```

## Estratégia de Mocking
- Usar @MockBean para ChatModel para evitar chamadas externas
- Configurar respostas mockadas para diferentes cenários
- Validar construção de prompts sem depender de LLM real
- Testar lógica de negócio independentemente do modelo LLM

## Referências
- [CDU-LLM-002: Conversação com Chat LLM](../../../../CDU/LLM/CDU-LLM-002.md)
- ADR 010: Padrões de Nomenclatura
- ADR 012: Padrões de Teste Automatizado
- ADR 011: Exception Handling Patterns
- ADR 050: Diretrizes Gerais de Padronização
- ADR 052: MADR e Linguagem Normativa

## Aderência a ADRs

Este caso de teste foi gerado como documento vivo de rastreabilidade entre teste, CDU, domínio e decisões arquiteturais do `ia-core`.

### Metadados de contexto

| Campo | Valor |
|-------|-------|
| Componente | `Agent Session - Integração` |
| Camada | Integração |
| Tipo de teste | Integração |
| Domínio | Agentes LLM e Conversação |
| CDU relacionada | [CDU-LLM-002: Conversação com Chat LLM](../../../../CDU/LLM/CDU-LLM-002.md) |
| Status da camada | Implementado |

### Matriz de conformidade

| ADR | Tema | Aplicabilidade | Critério de conformidade |
|-----|------|----------------|--------------------------|
| ADR-010 | Padrões de Nomenclatura | Obrigatório | Nomes de arquivos, classes, métodos e campos seguem ADR-010. |
| ADR-012 | Padrões de Teste Automatizado | Obrigatório | Cenário feliz, negativo, dependências, mocks e rastreabilidade documentados. |
| ADR-050 | Diretrizes Gerais de Padronização | Obrigatório | Documento UTF-8, claro e alinhado à padronização do ia-core. |
| ADR-052 | MADR e Linguagem Normativa | Obrigatório | Critérios objetivos e termos normativos sem ambiguidade. |
| ADR-011 | Exception Handling | Aplicável | Validar exceções de domínio, validação, códigos de erro e mensagens i18n. |

### Critérios de aceitação obrigatórios

- [ ] O caso informa objetivo, componentes testados, tipo de teste, domínio e CDU relacionado.
- [ ] O fluxo cobre cenário feliz, entradas inválidas, exceções e dependências relevantes.
- [ ] Os nomes de classes, métodos, arquivos e mensagens seguem ADR-010.
- [ ] Os asserts são explícitos, legíveis e preferencialmente usam AssertJ/JUnit 5 conforme ADR-012.
- [ ] Teste usa TestContainers para banco de dados real conforme ADR-012.
- [ ] Teste valida interação entre múltiplos serviços.
- [ ] Teste usa mock de ChatModel para evitar chamadas externas conforme ADR-012.
- [ ] O documento está em UTF-8 e usa linguagem clara e consistente com ADR-050/ADR-052.

### Evidências esperadas

- Cenário feliz documentado com Given/When/Then ou fluxo equivalente.
- Cenários negativos e exceções documentados quando aplicáveis.
- Dependências, mocks, stubs e pré-condições explicitados.
- Resultados esperados verificáveis por teste automatizado.
- Rastreabilidade com CDU, domínio, componentes e ADRs aplicáveis.
- Configuração de TestContainers documentada.
- Estratégia de mocking documentada.

### Referências ADR

- [ADR-010 - Padrões de Nomenclatura](../../../../ADR/010-nomenclature-standards.md)
- [ADR-011 - Exception Handling Patterns](../../../../ADR/011-exception-handling-patterns.md)
- [ADR-012 - Padrões de Teste Automatizado](../../../../ADR/012-testing-patterns.md)
- [ADR-050 - Diretrizes Gerais de Padronização](../../../../ADR/050-standardization-guidelines.md)
- [ADR-052 - MADR e Linguagem Normativa](../../../../ADR/052-adopt-madr-and-rfc-normative-language-for-adrs.md)

### Referências do projeto

- CDU: [CDU-LLM-002: Conversação com Chat LLM](../../../../CDU/LLM/CDU-LLM-002.md)
- Classes: [`AgentOrchestratorService`](../../../../ia-core-llm-service/src/main/java/com/ia/core/llm/service/agente/AgentOrchestratorService.java), [`AgentSessionService`](../../../../ia-core-llm-service/src/main/java/com/ia/core/llm/service/agente/session/AgentSessionService.java), [`ContextoConversacaoService`](../../../../ia-core-llm-service/src/main/java/com/ia/core/llm/service/agente/ContextoConversacaoService.java), [`ChatService`](../../../../ia-core-llm-service/src/main/java/com/ia/core/llm/service/chat/ChatService.java)
