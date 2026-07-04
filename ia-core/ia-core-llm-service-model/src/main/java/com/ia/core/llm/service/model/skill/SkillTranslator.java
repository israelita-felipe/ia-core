package com.ia.core.llm.service.model.skill;

/**
 * Translator constants for Skill DTO.
 * <p>
 * Contains constants for i18n keys, validation messages, and field names
 * used throughout the Skill DTO processing pipeline.
 *
 * @author Israel Araújo
 * @since 1.0.0
 * @see com.ia.core.llm.service.model.skill.SkillDTO
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

    /**
     * Validation message keys
     */
    public static final class VALIDATION {
        public static final String IDENTIFICADOR_REQUIRED = "validation.skill.identificador.required";
        public static final String IDENTIFICADOR_SIZE = "validation.skill.identificador.size";
        public static final String TITULO_REQUIRED = "validation.skill.titulo.required";
        public static final String TITULO_SIZE = "validation.skill.titulo.size";
        public static final String DESCRICAO_SIZE = "validation.skill.descricao.size";
        public static final String TIPO_REQUIRED = "validation.skill.tipo.required";
        public static final String MODULO_ORIGEM_SIZE = "validation.skill.modulo_origem.size";
    }

    /**
     * Business rule message keys
     */
    public static final class RULE {
        public static final String IDENTIFICADOR_DUPLICADO = "skill.rule.identificador.duplicado";
    }

    /**
     * Success/error message keys
     */
    public static final class MESSAGE {
        public static final String CRIADO_SUCESSO = "skill.message.criado.sucesso";
        public static final String ATUALIZADO_SUCESSO = "skill.message.atualizado.sucesso";
        public static final String DELETADO_SUCESSO = "skill.message.deletado.sucesso";
    }

    /**
     * Domain event message keys
     */
    public static final class EVENT {
        public static final String SKILL_CRIADO = "skill.event.criado";
        public static final String SKILL_ATUALIZADO = "skill.event.atualizado";
    }
}
