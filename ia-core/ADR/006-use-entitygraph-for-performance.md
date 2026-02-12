# ADR-006: Usar EntityGraph para Otimização de Performance (N+1 Queries)

## Status

✅ Aceito

## Contexto

O projeto Biblia apresentava problemas de performance conhecidos como "N+1 queries" ao carregar entidades com relacionamentos (ex: evento com local, materiais, inscrições). Cada acesso lazy a um relacionamento gerava uma nova query no banco.

## Decisão

Usar **JPA EntityGraph** para otimizar o carregamento de relacionamentos em consultas específicas.

## Detalhes

### Implementação no Repository

```java
@EntityGraph("Evento.withLocal")
Optional<Evento> findByIdWithLocal(Long id);

@EntityGraph(type = EntityGraphType.FETCH, attributePaths = {"local", "materiais", "inscricoes"})
List<Evento> findAllWithRelations();

@EntityGraph("Evento.withAll")
Optional<Evento> findByIdWithAll(Long id);
```

### Definição de EntityGraphs

```java
@EntityGraph(value = "Evento.withAll", type = EntityGraphType.FETCH)
@NamedEntityGraph(
    name = "Evento.withAll",
    attributeNodes = {
        @NamedAttributeNode("local"),
        @NamedAttributeNode("materiais"),
        @NamedAttributeNode("inscricoes"),
        @NamedAttributeNode("organizadores")
    }
)
public class Evento { ... }
```

### Graph Hierarchy

```java
// Hierarquia de EntityGraphs para Evento:
@EntityGraph("Evento.withLocal")           // Apenas local
@EntityGraph("Evento.withMateriais")       // local + materiais
@EntityGraph("Evento.withInscricoes")      // local + inscricoes
@EntityGraph("Evento.withAll")             // Todos os relacionamentos
```

## Consequências

### Positivas

- ✅ Eliminação de N+1 queries
- ✅ Carregamento eager seletivo
- ✅ Redução de queries de 10+ para 1-2 por operação
- ✅ Documentação clara dos relacionamentos carregados
- ✅ Compatível com Spring Data JPA

### Negativas

- ❌ Necessário definir graphs para cada entidade
- ❌ Memory overhead se carregar relacionamentos desnecessários
- ❌ Requer atenção para não criar graphs muito abrangentes

## Status de Implementação

✅ **COMPLETO**

- EntityGraph implementado em:
  - [`EventoRepository`](biblia/biblia-service/src/main/java/com/ia/biblia/service/evento/EventoRepository.java)
  - [`PessoaRepository`](biblia/biblia-service/src/main/java/com/ia/biblia/service/pessoa/PessoaRepository.java)
  - [`FamiliaRepository`](biblia/biblia-service/src/main/java/com/ia/biblia/service/familia/FamiliaRepository.java)
  - [`DespesaRepository`](biblia/biblia-service/src/main/java/com/ia/biblia/service/financeiro/DespesaRepository.java)
  - [`ReceitaRepository`](biblia/biblia-service/src/main/java/com/ia/biblia/service/financeiro/ReceitaRepository.java)

## Data

2024-03-15

## Revisores

- Team Lead
- Architect
