package com.ia.core.owl.service.model.axioma;

/**
 * Translator constants for Axioma (Ontology Axiom) DTO.
 * <p>
 * Contains constants for i18n keys, validation messages, and field names
 * used throughout the Axioma DTO processing pipeline.
 *
 * @author Israel Araújo
 * @since 1.0.0
 * @see AxiomaDTO
 */
public final class AxiomaTranslator {

    private AxiomaTranslator() {
        // Utility class
    }

    /**
     * Help text i18n keys
     */
    public static final class HELP {
        public static final String AXIOMA = "axioma.help";
        public static final String URI = "axioma.help.uri";
        public static final String URI_VERSION = "axioma.help.uri.version";
        public static final String PREFIX = "axioma.help.prefix";
        public static final String EXPRESSAO = "axioma.help.expressao";
        public static final String IS_CONSISTENTE = "axioma.help.consistente";
        public static final String IS_INFERIDO = "axioma.help.inferido";
        public static final String IS_ATIVO = "axioma.help.ativo";
    }

    /**
     * Validation message keys
     */
    public static final class VALIDATION {
        public static final String PREFIX_REQUIRED = "axioma.validation.prefix.required";
        public static final String PREFIX_SIZE = "axioma.validation.prefix.size";
        public static final String URI_VERSION_REQUIRED = "axioma.validation.uriVersion.required";
        public static final String EXPRESSAO_REQUIRED = "axioma.validation.expressao.required";
        public static final String EXPRESSAO_SIZE = "axioma.validation.expressao.size";
    }

    /**
     * Business rule message keys
     */
    public static final class RULE {
        public static final String AXIOMA_INVALIDO = "axioma.rule.axioma.invalido";
        public static final String PREFIX_NAO_ENCONTRADO = "axioma.rule.prefix.nao.encontrado";
    }

    /**
     * Success/error message keys
     */
    public static final class MESSAGE {
        public static final String CRIADO_SUCESSO = "axioma.message.criado.sucesso";
        public static final String ATUALIZADO_SUCESSO = "axioma.message.atualizado.sucesso";
        public static final String DELETADO_SUCESSO = "axioma.message.deletado.sucesso";
    }

    /**
     * Domain event message keys
     */
    public static final class EVENT {
        public static final String AXIOMA_CRIADO = "axioma.event.criado";
        public static final String AXIOMA_INVALIDADO = "axioma.event.invalidado";
    }

    /**
     * DTO class canonical name
     */
    public static final String AXIOMA_CLASS = AxiomaDTO.class.getCanonicalName();

    /**
     * Field name constants
     */
    public static final String AXIOMA = "axioma";
    public static final String URI = "axioma.uri";
    public static final String URI_VERSION = "axioma.uri.version";
    public static final String PREFIX = "axioma.prefix";
    public static final String EXPRESSAO = "axioma.expressao";
    public static final String IS_CONSISTENTE = "axioma.consistente";
    public static final String IS_INFERIDO = "axioma.inferido";
    public static final String IS_ATIVO = "axioma.ativo";
}
