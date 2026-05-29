package com.ia.core.communication.service.model.modelomensagem.dto;

/**
 * Constantes de tradução para ModeloMensagemDTO.
 * <p>
 * Contém constantes para chaves i18n, mensagens de validação e nomes de campos
 * utilizados no pipeline de processamento do DTO de ModeloMensagem.
 *
 * @author Israel Araújo
 * @since 1.0.0
 * @see com.ia.core.communication.service.model.modelomensagem.dto.ModeloMensagemDTO
 */
public final class ModeloMensagemTranslator {

    private ModeloMensagemTranslator() {
        // Utility class
    }

    /**
     * Help text i18n keys
     */
    public static final class HELP {
        public static final String MODELO_MENSAGEM = "modelo.mensagem.help";
        public static final String NOME = "modelo.mensagem.help.nome";
        public static final String DESCRICAO = "modelo.mensagem.help.descricao";
        public static final String CORPO_MODELO = "modelo.mensagem.help.corpo.modelo";
        public static final String TIPO_CANAL = "modelo.mensagem.help.tipo.canal";
        public static final String ATIVO = "modelo.mensagem.help.ativo";
    }

    /**
     * Validation message keys
     */
    public static final class VALIDATION {
        public static final String NOME_NOT_BLANK = "modelo.mensagem.validation.nome.not.blank";
        public static final String NOME_SIZE = "modelo.mensagem.validation.nome.size";
        public static final String DESCRICAO_SIZE = "modelo.mensagem.validation.descricao.size";
        public static final String CORPO_MODELO_NOT_BLANK = "modelo.mensagem.validation.corpo.modelo.not.blank";
        public static final String TIPO_CANAL_NOT_NULL = "modelo.mensagem.validation.tipo.canal.not.null";
    }

    /**
     * Business rule message keys
     */
    public static final class RULE {
        public static final String NOME_DUPLICADO = "modelo.mensagem.rule.nome.duplicado";
        public static final String MODELO_INATIVO = "modelo.mensagem.rule.modelo.inativo";
        public static final String VARIAVEIS_INVALIDAS = "modelo.mensagem.rule.variaveis.invalidas";
        public static final String MODELO_NAO_ENCONTRADO = "modelo.mensagem.rule.modelo.nao.encontrado";
        public static final String CONTATO_NAO_ENCONTRADO = "modelo.mensagem.rule.contato.nao.encontrado";
    }

    /**
     * Success/error message keys
     */
    public static final class MESSAGE {
        public static final String CRIADO_SUCESSO = "modelo.mensagem.message.criado.sucesso";
        public static final String ATUALIZADO_SUCESSO = "modelo.mensagem.message.atualizado.sucesso";
        public static final String DELETADO_SUCESSO = "modelo.mensagem.message.deletado.sucesso";
    }

    /**
     * Domain event message keys
     */
    public static final class EVENT {
        public static final String MODELO_CRIADO = "modelo.mensagem.event.criado";
        public static final String MODELO_ATUALIZADO = "modelo.mensagem.event.atualizado";
    }

    /**
     * DTO class canonical name
     */
    public static final String MODELO_MENSAGEM_CLASS = ModeloMensagemDTO.class.getCanonicalName();

    /**
     * Field name constants
     */
    public static final String MODELO_MENSAGEM = "modelo.mensagem";
    public static final String NOME = "modelo.mensagem.nome";
    public static final String DESCRICAO = "modelo.mensagem.descricao";
    public static final String CORPO_MODELO = "modelo.mensagem.corpo.modelo";
    public static final String TIPO_CANAL = "modelo.mensagem.tipo.canal";
    public static final String ATIVO = "modelo.mensagem.ativo";
}
