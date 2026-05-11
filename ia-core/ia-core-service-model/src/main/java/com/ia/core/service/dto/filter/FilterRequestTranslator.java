package com.ia.core.service.dto.filter;

/**
 * Translator constants for FilterRequest DTO.
 * <p>
 * Contains constants for i18n keys, validation messages, and field names
 * used throughout the FilterRequest DTO processing pipeline.
 *
 * @author Israel Araújo
 * @since 1.0.0
 * @see com.ia.core.service.dto.filter.FilterRequestDTO
 */
public final class FilterRequestTranslator {

    private FilterRequestTranslator() {
        // Utility class
    }

    /**
     * DTO class canonical name
     */
    public static final String FILTER_REQUEST_CLASS = FilterRequestDTO.class.getCanonicalName();

    /**
     * Field name constants
     */
    public static final String FILTER_REQUEST = "filter.request";
    public static final String VALUE = "filter.request.value";
    public static final String KEY = "filter.request.key";
    public static final String NEGATE = "filter.request.negate";
    public static final String OPERATOR = "filter.request.operator";
    public static final String FIELD_TYPE = "filter.request.fieldType";
    public static final String TEXT_FILTER = "filter.request.text.filter";
    public static final String DOUBLE_FILTER = "filter.request.double.filter";
    public static final String INTEGER_FILTER = "filter.request.integer.filter";
    public static final String LONG_FILTER = "filter.request.long.filter";
    public static final String BOOLEAN_FILTER = "filter.request.boolean.filter";
    public static final String CHARACTER_FILTER = "filter.request.character.filter";
    public static final String DATE_FILTER = "filter.request.date.filter";
    public static final String TIME_FILTER = "filter.request.time.filter";
    public static final String DATE_TIME_FILTER = "filter.request.date.time.filter";
    public static final String ENUM_FILTER = "filter.request.enum.filter";

    /**
     * Help text i18n keys
     */
    public static final class HELP {
        public static final String FILTER_REQUEST = "filter.request.help";
        public static final String VALUE = "filter.request.help.value";
        public static final String KEY = "filter.request.help.key";
        public static final String OPERATOR = "filter.request.help.operator";
        public static final String FIELD_TYPE = "filter.request.help.fieldType";
    }

    /**
     * Validation message keys
     */
    public static final class VALIDATION {
        public static final String KEY_REQUIRED = "filter.request.validation.key.required";
        public static final String OPERATOR_REQUIRED = "filter.request.validation.operator.required";
        public static final String VALUE_REQUIRED = "filter.request.validation.value.required";
    }

    /**
     * Business rule message keys
     */
    public static final class RULE {
        public static final String CAMPO_INEXISTENTE = "filter.request.rule.campo.inexistente";
        public static final String OPERADOR_INVALIDO = "filter.request.rule.operador.invalido";
    }

    /**
     * Success/error message keys
     */
    public static final class MESSAGE {
        public static final String FILTRO_APLICADO = "filter.request.message.filtro.aplicado";
    }

    /**
     * Domain event message keys
     */
    public static final class EVENT {
        public static final String FILTRO_CRIADO = "filter.request.event.criado";
    }
}
