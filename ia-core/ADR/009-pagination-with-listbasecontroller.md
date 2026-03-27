# ADR-009: Paginação com ListBaseController e SearchRequestDTO

## Status

✅ Aceito

## Contexto

O projeto precisa de uma forma padronizada de implementar paginação e filtros em endpoints REST, evitando carregamento completo de dados (full load) que impacta performance.

## Decisão

Usar **ListBaseController** com **SearchRequestDTO** para paginação e filtros dinâmicos, reutilizando a infraestrutura do ia-core.

## Detalhes

### SearchRequestDTO

```java
public class EventoSearchRequest extends SearchRequestDTO {

    /** Mapa de filtros */
  private static final Map<FilterProperty, Collection<FilterRequestDTO>> filterMap = new HashMap<>();

  /**
   * Construtor padrão
   */
  protected EventoSearchRequest() {
    createFilters(filterMap, EventoTranslator.DESCRICAO,
                  EventoDTO.CAMPOS.DESCRICAO, FieldType.STRING,
                  OperatorDTO.LIKE, OperatorDTO.EQUAL,
                  OperatorDTO.NOT_EQUAL);
    createFilters(filterMap, EventoTranslator.TITULO,
                  EventoDTO.CAMPOS.TITULO, FieldType.STRING,
                  OperatorDTO.LIKE, OperatorDTO.EQUAL,
                  OperatorDTO.NOT_EQUAL);
  }

  @Override
  public Map<FilterProperty, Collection<FilterRequestDTO>> getAvaliableFilters() {
    return filterMap;
  }
}
```

### ListBaseController

```java
@RestController
@RequestMapping("/api/eventos")
public class EventoController extends ListBaseController<Evento, EventoDTO> {

    @Autowired
    private EventoService eventoService;

    @Override
    protected BaseService<Evento, EventoDTO> getService() {
        return eventoService;
    }

    @PostMapping("/search")
    public ResponseEntity<Page<EventoDTO>> search(
            @Valid @RequestBody EventoSearchRequest request) {
        Page<EventoDTO> page = ((ListBaseService<?, EventoDTO>) getService())
            .findAll(request.toSearchRequest());
        return ResponseEntity.status(HttpStatus.OK).body(page);
    }
}
```

### Implementação no BaseService

```java
public Page<D> findAll(SearchRequest request) {
    Specification<T> spec = new SearchSpecification<>(request);
    Page<T> entities = repository.findAll(spec, PageRequest.of(
        request.getPage(),
        request.getSize(),
        Sort.by(request.getSortDirection().getSpringDirection(), 
                 request.getSortField())
    ));
    return entities.map(this.mapper::toDTO);
}
```

### Exemplo de Requisição

```json
POST /api/eventos/search
{
  "page": 0,
  "size": 20,
  "sorts": [
    { "field": "dataEvento", "direction": "DESC" }
  ],
  "filters": [
    { "field": "nome", "operator": "LIKE", "value": "%culto%" },
    { "field": "status", "operator": "EQUAL", "value": "AGENDADO" },
    { "field": "dataEvento", "operator": "GREATER_THAN_OR_EQUAL_TO", "value": "2024-01-01" }
  ],
  "disjunction": false
}
```

### Exemplo de Resposta

```json
{
  "content": [
    {
      "id": 1,
      "nome": "Culto de Domingo",
      "dataEvento": "2024-01-14",
      "status": "AGENDADO",
      "local": {
        "id": 1,
        "nome": "Templo Principal"
      }
    }
  ],
  "pageable": {
    "pageNumber": 0,
    "pageSize": 20,
    "sort": [
      { "direction": "DESC", "property": "dataEvento" }
    ]
  },
  "totalElements": 150,
  "totalPages": 8
}
```

## Consequências

### Positivas

- ✅ Paginação consistente em todos os endpoints
- ✅ Filtros dinâmicos via Specification
- ✅ Performance otimizada (evita full load)
- ✅ API padronizada
- ✅ Suporte a ordenação
- ✅ Integração com EntityGraph

### Negativas

- ❌ Curva de aprendizado para filtros
- ❌ Necessário criar SearchRequestDTO para cada entidade
- ❌ Overhead de configuração inicial

## Status de Implementação

✅ **COMPLETO**

- [`ListBaseController`](ia-core/ia-core-rest/src/main/java/com/ia/core/rest/control/ListBaseController.java) implementado
- [`SearchRequestDTO`](ia-core/ia-core-service-model/src/main/java/com/ia/core/service/dto/SearchRequestDTO.java) implementado
- SearchRequestDTOs criados para:
  - Evento
  - Pessoa
  - Familia
  - Despesa
  - Receita
  - Transferencia

## Data

2024-03-30

## Revisores

- Team Lead
- Architect

## Referências

1. **Spring Data JPA - Pagination**
   - URL: https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repositories.paging
   - Documentação oficial sobre paginação com Spring Data

2. **Spring REST Pagination Best Practices**
   - URL: https://www.baeldung.com/spring-boot-pagination
   - Guia completo sobre paginação em REST APIs

3. **Baeldung - Spring Page Request**
   - URL: https://www.baeldung.com/spring-data-jpa-pageable
   - Implementação de Pageable com filtros dinâmicos

4. **Google Cloud API Design - Pagination**
   - URL: https://cloud.google.com/apis/design/design_patterns#list_standard_methods
   - Boas práticas para paginação em APIs

5. **Baeldung - Search Specifications**
   - URL: https://www.baeldung.com/spring-data-jpa-specification
   - Combining Specifications com paginação
