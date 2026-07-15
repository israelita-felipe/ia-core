package com.ia.core.llm.service.model.session;

/**
 * Translator constants for session DTOs.
 * <p>
 * Contains constants for i18n keys, validation messages, and field names
 * used throughout the session DTO processing pipeline.
 *
 * @author Israel Araújo
 * @since 1.0.0
 * @see AgentSessionRequestDTO
 * @see AgentConfirmationDTO
 * @see AgentSessionResponseDTO
 */
public final class SessionTranslator {

    private SessionTranslator() {
        // Utility class
    }

    /**
     * Validation message keys
     */
    public static final class VALIDATION {
        public static final String USER_MESSAGE_NOT_BLANK = "session.validation.user.message.not.blank";
    }

    /**
     * Error message keys
     */
    public static final class ERROR {
        public static final String SESSION_NOT_FOUND = "session.error.notfound";
    }

    /**
     * Success message keys
     */
    public static final class MESSAGE {
        public static final String EXECUTED = "session.message.executed";
        public static final String CONFIRMED = "session.message.confirmed";
    }

    /**
     * Domain event message keys
     */
    public static final class EVENT {
        public static final String SESSION_STARTED = "session.event.started";
        public static final String SESSION_ENDED = "session.event.ended";
    }

    /**
     * Field name constants
     */
    public static final String SESSION = "session";
    public static final String SESSION_ID = "session.id";
    public static final String USER_MESSAGE = "session.user.message";
    public static final String CONFIRMED = "session.confirmed";
    public static final String PENDING_CONFIRMATION = "session.pending.confirmation";
}