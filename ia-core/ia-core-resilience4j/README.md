# ia-core-resilience4j

A reusable resilience4j module for IA Core applications, providing declarative resilience patterns including Circuit Breaker, Retry, Bulkhead, Rate Limiter, and Time Limiter.

## Overview

This module provides a generic, reusable implementation of resilience patterns using Resilience4j, adapted from the Biblia service implementation but made generic for use across different service and view layers. It simplifies the application of resilience patterns through declarative annotations and centralized configuration.



## Estrutura do Pacote

O módulo segue esta estrutura de pacote:

```
src/main/java/com/ia/core/resilience4j/
├── annotation/
│   └── EnableResilience.java
├── aspect/
├── config/
│   ├── ResilienceAutoConfiguration.java
│   └── ResilienceProperties.java
├── dto/
├── exception/
├── fallback/
├── metrics/
├── profile/
│   └── ResilienceProfile.java
├── registry/
└── template/
```


## Features

- **Declarative Resilience**: Use `@Resilient` annotation to apply resilience patterns to methods
- **Multiple Patterns**: Circuit Breaker, Retry, Bulkhead, Rate Limiter, Time Limiter
- **Fallback Strategies**: Configurable fallback mechanisms for graceful degradation
- **Profile-Based Configuration**: Different resilience configurations for different service types
- **Metrics Integration**: Automatic metrics collection via Micrometer
- **AOP-Based**: Transparent aspect-oriented programming implementation
- **Spring Boot 3 Ready**: Designed for Spring Boot 3 applications
- **Chain of Responsibility**: Sophisticated fallback handling mechanism
- **Customizable**: Override individual resilience parameters per method

## Installation

Add the module as a dependency in your project's pom.xml:

```xml
<dependency>
    <groupId>com.ia</groupId>
    <artifactId>ia-core-resilience4j</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>
```

## Usage

### 1. Enable Resilience

Add `@EnableResilience` to your main application class or configuration:

```java
@SpringBootApplication
@EnableResilience
public class MyApplication {
    public static void main(String[] args) {
        SpringApplication.run(MyApplication.class, args);
    }
}
```

### 2. Apply Resilience to Methods

Use the `@Resilient` annotation on service methods:

```java
@Service
public class MyService {

    @Resilient(profile = ResilienceProfile.EXTERNAL_API)
    public String callExternalApi(String input) {
        // Your external API call logic here
        return externalApiClient.call(input);
    }
}
```

### 3. Configure Resilience Profiles

Configure resilience profiles in your `application.yml` or `application.properties`:

```yaml
ia:
  core:
    resilience4j:
      global:
        circuitBreaker:
          failureRateThreshold: 50
          waitDurationInOpenStateMs: 60000
          slidingWindowSize: 20
          minimumNumberOfCalls: 10
          permittedCallsInHalfOpen: 3
          automaticTransitionFromOpenToHalfOpen: true
          slowCallDurationThresholdMs: 5000
          slowCallRateThreshold: 100
          recordExceptions:
            - java.io.IOException
            - java.net.ConnectException
            - java.net.SocketTimeoutException
        retry:
          maxAttempts: 3
          initialWaitMs: 1000
          retryExceptions:
            - java.io.IOException
            - java.net.ConnectException
        bulkhead:
          maxConcurrentCalls: 10
          maxWaitDurationMs: 100
        rateLimiter:
          limitForPeriod: 10
          limitRefreshPeriodMs: 1000
          timeoutDurationMs: 500
        timeout:
          durationMs: 5000
          cancelRunningFuture: true

      profiles:
        EXTERNAL_API:
          circuitBreaker:
            failureRateThreshold: 40
            waitDurationInOpenStateMs: 30000
            slidingWindowSize: 10
            minimumNumberOfCalls: 5
            permittedCallsInHalfOpen: 2
            automaticTransitionFromOpenToHalfOpen: true
            slowCallDurationThresholdMs: 3000
            slowCallRateThreshold: 80
          retry:
            maxAttempts: 2
            initialWaitMs: 500
          bulkhead:
            maxConcurrentCalls: 5
            maxWaitDurationMs: 50
        LLM_SERVICE:
          circuitBreaker:
            failureRateThreshold: 30
            waitDurationInOpenStateMs: 60000
            slidingWindowSize: 10
            minimumNumberOfCalls: 3
            permittedCallsInHalfOpen: 1
            automaticTransitionFromOpenToHalfOpen: true
            slowCallDurationThresholdMs: 10000
            slowCallRateThreshold: 50
          retry:
            maxAttempts: 1
            initialWaitMs: 2000
          bulkhead:
            maxConcurrentCalls: 3
            maxWaitDurationMs: 100
        WEB_SCRAPING:
          circuitBreaker:
            failureRateThreshold: 50
            waitDurationInOpenStateMs: 60000
            slidingWindowSize: 10
            minimumNumberOfCalls: 3
            permittedCallsInHalfOpen: 1
            automaticTransitionFromOpenToHalfOpen: true
            slowCallDurationThresholdMs: 15000
            slowCallRateThreshold: 40
          retry:
            maxAttempts: 3
            initialWaitMs: 2000
          bulkhead:
            maxConcurrentCalls: 2
            maxWaitDurationMs: 100
        INTERNAL_SERVICE:
          circuitBreaker:
            failureRateThreshold: 50
            waitDurationInOpenStateMs: 30000
            slidingWindowSize: 20
            minimumNumberOfCalls: 10
            permittedCallsInHalfOpen: 3
            automaticTransitionFromOpenToHalfOpen: true
            slowCallDurationThresholdMs: 3000
            slowCallRateThreshold: 100
          retry:
            maxAttempts: 3
            initialWaitMs: 1000
          bulkhead:
            maxConcurrentCalls: 20
            maxWaitDurationMs: 100
        DATABASE:
          circuitBreaker:
            failureRateThreshold: 60
            waitDurationInOpenStateMs: 30000
            slidingWindowSize: 20
            minimumNumberOfCalls: 10
            permittedCallsInHalfOpen: 3
            automaticTransitionFromOpenToHalfOpen: true
            slowCallDurationThresholdMs: 2000
            slowCallRateThreshold: 100
          retry:
            maxAttempts: 3
            initialWaitMs: 1000
          bulkhead:
            maxConcurrentCalls: 15
            maxWaitDurationMs: 100
```

### 4. Using Different Resilience Profiles

The module includes predefined resilience profiles optimized for different service types:

```java
@Service
public class MyService {

    // For external HTTP API calls (IBGE, WhatsApp, Telegram)
    @Resilient(profile = ResilienceProfile.EXTERNAL_API)
    public String callExternalApi(String input) {
        return externalApiClient.call(input);
    }

    // For Large Language Model service calls (OpenAI, Ollama)
    @Resilient(profile = ResilienceProfile.LLM_SERVICE)
    public String generateText(String prompt) {
        return llmService.generate(prompt);
    }

    // For web scraping operations
    @Resilient(profile = ResilienceProfile.WEB_SCRAPING)
    public String scrapeWebsite(String url) {
        return scraper.scrape(url);
    }

    // For internal service-to-service calls (Feign between modules)
    @Resilient(profile = ResilienceProfile.INTERNAL_SERVICE)
    public InternalResponse callInternalService(InternalRequest request) {
        return internalFeignClient.call(request);
    }

    // For database operations
    @Resilient(profile = ResilienceProfile.DATABASE)
    public List<Entity> findAll() {
        return repository.findAll();
    }
}
```

### 5. Customizing Individual Parameters

You can override individual resilience parameters while using a profile as a base:

```java
@Service
public class MyService {

    @Resilient(
        profile = ResilienceProfile.EXTERNAL_API,
        maxRetryAttempts = 5,           // Override retry attempts
        timeoutMs = 15000,              // Override timeout
        circuitBreakerFailureRate = 30  // Override circuit breaker threshold
    )
    public String callExternalApiWithCustomSettings(String input) {
        return externalApiClient.call(input);
    }
}
```

### 6. View Layer Usage (Feign Clients)

The resilience skill works seamlessly with Spring Cloud OpenFeign clients:

```java
@FeignClient(name = "ibge-service", url = "${ibge.service.url}")
@EnableResilience // Enable resilience for this Feign client
public interface IbgeFeignClient {

    @GetMapping("/municipios/{codigo}")
    @Resilient(profile = ResilienceProfile.EXTERNAL_API)
    MunicipioDto getMunicipioByCodigo(@PathVariable("codigo") String codigo);

    @GetMapping("/estados/{uf}")
    @Resilient(profile = ResilienceProfile.EXTERNAL_API,
               maxRetryAttempts = 3,
               timeoutMs = 10000)
    EstadoDto getEstadoByUf(@PathVariable("uf") String uf);
}
```

Alternatively, you can apply resilience at the Feign client configuration level:

```java
@Configuration
public class FeignClientConfig {

    @Bean
    public IbgeFeignClient ibgeFeignClient() {
        return Feign.builder()
                .client(defaultFeignClientBuilder())
                .target(IbgeFeignClient.class, "https://servicodados.ibge.gov.br/api/v1");
    }
}
```

### 7. Fallback Strategies and Customization

#### Default Fallback Behavior

By default, when a resilience pattern prevents execution (circuit breaker open, bulkhead full, etc.), the module throws specific exceptions:
- `BulkheadFullException`
- `CircuitBreakerOpenException`
- `RateLimitExceededException`
- `ResilienceExecutionException` (wrapper for other exceptions)

#### Custom Fallback Methods

You can specify a fallback method to be called when resilience patterns prevent execution:

```java
@Service
public class MyService {

    @Resilient(
        profile = ResilienceProfile.EXTERNAL_API,
        fallbackMethod = "getDefaultMunicipio",
        fallbackOn = {BulkheadFullException.class, CircuitBreakerOpenException.class}
    )
    public MunicipioDto getMunicipioByCodigo(String codigo) {
        return ibgeClient.getMunicipioByCodigo(codigo);
    }

    // Fallback method
    public MunicipioDto getDefaultMunicipio(String codigo, Exception exception) {
        // Log the fallback execution
        log.warn("Using fallback for municipio {} due to: {}", codigo, exception.getMessage());

        // Return a default or cached value
        return MunicipioDto.builder()
                .codigo(codigo)
                .nome("Município não disponível")
                .uf("XX")
                .build();
    }
}
```

#### Fallback Bean Reference

You can reference a fallback method in another bean:

```java
@Service
public class MyService {

    @Resilient(
        profile = ResilienceProfile.EXTERNAL_API,
        fallbackBean = "municipioFallbackService",
        fallbackMethod = "getFallbackMunicipio"
    )
    public MunicipioDto getMunicipioByCodigo(String codigo) {
        return ibgeClient.getMunicipioByCodigo(codigo);
    }
}

@Service
public class MunicipioFallbackService {

    public MunicipioDto getFallbackMunicipio(String codigo, Exception exception) {
        return MunicipioDto.builder()
                .codigo(codigo)
                .nome("Município em manutenção")
                .uf("XX")
                .build();
    }
}
```

#### Programmatic Fallback Registration

For more complex fallback logic, you can register custom fallback strategies:

```java
@Component
public class CustomFallbackConfig {

    @Autowired
    public CustomFallbackConfig(FallbackStrategyRegistry fallbackStrategyRegistry) {
        fallbackStrategyRegistry.register(ResilienceProfile.EXTERNAL_API, (method, args, exception) -> {
            // Custom fallback logic based on method name, arguments, and exception
            if ("getMunicipioByCodigo".equals(method.getName()) && args.length > 0) {
                String codigo = (String) args[0];
                return FallbackResponse.builder()
                        .success(true)
                        .data(MunicipioDto.builder()
                                .codigo(codigo)
                                .nome("Município padrão")
                                .uf("XX")
                                .build())
                        .message("Using cached fallback for municipio")
                        .retryable(false)
                        .build();
            }

            return FallbackResponse.builder()
                    .success(false)
                    .errorCode("FALLBACK_NOT_AVAILABLE")
                    .message("No fallback available for this operation")
                    .retryable(true)
                    .build();
        });
    }
}
```

### 8. Metrics and Monitoring

The module automatically collects metrics using Micrometer with the prefix `resilience4j`:

#### Available Metrics

- `resilience4j.calls`: Counter for total calls (tagged by result, profile, method)
- `resilience4j.calls.error`: Counter for failed calls (tagged by error type, profile, method)
- `resilience4j.timers`: Timer for call duration (tagged by result, profile, method)
- `resilience4j.calls.rejection`: Counter for rejected calls (tagged by rejection type, profile, method)

#### Rejection Types

- `circuit_breaker`: Calls rejected by circuit breaker
- `bulkhead`: Calls rejected by bulkhead (concurrency limit)
- `rate_limiter`: Calls rejected by rate limiter
- `timeout`: Calls that timed out

#### Accessing Metrics

Metrics are automatically available through Spring Boot Actuator:

- JSON format: `GET /actuator/metrics/resilience4j.calls`
- Prometheus format: `GET /actuator/prometheus`

#### Example Metric Tags

When calling a method annotated with `@Resilient(profile = ResilienceProfile.EXTERNAL_API)` named `getMunicipioByCodigo`:

- `resilience4j.calls{result="success",profile="EXTERNAL_API",method="getMunicipioByCodigo"}`
- `resilience4j.calls{result="failure",profile="EXTERNAL_API",method="getMunicipioByCodigo",exception_type="java.net.TimeoutException"}`
- `resilience4j.calls{rejection_type="circuit_breaker",profile="EXTERNAL_API",method="getMunicipioByCodigo"}`

### 9. Exception Handling

The module throws specific exceptions when resilience patterns prevent execution:

#### Exception Hierarchy

- `ResilienceException` (base exception)
  - `BulkheadFullException`: When bulkhead rejects a call due to concurrency limits
  - `CircuitBreakerOpenException`: When circuit breaker is open and rejects calls
  - `RateLimitExceededException`: When rate limiter rejects a call
  - `ResilienceExecutionException`: Wrapper for exceptions during resilient execution

#### Handling Exceptions

```java
@Service
public class MyService {

    public String handleMunicipioRequest(String codigo) {
        try {
            return getMunicipioWithResilience(codigo);
        } catch (BulkheadFullException e) {
            // Handle bulkhead rejection (too many concurrent calls)
            log.warn("Bulkhead full for municipio request: {}", codigo);
            return getCachedMunicipio(codigo);
        } catch (CircuitBreakerOpenException e) {
            // Handle circuit breaker open (service temporarily unavailable)
            log.warn("Circuit breaker open for municipio request: {}", codigo);
            return getFallbackMunicipio(codigo);
        } catch (RateLimitExceededException e) {
            // Handle rate limit exceeded
            log.warn("Rate limit exceeded for municipio request: {}", codigo);
            return waitAndRetry(codigo);
        } catch (ResilienceExecutionException e) {
            // Handle other execution exceptions
            log.error("Resilience execution failed for municipio {}: {}", codigo, e.getMessage());
            throw e.getCause(); // Re-throw the original exception
        }
    }

    @Resilient(profile = ResilienceProfile.EXTERNAL_API)
    private String getMunicipioWithResilience(String codigo) {
        return ibgeClient.getMunicipioByCodigo(codigo);
    }
}
```

### 10. Advanced Configuration

#### Disabling Fallback Per Method

```java
@Service
public class MyService {

    @Resilient(
        profile = ResilienceProfile.EXTERNAL_API,
        fallbackEnabled = false  // Disable fallback for this method
    )
    public String criticalOperation(String input) {
        // This method will throw resilience exceptions directly
        // without attempting any fallback
        return externalApiClient.criticalOperation(input);
    }
}
```

#### Specifying Which Exceptions Trigger Fallback

```java
@Service
public class MyService {

    @Resilient(
        profile = ResilienceProfile.EXTERNAL_API,
        fallbackOn = {IOException.class, TimeoutException.class},  // Only these exceptions trigger fallback
        noFallbackOn = {IllegalArgumentException.class}  // These exceptions never trigger fallback
    )
    public String callExternalApi(String input) {
        return externalApiClient.call(input);
    }
}
```

#### Using Registry Names for Multiple Instances

When you have multiple instances of the same client type:

```java
@Service
public class MyService {

    private final IbgeFeignClient ibgeClientPrimary;
    private final IbgeFeignClient ibgeClientSecondary;

    public MyService(IbgeFeignClient ibgeClientPrimary,
                     IbgeFeignClient ibgeClientSecondary) {
        this.ibgeClientPrimary = ibgeClientPrimary;
        this.ibgeClientSecondary = ibgeClientSecondary;
    }

    @Resilient(
        profile = ResilienceProfile.EXTERNAL_API,
        registryName = "ibge-primary"  // Specific registry name for this instance
    )
    public String callPrimary(String input) {
        return ibgeClientPrimary.call(input);
    }

    @Resilient(
        profile = ResilienceProfile.EXTERNAL_API,
        registryName = "ibge-secondary"  // Different registry name for this instance
    )
    public String callSecondary(String input) {
        return ibgeClientSecondary.call(input);
    }
}
```

## Building

```bash
./mvnw clean install
```

## Configuration Properties Reference

All configuration properties are under `ia.core.resilience4j`:

| Property Path | Description | Default |
|---------------|-------------|---------|
| `ia.core.resilience4j.global.circuitBreaker.failureRateThreshold` | Failure rate threshold in percentage | 50 |
| `ia.core.resilience4j.global.circuitBreaker.waitDurationInOpenStateMs` | Wait duration in open state | 60000 |
| `ia.core.resilience4j.global.circuitBreaker.slidingWindowSize` | Sliding window size | 20 |
| `ia.core.resilience4j.global.circuitBreaker.minimumNumberOfCalls` | Minimum number of calls | 10 |
| `ia.core.resilience4j.global.circuitBreaker.permittedCallsInHalfOpen` | Permitted calls in half open state | 3 |
| `ia.core.resilience4j.global.circuitBreaker.automaticTransitionFromOpenToHalfOpen` | Auto transition from open to half open | true |
| `ia.core.resilience4j.global.circuitBreaker.slowCallDurationThresholdMs` | Slow call duration threshold | 5000 |
| `ia.core.resilience4j.global.circuitBreaker.slowCallRateThreshold` | Slow call rate threshold | 100 |
| `ia.core.resilience4j.global.circuitBreaker.recordExceptions` | List of exceptions to record | [IOException, ConnectException, SocketTimeoutException] |
| `ia.core.resilience4j.global.retry.maxAttempts` | Maximum number of retry attempts | 3 |
| `ia.core.resilience4j.global.retry.initialWaitMs` | Initial wait time between retries | 1000 |
| `ia.core.resilience4j.global.retry.retryExceptions` | List of exceptions to retry | [IOException, ConnectException] |
| `ia.core.resilience4j.global.bulkhead.maxConcurrentCalls` | Maximum concurrent calls | 10 |
| `ia.core.resilience4j.global.bulkhead.maxWaitDurationMs` | Maximum wait duration | 100 |
| `ia.core.resilience4j.global.rateLimiter.limitForPeriod` | Limit for period | 10 |
| `ia.core.resilience4j.global.rateLimiter.limitRefreshPeriodMs` | Limit refresh period | 1000 |
| `ia.core.resilience4j.global.rateLimiter.timeoutDurationMs` | Timeout duration | 500 |
| `ia.core.resilience4j.global.timeout.durationMs` | Timeout duration | 5000 |
| `ia.core.resilience4j.global.timeout.cancelRunningFuture` | Cancel running future on timeout | true |

Each profile can override any subset of these global configurations.

## Resilience Profiles Reference

| Profile | Use Case | Retry Attempts | CB Threshold | Bulkhead | Rate Limit | Timeout |
|---------|----------|----------------|--------------|----------|------------|---------|
| `DEFAULT` | Basic resilience | 3 | 50% | 10 | 10/s | 5s |
| `EXTERNAL_API` | External HTTP APIs | 2 | 40% | 5 | 100/s | 15s |
| `LLM_SERVICE` | LLM API calls | 1 | 30% | 3 | 10/s | 30s |
| `WEB_SCRAPING` | Web scraping | 3 | 50% | 2 | 2/s | 60s |
| `INTERNAL_SERVICE` | Internal service calls | 3 | 50% | 20 | 200/s | 10s |
| `DATABASE` | Database operations | 3 | 60% | 15 | 500/s | 5s |

## Best Practices

1. **Choose the Right Profile**: Select the predefined profile that best matches your service type
2. **Start with Defaults**: Use profile defaults before customizing individual parameters
3. **Monitor Metrics**: Regularly check resilience metrics to tune configurations
4. **Implement Meaningful Fallbacks**: Provide useful fallback responses rather than just failing fast
5. **Handle Exceptions Appropriately**: Catch specific resilience exceptions for appropriate error handling
6. **Keep Fallbacks Simple**: Fallback methods should have minimal dependencies to avoid cascading failures
7. **Test Resilience Behavior**: Test circuit breaker, bulkhead, and rate limiter scenarios
8. **Document Fallback Behavior**: Clearly document what happens when resilience patterns prevent execution

## Troubleshooting

### No Metrics Appearing

Ensure you have Micrometer and Spring Boot Actuator dependencies in your project:
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
<dependency>
    <groupId>io.micrometer</groupId>
    <artifactId>micrometer-registry-prometheus</artifactId>
</dependency>
```

### Fallback Not Being Called

Check that:
1. `fallbackEnabled()` is not set to `false`
2. The exception type is included in `fallbackOn()` (or not excluded by `noFallbackOn()`)
3. The fallback method signature matches: `ReturnType methodName(Method method, Object[] args, Exception exception)`

### Configuration Not Taking Effect

Ensure:
1. Properties are under `ia.core.resilience4j` prefix
2. You're using the correct property names (camelCase in Java becomes kebab-case in YAML)
3. Profile names match exactly (case-sensitive)

## License

This module is part of the IA Core project and is licensed under the Apache License 2.0.
