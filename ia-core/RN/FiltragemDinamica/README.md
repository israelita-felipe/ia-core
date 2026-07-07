# Regras de Negócio - Módulo Filtragem Dinâmica

## Visão Geral
Este documento define as regras de negócio implementadas no módulo de Filtragem Dinâmica do ia-core-apps.

## Entidades

### FilterRequestDTO
Representa um filtro individual com campo, operador e valor.

### SearchRequestDTO
Representa uma requisição de busca complexa com múltiplos filtros, ordenação e paginação.

#### Regras Implementadas

##### FLD_001 - OperadorObrigatorioRule
- **Nome**: Operador Obrigatório
- **Descrição**: Todo filtro deve ter um operador de comparação especificado
- **Critérios**:
  - Campo operator é obrigatório em FilterRequestDTO
  - Operador deve ser um dos valores: EQUAL, NOT_EQUAL, LIKE, IN, GREATER_THAN, etc.
  - Filtro sem operador é rejeitado
- **Severidade**: ERRO
- **Referência CDU**: CDU027-FiltragemDinamica

##### FLD_002 - KeyObrigatoriaRule
- **Nome**: Key Obrigatória
- **Descrição**: Todo filtro deve ter uma key (nome do campo) definida
- **Critérios**:
  - Campo key é obrigatório em FilterRequestDTO
  - Key deve referenciar campo existente na entidade
  - Key não pode estar vazia
- **Severidade**: ERRO
- **Referência CDU**: CDU027-FiltragemDinamica

##### FLD_003 - ValorObrigatorioRule
- **Nome**: Valor Obrigatório
- **Descrição**: Filtros de comparação requerem valor (exceto IS_NULL/IS_NOT_NULL)
- **Critérios**:
  - Campos IS_NULL e IS_NOT_NULL não requerem valor
  - Demais operadores requerem valor não nulo
  - Validação ocorre ao aplicar filtro
- **Severidade**: ERRO
- **Referência CDU**: CDU027-FiltragemDinamica

##### FLD_004 - TipoCampoCorretoRule
- **Nome**: Tipo de Campo Correto
- **Descrição**: fieldType deve corresponder ao tipo do campo na entidade alvo
- **Critérios**:
  - fieldType é usado para conversão correta do valor
  - Conversão falha se tipo for incompatível
  - Validação previne erros em tempo de execução
- **Severidade**: ERRO
- **Referência CDU**: CDU027-FiltragemDinamica

##### FLD_005 - PaginaçãoPadraoRegrasRule
- **Nome**: Paginação Padrão Aplicada
- **Descrição**: Se page/size não especificados, usar valores padrão
- **Critérios**:
  - page padrão é 0 (primeira página)
  - size padrão é 20 registros por página
  - Valores são aplicados automaticamente quando ausentes
- **Severidade**: INFO
- **Referência CDU**: CDU027-FiltragemDinamica

##### FLD_006 - OrdenacaoPadraoRegrasRule
- **Nome**: Ordenação Padrão Aplicada
- **Descrição**: Se sort não especificado, usar ordenação padrão
- **Critérios**:
  - Ordenação padrão é por ID em ordem ascendente
  - Campo sort pode ser múltiplo
  - Direção padrão é ASC
- **Severidade**: INFO
- **Referência CDU**: CDU027-FiltragemDinamica

##### FLD_007 - ContextoPrioritarioRegrasRule
- **Nome**: Filtros de Contexto Prioritários
- **Descrição**: Filtros de contexto têm prioridade sobre filtros do usuário
- **Critérios**:
  - contextFilters são aplicados antes de filters
  - contextFilters não podem ser sobrescritos por usuário
  - Usado para isolamento de dados por tenant/perm
- **Severidade**: INFO
- **Referência CDU**: CDU027-FiltragemDinamica

## Validadores

- `FilterRequestValidator` - Valida regras de filtros individuais
- `SearchRequestValidator` - Valida regras de requisições de busca

## Padrão de Implementação

As regras de negócio seguem o padrão `BusinessRule<T>` do módulo ia-core-service:

```java
public class MinhaRegra implements BusinessRule<FilterRequestDTO> {
    @Override
    public String getCode() {
        return "FLD_001";
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
    public void validate(FilterRequestDTO entity, ValidationResult result) {
        // Lógica de validação
    }
}
```

## Referências

- ADR-040: DTO CAMPOS Pattern
- ADR-053: Usar CDU para Documentação de Casos de Uso
- Service Base: `com.ia.core.service.rules.BusinessRule`