package com.ia.core.communication.service.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Propriedades de configuração centralizada para o módulo Communication.
 *
 * <p>Permite configuração via YAML (application.yml) e programática.
 * Cada propriedade de comunicação pode ser configurada individualmente.</p>
 *
 * <p>Estrutura de configuração:</p>
 * <pre>
 * ia-core:
 *   communication:
 *     sms:
 *       enabled: false
 *       provider: twilio
 *       account-sid: ""
 *       auth-token: ""
 *       from-number: ""
 *       timeout: 30000
 *     email:
 *       enabled: false
 *       host: localhost
 *       port: 587
 *       username: ""
 *       password: ""
 *       from-address: noreply@example.com
 *       use-tls: true
 *     whatsapp:
 *       enabled: false
 *       api-url: ""
 *       api-key: ""
 *     telegram:
 *       enabled: false
 *       bot-token: ""
 *       api-url: ""
 *     notification:
 *       default-channel: email
 *       async-enabled: true
 * </pre>
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "ia-core.communication")
public class CommunicationProperties {

    /**
     * Configurações de SMS.
     */
    private SmsConfig sms = new SmsConfig();

    /**
     * Configurações de E-mail.
     */
    private EmailConfig email = new EmailConfig();

    /**
     * Configurações de WhatsApp.
     */
    private WhatsAppConfig whatsapp = new WhatsAppConfig();

    /**
     * Configurações de Telegram.
     */
    private TelegramConfig telegram = new TelegramConfig();

    /**
     * Configurações de notificações gerais.
     */
    private NotificationConfig notification = new NotificationConfig();

    @Getter
    @Setter
    public static class SmsConfig {
        /**
         * Habilitar envio de SMS.
         */
        private boolean enabled = false;

        /**
         * Provedor de SMS.
         */
        private String provider = "twilio";

        /**
         * Account SID do provedor.
         */
        private String accountSid = "";

        /**
         * Auth token do provedor.
         */
        private String authToken = "";

        /**
         * Número de origem.
         */
        private String fromNumber = "";

        /**
         * Timeout em milissegundos.
         */
        private int timeout = 30000;

        /**
         * Versão da API.
         */
        private String apiVersion = "2010-04-01";

        /**
         * SID do serviço de mensagens.
         */
        private String messagingServiceSid = "";
    }

    @Getter
    @Setter
    public static class EmailConfig {
        /**
         * Habilitar envio de e-mail.
         */
        private boolean enabled = false;

        /**
         * Servidor SMTP.
         */
        private String host = "localhost";

        /**
         * Porta SMTP.
         */
        private int port = 587;

        /**
         * Usuário SMTP.
         */
        private String username = "";

        /**
         * Senha SMTP.
         */
        private String password = "";

        /**
         * E-mail de origem.
         */
        private String fromAddress = "noreply@example.com";

        /**
         * Usar TLS.
         */
        private boolean useTls = true;

        /**
         * Protocolo SMTP.
         */
        private String protocol = "smtp";

        /**
         * Timeout de conexão em milissegundos.
         */
        private int connectionTimeout = 10000;

        /**
         * Timeout de escrita em milissegundos.
         */
        private int writeTimeout = 10000;

        /**
         * Timeout de leitura em milissegundos.
         */
        private int readTimeout = 10000;

        /**
         * Habilitar debug de e-mail.
         */
        private boolean debug = false;
    }

    @Getter
    @Setter
    public static class WhatsAppConfig {
        /**
         * Habilitar WhatsApp.
         */
        private boolean enabled = false;

        /**
         * URL da API.
         */
        private String apiUrl = "";

        /**
         * Chave da API.
         */
        private String apiKey = "";

        /**
         * ID do número de telefone.
         */
        private String phoneNumberId = "";

        /**
         * ID da conta business.
         */
        private String businessAccountId = "";
    }

    @Getter
    @Setter
    public static class TelegramConfig {
        /**
         * Habilitar Telegram.
         */
        private boolean enabled = false;

        /**
         * Token do bot.
         */
        private String botToken = "";

        /**
         * URL da API.
         */
        private String apiUrl = "";

        /**
         * Modo de parse.
         */
        private String parseMode = "Markdown";

        /**
         * URL do webhook.
         */
        private String webhookUrl = "";

        /**
         * ID do chat padrão para envio de mensagens.
         */
        private String chatId = "";

        /**
         * Habilitar parse mode HTML.
         */
        private boolean enableHtml = true;

        /**
         * Timeout em milissegundos para requisições.
         */
        private int timeout = 30000;
    }

    @Getter
    @Setter
    public static class NotificationConfig {
        /**
         * Canal padrão de notificação.
         */
        private String defaultChannel = "email";

        /**
         * Envio assíncrono.
         */
        private boolean asyncEnabled = true;

        /**
         * Tentativas de reenvio.
         */
        private int retryAttempts = 3;

        /**
         * Atraso entre reenvios em milissegundos.
         */
        private long retryDelay = 5000;

        /**
         * Habilitar templates.
         */
        private boolean templatesEnabled = true;
    }
}
