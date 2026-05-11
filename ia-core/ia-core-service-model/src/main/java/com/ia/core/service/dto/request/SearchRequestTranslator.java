package com.ia.core.service.dto.request;

/**
 * Translator constants for SearchRequest DTO.
 * <p>
 * Contains constants for i18n keys, validation messages, and field names
 * used throughout the SearchRequest DTO processing pipeline.
 *
 * @author Israel Araújo
 * @since 1.0.0
 * @see com.ia.core.service.dto.request.SearchRequestDTO
 */
public final class SearchRequestTranslator {

    private SearchRequestTranslator() {
        // Utility class
    }

    /**
     * DTO class canonical name
     */
    public static final String FILTER_REQUEST_CLASS = SearchRequestDTO.class.getCanonicalName();

    /**
     * Field name constants
     */
    public static final String FILTER_REQUEST = "search.request";

    /**
     * Help text i18n keys
     */
    public static final class HELP {
        public static final String FILTER_REQUEST = "search.request.help";
    }

    /**
     * Validation message keys
     */
    public static final class VALIDATION {
        public static final String PAGE_REQUIRED = "search.request.validation.page.required";
        public static final String PAGE_SIZE_REQUIRED = "search.request.validation.pageSize.required";
    }

    /**
     * Business rule message keys
     */
    public static final class RULE {
        public static final String PAGE_INVALIDA = "search.request.rule.pagina.invalida";
        public static final String TAMANHO_PAGINA_INVALIDO = "search.request.rule.tamanho.pagina.invalido";
    }

    /**
     * Success/error message keys
     */
    public static final class MESSAGE {
        public static final String PESQUISA_REALIZADA = "search.request.message.pesquisa.realizada";
    }

    /**
     * Domain event message keys
     */
    public static final class EVENT {
        public static final String PESQUISA_EFETUADA = "search.request.event.pesquisa.efetuada";
    }
}
