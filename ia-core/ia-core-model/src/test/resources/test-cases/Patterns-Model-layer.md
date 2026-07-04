# Caso de Teste: Patterns

## Descrição
Testa a classe Patterns que contém padrões de regex para validações comuns.

## Classe Testada
`com.ia.core.model.util.Patterns`

## Fluxo do Teste
1. Testar padrão de email
2. Testar padrão de CPF
3. Testar padrão de CNPJ
4. Testar padrão de telefone
5. Testar padrão de CEP

## Cenários

### Cenário 1: Validar email válido
- **Dado**: Um email válido "usuario@exemplo.com"
- **Quando**: Testar com padrão de email
- **Então**: Deve corresponder ao padrão

### Cenário 2: Validar email inválido
- **Dado**: Um email inválido "usuario@exemplo"
- **Quando**: Testar com padrão de email
- **Então**: Não deve corresponder ao padrão

### Cenário 3: Validar CPF válido
- **Dado**: Um CPF válido formatado
- **Quando**: Testar com padrão de CPF
- **Então**: Deve corresponder ao padrão

### Cenário 4: Validar CPF inválido
- **Dado**: Um CPF inválido
- **Quando**: Testar com padrão de CPF
- **Então**: Não deve corresponder ao padrão

### Cenário 5: Validar CNPJ válido
- **Dado**: Um CNPJ válido formatado
- **Quando**: Testar com padrão de CNPJ
- **Então**: Deve corresponder ao padrão

### Cenário 6: Validar telefone válido
- **Dado**: Um telefone válido "(11) 99999-9999"
- **Quando**: Testar com padrão de telefone
- **Então**: Deve corresponder ao padrão

### Cenário 7: Validar CEP válido
- **Dado**: Um CEP válido "01234-567"
- **Quando**: Testar com padrão de CEP
- **Então**: Deve corresponder ao padrão
