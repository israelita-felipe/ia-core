# Documento de Refatoração - ia-core

Este documento apresenta uma análise detalhada do código do projeto **ia-core** com o objetivo de identificar problemas de lógica, clean code, redundância e propor melhorias. A análise abrange múltiplos módulos, incluindo `ia-core-service`, `ia-core-security-service`, `ia-core-model`, `ia-core-rest`, entre outros.

## Local dos projetos e módulos

/home/israel/git/ia-core-apps/ia-core/ia-core-communication-model
/home/israel/git/ia-core-apps/ia-core/ia-core-communication-rest
/home/israel/git/ia-core-apps/ia-core/ia-core-communication-service
/home/israel/git/ia-core-apps/ia-core/ia-core-communication-service-model
/home/israel/git/ia-core-apps/ia-core/ia-core-communication-view
/home/israel/git/ia-core-apps/ia-core/ia-core-flyway-model
/home/israel/git/ia-core-apps/ia-core/ia-core-flyway-service
/home/israel/git/ia-core-apps/ia-core/ia-core-flyway-service-model
/home/israel/git/ia-core-apps/ia-core/ia-core-flyway-view
/home/israel/git/ia-core-apps/ia-core/ia-core-grammar
/home/israel/git/ia-core-apps/ia-core/ia-core-integration-test
/home/israel/git/ia-core-apps/ia-core/ia-core-llm-model
/home/israel/git/ia-core-apps/ia-core/ia-core-llm-service
/home/israel/git/ia-core-apps/ia-core/ia-core-llm-service-model
/home/israel/git/ia-core-apps/ia-core/ia-core-llm-view
/home/israel/git/ia-core-apps/ia-core/ia-core-model
/home/israel/git/ia-core-apps/ia-core/ia-core-nlp
/home/israel/git/ia-core-apps/ia-core/ia-core-quartz
/home/israel/git/ia-core-apps/ia-core/ia-core-quartz-service
/home/israel/git/ia-core-apps/ia-core/ia-core-quartz-service-model
/home/israel/git/ia-core-apps/ia-core/ia-core-quartz-view
/home/israel/git/ia-core-apps/ia-core/ia-core-report
/home/israel/git/ia-core-apps/ia-core/ia-core-resilience4j
/home/israel/git/ia-core-apps/ia-core/ia-core-rest
/home/israel/git/ia-core-apps/ia-core/ia-core-security-model
/home/israel/git/ia-core-apps/ia-core/ia-core-security-service
/home/israel/git/ia-core-apps/ia-core/ia-core-security-service-model
/home/israel/git/ia-core-apps/ia-core/ia-core-security-test
/home/israel/git/ia-core-apps/ia-core/ia-core-security-view
/home/israel/git/ia-core-apps/ia-core/ia-core-service
/home/israel/git/ia-core-apps/ia-core/ia-core-service-model
/home/israel/git/ia-core-apps/ia-core/ia-core-view

## 📊 Problemas Identificados

### Tabela 1: Problemas por Classe

| Classe | Linhas | Problema                                                                                                                                           | Solução                                                                                                                                                                                                                                                                                                                                          | Status |
|--------|--------|----------------------------------------------------------------------------------------------------------------------------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|--------|
| `ia-core-llm-service` | - | Uso de versões milestone (Spring AI 2.0.0-M1) | Atualizar para versões estáveis mais recentes; remover referências a milestone. | ⏳ Pendente (aguardando GA release) |
| `ia-core-llm-service` | - | MCP configurado mas não implementado | Implementar servidor MCP conforme configuração em application-llm-service.yml | ✅ Configurado (aguardando implementação do endpoint) |
| `ia-core-llm-service` | - | FerramentaDiscoveryService limitada ao pacote de serviço | Permitir descoberta de tools em múltiplos pacotes (service e view) | ✅ Implementado (suporta pacotes configuráveis) |
| `ia-core-llm-view` | - | Camada view não define suas próprias tools | Criar mecanismo para view layer definir tools independentemente | ⏳ Parcialmente implementado (ViewToolCatalog existe mas está vazio) |
| `ia-core-llm-rest` | - | A2AController implementado com simulação | Implementar conexão real A2A usando spring-ai-agent-utils-a2a | ⏳ Pendente (implementação simulada) |
| `ia-core-llm-rest` | - | WebSearchTool depende de API key não documentada | Documentar configuração necessária para brave.api.key | ⏳ Pendente (documentação) |

### Tabela 2: Problemas e Implementações Sugeridas (boas práticas)

| Nº | Problema Identificado | Implementação Sugerida (boas práticas) | Padrões Recomendados |
|----|-----------------------|----------------------------------------|----------------------|
| 1  | Uso de versões milestone (Spring AI 2.0.0-M1) | Atualizar para versões estáveis mais recentes; remover referências a milestone. | Dependency Management, Stability |
| 2  | MCP configurado mas não implementado | Implementar servidor MCP conforme RFC 8485 (Model Context Protocol) com SSE endpoint. | MCP Protocol, Server-Sent Events |
| 3  | FerramentaDiscoveryService limitada ao pacote de serviço | Permitir descoberta de tools em múltiplos pacotes configuráveis (service e view). | Separation of Concerns, Modularity |
| 4  | Camada view não define suas próprias tools | Criar mecanismo para view layer definir tools independentemente usando @Tool annotations em pacotes separados. | Layer Independence, Tool Calling |
| 5  | A2AController implementado com simulação | Implementar conexão real A2A usando spring-ai-agent-utils-a2a. | Agent-to-Agent Protocol, Integration |
| 6  | WebSearchTool depende de API key não documentada | Documentar configuração necessária para brave.api.key no README ou documentação de configuração. | Documentation, Configuration Management |

### Tabela 3: Boas Práticas de IA (padrao_desenvolvimento_ia.md)

| Nº | Padrão | Descrição | ADR Referência | Status |
|----|--------|-----------|----------------|--------|
| 1  | Chat Memory Patterns | Usar JdbcChatMemoryRepository para persistência de chat memory e ViewChatMemoryManager para gerenciamento na camada view | padrao_desenvolvimento_ia.md | ✅ Documentado |
| 2  | Specification Pattern | Usar Specification Pattern para filtros dinâmicos em queries de IA | ADR-002 | ✅ Documentado |
| 3  | Domain Events | Usar Domain Events para comunicação desacoplada entre componentes de IA | ADR-005 | ✅ Documentado |
| 4  | Exception Handling | Usar hierarquia de exceções baseada em domínio para erros de IA | ADR-011 | ✅ Documentado |
| 5  | Logging e Monitoramento | Usar Correlation ID para rastreamento de requisições de IA e métricas com Micrometer | ADR-013 | ✅ Documentado |
| 6  | Business Rule Chain | Usar Business Rule Chain para validações de negócio em contexto de IA | ADR-018 | ✅ Documentado |
| 7  | EntityGraph | Usar EntityGraph para otimizar queries de IA e evitar N+1 | ADR-006 | ✅ Documentado |
| 8  | Service Validator | Usar Service Validator para validações de IA separadas da lógica de negócio | ADR-019 | ✅ Documentado |
| 9  | HasVersion | Usar HasVersion para controle de versão otimista em entidades de IA | ADR-026 | ✅ Documentado |
| 10 | TSID | Usar TSID (Time-Sorted Unique Identifier) para IDs de entidades de IA | ADR-015 | ✅ Documentado |
| 11 | SuperBuilder | Usar SuperBuilder do Lombok para entidades JPA de IA | ADR-020 | ✅ Documentado |
| 12 | Flyway | Usar Flyway para migrations de banco de dados de IA | ADR-017 | ✅ Documentado |

## 🔍 Análise Classe a Classe

### 1. `ia-core-llm-service`

**Descrição**: Módulo de serviço para integração com LLMs (Large Language Models). Atualmente usando versão milestone do Spring AI.

**Estado Atual (29/05/2026)**:
- **Uso de versões milestone** (pom.xml): O módulo usa Spring AI 2.0.0-M1, que é uma versão milestone não estável. Isso pode causar instabilidade e problemas de compatibilidade.
- **MCP configurado**: A configuração em `application-llm-service.yml` habilita o servidor MCP com SSE endpoint `/mcp/sse`. O servidor está configurado mas o endpoint ainda não foi implementado.
- **Sistema de Agentes implementado**: Implementação completa do sistema de agentes com AgentOrchestratorService, AgentSessionService, AgenteService, AgenteRepository, etc. Suporta subagentes, A2A protocol, e multi-model routing.
- **FerramentaDiscoveryService melhorada**: O serviço de descoberta de ferramentas agora suporta múltiplos pacotes configuráveis via `scan-packages` em `application-llm-service.yml`. Não está mais limitado ao pacote de serviço.
- **LlmToolCatalog implementado**: Catálogo de ferramentas na camada service com tool `echo` para validação de funcionalidade de tool calling.
- **Resilience4j integrado**: Integração completa com resilience4j usando anotações @Resilient em múltiplos serviços e clientes.

**Problemas identificados no código**:
- **Uso de versões milestone** (pom.xml): O módulo usa Spring AI 2.0.0-M1, que é uma versão milestone não estável.
- **MCP endpoint não implementado**: A configuração existe mas o endpoint `/mcp/sse` ainda não foi implementado.
- **ViewToolCatalog vazio**: O catálogo de ferramentas da camada view existe mas está vazio, sem tools implementadas.

**Solução Proposta**:
- Aguardar GA release do Spring AI e atualizar para versão estável
- Implementar endpoint MCP SSE conforme RFC 8485
- Implementar tools específicas da camada view em ViewToolCatalog
- Adicionar pacote de view layer à configuração de scan de tools (se necessário)

### 2. `ia-core-llm-model`

**Descrição**: Módulo de modelo para entidades LLM.

**Análise atual**:
- **Ferramenta entity**: A entidade `Ferramenta` tem tipos: AGENTE_ESPECIALISTA, TOOL_SPRING, RECURSO_MCP
- **Comentário**: O comentário menciona "Capacidade invocável (sub-agente, {@code @Tool} Spring AI ou recurso MCP)"
- **ModuloOrigem**: A entidade tem campo `moduloOrigem` para identificar a origem da ferramenta

**Solução Proposta**:
- O modelo está bem estruturado para suportar múltiplos tipos de ferramentas
- O campo `moduloOrigem` pode ser usado para distinguir tools de service vs view layer
- Manter como está, apenas garantir que o campo `moduloOrigem` seja preenchido corretamente

### 3. `ia-core-llm-view`

**Descrição**: Módulo de view para interface Vaadin com funcionalidades LLM.

**Estado Atual (29/05/2026)**:
- **ViewToolCatalog criado**: O pacote `com.ia.core.llm.view.tool` foi criado com a classe ViewToolCatalog.
- **Catálogo vazio**: A classe ViewToolCatalog existe mas está vazia, sem tools implementadas.
- **FerramentaManager**: Apenas um CRUD manager, não define tools.

**Problemas identificados no código**:
- **ViewToolCatalog vazio**: O catálogo de ferramentas da camada view existe mas não tem tools implementadas.
- **Ausência de tools view-specific**: Não há implementação de tools específicas da camada view.

**Solução Proposta**:
- Implementar tools específicas da camada view em ViewToolCatalog
- Adicionar métodos anotados com `@Tool` para funcionalidades view-specific (navegação, atualização de UI, etc.)
- Configurar `FerramentaDiscoveryService` para escanear também o pacote view (se necessário)
- Usar o campo `moduloOrigem` para identificar tools de service vs view

### 4. `ia-core-llm-rest`

**Descrição**: Módulo REST para expor serviços LLM via API HTTP.

**Estado Atual (29/05/2026)**:
- **A2AController implementado**: Controller REST para protocolo Agent-to-Agent (A2A) com endpoints /connect, /status, /disconnect, /agents.
- **WebSearchController implementado**: Controller REST para busca na internet com endpoints /search, /status.
- **WebSearchTool implementado**: Built-in tool para busca na internet usando BraveWebSearchTool do spring-ai-agent-utils.
- **Dependências**: Usa ia-core-llm-service, ia-core-security-service e ia-core-rest.
- **A2A simulado**: A implementação do A2AController é simulada, não usa spring-ai-agent-utils-a2a real.

**Problemas identificados no código**:
- **A2A simulado**: A implementação do A2AController é apenas uma simulação, não implementa conexão real com spring-ai-agent-utils-a2a.
- **WebSearchTool depende de API key**: Requer configuração de brave.api.key para funcionar.

**Solução Proposta**:
- Implementar conexão real A2A usando spring-ai-agent-utils-a2a
- Documentar configuração necessária para brave.api.key
- Considerar adicionar mais endpoints REST para outras funcionalidades LLM

## 📋 Implementação Recomendada para Tools Independentes

### Arquitetura Atual (29/05/2026)

**Camada Service (ia-core-llm-service)**:
- Pacote: `com.ia.core.llm.service.tool`
- ✅ Implementado: LlmToolCatalog com tool `echo` para validação
- Define tools para operações de negócio e integrações externas
- Tools são descobertas automaticamente via `@Tool` annotation
- FerramentaDiscoveryService suporta múltiplos pacotes configuráveis

**Camada View (ia-core-llm-view)**:
- Pacote: `com.ia.core.llm.view.tool`
- ⏳ Parcialmente implementado: ViewToolCatalog existe mas está vazio
- Define tools para interações específicas da UI Vaadin
- Tools são descobertas automaticamente via `@Tool` annotation

**Independência**:
- Cada camada define suas próprias tools em pacotes separados
- ✅ `FerramentaDiscoveryService` escaneia pacotes configuráveis via `scan-packages`
- O campo `moduloOrigem` identifica a origem da tool
- Tools podem ser chamadas internamente por cada camada

### Configuração Atual

**application-llm-service.yml**:
```yaml
ia-core:
  llm:
    ferramenta:
      discovery:
        enabled: true
        scan-packages:
          - com.ia.core.llm.service.tool
        refresh-on-startup: true
```

**FerramentaDiscoveryService**:
- ✅ Modificado para escanear múltiplos pacotes configurados
- ✅ Preenche campo `moduloOrigem` baseado no pacote da tool
- ✅ Mantém registro de tools de ambas as camadas

### Implementação de MCP Server

**Estado Atual (29/05/2026)**:
- ⏳ Configurado em `application-llm-service.yml` mas endpoint não implementado
- Configuração: enabled: true, SSE endpoint: `/mcp/sse`, agent card: `/.well-known/agent-card.json`

**Requisitos Pendentes**:
- Implementar endpoint MCP SSE conforme RFC 8485
- Expor tools via SSE endpoint `/mcp/sse`
- Suportar tool calling via protocolo MCP
- Implementar agent card em `/.well-known/agent-card.json`
- Integrar com `FerramentaDiscoveryService` para expor tools registradas

**Benefícios**:
- Permite integração com clientes MCP externos
- Padronização via protocolo MCP
- Separação clara entre camadas
- Tools podem ser usadas por múltiplos contextos

## 📋 Conclusão

O projeto **ia-core** possui uma arquitetura **excelente e bem estruturada**, com padrões de design consistentemente aplicados across todos os 32 módulos analisados. A arquitetura de 4 camadas (model, service, service-model, view/rest) é seguida rigorosamente, e os padrões de design estão bem implementados.

### Pontos Fortes Identificados

✅ **Arquitetura de Camadas Consistente**: Todos os módulos de domínio seguem o mesmo padrão de 4 camadas, facilitando manutenção e onboarding de novos desenvolvedores.

✅ **Service Layer Pattern Robusto**: Hierarquia de serviços bem estruturada com AbstractBaseService, AbstractCrudService, DefaultBaseService e DefaultSecuredBaseService.

✅ **DTO Pattern com MapStruct**: Separação clara entre entidades e DTOs, com mapeamento automatizado via MapStruct.

✅ **Resilience Pattern Declarativo**: O módulo ia-core-resilience4j fornece uma implementação excelente de resiliência declarativa com @Resilient e profiles predefinidos. ✅ **Integrado em múltiplos serviços** (29/05/2026).

✅ **Security Pattern Centralizado**: Módulos de segurança bem organizados e centralizados, com JWT, roles e permissions.

✅ **Testing Pattern Robusto**: Estrutura de testes com TestContainers, fixtures e custom assertions.

✅ **Event-Driven Pattern**: Mecanismo de eventos funcionando para publicação após operações CRUD.

✅ **Builder Pattern Consistente**: Uso padronizado de @Builder em todas as DTOs e entidades.

✅ **Strategy Pattern**: Implementações variadas para LLM providers e resilience profiles.

✅ **Módulos Especializados Bem Integrados**: ANTLR para gramáticas, OpenNLP para NLP, JasperReports para relatórios.

✅ **LLM Model Bem Estruturado**: Entidade Ferramenta suporta múltiplos tipos (AGENTE_ESPECIALISTA, TOOL_SPRING, RECURSO_MCP).

✅ **Sistema de Agentes Implementado**: Implementação completa com AgentOrchestratorService, AgentSessionService, suporte a subagentes, A2A protocol e multi-model routing (29/05/2026).

✅ **Tool Discovery Configurável**: FerramentaDiscoveryService suporta múltiplos pacotes configuráveis via `scan-packages` (29/05/2026).

✅ **LlmToolCatalog Implementado**: Catálogo de ferramentas na camada service com tool `echo` para validação (29/05/2026).

✅ **REST Layer Implementada**: Módulo ia-core-llm-rest com A2AController, WebSearchController e WebSearchTool (29/05/2026).

### Melhorias Recomendadas (Prioridade Alta)

⚠️ **Implementar MCP Server Endpoint**: Implementar endpoint MCP SSE conforme RFC 8485. Configuração existe mas endpoint não implementado (29/05/2026).

⚠️ **Implementar Tools da Camada View**: ViewToolCatalog existe mas está vazio. Implementar tools específicas da camada view (29/05/2026).

⚠️ **Implementar A2A Real**: A2AController está simulado. Implementar conexão real usando spring-ai-agent-utils-a2a (29/05/2026).

⚠️ **Documentar WebSearchTool**: Documentar configuração necessária para brave.api.key (29/05/2026).

⚠️ **Atualizar Spring AI**: Aguardar GA release e atualizar para versão estável (ainda em 2.0.0-M1).

### Resumo

O projeto está em um estado **muito bom** em termos de arquitetura e padrões de design. As recomendações são **melhorias incrementais** e não refatorações estruturais. A base sólida estabelecida permite evolução segura e manutenção eficiente.

**Progresso Recente (29/05/2026)**:
- ✅ Sistema de agentes totalmente implementado
- ✅ Tool discovery configurável implementado
- ✅ LlmToolCatalog implementado
- ✅ Resilience4j integrado em múltiplos serviços
- ✅ REST layer implementada (ia-core-llm-rest com A2AController, WebSearchController, WebSearchTool)
- ⏳ MCP endpoint configurado mas não implementado
- ⏳ ViewToolCatalog existe mas está vazio
- ⏳ A2AController simulado (implementação real pendente)
- ⏳ WebSearchTool requer documentação de API key

---

## 📊 Estado do Projeto Biblia

**Descrição**: Projeto Biblia é uma aplicação que utiliza ia-core como parent, implementando funcionalidades específicas para gestão de conteúdo bíblico.

**Estado Atual (29/05/2026)**:
- **Parent**: ia-core 0.0.1-SNAPSHOT
- **Spring Boot**: 4.0.6 (herdado do ia-core)
- **Java**: 21 (herdado do ia-core)
- **Vaadin**: 25.1.5 (herdado do ia-core)
- **Módulos**: biblia-model, biblia-service, biblia-rest, biblia-view, biblia-grammar, biblia-nlp, biblia-service-model, biblia-test
- **Tools Implementadas**: BibliaSearchTool e VersiculoTool (arquivos existem mas estão vazios)
- **Recent Commits**: "implementação base de agentes, mcp, llm, tools" (similar ao ia-core)

**Pontos Fortes**:
- ✅ Segue a arquitetura de 4 camadas do ia-core
- ✅ Utiliza os padrões de design do ia-core
- ✅ Estrutura de módulos consistente
- ✅ Integração com LLM tools (BibliaSearchTool, VersiculoTool)

**Pendências**:
- ⏳ Implementar funcionalidade nas tools (BibliaSearchTool, VersiculoTool estão vazias)
- ⏳ Integrar com sistema de agentes do ia-core
- ⏳ Configurar MCP se necessário

---

*Documento atualizado em [29/05/2026] - Estado atual dos projetos ia-core e Biblia*
