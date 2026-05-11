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
     * Help text i18n keys
     */
    public static final class HELP {
        public static final String CHAT = "chat.help";
        public static final String FREE_CHAT = "chat.help.free";
        public static final String TEMPLATE_CHAT = "chat.help.template";
    }

    /**
     * Validation message keys
     */
    public static final class VALIDATION {
        public static final String CHAT_NOT_BLANK = "chat.validation.chat.not.blank";
    }

    /**
     * Business rule message keys
     */
    public static final class RULE {
        public static final String CHAT_INVALIDO = "chat.rule.chat.invalido";
    }

    /**
     * Success/error message keys
     */
    public static final class MESSAGE {
        public static final String CRIADO_SUCESSO = "chat.message.criado.sucesso";
        public static final String ATUALIZADO_SUCESSO = "chat.message.atualizado.sucesso";
        public static final String DELETADO_SUCESSO = "chat.message.deletado.sucesso";
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
}
