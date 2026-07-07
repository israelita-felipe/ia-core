package com.ia.core.communication.service.model.contatomensagem.dto;

/**
 * Constantes de tradução para ContatoMensagemDTO.
 * <p>
 * Contém constantes para chaves i18n, mensagens de validação e nomes de campos
 * utilizados no pipeline de processamento do DTO de ContatoMensagem.
 *
 * @author Israel Araújo
 * @since 1.0.0
 * @see com.ia.core.communication.service.model.contatomensagem.dto.ContatoMensagemDTO
 */
public final class ContatoMensagemTranslator {

    private ContatoMensagemTranslator() {
        // Utility class
    }

    /**
     * Help text i18n keys
     */
    public static final class HELP {
        public static final String CONTATO_MENSAGEM = "contato.mensagem.help";
        public static final String GRUPO_CONTATO = "contato.mensagem.help.grupo.contato";
        public static final String TELEFONE = "contato.mensagem.help.telefone";
        public static final String NOME = "contato.mensagem.help.nome";
    }

    /**
     * Validation message keys
     */
    public static final class VALIDATION {
        public static final String GRUPO_CONTATO_NOT_NULL = "contato.mensagem.validation.grupo.contato.not.null";
        public static final String TELEFONE_NOT_BLANK = "contato.mensagem.validation.telefone.not.blank";
        public static final String TELEFONE_SIZE = "contato.mensagem.validation.telefone.size";
        public static final String NOME_SIZE = "contato.mensagem.validation.nome.size";
    }

    /**
     * Business rule message keys
     */
    public static final class RULE {
        public static final String GRUPO_NAO_ENCONTRADO = "contato.mensagem.rule.grupo.nao.encontrado";
        public static final String TELEFONE_DUPLICADO = "contato.mensagem.rule.telefone.duplicado";
    }

    /**
     * Error message keys
     */
    public static final class ERROR {
        public static final String NOT_FOUND = "contato.mensagem.error.notfound";
        public static final String DUPLICATE = "contato.mensagem.error.duplicate";
    }

    /**
     * Success message keys
     */
    public static final class MESSAGE {
        public static final String CREATED = "contato.mensagem.message.created";
        public static final String UPDATED = "contato.mensagem.message.updated";
        public static final String DELETED = "contato.mensagem.message.deleted";
    }

    /**
     * Domain event message keys
     */
    public static final class EVENT {
        public static final String CONTATO_ADICIONADO = "contato.mensagem.event.contato.adicionado";
        public static final String CONTATO_REMOVIDO = "contato.mensagem.event.contato.removido";
    }

    /**
     * DTO class canonical name
     */
    public static final String CONTATO_MENSAGEM_CLASS = ContatoMensagemDTO.class.getCanonicalName();

    /**
     * Field name constants
     */
    public static final String CONTATO_MENSAGEM = "contato.mensagem";
    public static final String GRUPO_CONTATO = "contato.mensagem.grupo.contato";
    public static final String TELEFONE = "contato.mensagem.telefone";
    public static final String NOME = "contato.mensagem.nome";
}
