# FASE Q: Specification Pattern - Documentação para Biblia

## Visão Geral

O **Specification Pattern** já está implementado no `ia-core` e pode ser reutilizado diretamente pelo Biblia. Esta documentação guia a utilização correta da infraestrutura existente.

---

## Infraestrutura Existente (ia-core)

### Artefatos Disponíveis

| Classe | Descrição | Localização |
|--------|-----------|-------------|
| [`SearchSpecification<T>`][SearchSpecification] | Specification principal do Spring Data JPA | `ia-core-model` |
| [`SearchRequest`][SearchRequest] | Requisição de busca com filtros e ordenação | `ia-core-model` |
| [`FilterRequest`][FilterRequest] | Filtro individual | `ia-core-model` |
| [`Operator`][Operator] | Enum com 8 operadores (EQUAL, LIKE, IN, etc.) | `ia-core-model` |
| [`FieldType`][FieldType] | Enum com 11 tipos de campo para parsing | `ia-core-model` |
| [`SortRequest`][SortRequest] | Requisição de ordenação | `ia-core-model` |
| [`SortDirection`][SortDirection] | ASC/DESC para ordenação | `ia-core-model` |
| [`SearchRequestMapper`][SearchRequestMapper] | Mapper DTO → Model | `ia-core-service` |
| [`CollectionSearchSpecification<T>`][CollectionSearchSpecification] | Specification para coleções em memória | `ia-core-service-model` |

---

## Operadores Suportados

| Operador | Descrição | Exemplo JSON |
|----------|-----------|--------------|
| `EQUAL` | Igualdade exata | `{"key": "status", "operator": "EQUAL", "value": "ATIVO"}` |
| `NOT_EQUAL` | Diferente | `{"key": "status", "operator": "NOT_EQUAL", "value": "INATIVO"}` |
| `LIKE` | Like case-insensitive | `{"key": "nome", "operator": "LIKE", "value": "João"}` → `LIKE %João%` |
| `IN` | Em lista | `{"key": "status", "operator": "IN", "value": ["A", "B"]}` |
| `GREATER_THAN` | Maior que | `{"key": "idade", "operator": "GREATER_THAN", "value": 18}` |
| `LESS_THAN` | Menor que | `{"key": "idade", "operator": "LESS_THAN", "value": 65}` |
| `GREATER_THAN_OR_EQUAL_TO` | Maior ou igual | `{"key": "salario", "operator": "GREATER_THAN_OR_EQUAL_TO", "value": 1000}` |
| `LESS_THAN_OR_EQUAL_TO` | Menor ou igual | `{"key": "idade", "operator": "LESS_THAN_OR_EQUAL_TO", "value": 65}` |

## Tipos de Campo (FieldType)

| FieldType | Tipo Java | Exemplo de Valor |
|-----------|-----------|------------------|
| `STRING` | `String` | `"João"` |
| `LONG` | `Long` | `123L` |
| `INTEGER` | `Integer` | `42` |
| `DOUBLE` | `Double` | `10.5` |
| `BOOLEAN` | `Boolean` | `true` |
| `DATE` | `LocalDate` | `"2024-01-20"` |
| `TIME` | `LocalTime` | `"14:30:00"` |
| `DATE_TIME` | `LocalDateTime` | `"2024-01-20T14:30:00"` |
| `CHAR` | `Character` | `"A"` |
| `ENUM` | `Enum` | `"STATUS_ATIVO"` |
| `OBJECT` | `Object` | Objeto complexo |

---

## Como Reutilizar no Biblia

### Seguir o Padrão Existente

**Importante:** O Biblia já possui `EventoSearchRequest` que define o padrão a ser seguido.

Todos os SearchRequests do Biblia devem:
1. Estender `SearchRequestDTO`
2. Configurar filtros disponíveis no construtor
3. Usar `FieldType` e `OperatorDTO` do ia-core

Consulte o código existente em:
```
gestor-igreja/Biblia/biblia-service-model/src/main/java/com/ia/biblia/service/evento/dto/EventoSearchRequest.java
```

### 2. Adicionar método getSearchRequest() no DTO

```java
// NO DTO DA ENTIDADE (ex: EventoDTO)
public static final SearchRequestDTO getSearchRequest() {
    return new EventoSearchRequest();
}
```

### 3. Configurar o Repository

```java
// JpaSpecificationExecutor já herda métodos como:
// - findAll(Specification<T>)
// - findAll(Specification<T>, Pageable)
// - count(Specification<T>)
public interface EntidadeRepository extends BaseEntityRepository<Entidade, Long>, 
    JpaSpecificationExecutor<Entidade> {
}
```

### 4. Configurar o Service

Seguir o padrão existente do `EventoServiceConfig` no Biblia.

### 5. Configurar o Controller REST

Seguir o padrão existente do `EventoController` no Biblia que estende `ListBaseController`.

---

## Exemplos de Consultas

### Exemplo 1: Busca Simples com LIKE

**Request:**
```json
POST /api/v1/eventos/all
{
    "filters": [
        {
            "key": "descricao",
            "operator": "LIKE",
            "fieldType": "STRING",
            "value": "reunião"
        }
    ],
    "page": 0,
    "size": 20,
    "disjunction": true
}
```

**SQL Gerado:**
```sql
SELECT * FROM evento 
WHERE UPPER(descricao) LIKE '%REUNIÃO%'
ORDER BY id ASC
LIMIT 20 OFFSET 0
```

### Exemplo 2: Múltiplos Filtros com AND

**Request:**
```json
POST /api/v1/eventos/all
{
    "filters": [
        {
            "key": "status",
            "operator": "EQUAL",
            "fieldType": "ENUM",
            "value": "ATIVO"
        },
        {
            "key": "dataInicio",
            "operator": "GREATER_THAN_OR_EQUAL_TO",
            "fieldType": "DATE",
            "value": "2024-01-01"
        }
    ],
    "sorts": [
        {
            "key": "dataInicio",
            "direction": "DESC"
        }
    ],
    "disjunction": false
}
```

**SQL Gerado:**
```sql
SELECT * FROM evento 
WHERE status = 'ATIVO' 
  AND data_inicio >= '2024-01-01'
ORDER BY data_inicio DESC
```

### Exemplo 3: Filtro com IN

**Request:**
```json
POST /api/v1/eventos/all
{
    "filters": [
        {
            "key": "tipo",
            "operator": "IN",
            "fieldType": "ENUM",
            "value": ["CULTO", "REUNIAO", "EVENTO_ESPECIAL"]
        }
    ],
    "disjunction": true
}
```

**SQL Gerado:**
```sql
SELECT * FROM evento 
WHERE tipo IN ('CULTO', 'REUNIAO', 'EVENTO_ESPECIAL')
```

### Exemplo 4: Contexto de Segurança

**Request:**
```json
POST /api/v1/eventos/all
{
    "filters": [
        {
            "key": "descricao",
            "operator": "LIKE",
            "fieldType": "STRING",
            "value": "reunião"
        }
    ],
    "context": [
        {
            "key": "igreja.id",
            "operator": "EQUAL",
            "fieldType": "LONG",
            "value": "1"
        }
    ],
    "disjunction": true
}
```

**Nota:** O contexto é usado para filtros automáticos de segurança (ex: filtrar por igreja do usuário logado).

---

## Coleções em Memória

Para filtrar coleções já carregadas em memória, use `CollectionSearchSpecification`:

```java
// Exemplo em um serviço
public List<Evento> filtrarEventosEmMemoria(List<Evento> eventos, SearchRequestDTO request) {
    CollectionSearchSpecification<Evento> specification = 
        new CollectionSearchSpecification<>(request);
    
    return eventos.stream()
        .filter(specification.toPredicate())
        .collect(Collectors.toList());
}
```

---

## Boas Práticas

### ✅ Faça

1. **Estenda `SearchRequestDTO`** para cada entidade
2. **Siga o padrão do `EventoSearchRequest`** existente no Biblia
3. **Use `FieldType` correto** para conversão automática de valores
4. **Configure `JpaSpecificationExecutor`** no repository
5. **Use `ListBaseService`** para herdar implementação padrão

### ❌ Não Faça

1. **Não crie Specification classes específicas** - reutilize `SearchSpecification<T>`
2. **Não sobrescreva `toPredicate()`** - a implementação base já cobre todos os casos
3. **Não misture operadores** sem necessidade - use `disjunction: false` para AND
4. **Não altere o padrão** estabelecido no `EventoSearchRequest`

---

## Dependências Maven

O Biblia já herda as dependências do ia-core-parent. Verifique se o módulo `ia-core-model` está como dependência:

```xml
<!-- No pom.xml do biblia-service-model -->
<dependency>
    <groupId>com.ia</groupId>
    <artifactId>ia-core-model</artifactId>
    <version>${ia-core.version}</version>
</dependency>

<dependency>
    <groupId>com.ia</groupId>
    <artifactId>ia-core-service</artifactId>
    <version>${ia-core.version}</version>
</dependency>
```

---

## Status de Implementação

| Componente | Status | Observação |
|------------|--------|------------|
| `SearchSpecification<T>` | ✅ Implementado | Já no ia-core-model |
| Operadores | ✅ 8 implementados | EQUAL, NOT_EQUAL, LIKE, IN, GREATER_THAN, LESS_THAN, GREATER_THAN_OR_EQUAL_TO, LESS_THAN_OR_EQUAL_TO |
| FieldTypes | ✅ 11 implementados | STRING, LONG, INTEGER, DOUBLE, BOOLEAN, DATE, TIME, DATE_TIME, CHAR, ENUM, OBJECT |
| `SearchRequestMapper` | ✅ Implementado | Mapper DTO → Model |
| `ListBaseService` | ✅ Implementado | Integração automática |
| `ListBaseController` | ✅ Implementado | Endpoint REST pronto |
| BiblIA SearchRequests | ✅ Existente | Seguir padrão do `EventoSearchRequest` existente |

---

## Observação Importante

**O padrão de SearchRequest já está definido no `EventoSearchRequest` do Biblia. Todos os outros SearchRequests devem seguir o mesmo padrão.**

Consulte o código fonte existente em:
- `gestor-igreja/Biblia/biblia-service-model/src/main/java/com/ia/biblia/service/evento/dto/EventoSearchRequest.java`

---

## Referências

- [ADR-002: Specification Pattern](./ADR/002-use-specification-for-filtering.md)
- [SearchSpecification.java][SearchSpecification]
- [Operator.java][Operator]
- [FieldType.java][FieldType]
- [SearchRequestDTO.java][SearchRequestDTO]
- [ListBaseService.java][ListBaseService]

[SearchSpecification]: ia-core/ia-core-model/src/main/java/com/ia/core/model/specification/SearchSpecification.java
[SearchRequest]: ia-core/ia-core-model/src/main/java/com/ia/core/model/filter/SearchRequest.java
[FilterRequest]: ia-core/ia-core-model/src/main/java/com/ia/core/model/filter/FilterRequest.java
[Operator]: ia-core/ia-core-model/src/main/java/com/ia/core/model/filter/Operator.java
[FieldType]: ia-core/ia-core-model/src/main/java/com/ia/core/model/filter/FieldType.java
[SortRequest]: ia-core/ia-core-model/src/main/java/com/ia/core/model/filter/SortRequest.java
[SortDirection]: ia-core/ia-core-model/src/main/java/com/ia/core/model/filter/SortDirection.java
[SearchRequestMapper]: ia-core/ia-core-service/src/main/java/com/ia/core/service/mapper/SearchRequestMapper.java
[CollectionSearchSpecification]: ia-core/ia-core-service-model/src/main/java/com/ia/core/service/specification/CollectionSearchSpecification.java
[SearchRequestDTO]: ia-core/ia-core-service-model/src/main/java/com/ia/core/service/dto/request/SearchRequestDTO.java
[ListBaseService]: ia-core/ia-core-service/src/main/java/com/ia/core/service/ListBaseService.java
