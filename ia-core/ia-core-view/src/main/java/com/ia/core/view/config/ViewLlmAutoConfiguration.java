package com.ia.core.view.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * Configuração automática do módulo View com suporte MCP.
 * <p>
 * Habilita a configuração automática do módulo View com suporte a Model Context Protocol (MCP)
 * quando a propriedade ia-core.view.llm.enabled for true.
 * Esta configuração é independente do ia-core-llm-service para evitar dependência circular.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@AutoConfiguration
@ConditionalOnProperty(prefix = "ia-core.view.llm", name = "enabled", havingValue = "true", matchIfMissing = true)
@EnableConfigurationProperties(ViewLlmModuleProperties.class)
public class ViewLlmAutoConfiguration {
}
