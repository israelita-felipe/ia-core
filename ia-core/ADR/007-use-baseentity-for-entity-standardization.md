# ADR-007: Usar BaseEntity para Padronização de Entidades

## Status

✅ Aceito

## Contexto

O projeto apresentava inconsistências entre entidades JPA, com cada entidade definindo seus próprios campos de auditoria (id, version, createdAt, etc.) de formas diferentes.

## Decisão

Usar **BaseEntity** como classe base abstrata para todas as entidades JPA do domínio.

## Detalhes

### Classe BaseEntity (Atualizada)

```java
@MappedSuperclass
@SuperBuilder(toBuilder = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseEntity
  implements Serializable, HasVersion<Long>, Comparable<BaseEntity> {

  @Id
  private Long id;

  @Default
  @Version
  @Column(name = "version", columnDefinition = "bigint default 1", nullable = false)
  private Long version = HasVersion.DEFAULT_VERSION;

  @PrePersist
  public void generateIdIfAbsent() {
    if (this.id == null) {
      this.id = TSID.Factory.getTsid4096().toLong();
    }
  }

  @Override
  public int compareTo(BaseEntity o) {
    return Comparator.nullsFirst(Comparator.naturalOrder())
        .compare(this.id, o.id);
  }
}
```

### Mudanças Importantes

1. **TSID para IDs**: Substituído `@GeneratedValue` por TSID (`TSID.Factory.getTsid4096()`)
2. **SuperBuilder**: Adicionado `@SuperBuilder(toBuilder = true)` para suporte a herança
3. **HasVersion**: Interface para controle de versão padronizado
4. **Comparable**: Implementado para ordenação natural por ID

### Ver ADRs Relacionados

- [ADR-015: Usar TSID para Identidade](015-use-tsid-for-entity-identity.md)
- [ADR-020: Usar SuperBuilder para JPA](020-use-superbuilder-for-jpa-entities.md)
- [ADR-026: Usar HasVersion para Versionamento](026-use-has-version-for-entity-versioning.md)

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

## Referências

1. **Jakarta Persistence Specification - MappedSuperclass**
   - URL: https://jakarta.ee/specifications/persistence/3.1/jakarta-persistence-spec-3.1.html#a1650
   - Especificação oficial sobreMappedSuperclass

2. **Baeldung - JPA MappedSuperclass**
   - URL: https://www.baeldung.com/hibernate-inheritance#mapped-superclass
   - Guia sobre herança de entidades comMappedSuperclass

3. **Vlad Mihalcea - BaseEntity**
   - URL: https://vladmihalcea.com/spring-boot-jpa-base-entity/
   - Implementação de BaseEntity com Spring Boot

4. **Baeldung - JPA Auditing**
   - URL: https://www.baeldung.com/spring-data-jpa-auditing
   - Auditoria automática com Spring Data JPA

5. **Hibernate ORM - Entity**
   - URL: https://docs.jboss.org/hibernate/orm/current/userguide/html_single/Hibernate_User_Guide.html#entity
   - Documentação sobre entidades JPA/Hibernate
