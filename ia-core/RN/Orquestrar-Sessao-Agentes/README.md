# Regras de Negócio - Módulo Orquestrar Sessão de Agentes

## Visão Geral
Este documento define as regras de negócio implementadas no módulo de Orquestrar Sessão de Agentes do ia-core-apps.

## Referência
- **CDU**: CDU018-Orquestrar-Sessao-Agentes
- **Service**: ia-core-llm-service
- **Módulo**: ia-core-llm-model

## Entidades

### AgentSession
Representa uma sessão de orquestração multi-agente com contexto e histórico.

### PendingAction
Representa uma ação pendente de confirmação no contexto de uma sessão.

#### Regras Implementadas

##### SES_001 - MensagemUsuarioObrigatoriaRule
- **Nome**: Mensagem do Usuário Obrigatória
- **Descrição**: Garante que a mensagem do usuário não esteja vazia ou em branco
- **Critérios**:
  - userMessage é obrigatório para execução de sessão
  - Mensagem não pode conter apenas espaços em branco
  - Mensagem é validada antes do início da orquestração
- **Severidade**: ERRO
- **Referência CDU**: CDU018-Orquestrar-Sessao-Agentes

##### SES_002 - SessionIdPreservadoRule
- **Nome**: SessionId Preservado na Resposta
- **Descrição**: Garante que o sessionId seja preservado em todas as respostas
- **Critérios**:
  - sessionId é retornado em todas as respostas de orquestração
  - SessionId permite continuidade da sessão
  - Novo sessionId é gerado quando necessário
- **Severidade**: INFO
- **Referência CDU**: CDU018-Orquestrar-Sessao-Agentes

##### SES_003 - ConfirmacaoAcoesSensiveRule
- **Nome**: Confirmação para Ações Sensíveis
- **Descrição**: Ações sensíveis exigem confirmação explícita do usuário
- **Critérios**:
  - Ações marcadas como sensíveis ficam pendentes de confirmação
  - pendingConfirmation=true indica ação aguardando confirmação
  - Resposta final só é enviada após confirmação
- **Severidade**: AVISO
- **Referência CDU**: CDU018-Orquestrar-Sessao-Agentes

##### SES_004 - SeparacaoResponsabilidadesRule
- **Nome**: Separação de Responsabilidades
- **Descrição**: Camada de sessão não deve executar regras de negócio de domínio
- **Critérios**:
  - Orquestração de sessão é agnóstica ao domínio
  - Regras de negócio são executadas nas camadas de serviço
  - Fallback lida com falhas de serviço
- **Severidade**: INFO
- **Referência CDU**: CDU018-Orquestrar-Sessao-Agentes

##### SES_005 - FalhasRastreaveisRule
- **Nome**: Falhas Rastreáveis Sem Exposição de Dados
- **Descrição**: Falhas são registradas com contexto sem expor dados sensíveis
- **Critérios**:
  - Erros são logados com correlationId
  - Mensagens retornadas ao usuário são genéricas
  - Stack traces são preservadas apenas nos logs internos
- **Severidade**: INFO
- **Referência CDU**: CDU018-Orquestrar-Sessao-Agentes

## Validadores

- `AgentSessionValidator` - Orquestra regras de AgentSession

## Padrão de Implementação

As regras de negócio seguem o padrão `BusinessRule<T>` do módulo ia-core-service:

```java
public class MinhaRegra implements BusinessRule<AgentSessionDTO> {
    @Override
    public String getCode() {
        return "SES_001";
    }

    @Override
    public String getName() {
        return "Minha Regra";
    }

    @Override
    public String getDescription() {
        return "Descrição da regra";
    }

    @Override
    public void validate(AgentSessionDTO entity, ValidationResult result) {
        // Lógica de validação
    }
}
```

## Referências

- ADR-053: Usar CDU para Documentação de Casos de Uso
- ADR-011: Exception Handling Patterns
- ADR-051: RFC 9110 HTTP Semantics
- Service Base: `com.ia.core.service.rules.BusinessRule`