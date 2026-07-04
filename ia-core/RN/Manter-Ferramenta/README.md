# Regras de Negócio - Módulo Manter Ferramenta

## Visão Geral
Este documento define as regras de negócio implementadas no módulo de Manter Ferramenta do ia-core-apps.

## Entidades

### Ferramenta
Representa uma ferramenta ou função que pode ser invocada por agentes LLM.

#### Regras Implementadas

##### FER_001 - IdentificadorFerramentaUnicoRule
- **Nome**: Identificador de Ferramenta Único
- **Descrição**: Garante que o identificador da ferramenta seja único no sistema
- **Critérios**:
  - Identificador é obrigatório
  - Deve ser único no sistema
  - Sistema verifica duplicidade antes do cadastro
- **Severidade**: ERRO
- **Referência CDU**: CDU004-Manter-Ferramenta

##### FER_002 - NomeFerramentaObrigatorioRule
- **Nome**: Nome de Ferramenta Obrigatório
- **Descrição**: Garante que o nome da ferramenta seja informado
- **Critérios**:
  - Nome é obrigatório
  - Nome não pode estar vazio ou em branco
- **Severidade**: ERRO
- **Referência CDU**: CDU004-Manter-Ferramenta

##### FER_003 - TipoFerramentaValidoRule
- **Nome**: Tipo de Ferramenta Válido
- **Descrição**: Garante que o tipo da ferramenta seja um dos valores permitidos
- **Critérios**:
  - Tipo deve ser: BUILTIN, CUSTOM ou MCP
  - Tipo é obrigatório
- **Severidade**: ERRO
- **Referência CDU**: CDU004-Manter-Ferramenta

##### FER_004 - FerramentaBuiltinNaoPodeSerExcluidaRule
- **Nome**: Ferramenta BUILTIN Não Pode Ser Excluída
- **Descrição**: Impede a exclusão de ferramentas do tipo BUILTIN
- **Critérios**:
  - Ferramentas do tipo BUILTIN são protegidas do sistema
  - Não podem ser excluídas manualmente
- **Severidade**: ERRO
- **Referência CDU**: CDU004-Manter-Ferramenta

##### FER_005 - FerramentaEmUsoNaoPodeSerExcluidaRule
- **Nome**: Ferramenta em Uso Não Pode Ser Excluída
- **Descrição**: Impede a exclusão de ferramentas associadas a agentes ou skills
- **Critérios**:
  - Ferramenta não pode ser excluída se associada a agentes
  - Ferramenta não pode ser excluída se associada a skills
  - Sistema lista dependências antes da exclusão
- **Severidade**: ERRO
- **Referência CDU**: CDU004-Manter-Ferramenta

##### FER_006 - DescobertaAutomaticaValidaRule
- **Nome**: Descoberta Automática Válida
- **Descrição**: Valida que a descoberta automática encontra classes anotadas com @Tool
- **Critérios**:
  - Sistema busca classes anotadas com @Tool
  - Classes devem estar no classpath configurado
  - Resultados são listados para seleção
- **Severidade**: INFO
- **Referência CDU**: CDU004-Manter-Ferramenta

##### FER_007 - FormatoIdentificadorValidoRule
- **Nome**: Formato de Identificador Válido
- **Descrição**: Garante que o identificador siga o padrão esperado
- **Critérios**:
  - Identificador deve seguir o padrão: modulo.nome_ferramenta
  - Deve conter apenas caracteres alfanuméricos, pontos e sublinhados
- **Severidade**: ERRO
- **Referência CDU**: CDU004-Manter-Ferramenta

## Validadores

- `FerramentaValidator` - Orquestra regras de Ferramenta

## Padrão de Implementação

As regras de negócio seguem o padrão `BusinessRule<T>` do módulo ia-core-service:

```java
public class MinhaRegra implements BusinessRule<FerramentaDTO> {
    @Override
    public String getCode() {
        return "FER_001";
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
    public void validate(FerramentaDTO entity, ValidationResult result) {
        // Lógica de validação
    }
}
```

## Referências

- ADR-053: Usar CDU para Documentação de Casos de Uso
- ADR-011: Exception Handling Patterns
- Service Base: `com.ia.core.service.rules.BusinessRule`