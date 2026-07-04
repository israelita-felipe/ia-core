# Caso de Teste: TokenizerService

## Descrição
Testa a classe TokenizerService que implementa tokenização usando Apache OpenNLP.

## Classe Testada
`com.ia.core.nlp.model.services.TokenizerService`

## Fluxo do Teste
1. Testar carregamento do modelo
2. Testar tokenização de texto
3. Testar cache do modelo

## Cenários

### Cenário 1: Tokenizar texto simples
- **Dado**: Um texto simples
- **Quando**: Chamar tokenizerService.tokenize(texto)
- **Então**: Deve retornar array de tokens
- **E**: Tokens devem corresponder às palavras do texto

### Cenário 2: Tokenizar texto com pontuação
- **Dado**: Um texto com pontuação
- **Quando**: Chamar tokenizerService.tokenize(texto)
- **Então**: Deve retornar array de tokens
- **E**: Pontuação deve ser tratada corretamente

### Cenário 3: Carregar modelo lazy
- **Dado**: TokenizerService não usado anteriormente
- **Quando**: Chamar tokenizerService.tokenize(texto)
- **Então**: Deve carregar modelo na primeira chamada
- **E**: Deve cachear modelo para chamadas subsequentes

### Cenário 4: Reutilizar modelo cacheado
- **Dado**: TokenizerService já usado
- **Quando**: Chamar tokenizerService.tokenize(texto) novamente
- **Então**: Deve usar modelo cacheado
- **E**: Não deve recarregar o modelo
