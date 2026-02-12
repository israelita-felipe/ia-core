# ADR-007: Usar BaseEntity para Padronização de Entidades

## Status

✅ Aceito

## Contexto

O projeto apresentava inconsistências entre entidades JPA, com cada entidade definindo seus próprios campos de auditoria (id, version, createdAt, etc.) de formas diferentes.

## Decisão

Usar **BaseEntity** como classe base abstrata para todas as entidades JPA do domínio.

## Detalhes

### Classe BaseEntity

```java
@MappedSuperclass
public abstract class BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    private Long version;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "updated_by")
    private Long updatedBy;

    @Column(name = "ativo", nullable = false)
    private Boolean ativo = true;

    // Métodos de lifecycle
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
```

### Entidades do Biblia

Todas as entidades extendem BaseEntity:

```java
@Entity
@Table(name = "evento")
@EntityGraph("Evento.withAll")
public class Evento extends BaseEntity { ... }

@Entity
@Table(name = "pessoa")
public class Pessoa extends BaseEntity { ... }

@Entity
@Table(name = "familia")
public class Familia extends BaseEntity { ... }

@Entity
@Table(name = "despesa")
public class Despesa extends BaseEntity { ... }

@Entity
@Table(name = "receita")
public class Receita extends BaseEntity { ... }

@Entity
@Table(name = "transferencia")
public class Transferencia extends BaseEntity { ... }
```

### Implementação de Auditoria

```java
@Component
public class AuditAspect {

    @Autowired
    private SecurityContextService securityContext;

    @PrePersist
    protected void prePersist(BaseEntity entity) {
        Long userId = securityContext.getCurrentUserId();
        entity.setCreatedBy(userId);
        entity.setCreatedAt(LocalDateTime.now());
    }

    @PreUpdate
    protected void preUpdate(BaseEntity entity) {
        Long userId = securityContext.getCurrentUserId();
        entity.setUpdatedBy(userId);
        entity.setUpdatedAt(LocalDateTime.now());
    }
}
```

## Consequências

### Positivas

- ✅ Consistência entre todas as entidades
- ✅ Campos de auditoria padronizados (id, version, createdAt, updatedAt)
- ✅ Campo `ativo` para soft delete
- ✅ Suporte a versionamento otimista
- ✅ Redução de código duplicado

### Negativas

- ❌ Requer migration para entidades existentes
- ❌的所有 entidades precisam seguir o mesmo padrão

## Status de Implementação

✅ **COMPLETO**

- [`BaseEntity`](ia-core/ia-core-model/src/main/java/com/ia/core/model/BaseEntity.java) implementado
- 20+ entidades do Biblia extendem BaseEntity
- Soft delete implementado via campo `ativo`

## Data

2024-03-20

## Revisores

- Team Lead
- Architect
