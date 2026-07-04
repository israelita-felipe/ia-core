# Caso de Teste: SortRequest

## Descrição
Testa a classe SortRequest que representa critérios de ordenação para consultas.

## Classe Testada
`com.ia.core.model.filter.SortRequest`

## Fluxo do Teste
1. Criar SortRequest com campo e direção
2. Testar getters e setters
3. Testar validação de campos
4. Testar conversão para string

## Cenários

### Cenário 1: Criar SortRequest com campo e direção
- **Dado**: Um campo "nome" e direção ASC
- **Quando**: Criar SortRequest
- **Então**: Deve armazenar o campo
- **E**: Deve armazenar a direção

### Cenário 2: Criar SortRequest com direção descendente
- **Dado**: Um campo "dataCriacao" e direção DESC
- **Quando**: Criar SortRequest
- **Então**: Deve armazenar o campo
- **E**: Deve armazenar a direção DESC

### Cenário 3: Testar getter de campo
- **Dado**: Um SortRequest criado com campo "email"
- **Quando**: Chamar getField()
- **Então**: Deve retornar "email"

### Cenário 4: Testar getter de direção
- **Dado**: Um SortRequest criado com direção ASC
- **Quando**: Chamar getDirection()
- **Então**: Deve retornar ASC

### Cenário 5: Testar setter de campo
- **Dado**: Um SortRequest criado
- **Quando**: Chamar setField("novoCampo")
- **Então**: Deve atualizar o campo

### Cenário 6: Testar setter de direção
- **Dado**: Um SortRequest criado
- **Quando**: Chamar setDirection(DESC)
- **Então**: Deve atualizar a direção
