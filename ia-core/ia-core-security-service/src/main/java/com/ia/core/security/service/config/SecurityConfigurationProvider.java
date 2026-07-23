package com.ia.core.security.service.config;

import com.ia.core.model.configuracao.TipoConfiguracao;
import com.ia.core.security.model.SecurityModel;
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
 * Provider de configuração para o módulo Security.
 * <p>
 * Fornece configurações específicas para segurança do sistema,
 * incluindo JWT, tokens, e configurações de autenticação.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@Component
@EnableConfigurationProperties(SecurityProperties.class)
public class SecurityConfigurationProvider implements ConfigurationProvider {

    private static final Set<String> ALLOWED_CHAVES = Set.of(
        SecurityPropertiesConstants.ENABLED,
        SecurityPropertiesConstants.AGENT_TOOL_SCAN_PACKAGES,
        SecurityPropertiesConstants.JWT_SECRET,
        SecurityPropertiesConstants.JWT_EXPIRATION,
        SecurityPropertiesConstants.JWT_ISSUER,
        SecurityPropertiesConstants.PASSWORD_MIN_LENGTH,
        SecurityPropertiesConstants.PASSWORD_REQUIRE_UPPERCASE,
        SecurityPropertiesConstants.PASSWORD_REQUIRE_LOWERCASE,
        SecurityPropertiesConstants.PASSWORD_REQUIRE_DIGITS,
        SecurityPropertiesConstants.PASSWORD_REQUIRE_SPECIAL,
        SecurityPropertiesConstants.HTTP_CORS_ENABLED,
        SecurityPropertiesConstants.HTTP_CORS_ALLOWED_ORIGINS,
        SecurityPropertiesConstants.HTTP_CORS_ALLOWED_METHODS,
        SecurityPropertiesConstants.HTTP_CORS_ALLOWED_HEADERS,
        SecurityPropertiesConstants.HTTP_CSRF_ENABLED,
        SecurityPropertiesConstants.HTTP_SESSION_ENABLED,
        SecurityPropertiesConstants.SWAGGER_UI_PATH,
        SecurityPropertiesConstants.SWAGGER_API_DOCS_PATH,
        SecurityPropertiesConstants.SWAGGER_API_DOCS_YAML,
        SecurityPropertiesConstants.SWAGGER_OPENAPI_TITLE,
        SecurityPropertiesConstants.SWAGGER_OPENAPI_DESCRIPTION,
        SecurityPropertiesConstants.SWAGGER_OPENAPI_VERSION,
        SecurityPropertiesConstants.SWAGGER_SECURITY_SCHEME_NAME,
        SecurityPropertiesConstants.SWAGGER_SECURITY_SCHEME,
        SecurityPropertiesConstants.SWAGGER_SECURITY_BEARER_FORMAT
    );

    @Getter
    private final SecurityProperties securityProperties;

    @Getter
    private final Properties properties;

    @Getter
    private List<ConfiguracaoSistemaDTO<?>> configurations;

    public SecurityConfigurationProvider(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
        this.properties = createProperties();
        this.configurations = buildConfigurations();
    }

    @Override
    public List<ConfiguracaoSistemaDTO<?>> getConfigurations() {
        return configurations;
    }

    @Override
    public String getModulo() {
        return SecurityModel.NAME;
    }

    @Override
    public void validar(ConfiguracaoSistemaDTO<?> config) {
        if (!ALLOWED_CHAVES.contains(config.getChave())) {
            log.debug("Chave de configuração ignorada: {}", config.getChave());
            return;
        }

        switch (config.getChave()) {
            case SecurityPropertiesConstants.JWT_SECRET:
                if (config.getValor() != null && config.getValor().length() < 32) {
                    throw new IllegalArgumentException("JWT secret deve ter pelo menos 32 caracteres");
                }
                break;
            case SecurityPropertiesConstants.JWT_EXPIRATION:
                if (config.getValor() != null) {
                    try {
                        long expiration = Long.parseLong(config.getValor());
                        if (expiration <= 0) {
                            throw new IllegalArgumentException("Expiração JWT deve ser positiva");
                        }
                    } catch (NumberFormatException e) {
                        throw new IllegalArgumentException("Expiração JWT inválida: " + config.getValor());
                    }
                }
                break;
            case SecurityPropertiesConstants.PASSWORD_MIN_LENGTH:
                if (config.getValor() != null) {
                    try {
                        int minLength = Integer.parseInt(config.getValor());
                        if (minLength < 4 || minLength > 100) {
                            throw new IllegalArgumentException("Tamanho mínimo de senha deve estar entre 4 e 100");
                        }
                    } catch (NumberFormatException e) {
                        throw new IllegalArgumentException("Tamanho mínimo inválido: " + config.getValor());
                    }
                }
                break;
            case SecurityPropertiesConstants.PASSWORD_REQUIRE_UPPERCASE,
                 SecurityPropertiesConstants.PASSWORD_REQUIRE_LOWERCASE,
                 SecurityPropertiesConstants.PASSWORD_REQUIRE_DIGITS,
                 SecurityPropertiesConstants.PASSWORD_REQUIRE_SPECIAL,
                 SecurityPropertiesConstants.HTTP_CORS_ENABLED,
                 SecurityPropertiesConstants.HTTP_CSRF_ENABLED,
                 SecurityPropertiesConstants.HTTP_SESSION_ENABLED:
                if (config.getValor() != null) {
                    Boolean.parseBoolean(config.getValor());
                }
                break;
            default:
                break;
        }
        log.debug("Validação concluída para configuração Security: {}", config.getChave());
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

        switch (chave) {
            case SecurityPropertiesConstants.JWT_SECRET -> securityProperties.getJwt().setSecret(valor);
            case SecurityPropertiesConstants.JWT_EXPIRATION -> securityProperties.getJwt().setExpiration(Long.parseLong(valor));
            case SecurityPropertiesConstants.JWT_ISSUER -> securityProperties.getJwt().setIssuer(valor);
            case SecurityPropertiesConstants.PASSWORD_MIN_LENGTH -> securityProperties.getPassword().setMinLength(Integer.parseInt(valor));
            case SecurityPropertiesConstants.PASSWORD_REQUIRE_UPPERCASE -> securityProperties.getPassword().setRequireUppercase(Boolean.parseBoolean(valor));
            case SecurityPropertiesConstants.PASSWORD_REQUIRE_LOWERCASE -> securityProperties.getPassword().setRequireLowercase(Boolean.parseBoolean(valor));
            case SecurityPropertiesConstants.PASSWORD_REQUIRE_DIGITS -> securityProperties.getPassword().setRequireDigits(Boolean.parseBoolean(valor));
            case SecurityPropertiesConstants.PASSWORD_REQUIRE_SPECIAL -> securityProperties.getPassword().setRequireSpecial(Boolean.parseBoolean(valor));
            case SecurityPropertiesConstants.HTTP_CORS_ENABLED -> securityProperties.getHttp().setCorsEnabled(Boolean.parseBoolean(valor));
            case SecurityPropertiesConstants.HTTP_CORS_ALLOWED_ORIGINS -> securityProperties.getHttp().setCorsAllowedOrigins(valor);
            case SecurityPropertiesConstants.HTTP_CORS_ALLOWED_METHODS -> securityProperties.getHttp().setCorsAllowedMethods(valor);
            case SecurityPropertiesConstants.HTTP_CORS_ALLOWED_HEADERS -> securityProperties.getHttp().setCorsAllowedHeaders(valor);
            case SecurityPropertiesConstants.HTTP_CSRF_ENABLED -> securityProperties.getHttp().setCsrfEnabled(Boolean.parseBoolean(valor));
            case SecurityPropertiesConstants.HTTP_SESSION_ENABLED -> securityProperties.getHttp().setSessionEnabled(Boolean.parseBoolean(valor));
            case SecurityPropertiesConstants.SWAGGER_UI_PATH -> securityProperties.getSwagger().setUiPath(valor);
            case SecurityPropertiesConstants.SWAGGER_API_DOCS_PATH -> securityProperties.getSwagger().setApiDocsPath(valor);
            case SecurityPropertiesConstants.SWAGGER_API_DOCS_YAML -> securityProperties.getSwagger().setApiDocsYaml(valor);
            case SecurityPropertiesConstants.SWAGGER_OPENAPI_TITLE -> securityProperties.getSwagger().setOpenapiTitle(valor);
            case SecurityPropertiesConstants.SWAGGER_OPENAPI_DESCRIPTION -> securityProperties.getSwagger().setOpenapiDescription(valor);
            case SecurityPropertiesConstants.SWAGGER_OPENAPI_VERSION -> securityProperties.getSwagger().setOpenapiVersion(valor);
            case SecurityPropertiesConstants.SWAGGER_SECURITY_SCHEME_NAME -> securityProperties.getSwagger().setSecuritySchemeName(valor);
            case SecurityPropertiesConstants.SWAGGER_SECURITY_SCHEME -> securityProperties.getSwagger().setSecurityScheme(valor);
            case SecurityPropertiesConstants.SWAGGER_SECURITY_BEARER_FORMAT -> securityProperties.getSwagger().setSecurityBearerFormat(valor);
            case SecurityPropertiesConstants.ENABLED -> securityProperties.setEnabled(Boolean.parseBoolean(valor));
            case SecurityPropertiesConstants.AGENT_TOOL_SCAN_PACKAGES -> {
                String pkg = valor.trim();
                if (!pkg.isEmpty()) {
                    securityProperties.getAgent().getToolScanPackages().clear();
                    securityProperties.getAgent().getToolScanPackages().add(pkg);
                }
            }
            default -> {
                log.debug("Chave de configuração Security não mapeada para aplicação: {}", chave);
                return;
            }
        }

        this.properties.clear();
        this.properties.putAll(createProperties());

        log.info("Configuração Security aplicada: chave={}, valor={}", chave, valor);
    }

    private List<ConfiguracaoSistemaDTO<?>> buildConfigurations() {
        List<ConfiguracaoSistemaDTO<?>> configs = new ArrayList<>();

        // JWT
        add(configs, SecurityPropertiesConstants.JWT_SECRET, securityProperties.getJwt().getSecret(), TipoConfiguracao.STRING, "JWT", "Secret JWT");
        add(configs, SecurityPropertiesConstants.JWT_EXPIRATION, securityProperties.getJwt().getExpiration(), TipoConfiguracao.INTEGER, "JWT", "Expiração JWT em ms");
        add(configs, SecurityPropertiesConstants.JWT_ISSUER, securityProperties.getJwt().getIssuer(), TipoConfiguracao.STRING, "JWT", "Emissor JWT");

        // Password
        add(configs, SecurityPropertiesConstants.PASSWORD_MIN_LENGTH, securityProperties.getPassword().getMinLength(), TipoConfiguracao.INTEGER, "Senha", "Tamanho mínimo da senha");
        add(configs, SecurityPropertiesConstants.PASSWORD_REQUIRE_UPPERCASE, securityProperties.getPassword().isRequireUppercase(), TipoConfiguracao.BOOLEAN, "Senha", "Requer maiúscula");
        add(configs, SecurityPropertiesConstants.PASSWORD_REQUIRE_LOWERCASE, securityProperties.getPassword().isRequireLowercase(), TipoConfiguracao.BOOLEAN, "Senha", "Requer minúscula");
        add(configs, SecurityPropertiesConstants.PASSWORD_REQUIRE_DIGITS, securityProperties.getPassword().isRequireDigits(), TipoConfiguracao.BOOLEAN, "Senha", "Requer dígitos");
        add(configs, SecurityPropertiesConstants.PASSWORD_REQUIRE_SPECIAL, securityProperties.getPassword().isRequireSpecial(), TipoConfiguracao.BOOLEAN, "Senha", "Requer caracteres especiais");

        // HTTP
        add(configs, SecurityPropertiesConstants.HTTP_CORS_ENABLED, securityProperties.getHttp().isCorsEnabled(), TipoConfiguracao.BOOLEAN, "HTTP", "Habilitar CORS");
        add(configs, SecurityPropertiesConstants.HTTP_CORS_ALLOWED_ORIGINS, securityProperties.getHttp().getCorsAllowedOrigins(), TipoConfiguracao.STRING, "HTTP", "Origens permitidas");
        add(configs, SecurityPropertiesConstants.HTTP_CORS_ALLOWED_METHODS, securityProperties.getHttp().getCorsAllowedMethods(), TipoConfiguracao.STRING, "HTTP", "Métodos permitidos");
        add(configs, SecurityPropertiesConstants.HTTP_CORS_ALLOWED_HEADERS, securityProperties.getHttp().getCorsAllowedHeaders(), TipoConfiguracao.STRING, "HTTP", "Headers permitidos");
        add(configs, SecurityPropertiesConstants.HTTP_CSRF_ENABLED, securityProperties.getHttp().isCsrfEnabled(), TipoConfiguracao.BOOLEAN, "HTTP", "Habilitar CSRF");
        add(configs, SecurityPropertiesConstants.HTTP_SESSION_ENABLED, securityProperties.getHttp().isSessionEnabled(), TipoConfiguracao.BOOLEAN, "HTTP", "Habilitar session");

        // Core
        add(configs, SecurityPropertiesConstants.ENABLED, securityProperties.isEnabled(), TipoConfiguracao.BOOLEAN, "Security", "Habilitar módulo de segurança");
        add(configs, SecurityPropertiesConstants.AGENT_TOOL_SCAN_PACKAGES, String.join(",", securityProperties.getAgent().getToolScanPackages()), TipoConfiguracao.STRING, "Security", "Pacotes para escaneamento de ferramentas");

        // Swagger
        add(configs, SecurityPropertiesConstants.SWAGGER_UI_PATH, securityProperties.getSwagger().getUiPath(), TipoConfiguracao.STRING, "Swagger", "Caminho da UI do Swagger");
        add(configs, SecurityPropertiesConstants.SWAGGER_API_DOCS_PATH, securityProperties.getSwagger().getApiDocsPath(), TipoConfiguracao.STRING, "Swagger", "Caminho dos documentos da API");
        add(configs, SecurityPropertiesConstants.SWAGGER_API_DOCS_YAML, securityProperties.getSwagger().getApiDocsYaml(), TipoConfiguracao.STRING, "Swagger", "Caminho do YAML dos documentos da API");

        // OpenAPI
        add(configs, SecurityPropertiesConstants.SWAGGER_OPENAPI_TITLE, securityProperties.getSwagger().getOpenapiTitle(), TipoConfiguracao.STRING, "Swagger", "Título da API na documentação");
        add(configs, SecurityPropertiesConstants.SWAGGER_OPENAPI_DESCRIPTION, securityProperties.getSwagger().getOpenapiDescription(), TipoConfiguracao.STRING, "Swagger", "Descrição da API na documentação");
        add(configs, SecurityPropertiesConstants.SWAGGER_OPENAPI_VERSION, securityProperties.getSwagger().getOpenapiVersion(), TipoConfiguracao.STRING, "Swagger", "Versão da API na documentação");
        add(configs, SecurityPropertiesConstants.SWAGGER_SECURITY_SCHEME_NAME, securityProperties.getSwagger().getSecuritySchemeName(), TipoConfiguracao.STRING, "Swagger", "Nome do esquema de segurança");
        add(configs, SecurityPropertiesConstants.SWAGGER_SECURITY_SCHEME, securityProperties.getSwagger().getSecurityScheme(), TipoConfiguracao.STRING, "Swagger", "Tipo de esquema de segurança");
        add(configs, SecurityPropertiesConstants.SWAGGER_SECURITY_BEARER_FORMAT, securityProperties.getSwagger().getSecurityBearerFormat(), TipoConfiguracao.STRING, "Swagger", "Formato do bearer token");

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

        properties.setProperty(SecurityPropertiesConstants.JWT_SECRET, securityProperties.getJwt().getSecret());
        properties.setProperty(SecurityPropertiesConstants.JWT_EXPIRATION, String.valueOf(securityProperties.getJwt().getExpiration()));
        properties.setProperty(SecurityPropertiesConstants.JWT_ISSUER, securityProperties.getJwt().getIssuer());
        properties.setProperty(SecurityPropertiesConstants.PASSWORD_MIN_LENGTH, String.valueOf(securityProperties.getPassword().getMinLength()));
        properties.setProperty(SecurityPropertiesConstants.PASSWORD_REQUIRE_UPPERCASE, String.valueOf(securityProperties.getPassword().isRequireUppercase()));
        properties.setProperty(SecurityPropertiesConstants.PASSWORD_REQUIRE_LOWERCASE, String.valueOf(securityProperties.getPassword().isRequireLowercase()));
        properties.setProperty(SecurityPropertiesConstants.PASSWORD_REQUIRE_DIGITS, String.valueOf(securityProperties.getPassword().isRequireDigits()));
        properties.setProperty(SecurityPropertiesConstants.PASSWORD_REQUIRE_SPECIAL, String.valueOf(securityProperties.getPassword().isRequireSpecial()));
        properties.setProperty(SecurityPropertiesConstants.HTTP_CORS_ENABLED, String.valueOf(securityProperties.getHttp().isCorsEnabled()));
        properties.setProperty(SecurityPropertiesConstants.HTTP_CORS_ALLOWED_ORIGINS, securityProperties.getHttp().getCorsAllowedOrigins());
        properties.setProperty(SecurityPropertiesConstants.HTTP_CORS_ALLOWED_METHODS, securityProperties.getHttp().getCorsAllowedMethods());
        properties.setProperty(SecurityPropertiesConstants.HTTP_CORS_ALLOWED_HEADERS, securityProperties.getHttp().getCorsAllowedHeaders());
        properties.setProperty(SecurityPropertiesConstants.HTTP_CSRF_ENABLED, String.valueOf(securityProperties.getHttp().isCsrfEnabled()));
        properties.setProperty(SecurityPropertiesConstants.HTTP_SESSION_ENABLED, String.valueOf(securityProperties.getHttp().isSessionEnabled()));

        // Core
        properties.setProperty(SecurityPropertiesConstants.ENABLED, String.valueOf(securityProperties.isEnabled()));
        properties.setProperty(SecurityPropertiesConstants.AGENT_TOOL_SCAN_PACKAGES, String.join(",", securityProperties.getAgent().getToolScanPackages()));

        // Swagger
        properties.setProperty(SecurityPropertiesConstants.SWAGGER_UI_PATH, securityProperties.getSwagger().getUiPath());
        properties.setProperty(SecurityPropertiesConstants.SWAGGER_API_DOCS_PATH, securityProperties.getSwagger().getApiDocsPath());
        properties.setProperty(SecurityPropertiesConstants.SWAGGER_API_DOCS_YAML, securityProperties.getSwagger().getApiDocsYaml());

        // OpenAPI
        properties.setProperty(SecurityPropertiesConstants.SWAGGER_OPENAPI_TITLE, securityProperties.getSwagger().getOpenapiTitle());
        properties.setProperty(SecurityPropertiesConstants.SWAGGER_OPENAPI_DESCRIPTION, securityProperties.getSwagger().getOpenapiDescription());
        properties.setProperty(SecurityPropertiesConstants.SWAGGER_OPENAPI_VERSION, securityProperties.getSwagger().getOpenapiVersion());
        properties.setProperty(SecurityPropertiesConstants.SWAGGER_SECURITY_SCHEME_NAME, securityProperties.getSwagger().getSecuritySchemeName());
        properties.setProperty(SecurityPropertiesConstants.SWAGGER_SECURITY_SCHEME, securityProperties.getSwagger().getSecurityScheme());
        properties.setProperty(SecurityPropertiesConstants.SWAGGER_SECURITY_BEARER_FORMAT, securityProperties.getSwagger().getSecurityBearerFormat());

        return properties;
    }
}
