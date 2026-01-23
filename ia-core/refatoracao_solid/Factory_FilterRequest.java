package refatoracao_solid;

/**
 * Exemplo de Factory Pattern para criação de filtros.
 */
public interface FilterRequestFactory {
    /**
     * Cria filtro de igualdade.
     */
    FilterRequest createEqual(String key, Object value);
    /**
     * Cria filtro de LIKE.
     */
    FilterRequest createLike(String key, String value);
}

public class FilterRequestFactoryImpl implements FilterRequestFactory {
    public FilterRequest createEqual(String key, Object value) {
        return new FilterRequest(key, "EQUAL", value);
    }
    public FilterRequest createLike(String key, String value) {
        return new FilterRequest(key, "LIKE", value);
    }
}

class FilterRequest {
    private String key;
    private String operator;
    private Object value;
    public FilterRequest(String key, String operator, Object value) {
        this.key = key;
        this.operator = operator;
        this.value = value;
    }
    // getters/setters
}
