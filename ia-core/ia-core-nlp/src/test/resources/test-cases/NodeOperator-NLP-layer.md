# Caso de Teste: NodeOperator

## Descrição
Testa a classe NodeOperator que fornece operações para manipulação de nós NLP.

## Classe Testada
`com.ia.core.nlp.model.NodeOperator`

## Fluxo do Teste
1. Testar cálculo de cosseno de similaridade
2. Testar operação recursiva em array de nós
3. Testar constante IDENTITY

## Cenários

### Cenário 1: Calcular cosseno de similaridade entre dois nós
- **Dado**: Dois Nodes diferentes
- **Quando**: Chamar NodeOperator.coss(node1, node2)
- **Então**: Deve retornar valor do cosseno de similaridade
- **E**: Valor deve estar entre -1 e 1

### Cenário 2: Calcular cosseno de nós idênticos
- **Dado**: Dois Nodes iguais
- **Quando**: Chamar NodeOperator.coss(node1, node2)
- **Então**: Deve retornar valor próximo de 1

### Cenário 3: Operar array com um nó
- **Dado**: Um array com um Node
- **Quando**: Chamar NodeOperator.operate(array)
- **Então**: Deve retornar o nó normalizado

### Cenário 4: Operar array com dois nós
- **Dado**: Um array com dois Nodes
- **Quando**: Chamar NodeOperator.operate(array)
- **Então**: Deve retornar o primeiro nó normalizado

### Cenário 5: Operar array com múltiplos nós
- **Dado**: Um array com múltiplos Nodes
- **Quando**: Chamar NodeOperator.operate(array)
- **Então**: Deve aplicar operação recursiva
- **E**: Deve retornar nó resultante normalizado

### Cenário 6: Verificar constante IDENTITY
- **Dado**: A constante IDENTITY
- **Quando**: Verificar valores
- **Então**: Deve ser matriz identidade 2x2
- **E**: G deve ser 1, A deve ser 0, T deve ser 0, C deve ser 1

### Cenário 7: Tentar instanciar NodeOperator
- **Dado**: Tentar criar instância de NodeOperator
- **Quando**: Chamar construtor
- **Então**: Deve lançar UnsupportedOperationException
