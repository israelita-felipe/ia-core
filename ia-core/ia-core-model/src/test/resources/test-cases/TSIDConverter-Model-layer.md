# Caso de Teste: TSIDConverter

## Descrição
Testa a classe TSIDConverter que converte entre TSID e representações de banco de dados.

## Classe Testada
`com.ia.core.model.converter.TSIDConverter`

## Fluxo do Teste
1. Converter TSID para representação de banco
2. Converter representação de banco para TSID
3. Testar conversão bidirecional
4. Testar valores nulos

## Cenários

### Cenário 1: Converter TSID para banco
- **Dado**: Um TSID válido
- **Quando**: Chamar convertToDatabaseColumn()
- **Então**: Deve retornar representação de banco
- **E**: Deve ser reversível

### Cenário 2: Converter representação de banco para TSID
- **Dado**: Uma representação de banco válida
- **Quando**: Chamar convertToEntityAttribute()
- **Então**: Deve retornar TSID correspondente

### Cenário 3: Converter valor nulo para banco
- **Dado**: Um TSID nulo
- **Quando**: Chamar convertToDatabaseColumn()
- **Então**: Deve retornar null

### Cenário 4: Converter valor nulo do banco
- **Dado**: Uma representação de banco nula
- **Quando**: Chamar convertToEntityAttribute()
- **Então**: Deve retornar null

### Cenário 5: Testar conversão bidirecional
- **Dado**: Um TSID válido
- **Quando**: Converter para banco e depois de volta
- **Então**: Deve retornar TSID original
- **E**: Deve manter igualdade
