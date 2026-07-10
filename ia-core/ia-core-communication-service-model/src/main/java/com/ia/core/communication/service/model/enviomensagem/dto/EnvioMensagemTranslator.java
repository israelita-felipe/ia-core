package com.ia.core.communication.service.model.enviomensagem.dto;

/**
 * Constantes de tradução para EnvioMensagemDTO.
 * <p>
 * Contém constantes para chaves i18n, mensagens de validação e nomes de campos
 * utilizados no pipeline de processamento do DTO de EnvioMensagem.
 *
 * @author Israel Araújo
 * @since 1.0.0
 * @see EnvioMensagemRequestDTO
 */
public final class EnvioMensagemTranslator {

    private EnvioMensagemTranslator() {
        // Utility class
    }

    /**
     * Validation message keys
     */
    public static final class VALIDATION {
        public static final String TIPO_CANAL_NOT_NULL = "envio.mensagem.validation.tipo.canal.not.null";
        public static final String CORPO_MENSAGEM_NOT_BLANK = "envio.mensagem.validation.corpo.mensagem.not.blank";
        public static final String MODELO_MENSAGEM_ID_NULL = "envio.mensagem.validation.modelo.mensagem.id.not.null";
        public static final String TELEFONES_NOT_EMPTY = "envio.mensagem.validation.telefones.not.empty";
    }

    /**
     * Help text i18n keys
     */
    public static final class HELP {
        public static final String ENVIO_MENSAGEM = "envio.mensagem.help";
        public static final String TIPO_CANAL = "envio.mensagem.help.tipo.canal";
        public static final String CORPO_MENSAGEM = "envio.mensagem.help.corpo.mensagem";
        public static final String MODELO_MENSAGEM_ID = "envio.mensagem.help.modelo.mensagem.id";
        public static final String PARAMETROS_TEMPLATE = "envio.mensagem.help.parametros.template";
        public static final String TELEFONES = "envio.mensagem.help.telefones";
        public static final String GRUPOS_CONTATO_IDS = "envio.mensagem.help.grupos.contato.ids";
        public static final String AGENDADO = "envio.mensagem.help.agendado";
        public static final String DATA_AGENDAMENTO = "envio.mensagem.help.data.agendamento";
    }

    /**
     * Business rule message keys
     */
    public static final class RULE {
        public static final String CANAL_NAO_SUPORTADO = "envio.mensagem.rule.canal.nao.suportado";
        public static final String MODELO_NAO_ENCONTRADO = "envio.mensagem.rule.modelo.nao.encontrado";
        public static final String TELEFONE_INVALIDO = "envio.mensagem.rule.telefone.invalido";
        public static final String AGENDAMENTO_PASSADO = "envio.mensagem.rule.agendamento.passado";
    }

    /**
     * Error message keys
     */
    public static final class ERROR {
        public static final String NOT_FOUND = "envio.mensagem.error.notfound";
        public static final String DUPLICATE = "envio.mensagem.error.duplicate";
    }

    /**
     * Success message keys
     */
    public static final class MESSAGE {
        public static final String SENT = "envio.mensagem.message.sent";
        public static final String SCHEDULED = "envio.mensagem.message.scheduled";
        public static final String SEND_ERROR = "envio.mensagem.message.send.error";
    }

    /**
     * Domain event message keys
     */
    public static final class EVENT {
        public static final String MENSAGEM_ENVIADA = "envio.mensagem.event.enviada";
        public static final String MENSAGEM_AGENDADA = "envio.mensagem.event.agendada";
        public static final String MENSAGEM_ERRO = "envio.mensagem.event.erro";
    }

    /**
     * Field name constants
     */
    public static final String ENVIO_MENSAGEM = "envio.mensagem";
    public static final String TIPO_CANAL = "envio.mensagem.tipo.canal";
    public static final String CORPO_MENSAGEM = "envio.mensagem.corpo.mensagem";
    public static final String MODELO_MENSAGEM_ID = "envio.mensagem.modelo.mensagem.id";
    public static final String PARAMETROS_TEMPLATE = "envio.mensagem.parametros.template";
    public static final String TELEFONES = "envio.mensagem.telefones";
    public static final String GRUPOS_CONTATO_IDS = "envio.mensagem.grupos.contato.ids";
    public static final String AGENDADO = "envio.mensagem.agendado";
    public static final String DATA_AGENDAMENTO = "envio.mensagem.data.agendamento";
    public static final String ENVIO_MENSAGEM_CLASS = EnvioMensagemRequestDTO.class.getCanonicalName();
}