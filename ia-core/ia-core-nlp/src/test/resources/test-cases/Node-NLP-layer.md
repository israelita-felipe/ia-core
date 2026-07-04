# Caso de Teste: Node

## Descrição
Testa a classe Node que representa um nó numérico para processamento de linguagem natural (NLP).

## Classe Testada
`com.ia.core.nlp.model.Node`

## Fluxo do Teste
1. Criar Node com valores G, A, T, C
2. Testar operação de adição
3. Testar operação de multiplicação escalar
4. Testar operação de multiplicação matricial
5. Testar negação
6. Testar normalização
7. Testar compactação

## Cenários

### Cenário 1: Criar Node com valores
- **Dado**: Valores G, A, T, C
- **Quando**: Criar Node.valueOf(g, a, t, c)
- **Então**: Deve criar Node com os valores fornecidos
- **E**: Getters devem retornar os valores corretos

### Cenário 2: Adicionar dois Nodes
- **Dado**: Dois Nodes com valores diferentes
- **Quando**: Chamar node1.add(node2)
- **Então**: Deve retornar novo Node com valores somados
- **E**: Valores originais não devem ser modificados

### Cenário 3: Multiplicar Node por escalar
- **Dado**: Um Node e um valor escalar
- **Quando**: Chamar node.multiply(valor)
- **Então**: Deve retornar novo Node com valores multiplicados
- **E**: Valores originais não devem ser modificados

### Cenário 4: Multiplicar dois Nodes (matricial)
- **Dado**: Dois Nodes
- **Quando**: Chamar node1.multiply(node2)
- **Então**: Deve retornar novo Node com resultado da multiplicação matricial
- **E**: Deve seguir fórmula de multiplicação 2x2

### Cenário 5: Negar Node
- **Dado**: Um Node
- **Quando**: Chamar node.negate()
- **Então**: Deve retornar novo Node com valores multiplicados por -1
- **E**: Flag negated deve ser true

### Cenário 6: Normalizar Node
- **Dado**: Um Node com valores
- **Quando**: Chamar node.normalize()
- **Então**: Deve retornar novo Node normalizado
- **E**: Valores devem ser divididos pela norma euclidiana

### Cenário 7: Compactar Node
- **Dado**: Um Node com valores G, A, T, C
- **Quando**: Chamar node.compact()
- **Então**: Deve transformar matriz 2x2 em representação 2D
- **E**: Deve usar CompactNode para compactação

### Cenário 8: Testar toString
- **Dado**: Um Node criado
- **Quando**: Chamar toString()
- **Então**: Deve retornar string formatada com valores G, A, T, C
