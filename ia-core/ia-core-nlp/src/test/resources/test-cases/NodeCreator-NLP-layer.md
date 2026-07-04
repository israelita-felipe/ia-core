# Caso de Teste: NodeCreator

## Descrição
Testa a classe NodeCreator que cria nós NLP a partir de texto e caracteres.

## Classe Testada
`com.ia.core.nlp.model.NodeCreator`

## Fluxo do Teste
1. Testar conversão de caractere para nós
2. Testar conversão de texto para nós
3. Testar conversão de palavra para nós
4. Testar validação de entrada

## Cenários

### Cenário 1: Converter caractere para nós
- **Dado**: Um caractere
- **Quando**: Chamar NodeCreator.fromChar(caractere)
- **Então**: Deve retornar array de 4 Nodes
- **E**: Cada Node deve representar um par de bits da representação binária

### Cenário 2: Converter texto para nós
- **Dado**: Um texto com palavras separadas por espaço
- **Quando**: Chamar NodeCreator.fromText(texto)
- **Então**: Deve retornar array de Nodes
- **E**: Cada palavra deve ser processada individualmente

### Cenário 3: Converter array de strings para nós
- **Dado**: Um array de strings
- **Quando**: Chamar NodeCreator.fromText(array)
- **Então**: Deve retornar array de Nodes
- **E**: Cada string deve ser processada individualmente

### Cenário 4: Converter palavra para nós
- **Dado**: Uma palavra sem espaços
- **Quando**: Chamar NodeCreator.fromWord(palavra)
- **Então**: Deve retornar array de Nodes
- **E**: Cada caractere deve ser convertido para Node

### Cenário 5: Lançar exceção para palavra com espaços
- **Dado**: Uma palavra com espaços
- **Quando**: Chamar NodeCreator.fromWord(palavra)
- **Então**: Deve lançar IllegalArgumentException
- **E**: Mensagem deve indicar que palavra não pode conter espaços

### Cenário 6: Tentar instanciar NodeCreator
- **Dado**: Tentar criar instância de NodeCreator
- **Quando**: Chamar construtor
- **Então**: Deve lançar UnsupportedOperationException
