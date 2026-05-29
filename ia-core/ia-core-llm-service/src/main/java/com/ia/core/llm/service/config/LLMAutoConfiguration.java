package com.ia.core.llm.service.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
/**
 * Configuração automática do módulo LLM.
 * <p>
 * Habilita a configuração automática do módulo de Large Language Model
 * quando a propriedade ia-core.llm.enabled for true.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@AutoConfiguration
@ConditionalOnProperty(prefix = "ia-core.llm", name = "enabled", havingValue = "true", matchIfMissing = true)
@EnableConfigurationProperties(LlmModuleProperties.class)
public class LLMAutoConfiguration {
}
