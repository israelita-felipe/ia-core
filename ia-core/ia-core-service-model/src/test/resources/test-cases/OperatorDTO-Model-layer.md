# Casos de Teste - OperatorDTO (Model Layer)

## CDU Relacionado
CDU027-FiltragemDinamica

## Descrição
Casos de teste básicos para validar o padrão OperatorDTO no módulo ia-core-service-model.

## Cenários de Teste

### CT001 - Operador EQUAL
**Descrição**: Validar operador de igualdade
**Entrada**: OperatorDTO.EQUAL
**Resultado Esperado**: Predicate construído corretamente
**Tipo**: Unitário
**Prioridade**: Alta

### CT002 - Operador LIKE
**Descrição**: Validar operador de correspondência parcial
**Entrada**: OperatorDTO.LIKE
**Resultado Esperado**: Predicate construído corretamente
**Tipo**: Unitário
**Prioridade**: Alta

### CT003 - Operador IN
**Descrição**: Validar operador de inclusão em lista
**Entrada**: OperatorDTO.IN
**Resultado Esperado**: Predicate construído corretamente
**Tipo**: Unitário
**Prioridade**: Alta

### CT004 - Operador GREATER_THAN
**Descrição**: Validar operador maior que
**Entrada**: OperatorDTO.GREATER_THAN
**Resultado Esperado**: Predicate construído corretamente
**Tipo**: Unitário
**Prioridade**: Alta

### CT005 - Operador IS_NULL
**Descrição**: Validar operador de nulidade
**Entrada**: OperatorDTO.IS_NULL
**Resultado Esperado**: Predicate construído corretamente
**Tipo**: Unitário
**Prioridade**: Alta

### CT006 - Build de Predicate com Reflexão
**Descrição**: Validar construção de Predicate usando reflexão
**Entrada**: OperatorDTO, valor, nome do campo, tipo do campo
**Resultado Esperado**: Predicate construído e aplicável
**Tipo**: Unitário
**Prioridade**: Alta

## Gaps de Cobertura
- Testes de integração com FilterRequestDTO
- Testes de performance de reflexão
- Testes de operadores customizados

## Referências
- CDU027-FiltragemDinamica
- ADR-012: Testing Patterns
