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

Usar **Resilience4j** como biblioteca de padrões de resiliência, integrado ao Spring Boot via **ia-core-resilience4j** — um módulo reutilizável que fornece anotações declarativas (`@Resilient`) e perfis de resiliência (`ResilienceProfile`) para aplicação consistente de padrões de resiliência em toda a aplicação.

## Detalhes

 ### Dependências Maven

 ```xml
 <dependency>
     <groupId>com.ia</groupId>
     <artifactId>ia-core-resilience4j</artifactId>
     <version>0.0.1-SNAPSHOT</version>
 </dependency>
 ```

 ### Habilitação do Módulo

 Adicionar `@EnableResilience` na classe principal da aplicação:

 O módulo `ia-core-resilience4j` fornece:
 - `@EnableResilience` - Anotação para ativar a configuração automática
 - `@Resilient` - Anotação para aplicar padrões de resiliência
 - `ResilienceProfile` - Enum com perfis predefinidos

```java
@SpringBootApplication
@EnableResilience
public class MyApplication {
    public static void main(String[] args) {
        SpringApplication.run(MyApplication.class, args);
    }
}
```

### 1. Circuit Breaker

 ```java
 package com.ia.biblia.service.resilience;

 import com.ia.core.resilience4j.annotation.Resilient;
 import com.ia.core.resilience4j.profile.ResilienceProfile;

 @Service
 @RequiredArgsConstructor
 public class ExternalApiService {

     @Resilient(ResilienceProfile.EXTERNAL_API)
     public String callExternalApi(String param) {
         return externalClient.getData(param);
     }
 }
 ```

 ### 2. Retry

 ```java
 package com.ia.biblia.service.resilience;

 import com.ia.core.resilience4j.annotation.Resilient;
 import com.ia.core.resilience4j.profile.ResilienceProfile;

 @Service
 @RequiredArgsConstructor
 public class PaymentService {

     @Resilient(value = ResilienceProfile.LLM_SERVICE, maxRetryAttempts = 3)
     public PaymentResult processPayment(PaymentDTO payment) {
         return paymentGateway.process(payment);
     }
 }
 ```

 ### 3. Rate Limiter

 ```java
 package com.ia.biblia.service.resilience;

 import com.ia.core.resilience4j.annotation.Resilient;
 import com.ia.core.resilience4j.profile.ResilienceProfile;

 @Service
 public class ApiService {

     @Resilient(ResilienceProfile.WEB_SCRAPING)
     public Response getData() {
         // limit: 100 requests per 10 seconds
     }
 }
 ```

 ### 4. Bulkhead

 ```java
 package com.ia.biblia.service.resilience;

 import com.ia.core.resilience4j.annotation.Resilient;
 import com.ia.core.resilience4j.profile.ResilienceProfile;

 @Service
 public class ConcurrentService {

     @Resilient(ResilienceProfile.INTERNAL_SERVICE)
     public Result processConcurrently(Request request) {
         // Máximo 10 chamadas simultâneas
     }
 }
 ```

 ### 5. Timeout

 ```java
 package com.ia.biblia.service.resilience;

 import com.ia.core.resilience4j.annotation.Resilient;
 import com.ia.core.resilience4j.profile.ResilienceProfile;

 @Service
 public class TimeoutService {

     @Resilient(value = ResilienceProfile.DATABASE, timeoutMs = 5000)
     public Response getWithTimeout() {
         // Timeout de 5 segundos
     }
 }
 ```

 ### Combinação de Patterns

 ```java
 package com.ia.biblia.service.resilience;

 import com.ia.core.resilience4j.annotation.Resilient;
 import com.ia.core.resilience4j.profile.ResilienceProfile;

 @Service
 public class CombinedService {

     @Resilient(value = ResilienceProfile.EXTERNAL_API, maxRetryAttempts = 3)
     public Response combinedCall(String param) {
         return ibgeClient.call(param);
     }
 }
 ```

### Configuração Centralizada

A configuração é centralizada via namespace `ia.core.resilience4j` no `application.yml`:

 ```yaml
ia:
  core:
    resilience4j:
      global:
        circuit-breaker:
          failure-rate-threshold: 50
          wait-duration-in-open-state-ms: 60000
          sliding-window-size: 20
          minimum-number-of-calls: 10
          permitted-calls-in-half-open: 3
          automatic-transition-from-open-to-half-open: true
          slow-call-duration-threshold-ms: 5000
          slow-call-rate-threshold: 100
          record-exceptions:
            - java.io.IOException
            - java.net.ConnectException
            - java.net.SocketTimeoutException
        retry:
          max-attempts: 3
          initial-wait-ms: 1000
          retry-exceptions:
            - java.io.IOException
            - java.net.ConnectException
        bulkhead:
          max-concurrent-calls: 10
          max-wait-duration-ms: 100
        rate-limiter:
          limit-for-period: 10
          limit-refresh-period-ms: 1000
          timeout-duration-ms: 500
        timeout:
          duration-ms: 5000
          cancel-running-future: true

      profiles:
        EXTERNAL_API:
          circuit-breaker:
            failure-rate-threshold: 40
            wait-duration-in-open-state-ms: 30000
            sliding-window-size: 10
            minimum-number-of-calls: 5
            permitted-calls-in-half-open: 2
            automatic-transition-from-open-to-half-open: true
            slow-call-duration-threshold-ms: 3000
            slow-call-rate-threshold: 80
          retry:
            max-attempts: 2
            initial-wait-ms: 500
          bulkhead:
            max-concurrent-calls: 5
            max-wait-duration-ms: 50
        LLM_SERVICE:
          circuit-breaker:
            failure-rate-threshold: 30
            wait-duration-in-open-state-ms: 60000
            sliding-window-size: 10
            minimum-number-of-calls: 3
            permitted-calls-in-half-open: 1
            automatic-transition-from-open-to-half-open: true
            slow-call-duration-threshold-ms: 10000
            slow-call-rate-threshold: 50
          retry:
            max-attempts: 1
            initial-wait-ms: 2000
          bulkhead:
            max-concurrent-calls: 3
            max-wait-duration-ms: 100
        WEB_SCRAPING:
          circuit-breaker:
            failure-rate-threshold: 50
            wait-duration-in-open-state-ms: 60000
            sliding-window-size: 10
            minimum-number-of-calls: 3
            permitted-calls-in-half-open: 1
            automatic-transition-from-open-to-half-open: true
            slow-call-duration-threshold-ms: 15000
            slow-call-rate-threshold: 40
          retry:
            max-attempts: 3
            initial-wait-ms: 2000
          bulkhead:
            max-concurrent-calls: 2
            max-wait-duration-ms: 100
        INTERNAL_SERVICE:
          circuit-breaker:
            failure-rate-threshold: 50
            wait-duration-in-open-state-ms: 30000
            sliding-window-size: 20
            minimum-number-of-calls: 10
            permitted-calls-in-half-open: 3
            automatic-transition-from-open-to-half-open: true
            slow-call-duration-threshold-ms: 3000
            slow-call-rate-threshold: 100
          retry:
            max-attempts: 3
            initial-wait-ms: 1000
          bulkhead:
            max-concurrent-calls: 20
            max-wait-duration-ms: 100
        DATABASE:
          circuit-breaker:
            failure-rate-threshold: 60
            wait-duration-in-open-state-ms: 30000
            sliding-window-size: 20
            minimum-number-of-calls: 10
            permitted-calls-in-half-open: 3
            automatic-transition-from-open-to-half-open: true
            slow-call-duration-threshold-ms: 2000
            slow-call-rate-threshold: 100
          retry:
            max-attempts: 3
            initial-wait-ms: 1000
          bulkhead:
            max-concurrent-calls: 15
            max-wait-duration-ms: 100
 ```

## Perfis Predefinidos

O módulo `ia-core-resilience4j` fornece perfis de resiliência predefinidos, cada um otimizado para um tipo específico de operação. Os perfis podem ser usados diretamente na anotação `@Resilient` e suas configurações podem ser ajustadas individualmente via `application.yml` sob o namespace `ia.core.resilience4j.profiles`.

| Perfil | Descrição | Casos de Uso |
|--------|-----------|--------------|
| `EXTERNAL_API` | Resiliência para chamadas a APIs externas com tolerância a falhas de rede e latência variável. | IBGE, WhatsApp, Telegram, serviços de terceiros |
| `LLM_SERVICE` | Configuração otimizada para serviços de LLM com alta latência e custos elevados por requisição. | OpenAI, Ollama, GPT-4, Claude |
| `WEB_SCRAPING` | Resiliência para operações de web scraping com alta taxa de falha e necessidade de retry agressivo. | Scraping de sites, coleta de dados web |
| `INTERNAL_SERVICE` | Resiliência para chamadas entre serviços internos via Feign, com maior tolerância a concorrência. | Chamadas Feign entre módulos, microserviços internos |
| `DATABASE` | Resiliência para operações de banco de dados com foco em evitar sobrecarga do SGBD. | JPA Repositories, queries complexas, conexões JDBC |
| `DEFAULT` | Configurações padrão equilibradas para operações gerais sem perfil específico. | Uso genérico, operações internas simples |

### Comparação de Parâmetros

| Parâmetro | `DEFAULT` | `EXTERNAL_API` | `LLM_SERVICE` | `WEB_SCRAPING` | `INTERNAL_SERVICE` | `DATABASE` |
|-----------|-----------|----------------|---------------|----------------|--------------------|------------|
| CB Failure Rate | 50% | 40% | 30% | 50% | 50% | 60% |
| CB Wait (ms) | 30000 | 30000 | 60000 | 60000 | 30000 | 30000 |
| CB Sliding Window | 10 | 10 | 10 | 10 | 20 | 20 |
| Retry Max Attempts | 3 | 2 | 1 | 3 | 3 | 3 |
| Retry Initial Wait (ms) | 1000 | 500 | 2000 | 2000 | 1000 | 1000 |
| Bulkhead Max Concurrent | 10 | 5 | 3 | 2 | 20 | 15 |
| Timeout (ms) | 5000 | 15000 | 30000 | 60000 | 10000 | 5000 |

---

## Consequências

### Positivas

- ✅ Tratamento de falhas elegante
- ✅ Recuperação automática
- ✅ Monitoramento de resiliência
- ✅ Fallbacks configuráveis
- ✅ Patterns combináveis
- ✅ Configuração centralizada via `ia.core.resilience4j`
- ✅ Perfis predefinidos para diferentes tipos de serviço

### Negativas

- ❌ Complexidade adicional
- ❌ Requer tuning de parâmetros
- ❌ Não substitui boas práticas de design

## Status de Implementação

✅ **COMPLETO**

- Circuit breaker em serviços externos
- Retry configurado para APIs instáveis
- Dependência adicionada em ia-core-view
- Feign Clients devem usar Resilience4j (configuração disponível)

### 5.1 Feign Clients com Resilience4j (via Spring Cloud)

A partir de 2026-03-27, o projeto suporta Resilience4j para clientes Feign via Spring Cloud Circuit Breaker.

1. **Dependências** (já adicionadas em ia-core-view/pom.xml):

 ```xml
 <dependency>
     <groupId>com.ia</groupId>
     <artifactId>ia-core-resilience4j</artifactId>
     <version>0.0.1-SNAPSHOT</version>
 </dependency>
 ```

2. **Habilite Resilience4j no cliente Feign**:

 ```java
 package com.ia.biblia.service.resilience;

 import com.ia.core.resilience4j.annotation.Resilient;
 import com.ia.core.resilience4j.profile.ResilienceProfile;

 @FeignClient(name = "externalApi", url = "https://api.example.com")
 public interface ExternalApiClient {

     @GetMapping("/data")
     @Resilient(ResilienceProfile.EXTERNAL_API)
     DataResponse getData();
 }
 ```

 3. **Configure fallback** (opcional):

 ```java
 package com.ia.biblia.service.resilience;

 import com.ia.core.resilience4j.annotation.Resilient;
 import com.ia.core.resilience4j.profile.ResilienceProfile;

 @FeignClient(name = "externalApi", url = "https://api.example.com")
 public interface ExternalApiClient {

     @GetMapping("/data")
     @Resilient(value = ResilienceProfile.EXTERNAL_API,
                fallbackBean = "municipioFallbackService")
     DataResponse getData();
 }

 @Service
 public class MunicipioFallbackService {

     public DataResponse getData(String param, Exception exception) {
         return DataResponse.empty(); // Retorna dados padrão em caso de falha
     }
 }
 ```

 4. **Configure via application.yml**:

  ```yaml
ia:
  core:
    resilience4j:
      global:
        circuit-breaker:
          failure-rate-threshold: 50
          wait-duration-in-open-state-ms: 60000
          sliding-window-size: 20
          minimum-number-of-calls: 10
          permitted-calls-in-half-open: 3
          automatic-transition-from-open-to-half-open: true
          slow-call-duration-threshold-ms: 5000
          slow-call-rate-threshold: 100
          record-exceptions:
            - java.io.IOException
            - java.net.ConnectException
            - java.net.SocketTimeoutException
        retry:
          max-attempts: 3
          initial-wait-ms: 1000
          retry-exceptions:
            - java.io.IOException
            - java.net.ConnectException
        bulkhead:
          max-concurrent-calls: 10
          max-wait-duration-ms: 100
        rate-limiter:
          limit-for-period: 10
          limit-refresh-period-ms: 1000
          timeout-duration-ms: 500
        timeout:
          duration-ms: 5000
          cancel-running-future: true

      profiles:
        EXTERNAL_API:
          circuit-breaker:
            failure-rate-threshold: 40
            wait-duration-in-open-state-ms: 30000
            sliding-window-size: 10
            minimum-number-of-calls: 5
            permitted-calls-in-half-open: 2
            automatic-transition-from-open-to-half-open: true
            slow-call-duration-threshold-ms: 3000
            slow-call-rate-threshold: 80
          retry:
            max-attempts: 2
            initial-wait-ms: 500
          bulkhead:
            max-concurrent-calls: 5
            max-wait-duration-ms: 50
        LLM_SERVICE:
          circuit-breaker:
            failure-rate-threshold: 30
            wait-duration-in-open-state-ms: 60000
            sliding-window-size: 10
            minimum-number-of-calls: 3
            permitted-calls-in-half-open: 1
            automatic-transition-from-open-to-half-open: true
            slow-call-duration-threshold-ms: 10000
            slow-call-rate-threshold: 50
          retry:
            max-attempts: 1
            initial-wait-ms: 2000
          bulkhead:
            max-concurrent-calls: 3
            max-wait-duration-ms: 100
        WEB_SCRAPING:
          circuit-breaker:
            failure-rate-threshold: 50
            wait-duration-in-open-state-ms: 60000
            sliding-window-size: 10
            minimum-number-of-calls: 3
            permitted-calls-in-half-open: 1
            automatic-transition-from-open-to-half-open: true
            slow-call-duration-threshold-ms: 15000
            slow-call-rate-threshold: 40
          retry:
            max-attempts: 3
            initial-wait-ms: 2000
          bulkhead:
            max-concurrent-calls: 2
            max-wait-duration-ms: 100
        INTERNAL_SERVICE:
          circuit-breaker:
            failure-rate-threshold: 50
            wait-duration-in-open-state-ms: 30000
            sliding-window-size: 20
            minimum-number-of-calls: 10
            permitted-calls-in-half-open: 3
            automatic-transition-from-open-to-half-open: true
            slow-call-duration-threshold-ms: 3000
            slow-call-rate-threshold: 100
          retry:
            max-attempts: 3
            initial-wait-ms: 1000
          bulkhead:
            max-concurrent-calls: 20
            max-wait-duration-ms: 100
        DATABASE:
          circuit-breaker:
            failure-rate-threshold: 60
            wait-duration-in-open-state-ms: 30000
            sliding-window-size: 20
            minimum-number-of-calls: 10
            permitted-calls-in-half-open: 3
            automatic-transition-from-open-to-half-open: true
            slow-call-duration-threshold-ms: 2000
            slow-call-rate-threshold: 100
          retry:
            max-attempts: 3
            initial-wait-ms: 1000
          bulkhead:
            max-concurrent-calls: 15
            max-wait-duration-ms: 100
  ```

 **Nota**: O uso de RestTemplate é desencorajado. Todos os clientes HTTP devem usar Feign.

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
