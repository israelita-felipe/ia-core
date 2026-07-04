# Caso de Teste: ResilienceProperties

## Descrição
Testa a classe ResilienceProperties que centraliza as configurações do Resilience4j.

## Classe Testada
`com.ia.core.resilience4j.config.ResilienceProperties`

## Fluxo do Teste
1. Testar configurações globais
2. Testar configurações de perfil
3. Testar configurações de Retry
4. Testar configurações de Circuit Breaker
5. Testar configurações de Bulkhead
6. Testar configurações de Rate Limiter
7. Testar configurações de Timeout
8. Testar configurações de Fallback
9. Testar configurações de Metrics

## Cenários

### Cenário 1: Verificar configuração global padrão
- **Dado**: Uma instância de ResilienceProperties
- **Quando**: Obter a configuração global
- **Então**: Deve ter Retry configurado
- **E**: Deve ter CircuitBreaker configurado
- **E**: Deve ter Bulkhead configurado
- **E**: Deve ter RateLimiter configurado
- **E**: Deve ter Timeout configurado

### Cenário 2: Verificar valores padrão de Retry
- **Dado**: Uma instância de ResilienceProperties
- **Quando**: Obter a configuração de Retry global
- **Então**: maxAttempts deve ser 3
- **E**: initialWaitMs deve ser 1000
- **E**: backoffMultiplier deve ser 2.0
- **E**: maxWaitMs deve ser 10000
- **E**: exponentialBackoff deve ser true

### Cenário 3: Verificar valores padrão de Circuit Breaker
- **Dado**: Uma instância de ResilienceProperties
- **Quando**: Obter a configuração de Circuit Breaker global
- **Então**: failureRateThreshold deve ser 50
- **E**: waitDurationInOpenStateMs deve ser 30000
- **E**: slidingWindowSize deve ser 10
- **E**: minimumNumberOfCalls deve ser 5

### Cenário 4: Verificar valores padrão de Bulkhead
- **Dado**: Uma instância de ResilienceProperties
- **Quando**: Obter a configuração de Bulkhead global
- **Então**: maxConcurrentCalls deve ser 10
- **E**: maxWaitDurationMs deve ser 0

### Cenário 5: Verificar valores padrão de Rate Limiter
- **Dado**: Uma instância de ResilienceProperties
- **Quando**: Obter a configuração de Rate Limiter global
- **Então**: limitForPeriod deve ser 50
- **E**: limitRefreshPeriodMs deve ser 1000
- **E**: timeoutDurationMs deve ser 2000

### Cenário 6: Verificar valores padrão de Timeout
- **Dado**: Uma instância de ResilienceProperties
- **Quando**: Obter a configuração de Timeout global
- **Então**: durationMs deve ser 10000
- **E**: cancelRunningFuture deve ser true

### Cenário 7: Verificar valores padrão de Fallback
- **Dado**: Uma instância de ResilienceProperties
- **Quando**: Obter a configuração de Fallback de perfil
- **Então**: enabled deve ser true
- **E**: fallbackMethod deve ser null
- **E**: fallbackBean deve ser null

### Cenário 8: Verificar valores padrão de Metrics
- **Dado**: Uma instância de ResilienceProperties
- **Quando**: Obter a configuração de Metrics
- **Então**: enabled deve ser true
- **E**: exportToMicrometer deve ser true
