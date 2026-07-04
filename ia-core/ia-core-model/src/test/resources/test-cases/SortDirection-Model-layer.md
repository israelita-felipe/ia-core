# Caso de Teste: SortDirection

## Descrição
Testa o enum SortDirection que define direções de ordenação para consultas.

## Enum Testado
`com.ia.core.model.filter.SortDirection`

## Fluxo do Teste
1. Testar valores do enum
2. Testar conversão para string
3. Testar parsing de string
4. Testar comportamento de cada direção

## Cenários

### Cenário 1: Verificar direções disponíveis
- **Dado**: O enum SortDirection
- **Quando**: Listar todos os valores
- **Então**: Deve incluir ASC e DESC

### Cenário 2: Testar direção ascendente (ASC)
- **Dado**: Direção ASC
- **Quando**: Usar em ordenação
- **Então**: Deve representar ordenação ascendente
- **E**: Deve ter representação de string correta

### Cenário 3: Testar direção descendente (DESC)
- **Dado**: Direção DESC
- **Quando**: Usar em ordenação
- **Então**: Deve representar ordenação descendente
- **E**: Deve ter representação de string correta

### Cenário 4: Testar conversão de string para enum
- **Dado**: Uma string de direção válida
- **Quando**: Converter para SortDirection
- **Então**: Deve retornar enum correspondente

### Cenário 5: Testar conversão de enum para string
- **Dado**: Uma direção de enum
- **Quando**: Converter para string
- **Então**: Deve retornar string correspondente
