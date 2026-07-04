# Caso de Teste: Operator

## Descrição
Testa o enum Operator que define operadores de filtro para consultas.

## Enum Testado
`com.ia.core.model.filter.Operator`

## Fluxo do Teste
1. Testar valores do enum
2. Testar conversão para string
3. Testar parsing de string
4. Testar comportamento de cada operador

## Cenários

### Cenário 1: Verificar todos os operadores disponíveis
- **Dado**: O enum Operator
- **Quando**: Listar todos os valores
- **Então**: Deve incluir operadores comuns (EQ, NE, GT, LT, GE, LE, LIKE, IN, IS_NULL, IS_NOT_NULL)

### Cenário 2: Testar operador de igualdade (EQ)
- **Dado**: Operador EQ
- **Quando**: Usar em comparação
- **Então**: Deve representar igualdade
- **E**: Deve ter representação de string correta

### Cenário 3: Testar operador de desigualdade (NE)
- **Dado**: Operador NE
- **Quando**: Usar em comparação
- **Então**: Deve representar desigualdade
- **E**: Deve ter representação de string correta

### Cenário 4: Testar operadores de comparação numérica
- **Dado**: Operadores GT, LT, GE, LE
- **Quando**: Usar em comparações numéricas
- **Então**: Deve representar maior, menor, maior ou igual, menor ou igual
- **E**: Devem ter representações de string corretas

### Cenário 5: Testar operador LIKE
- **Dado**: Operador LIKE
- **Quando**: Usar em comparação de strings
- **Então**: Deve representar correspondência de padrão
- **E**: Deve ter representação de string correta

### Cenário 6: Testar operador IN
- **Dado**: Operador IN
- **Quando**: Usar em comparação com lista
- **Então**: Deve representar inclusão em lista
- **E**: Deve ter representação de string correta

### Cenário 7: Testar operadores de nulidade
- **Dado**: Operadores IS_NULL e IS_NOT_NULL
- **Quando**: Usar em verificação de nulidade
- **Então**: Deve representar verificação de nulidade
- **E**: Devem ter representações de string corretas
