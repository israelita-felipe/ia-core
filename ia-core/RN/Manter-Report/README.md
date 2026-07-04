# Regras de Negócio - Módulo Manter Report

## Visão Geral
Este documento define as regras de negócio implementadas no módulo de Manter Report do ia-core-apps.

## Entidades

### Relatorio
Representa um relatório configurado no sistema.

#### Regras Implementadas

##### RPT_001 - NomeRelatorioUnicoRule
- **Nome**: Nome de Relatório Único
- **Descrição**: Garante que o nome do relatório seja único no sistema
- **Critérios**:
  - Nome é obrigatório
  - Nome não pode ser duplicado
  - Comparação case-insensitive
- **Severidade**: ERRO
- **Referência CDU**: CDU013-Manter-Report

##### RPT_002 - TemplateObrigatorioRule
- **Nome**: Template Obrigatório
- **Descrição**: Garante que o template JasperReports (.jrxml) seja informado e válido
- **Critérios**:
  - Template é obrigatório para cadastro
  - Arquivo deve ter extensão .jrxml
  - Template deve compilar sem erros
- **Severidade**: ERRO
- **Referência CDU**: CDU013-Manter-Report

##### RPT_003 - QuerySQLValidaRule
- **Nome**: Query SQL Válida
- **Descrição**: Garante que a query SQL definida no template seja válida
- **Critérios**:
  - Query deve ter sintaxe SQL válida
  - Query deve referenciar tabelas existentes
  - Parâmetros da query devem ser declarados
- **Severidade**: ERRO
- **Referência CDU**: CDU013-Manter-Report

##### RPT_004 - ParametroObrigatorioPreenchidoRule
- **Nome**: Parâmetro Obrigatório Preenchido
- **Descrição**: Garante que parâmetros marcados como obrigatórios sejam informados na execução
- **Critérios**:
  - Parâmetros obrigatórios devem ser informados
  - Sistema valida antes da execução
  - Valores devem estar no tipo correto
- **Severidade**: ERRO
- **Referência CDU**: CDU013-Manter-Report

##### RPT_005 - FormatoRelatorioValidoRule
- **Nome**: Formato de Relatório Válido
- **Descrição**: Garante que o formato de saída seja suportado
- **Critérios**:
  - Formato deve ser: PDF, EXCEL ou HTML
  - Formato padrão pode ser definido no cadastro
- **Severidade**: ERRO
- **Referência CDU**: CDU013-Manter-Report

##### RPT_006 - HistoricoExecucoesMantidoRule
- **Nome**: Histórico de Execuções Mantido
- **Descrição**: Garante que o histórico de execuções seja mantido para auditoria
- **Critérios**:
  - Cada execução é registrada no histórico
  - Status da execução é registrado (SUCESSO, ERRO)
  - Arquivo gerado é armazenado
- **Severidade**: INFO
- **Referência CDU**: CDU013-Manter-Report

##### RPT_007 - AgendamentoValidoRule
- **Nome**: Agendamento Válido
- **Descrição**: Garante que o agendamento do relatório seja configurado corretamente
- **Critérios**:
  - Periodicidade deve ser válida
  - Destinatários devem ter emails válidos
  - Quartz Scheduler deve estar ativo
- **Severidade**: ERRO
- **Referência CDU**: CDU013-Manter-Report

## Validadores

- `RelatorioValidator` - Orquestra regras de Relatorio

## Padrão de Implementação

As regras de negócio seguem o padrão `BusinessRule<T>` do módulo ia-core-service:

```java
public class MinhaRegra implements BusinessRule<RelatorioDTO> {
    @Override
    public String getCode() {
        return "RPT_001";
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
    public void validate(RelatorioDTO entity, ValidationResult result) {
        // Lógica de validação
    }
}
```

## Referências

- ADR-053: Usar CDU para Documentação de Casos de Uso
- ADR-011: Exception Handling Patterns
- Service Base: `com.ia.core.service.rules.BusinessRule`