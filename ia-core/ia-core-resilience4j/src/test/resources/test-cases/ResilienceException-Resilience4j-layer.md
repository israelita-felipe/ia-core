# Caso de Teste: ResilienceException

## Descrição
Testa a exceção base ResilienceException para todos os erros relacionados a resiliência.

## Classe Testada
`com.ia.core.resilience4j.exception.ResilienceException`

## Fluxo do Teste
1. Testar construtores
2. Testar getters
3. Testar valores padrão

## Cenários

### Cenário 1: Criar exceção com mensagem
- **Dado**: Uma mensagem de erro
- **Quando**: Criar ResilienceException com a mensagem
- **Então**: Deve criar exceção com a mensagem
- **E**: errorCode deve ser "RESILIENCE_ERROR"
- **E**: retryable deve ser true

### Cenário 2: Criar exceção com mensagem e causa
- **Dado**: Uma mensagem e uma causa
- **Quando**: Criar ResilienceException com mensagem e causa
- **Então**: Deve criar exceção com mensagem e causa
- **E**: errorCode deve ser "RESILIENCE_ERROR"
- **E**: retryable deve ser true

### Cenário 3: Criar exceção com código, mensagem e retryable
- **Dado**: Um código, mensagem e flag retryable
- **Quando**: Criar ResilienceException com os parâmetros
- **Então**: Deve criar exceção com os valores configurados
- **E**: getErrorCode() deve retornar o código configurado
- **E**: isRetryable() deve retornar a flag configurada

### Cenário 4: Criar exceção com código, mensagem, causa e retryable
- **Dado**: Um código, mensagem, causa e flag retryable
- **Quando**: Criar ResilienceException com os parâmetros
- **Então**: Deve criar exceção com os valores configurados
- **E**: getErrorCode() deve retornar o código configurado
- **E**: isRetryable() deve retornar a flag configurada

### Cenário 5: Verificar getErrorCode
- **Dado**: Uma ResilienceException criada
- **Quando**: Chamar getErrorCode()
- **Então**: Deve retornar o código de erro configurado

### Cenário 6: Verificar isRetryable
- **Dado**: Uma ResilienceException criada
- **Quando**: Chamar isRetryable()
- **Então**: Deve retornar a flag retryable configurada
