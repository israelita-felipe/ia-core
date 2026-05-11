package com.ia.core.llm.service.model.comando;

/**
 * Translator constants for ComandoSistema (System Command) DTO.
 * <p>
 * Contains constants for i18n keys, validation messages, and field names
 * used throughout the ComandoSistema DTO processing pipeline.
 *
 * @author Israel Araújo
 * @since 1.0.0
 * @see com.ia.core.llm.service.model.comando.ComandoSistemaDTO
 */
public final class ComandoSistemaTranslator {

    private ComandoSistemaTranslator() {
        // Utility class
    }

    /**
     * Help text i18n keys
     */
    public static final class HELP {
        public static final String COMANDO_SISTEMA = "comando.sistema.help";
        public static final String TITULO = "comando.sistema.help.titulo";
        public static final String COMANDO = "comando.sistema.help.comando";
        public static final String FINALIDADE = "comando.sistema.help.finalidade";
    }

    /**
     * DTO class canonical name
     */
    public static final String COMANDO_SISTEMA_CLASS = ComandoSistemaDTO.class.getCanonicalName();

    /**
     * Field name constants
     */
    public static final String COMANDO_SISTEMA = "comando.sistema";
    public static final String TITULO = "comando.sistema.titulo";
    public static final String COMANDO = "comando.sistema.comando";
    public static final String FINALIDADE = "comando.sistema.finalidade";

    /**
     * Validation message keys
     */
    public static final class VALIDATION {
        public static final String TITULO_REQUIRED = "validation.comando.titulo.required";
        public static final String TITULO_SIZE = "validation.comando.titulo.size";
        public static final String COMANDO_SIZE = "validation.comando.comando.size";
        public static final String TEMPLATE_REQUIRED = "validation.comando.template.required";
        public static final String FINALIDADE_REQUIRED = "validation.comando.finalidade.required";
    }

    /**
     * Business rule message keys
     */
    public static final class RULE {
        public static final String COMANDO_PERIGOSO = "comando.sistema.rule.comando.perigoso";
        public static final String TEMPLATE_INVALIDO = "comando.sistema.rule.template.invalido";
    }

    /**
     * Success/error message keys
     */
    public static final class MESSAGE {
        public static final String CRIADO_SUCESSO = "comando.sistema.message.criado.sucesso";
        public static final String EXECUTADO_SUCESSO = "comando.sistema.message.executado.sucesso";
    }

    /**
     * Domain event message keys
     */
    public static final class EVENT {
        public static final String COMANDO_CRIADO = "comando.sistema.event.criado";
        public static final String COMANDO_EXECUTADO = "comando.sistema.event.executado";
    }
}
