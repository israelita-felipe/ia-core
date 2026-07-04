# Caso de Teste: LLM - End-to-End (E2E)

## Descrição
Caso de teste end-to-end para validar fluxos completos de agentes LLM, simulando cenários reais de uso em produção com integração completa de serviços.

## Componentes Testados
- `com.ia.core.llm.service.agente.AgentOrchestratorService`
- `com.ia.core.llm.service.agente.session.AgentSessionService`
- `com.ia.core.llm.service.agente.ContextoConversacaoService`
- `com.ia.core.llm.service.chat.ChatService`
- `com.ia.core.llm.service.template.PromptTemplateService`
- `com.ia.core.llm.service.agente.OntologyBuilderService`
- `com.ia.core.llm.service.model.session.AgentSessionRequestDTO`
- `com.ia.core.llm.service.model.session.AgentSessionResponseDTO`
- `com.ia.core.llm.service.model.chat.ChatRequestDTO`
- `com.ia.core.llm.service.model.chat.ChatResponseDTO`

## Stack do DTO
| Camada | Componente | Status |
|--------|------------|--------|
| Model | AgentSession, ChatSession, ContextoConversacao, Agente, Skill, Template | Implementado |
| Repository | AgentSessionRepository, ChatSessionRepository, AgenteRepository | Implementado |
| Mapper | AgentSessionMapper, ChatMapper | Não implementado |
| ServiceModel | AgentSessionRequestDTO, AgentSessionResponseDTO, ChatRequestDTO, ChatResponseDTO, AgenteDTO | Implementado |
| Service | AgentOrchestratorService, AgentSessionService, ContextoConversacaoService, ChatService, PromptTemplateService | Implementado |
| API/REST | AgentSessionController, ChatController | Implementado |
| View/Client | AgentSessionManager, AgentSessionClient | Implementado |

## Objetivo
Documentar e validar fluxos end-to-end de agentes LLM, testando cenários completos que simulam uso real em produção, incluindo criação de sessões, conversações, uso de ferramentas, ontologia e gerenciamento de contexto.

## Fluxo do Teste
1. Dado o contexto de agentes LLM completo no domínio `Conversação com Chat LLM`.
2. Quando fluxos end-to-end são executados simulando cenários reais.
3. Então o comportamento deve validar a interação correta entre todos os componentes da stack.
4. E deve manter rastreabilidade completa com [CDU-LLM-002: Conversação com Chat LLM](../../../../CDU/LLM/CDU-LLM-002.md).
5. E deve garantir consistência entre banco de dados e serviços LLM.

## Cenários

### Cenário 1: Fluxo completo de conversação multi-turno
**Given**: Um usuário inicia uma conversação com um agente LLM.
**When**: Múltiplas mensagens são trocadas ao longo do tempo.
**Then**: Deve criar e manter a sessão corretamente.
**And**: Deve preservar o contexto entre turnos.
**And**: Deve processar cada mensagem através do ChatService.
**And**: Deve atualizar o contexto após cada interação.
**And**: Deve persistir o histórico completo no banco de dados.
**And**: O usuário deve poder retomar a conversação posteriormente.

### Cenário 2: Fluxo completo de uso de ferramentas complexas
**Given**: Um agente configurado com múltiplas ferramentas.
**When**: Durante a conversação, o agente precisa usar múltiplas ferramentas em sequência.
**Then**: Deve identificar a necessidade de cada ferramenta.
**And**: Deve invocar as ferramentas na ordem correta.
**And**: Deve passar os resultados de uma ferramenta para a próxima.
**And**: Deve incorporar todos os resultados na resposta final.
**And**: Deve registrar o uso de todas as ferramentas no contexto.

### Cenário 3: Fluxo completo de integração com ontologia dinâmica
**Given**: Uma sessão de agente com ontologia configurada.
**When**: A conversação requer informações da ontologia que são atualizadas dinamicamente.
**Then**: Deve consultar a ontologia atualizada.
**And**: Deve incorporar as informações mais recentes no contexto.
**And**: Deve atualizar a ontologia se a conversação gerar novos conhecimentos.
**And**: Deve manter consistência entre ontologia e contexto.
**And**: Deve permitir reutilização da ontologia em outras sessões.

### Cenário 4: Fluxo completo de gerenciamento de contexto longo
**Given**: Uma sessão de agente com conversação extensa.
**When**: O contexto excede os limites do modelo LLM.
**Then**: Deve identificar quando o contexto está muito grande.
**And**: Deve resumir o contexto preservando informações críticas.
**And**: Deve manter a coerência da conversação após o resumo.
**And**: Deve permitir recuperação de informações do contexto original.
**And**: Deve registrar as operações de resumo para auditoria.

### Cenário 5: Fluxo completo de tratamento de erros e recuperação
**Given**: Uma sessão de agente em que múltiplos erros podem ocorrer.
**When**: Erros ocorrem em diferentes pontos do fluxo (ChatService, ferramentas, ontologia).
**Then**: Deve capturar cada exceção adequadamente.
**Then**: Deve tentar recuperar usando estratégias de fallback.
**And**: Deve informar o usuário sobre erros de forma clara.
**And**: Deve permitir retentativa da operação falha.
**And**: Deve registrar todos os erros para diagnóstico.
**And**: A sessão deve permanecer funcional após erros recuperáveis.

### Cenário 6: Fluxo completo de múltiplos usuários concorrentes
**Given**: Múltiplos usuários interagindo com agentes simultaneamente.
**When**: As sessões são criadas e mantidas em paralelo.
**Then**: Deve criar cada sessão independentemente.
**And**: Deve manter contextos separados para cada usuário.
**And**: Deve evitar conflitos entre sessões.
**And**: Deve gerenciar recursos do ChatService adequadamente.
**And**: Deve garantir isolamento completo entre sessões.

### Cenário 7: Fluxo completo de personalização e templates
**Given**: Um agente configurado com múltiplos templates de prompt.
**When**: O agente precisa adaptar respostas baseado no contexto do usuário.
**Then**: Deve selecionar o template apropriado automaticamente.
**And**: Deve personalizar o template com informações do usuário.
**And**: Deve validar o prompt gerado antes de enviar ao ChatService.
**And**: Deve ajustar o estilo da resposta conforme preferências.
**And**: Deve registrar os templates usados para análise de eficácia.

### Cenário 8: Fluxo completo de análise e métricas
**Given**: Múltiplas sessões de agente executadas.
**When**: É solicitado um relatório de análise com métricas.
**Then**: Deve calcular métricas de uso (número de sessões, mensagens por sessão).
**And**: Deve analisar eficácia das ferramentas usadas.
**And**: Deve identificar padrões nas conversações.
**And**: Deve calcular tempo médio de resposta.
**And**: Deve permitir filtragem por período, usuário, tipo de agente.
**And**: Deve exportar o relatório em formato estruturado.

## Dependências
- JUnit 5
- AssertJ
- Spring Boot Test
- TestContainers para banco de dados real (PostgreSQL)
- Spring AI com Ollama para LLM real (opcional para e2e)
- Dados de teste sem informação sensível
- Ambiente de teste configurado para simular produção

## Configuração de Teste
```java
@SpringBootTest
@Testcontainers
@ActiveProfiles("testcontainers")
class LLME2ETest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15");

    @Container
    static OllamaContainer ollama = new OllamaContainer("ollama/ollama:latest")
        .withModel("llama2");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("spring.ai.ollama.base-url", ollama::getEndpoint);
    }

    @Autowired
    private AgentOrchestratorService agentOrchestratorService;

    @Autowired
    private AgentSessionService agentSessionService;

    @Autowired
    private ContextoConversacaoService contextoConversacaoService;

    @Autowired
    private ChatService chatService;

    @Autowired
    private PromptTemplateService promptTemplateService;

    @Autowired
    private OntologyBuilderService ontologyBuilderService;
}
```

## Estratégia de Execução
- Testes E2E devem ser executados manualmente ou em pipeline de CI/CD
- Podem usar Ollama para LLM real ou mock dependendo do cenário
- Devem usar banco de dados real via TestContainers
- Devem limpar o banco de dados antes de cada teste
- Devem validar estado final do banco de dados
- Devem verificar consistência entre banco e serviços

## Referências
- [CDU-LLM-002: Conversação com Chat LLM](../../../../CDU/LLM/CDU-LLM-002.md)
- ADR 010: Padrões de Nomenclatura
- ADR 012: Padrões de Teste Automatizado
- ADR 011: Exception Handling Patterns
- ADR 047: Usar UTF-8, Tags de Idioma e Web Linking para NLP
- ADR 050: Diretrizes Gerais de Padronização
- ADR 052: MADR e Linguagem Normativa

## Aderência a ADRs

Este caso de teste foi gerado como documento vivo de rastreabilidade entre teste, CDU, domínio e decisões arquiteturais do `ia-core`.

### Metadados de contexto

| Campo | Valor |
|-------|-------|
| Componente | `LLM - End-to-End` |
| Camada | E2E |
| Tipo de teste | End-to-End |
| Domínio | Agentes LLM e Conversação |
| CDU relacionada | [CDU-LLM-002: Conversação com Chat LLM](../../../../CDU/LLM/CDU-LLM-002.md) |
| Status da camada | Implementado |

### Matriz de conformidade

| ADR | Tema | Aplicabilidade | Critério de conformidade |
|-----|------|----------------|--------------------------|
| ADR-010 | Padrões de Nomenclatura | Obrigatório | Nomes de arquivos, classes, métodos e campos seguem ADR-010. |
| ADR-012 | Padrões de Teste Automatizado | Obrigatório | Cenário feliz, negativo, dependências, mocks e rastreabilidade documentados. |
| ADR-047 | UTF-8, Tags de Idioma e Web Linking para NLP | Aplicável | Validar uso de UTF-8 e tags de idioma em processamento NLP. |
| ADR-050 | Diretrizes Gerais de Padronização | Obrigatório | Documento UTF-8, claro e alinhado à padronização do ia-core. |
| ADR-052 | MADR e Linguagem Normativa | Obrigatório | Critérios objetivos e termos normativos sem ambiguidade. |
| ADR-011 | Exception Handling | Aplicável | Validar exceções de domínio, validação, códigos de erro e mensagens i18n. |

### Critérios de aceitação obrigatórios

- [ ] O caso informa objetivo, componentes testados, tipo de teste, domínio e CDU relacionado.
- [ ] O fluxo cobre cenários completos simulando uso real em produção.
- [ ] Os nomes de classes, métodos, arquivos e mensagens seguem ADR-010.
- [ ] Os asserts são explícitos, legíveis e preferencialmente usam AssertJ/JUnit 5 conforme ADR-012.
- [ ] Teste usa TestContainers para banco de dados real conforme ADR-012.
- [ ] Teste pode usar Ollama para LLM real ou mock dependendo do cenário.
- [ ] Teste valida estado final do banco de dados.
- [ ] Teste valida uso de UTF-8 e tags de idioma conforme ADR-047.
- [ ] O documento está em UTF-8 e usa linguagem clara e consistente com ADR-050/ADR-052.

### Evidências esperadas

- Cenários completos documentados com Given/When/Then.
- Cenários negativos e exceções documentados quando aplicáveis.
- Dependências e pré-condições explicitados.
- Resultados esperados verificáveis por teste automatizado.
- Rastreabilidade com CDU, domínio, componentes e ADRs aplicáveis.
- Configuração de TestContainers documentada.
- Estratégia de execução definida.
- Validação de NLP e tags de idioma documentada.

### Referências ADR

- [ADR-010 - Padrões de Nomenclatura](../../../../ADR/010-nomenclature-standards.md)
- [ADR-011 - Exception Handling Patterns](../../../../ADR/011-exception-handling-patterns.md)
- [ADR-012 - Padrões de Teste Automatizado](../../../../ADR/012-testing-patterns.md)
- [ADR-047 - Usar UTF-8, Tags de Idioma e Web Linking para NLP](../../../../ADR/047-use-utf-8-language-tags-web-linking-for-nlp.md)
- [ADR-050 - Diretrizes Gerais de Padronização](../../../../ADR/050-standardization-guidelines.md)
- [ADR-052 - MADR e Linguagem Normativa](../../../../ADR/052-adopt-madr-and-rfc-normative-language-for-adrs.md)

### Referências do projeto

- CDU: [CDU-LLM-002: Conversação com Chat LLM](../../../../CDU/LLM/CDU-LLM-002.md)
- Classes: [`AgentOrchestratorService`](../../../../ia-core-llm-service/src/main/java/com/ia/core/llm/service/agente/AgentOrchestratorService.java), [`AgentSessionService`](../../../../ia-core-llm-service/src/main/java/com/ia/core/llm/service/agente/session/AgentSessionService.java), [`ContextoConversacaoService`](../../../../ia-core-llm-service/src/main/java/com/ia/core/llm/service/agente/ContextoConversacaoService.java), [`ChatService`](../../../../ia-core-llm-service/src/main/java/com/ia/core/llm/service/chat/ChatService.java)
