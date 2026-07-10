package com.ia.core.communication.service.model.grupocontato.dto;

/**
 * Constantes de tradução para GrupoContatoDTO.
 * <p>
 * Contém constantes para chaves i18n, mensagens de validação e nomes de campos
 * utilizados no pipeline de processamento do DTO de GrupoContato.
 *
 * @author Israel Araújo
 * @since 1.0.0
 * @see GrupoContatoDTO
 */
public final class GrupoContatoTranslator {

    private GrupoContatoTranslator() {
        // Utility class
    }

    /**
     * Validation message keys
     */
    public static final class VALIDATION {
        public static final String NOME_NOT_BLANK = "grupo.contato.validation.nome.not.blank";
        public static final String NOME_SIZE = "grupo.contato.validation.nome.size";
        public static final String DESCRICAO_SIZE = "grupo.contato.validation.descricao.size";
    }

    /**
     * Help text i18n keys
     */
    public static final class HELP {
        public static final String GRUPO_CONTATO = "grupo.contato.help";
        public static final String NOME = "grupo.contato.help.nome";
        public static final String DESCRICAO = "grupo.contato.help.descricao";
        public static final String ATIVO = "grupo.contato.help.ativo";
    }

    /**
     * Error message keys
     */
    public static final class ERROR {
        public static final String NOT_FOUND = "grupo.contato.error.notfound";
        public static final String DUPLICATE = "grupo.contato.error.duplicate";
    }

    /**
     * Success message keys
     */
    public static final class MESSAGE {
        public static final String CREATED = "grupo.contato.message.created";
        public static final String UPDATED = "grupo.contato.message.updated";
        public static final String DELETED = "grupo.contato.message.deleted";
    }

    /**
     * Business rule message keys
     */
    public static final class RULE {
        public static final String GRUPO_SEM_CONTATOS = "grupo.contato.rule.grupo.sem.contatos";
        public static final String CONTATO_DUPLICADO = "grupo.contato.rule.contato.duplicado";
    }

    /**
     * Domain event message keys
     */
    public static final class EVENT {
        public static final String GRUPO_CRIADO = "grupo.contato.event.criado";
        public static final String GRUPO_ATUALIZADO = "grupo.contato.event.atualizado";
        public static final String CONTATO_ADICIONADO_AO_GRUPO = "grupo.contato.event.contato.adicionado";
    }

    /**
     * Field name constants
     */
    public static final String GRUPO_CONTATO = "grupo.contato";
    public static final String NOME = "grupo.contato.nome";
    public static final String DESCRICAO = "grupo.contato.descricao";
    public static final String ATIVO = "grupo.contato.ativo";
    public static final String GRUPO_CONTATO_CLASS = GrupoContatoDTO.class.getCanonicalName();
}