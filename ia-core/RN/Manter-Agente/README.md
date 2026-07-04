# Regras de Negócio - Módulo Manter Agente

## Visão Geral
Este documento define as regras de negócio implementadas no módulo de Manter Agente do ia-core-apps.

## Entidades

### Agente
Representa um agente LLM configurado no sistema.

#### Regras Implementadas

##### AGT_001 - IdentificadorAgenteValidoRule
- **Nome**: Identificador de Agente Válido
- **Descrição**: Garante que o identificador do agente seja único e tenha formato válido
- **Critérios**:
  - Identificador é obrigatório
  - Deve ter entre 2 e 100 caracteres
  - Deve ser único no sistema
- **Severidade**: ERRO
- **Referência CDU**: CDU002-Manter-Agente

##### AGT_002 - TituloAgenteValidoRule
- **Nome**: Título de Agente Válido
- **Descrição**: Garante que o título do agente seja válido
- **Critérios**:
  - Título é obrigatório
  - Deve ter entre 2 e 200 caracteres
- **Severidade**: ERRO
- **Referência CDU**: CDU002-Manter-Agente

##### AGT_003 - DescricaoAgenteValidoRule
- **Nome**: Descrição de Agente Válida
- **Descrição**: Garante que a descrição do agente não exceda o limite
- **Critérios**:
  - Descrição pode ter até 1000 caracteres
  - Campo opcional
- **Severidade**: AVISO
- **Referência CDU**: CDU002-Manter-Agente

##### AGT_004 - ModeloLLMValidoRule
- **Nome**: Modelo LLM Válido
- **Descrição**: Garante que o modelo LLM configurado seja válido
- **Critérios**:
  - Modelo deve ter até 100 caracteres
  - Modelo deve ser compatível com o provedor configurado
- **Severidade**: ERRO
- **Referência CDU**: CDU002-Manter-Agente

##### AGT_005 - TemperatureValidaRule
- **Nome**: Temperature Válida
- **Descrição**: Garante que o parâmetro temperature esteja no intervalo válido
- **Critérios**:
  - Temperature deve estar entre 0.0 e 2.0
  - Valor padrão é 0.7
- **Severidade**: ERRO
- **Referência CDU**: CDU002-Manter-Agente

##### AGT_006 - MaxTokensValidoRule
- **Nome**: MaxTokens Válido
- **Descrição**: Garante que o parâmetro maxTokens seja positivo
- **Critérios**:
  - MaxTokens deve ser positivo
  - Valor padrão é 2048
  - Deve respeitar limite do modelo
- **Severidade**: ERRO
- **Referência CDU**: CDU002-Manter-Agente

##### AGT_007 - AgentePossuiFerramentaOuSkillRule
- **Nome**: Agente Possui Ferramenta ou Skill
- **Descrição**: Garante que um agente tenha pelo menos uma ferramenta ou habilidade associada
- **Critérios**:
  - Agente deve ter pelo menos uma ferramenta OU habilidade
  - Validação ocorre no momento do cadastro
- **Severidade**: ERRO
- **Referência CDU**: CDU002-Manter-Agente

##### AGT_008 - AgenteEmUsoNaoPodeSerExcluidoRule
- **Nome**: Agente em Uso Não Pode Ser Excluído
- **Descrição**: Impede a exclusão de agentes que estão ativos em sessões
- **Critérios**:
  - Agente não pode ser excluído se possuir sessões de chat ativas
  - Agente não pode ser excluído se estiver sendo usado por outros processos
- **Severidade**: ERRO
- **Referência CDU**: CDU002-Manter-Agente

## Validadores

- `AgenteValidator` - Orquestra regras de Agente

## Padrão de Implementação

As regras de negócio seguem o padrão `BusinessRule<T>` do módulo ia-core-service:

```java
public class MinhaRegra implements BusinessRule<AgenteDTO> {
    @Override
    public String getCode() {
        return "AGT_001";
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
    public void validate(AgenteDTO entity, ValidationResult result) {
        // Lógica de validação
    }
}
```

## Referências

- ADR-053: Usar CDU para Documentação de Casos de Uso
- ADR-011: Exception Handling Patterns
- Service Base: `com.ia.core.service.rules.BusinessRule`