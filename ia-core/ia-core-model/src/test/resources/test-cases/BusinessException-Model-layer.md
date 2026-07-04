# Caso de Teste: BusinessException

## Descrição
Testa a classe BusinessException que representa violações de regras de negócio.

## Classe Testada
`com.ia.core.model.exception.BusinessException`

## Fluxo do Teste
1. Criar exceção com mensagem
2. Criar exceção com código e mensagem
3. Criar exceção com código, mensagem e causa
4. Lançar exceção e verificar propriedades

## Cenários

### Cenário 1: Criar exceção com mensagem
- **Dado**: Uma mensagem de erro
- **Quando**: Criar BusinessException com a mensagem
- **Então**: Deve usar código de erro padrão "BUSINESS_RULE_VIOLATION"
- **E**: Deve armazenar a mensagem fornecida

### Cenário 2: Criar exceção com código e mensagem personalizados
- **Dado**: Um código de erro e uma mensagem
- **Quando**: Criar BusinessException com código e mensagem
- **Então**: Deve usar o código de erro fornecido
- **E**: Deve armazenar a mensagem fornecida

### Cenário 3: Criar exceção com código, mensagem e causa
- **Dado**: Um código de erro, uma mensagem e uma causa
- **Quando**: Criar BusinessException com código, mensagem e causa
- **Então**: Deve usar o código de erro fornecido
- **E**: Deve armazenar a mensagem fornecida
- **E**: Deve armazenar a causa original

### Cenário 4: Lançar exceção e verificar propriedades
- **Dado**: Uma mensagem de erro
- **Quando**: Lançar BusinessException
- **Então**: Deve ser instância de BusinessException
- **E**: Deve ter a mensagem fornecida
- **E**: Deve ter código de erro padrão
