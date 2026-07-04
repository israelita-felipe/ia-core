# Caso de Teste: TokenService

## Descrição
Testa a interface TokenService que define operações com tokens JWT na camada REST.

## Classe Testada
`com.ia.core.rest.security.token.TokenService`

## Fluxo do Teste
1. Testar método generateToken()
2. Testar método validateToken()
3. Testar método getUsernameFromToken()
4. Testar método getUserCodeFromToken()

## Cenários

### Cenário 1: Verificar método generateToken()
- **Dado**: Uma implementação de TokenService
- **Quando**: Chamar generateToken() com username e userCode
- **Então**: Deve retornar JwtToken
- **E**: O token deve conter username
- **E**: O token deve conter userCode

### Cenário 2: Verificar método validateToken()
- **Dado**: Uma implementação de TokenService
- **Quando**: Chamar validateToken() com JwtToken válido
- **Então**: Deve retornar TokenValidationResult
- **E**: O resultado deve indicar que o token é válido

### Cenário 3: Verificar método validateToken() com token inválido
- **Dado**: Uma implementação de TokenService
- **Quando**: Chamar validateToken() com JwtToken inválido
- **Então**: Deve retornar TokenValidationResult
- **E**: O resultado deve indicar que o token é inválido

### Cenário 4: Verificar método getUsernameFromToken()
- **Dado**: Uma implementação de TokenService
- **Quando**: Chamar getUsernameFromToken() com JwtToken
- **Então**: Deve retornar o username do token

### Cenário 5: Verificar método getUserCodeFromToken()
- **Dado**: Uma implementação de TokenService
- **Quando**: Chamar getUserCodeFromToken() com JwtToken
- **Então**: Deve retornar o userCode do token
