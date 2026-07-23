package com.ia.core.communication.service.config;

import com.ia.core.communication.model.CommunicationModel;
import com.ia.core.model.configuracao.TipoConfiguracao;
import com.ia.core.service.configuracao.ConfigurationProvider;
import com.ia.core.service.configuracao.dto.ConfiguracaoSistemaDTO;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

/**
 * Provider de configuração para o módulo Communication.
 * <p>
 * Fornece configurações específicas para comunicação do sistema,
 * incluindo SMS, e-mail, WhatsApp e Telegram.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@Component
@EnableConfigurationProperties(CommunicationProperties.class)
public class CommunicationConfigurationProvider implements ConfigurationProvider {

    private static final Set<String> ALLOWED_CHAVES = Set.of(
        CommunicationPropertiesConstants.SMS_ENABLED,
        CommunicationPropertiesConstants.SMS_PROVIDER,
        CommunicationPropertiesConstants.SMS_ACCOUNT_SID,
        CommunicationPropertiesConstants.SMS_AUTH_TOKEN,
        CommunicationPropertiesConstants.SMS_FROM_NUMBER,
        CommunicationPropertiesConstants.SMS_TIMEOUT,
        CommunicationPropertiesConstants.SMS_API_VERSION,
        CommunicationPropertiesConstants.SMS_MESSAGING_SERVICE_SID,
        CommunicationPropertiesConstants.EMAIL_ENABLED,
        CommunicationPropertiesConstants.EMAIL_HOST,
        CommunicationPropertiesConstants.EMAIL_PORT,
        CommunicationPropertiesConstants.EMAIL_USERNAME,
        CommunicationPropertiesConstants.EMAIL_PASSWORD,
        CommunicationPropertiesConstants.EMAIL_FROM_ADDRESS,
        CommunicationPropertiesConstants.EMAIL_USE_TLS,
        CommunicationPropertiesConstants.EMAIL_PROTOCOL,
        CommunicationPropertiesConstants.EMAIL_CONNECTION_TIMEOUT,
        CommunicationPropertiesConstants.EMAIL_WRITE_TIMEOUT,
        CommunicationPropertiesConstants.EMAIL_READ_TIMEOUT,
        CommunicationPropertiesConstants.EMAIL_DEBUG,
        CommunicationPropertiesConstants.WHATSAPP_ENABLED,
        CommunicationPropertiesConstants.WHATSAPP_API_URL,
        CommunicationPropertiesConstants.WHATSAPP_API_KEY,
        CommunicationPropertiesConstants.WHATSAPP_PHONE_NUMBER_ID,
        CommunicationPropertiesConstants.WHATSAPP_BUSINESS_ACCOUNT_ID,
         CommunicationPropertiesConstants.TELEGRAM_ENABLED,
         CommunicationPropertiesConstants.TELEGRAM_BOT_TOKEN,
         CommunicationPropertiesConstants.TELEGRAM_API_URL,
         CommunicationPropertiesConstants.TELEGRAM_PARSE_MODE,
         CommunicationPropertiesConstants.TELEGRAM_WEBHOOK_URL,
         CommunicationPropertiesConstants.TELEGRAM_CHAT_ID,
         CommunicationPropertiesConstants.TELEGRAM_ENABLE_HTML,
         CommunicationPropertiesConstants.TELEGRAM_TIMEOUT,
        CommunicationPropertiesConstants.NOTIFICATION_DEFAULT_CHANNEL,
        CommunicationPropertiesConstants.NOTIFICATION_ASYNC_ENABLED,
        CommunicationPropertiesConstants.NOTIFICATION_RETRY_ATTEMPTS,
        CommunicationPropertiesConstants.NOTIFICATION_RETRY_DELAY,
        CommunicationPropertiesConstants.NOTIFICATION_TEMPLATES_ENABLED
    );

    @Getter
    private final CommunicationProperties communicationProperties;

    @Getter
    private final Properties properties;

    @Getter
    private List<ConfiguracaoSistemaDTO<?>> configurations;

    public CommunicationConfigurationProvider(CommunicationProperties communicationProperties) {
        this.communicationProperties = communicationProperties;
        this.properties = createProperties();
        this.configurations = buildConfigurations();
    }

    @Override
    public List<ConfiguracaoSistemaDTO<?>> getConfigurations() {
        return configurations;
    }

    @Override
    public String getModulo() {
        return CommunicationModel.NAME;
    }

    @Override
    public void validar(ConfiguracaoSistemaDTO<?> config) {
        if (!ALLOWED_CHAVES.contains(config.getChave())) {
            log.debug("Chave de configuração ignorada: {}", config.getChave());
            return;
        }

        switch (config.getChave()) {
            case CommunicationPropertiesConstants.SMS_TIMEOUT:
            case CommunicationPropertiesConstants.EMAIL_PORT:
                if (config.getValor() != null) {
                    try {
                        int value = Integer.parseInt(config.getValor());
                        if (value <= 0 || value > 65535) {
                            throw new IllegalArgumentException(
                                "Valor deve estar entre 1 e 65535: " + config.getValor());
                        }
                    } catch (NumberFormatException e) {
                        throw new IllegalArgumentException("Valor inválido: " + config.getValor());
                    }
                }
                break;
            case CommunicationPropertiesConstants.SMS_ENABLED,
                 CommunicationPropertiesConstants.EMAIL_ENABLED,
                 CommunicationPropertiesConstants.WHATSAPP_ENABLED,
                 CommunicationPropertiesConstants.TELEGRAM_ENABLED,
                 CommunicationPropertiesConstants.EMAIL_USE_TLS,
                 CommunicationPropertiesConstants.NOTIFICATION_ASYNC_ENABLED,
                 CommunicationPropertiesConstants.NOTIFICATION_TEMPLATES_ENABLED,
                 CommunicationPropertiesConstants.TELEGRAM_ENABLE_HTML:
                if (config.getValor() != null) {
                    Boolean.parseBoolean(config.getValor());
                }
                break;
            case CommunicationPropertiesConstants.EMAIL_CONNECTION_TIMEOUT,
                 CommunicationPropertiesConstants.EMAIL_WRITE_TIMEOUT,
                 CommunicationPropertiesConstants.EMAIL_READ_TIMEOUT,
                 CommunicationPropertiesConstants.NOTIFICATION_RETRY_ATTEMPTS,
                 CommunicationPropertiesConstants.TELEGRAM_TIMEOUT:
                if (config.getValor() != null) {
                    try {
                        int value = Integer.parseInt(config.getValor());
                        if (value < 0) {
                            throw new IllegalArgumentException(
                                "Valor deve ser não negativo: " + config.getValor());
                        }
                    } catch (NumberFormatException e) {
                        throw new IllegalArgumentException("Valor inválido: " + config.getValor());
                    }
                }
                break;
            default:
                break;
        }
        log.debug("Validação concluída para configuração Communication: {}", config.getChave());
    }

    @Override
    public void aplicar(ConfiguracaoSistemaDTO<?> config) {
        validar(config);

        String chave = config.getChave();
        String valor = config.getValor();

        int index = configurations.indexOf(config);
        if (index != -1) {
            configurations.set(index, config);
        } else {
            configurations.add(config);
        }

        CommunicationProperties.SmsConfig sms = communicationProperties.getSms();
        CommunicationProperties.EmailConfig email = communicationProperties.getEmail();
        CommunicationProperties.WhatsAppConfig whatsapp = communicationProperties.getWhatsapp();
        CommunicationProperties.TelegramConfig telegram = communicationProperties.getTelegram();
        CommunicationProperties.NotificationConfig notification = communicationProperties.getNotification();

        switch (chave) {
            case CommunicationPropertiesConstants.SMS_ENABLED -> sms.setEnabled(Boolean.parseBoolean(valor));
            case CommunicationPropertiesConstants.SMS_PROVIDER -> sms.setProvider(valor);
            case CommunicationPropertiesConstants.SMS_ACCOUNT_SID -> sms.setAccountSid(valor);
            case CommunicationPropertiesConstants.SMS_AUTH_TOKEN -> sms.setAuthToken(valor);
            case CommunicationPropertiesConstants.SMS_FROM_NUMBER -> sms.setFromNumber(valor);
            case CommunicationPropertiesConstants.SMS_TIMEOUT -> sms.setTimeout(Integer.parseInt(valor));
            case CommunicationPropertiesConstants.SMS_API_VERSION -> sms.setApiVersion(valor);
            case CommunicationPropertiesConstants.SMS_MESSAGING_SERVICE_SID -> sms.setMessagingServiceSid(valor);
            case CommunicationPropertiesConstants.EMAIL_ENABLED -> email.setEnabled(Boolean.parseBoolean(valor));
            case CommunicationPropertiesConstants.EMAIL_HOST -> email.setHost(valor);
            case CommunicationPropertiesConstants.EMAIL_PORT -> email.setPort(Integer.parseInt(valor));
            case CommunicationPropertiesConstants.EMAIL_USERNAME -> email.setUsername(valor);
            case CommunicationPropertiesConstants.EMAIL_PASSWORD -> email.setPassword(valor);
            case CommunicationPropertiesConstants.EMAIL_FROM_ADDRESS -> email.setFromAddress(valor);
            case CommunicationPropertiesConstants.EMAIL_USE_TLS -> email.setUseTls(Boolean.parseBoolean(valor));
            case CommunicationPropertiesConstants.EMAIL_PROTOCOL -> email.setProtocol(valor);
            case CommunicationPropertiesConstants.EMAIL_CONNECTION_TIMEOUT -> email.setConnectionTimeout(Integer.parseInt(valor));
            case CommunicationPropertiesConstants.EMAIL_WRITE_TIMEOUT -> email.setWriteTimeout(Integer.parseInt(valor));
            case CommunicationPropertiesConstants.EMAIL_READ_TIMEOUT -> email.setReadTimeout(Integer.parseInt(valor));
            case CommunicationPropertiesConstants.EMAIL_DEBUG -> email.setDebug(Boolean.parseBoolean(valor));
            case CommunicationPropertiesConstants.WHATSAPP_ENABLED -> whatsapp.setEnabled(Boolean.parseBoolean(valor));
            case CommunicationPropertiesConstants.WHATSAPP_API_URL -> whatsapp.setApiUrl(valor);
            case CommunicationPropertiesConstants.WHATSAPP_API_KEY -> whatsapp.setApiKey(valor);
            case CommunicationPropertiesConstants.WHATSAPP_PHONE_NUMBER_ID -> whatsapp.setPhoneNumberId(valor);
            case CommunicationPropertiesConstants.WHATSAPP_BUSINESS_ACCOUNT_ID -> whatsapp.setBusinessAccountId(valor);
            case CommunicationPropertiesConstants.TELEGRAM_ENABLED -> telegram.setEnabled(Boolean.parseBoolean(valor));
            case CommunicationPropertiesConstants.TELEGRAM_BOT_TOKEN -> telegram.setBotToken(valor);
            case CommunicationPropertiesConstants.TELEGRAM_API_URL -> telegram.setApiUrl(valor);
            case CommunicationPropertiesConstants.TELEGRAM_PARSE_MODE -> telegram.setParseMode(valor);
            case CommunicationPropertiesConstants.TELEGRAM_WEBHOOK_URL -> telegram.setWebhookUrl(valor);
            case CommunicationPropertiesConstants.TELEGRAM_CHAT_ID -> telegram.setChatId(valor);
            case CommunicationPropertiesConstants.TELEGRAM_ENABLE_HTML -> telegram.setEnableHtml(Boolean.parseBoolean(valor));
            case CommunicationPropertiesConstants.TELEGRAM_TIMEOUT -> telegram.setTimeout(Integer.parseInt(valor));
            case CommunicationPropertiesConstants.NOTIFICATION_DEFAULT_CHANNEL -> notification.setDefaultChannel(valor);
            case CommunicationPropertiesConstants.NOTIFICATION_ASYNC_ENABLED -> notification.setAsyncEnabled(Boolean.parseBoolean(valor));
            case CommunicationPropertiesConstants.NOTIFICATION_RETRY_ATTEMPTS -> notification.setRetryAttempts(Integer.parseInt(valor));
            case CommunicationPropertiesConstants.NOTIFICATION_RETRY_DELAY -> notification.setRetryDelay(Long.parseLong(valor));
            case CommunicationPropertiesConstants.NOTIFICATION_TEMPLATES_ENABLED -> notification.setTemplatesEnabled(Boolean.parseBoolean(valor));
            default -> {
                log.debug("Chave de configuração Communication não mapeada para aplicação: {}", chave);
                return;
            }
        }

        this.properties.clear();
        this.properties.putAll(createProperties());

        log.info("Configuração Communication aplicada: chave={}, valor={}", chave, valor);
    }

    private List<ConfiguracaoSistemaDTO<?>> buildConfigurations() {
        List<ConfiguracaoSistemaDTO<?>> configs = new ArrayList<>();

        CommunicationProperties.SmsConfig sms = communicationProperties.getSms();
        CommunicationProperties.EmailConfig email = communicationProperties.getEmail();
        CommunicationProperties.WhatsAppConfig whatsapp = communicationProperties.getWhatsapp();
        CommunicationProperties.TelegramConfig telegram = communicationProperties.getTelegram();
        CommunicationProperties.NotificationConfig notification = communicationProperties.getNotification();

        // SMS
        add(configs, CommunicationPropertiesConstants.SMS_ENABLED, sms.isEnabled(), TipoConfiguracao.BOOLEAN, "SMS", "Habilitar envio de SMS");
        add(configs, CommunicationPropertiesConstants.SMS_PROVIDER, sms.getProvider(), TipoConfiguracao.STRING, "SMS", "Provedor de SMS");
        add(configs, CommunicationPropertiesConstants.SMS_ACCOUNT_SID, sms.getAccountSid(), TipoConfiguracao.STRING, "SMS", "Account SID do provedor");
        add(configs, CommunicationPropertiesConstants.SMS_AUTH_TOKEN, sms.getAuthToken(), TipoConfiguracao.STRING, "SMS", "Auth token do provedor");
        add(configs, CommunicationPropertiesConstants.SMS_FROM_NUMBER, sms.getFromNumber(), TipoConfiguracao.STRING, "SMS", "Número de origem");
        add(configs, CommunicationPropertiesConstants.SMS_TIMEOUT, sms.getTimeout(), TipoConfiguracao.INTEGER, "SMS", "Timeout em milissegundos");
        add(configs, CommunicationPropertiesConstants.SMS_API_VERSION, sms.getApiVersion(), TipoConfiguracao.STRING, "SMS", "Versão da API");
        add(configs, CommunicationPropertiesConstants.SMS_MESSAGING_SERVICE_SID, sms.getMessagingServiceSid(), TipoConfiguracao.STRING, "SMS", "SID do serviço de mensagens");

        // Email
        add(configs, CommunicationPropertiesConstants.EMAIL_ENABLED, email.isEnabled(), TipoConfiguracao.BOOLEAN, "E-mail", "Habilitar envio de e-mail");
        add(configs, CommunicationPropertiesConstants.EMAIL_HOST, email.getHost(), TipoConfiguracao.STRING, "E-mail", "Servidor SMTP");
        add(configs, CommunicationPropertiesConstants.EMAIL_PORT, email.getPort(), TipoConfiguracao.INTEGER, "E-mail", "Porta SMTP");
        add(configs, CommunicationPropertiesConstants.EMAIL_USERNAME, email.getUsername(), TipoConfiguracao.STRING, "E-mail", "Usuário SMTP");
        add(configs, CommunicationPropertiesConstants.EMAIL_PASSWORD, email.getPassword(), TipoConfiguracao.STRING, "E-mail", "Senha SMTP");
        add(configs, CommunicationPropertiesConstants.EMAIL_FROM_ADDRESS, email.getFromAddress(), TipoConfiguracao.STRING, "E-mail", "E-mail de origem");
        add(configs, CommunicationPropertiesConstants.EMAIL_USE_TLS, email.isUseTls(), TipoConfiguracao.BOOLEAN, "E-mail", "Usar TLS");
        add(configs, CommunicationPropertiesConstants.EMAIL_PROTOCOL, email.getProtocol(), TipoConfiguracao.STRING, "E-mail", "Protocolo SMTP");
        add(configs, CommunicationPropertiesConstants.EMAIL_CONNECTION_TIMEOUT, email.getConnectionTimeout(), TipoConfiguracao.INTEGER, "E-mail", "Timeout de conexão em ms");
        add(configs, CommunicationPropertiesConstants.EMAIL_WRITE_TIMEOUT, email.getWriteTimeout(), TipoConfiguracao.INTEGER, "E-mail", "Timeout de escrita em ms");
        add(configs, CommunicationPropertiesConstants.EMAIL_READ_TIMEOUT, email.getReadTimeout(), TipoConfiguracao.INTEGER, "E-mail", "Timeout de leitura em ms");
        add(configs, CommunicationPropertiesConstants.EMAIL_DEBUG, email.isDebug(), TipoConfiguracao.BOOLEAN, "E-mail", "Habilitar debug de e-mail");

        // WhatsApp
        add(configs, CommunicationPropertiesConstants.WHATSAPP_ENABLED, whatsapp.isEnabled(), TipoConfiguracao.BOOLEAN, "WhatsApp", "Habilitar WhatsApp");
        add(configs, CommunicationPropertiesConstants.WHATSAPP_API_URL, whatsapp.getApiUrl(), TipoConfiguracao.STRING, "WhatsApp", "URL da API");
        add(configs, CommunicationPropertiesConstants.WHATSAPP_API_KEY, whatsapp.getApiKey(), TipoConfiguracao.STRING, "WhatsApp", "Chave da API");
        add(configs, CommunicationPropertiesConstants.WHATSAPP_PHONE_NUMBER_ID, whatsapp.getPhoneNumberId(), TipoConfiguracao.STRING, "WhatsApp", "ID do número de telefone");
        add(configs, CommunicationPropertiesConstants.WHATSAPP_BUSINESS_ACCOUNT_ID, whatsapp.getBusinessAccountId(), TipoConfiguracao.STRING, "WhatsApp", "ID da conta business");

        // Telegram
        add(configs, CommunicationPropertiesConstants.TELEGRAM_ENABLED, telegram.isEnabled(), TipoConfiguracao.BOOLEAN, "Telegram", "Habilitar Telegram");
        add(configs, CommunicationPropertiesConstants.TELEGRAM_BOT_TOKEN, telegram.getBotToken(), TipoConfiguracao.STRING, "Telegram", "Token do bot");
        add(configs, CommunicationPropertiesConstants.TELEGRAM_API_URL, telegram.getApiUrl(), TipoConfiguracao.STRING, "Telegram", "URL da API");
        add(configs, CommunicationPropertiesConstants.TELEGRAM_PARSE_MODE, telegram.getParseMode(), TipoConfiguracao.STRING, "Telegram", "Modo de parse");
        add(configs, CommunicationPropertiesConstants.TELEGRAM_WEBHOOK_URL, telegram.getWebhookUrl(), TipoConfiguracao.STRING, "Telegram", "URL do webhook");
        add(configs, CommunicationPropertiesConstants.TELEGRAM_CHAT_ID, telegram.getChatId(), TipoConfiguracao.STRING, "Telegram", "ID do chat padrão");
        add(configs, CommunicationPropertiesConstants.TELEGRAM_ENABLE_HTML, telegram.isEnableHtml(), TipoConfiguracao.BOOLEAN, "Telegram", "Habilitar HTML");
        add(configs, CommunicationPropertiesConstants.TELEGRAM_TIMEOUT, telegram.getTimeout(), TipoConfiguracao.INTEGER, "Telegram", "Timeout em milissegundos");

        // Notificações gerais
        add(configs, CommunicationPropertiesConstants.NOTIFICATION_DEFAULT_CHANNEL, notification.getDefaultChannel(), TipoConfiguracao.STRING, "Notificações", "Canal padrão");
        add(configs, CommunicationPropertiesConstants.NOTIFICATION_ASYNC_ENABLED, notification.isAsyncEnabled(), TipoConfiguracao.BOOLEAN, "Notificações", "Envio assíncrono");
        add(configs, CommunicationPropertiesConstants.NOTIFICATION_RETRY_ATTEMPTS, notification.getRetryAttempts(), TipoConfiguracao.INTEGER, "Notificações", "Tentativas de reenvio");
        add(configs, CommunicationPropertiesConstants.NOTIFICATION_RETRY_DELAY, notification.getRetryDelay(), TipoConfiguracao.STRING, "Notificações", "Atraso entre reenvios em ms");
        add(configs, CommunicationPropertiesConstants.NOTIFICATION_TEMPLATES_ENABLED, notification.isTemplatesEnabled(), TipoConfiguracao.BOOLEAN, "Notificações", "Habilitar templates");

        return configs;
    }

    private void add(List<ConfiguracaoSistemaDTO<?>> configs, String chave, Object valor, TipoConfiguracao tipo, String categoria, String descricao) {
        configs.add(ConfiguracaoSistemaDTO.builder()
            .modulo(getModulo())
            .chave(chave)
            .valor(convertValue(valor))
            .tipo(tipo)
            .categoria(categoria)
            .descricao(descricao)
            .build());
    }

    private String convertValue(Object valor) {
        if (valor == null) {
            return "";
        }
        return String.valueOf(valor);
    }

    private Properties createProperties() {
        Properties properties = new Properties();

        CommunicationProperties.SmsConfig sms = communicationProperties.getSms();
        CommunicationProperties.EmailConfig email = communicationProperties.getEmail();
        CommunicationProperties.WhatsAppConfig whatsapp = communicationProperties.getWhatsapp();
        CommunicationProperties.TelegramConfig telegram = communicationProperties.getTelegram();
        CommunicationProperties.NotificationConfig notification = communicationProperties.getNotification();

        // SMS
        properties.setProperty(CommunicationPropertiesConstants.SMS_ENABLED, String.valueOf(sms.isEnabled()));
        properties.setProperty(CommunicationPropertiesConstants.SMS_PROVIDER, sms.getProvider());
        properties.setProperty(CommunicationPropertiesConstants.SMS_ACCOUNT_SID, sms.getAccountSid());
        properties.setProperty(CommunicationPropertiesConstants.SMS_AUTH_TOKEN, sms.getAuthToken());
        properties.setProperty(CommunicationPropertiesConstants.SMS_FROM_NUMBER, sms.getFromNumber());
        properties.setProperty(CommunicationPropertiesConstants.SMS_TIMEOUT, String.valueOf(sms.getTimeout()));
        properties.setProperty(CommunicationPropertiesConstants.SMS_API_VERSION, sms.getApiVersion());
        properties.setProperty(CommunicationPropertiesConstants.SMS_MESSAGING_SERVICE_SID, sms.getMessagingServiceSid());

        // Email
        properties.setProperty(CommunicationPropertiesConstants.EMAIL_ENABLED, String.valueOf(email.isEnabled()));
        properties.setProperty(CommunicationPropertiesConstants.EMAIL_HOST, email.getHost());
        properties.setProperty(CommunicationPropertiesConstants.EMAIL_PORT, String.valueOf(email.getPort()));
        properties.setProperty(CommunicationPropertiesConstants.EMAIL_USERNAME, email.getUsername());
        properties.setProperty(CommunicationPropertiesConstants.EMAIL_PASSWORD, email.getPassword());
        properties.setProperty(CommunicationPropertiesConstants.EMAIL_FROM_ADDRESS, email.getFromAddress());
        properties.setProperty(CommunicationPropertiesConstants.EMAIL_USE_TLS, String.valueOf(email.isUseTls()));
        properties.setProperty(CommunicationPropertiesConstants.EMAIL_PROTOCOL, email.getProtocol());
        properties.setProperty(CommunicationPropertiesConstants.EMAIL_CONNECTION_TIMEOUT, String.valueOf(email.getConnectionTimeout()));
        properties.setProperty(CommunicationPropertiesConstants.EMAIL_WRITE_TIMEOUT, String.valueOf(email.getWriteTimeout()));
        properties.setProperty(CommunicationPropertiesConstants.EMAIL_READ_TIMEOUT, String.valueOf(email.getReadTimeout()));
        properties.setProperty(CommunicationPropertiesConstants.EMAIL_DEBUG, String.valueOf(email.isDebug()));

        // WhatsApp
        properties.setProperty(CommunicationPropertiesConstants.WHATSAPP_ENABLED, String.valueOf(whatsapp.isEnabled()));
        properties.setProperty(CommunicationPropertiesConstants.WHATSAPP_API_URL, whatsapp.getApiUrl());
        properties.setProperty(CommunicationPropertiesConstants.WHATSAPP_API_KEY, whatsapp.getApiKey());
        properties.setProperty(CommunicationPropertiesConstants.WHATSAPP_PHONE_NUMBER_ID, whatsapp.getPhoneNumberId());
        properties.setProperty(CommunicationPropertiesConstants.WHATSAPP_BUSINESS_ACCOUNT_ID, whatsapp.getBusinessAccountId());

        // Telegram
        properties.setProperty(CommunicationPropertiesConstants.TELEGRAM_ENABLED, String.valueOf(telegram.isEnabled()));
        properties.setProperty(CommunicationPropertiesConstants.TELEGRAM_BOT_TOKEN, telegram.getBotToken());
        properties.setProperty(CommunicationPropertiesConstants.TELEGRAM_API_URL, telegram.getApiUrl());
        properties.setProperty(CommunicationPropertiesConstants.TELEGRAM_PARSE_MODE, telegram.getParseMode());
        properties.setProperty(CommunicationPropertiesConstants.TELEGRAM_WEBHOOK_URL, telegram.getWebhookUrl());
        properties.setProperty(CommunicationPropertiesConstants.TELEGRAM_CHAT_ID, telegram.getChatId());
        properties.setProperty(CommunicationPropertiesConstants.TELEGRAM_ENABLE_HTML, String.valueOf(telegram.isEnableHtml()));
        properties.setProperty(CommunicationPropertiesConstants.TELEGRAM_TIMEOUT, String.valueOf(telegram.getTimeout()));

        // Notificações gerais
        properties.setProperty(CommunicationPropertiesConstants.NOTIFICATION_DEFAULT_CHANNEL, notification.getDefaultChannel());
        properties.setProperty(CommunicationPropertiesConstants.NOTIFICATION_ASYNC_ENABLED, String.valueOf(notification.isAsyncEnabled()));
        properties.setProperty(CommunicationPropertiesConstants.NOTIFICATION_RETRY_ATTEMPTS, String.valueOf(notification.getRetryAttempts()));
        properties.setProperty(CommunicationPropertiesConstants.NOTIFICATION_RETRY_DELAY, String.valueOf(notification.getRetryDelay()));
        properties.setProperty(CommunicationPropertiesConstants.NOTIFICATION_TEMPLATES_ENABLED, String.valueOf(notification.isTemplatesEnabled()));

        return properties;
    }
}
