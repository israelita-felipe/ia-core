package com.ia.core.security.service.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * Propriedades de configuração centralizada para o módulo Security.
 *
 * <p>Permite configuração via YAML (application.yml) e programática.
 * Cada propriedade de segurança pode ser configurada individualmente.</p>
 *
 * <p>Estrutura de configuração:</p>
 * <pre>
 * ia-core:
 *   security:
 *     jwt:
 *       secret: ""
 *       expiration: 86400000
 *       issuer: ia-core
 *     password:
 *       min-length: 8
 *       require-uppercase: true
 *       require-lowercase: true
 *       require-digits: true
 *       require-special: false
 * </pre>
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "ia-core.security")
public class SecurityProperties {

    /**
     * Configurações de JWT.
     */
    private JwtConfig jwt = new JwtConfig();

    /**
     * Configurações de senha.
     */
    private PasswordConfig password = new PasswordConfig();

    @Getter
    @Setter
    public static class JwtConfig {
        /**
         * Secret JWT.
         */
        private String secret = "";

        /**
         * Expiração JWT em milissegundos.
         */
        private long expiration = 86400000L;

        /**
         * Emissor JWT.
         */
        private String issuer = "ia-core";
    }

    @Getter
    @Setter
    public static class PasswordConfig {
        /**
         * Tamanho mínimo da senha.
         */
        private int minLength = 8;

        /**
         * Requer letra maiúscula.
         */
        private boolean requireUppercase = true;

        /**
         * Requer letra minúscula.
         */
        private boolean requireLowercase = true;

        /**
         * Requer dígitos.
         */
        private boolean requireDigits = true;

        /**
         * Requer caracteres especiais.
         */
        private boolean requireSpecial = false;
    }

    /**
     * Habilitar módulo de segurança.
     */
    private boolean enabled = true;

    /**
     * Configurações do agente de segurança.
     */
    private AgentConfig agent = new AgentConfig();

    @Getter
    @Setter
    public static class AgentConfig {
        /**
         * Pacotes para escaneamento de ferramentas de segurança.
         */
        private java.util.List<String> toolScanPackages = new java.util.ArrayList<>(List.of("com.ia.core.security.service.tool"));
    }

    /**
     * Configurações HTTP.
     */
    private HttpConfig http = new HttpConfig();

    /**
     * Configurações do Swagger.
     */
    private SwaggerConfig swagger = new SwaggerConfig();

    @Getter
    @Setter
    public static class SwaggerConfig {
        /**
         * Caminho da UI do Swagger. Padrão: /swagger-ui.html
         */
        private String uiPath = "/swagger-ui.html";

        /**
         * Caminho dos documentos da API. Padrão: /v3/api-docs/**
         */
        private String apiDocsPath = "/v3/api-docs/**";

        /**
         * Caminho do arquivo YAML dos documentos da API. Padrão: /v3/api-docs.yaml
         */
        private String apiDocsYaml = "/v3/api-docs.yaml";

        /**
         * Título da documentação OpenAPI. Padrão: API de Gestão
         */
        private String openapiTitle = "API de Gestão";

        /**
         * Descrição da documentação OpenAPI. Padrão: Documentação da API com JWT
         */
        private String openapiDescription = "Documentação da API com JWT";

        /**
         * Versão da documentação OpenAPI. Padrão: 1.0
         */
        private String openapiVersion = "1.0";

        /**
         * Nome do esquema de segurança na documentação OpenAPI. Padrão: Token de Autenticação
         */
        private String securitySchemeName = "Token de Autenticação";

        /**
         * Tipo de esquema de segurança HTTP. Padrão: Bearer
         */
        private String securityScheme = "Bearer";

        /**
         * Formato do bearer token. Padrão: JWT
         */
        private String securityBearerFormat = "JWT";
    }

    @Getter
    @Setter
    public static class HttpConfig {
        /**
         * Habilitar CORS.
         */
        private boolean corsEnabled = true;

        /**
         * Origens permitidas (separadas por vírgula).
         */
        private String corsAllowedOrigins = "*";

        /**
         * Métodos permitidos.
         */
        private String corsAllowedMethods = "GET,POST,PUT,DELETE,OPTIONS";

        /**
         * Headers permitidos.
         */
        private String corsAllowedHeaders = "*";

        /**
         * Habilitar CSRF.
         */
        private boolean csrfEnabled = true;

        /**
         * Habilitar session.
         */
        private boolean sessionEnabled = false;
    }
}
