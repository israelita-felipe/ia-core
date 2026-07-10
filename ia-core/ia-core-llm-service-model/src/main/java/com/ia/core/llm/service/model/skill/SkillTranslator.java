package com.ia.core.llm.service.model.skill;

/**
 * Translator constants for Skill DTO.
 * <p>
 * Contains constants for i18n keys, validation messages, and field names
 * used throughout the Skill DTO processing pipeline.
 *
 * @author Israel Araújo
 * @since 1.0.0
 * @see SkillDTO
 */
public final class SkillTranslator {

    private SkillTranslator() {
        // Utility class
    }

    /**
     * Help text i18n keys
     */
    public static final class HELP {
        public static final String SKILL = "skill.help";
        public static final String IDENTIFICADOR = "skill.help.identificador";
        public static final String TITULO = "skill.help.titulo";
        public static final String DESCRICAO = "skill.help.descricao";
        public static final String TIPO = "skill.help.tipo";
        public static final String MODULO_ORIGEM = "skill.help.modulo_origem";
    }

    /**
     * Validation message keys
     */
    public static final class VALIDATION {
        public static final String IDENTIFICADOR_REQUIRED = "skill.validation.identificador.required";
        public static final String IDENTIFICADOR_SIZE = "skill.validation.identificador.size";
        public static final String TITULO_REQUIRED = "skill.validation.titulo.required";
        public static final String TITULO_SIZE = "skill.validation.titulo.size";
        public static final String DESCRICAO_SIZE = "skill.validation.descricao.size";
        public static final String TIPO_REQUIRED = "skill.validation.tipo.required";
        public static final String MODULO_ORIGEM_SIZE = "skill.validation.modulo_origem.size";
    }

    /**
     * Error message keys
     */
    public static final class ERROR {
        public static final String NOT_FOUND = "skill.error.notfound";
        public static final String DUPLICATE = "skill.error.duplicate";
    }

    /**
     * Success message keys
     */
    public static final class MESSAGE {
        public static final String CREATED = "skill.message.created";
        public static final String UPDATED = "skill.message.updated";
        public static final String DELETED = "skill.message.deleted";
    }

    /**
     * Business rule message keys
     */
    public static final class RULE {
        public static final String IDENTIFICADOR_DUPLICADO = "skill.rule.identificador.duplicado";
    }

    /**
     * Domain event message keys
     */
    public static final class EVENT {
        public static final String SKILL_CRIADO = "skill.event.criado";
        public static final String SKILL_ATUALIZADO = "skill.event.atualizado";
    }

    /**
     * DTO class canonical name
     */
    public static final String SKILL_CLASS = SkillDTO.class.getCanonicalName();

    /**
     * Field name constants
     */
    public static final String SKILL = "skill";
    public static final String IDENTIFICADOR = "skill.identificador";
    public static final String TITULO = "skill.titulo";
    public static final String DESCRICAO = "skill.descricao";
    public static final String TIPO = "skill.tipo";
    public static final String ATIVO = "skill.ativo";
    public static final String MODULO_ORIGEM = "skill.modulo_origem";
}
