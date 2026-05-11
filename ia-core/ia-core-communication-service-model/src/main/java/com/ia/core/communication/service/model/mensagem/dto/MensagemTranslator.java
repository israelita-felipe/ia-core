package com.ia.core.communication.service.model.mensagem.dto;

/**
 * Translator constants for Mensagem (Message) DTO.
 * <p>
 * Contains constants for i18n keys, validation messages, and field names
 * used throughout the Mensagem DTO processing pipeline.
 *
 * @author Israel Araújo
 * @since 1.0.0
 * @see com.ia.core.communication.service.model.mensagem.dto.MensagemDTO
 */
public final class MensagemTranslator {

    private MensagemTranslator() {
        // Utility class
    }

    /**
     * Help text i18n keys
     */
    public static final class HELP {
        public static final String MENSAGEM = "mensagem.help";
        public static final String TELEFONE_DESTINATARIO = "mensagem.help.telefone.destinatario";
        public static final String NOME_DESTINATARIO = "mensagem.help.nome.destinatario";
        public static final String CORPO_MENSAGEM = "mensagem.help.corpo.mensagem";
        public static final String TIPO_CANAL = "mensagem.help.tipo.canal";
        public static final String STATUS_MENSAGEM = "mensagem.help.status.mensagem";
        public static final String ID_EXTERNO = "mensagem.help.id.externo";
        public static final String DATA_ENVIO = "mensagem.help.data.envio";
        public static final String DATA_ENTREGA = "mensagem.help.data.entrega";
        public static final String DATA_LEITURA = "mensagem.help.data.leitura";
        public static final String MOTIVO_FALHA = "mensagem.help.motivo.falha";
    }

    /**
     * Validation message keys
     */
    public static final class VALIDATION {
        public static final String TELEFONE_DESTINATARIO_NOT_BLANK = "mensagem.validation.telefone.destinatario.not.blank";
        public static final String TELEFONE_DESTINATARIO_SIZE = "mensagem.validation.telefone.destinatario.size";
        public static final String NOME_DESTINATARIO_SIZE = "mensagem.validation.nome.destinatario.size";
        public static final String CORPO_MENSAGEM_NOT_BLANK = "mensagem.validation.corpo.mensagem.not.blank";
        public static final String TIPO_CANAL_NOT_NULL = "mensagem.validation.tipo.canal.not.null";
        public static final String STATUS_MENSAGEM_NOT_NULL = "mensagem.validation.status.mensagem.not.null";
        public static final String ID_EXTERNO_SIZE = "mensagem.validation.id.externo.size";
        public static final String MOTIVO_FALHA_SIZE = "mensagem.validation.motivo.falha.size";
    }

    /**
     * Business rule message keys
     */
    public static final class RULE {
        public static final String CANAL_NAO_SUPORTADO = "mensagem.rule.canal.nao.suportado";
        public static final String TELEFONE_INVALIDO = "mensagem.rule.telefone.invalido";
        public static final String MODELO_NAO_ENCONTRADO = "mensagem.rule.modelo.nao.encontrado";
        public static final String GRUPO_NAO_ENCONTRADO = "mensagem.rule.grupo.nao.encontrado";
    }

    /**
     * Success/error message keys
     */
    public static final class MESSAGE {
        public static final String ENVIADO_SUCESSO = "mensagem.message.enviado.sucesso";
        public static final String ENVIADO_EM_MASSA_SUCESSO = "mensagem.message.enviado.em.massa.sucesso";
        public static final String STATUS_ATUALIZADO = "mensagem.message.status.atualizado";
        public static final String FALHA_ENVIO = "mensagem.message.falha.envio";
    }

    /**
     * Domain event message keys
     */
    public static final class EVENT {
        public static final String MENSAGEM_ENVIADA = "mensagem.event.enviada";
        public static final String MENSAGEM_ENTREGUE = "mensagem.event.entregue";
        public static final String MENSAGEM_LIDA = "mensagem.event.lida";
        public static final String MENSAGEM_FALHOU = "mensagem.event.falhou";
    }

    /**
     * DTO class canonical name
     */
    public static final String MENSAGEM_CLASS = MensagemDTO.class.getCanonicalName();

    /**
     * Field name constants
     */
    public static final String MENSAGEM = "mensagem";
    public static final String TELEFONE_DESTINATARIO = "mensagem.telefone.destinatario";
    public static final String NOME_DESTINATARIO = "mensagem.nome.destinatario";
    public static final String CORPO_MENSAGEM = "mensagem.corpo.mensagem";
    public static final String TIPO_CANAL = "mensagem.tipo.canal";
    public static final String STATUS_MENSAGEM = "mensagem.status.mensagem";
    public static final String ID_EXTERNO = "mensagem.id.externo";
    public static final String DATA_ENVIO = "mensagem.data.envio";
    public static final String DATA_ENTREGA = "mensagem.data.entrega";
    public static final String DATA_LEITURA = "mensagem.data.leitura";
    public static final String MOTIVO_FALHA = "mensagem.motivo.falha";
}
