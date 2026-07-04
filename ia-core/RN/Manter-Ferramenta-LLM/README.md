# Regras de Negócio - Módulo Manter Ferramenta LLM

## Visão Geral
Este documento define as regras de negócio implementadas no módulo de Manter Ferramenta LLM do ia-core-apps.

## Entidades

### FerramentaLLM
Representa uma ferramenta LLM específica que pode ser invocada por agentes LLM, com suporte a parâmetros e tipos de retorno definidos.

#### Regras Implementadas

##### LLM_001 - IdentificadorFerramentaLLMUnicoRule
- **Nome**: Identificador de Ferramenta LLM Único
- **Descrição**: Garante que o identificador da ferramenta LLM seja único no sistema
- **Critérios**:
  - Identificador é obrigatório
  - Deve seguir o padrão: llm.nome_ferramenta
  - Deve ser único no sistema
- **Severidade**: ERRO
- **Referência CDU**: CDU005-Manter-Ferramenta-LLM

##### LLM_002 - NomeFerramentaLLMObrigatorioRule
- **Nome**: Nome de Ferramenta LLM Obrigatório
- **Descrição**: Garante que o nome da ferramenta LLM seja informado e válido
- **Critérios**:
  - Nome é obrigatório
  - Nome deve ter entre 2 e 200 caracteres
  - Nome não pode estar vazio ou em branco
- **Severidade**: ERRO
- **Referência CDU**: CDU005-Manter-Ferramenta-LLM

##### LLM_003 - TipoRetornoValidoRule
- **Nome**: Tipo de Retorno Válido
- **Descrição**: Garante que o tipo de retorno da ferramenta LLM seja um tipo suportado
- **Critérios**:
  - Tipo de retorno deve ser: STRING, INTEGER, BOOLEAN, OBJECT, ARRAY ou VOID
  - Tipo é obrigatório
- **Severidade**: ERRO
- **Referência CDU**: CDU005-Manter-Ferramenta-LLM

##### LLM_004 - ParametrosDefinidosRule
- **Nome**: Parâmetros Devem Ter Tipo Definido
- **Descrição**: Garante que todos os parâmetros da ferramenta LLM tenham tipo especificado
- **Critérios**:
  - Cada parâmetro deve ter tipo: STRING, INTEGER, BOOLEAN, OBJECT ou ARRAY
  - Parâmetros obrigatórios devem ter marcação explícita
- **Severidade**: ERRO
- **Referência CDU**: CDU005-Manter-Ferramenta-LLM

##### LLM_005 - FerramentaLLMEmUsoNaoPodeSerExcluidaRule
- **Nome**: Ferramenta LLM em Uso Não Pode Ser Excluída
- **Descrição**: Impede a exclusão de ferramentas LLM associadas a agentes ou skills
- **Critérios**:
  - Ferramenta LLM não pode ser excluída se associada a agentes
  - Ferramenta LLM não pode ser excluída se associada a skills
  - Sistema lista dependências antes da exclusão
- **Severidade**: ERRO
- **Referência CDU**: CDU005-Manter-Ferramenta-LLM

##### LLM_006 - HistoricoAtivacoesMantidoRule
- **Nome**: Histórico de Ativações Mantido
- **Descrição**: Garante que o sistema mantenha histórico de ativações de ferramentas LLM
- **Critérios**:
  - Histórico deve registrar parâmetros usados
  - Histórico deve registrar resultado da execução
  - Histórico deve registrar timestamp da ativação
- **Severidade**: INFO
- **Referência CDU**: CDU005-Manter-Ferramenta-LLM

## Validadores

- `FerramentaLLMValidator` - Orquestra regras de FerramentaLLM

## Padrão de Implementação

As regras de negócio seguem o padrão `BusinessRule<T>` do módulo ia-core-service:

```java
public class MinhaRegra implements BusinessRule<FerramentaLLMDTO> {
    @Override
    public String getCode() {
        return "LLM_001";
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
    public void validate(FerramentaLLMDTO entity, ValidationResult result) {
        // Lógica de validação
    }
}
```

## Referências

- ADR-053: Usar CDU para Documentação de Casos de Uso
- ADR-011: Exception Handling Patterns
- Service Base: `com.ia.core.service.rules.BusinessRule`