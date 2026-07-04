# Caso de Teste: RestPasswordEncoder

## Descrição
Testa a classe RestPasswordEncoder que implementa codificação de senhas usando BCrypt.

## Classe Testada
`com.ia.core.rest.security.RestPasswordEncoder`

## Fluxo do Teste
1. Testar herança de BCryptPasswordEncoder
2. Testar implementação de UserPasswordEncoder
3. Testar codificação de senha
4. Testar verificação de senha

## Cenários

### Cenário 1: Verificar herança de BCryptPasswordEncoder
- **Dado**: Uma instância de RestPasswordEncoder
- **Quando**: Verificar a classe pai
- **Então**: Deve herdar de BCryptPasswordEncoder
- **E**: Deve ter funcionalidades de BCrypt

### Cenário 2: Verificar implementação de UserPasswordEncoder
- **Dado**: Uma instância de RestPasswordEncoder
- **Quando**: Verificar as interfaces implementadas
- **Então**: Deve implementar UserPasswordEncoder
- **E**: Deve ter métodos da interface

### Cenário 3: Codificar senha
- **Dado**: Uma senha em texto plano "password123"
- **Quando**: Chamar encode()
- **Então**: Deve retornar hash BCrypt
- **E**: O hash deve ser diferente da senha original
- **E**: O hash deve começar com "$2a$" ou "$2b$"

### Cenário 4: Verificar senha correta
- **Dado**: Uma senha codificada
- **Quando**: Chamar matches() com a senha correta
- **Então**: Deve retornar true

### Cenário 5: Verificar senha incorreta
- **Dado**: Uma senha codificada
- **Quando**: Chamar matches() com senha incorreta
- **Então**: Deve retornar false
