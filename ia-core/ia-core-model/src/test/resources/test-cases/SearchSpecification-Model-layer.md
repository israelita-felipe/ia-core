# Caso de Teste: SearchSpecification

## Descrição
Testa a classe SearchSpecification que constrói especificações JPA para consultas dinâmicas.

## Classe Testada
`com.ia.core.model.specification.SearchSpecification`

## Fluxo do Teste
1. Criar SearchSpecification com filtros
2. Testar construção de especificações
3. Testar operadores de filtro
4. Testar paginação e ordenação

## Cenários

### Cenário 1: Criar SearchSpecification com filtros simples
- **Dado**: Um filtro com campo, operador e valor
- **Quando**: Criar SearchSpecification
- **Então**: Deve construir especificação JPA
- **E**: Deve aplicar filtro corretamente

### Cenário 2: Criar SearchSpecification com múltiplos filtros
- **Dado**: Vários filtros com diferentes operadores
- **Quando**: Criar SearchSpecification
- **Então**: Deve construir especificação com todos os filtros
- **E**: Deve aplicar operador AND entre filtros

### Cenário 3: Testar filtro de igualdade
- **Dado**: Um filtro com operador EQ
- **Quando**: Construir especificação
- **Então**: Deve gerar predicado de igualdade

### Cenário 4: Testar filtro de LIKE
- **Dado**: Um filtro com operador LIKE
- **Quando**: Construir especificação
- **Então**: Deve gerar predicado de correspondência de padrão

### Cenário 5: Testar filtro de nulidade
- **Dado**: Um filtro com operador IS_NULL
- **Quando**: Construir especificação
- **Então**: Deve gerar predicado de nulidade

### Cenário 6: Testar paginação
- **Dado**: Parâmetros de página e tamanho
- **Quando**: Criar SearchSpecification com paginação
- **Então**: Deve configurar paginação corretamente

### Cenário 7: Testar ordenação
- **Dado**: Critérios de ordenação
- **Quando**: Criar SearchSpecification com ordenação
- **Então**: Deve configurar ordenação corretamente
