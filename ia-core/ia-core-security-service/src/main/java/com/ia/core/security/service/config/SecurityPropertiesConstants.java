package com.ia.core.security.service.config;

/**
 * Constantes para as chaves de propriedades do módulo Security.
 *
 * <p>Estas constantes são usadas internamente por {@link SecurityProperties}
 * e podem ser usadas para referenciar as chaves de configuração.</p>
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
public final class SecurityPropertiesConstants {

    // =========================================================================
    // JWT Properties (ia-core.security.jwt.*)
    // =========================================================================
    /**
     * Property key: ia-core.security.jwt.secret
     */
    public static final String JWT_SECRET = "security.jwt.secret";

    /**
     * Property key: ia-core.security.jwt.expiration
     */
    public static final String JWT_EXPIRATION = "security.jwt.expiration";

    /**
     * Property key: ia-core.security.jwt.issuer
     */
    public static final String JWT_ISSUER = "security.jwt.issuer";

    // =========================================================================
    // Password Properties (ia-core.security.password.*)
    // =========================================================================
    /**
     * Property key: ia-core.security.password.min-length
     */
    public static final String PASSWORD_MIN_LENGTH = "security.password.min-length";

    /**
     * Property key: ia-core.security.password.require-uppercase
     */
    public static final String PASSWORD_REQUIRE_UPPERCASE = "security.password.require-uppercase";

    /**
     * Property key: ia-core.security.password.require-lowercase
     */
    public static final String PASSWORD_REQUIRE_LOWERCASE = "security.password.require-lowercase";

    /**
     * Property key: ia-core.security.password.require-digits
     */
    public static final String PASSWORD_REQUIRE_DIGITS = "security.password.require-digits";

    /**
     * Property key: ia-core.security.password.require-special
     */
    public static final String PASSWORD_REQUIRE_SPECIAL = "security.password.require-special";

    // =========================================================================
    // HTTP Properties (ia-core.security.http.*)
    // =========================================================================
    /**
     * Property key: ia-core.security.http.cors.enabled
     */
    public static final String HTTP_CORS_ENABLED = "security.http.cors.enabled";

    /**
     * Property key: ia-core.security.http.cors.allowed-origins
     */
    public static final String HTTP_CORS_ALLOWED_ORIGINS = "security.http.cors.allowed-origins";

    /**
     * Property key: ia-core.security.http.cors.allowed-methods
     */
    public static final String HTTP_CORS_ALLOWED_METHODS = "security.http.cors.allowed-methods";

    /**
     * Property key: ia-core.security.http.cors.allowed-headers
     */
    public static final String HTTP_CORS_ALLOWED_HEADERS = "security.http.cors.allowed-headers";

    /**
     * Property key: ia-core.security.http.csrf.enabled
     */
    public static final String HTTP_CSRF_ENABLED = "security.http.csrf.enabled";

    /**
     * Property key: ia-core.security.http.session.enabled
     */
    public static final String HTTP_SESSION_ENABLED = "security.http.session.enabled";

    // =========================================================================
    // Core Properties (ia-core.security.*)
    // =========================================================================
    /**
     * Property key: ia-core.security.enabled
     */
    public static final String ENABLED = "security.enabled";

    /**
     * Property key: ia-core.security.agent.tool-scan-packages
     */
    public static final String AGENT_TOOL_SCAN_PACKAGES = "security.agent.tool-scan-packages";

    // =========================================================================
    // Swagger Properties (ia-core.security.swagger.*)
    // =========================================================================
    /**
     * Property key: ia-core.security.swagger.ui-path
     */
    public static final String SWAGGER_UI_PATH = "security.swagger.ui-path";

    /**
     * Property key: ia-core.security.swagger.api-docs-path
     */
    public static final String SWAGGER_API_DOCS_PATH = "security.swagger.api-docs-path";

    /**
     * Property key: ia-core.security.swagger.api-docs-yaml
     */
    public static final String SWAGGER_API_DOCS_YAML = "security.swagger.api-docs-yaml";

    /**
     * Property key: ia-core.security.swagger.openapi-title
     */
    public static final String SWAGGER_OPENAPI_TITLE = "security.swagger.openapi-title";

    /**
     * Property key: ia-core.security.swagger.openapi-description
     */
    public static final String SWAGGER_OPENAPI_DESCRIPTION = "security.swagger.openapi-description";

    /**
     * Property key: ia-core.security.swagger.openapi-version
     */
    public static final String SWAGGER_OPENAPI_VERSION = "security.swagger.openapi-version";

    /**
     * Property key: ia-core.security.swagger.security-scheme-name
     */
    public static final String SWAGGER_SECURITY_SCHEME_NAME = "security.swagger.security-scheme-name";

    /**
     * Property key: ia-core.security.swagger.security-scheme
     */
    public static final String SWAGGER_SECURITY_SCHEME = "security.swagger.security-scheme";

    /**
     * Property key: ia-core.security.swagger.security-bearer-format
     */
    public static final String SWAGGER_SECURITY_BEARER_FORMAT = "security.swagger.security-bearer-format";

    private SecurityPropertiesConstants() {
        // Utility class
    }
}
