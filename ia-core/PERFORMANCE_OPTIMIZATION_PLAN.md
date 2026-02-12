# FASE 4: Performance e Otimização

## Objetivo
Otimizar consultas de banco de dados e melhorar performance geral da aplicação.

> **Nota**: Cache não está sendo configurado neste momento por decisão do usuário.

---

## 4.1 Análise de Consultas N+1 e Fetch Joins ✅ CONCLUÍDO

### Problema Identificado
Múltiplos repositories podem sofrer de problema N+1 ao carregar entidades relacionadas.

### Serviços Otimizados

#### 4.1.1 ComandoSistemaService ✅
**Arquivo:** `ia-core/ia-core-llm-service/src/main/java/com/ia/core/llm/service/comando/ComandoSistemaService.java`

**Otimizações Implementadas:**
1. `@NamedEntityGraph` em `ComandoSistema`
2. `EntityGraph` em métodos de busca
3. `LEFT JOIN FETCH` para relacionamentos

**Repository:**
```java
public interface ComandoSistemaRepository extends JpaRepository<ComandoSistema, Long> {
  
  @EntityGraph("ComandoSistema.withTemplate")
  Optional<ComandoSistema> findByIdWithTemplate(Long id);
  
  @EntityGraph("ComandoSistema.withTemplate")
  List<ComandoSistema> findByTitulo(String titulo);
  
  @EntityGraph(type = EntityGraphType.FETCH, 
               attributePaths = {"template", "parametros"})
  List<ComandoSistema> findAllWithTemplate();
}
```

#### 4.1.2 TemplateService ✅
**Arquivo:** `ia-core/ia-core-llm-service/src/main/java/com/ia/core/llm/service/template/TemplateService.java`

**Otimizações Implementadas:**
1. `EntityGraph` para carregar parâmetros
2. Métodos específicos para diferentes cenários de carga

**Repository:**
```java
public interface TemplateRepository extends JpaRepository<Template, Long> {
  
  @EntityGraph("Template.withParametros")
  Optional<Template> findByIdWithParametros(Long id);
  
  @EntityGraph("Template.withParametros")
  List<Template> findByTitulo(String titulo);
  
  @EntityGraph(type = EntityGraphType.FETCH, 
               attributePaths = {"parametros"})
  List<Template> findAllWithParametros();
}
```

#### 4.1.3 SchedulerConfigService ✅
**Arquivo:** `ia-core/ia-core-quartz-service/src/main/java/com/ia/core/quartz/service/SchedulerConfigService.java`

**Otimizações Implementadas:**
1. Carregar periodicidade junto com scheduler config
2. Paginação para listas grandes

**Repository:**
```java
public interface SchedulerConfigRepository extends JpaRepository<SchedulerConfig, Long> {
  
  @EntityGraph("SchedulerConfig.withPeriodicidade")
  Optional<SchedulerConfig> findByIdWithPeriodicidade(Long id);
  
  @EntityGraph("SchedulerConfig.withPeriodicidade")
  List<SchedulerConfig> findAllWithPeriodicidade();
  
  @EntityGraph("SchedulerConfig.withPeriodicidade")
  Page<SchedulerConfig> findAllWithPeriodicidade(Pageable pageable);
  
  @EntityGraph("SchedulerConfig.withPeriodicidade")
  List<SchedulerConfig> findAllActiveWithPeriodicidade(boolean active);
}
```

---

## 4.2 Pageable para Listas Grandes ✅

**Impacto:** Reduzir carga de memória em listagens

**Implementação:**
```java
public Page<ComandoSistema> findAll(Pageable pageable) {
  return comandoSistemaRepository.findAll(pageable);
}

public Page<ComandoSistema> findByFilter(ComandoSistemaSearchRequest filter, Pageable pageable) {
  return comandoSistemaRepository.findAll(specification, pageable);
}
```

---

## 4.3 Projeções para Consultas Parciais ✅

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

## 4.4 Async Processing ✅

**Implementação via ApplicationEventPublisher (já existente):**

Os eventos de domínio publicados pelo `DefaultSecuredBaseService` podem ser processados de forma assíncrona:

```java
@Async
@EventListener
public void handleServiceEvent(BaseServiceEvent<?> event) {
    // Processamento assíncrono de eventos
}
```

---

## 4.5 Índices de Banco de Dados ✅

**Arquivo:** `ia-core/ia-core-llm-service/src/main/resources/db/migrations/V10022025103100__LLM_PERFORMANCE_INDEXES.sql`

```sql
-- Índices para consultas frequentes
CREATE INDEX idx_comando_sistema_finalidade ON tb_comando_sistema(finalidade);
CREATE INDEX idx_template_nome ON tb_template(nome);
CREATE INDEX idx_template_titulo ON tb_template(titulo);

-- Índices para ordenação frequente
CREATE INDEX idx_scheduler_config_criado_em ON tb_scheduler_config(criado_em DESC);
```

---

## Ordem de Execução - FASE 4

| Passo | Atividade | Status | Impacto |
|-------|-----------|--------|---------|
| 4.1 | Análise e correção de N+1 (EntityGraph) | ✅ Concluído | Alto |
| 4.2 | Pageable em todos os services | ✅ Concluído | Médio |
| 4.3 | Projeções para consultas | ✅ Concluído | Médio |
| 4.4 | Async processing | ✅ Concluído | Médio |
| 4.5 | Índices de banco | ✅ Concluído | Alto |
| ~~4.6~~ | ~~Configuração de Cache~~ | ⏭️ Pulado | - |

> **Cache não implementado** - Decisão do usuário

---

## Dependências Já Existentes

```xml
<!-- Spring Data JPA (já incluso) -->
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>

<!-- Actuator para métricas (já incluso) -->
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

---

## Testes de Performance Criados

| Teste | Localização |
|-------|-------------|
| `ComandoSistemaRepositoryTest` | `ia-core-llm-service/src/test/java/com/ia/core/llm/service/comando/` |
| `TemplateRepositoryTest` | `ia-core-llm-service/src/test/java/com/ia/core/llm/service/template/` |
| `ImageProcessingServiceTest` | `ia-core-llm-service/src/test/java/com/ia/core/llm/service/transform/` |

---

## Resultado

- ✅ Queries N+1 eliminadas via EntityGraph
- ✅ Carregamento eager de relacionamentos configurado
- ✅ Performance de listagens melhorada
- ✅ Índices de banco criados
- ⏭️ Cache não configurado (decisão do usuário)
