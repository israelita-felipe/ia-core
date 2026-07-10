package com.ia.core.llm.service.model.chat;

/**
 * Translator constants for Chat DTO.
 * <p>
 * Contains constants for i18n keys, validation messages, and field names
 * used throughout the Chat DTO processing pipeline.
 *
 * @author Israel Araújo
 * @since 1.0.0
 * @see ChatDTO
 */
public final class ChatTranslator {

    private ChatTranslator() {
        // Utility class
    }

    /**
     * Validation message keys
     */
    public static final class VALIDATION {
        public static final String SESSION_ID_REQUIRED = "chat.validation.sessionId.required";
        public static final String SESSION_ID_SIZE = "chat.validation.sessionId.size";
        public static final String REQUEST_REQUIRED = "chat.validation.request.required";
        public static final String REQUEST_SIZE = "chat.validation.request.size";
        public static final String TITULO_SIZE = "chat.validation.titulo.size";
        public static final String USUARIO_ID_SIZE = "chat.validation.usuarioId.size";
        public static final String AGENTE_REQUIRED = "chat.validation.agente.required";
        public static final String DATA_INICIO_REQUIRED = "chat.validation.dataInicio.required";
    }

    /**
     * Help text i18n keys
     */
    public static final class HELP {
        public static final String CHAT = "chat.help";
        public static final String FREE_CHAT = "chat.help.free";
        public static final String TEMPLATE_CHAT = "chat.help.template";
    }

    /**
     * Business rule message keys
     */
    public static final class RULE {
        public static final String CHAT_INVALIDO = "chat.rule.chat.invalido";
    }

    /**
     * Error message keys
     */
    public static final class ERROR {
        public static final String NOT_FOUND = "chat.error.notfound";
        public static final String DUPLICATE = "chat.error.duplicate";
    }

    /**
     * Success message keys
     */
    public static final class MESSAGE {
        public static final String CREATED = "chat.message.created";
        public static final String UPDATED = "chat.message.updated";
        public static final String DELETED = "chat.message.deleted";
    }

    /**
     * Domain event message keys
     */
    public static final class EVENT {
        public static final String CHAT_CRIADO = "chat.event.criado";
        public static final String CHAT_ENVIADO = "chat.event.enviado";
    }

    /**
     * DTO class canonical name
     */
    public static final String CHAT_CLASS = ChatRequestDTO.class.getCanonicalName();

    /**
     * Field name constants
     */
    public static final String CHAT = "chat";
    public static final String FREE_CHAT = "chat.free";
    public static final String TEMPLATE_CHAT = "chat.template";
    public static final String SESSION_ID = "chat.sessionId";
    public static final String TITULO = "chat.titulo";
    public static final String STATUS = "chat.status";
    public static final String USUARIO_ID = "chat.usuarioId";
    public static final String AGENTE = "chat.agente";
}