package com.ia.core.llm.service.model.agente;

/**
 * Translator para internacionalização de Agente.
 * <p>
 * Centraliza todas as chaves de tradução usadas na UI e validações,
 * seguindo o padrão ADR-003 para gerenciamento de labels.
 *
 * @author Israel Araújo
 * @since 1.0.0
 * @see AgenteDTO
 */
public final class AgenteTranslator {

    private AgenteTranslator() {}

    /**
     * Validation message keys
     */
    public static final class VALIDATION {
        public static final String IDENTIFICADOR_REQUIRED = "agente.validation.identificador.required";
        public static final String IDENTIFICADOR_SIZE = "agente.validation.identificador.size";
        public static final String TITULO_REQUIRED = "agente.validation.titulo.required";
        public static final String TITULO_SIZE = "agente.validation.titulo.size";
        public static final String DESCRICAO_SIZE = "agente.validation.descricao.size";
        public static final String MODELO_SIZE = "agente.validation.modelo.size";
        public static final String MODULO_ORIGEM_SIZE = "agente.validation.modulo_origem.size";
    }

    /**
     * Help text i18n keys
     */
    public static final class HELP {
        public static final String IDENTIFICADOR = "agente.help.identificador";
        public static final String TITULO = "agente.help.titulo";
        public static final String DESCRICAO = "agente.help.descricao";
        public static final String INSTRUCOES = "agente.help.instrucoes";
        public static final String MODELO = "agente.help.modelo";
        public static final String MODULO_ORIGEM = "agente.help.modulo_origem";
    }

    /**
     * Error message keys
     */
    public static final class ERROR {
        public static final String NOT_FOUND = "agente.error.notfound";
        public static final String DUPLICATE = "agente.error.duplicate";
    }

    /**
     * Success message keys
     */
    public static final class MESSAGE {
        public static final String CREATED = "agente.message.created";
        public static final String UPDATED = "agente.message.updated";
        public static final String DELETED = "agente.message.deleted";
    }

    /**
     * Business rule message keys
     */
    public static final class RULE {
        public static final String IDENTIFICADOR_DUPLICADO = "agente.rule.identificador.duplicado";
    }

    /**
     * Domain event message keys
     */
    public static final class EVENT {
        public static final String AGENTE_CRIADO = "agente.event.criado";
        public static final String AGENTE_ATUALIZADO = "agente.event.atualizado";
    }

    /**
     * Field name constants
     */
    public static final String AGENTE = "agente";
    public static final String IDENTIFICADOR = "agente.identificador";
    public static final String TITULO = "agente.titulo";
    public static final String DESCRICAO = "agente.descricao";
    public static final String INSTRUCOES = "agente.instrucoes";
    public static final String MODELO = "agente.modelo";
    public static final String ATIVO = "agente.ativo";
    public static final String MODULO_ORIGEM = "agente.modulo_origem";
    public static final String TEMPERATURE = "agente.temperature";
    public static final String MAX_TOKENS = "agente.maxTokens";
    public static final String FERRAMENTAS = "agente.ferramentas";
    public static final String SKILLS = "agente.skills";
    public static final String AGENTE_CLASS = AgenteDTO.class.getCanonicalName();
}