# ADR-020: Usar SuperBuilder com Lombok para Entidades JPA

## Status

✅ Aceito

## Contexto

O projeto precisa de uma forma padronizada de criar instâncias de entidades JPA que:
- Suporte herança de campos da `BaseEntity`
- Permita builder fluente para testes e criação de objetos
- Seja compatível com JPA e Hibernate
- Funcione com `toBuilder = true` para cópia de entidades

## Decisão

Usar **`@SuperBuilder`** do Lombok ao invés de `@Builder` padrão para entidades JPA que herdam de `BaseEntity`.

## Detalhes

### BaseEntity com SuperBuilder

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
  private Long version = HasVersion.DEFAULT_VERSION;

  @PrePersist
  public void generateIdIfAbsent() {
    if (this.id == null) {
      this.id = TSID.Factory.getTsid4096().toLong();
    }
  }
}
```

### Entidade Filha

```java
@Entity
@Table(name = "pessoa")
@SuperBuilder(toBuilder = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pessoa extends BaseEntity {

  @Column(length = 100, nullable = false)
  private String nome;

  @Enumerated(EnumType.STRING)
  private TipoPessoa tipo;

  @OneToMany(mappedBy = "pessoa", cascade = CascadeType.ALL)
  @Builder.Default
  private List<Contato> contatos = new ArrayList<>();
}
```

### Uso com Builder

```java
// Criação
Pessoa pessoa = Pessoa.builder()
    .nome("João Silva")
    .tipo(TipoPessoa.FISICA)
    .build();

// Cópia com toBuilder
Pessoa atualizada = pessoa.toBuilder()
    .nome("João Silva Santos")
    .build();
```

### Por que SuperBuilder ao invés de Builder?

| Característica | `@Builder` | `@SuperBuilder` |
|----------------|------------|-----------------|
| Herança de campos | ❌ Não suporta | ✅ Suporta |
| Campos da BaseEntity | Não incluídos | Incluídos automaticamente |
| toBuilder | Sim | Sim |
| Compatibilidade JPA | Sim | Sim |

### Configuração com MapStruct

```xml
<!-- pom.xml - Ordem dos annotation processors -->
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-compiler-plugin</artifactId>
    <configuration>
        <annotationProcessorPaths>
            <path>
                <groupId>org.projectlombok</groupId>
                <artifactId>lombok</artifactId>
            </path>
            <path>
                <groupId>org.mapstruct</groupId>
                <artifactId>mapstruct-processor</artifactId>
            </path>
        </annotationProcessorPaths>
    </configuration>
</plugin>
```

## Consequências

### Positivas
- ✅ Builder fluente com herança completa
- ✅ `toBuilder` para cópia imutável de entidades
- ✅ Compatível com JPA/Hibernate
- ✅ Campos da BaseEntity incluídos automaticamente

### Negativas
- ❌ Requer Lombok 1.18.16+
- ❌ Mais complexo que `@Builder` simples
- ❌ DTOs simples podem usar `@Builder` normal

## Status de Implementação

✅ **COMPLETO**

- `@SuperBuilder(toBuilder = true)` em [`BaseEntity`](../../ia-core-model/src/main/java/com/ia/core/model/BaseEntity.java)
- Todas as entidades usam SuperBuilder

## Data

2024-01-12

## Revisores

- Team Lead
- Architect

## Referências

1. **Lombok @SuperBuilder**
   - URL: https://projectlombok.org/features/SuperBuilder
   - Documentação oficial

2. **Baeldung - Lombok SuperBuilder**
   - URL: https://www.baeldung.com/lombok-builder-superbuilder
   - Comparação entre Builder e SuperBuilder
