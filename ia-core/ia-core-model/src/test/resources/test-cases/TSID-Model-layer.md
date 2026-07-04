# Caso de Teste: TSID

## Descrição
Testa a classe TSID (Time-Sorted Unique Identifier) que representa identificadores únicos ordenados por tempo.

## Classe Testada
`com.ia.core.model.TSID`

## Fluxo do Teste
1. Criar TSID a partir de número
2. Criar TSID a partir de string
3. Criar TSID a partir de bytes
4. Converter TSID para diferentes formatos
5. Validar strings de TSID
6. Comparar TSIDs
7. Testar métodos de fábrica

## Cenários

### Cenário 1: Criar TSID a partir de número
- **Dado**: Um número long
- **Quando**: Criar TSID.from(numero)
- **Então**: Deve criar TSID com o número fornecido
- **E**: toLong() deve retornar o mesmo número

### Cenário 2: Criar TSID a partir de string válida
- **Dado**: Uma string de TSID válida (13 caracteres)
- **Quando**: Criar TSID.from(string)
- **Então**: Deve criar TSID com sucesso
- **E**: toString() deve retornar string equivalente

### Cenário 3: Criar TSID a partir de string inválida
- **Dado**: Uma string de TSID inválida
- **Quando**: Tentar criar TSID.from(string)
- **Então**: Deve lançar IllegalArgumentException

### Cenário 4: Criar TSID a partir de bytes
- **Dado**: Um array de 8 bytes
- **Quando**: Criar TSID.from(bytes)
- **Então**: Deve criar TSID com sucesso
- **E**: toBytes() deve retornar bytes equivalentes

### Cenário 5: Converter TSID para string uppercase
- **Dado**: Um TSID criado
- **Quando**: Chamar toString()
- **Então**: Deve retornar string em uppercase
- **E**: String deve ter 13 caracteres

### Cenário 6: Converter TSID para string lowercase
- **Dado**: Um TSID criado
- **Quando**: Chamar toLowerCase()
- **Então**: Deve retornar string em lowercase
- **E**: String deve ter 13 caracteres

### Cenário 7: Validar string de TSID
- **Dado**: Uma string de TSID válida
- **Quando**: Chamar TSID.isValid(string)
- **Então**: Deve retornar true

### Cenário 8: Validar string de TSID inválida
- **Dado**: Uma string de TSID inválida
- **Quando**: Chamar TSID.isValid(string)
- **Então**: Deve retornar false

### Cenário 9: Comparar dois TSIDs
- **Dado**: Dois TSIDs diferentes
- **Quando**: Chamar compareTo()
- **Então**: Deve comparar como unsigned long
- **E**: Deve retornar -1, 0 ou 1

### Cenário 10: Testar igualdade de TSIDs
- **Dado**: Dois TSIDs com mesmo número
- **Quando**: Chamar equals()
- **Então**: Deve retornar true
- **E**: hashCode() deve ser igual

### Cenário 11: Obter instant de criação
- **Dado**: Um TSID criado
- **Quando**: Chamar getInstant()
- **Então**: Deve retornar Instant correto
- **E**: getUnixMilliseconds() deve retornar valor correto

### Cenário 12: Criar TSID rápido
- **Dado**: Nenhum parâmetro
- **Quando**: Chamar TSID.fast()
- **Então**: Deve criar TSID com sucesso
- **E**: TSIDs consecutivos devem ser diferentes

### Cenário 13: Codificar TSID em base diferente
- **Dado**: Um TSID e uma base (ex: 16, 62)
- **Quando**: Chamar encode(base)
- **Então**: Deve retornar string codificada na base especificada

### Cenário 14: Decodificar string de TSID em base diferente
- **Dado**: Uma string codificada em base (ex: 16, 62)
- **Quando**: Chamar TSID.decode(string, base)
- **Então**: Deve retornar TSID correspondente

### Cenário 15: Formatar TSID com formato customizado
- **Dado**: Um TSID e um formato (ex: "K%S", "DOC-%X.PDF")
- **Quando**: Chamar format(format)
- **Então**: Deve retornar string formatada conforme especificado

### Cenário 16: Desformatar string de TSID
- **Dado**: Uma string formatada e o formato usado
- **Quando**: Chamar TSID.unformat(formatted, format)
- **Então**: Deve retornar TSID original
