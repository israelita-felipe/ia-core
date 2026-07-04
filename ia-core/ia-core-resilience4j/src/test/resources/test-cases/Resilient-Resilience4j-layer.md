# Caso de Teste: Resilient

## Descrição
Testa a anotação Resilient que combina múltiplos padrões de resiliência do Resilience4j.

## Classe Testada
`com.ia.core.resilience4j.annotation.Resilient`

## Fluxo do Teste
1. Testar valores padrão da anotação
2. Testar configurações de Circuit Breaker
3. Testar configurações de Retry
4. Testar configurações de Bulkhead
5. Testar configurações de Rate Limiter
6. Testar configurações de Timeout
7. Testar configurações de Fallback

## Cenários

### Cenário 1: Verificar valor padrão do profile
- **Dado**: A anotação Resilient sem parâmetros
- **Quando**: Obter o valor padrão
- **Então**: Deve ser ResilienceProfile.DEFAULT

### Cenário 2: Verificar valor padrão do registryName
- **Dado**: A anotação Resilient sem parâmetros
- **Quando**: Obter o valor padrão
- **Então**: Deve ser string vazia

### Cenário 3: Verificar valores padrão do Circuit Breaker
- **Dado**: A anotação Resilient sem parâmetros
- **Quando**: Obter valores padrão
- **Então**: circuitBreakerFailureRate deve ser -1
- **E**: circuitBreakerWaitDuration deve ser -1
- **E**: circuitBreakerSlidingWindow deve ser -1
- **E**: circuitBreakerMinCalls deve ser -1

### Cenário 4: Verificar valores padrão do Retry
- **Dado**: A anotação Resilient sem parâmetros
- **Quando**: Obter valores padrão
- **Então**: maxRetryAttempts deve ser -1
- **E**: retryInitialWait deve ser -1
- **E**: retryBackoffMultiplier deve ser -1
- **E**: retryMaxWait deve ser -1

### Cenário 5: Verificar valores padrão do Bulkhead
- **Dado**: A anotação Resilient sem parâmetros
- **Quando**: Obter valores padrão
- **Então**: bulkheadMaxConcurrent deve ser -1
- **E**: bulkheadMaxWait deve ser -1

### Cenário 6: Verificar valores padrão do Rate Limiter
- **Dado**: A anotação Resilient sem parâmetros
- **Quando**: Obter valores padrão
- **Então**: rateLimiterLimit deve ser -1
- **E**: rateLimiterPeriod deve ser -1

### Cenário 7: Verificar valor padrão do Timeout
- **Dado**: A anotação Resilient sem parâmetros
- **Quando**: Obter valor padrão
- **Então**: timeoutMs deve ser -1

### Cenário 8: Verificar valores padrão do Fallback
- **Dado**: A anotação Resilient sem parâmetros
- **Quando**: Obter valores padrão
- **Então**: fallbackMethod deve ser string vazia
- **E**: fallbackBean deve ser string vazia
- **E**: fallbackEnabled deve ser false
