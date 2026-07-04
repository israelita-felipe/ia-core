# Casos de Teste - JsonUtil (Model Layer)

## CDU Relacionado
CDU028-UtilitariosJSON

## Descrição
Casos de teste básicos para validar o padrão JsonUtil no módulo ia-core-service-model.

## Cenários de Teste

### CT001 - Serialização de Objeto Simples
**Descrição**: Validar serialização de POJO para JSON
**Entrada**: Objeto com campos básicos
**Resultado Esperado**: String JSON válida gerada
**Tipo**: Unitário
**Prioridade**: Alta

### CT002 - Deserialização de JSON para Objeto
**Descrição**: Validar deserialização de JSON para POJO
**Entrada**: String JSON válida
**Resultado Esperado**: Objeto instanciado com campos populados
**Tipo**: Unitário
**Prioridade**: Alta

### CT003 - Serialização com LocalDateTime
**Descrição**: Validar serialização de tipo temporal
**Entrada**: Objeto com campo LocalDateTime
**Resultado Esperado**: Data serializada em formato ISO-8601
**Tipo**: Unitário
**Prioridade**: Alta

### CT004 - Deserialização com LocalDateTime
**Descrição**: Validar deserialização de tipo temporal
**Entrada**: JSON com data em formato ISO-8601
**Resultado Esperado**: LocalDateTime populado corretamente
**Tipo**: Unitário
**Prioridade**: Alta

### CT005 - Extração de Propriedade Simples
**Descrição**: Validar extração de propriedade via dot notation
**Entrada**: JSON com propriedade "nome"
**Resultado Esperado**: Valor da propriedade retornado
**Tipo**: Unitário
**Prioridade**: Alta

### CT006 - Extração de Propriedade Aninhada
**Descrição**: Validar extração de propriedade aninhada
**Entrada**: JSON com "usuario.endereco.rua"
**Resultado Esperado**: Valor da propriedade aninhada retornado
**Tipo**: Unitário
**Prioridade**: Alta

### CT007 - Serialização com Valor Nulo
**Descrição**: Validar tratamento de valores nulos
**Entrada**: Objeto com campos nulos
**Resultado Esperado**: JSON gerado com campos nulos incluídos
**Tipo**: Unitário
**Prioridade**: Média

### CT008 - Deserialização de JSON Inválido
**Descrição**: Validar tratamento de JSON malformado
**Entrada**: String JSON inválida
**Resultado Esperado**: JsonSyntaxException lançada
**Tipo**: Unitário
**Prioridade**: Alta

## Gaps de Cobertura
- Testes de serialização com tipos complexos
- Testes de performance com objetos grandes
- Testes de adaptadores customizados

## Referências
- CDU028-UtilitariosJSON
- ADR-012: Testing Patterns
