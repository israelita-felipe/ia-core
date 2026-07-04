# Casos de Teste - FilterRequestDTO (Model Layer)

## CDU Relacionado
CDU027-FiltragemDinamica

## Descrição
Casos de teste básicos para validar o padrão FilterRequestDTO no módulo ia-core-service-model.

## Cenários de Teste

### CT001 - Criação de Filtro Simples
**Descrição**: Validar criação de filtro com key, operator e value
**Entrada**: key="nome", operator=EQUAL, value="João"
**Resultado Esperado**: FilterRequestDTO criado com campos populados
**Tipo**: Unitário
**Prioridade**: Alta

### CT002 - Filtro com Negation
**Descrição**: Validar filtro com negate=true
**Entrada**: FilterRequestDTO com negate=true
**Resultado Esperado**: Filtro configurado para negação
**Tipo**: Unitário
**Prioridade**: Alta

### CT003 - Acesso a CAMPOS
**Descrição**: Validar acesso a constantes de nomes de campos
**Entrada**: FilterRequestDTO.CAMPOS.KEY
**Resultado Esperado**: String "key" é retornada
**Tipo**: Unitário
**Prioridade**: Alta

### CT004 - Filtro com FieldType
**Descrição**: Validar filtro com tipo de campo especificado
**Entrada**: FilterRequestDTO com fieldType="String"
**Resultado Esperado**: Tipo de campo configurado corretamente
**Tipo**: Unitário
**Prioridade**: Média

### CT005 - Filtro com Valor Nulo
**Descrição**: Validar comportamento com valor nulo
**Entrada**: FilterRequestDTO com value=null
**Resultado Esperado**: Filtro criado sem exceções
**Tipo**: Unitário
**Prioridade**: Média

### CT006 - Filtro com Operador IS_NULL
**Descrição**: Validar filtro sem valor para operador IS_NULL
**Entrada**: FilterRequestDTO com operator=IS_NULL, value=null
**Resultado Esperado**: Filtro criado corretamente
**Tipo**: Unitário
**Prioridade**: Alta

## Gaps de Cobertura
- Testes de integração com SearchRequestDTO
- Testes de validação de tipos de campo
- Testes de construção de Predicates

## Referências
- CDU027-FiltragemDinamica
- ADR-012: Testing Patterns
