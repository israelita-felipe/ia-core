# ADR-013: Padrões de Logging e Monitoramento

## Título

Padrões de Logging, Correlação de Requisições e Monitoramento com Spring Boot Actuator e Micrometer

## Status

✅ Aceito

## Contexto

A aplicação ia-core-apps necessita de uma estratégia padronizada de logging e monitoramento para garantir:

- **Rastreabilidade**: Capacidade de rastrear uma requisição através de todas as camadas da aplicação
- **Debuggability**: Facilidade de diagnóstico de problemas em ambiente de produção
- **Observabilidade**: Métricas e health checks para monitorar a saúde da aplicação
- **Conformidade**: Registro adequado de operações para auditoria

Decisões anteriores (ADR-001 a ADR-012) estabeleceram padrões para mapeamento, filtragem, i18n, DI, eventos de domínio, EntityGraph, BaseEntity, MVVM, paginação, nomenclatura e tratamento de exceções, mas não abordaram explicitamente logging e monitoramento.

## Decisões

### Decisão 1: Correlation ID para Rastreamento de Requisições
**Escolhido:** Implementar filtro para geração e propagação de Correlation ID

**Alternativas consideradas:**
1. Usar apenas ID de sessão
2. Gerar correlation ID no controller
3. Filtro dedicado com propagação via MDC e headers

**Justificativa:**
- Correlation ID permite rastrear uma requisição através de múltiplas camadas
- Propagação via headers permite rastreamento distribuído
- MDC (Mapped Diagnostic Context) garante disponibilidade em todos os logs
- Header `x-correlation-id` é padrão widely accepted

**Implementação:**
```java
@Component
public class CorrelationIdFilter extends OncePerRequestFilter {
    
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) {
        String correlationId = getOrGenerateCorrelationId(request);
        MDC.put("correlationId", correlationId);
        response.addHeader("x-correlation-id", correlationId);
        filterChain.doFilter(request, response);
    }
}
```

### Decisão 2: Structured Logging com Logback
**Escolhido:** Usar Logback com padrão de saída estruturado

**Padrão de saída:**
```
2024-02-10 10:30:00.123 [main] INFO  c.i.c.service.ExemploService - Inicialização completa
2024-02-10 10:30:01.456 [http-nio-8080-exec-1] DEBUG c.i.c.controller.ExemploController - GET /api/v1/exemplo/123
2024-02-10 10:30:01.789 [http-nio-8080-exec-1] INFO  c.i.c.service.ExemploService - Busca realizada id=123
```

**Com Correlation ID:**
```
2024-02-10 10:30:01.456 [http-nio-8080-exec-1] [a1b2c3d4e5f6] DEBUG c.i.c.controller.ExemploController - GET /api/v1/exemplo/123
```

**Justificativa:**
- Logback é o logger padrão do Spring Boot
- Suporte nativo a MDC
- Configuração flexível via XML
- Performance otimizada

### Decisão 3: Níveis de Log por Ambiente
**Escolhido:** Configurar níveis específicos por ambiente

| Ambiente | Root Level | Application Level |
|----------|------------|-------------------|
| dev | DEBUG | DEBUG |
| homolog | INFO | INFO |
| prod | WARN | INFO |

**Justificativa:**
- Reduz ruído em produção
- Permite debug detalhado em desenvolvimento
- Otimização de performance em produção

### Decisão 4: Métricas com Micrometer
**Escolhido:** Usar Micrometer para coleta de métricas

**Métricas principais:**
```java
// Contadores
Counter.builder("ia_core_api_requests_total")
    .description("Total de requisições")
    .tag("method", request.getMethod())
    .tag("endpoint", request.getRequestURI())
    .register(registry);

// Timers
Timer.builder("ia_core_response_time")
    .description("Tempo de resposta")
    .publishPercentiles(0.5, 0.95, 0.99)
    .register(registry);

// Gauges
Gauge.builder("ia_core_active_sessions", service, s -> s.getActiveSessionCount())
    .description("Sessões ativas")
    .register(registry);
```

**Alternativas consideradas:**
1. Métricas nativas do Spring Boot (limitado)
2. Custom Metrics API ( Spring Boot 2.x)
3. Micrometer (recomendado, vendor-neutral)

**Justificativa:**
- Abstração vendor-neutral
- Integração com Prometheus, Grafana, CloudWatch
- Suporte a tags/dimensions
- Exportação flexível

### Decisão 5: Health Checks Customizados
**Escolhido:** Implementar health indicators específicos

**Health Indicators:**
```java
@Component
public class DatabaseHealthIndicator extends AbstractHealthIndicator {
    @Override
    protected void doHealthCheck(Health.Builder builder) {
        try (Connection c = dataSource.getConnection()) {
            builder.up().withDetail("database", "connected");
        } catch (SQLException e) {
            builder.down(e);
        }
    }
}
```

**Componentes monitorados:**
- Database (JDBC connection)
- Vector Store (document count)
- LLM Service (model availability)
- Disk space

**Justificativa:**
- Visibilidade granular da saúde da aplicação
- Integração com Kubernetes/Cloud load balancers
- Early warning de problemas

### Decisão 6: Exportação para Prometheus
**Escolhido:** Habilitar endpoint `/actuator/prometheus`

**Configuração:**
```yaml
management:
  endpoints:
    web:
      exposure:
        include: health,info,metrics,prometheus
  metrics:
    tags:
      application: ia-core-apps
      environment: ${APP_ENV:dev}
```

**Métricas expostas:**
- HTTP requests (count, duration, percentiles)
- JVM memory, threads, gc
- Database connection pool
- Custom business metrics

**Justificativa:**
- Ecossistema widely adopted
- Integração com Grafana
- Query language powerful
- Alerting nativo

## Detalhamento Técnico

### Hierarquia de Classes de Logging

```
Logging (interface)
    └── Slf4jLogger (implementação via Lombok @Slf4j)
         └── MDC integration para correlation ID
```

### Fluxo de Correlation ID

```
Requisição HTTP
    ↓
CorrelationIdFilter (gera ou captura ID)
    ↓
MDC.put("correlationId", id)
    ↓
Controller (loga com ID)
    ↓
Service (loga com ID)
    ↓
Repository (loga com ID)
    ↓
Response (header x-correlation-id)
```

### Mapeamento de Níveis

| Cenário | Nível | Exemplo |
|---------|-------|---------|
| Início/fim de operação importante | INFO | "Iniciando processamento de pedido" |
| Validação de entrada | DEBUG | "Parâmetro validateInput={}" |
| Operação de negócio | INFO | "Pedido {} criado com sucesso" |
| Regra de negócio violada | WARN | "Tentativa de acesso não autorizado" |
| Erro de sistema | ERROR | "Falha na conexão com banco" |
| Query SQL | TRACE | "SELECT * FROM orders WHERE..." |

### Métricas de Negócio

| Métrica | Tipo | Descrição |
|---------|------|-----------|
| `ia_core_api_requests_total` | Counter | Total de requisições por endpoint |
| `ia_core_response_time` | Timer | Tempo de resposta da API |
| `ia_core_entities_created` | Counter | Entidades criadas por tipo |
| `ia_core_processamentos_total` | Counter | Processamentos por tipo |
| `ia_core_active_sessions` | Gauge | Sessões ativas |

## Implementação

### Dependências (pom.xml)

```xml
<!-- Micrometer Registry Prometheus -->
<dependency>
    <groupId>io.micrometer</groupId>
    <artifactId>micrometer-registry-prometheus</artifactId>
</dependency>

<!-- Lombok (já existente) -->
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <optional>true</optional>
</dependency>
```

### Arquivo de Configuração (logback-spring.xml)

```xml
<configuration>
    <property name="LOG_PATTERN" 
        value="%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n"/>
    <property name="CORRELATION_PATTERN" 
        value="%d{yyyy-MM-dd HH:mm:ss.SSS} [%X{correlationId}] %-5level %logger{36} - %msg%n"/>

    <appender name="CONSOLE" class="ch.qos.logback.core.ConsoleAppender">
        <encoder>
            <pattern>${CORRELATION_PATTERN}</pattern>
        </encoder>
    </appender>

    <appender name="ASYNC_CONSOLE" class="ch.qos.logback.classic.AsyncAppender">
        <appender-ref ref="CONSOLE"/>
        <queueSize>512</queueSize>
        <discardingThreshold>0</discardingThreshold>
    </appender>

    <root level="INFO">
        <appender-ref ref="ASYNC_CONSOLE"/>
    </root>
</configuration>
```

### Exemplo de Uso em Serviço

```java
@Service
@RequiredArgsConstructor
@Slf4j
public class ExemploService {

    private final ExemploRepository repository;

    public ExemploDTO buscarPorId(String id) {
        log.debug("Buscando entidade com id: {}", id);
        
        Exemplo entidade = repository.findById(id)
            .orElseThrow(() -> {
                log.warn("Entidade não encontrada com id: {}", id);
                return new ResourceNotFoundException("Exemplo", id);
            });
        
        log.info("Entidade encontrada com sucesso id: {}", id);
        return mapper.toDTO(entidade);
    }
}
```

## Migração

### De Logging Ad-hoc para Padronizado

```java
// Antes (não padronizado)
public void processar() {
    System.out.println("Iniciando"); // ❌
    try {
        // lógica
    } catch (Exception e) {
        e.printStackTrace(); // ❌
    }
}

// Depois (padronizado)
@Slf4j
public void processar() {
    log.info("Iniciando processamento");
    try {
        // lógica
    } catch (Exception e) {
        log.error("Erro durante processamento", e);
    }
}
```

### Adição de Correlation ID

```java
// Antes
public ResponseEntity<DTO> metodo(String id) {
    log.info("Buscando {}", id);
    // ...
}

// Depois
public ResponseEntity<DTO> metodo(String id) {
    log.info("Buscando {} - correlationId: {}", id, MDC.get("correlationId"));
    // ...
}
```

## Consequências

### Positivas
- Rastreabilidade completa de requisições
- Facilidade de debugging em produção
- Monitoramento proativo com métricas
- Integração com ferramentas padrão do mercado
- Conformidade com práticas DevOps

### Negativas
- Overhead mínimo de performance (AsyncAppender mitiga)
- Necessidade de disciplina da equipe para seguir convenções
- Curva de aprendizado inicial

## Referências

- [Spring Boot Actuator](https://docs.spring.io/spring-boot/docs/current/reference/html/actuator.html)
- [Micrometer Metrics](https://micrometer.io/docs)
- [Logback Configuration](https://logback.qos.ch/manual/configuration.html)
- [MDC - Mapped Diagnostic Context](https://logback.qos.ch/manual/mdc.html)
- [Prometheus Metrics](https://prometheus.io/docs/concepts/data_model/)

## Histórico de Revisões

| Versão | Data | Autor | Descrição |
|--------|------|-------|-----------|
| 1.0 | 2026-02-10 | Israel Araújo | Versão inicial |

---

**Nota:** Este ADR complementa os padrões estabelecidos nos ADRs anteriores e deve ser considerado em conjunto com o `LOGGING_MONITORING_PLAN.md` para implementação completa.
