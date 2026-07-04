# Regras de Negócio - Módulo Conversação Chat

## Visão Geral
Este documento define as regras de negócio implementadas no módulo de Conversação Chat do ia-core-apps.

## Entidades

### ChatSessao
Representa uma sessão de conversação com um agente LLM.

#### Regras Implementadas

##### CHT_001 - SessaoAgenteValidoRule
- **Nome**: Sessão Agente Válido
- **Descrição**: Garante que a sessão esteja associada a um agente válido e ativo
- **Critérios**:
  - Agente deve existir no sistema
  - Agente deve estar com status ativo
  - Agente deve ter modelo LLM configurado
- **Severidade**: ERRO
- **Referência CDU**: CDU001-Conversacao-Chat

##### CHT_002 - SessaoIdentificadorUnicoRule
- **Nome**: Sessão Identificador Único
- **Descrição**: Garante que o identificador da sessão seja único
- **Critérios**:
  - sessionId deve ser único no sistema
  - Formato deve seguir padrão UUID ou similar
- **Severidade**: ERRO
- **Referência CDU**: CDU001-Conversacao-Chat

##### CHT_003 - LimiteSessoesSimultaneasRule
- **Nome**: Limite de Sessões Simultâneas
- **Descrição**: Limita o número de sessões simultâneas por usuário
- **Critérios**:
  - Máximo de 100 sessões simultâneas por usuário
  - Sessões encerradas liberam o slot
- **Severidade**: AVISO
- **Referência CDU**: CDU001-Conversacao-Chat

##### CHT_004 - TimeoutRespostaLLMRule
- **Nome**: Timeout de Resposta LLM
- **Descrição**: Define o limite de tempo para resposta do modelo LLM
- **Critérios**:
  - Resposta deve ser retornada em menos de 10 segundos
  - Timeout deve ser configurável
  - Erro deve ser tratado graceful
- **Severidade**: ERRO
- **Referência CDU**: CDU001-Conversacao-Chat

### MensagemChat
Representa uma mensagem na conversação.

#### Regras Implementadas

##### CHT_005 - TamanhoContextoLimitadoRule
- **Nome**: Tamanho de Contexto Limitado
- **Descrição**: Limita o tamanho do contexto para evitar timeout
- **Critérios**:
  - Contexto não deve exceder limite máximo configurado
  - Sistema deve alertar quando contexto está próximo do limite
  - Sistema pode oferecer resumo de contexto
- **Severidade**: AVISO
- **Referência CDU**: CDU001-Conversacao-Chat

##### CHT_006 - MensagemNaoVaziaRule
- **Nome**: Mensagem Não Vazia
- **Descrição**: Garante que mensagens enviadas não sejam vazias
- **Critérios**:
  - Mensagem não pode estar vazia ou em branco
  - Tamanho mínimo de 1 caractere
  - Mensagem deve ser validada antes do envio ao LLM
- **Severidade**: ERRO
- **Referência CDU**: CDU001-Conversacao-Chat

## Validadores

Cada entidade possui um validador específico que orquestra as regras:

- `ChatSessaoValidator` - Orquestra regras de ChatSessao
- `MensagemChatValidator` - Orquestra regras de MensagemChat

## Padrão de Implementação

As regras de negócio seguem o padrão `BusinessRule<T>` do módulo ia-core-service:

```java
public class MinhaRegra implements BusinessRule<MeuDTO> {
    @Override
    public String getCode() {
        return "CHT_001";
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
    public void validate(MeuDTO entity, ValidationResult result) {
        // Lógica de validação
    }
}
```

## Referências

- ADR-053: Usar CDU para Documentação de Casos de Uso
- ADR-011: Exception Handling Patterns
- Service Base: `com.ia.core.service.rules.BusinessRule`