package com.ia.core.llm.service.model.chat;

/**
 * Translator constants for ChatRequest DTO.
 * <p>
 * Contains constants for i18n keys, validation messages, and field names
 * used throughout the ChatRequest DTO processing pipeline.
 *
 * @author Israel Araújo
 * @since 1.0.0
 * @see com.ia.core.llm.service.model.chat.ChatRequestDTO
 */
public final class ChatRequestTranslator {

    private ChatRequestTranslator() {
        // Utility class
    }

    /**
     * Help text i18n keys
     */
    public static final class HELP {
        public static final String CHAT_REQUEST = "chat.request.help";
        public static final String PROMPT_ID = "chat.request.help.prompt.id";
        public static final String REQUEST = "chat.request.help.request";
        public static final String TEXT = "chat.request.help.text";
    }

    /**
     * DTO class canonical name
     */
    public static final String CHAT_REQUEST_CLASS = ChatRequestDTO.class.getCanonicalName();

    /**
     * Field name constants
     */
    public static final String CHAT_REQUEST = "chat.request";
    public static final String PROMPT_ID = "chat.request.prompt.id";
    public static final String REQUEST = "chat.request.request";
    public static final String TEXT = "chat.request.text";

    /**
     * Validation message keys
     */
    public static final class VALIDATION {
        public static final String REQUEST_REQUIRED = "validation.chat.request.required";
        public static final String REQUEST_SIZE = "validation.chat.request.size";
    }

    /**
     * Business rule message keys
     */
    public static final class RULE {
        public static final String PROMPT_INVALIDO = "chat.request.rule.prompt.invalido";
        public static final String CONTEXTO_OBRIGATORIO = "chat.request.rule.contexto.obrigatorio";
    }

    /**
     * Success/error message keys
     */
    public static final class MESSAGE {
        public static final String RESPOSTA_GERADA = "chat.request.message.resposta.gerada";
        public static final String ERRO_PROCESSAMENTO = "chat.request.message.erro.processamento";
    }

    /**
     * Domain event message keys
     */
    public static final class EVENT {
        public static final String CHAT_REQUISITADO = "chat.request.event.requisitado";
        public static final String RESPOSTA_RECEBIDA = "chat.request.event.resposta.recebida";
    }
}
