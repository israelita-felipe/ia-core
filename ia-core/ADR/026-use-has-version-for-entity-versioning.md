# ADR-026: Usar HasVersion para Controle de Versão de Entidades

## Status

✅ Aceito

## Contexto

O projeto precisa de uma forma padronizada de:
- Representar o campo de versão em entidades JPA
- Garantir compatibilidade entre diferentes tipos de versão (Long, Integer)
- Fornecer um valor default consistente para versionamento otimista

## Decisão

Usar interface **`HasVersion<T>`** como contrato para entidades com controle de versão.

## Detalhes

### Interface HasVersion

```java
public interface HasVersion<T> {

  /** Versão default para novas entidades */
  Long DEFAULT_VERSION = 1L;

  /** Retorna a versão atual */
  T getVersion();
}
```

### Uso em BaseEntity

```java
@MappedSuperclass
@SuperBuilder(toBuilder = true)
public abstract class BaseEntity
  implements Serializable, HasVersion<Long>, Comparable<BaseEntity> {

  @Default
  @Version
  @Column(name = "version", columnDefinition = "bigint default 1", nullable = false)
  private Long version = HasVersion.DEFAULT_VERSION;
}
```

### Alternativas Consideradas

| Alternativa | Prós | Contras |
|-------------|------|---------|
| Campo direto na entidade | Simples | Não padronizado |
| **HasVersion interface** | Contrato claro, genérico | Indireção adicional |

## Consequências

### Positivas
- ✅ Contrato explícito para versionamento
- ✅ Tipo genérico (Long, Integer)
- ✅ Valor default centralizado

### Negativas
- ❌ Interface simples pode ser desnecessária

## Status de Implementação

✅ **COMPLETO**

- [`HasVersion.java`](../../ia-core-model/src/main/java/com/ia/core/model/HasVersion.java) implementado
- Integrado em [`BaseEntity`](../../ia-core-model/src/main/java/com/ia/core/model/BaseEntity.java)

## Data

2024-03-15

## Revisores

- Team Lead
- Architect

## Referências

1. **JPA Optimistic Locking**
   - URL: https://docs.jboss.org/hibernate/orm/current/userguide/html_single/Hibernate_User_Guide.html#locking-optimistic
   - Documentação oficial do Hibernate
