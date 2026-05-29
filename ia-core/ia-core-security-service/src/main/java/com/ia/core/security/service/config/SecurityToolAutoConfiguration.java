package com.ia.core.security.service.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.ComponentScan;

/**
 * Configuração automática para ferramentas de segurança MCP.
 * <p>
 * Habilita o scan de componentes no pacote tool para descoberta automática
 * de ferramentas anotadas com @Tool que serão expostas via MCP.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@AutoConfiguration
@ConditionalOnProperty(prefix = "ia-core.security", name = "enabled", havingValue = "true", matchIfMissing = true)
@ComponentScan(basePackages = "com.ia.core.security.service")
public class SecurityToolAutoConfiguration {
}
