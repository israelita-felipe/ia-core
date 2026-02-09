# FASE 4: Performance e Otimização

## Objetivo
Otimizar consultas de banco de dados, adicionar cache estratégico e melhorar performance geral da aplicação.

---

## 4.1 Análise de Consultas N+1 e Fetch Joins

### Problema Identificado
Múltiplos repositories podem sofrem de problema N+1 ao carregar entidades relacionadas.

### Serviços a Otimizar

#### 4.1.1 ComandoSistemaService
**Arquivo:** `ia-core/ia-core-llm-service/src/main/java/com/ia/core/llm/service/comando/ComandoSistemaService.java`

**Otimizações:**
1. Adicionar `@NamedEntityGraph` em `ComandoSistema`
2. Implementar `EntityGraph` em métodos de busca
3. Adicionar `LEFT JOIN FETCH` para relacionamentos

**Refatoração Proposta:**
```java
@NamedEntityGraph(
  name = "ComandoSistema.withTemplate",
  attributeNodes = @NamedAttributeNode("template")
)
@Entity
public class ComandoSistema { ... }

// Repository
public interface ComandoSistemaRepository extends JpaRepository<ComandoSistema, Long> {
  
  @EntityGraph("ComandoSistema.withTemplate")
  Optional<ComandoSistema> findById(Long id);
  
  @EntityGraph(type = EntityGraphType.FETCH, 
               attributePaths = {"template", "parametros"})
  List<ComandoSistema> findAll();
}
```

#### 4.1.2 TemplateService
**Arquivo:** `ia-core/ia-core-llm-service/src/main/java/com/ia/core/llm/service/template/TemplateService.java`

**Otimizações:**
1. Adicionar `EntityGraph` para carregar parâmetros
2. Criar métodos específicos para diferentes cenários de carga

#### 4.1.3 SchedulerConfigService
**Arquivo:** `ia-core/ia-core-quartz-service/src/main/java/com/ia/core/quartz/service/SchedulerConfigService.java`

**Otimizações:**
1. Carregar triggers junto com scheduler config
2. Implementar paginação para listas grandes

---

## 4.2 Configuração de Cache

### 4.2.1 Cache com Caffeine/Redis

**Dependência já existente:** `@EnableCache` em `ia-core-model`

**Configuração por Entidade:**

#### ComandoSistema (Alta Frequência)
```java
@Cacheable(value = "comandos", key = "#id")
@CacheEvict(value = "comandos", allEntries = true)
public class ComandoSistemaService { ... }
```

#### Template (Média Frequência)
```java
@Cacheable(value = "templates", key = "#id")
@CacheEvict(value = "templates", allEntries = true)
public class TemplateService { ... }
```

#### SchedulerConfig (Baixa Frequência)
```java
@Cacheable(value = "schedulers", key = "#id")
@CachePut(value = "schedulers", key = "#result.id")
public class SchedulerConfigService { ... }
```

### 4.2.2 Configuração de Cache distribuído (Opcional)

**Arquivo:** `ia-core/ia-core-config/src/main/resources/cache.yml`

```yaml
cache:
  default:
    expireAfterWrite: 3600s
    maximumSize: 1000
  comandos:
    expireAfterWrite: 1800s
    maximumSize: 500
  templates:
    expireAfterWrite: 3600s
    maximumSize: 200
```

---

## 4.3 Otimização de Queries

### 4.3.1 Pageable para Listas Grandes

**Impacto:** Reduzir carga de memória em listagens

#### Implementação em Todos os Services
```java
public Page<ComandoSistema> findAll(Pageable pageable) {
  return comandoSistemaRepository.findAll(pageable);
}

public Page<ComandoSistema> findByFilter(ComandoSistemaSearchRequest filter, Pageable pageable) {
  return comandoSistemaRepository.findAll(specification, pageable);
}
```

### 4.3.2 Projeções para Consultas Parciais

**Evitar carregar entidades completas desnecessariamente:**

```java
// Projeção para ListView (apenas dados necessários)
public interface ComandoSistemaSummary {
  Long getId();
  String getNome();
  String getFinalidade();
}

// Repository
public interface ComandoSistemaRepository extends JpaRepository<ComandoSistema, Long> {
  List<ComandoSistemaSummary> findAllProjectedBy();
}
```

---

## 4.4 Async Processing

### 4.4.1 Queries Assíncronas com `@Async`

**Arquivo:** `ia-core/ia-core-config/src/main/java/com/ia/core/config/AsyncConfig.java`

```java
@Configuration
@EnableAsync
public class AsyncConfig {
  
  @Bean("taskExecutor")
  public Executor taskExecutor() {
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    executor.setCorePoolSize(5);
    executor.setMaxPoolSize(10);
    executor.setQueueCapacity(25);
    executor.setThreadNamePrefix("ia-core-async-");
    executor.initialize();
    return executor;
  }
}

// Service
@Async("taskExecutor")
public CompletableFuture<List<ComandoSistema>> findAllAsync() {
  return CompletableFuture.completedFuture(comandoSistemaRepository.findAll());
}
```

### 4.4.2 Processamento de Lotes

```java
@Async("taskExecutor")
public void processarLote(List<ComandoSistema> comandos) {
  comandos.parallelStream().forEach(this::processar);
}
```

---

## 4.5 Índices de Banco de Dados

### 4.5.1 Script Flyway para Índices

**Arquivo:** `ia-core/ia-core-llm-service/src/main/resources/db/migrations/V4__PERFORMANCE_INDEXES.sql`

```sql
-- Índices para consultas frequentes
CREATE INDEX idx_comando_sistema_finalidade ON tb_comando_sistema(finalidade);
CREATE INDEX idx_template_nome ON tb_template(nome);
CREATE INDEX idx_axioma_tipo ON tb_axioma(tipo_axioma);

-- Índices para pesquisas textuais
CREATE INDEX idx_comando_sistema_nome_hash ON tb_comando_sistema USING HASH(nome);

-- Índices para ordenação frequente
CREATE INDEX idx_scheduler_config_criado_em ON tb_scheduler_config(criado_em DESC);
```

---

## 4.6 Auditoria de Performance

### 4.6.1 AOP para Métricas

```java
@Aspect
@Component
public class PerformanceAspect {
  
  @Around("execution(* com.ia.core..service.*.*(..))")
  public Object measureTime(ProceedingJoinPoint joinPoint) throws Throwable {
    StopWatch stopWatch = new StopWatch();
    stopWatch.start();
    
    try {
      return joinPoint.proceed();
    } finally {
      stopWatch.stop();
      log.info("{} executado em {}ms", 
               joinPoint.getSignature(), 
               stopWatch.getTotalTimeMillis());
    }
  }
}
```

---

## Ordem de Execução - FASE 4

| Passo | Atividade | Prioridade | Impacto |
|-------|-----------|------------|---------|
| 4.1 | Análise e correção de N+1 (EntityGraph) | ALTA | Alto |
| 4.2 | Configuração de cache básico | ALTA | Alto |
| 4.3 | Pageable em todos os services | MÉDIA | Médio |
| 4.4 | Async processing | MÉDIA | Médio |
| 4.5 | Índices de banco | BAIXA | Alto |
| 4.6 | Auditoria de performance | BAIXA | Baixo |

---

## Dependências Necessárias

```xml
<!-- Caffeine Cache (já incluso via spring-boot-starter-cache) -->
<dependency>
  <groupId>com.github.ben-manes.caffeine</groupId>
  <artifactId>caffeine</artifactId>
</dependency>

<!-- Actuator para métricas -->
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>

<!-- Micrometer para tracing -->
<dependency>
  <groupId>io.micrometer</groupId>
  <artifactId>micrometer-tracing-bridge-otel</artifactId>
</dependency>
```

---

## Validação

Após implementação, validar com:
1. Queries com EXPLAIN ANALYZE
2. JMeter para testes de carga
3. Actuator `/metrics` para monitoramento
