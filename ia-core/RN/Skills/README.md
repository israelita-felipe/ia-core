# Regras de Negócio - Módulo Skills

## Visão Geral
Este documento define as regras de negócio para o módulo Skills do ia-core-apps.

## Referência
- **CDU**: CDU016-Manter-Skill
- **Service**: ia-core-llm-service
- **Módulo**: ia-core-llm-model

## Entidades

### Skill
Representa uma habilidade especializada de um agente LLM.

#### Regras Implementadas

##### SKL_001 - SkillIdentificadorUnicoRule
- **Nome**: Skill Identificador Único
- **Descrição**: Garante que o identificador da skill seja único no sistema
- **Critérios**:
  - Identificador é obrigatório
  - Deve seguir o padrão: modulo.nome_skill
- **Severidade**: ERRO
- **Referência CDU**: RN001

##### SKL_002 - SkillEmUsoNaoPodeSerExcluidaRule
- **Nome**: Skill em Uso Não Pode Ser Excluída
- **Descrição**: Impede a exclusão de skills associadas a agentes
- **Critérios**:
  - Skill não pode ser excluída se associada a agentes
  - Sistema lista agentes dependentes
- **Severidade**: ERRO
- **Referência CDU**: RN004

## Skills Identificadas

| Módulo | Descrição |
|--------|-----------|
| ia-core-llm-service | Orquestrar operações de chat com LLM |
| ia-core-nlp-service | Processamento de linguagem natural |
| ia-core-communication-service | Envio de mensagens via múltiplos canais |
| ia-core-quartz-service | Agendamento de jobs com suporte a periodicidade RFC5545 |

## Padrão de Implementação

As regras de negócio seguem o padrão `BusinessRule<T>` do módulo ia-core-service:

```java
public class SkillIdentificadorUnicoRule implements BusinessRule<SkillDTO> {
    private static final String CODE = "SKL_001";
    
    @Override
    public String getCode() {
        return CODE;
    }

    @Override
    public String getName() {
        return "Skill Identificador Único";
    }

    @Override
    public String getDescription() {
        return "Garante que o identificador da skill seja único";
    }

    @Override
    public void validate(SkillDTO entity, ValidationResult result) {
        if (entity.getIdentificador() == null || entity.getIdentificador().trim().isEmpty()) {
            result.addError("identificador", "Identificador é obrigatório");
        }
    }
}
```

## Referências

- ADR-053: Usar CDU para Documentação de Casos de Uso
- ADR-011: Exception Handling Patterns
- Service Base: `com.ia.core.service.rules.BusinessRule`