# ADR-002: Usar Specification Pattern para Filtros Dinâmicos

## Status

✅ Aceito

## Contexto

O projeto precisa de uma forma flexível de implementar filtros e consultas dinâmicas sem criar métodos de repository específicos para cada combinação de filtros.

## Decisão

Usar **Specification Pattern** (JPA Criteria API) com operadores predefinidos.

## Detalhes

### Alternativas Consideradas

| Alternativa | Prós | Contras |
|-------------|------|---------|
| Specification | Flexível, composável, type-safe | Curva de aprendizado |
| QueryDSL | API fluente, type-safe | Dependência adicional |
| JPA Criteria | Nativo, flexível | Verboso |
| JPQL Dinâmico | Familiar | Error-prone |

### Operadores Implementados

| Operador | Descrição | Exemplo |
|----------|-----------|---------|
| `EQUAL` | Igualdade | `nome = "João"` |
| `NOT_EQUAL` | Diferente | `status != "INATIVO"` |
| `LIKE` | Like (case-insensitive) | `nome LIKE "%João%"` |
| `IN` | Em lista | `status IN ("A", "B")` |
| `GREATER_THAN` | Maior que | `idade > 18` |
| `LESS_THAN` | Menor que | `idade < 65` |
| `GREATER_THAN_OR_EQUAL_TO` | Maior ou igual | `salario >= 1000` |
| `LESS_THAN_OR_EQUAL_TO` | Menor ou igual | `idade <= 65` |

### FieldTypes Suportados

| FieldType | Tipo Java |
|-----------|-----------|
| `BOOLEAN` | `Boolean` |
| `CHAR` | `Character` |
| `DATE` | `LocalDate` |
| `TIME` | `LocalTime` |
| `DATE_TIME` | `LocalDateTime` |
| `STRING` | `String` |
| `LONG` | `Long` |
| `INTEGER` | `Integer` |
| `DOUBLE` | `Double` |
| `ENUM` | `Enum` |
| `OBJECT` | `Object` |

## Implementação

```java
public class SearchSpecification<T> implements Specification<T> {
    private final SearchRequest request;
    
    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query,
                                 CriteriaBuilder cb) {
        Predicate predicate = cb.equal(cb.literal(Boolean.TRUE),
                                       request.isDisjunction());
        
        for (FilterRequest filter : this.request.getFilters()) {
            predicate = filter.getOperator().build(root, cb, filter, predicate,
                                                   request.isDisjunction());
        }
        
        // Ordenação
        List<Order> orders = new ArrayList<>();
        for (SortRequest sort : this.request.getSorts()) {
            orders.add(sort.getDirection().build(root, cb, sort));
        }
        
        query.orderBy(orders);
        return predicate;
    }
}
```

## Consequências

### Positivas

- ✅ Filtros dinâmicos e composáveis
- ✅ Type-safe
- ✅ Reutilizável entre entidades
- ✅ Suporte a ordenação
- ✅ 8 operadores + 11 field types

### Negativas

- ❌ Curva de aprendizado com Criteria API
- ❌ Debugging mais complexo

## Status de Implementação

✅ **COMPLETO**

- [`SearchSpecification.java`](../../ia-core-model/src/main/java/com/ia/core/model/specification/SearchSpecification.java) implementado
- [`Operator.java`](../../ia-core-model/src/main/java/com/ia/core/model/filter/Operator.java): 8 operadores
- [`FieldType.java`](../../ia-core-model/src/main/java/com/ia/core/model/filter/FieldType.java): 11 tipos
- Usado em todos os serviços

## Data

2024-01-20

## Revisores

- Team Lead
- Architect
