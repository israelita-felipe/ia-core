package com.ia.core.communication.service.config;

/**
 * Constantes para as chaves de propriedades do módulo Communication.
 *
 * <p>Estas constantes são usadas internamente por {@link CommunicationProperties}
 * e podem ser usadas para referenciar as chaves de configuração.</p>
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
public final class CommunicationPropertiesConstants {

    // =========================================================================
    // SMS Properties (ia-core.communication.sms.*)
    // =========================================================================
    /**
     * Property key: ia-core.communication.sms.enabled
     */
    public static final String SMS_ENABLED = "sms.enabled";

    /**
     * Property key: ia-core.communication.sms.provider
     */
    public static final String SMS_PROVIDER = "sms.provider";

    /**
     * Property key: ia-core.communication.sms.account-sid
     */
    public static final String SMS_ACCOUNT_SID = "sms.account-sid";

    /**
     * Property key: ia-core.communication.sms.auth-token
     */
    public static final String SMS_AUTH_TOKEN = "sms.auth-token";

    /**
     * Property key: ia-core.communication.sms.from-number
     */
    public static final String SMS_FROM_NUMBER = "sms.from-number";

    /**
     * Property key: ia-core.communication.sms.timeout
     */
    public static final String SMS_TIMEOUT = "sms.timeout";

    /**
     * Property key: ia-core.communication.sms.api-version
     */
    public static final String SMS_API_VERSION = "sms.api-version";

    /**
     * Property key: ia-core.communication.sms.messaging-service-sid
     */
    public static final String SMS_MESSAGING_SERVICE_SID = "sms.messaging-service-sid";

    // =========================================================================
    // Email Properties (ia-core.communication.email.*)
    // =========================================================================
    /**
     * Property key: ia-core.communication.email.enabled
     */
    public static final String EMAIL_ENABLED = "email.enabled";

    /**
     * Property key: ia-core.communication.email.host
     */
    public static final String EMAIL_HOST = "email.host";

    /**
     * Property key: ia-core.communication.email.port
     */
    public static final String EMAIL_PORT = "email.port";

    /**
     * Property key: ia-core.communication.email.username
     */
    public static final String EMAIL_USERNAME = "email.username";

    /**
     * Property key: ia-core.communication.email.password
     */
    public static final String EMAIL_PASSWORD = "email.password";

    /**
     * Property key: ia-core.communication.email.from-address
     */
    public static final String EMAIL_FROM_ADDRESS = "email.from-address";

    /**
     * Property key: ia-core.communication.email.use-tls
     */
    public static final String EMAIL_USE_TLS = "email.use-tls";

    /**
     * Property key: ia-core.communication.email.protocol
     */
    public static final String EMAIL_PROTOCOL = "email.protocol";

    /**
     * Property key: ia-core.communication.email.connection-timeout
     */
    public static final String EMAIL_CONNECTION_TIMEOUT = "email.connection-timeout";

    /**
     * Property key: ia-core.communication.email.write-timeout
     */
    public static final String EMAIL_WRITE_TIMEOUT = "email.write-timeout";

    /**
     * Property key: ia-core.communication.email.read-timeout
     */
    public static final String EMAIL_READ_TIMEOUT = "email.read-timeout";

    /**
     * Property key: ia-core.communication.email.debug
     */
    public static final String EMAIL_DEBUG = "email.debug";

    // =========================================================================
    // WhatsApp Properties (ia-core.communication.whatsapp.*)
    // =========================================================================
    /**
     * Property key: ia-core.communication.whatsapp.enabled
     */
    public static final String WHATSAPP_ENABLED = "whatsapp.enabled";

    /**
     * Property key: ia-core.communication.whatsapp.api-url
     */
    public static final String WHATSAPP_API_URL = "whatsapp.api-url";

    /**
     * Property key: ia-core.communication.whatsapp.api-key
     */
    public static final String WHATSAPP_API_KEY = "whatsapp.api-key";

    /**
     * Property key: ia-core.communication.whatsapp.phone-number-id
     */
    public static final String WHATSAPP_PHONE_NUMBER_ID = "whatsapp.phone-number-id";

    /**
     * Property key: ia-core.communication.whatsapp.business-account-id
     */
    public static final String WHATSAPP_BUSINESS_ACCOUNT_ID = "whatsapp.business-account-id";

    // =========================================================================
    // Telegram Properties (ia-core.communication.telegram.*)
    // =========================================================================
    /**
     * Property key: ia-core.communication.telegram.enabled
     */
    public static final String TELEGRAM_ENABLED = "telegram.enabled";

    /**
     * Property key: ia-core.communication.telegram.bot-token
     */
    public static final String TELEGRAM_BOT_TOKEN = "telegram.bot-token";

    /**
     * Property key: ia-core.communication.telegram.api-url
     */
    public static final String TELEGRAM_API_URL = "telegram.api-url";

    /**
     * Property key: ia-core.communication.telegram.parse-mode
     */
    public static final String TELEGRAM_PARSE_MODE = "telegram.parse-mode";

    /**
     * Property key: ia-core.communication.telegram.webhook-url
     */
    public static final String TELEGRAM_WEBHOOK_URL = "telegram.webhook-url";

    /**
     * Property key: ia-core.communication.telegram.chat-id
     */
    public static final String TELEGRAM_CHAT_ID = "telegram.chat-id";

    /**
     * Property key: ia-core.communication.telegram.enable-html
     */
    public static final String TELEGRAM_ENABLE_HTML = "telegram.enable-html";

    /**
     * Property key: ia-core.communication.telegram.timeout
     */
    public static final String TELEGRAM_TIMEOUT = "telegram.timeout";

    // =========================================================================
    // Notification Properties (ia-core.communication.notification.*)
    // =========================================================================
    /**
     * Property key: ia-core.communication.notification.default-channel
     */
    public static final String NOTIFICATION_DEFAULT_CHANNEL = "notification.default-channel";

    /**
     * Property key: ia-core.communication.notification.async-enabled
     */
    public static final String NOTIFICATION_ASYNC_ENABLED = "notification.async-enabled";

    /**
     * Property key: ia-core.communication.notification.retry-attempts
     */
    public static final String NOTIFICATION_RETRY_ATTEMPTS = "notification.retry-attempts";

    /**
     * Property key: ia-core.communication.notification.retry-delay
     */
    public static final String NOTIFICATION_RETRY_DELAY = "notification.retry-delay";

    /**
     * Property key: ia-core.communication.notification.templates-enabled
     */
    public static final String NOTIFICATION_TEMPLATES_ENABLED = "notification.templates-enabled";

    private CommunicationPropertiesConstants() {
        // Utility class
    }
}
