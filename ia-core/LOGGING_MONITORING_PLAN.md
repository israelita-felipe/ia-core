# Plano de Logging e Monitoramento - ia-core-apps

## Visão Geral

Este documento estabelece a estratégia padronizada de logging e monitoramento para o projeto ia-core-apps, garantindo rastreabilidade, observabilidade e diagnóstico eficiente de problemas em ambiente de produção.

## 1. Estado Atual

### 1.1 Dependências Existentes
- **Spring Boot Actuator** - Já configurado no projeto
- **Lombok @Slf4j** - Uso existente nos serviços

### 1.2 Arquitetura de Logging Atual
O projeto atualmente utiliza:
- `lombok.extern.slf4j.Slf4j` para logging
- Sem configuração explícita de níveis por ambiente
- Sem correlation ID para rastreamento
- Sem métricas customizadas

### 1.3 Gaps Identificados
| Gap | Prioridade | Impacto |
|-----|------------|---------|
| Falta configuração de níveis por ambiente | Alta | Dificulta diagnóstico em produção |
| Ausência de Correlation ID | Alta | Impossibilita rastreamento de requisições |
| Sem métricas Micrometer | Média | Falta de observabilidade |
| Health checks basic apenas | Média | Visibilidade limitada de componentes |

## 2. Convenções de Logging

### 2.1 Padrões de Uso de Logger

#### Classes de Serviço
```java
@Service
@RequiredArgsConstructor
public class ExemploService {

    private static final Logger LOG = LoggerFactory.getLogger(ExemploService.class);
    // OU usando Lombok:
    // @Slf4j
    // private static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(ExemploService.class);

    public void metodoExemplo(String parametro) {
        LOG.info("Iniciando processamento para parâmetro: {}", parametro);
        try {
            // lógica de negócio
            LOG.debug("Debug info: {}", detalhes);
        } catch (Exception e) {
            LOG.error("Erro ao processar parâmetro: {}", parametro, e);
        }
    }
}
```

#### Classes de Controller
```java
@RestController
@RequestMapping("/api/v1/exemplo")
@RequiredArgsConstructor
public class ExemploController {

    private final ExemploService service;

    @GetMapping("/{id}")
    public ResponseEntity<ExemploDTO> buscarPorId(@PathVariable String id) {
        LOG.debug("Requisição GET para buscar ID: {}", id);
        return ResponseEntity.ok(service.buscarPorId(id));
    }
}
```

### 2.2 Níveis de Log por Cenário

| Cenário | Nível Recomendado | Exemplo |
|---------|-------------------|---------|
| Entrada/saída de métodos | DEBUG | `log.debug("Método {} iniciado", methodName)` |
| Operações de negócio | INFO | `log.info("Entidade {} criada com sucesso", entityId)` |
| Validações e regras de negócio | WARN | `log.warn("Tentativa de acesso negado para usuário {}", userId)` |
| Erros de sistema | ERROR | `log.error("Falha na operação de banco de dados", exception)` |
| Queries SQL debug | TRACE | `log.trace("Executando query: {}", sql)` |
| Configurações sensíveis | NEVER | Nunca logar senhas, tokens, etc. |

### 2.3 Structured Logging (Logback)

```xml
<!-- logback-spring.xml -->
<configuration>
    <property name="LOG_PATTERN" value="%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n"/>
    <property name="CORRELATION_PATTERN" value="%d{yyyy-MM-dd HH:mm:ss.SSS} [%X{correlationId}] %-5level %logger{36} - %msg%n"/>

    <appender name="CONSOLE" class="ch.qos.logback.core.ConsoleAppender">
        <encoder>
            <pattern>${CORRELATION_PATTERN}</pattern>
        </encoder>
    </appender>

    <appender name="FILE" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <file>logs/ia-core-apps.log</file>
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <fileNamePattern>logs/ia-core-apps.%d{yyyy-MM-dd}.log</fileNamePattern>
            <maxHistory>30</maxHistory>
            <totalSizeCap>1GB</totalSizeCap>
        </rollingPolicy>
        <encoder>
            <pattern>${CORRELATION_PATTERN}</pattern>
        </encoder>
    </appender>
</configuration>
```

## 3. Configuração por Ambiente

### 3.1 application.yml - Configuração Base

```yaml
logging:
  level:
    root: INFO
    com.ia.core: DEBUG
    org.springframework: WARN
    org.hibernate: ERROR
  pattern:
    console: "%d{yyyy-MM-dd HH:mm:ss.SSS} [%X{correlationId}] %-5level %logger{36} - %msg%n"
  file:
    name: logs/ia-core-apps.log
  file:
    max-size: 100MB
    max-history: 30
```

### 3.2 application-dev.yml

```yaml
logging:
  level:
    root: DEBUG
    com.ia.core: DEBUG
    org.springframework.web: DEBUG
    org.hibernate.SQL: DEBUG
  file:
    name: logs/ia-core-apps-dev.log
```

### 3.3 application-homolog.yml

```yaml
logging:
  level:
    root: INFO
    com.ia.core: INFO
    org.springframework.web: INFO
    org.hibernate.SQL: WARN
  file:
    name: logs/ia-core-apps-homolog.log
```

### 3.4 application-prod.yml

```yaml
logging:
  level:
    root: WARN
    com.ia.core: INFO
    org.springframework.web: WARN
    org.hibernate.SQL: ERROR
  pattern:
    console: "%d{yyyy-MM-dd HH:mm:ss.SSS} [%X{correlationId}] %-5level %logger{36} - %msg%n"
  file:
    name: logs/ia-core-apps-prod.log
    max-size: 200MB
    max-history: 90
```

## 4. Correlation ID (Rastreamento)

### 4.1 Filter para Geração de Correlation ID

```java
@Component
@RequiredArgsConstructor
public class CorrelationIdFilter extends OncePerRequestFilter {

    private static final String CORRELATION_ID_HEADER = "x-correlation-id";
    private static final String CORRELATION_ID_MDC_KEY = "correlationId";

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        
        String correlationId = getOrGenerateCorrelationId(request);
        
        MDC.put(CORRELATION_ID_MDC_KEY, correlationId);
        
        response.addHeader(CORRELATION_ID_HEADER, correlationId);
        
        try {
            filterChain.doFilter(request, response);
        } finally {
            MDC.remove(CORRELATION_ID_MDC_KEY);
        }
    }

    private String getOrGenerateCorrelationId(HttpServletRequest request) {
        String correlationId = request.getHeader(CORRELATION_ID_HEADER);
        if (StringUtils.hasText(correlationId)) {
            return correlationId;
        }
        return UUID.randomUUID().toString().replace("-", "");
    }
}
```

### 4.2 RestTemplate Interceptor

```java
@Component
@RequiredArgsConstructor
public class CorrelationIdInterceptor implements ClientHttpRequestInterceptor {

    private static final String CORRELATION_ID_HEADER = "x-correlation-id";

    @Override
    public ClientHttpResponse intercept(HttpRequest request,
                                         byte[] body,
                                         ClientHttpRequestExecution execution)
            throws IOException {
        
        String correlationId = MDC.get("correlationId");
        
        if (StringUtils.hasText(correlationId)) {
            request.getHeaders().add(CORRELATION_ID_HEADER, correlationId);
        }
        
        return execution.execute(request, body);
    }
}
```

### 4.3 WebClient Configurer

```java
@Configuration
@RequiredArgsConstructor
public class WebClientConfig {

    @Bean
    public WebClient.Builder correlationIdWebClientBuilder() {
        return WebClient.builder()
                .filter((request, next) -> {
                    String correlationId = MDC.get("correlationId");
                    if (StringUtils.hasText(correlationId)) {
                        request.headers(httpHeaders -> 
                            httpHeaders.add("x-correlation-id", correlationId));
                    }
                    return next.exchange(request);
                });
    }
}
```

## 5. Métricas com Micrometer

### 5.1 Dependências a Adicionar

```xml
<dependency>
    <groupId>io.micrometer</groupId>
    <artifactId>micrometer-registry-prometheus</artifactId>
</dependency>
<dependency>
    <groupId>io.micrometer</groupId>
    <artifactId>micrometer-registry-graphite</artifactId>
</dependency>
```

### 5.2 Métricas Customizadas

```java
@Configuration
@RequiredArgsConstructor
public class MetricsConfig {

    private final MeterRegistry meterRegistry;

    @Bean
    public Counter apiRequestCounter() {
        return Counter.builder("ia_core_api_requests_total")
                .description("Total de requisições à API")
                .tag("method", "GET")
                .tag("endpoint", "/api/v1")
                .register(meterRegistry);
    }

    @Bean
    public Timer apiResponseTimer() {
        return Timer.builder("ia_core_api_response_time")
                .description("Tempo de resposta da API")
                .publishPercentiles(0.5, 0.95, 0.99)
                .register(meterRegistry);
    }

    @Bean
    public Gauge activeSessionsGauge(ChatSessionService chatSessionService) {
        return Gauge.builder("ia_core_active_sessions", 
                chatSessionService, 
                ChatSessionService::getActiveSessionCount)
                .description("Número de sessões ativas")
                .register(meterRegistry);
    }
}
```

### 5.3 Métricas de Serviço

```java
@Service
@RequiredArgsConstructor
public class ExemploService {

    private final MeterRegistry meterRegistry;
    private final Timer responseTimer;

    public EntityDTO processar(Entity entity) {
        return responseTimer.record(() -> {
            meterRegistry.counter("ia_core_processamentos_total", 
                    "tipo", entity.getTipo())
                .increment();
            
            LOG.info("Processando entidade: {}", entity.getId());
            // lógica de negócio
        });
    }
}
```

## 6. Health Checks Customizados

### 6.1 Health Indicator para Banco de Dados

```java
@Component
@RequiredArgsConstructor
public class DatabaseHealthIndicator extends AbstractHealthIndicator {

    private final DataSource dataSource;

    @Override
    protected void doHealthCheck(Health.Builder builder) throws Exception {
        try (Connection connection = dataSource.getConnection()) {
            boolean valid = connection.isValid(5);
            if (valid) {
                builder.up()
                    .withDetail("database", "HSQLDB/MySQL/PostgreSQL")
                    .withDetail("valid", true);
            } else {
                builder.down()
                    .withDetail("database", "Connection invalid");
            }
        }
    }
}
```

### 6.2 Health Indicator para Vector Store

```java
@Component
@RequiredArgsConstructor
public class VectorStoreHealthIndicator extends AbstractHealthIndicator {

    private final VectorStoreOperations vectorStoreOperations;

    @Override
    protected void doHealthCheck(Health.Builder builder) throws Exception {
        try {
            int documentCount = vectorStoreOperations.getDocumentCount();
            builder.up()
                .withDetail("type", "VectorStore")
                .withDetail("documentCount", documentCount);
        } catch (Exception e) {
            builder.down()
                .withDetail("type", "VectorStore")
                .withDetail("error", e.getMessage());
        }
    }
}
```

### 6.3 Health Indicator para LLM Service

```java
@Component
@RequiredArgsConstructor
public class LlmServiceHealthIndicator extends AbstractHealthIndicator {

    private final ChatModel chatModel;

    @Override
    protected void doHealthCheck(Health.Builder builder) throws Exception {
        try {
            // Teste simples de conectividade com o modelo
            builder.up()
                .withDetail("type", "LLM")
                .withDetail("model", "llama3.2-vision")
                .withDetail("provider", "ollama");
        } catch (Exception e) {
            builder.down()
                .withDetail("type", "LLM")
                .withDetail("error", e.getMessage());
        }
    }
}
```

### 6.4 Configuração do Actuator

```yaml
management:
  endpoints:
    web:
      exposure:
        include: health,info,metrics,prometheus
  endpoint:
    health:
      show-details: always
      probes:
        enabled: true
    prometheus:
      enabled: true
  metrics:
    tags:
      application: ia-core-apps
      environment: ${APP_ENV:dev}
    distribution:
      percentiles-histogram:
        http.server.requests: true
      percentiles:
        http.server.requests: 0.5, 0.9, 0.95, 0.99
```

## 7. Plano de Implementação

### Fase 1: Configuração Básica (1 dia)
- [ ] Adicionar dependências do Micrometer ao pom.xml
- [ ] Criar arquivo `logback-spring.xml` com configuração base
- [ ] Configurar níveis de log por ambiente no `application-*.yml`
- [ ] Implementar `CorrelationIdFilter`

### Fase 2: Logging Estruturado (1 dia)
- [ ] Criar `CorrelationIdInterceptor` para RestTemplate
- [ ] Configurar MDC em todos os serviços existentes
- [ ] Adicionar logging consistente nos controllers
- [ ] Criar exemplos de logging em serviços

### Fase 3: Métricas (1 dia)
- [ ] Implementar `MetricsConfig` com contadores e timers
- [ ] Adicionar métricas de tempo de resposta
- [ ] Criar métricas customizadas por domínio
- [ ] Configurar exportação para Prometheus

### Fase 4: Health Checks (1 dia)
- [ ] Implementar `DatabaseHealthIndicator`
- [ ] Implementar `VectorStoreHealthIndicator`
- [ ] Implementar `LlmServiceHealthIndicator`
- [ ] Configurar endpoints do Actuator

## 8. Boas Práticas

### 8.1 O que NÃO fazer
- ❌ Logar dados sensíveis (senhas, tokens, dados pessoais)
- ❌ Usar `System.out.println` ou `System.err.println`
- ❌ Criar logs muito verbosos em produção
- ❌ Logar exceptions sem contexto
- ❌ Usar `String concatenation` em logs de produção

### 8.2 O que FAZER
- ✅ Usar placeholders `{}` para interpolação
- ✅ Incluir correlation ID em todos os logs
- ✅ Logar exceptions com stack trace completo
- ✅ Usar níveis apropriados (DEBUG em dev, WARN em prod)
- ✅ Manter consistência na estrutura de logs
- ✅ Configurar rotação de arquivos de log

## 9. Dashboard de Monitoramento

### 9.1 Métricas Principais para Prometheus

```
# HELP ia_core_api_requests_total Total de requisições à API
# TYPE ia_core_api_requests_total counter
ia_core_api_requests_total{method="GET",endpoint="/api/v1/users",status="200"} 1234

# HELP ia_core_api_response_time Tempo de resposta da API
# TYPE ia_core_api_response_time summary
ia_core_api_response_time{quantile="0.5"} 0.025
ia_core_api_response_time{quantile="0.95"} 0.150

# HELP ia_core_active_sessions Número de sessões ativas
# TYPE ia_core_active_sessions gauge
ia_core_active_sessions 42
```

### 9.2 Alertas Recomendados

| Alerta | Condição | Severidade |
|--------|----------|------------|
| API Down | `up == 0` | critical |
| Alta Latência | `http_server_requests_seconds > 2s` | warning |
| Alto Error Rate | `http_server_requests_seconds_status{status=~"5.."} > 0.05` | critical |
| Sessions Estagnadas | `active_sessions == 0` por 5min | warning |

## 10. Referências

- [Spring Boot Actuator](https://docs.spring.io/spring-boot/docs/current/reference/html/actuator.html)
- [Micrometer Metrics](https://micrometer.io/docs)
- [Logback Configuration](https://logback.qos.ch/manual/configuration.html)
- [Structured Logging with Logback](https://github.com/logfellow/logstash-logback-encoder)
- [RFC 7807 - Problem Details](https://datatracker.ietf.org/doc/html/rfc7807)

---

**Última Atualização:** 2026-02-10

**Versão:** 1.0.0
