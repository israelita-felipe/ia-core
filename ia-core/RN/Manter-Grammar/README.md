# Regras de Negócio - Módulo Manter Grammar

## Visão Geral
Este documento define as regras de negócio implementadas no módulo de Manter Grammar do ia-core-apps, para gerenciamento de gramáticas ANTLR.

## Entidades

### Grammar
Representa uma gramática ANTLR para parsing de texto e processamento de linguagem.

#### Regras Implementadas

##### GRM_001 - NomeGrammarUnicoRule
- **Nome**: Nome de Grammar Único
- **Descrição**: Garante que o nome da gramática seja único no sistema
- **Critérios**:
  - Nome é obrigatório
  - Nome não pode estar vazio ou em branco
  - Deve ser único no sistema
- **Severidade**: ERRO
- **Referência CDU**: CDU007-Manter-Grammar

##### GRM_002 - SintaxeANTLRValidaRule
- **Nome**: Sintaxe ANTLR Válida
- **Descrição**: Valida que a gramática ANTLR tenha sintaxe válida antes do cadastro
- **Critérios**:
  - Gramática deve ter sintaxe ANTLR válida
  - Regras devem seguir padrão ANTLR 4
  - Erros de sintaxe são reportados com linha específica
- **Severidade**: ERRO
- **Referência CDU**: CDU007-Manter-Grammar

##### GRM_003 - CompilacaoGeradaRule
- **Nome**: Compilação Gera Parser Automaticamente
- **Descrição**: Garante que a compilação da gramática gere parser automaticamente
- **Critérios**:
  - Sistema compila gramática após validação
  - Parser Java é gerado automaticamente
  - Arquivos gerados são armazenados no diretório correto
- **Severidade**: ERRO
- **Referência CDU**: CDU007-Manter-Grammar

##### GRM_004 - GrammarEmUsoNaoPodeSerExcluidaRule
- **Nome**: Grammar em Uso Não Pode Ser Excluída
- **Descrição**: Impede a exclusão de gramáticas associadas a processamentos ativos
- **Critérios**:
  - Gramática não pode ser excluída se estiver associada a NLP
  - Gramática não pode ser excluída se estiver em uso por agentes
  - Sistema lista dependências antes da exclusão
- **Severidade**: ERRO
- **Referência CDU**: CDU007-Manter-Grammar

##### GRM_005 - GrammarAmbíguaDetectadaRule
- **Nome**: Grammar Ambígua Detectada
- **Descrição**: Detecta e alerta sobre ambiguidades na gramática ANTLR
- **Critérios**:
  - Sistema detecta regras ambíguas durante validação
  - Alertas são exibidos para o desenvolvedor
  - Sugestões de correção são fornecidas
- **Severidade**: AVISO
- **Referência CDU**: CDU007-Manter-Grammar

## Validadores

- `GrammarValidator` - Orquestra regras de Grammar

## Padrão de Implementação

As regras de negócio seguem o padrão `BusinessRule<T>` do módulo ia-core-service:

```java
public class MinhaRegra implements BusinessRule<GrammarDTO> {
    @Override
    public String getCode() {
        return "GRM_001";
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
    public void validate(GrammarDTO entity, ValidationResult result) {
        // Lógica de validação
    }
}
```

## Referências

- ADR-053: Usar CDU para Documentação de Casos de Uso
- ADR-011: Exception Handling Patterns
- Service Base: `com.ia.core.service.rules.BusinessRule`