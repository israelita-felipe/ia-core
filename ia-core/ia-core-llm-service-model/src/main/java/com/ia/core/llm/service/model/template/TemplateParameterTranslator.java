package com.ia.core.llm.service.model.template;

/**
 * Translator constants for TemplateParameter DTO.
 * <p>
 * Contains constants for i18n keys, validation messages, and field names
 * used throughout the TemplateParameter DTO processing pipeline.
 *
 * @author Israel Araújo
 * @since 1.0.0
 * @see com.ia.core.llm.service.model.template.TemplateParameterDTO
 */
public final class TemplateParameterTranslator {

    private TemplateParameterTranslator() {
        // Utility class
    }

    /**
     * Help text i18n keys
     */
    public static final class HELP {
        public static final String TEMPLATE_PARAMETER = "template.parameter.help";
        public static final String NOME = "template.parameter.help.nome";
    }

    /**
     * DTO class canonical name
     */
    public static final String TEMPLATE_PARAMETER_CLASS = TemplateParameterDTO.class.getCanonicalName();

    /**
     * Field name constants
     */
    public static final String TEMPLATE_PARAMETER = "template.parameter";
    public static final String NOME = "template.parameter.nome";

    /**
     * Validation message keys
     */
    public static final class VALIDATION {
        public static final String NOME_REQUIRED = "validation.template.parameter.nome.required";
        public static final String NOME_SIZE = "validation.template.parameter.nome.size";
    }

    /**
     * Business rule message keys
     */
    public static final class RULE {
        public static final String NOME_DUPLICADO = "template.parameter.rule.nome.duplicado";
    }

    /**
     * Error message keys
     */
    public static final class ERROR {
        public static final String NOT_FOUND = "template.parameter.error.notfound";
        public static final String DUPLICATE = "template.parameter.error.duplicate";
    }

    /**
     * Success message keys
     */
    public static final class MESSAGE {
        public static final String CREATED = "template.parameter.message.created";
    }

    /**
     * Domain event message keys
     */
    public static final class EVENT {
        public static final String PARAMETRO_CRIADO = "template.parameter.event.criado";
    }
}
