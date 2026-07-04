# Caso de Teste: CompactNode

## Descrição
Testa a classe CompactNode que representa um nó compacto para processamento NLP.

## Classe Testada
`com.ia.core.nlp.model.CompactNode`

## Fluxo do Teste
1. Criar CompactNode com valores X e Y
2. Testar multiplicação escalar
3. Testar multiplicação de CompactNodes
4. Testar negação
5. Testar normalização

## Cenários

### Cenário 1: Criar CompactNode com valores
- **Dado**: Valores X e Y
- **Quando**: Criar CompactNode.valueOf(x, y)
- **Então**: Deve criar CompactNode com os valores fornecidos
- **E**: Getters devem retornar os valores corretos
- **E**: Flag negated deve ser false

### Cenário 2: Multiplicar CompactNode por escalar
- **Dado**: Um CompactNode e um valor escalar
- **Quando**: Chamar node.multiply(valor)
- **Então**: Deve retornar novo CompactNode com valores multiplicados
- **E**: Valores originais não devem ser modificados

### Cenário 3: Multiplicar dois CompactNodes
- **Dado**: Dois CompactNodes
- **Quando**: Chamar node1.multiply(node2)
- **Então**: Deve retornar novo CompactNode com valores multiplicados
- **E**: X deve ser x1 * x2
- **E**: Y deve ser y1 * y2

### Cenário 4: Negar CompactNode
- **Dado**: Um CompactNode
- **Quando**: Chamar node.negate()
- **Então**: Deve retornar novo CompactNode com valores multiplicados por -1
- **E**: Flag negated deve ser true

### Cenário 5: Normalizar CompactNode
- **Dado**: Um CompactNode com valores
- **Quando**: Chamar node.normalize()
- **Então**: Deve retornar novo CompactNode normalizado
- **E**: Valores devem ser divididos pela norma euclidiana

### Cenário 6: Testar toString
- **Dado**: Um CompactNode criado
- **Quando**: Chamar toString()
- **Então**: Deve retornar string formatada com valores X e Y
