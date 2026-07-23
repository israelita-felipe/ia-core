package com.ia.core.service.configuracao.dto;

/**
 * Translator constants for ConfiguracaoSistema DTO.
 * <p>
 * Contains constants for i18n keys, validation messages, and field names
 * used throughout the ConfiguracaoSistema DTO processing pipeline.
 *
 * @author Israel Araújo
 * @see ConfiguracaoSistemaDTO
 * @since 1.0.0
 */
public final class ConfiguracaoSistemaTranslator {

    public static final String CONFIGURACAO = "configuracao";
    /**
     * Error message keys
     */
    public static final String ERROR_NOT_FOUND = "configuracao.error.notfound";
    /**
     * Field name constants (used for filter labels)
     */
    public static final String CHAVE = "configuracao.chave";
    public static final String VALOR = "configuracao.valor";
    public static final String MODULO = "configuracao.modulo";
    public static final String CATEGORIA = "configuracao.categoria";
    public static final String DESCRICAO = "configuracao.descricao";
    public static final String TIPO = "configuracao.tipo";
    private ConfiguracaoSistemaTranslator() {
        // Utility class
    }

    /**
     * Help text i18n keys
     */
    public static final class HELP {
        public static final String CONFIGURACAO = "configuracao.help";
        public static final String CHAVE = "configuracao.help.chave";
        public static final String VALOR = "configuracao.help.valor";
        public static final String MODULO = "configuracao.help.modulo";
        public static final String CATEGORIA = "configuracao.help.categoria";
    }

    /**
     * Validation message keys
     */
    public static final class VALIDATION {
        public static final String CHAVE_NOT_NULL = "configuracao.validation.chave.not.null";
        public static final String CHAVE_NOT_BLANK = "configuracao.validation.chave.not.blank";
        public static final String VALOR_NOT_NULL = "configuracao.validation.valor.not.null";
        public static final String VALOR_NOT_BLANK = "configuracao.validation.valor.not.blank";
        public static final String MODULO_NOT_NULL = "configuracao.validation.modulo.not.null";
        public static final String CATEGORIA_NOT_NULL = "configuracao.validation.categoria.not.null";
        public static final String TIPO_NOT_NULL = "configuracao.validation.tipo.not.null";
    }

    /**
     * Business rule message keys
     */
    public static final class RULE {
        public static final String CHAVE_UNICA = "configuracao.rule.chave.unica";
        public static final String MODULO_NAO_VAZIO = "configuracao.rule.modulo.nao.vazio";
    }

    /**
     * Success message keys
     */
    public static final class MESSAGE {
        public static final String SAVE_SUCCESS = "configuracao.message.save.success";
        public static final String DELETE_SUCCESS = "configuracao.message.delete.success";
    }
}
