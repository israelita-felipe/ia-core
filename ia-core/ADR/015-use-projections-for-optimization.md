# ADR-015: Usar Spring Data Projections para Otimização de Queries

## Status

✅ Aceito

## Contexto

O projeto apresenta queries que retornam entidades completas quando apenas alguns campos são necessários. Isso causa:
- Transferência de dados desnecessários pela rede
- Overhead de serialização/deserialização
- Queries mais lentas em tabelas com muitos campos

## Decisão

Usar **Spring Data Projections** com @Query explícito para otimizar queries específicas que não precisam da entidade completa.

## Detalhes

### Abordagem de Implementação

A projeção será implementada diretamente nos repositories específicos usando @Query com SELECT de campos específicos.

### Implementação no Repository

```java
// Interface de projection
public interface EventoSummary extends EntityProjection {
    Long getId();
    String getTitulo();
    LocalDateTime getDataInicio();
}

// Repository com método de projection
public interface EventoRepository extends BaseEntityRepository<Evento> {
    
    // Projection usando interface
    @Query("SELECT e.id as id, e.titulo as titulo, e.dataInicio as dataInicio " +
           "FROM Evento e WHERE e.ativo = true")
    List<EventoSummary> findAllSummaries();
    
    // Projection com paginação
    @Query(value = "SELECT e.id as id, e.titulo as titulo, e.dataInicio as dataInicio " +
                   "FROM Evento e WHERE e.ativo = true",
           countQuery = "SELECT count(e) FROM Evento e WHERE e.ativo = true")
    Page<EventoSummary> findAllSummaries(Pageable pageable);
}
```

### Interface Base para Projections

```java
// ia-core-model/src/main/java/com/ia/core/model/projection/EntityProjection.java
public interface EntityProjection {
    default boolean isProjection() {
        return true;
    }
}
```

### EntityGraph como Complemento

O EntityGraph (já implementado conforme ADR-006) continua sendo usado para otimizar o carregamento de relacionamentos, enquanto Projection é usado para otimizar a seleção de colunas.

| Técnica | Uso |
|---------|-----|
| **EntityGraph** | Otimizar carregamento de relacionamentos (OneToMany, ManyToOne) |
| **Projection** | Otimizar seleção de colunas da entidade principal |

## Consequências

### Positivas

- ✅ Redução significativa de dados transferidos
- ✅ Melhor performance em queries de lista
- ✅ Suporte a paginação com projections
- ✅ Compatible com Spring Data JPA
- ✅ Funciona com @Query nativa

### Negativas

- ❌ Requer criar interface para cada tipo de projection
- ❌ Não suporta métodos com lógica de negócio
- ❌ Cada projection requer método específico no repository

## Implementação Proposta

### 1. ✅ Criar interface base EntityProjection
- Local: `ia-core-model/src/main/java/com/ia/core/model/projection/EntityProjection.java`
- Status: CONCLUÍDO

### 2. Adicionar métodos de projection em repositories específicos

#### EventoRepository
```java
@Query("SELECT e.id as id, e.titulo as titulo, e.dataInicio as dataInicio FROM Evento e WHERE e.ativo = true")
List<EventoSummary> findAllSummaries();
```

#### PessoaRepository
```java
@Query("SELECT p.id as id, p.nome as nome FROM Pessoa p WHERE p.ativo = true ORDER BY p.nome")
List<PessoaSummary> findAllSummaries();
```

#### FamiliaRepository
```java
@Query("SELECT f.id as id, f.nome as nome FROM Familia f WHERE f.ativo = true ORDER BY f.nome")
List<FamiliaSummary> findAllSummaries();
```

### 3. Atualizar ListBaseService para suportar projection (opcional)

Adicionar método genérico que pode ser sobrescrito em serviços específicos.

## Status de Implementação

- [x] Criar ADR (este arquivo)
- [x] Criar interface EntityProjection no ia-core-model
- [ ] Implementar projection em EventoRepository
- [ ] Implementar projection em PessoaRepository
- [ ] Implementar projection in FamiliaRepository

## Data

2026-03-14

## Revisores

- Team Lead
- Architect
