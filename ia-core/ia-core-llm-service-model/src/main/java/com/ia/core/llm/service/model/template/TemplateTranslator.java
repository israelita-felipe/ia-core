package com.ia.core.llm.service.model.template;

/**
 * Translator constants for Template DTO.
 * <p>
 * Contains constants for i18n keys, validation messages, and field names
 * used throughout the Template DTO processing pipeline.
 *
 * @author Israel Araújo
 * @since 1.0.0
 * @see TemplateDTO
 */
public final class TemplateTranslator {

    private TemplateTranslator() {
        // Utility class
    }

    /**
     * Help text i18n keys
     */
    public static final class HELP {
        public static final String TEMPLATE = "template.help";
        public static final String TITULO = "template.help.titulo";
        public static final String CONTEUDO = "template.help.conteudo";
        public static final String EXIGE_CONTEXTO = "template.help.exige.contexto";
        public static final String PARAMETROS = "template.help.parametros";
    }

    /**
     * Validation message keys
     */
    public static final class VALIDATION {
        public static final String TITULO_NOT_BLANK = "template.validation.titulo.not.blank";
        public static final String TITULO_SIZE = "template.validation.titulo.size";
        public static final String CONTEUDO_NOT_BLANK = "template.validation.conteudo.not.blank";
        public static final String CONTEUDO_SIZE = "template.validation.conteudo.size";
    }

    /**
     * Business rule message keys
     */
    public static final class RULE {
        public static final String TEMPLATE_INVALIDO = "template.rule.template.invalido";
        public static final String PARAMETROS_INVALIDOS = "template.rule.parametros.invalidos";
    }

    /**
     * Success/error message keys
     */
    public static final class MESSAGE {
        public static final String CRIADO_SUCESSO = "template.message.criado.sucesso";
        public static final String ATUALIZADO_SUCESSO = "template.message.atualizado.sucesso";
        public static final String DELETADO_SUCESSO = "template.message.deletado.sucesso";
    }

    /**
     * Domain event message keys
     */
    public static final class EVENT {
        public static final String TEMPLATE_CRIADO = "template.event.criado";
        public static final String TEMPLATE_ATUALIZADO = "template.event.atualizado";
    }

    /**
     * DTO class canonical name
     */
    public static final String TEMPLATE_CLASS = TemplateDTO.class.getCanonicalName();

    /**
     * Field name constants
     */
    public static final String TEMPLATE = "template";
    public static final String TITULO = "template.titulo";
    public static final String CONTEUDO = "template.conteudo";
    public static final String EXIGE_CONTEXTO = "template.exige.contexto";
    public static final String PARAMETROS = "template.parametros";
}
