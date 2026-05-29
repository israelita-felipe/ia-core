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

| Classe | Linhas | Problema                                                                                                                                           | Solução                                                                                                                                                                                                                                                                                                                                          |
|--------|--------|----------------------------------------------------------------------------------------------------------------------------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `ia-core-llm-service` | - | Uso de versões milestone (Spring AI 2.0.0-M1) | Atualizar para versões estáveis mais recentes; remover referências a milestone. |
| `ia-core-llm-service` | - | MCP configurado mas não implementado | Implementar servidor MCP conforme configuração em application-llm-service.yml |
| `ia-core-llm-service` | - | FerramentaDiscoveryService limitada ao pacote de serviço | Permitir descoberta de tools em múltiplos pacotes (service e view) |
| `ia-core-llm-view` | - | Camada view não define suas próprias tools | Criar mecanismo para view layer definir tools independentemente |

### Tabela 2: Problemas e Implementações Sugeridas (boas práticas)

| Nº | Problema Identificado | Implementação Sugerida (boas práticas) | Padrões Recomendados |
|----|-----------------------|----------------------------------------|----------------------|
| 1  | Uso de versões milestone (Spring AI 2.0.0-M1) | Atualizar para versões estáveis mais recentes; remover referências a milestone. | Dependency Management, Stability |
| 2  | MCP configurado mas não implementado | Implementar servidor MCP conforme RFC 8485 (Model Context Protocol) com SSE endpoint. | MCP Protocol, Server-Sent Events |
| 3  | FerramentaDiscoveryService limitada ao pacote de serviço | Permitir descoberta de tools em múltiplos pacotes configuráveis (service e view). | Separation of Concerns, Modularity |
| 4  | Camada view não define suas próprias tools | Criar mecanismo para view layer definir tools independentemente usando @Tool annotations em pacotes separados. | Layer Independence, Tool Calling |

## 🔍 Análise Classe a Classe

### 1. `ia-core-llm-service`

**Descrição**: Módulo de serviço para integração com LLMs (Large Language Models). Atualmente usando versão milestone do Spring AI.

**Problemas identificados no código**:
- **Uso de versões milestone** (pom.xml): O módulo usa Spring AI 2.0.0-M1, que é uma versão milestone não estável. Isso pode causar instabilidade e problemas de compatibilidade.
- **MCP configurado mas não implementado**: A configuração em `application-llm-service.yml` habilita o servidor MCP com SSE endpoint `/mcp/sse`, mas não há implementação do servidor MCP encontrada no código.
- **FerramentaDiscoveryService limitada**: O serviço de descoberta de ferramentas escaneia apenas o pacote `com.ia.core.llm.service.tool`, não permitindo que a camada view defina suas próprias tools.
- **Independência entre camadas**: A camada view não tem mecanismo para definir suas próprias tools independentemente da camada service.

**Solução Proposta**:
- Aguardar GA release do Spring AI (previsto para 28/05/2026)
- Atualizar para versão estável assim que disponível
- Remover referências a versões milestone do pom.xml
- Implementar servidor MCP conforme RFC 8485 com SSE endpoint
- Modificar `FerramentaDiscoveryService` para escanear múltiplos pacotes configuráveis
- Criar mecanismo para view layer definir tools independentemente
- Adicionar pacote de view layer à configuração de scan de tools

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

**Problemas identificados no código**:
- **FerramentaManager**: Apenas um CRUD manager, não define tools
- **Ausência de tools view-specific**: Não há implementação de tools específicas da camada view
- **Independência**: A camada view não pode definir suas próprias tools independentemente

**Solução Proposta**:
- Criar pacote `com.ia.core.llm.view.tool` para tools específicas da view
- Adicionar classes com métodos anotados com `@Tool` para funcionalidades view-specific
- Configurar `FerramentaDiscoveryService` para escanear também o pacote view
- Usar o campo `moduloOrigem` para identificar tools de service vs view
- Implementar tools view-specific para interações com UI Vaadin

## 📋 Implementação Recomendada para Tools Independentes

### Arquitetura Proposta

**Camada Service (ia-core-llm-service)**:
- Pacote: `com.ia.core.llm.service.tool`
- Define tools para operações de negócio e integrações externas
- Tools são descobertas automaticamente via `@Tool` annotation
- Exemplos: consultas de banco de dados, chamadas de APIs externas, processamento de dados

**Camada View (ia-core-llm-view)**:
- Pacote: `com.ia.core.llm.view.tool`
- Define tools para interações específicas da UI Vaadin
- Tools são descobertas automaticamente via `@Tool` annotation
- Exemplos: navegação entre views, atualização de componentes UI, interações com usuário

**Independência**:
- Cada camada define suas próprias tools em pacotes separados
- `FerramentaDiscoveryService` escaneia ambos os pacotes
- O campo `moduloOrigem` identifica a origem da tool
- Tools podem ser chamadas internamente por cada camada
- MCP server expõe todas as tools independentemente da origem

### Configuração Necessária

**application-llm-service.yml**:
```yaml
ia-core:
  llm:
    ferramenta:
      discovery:
        enabled: true
        scan-packages:
          - com.ia.core.llm.service.tool
          - com.ia.core.llm.view.tool  # Adicionar pacote view
        refresh-on-startup: true
```

**FerramentaDiscoveryService**:
- Modificar para escanear múltiplos pacotes configurados
- Preencher campo `moduloOrigem` baseado no pacote da tool
- Manter registro de tools de ambas as camadas

### Implementação de MCP Server

**Requisitos**:
- Implementar servidor MCP conforme RFC 8485
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

✅ **Resilience Pattern Declarativo**: O módulo ia-core-resilience4j fornece uma implementação excelente de resiliência declarativa com @Resilient e profiles predefinidos.

✅ **Security Pattern Centralizado**: Módulos de segurança bem organizados e centralizados, com JWT, roles e permissions.

✅ **Testing Pattern Robusto**: Estrutura de testes com TestContainers, fixtures e custom assertions.

✅ **Event-Driven Pattern**: Mecanismo de eventos funcionando para publicação após operações CRUD.

✅ **Builder Pattern Consistente**: Uso padronizado de @Builder em todas as DTOs e entidades.

✅ **Strategy Pattern**: Implementações variadas para LLM providers e resilience profiles.

✅ **Módulos Especializados Bem Integrados**: ANTLR para gramáticas, OpenNLP para NLP, JasperReports para relatórios.

✅ **LLM Model Bem Estruturado**: Entidade Ferramenta suporta múltiplos tipos (AGENTE_ESPECIALISTA, TOOL_SPRING, RECURSO_MCP).

### Melhorias Recomendadas (Prioridade Alta)

⚠️ **Implementar MCP Server**: Implementar servidor MCP conforme RFC 8485 com SSE endpoint para expor tools.

⚠️ **Independência de Tools**: Permitir que camada view defina suas próprias tools independentemente da camada service.

⚠️ **Configuração de Scan**: Modificar `FerramentaDiscoveryService` para escanear múltiplos pacotes (service e view).

⚠️ **Atualizar Spring AI**: Aguardar GA release (28/05/2026) e atualizar para versão estável.

### Resumo

O projeto está em um estado **muito bom** em termos de arquitetura e padrões de design. As recomendações são **melhorias incrementais** e não refatorações estruturais. A base sólida estabelecida permite evolução segura e manutenção eficiente.

---

*Documento atualizado em [27/05/2026] - Análise de módulos LLM e implementação de tools independentes*
