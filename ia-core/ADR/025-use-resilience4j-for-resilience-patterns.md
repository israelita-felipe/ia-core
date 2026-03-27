# ADR-025: Usar Resilience4j para Patterns de Resiliência

## Status

✅ Aceito

## Contexto

O projeto precisa lidar com cenários de falha como:
- Serviços externos indisponíveis
- Latência alta em chamadas
- Falhas temporárias (transientes)
- Sobrecarga de recursos

É necessário implementar padrões de resiliência para garantir:
- Tolerância a falhas
- Recuperação automática
- Fallback para operações críticas

## Decisão

Usar **Resilience4j** como biblioteca de padrões de resiliência, integrado ao Spring Boot via Spring Retry.

## Detalhes

### Dependências Maven

```xml
<dependency>
    <groupId>io.github.resilience4j</groupId>
    <artifactId>resilience4j-spring-boot3</artifactId>
    <version>2.2.0</version>
</dependency>
<dependency>
    <groupId>org.springframework.retry</groupId>
    <artifactId>spring-retry</artifactId>
</dependency>
```

### 1. Circuit Breaker

```java
@Service
@RequiredArgsConstructor
public class ExternalApiService {
    
    @CircuitBreaker(name = "externalApi", fallbackMethod = "fallback")
    public String callExternalApi(String param) {
        return externalClient.getData(param);
    }
    
    private String fallback(String param, Exception e) {
        log.warn("Circuit breaker fallback para: {}", param);
        return "{\"status\": \"unavailable\"}";
    }
}
```

### Configuração

```yaml
resilience4j:
  circuitbreaker:
    instances:
      externalApi:
        registerHealthIndicator: true
        slidingWindowSize: 10
        minimumNumberOfCalls: 5
        permittedNumberOfCallsInHalfOpenState: 3
        automaticTransitionFromOpenToHalfOpenEnabled: true
        waitDurationInOpenState: 5s
        failureRateThreshold: 50
        eventConsumerBufferSize: 10
```

### 2. Retry

```java
@Service
@RequiredArgsConstructor
public class PaymentService {
    
    @Retry(name = "paymentRetry", maxAttempts = 3)
    public PaymentResult processPayment(PaymentDTO payment) {
        return paymentGateway.process(payment);
    }
}
```

```yaml
resilience4j:
  retry:
    instances:
      paymentRetry:
        maxAttempts: 3
        waitDuration: 2s
        enableExponentialBackoff: true
        exponentialBackoffMultiplier: 2
        retryExceptions:
          - java.io.IOException
          - java.util.concurrent.TimeoutException
```

### 3. Rate Limiter

```java
@Service
public class ApiService {
    
    @RateLimiter(name = "apiRateLimit")
    public Response getData() {
        // limit: 100 requests per 10 seconds
    }
}
```

```yaml
resilience4j:
  ratelimiter:
    instances:
      apiRateLimit:
        limitForPeriod: 100
        limitRefreshPeriod: 10s
        timeoutDuration: 0
```

### 4. Bulkhead

```java
@Service
public class ConcurrentService {
    
    @Bulkhead(name = "concurrentBulkhead")
    public Result processConcurrently(Request request) {
        // Máximo 10 chamadas simultâneas
    }
}
```

```yaml
resilience4j:
  bulkhead:
    instances:
      concurrentBulkhead:
        maxConcurrentCalls: 10
        maxWaitDuration: 0
```

### 5. Timeout

```java
@Service
public class TimeoutService {
    
    @Timeout(name = "timeoutService", duration = 5)
    public Response getWithTimeout() {
        // Timeout de 5 segundos
    }
}
```

### Combinação de Patterns

```java
@Service
public class CombinedService {
    
    @CircuitBreaker(name = "combined", fallbackMethod = "fallback")
    @Retry(maxAttempts = 3, delay = 1000)
    @RateLimiter(name = "apiLimit")
    public Response combinedCall(String param) {
        return externalService.call(param);
    }
}
```

### Events e Monitoramento

```java
@Component
public class ResilienceEventListener {
    
    @EventListener
    public void onCircuitStateTransition(CircuitBreakerEvent event) {
        log.info("Circuit breaker transition: {} -> {}", 
            event.getStateTransition().getFromState(),
            event.getStateTransition().getToState());
    }
    
    @EventListener
    public void onRetry(RetryEvent event) {
        log.warn("Retry attempt: {}", event.getAttemptNumber());
    }
}
```

## Consequências

### Positivas

- ✅ Tratamento de falhas elegante
- ✅ Recuperação automática
- ✅ Monitoramento de resiliência
- ✅ Fallbacks configuráveis
- ✅ Patterns combinaveis

### Negativas

- ❌ Complexidade adicional
- ❌ Requer tuning de parâmetros
- ❌ Não substitui boas práticas de design

## Status de Implementação

✅ **COMPLETO**

- Circuit breaker em serviços externos
- Retry configurado para APIs instáveis

## Data

2024-05-15

## Revisores

- Team Lead
- Architect

## Referências

1. **Resilience4j Documentation**
   - URL: https://resilience4j.readme.io/
   - Documentação oficial

2. **Resilience4j GitHub**
   - URL: https://github.com/resilience4j/resilience4j
   - Repositório

3. **Baeldung - Resilience4j Tutorial**
   - URL: https://www.baeldung.com/resilience4j
   - Guia completo

4. **Spring Boot - Circuit Breaker**
   - URL: https://spring.io/blog/2022/05/06/spring-boot-circuitbreaker
   - Integração Spring

5. **Martin Fowler - Circuit Breaker**
   - URL: https://martinfowler.com/bliki/CircuitBreaker.html
   - Padrão original