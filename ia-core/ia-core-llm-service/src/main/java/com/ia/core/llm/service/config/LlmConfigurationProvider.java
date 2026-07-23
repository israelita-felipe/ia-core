package com.ia.core.llm.service.config;

import com.ia.core.llm.model.LLMModel;
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
 * Provider de configuração para o módulo LLM.
 * <p>
 * Fornece configurações específicas para o módulo Large Language Model,
 * incluindo endpoints, timeouts, e configurações de agentes.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@Component
@EnableConfigurationProperties({LlmModuleProperties.class, SpringAiProperties.class})
public class LlmConfigurationProvider implements ConfigurationProvider {

    private static final Set<String> ALLOWED_CHAVES = Set.of(
        LlmModulePropertiesConstants.ENABLED,
        LlmModulePropertiesConstants.SECURITY_PROTECTED_PATHS,
        LlmModulePropertiesConstants.AGENT_ORCHESTRATOR_ID,
        LlmModulePropertiesConstants.AGENT_TOOL_SCAN_PACKAGES,
        LlmModulePropertiesConstants.AGENT_ENABLED,
        LlmModulePropertiesConstants.AGENT_MAX_SUB_AGENT_TURNS,
        LlmModulePropertiesConstants.AGENT_SUBAGENT_RESOLVER_ENABLED,
        LlmModulePropertiesConstants.AGENT_SUBAGENT_RESOLVER_TYPE,
        LlmModulePropertiesConstants.AGENT_BUILT_IN_TOOLS_WEB_SEARCH_ENABLED,
        LlmModulePropertiesConstants.AGENT_A2A_ENABLED,
        LlmModulePropertiesConstants.AGENT_A2A_SERVER_URL,
        LlmModulePropertiesConstants.AGENT_A2A_AGENT_ID,
        LlmModulePropertiesConstants.AGENT_MULTI_MODEL_ENABLED,
        LlmModulePropertiesConstants.AGENT_MULTI_MODEL_DEFAULT_MODEL,
        LlmModulePropertiesConstants.SKILL_PROGRESSIVE_DISCLOSURE,
        LlmModulePropertiesConstants.FERRAMENTA_DISCOVERY_ENABLED,
        LlmModulePropertiesConstants.FERRAMENTA_DISCOVERY_SCAN_PACKAGES,
        LlmModulePropertiesConstants.FERRAMENTA_DISCOVERY_REFRESH_ON_STARTUP,
        LlmModulePropertiesConstants.AUDIT_ENABLED,
        LlmModulePropertiesConstants.AUDIT_TABLE,
        LlmModulePropertiesConstants.SPRING_AI_MODEL_CHAT,
        LlmModulePropertiesConstants.SPRING_AI_MODEL_EMBEDDING,
        LlmModulePropertiesConstants.SPRING_AI_OLLAMA_BASE_URL,
        LlmModulePropertiesConstants.SPRING_AI_OLLAMA_CHAT_OPTIONS_MODEL,
        LlmModulePropertiesConstants.SPRING_AI_OLLAMA_CHAT_OPTIONS_TEMPERATURE,
        LlmModulePropertiesConstants.SPRING_AI_OLLAMA_EMBEDDING_OPTIONS_MODEL,
        LlmModulePropertiesConstants.SPRING_AI_MCP_SERVER_ENABLED,
        LlmModulePropertiesConstants.SPRING_AI_MCP_SERVER_NAME,
        LlmModulePropertiesConstants.SPRING_AI_MCP_SERVER_VERSION,
        LlmModulePropertiesConstants.SPRING_AI_MCP_SERVER_SSE_ENDPOINT,
        LlmModulePropertiesConstants.SPRING_AI_MCP_SERVER_PATH,
        LlmModulePropertiesConstants.SPRING_AI_MCP_SERVER_AGENT_CARD_ENABLED,
        LlmModulePropertiesConstants.SPRING_AI_MCP_SERVER_AGENT_CARD_PATH,
        LlmModulePropertiesConstants.SPRING_AI_MCP_TOOLS_USECASE_SCAN_ENABLED,
        LlmModulePropertiesConstants.SPRING_AI_MCP_TOOLS_USECASE_SCAN_BASE_PACKAGE,
        LlmModulePropertiesConstants.SPRING_HTTP_CLIENT_READ_TIMEOUT,
        LlmModulePropertiesConstants.SPRING_HTTP_CLIENT_CONNECT_TIMEOUT,
        LlmModulePropertiesConstants.SPRING_AI_AGENT_ENABLED,
        LlmModulePropertiesConstants.SPRING_AI_AGENT_MAX_SUB_AGENT_TURNS
    );

    @Getter
    private final LlmModuleProperties llmProperties;

    @Getter
    private final SpringAiProperties springAiProperties;

    @Getter
    private final Properties properties;

    @Getter
    private List<ConfiguracaoSistemaDTO<?>> configurations;

    public LlmConfigurationProvider(LlmModuleProperties llmProperties, SpringAiProperties springAiProperties) {
        this.llmProperties = llmProperties;
        this.springAiProperties = springAiProperties;
        this.properties = createProperties();
        this.configurations = buildConfigurations();
    }

    @Override
    public List<ConfiguracaoSistemaDTO<?>> getConfigurations() {
        return configurations;
    }

    @Override
    public String getModulo() {
        return LLMModel.NAME;
    }

    @Override
    public void validar(ConfiguracaoSistemaDTO<?> config) {
        if (!ALLOWED_CHAVES.contains(config.getChave())) {
            log.debug("Chave de configuração ignorada: {}", config.getChave());
            return;
        }

        switch (config.getChave()) {
            case LlmModulePropertiesConstants.ENABLED,
                 LlmModulePropertiesConstants.AGENT_ENABLED,
                 LlmModulePropertiesConstants.AGENT_SUBAGENT_RESOLVER_ENABLED,
                 LlmModulePropertiesConstants.AGENT_BUILT_IN_TOOLS_WEB_SEARCH_ENABLED,
                 LlmModulePropertiesConstants.AGENT_A2A_ENABLED,
                 LlmModulePropertiesConstants.AGENT_MULTI_MODEL_ENABLED,
                 LlmModulePropertiesConstants.SKILL_PROGRESSIVE_DISCLOSURE,
                 LlmModulePropertiesConstants.FERRAMENTA_DISCOVERY_ENABLED,
                 LlmModulePropertiesConstants.FERRAMENTA_DISCOVERY_REFRESH_ON_STARTUP,
                 LlmModulePropertiesConstants.AUDIT_ENABLED,
                 LlmModulePropertiesConstants.SPRING_AI_MCP_SERVER_ENABLED,
                 LlmModulePropertiesConstants.SPRING_AI_MCP_SERVER_AGENT_CARD_ENABLED,
                 LlmModulePropertiesConstants.SPRING_AI_MCP_TOOLS_USECASE_SCAN_ENABLED:
                if (config.getValor() != null) {
                    Boolean.parseBoolean(config.getValor());
                }
                break;
            case LlmModulePropertiesConstants.AGENT_MAX_SUB_AGENT_TURNS:
                if (config.getValor() != null) {
                    try {
                        int value = Integer.parseInt(config.getValor());
                        if (value <= 0) {
                            throw new IllegalArgumentException("Max sub-agent turns deve ser positivo");
                        }
                    } catch (NumberFormatException e) {
                        throw new IllegalArgumentException("Valor inválido: " + config.getValor());
                    }
                }
                break;
            case LlmModulePropertiesConstants.SPRING_AI_OLLAMA_CHAT_OPTIONS_TEMPERATURE:
                if (config.getValor() != null) {
                    try {
                        double value = Double.parseDouble(config.getValor());
                        if (value < 0 || value > 2) {
                            throw new IllegalArgumentException("Temperatura deve estar entre 0 e 2");
                        }
                    } catch (NumberFormatException e) {
                        throw new IllegalArgumentException("Valor inválido: " + config.getValor());
                    }
                }
                break;
            default:
                break;
        }
        log.debug("Validação concluída para configuração LLM: {}", config.getChave());
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
            case LlmModulePropertiesConstants.ENABLED -> llmProperties.setEnabled(Boolean.parseBoolean(valor));
            case LlmModulePropertiesConstants.AGENT_ENABLED ->
                llmProperties.getAgent().setEnabled(Boolean.parseBoolean(valor));
            case LlmModulePropertiesConstants.AGENT_ORCHESTRATOR_ID ->
                llmProperties.getAgent().setOrchestratorId(valor);
            case LlmModulePropertiesConstants.AGENT_TOOL_SCAN_PACKAGES ->
                llmProperties.getAgent().setToolScanPackages(new ArrayList<>(List.of(valor.split(","))));
            case LlmModulePropertiesConstants.AGENT_MAX_SUB_AGENT_TURNS ->
                llmProperties.getAgent().setMaxSubAgentTurns(Integer.parseInt(valor));
            case LlmModulePropertiesConstants.AGENT_SUBAGENT_RESOLVER_ENABLED ->
                llmProperties.getAgent().getSubagentResolver().setEnabled(Boolean.parseBoolean(valor));
            case LlmModulePropertiesConstants.AGENT_SUBAGENT_RESOLVER_TYPE ->
                llmProperties.getAgent().getSubagentResolver().setType(valor);
            case LlmModulePropertiesConstants.AGENT_BUILT_IN_TOOLS_WEB_SEARCH_ENABLED ->
                llmProperties.getAgent().getBuiltInTools().getWebSearch().setEnabled(Boolean.parseBoolean(valor));
            case LlmModulePropertiesConstants.AGENT_A2A_ENABLED ->
                llmProperties.getAgent().getA2a().setEnabled(Boolean.parseBoolean(valor));
            case LlmModulePropertiesConstants.AGENT_A2A_SERVER_URL ->
                llmProperties.getAgent().getA2a().setServerUrl(valor);
            case LlmModulePropertiesConstants.AGENT_A2A_AGENT_ID -> llmProperties.getAgent().getA2a().setAgentId(valor);
            case LlmModulePropertiesConstants.AGENT_MULTI_MODEL_ENABLED ->
                llmProperties.getAgent().getMultiModel().setEnabled(Boolean.parseBoolean(valor));
            case LlmModulePropertiesConstants.AGENT_MULTI_MODEL_DEFAULT_MODEL ->
                llmProperties.getAgent().getMultiModel().setDefaultModel(valor);
            case LlmModulePropertiesConstants.SKILL_PROGRESSIVE_DISCLOSURE ->
                llmProperties.getFerramenta().setProgressiveDisclosure(Boolean.parseBoolean(valor));
            case LlmModulePropertiesConstants.FERRAMENTA_DISCOVERY_ENABLED ->
                llmProperties.getFerramenta().getDiscovery().setEnabled(Boolean.parseBoolean(valor));
            case LlmModulePropertiesConstants.FERRAMENTA_DISCOVERY_SCAN_PACKAGES ->
                llmProperties.getFerramenta().getDiscovery().setScanPackages(new ArrayList<>(List.of(valor.split(","))));
            case LlmModulePropertiesConstants.FERRAMENTA_DISCOVERY_REFRESH_ON_STARTUP ->
                llmProperties.getFerramenta().getDiscovery().setRefreshOnStartup(Boolean.parseBoolean(valor));
            case LlmModulePropertiesConstants.AUDIT_ENABLED ->
                llmProperties.getAudit().setEnabled(Boolean.parseBoolean(valor));
            case LlmModulePropertiesConstants.AUDIT_TABLE -> llmProperties.getAudit().setTable(valor);
            case LlmModulePropertiesConstants.SPRING_AI_MODEL_CHAT -> springAiProperties.getModel().setChat(valor);
            case LlmModulePropertiesConstants.SPRING_AI_MODEL_EMBEDDING -> springAiProperties.getModel().setEmbedding(valor);
            case LlmModulePropertiesConstants.SPRING_AI_OLLAMA_BASE_URL -> springAiProperties.getOllama().setBaseUrl(valor);
            case LlmModulePropertiesConstants.SPRING_AI_OLLAMA_CHAT_OPTIONS_MODEL ->
                springAiProperties.getOllama().getChat().setModel(valor);
            case LlmModulePropertiesConstants.SPRING_AI_OLLAMA_CHAT_OPTIONS_TEMPERATURE ->
                springAiProperties.getOllama().getChat().setTemperature(Double.parseDouble(valor));
            case LlmModulePropertiesConstants.SPRING_AI_OLLAMA_EMBEDDING_OPTIONS_MODEL ->
                springAiProperties.getOllama().getEmbedding().setModel(valor);
            case LlmModulePropertiesConstants.SPRING_AI_MCP_SERVER_ENABLED ->
                springAiProperties.getMcp().setEnabled(Boolean.parseBoolean(valor));
            case LlmModulePropertiesConstants.SPRING_AI_MCP_SERVER_NAME -> springAiProperties.getMcp().setName(valor);
            case LlmModulePropertiesConstants.SPRING_AI_MCP_SERVER_VERSION -> springAiProperties.getMcp().setVersion(valor);
            case LlmModulePropertiesConstants.SPRING_AI_MCP_SERVER_SSE_ENDPOINT ->
                springAiProperties.getMcp().setSseEndpoint(valor);
            case LlmModulePropertiesConstants.SPRING_AI_MCP_SERVER_PATH -> springAiProperties.getMcp().setPath(valor);
            case LlmModulePropertiesConstants.SPRING_AI_MCP_SERVER_AGENT_CARD_ENABLED ->
                springAiProperties.getMcp().getAgentCard().setEnabled(Boolean.parseBoolean(valor));
            case LlmModulePropertiesConstants.SPRING_AI_MCP_SERVER_AGENT_CARD_PATH ->
                springAiProperties.getMcp().getAgentCard().setPath(valor);
            case LlmModulePropertiesConstants.SPRING_AI_MCP_TOOLS_USECASE_SCAN_ENABLED ->
                springAiProperties.getMcp().getTools().getUsecaseScan().setEnabled(Boolean.parseBoolean(valor));
            case LlmModulePropertiesConstants.SPRING_AI_MCP_TOOLS_USECASE_SCAN_BASE_PACKAGE ->
                springAiProperties.getMcp().getTools().getUsecaseScan().setBasePackage(valor);
            case LlmModulePropertiesConstants.SPRING_HTTP_CLIENT_READ_TIMEOUT ->
                llmProperties.getHttp().setReadTimeout(Integer.parseInt(valor));
            case LlmModulePropertiesConstants.SPRING_HTTP_CLIENT_CONNECT_TIMEOUT ->
                llmProperties.getHttp().setConnectTimeout(Integer.parseInt(valor));
            default -> {
                log.debug("Chave de configuração LLM não mapeada para aplicação: {}", chave);
                return;
            }
        }

        this.properties.clear();
        this.properties.putAll(createProperties());

        log.info("Configuração LLM aplicada: chave={}, valor={}", chave, valor);
    }

    private List<ConfiguracaoSistemaDTO<?>> buildConfigurations() {
        List<ConfiguracaoSistemaDTO<?>> configs = new ArrayList<>();

        // Core
        add(configs, LlmModulePropertiesConstants.ENABLED, llmProperties.isEnabled(), TipoConfiguracao.BOOLEAN, "LLM", "Habilitar LLM");

        // Security
        add(configs, LlmModulePropertiesConstants.SECURITY_PROTECTED_PATHS, String.join(",", llmProperties.getSecurity().getProtectedPaths()), TipoConfiguracao.STRING, "Security", "Protected paths");

        // Agent
        add(configs, LlmModulePropertiesConstants.AGENT_ENABLED, llmProperties.getAgent().isEnabled(), TipoConfiguracao.BOOLEAN, "Agent", "Habilitar agente");
        add(configs, LlmModulePropertiesConstants.AGENT_ORCHESTRATOR_ID, llmProperties.getAgent().getOrchestratorId(), TipoConfiguracao.STRING, "Agent", "ID do orquestrador");
        add(configs, LlmModulePropertiesConstants.AGENT_TOOL_SCAN_PACKAGES, String.join(",", llmProperties.getAgent().getToolScanPackages()), TipoConfiguracao.STRING, "Agent", "Pacotes para scan de tools");
        add(configs, LlmModulePropertiesConstants.AGENT_MAX_SUB_AGENT_TURNS, llmProperties.getAgent().getMaxSubAgentTurns(), TipoConfiguracao.INTEGER, "Agent", "Máximo de sub-agent turns");
        add(configs, LlmModulePropertiesConstants.AGENT_SUBAGENT_RESOLVER_ENABLED, llmProperties.getAgent().getSubagentResolver().isEnabled(), TipoConfiguracao.BOOLEAN, "Agent", "Habilitar subagent resolver");
        add(configs, LlmModulePropertiesConstants.AGENT_SUBAGENT_RESOLVER_TYPE, llmProperties.getAgent().getSubagentResolver().getType(), TipoConfiguracao.STRING, "Agent", "Tipo de subagent resolver");
        add(configs, LlmModulePropertiesConstants.AGENT_BUILT_IN_TOOLS_WEB_SEARCH_ENABLED, llmProperties.getAgent().getBuiltInTools().getWebSearch().isEnabled(), TipoConfiguracao.BOOLEAN, "Agent", "Habilitar web search tool");
        add(configs, LlmModulePropertiesConstants.AGENT_A2A_ENABLED, llmProperties.getAgent().getA2a().isEnabled(), TipoConfiguracao.BOOLEAN, "Agent", "Habilitar A2A");
        add(configs, LlmModulePropertiesConstants.AGENT_A2A_SERVER_URL, llmProperties.getAgent().getA2a().getServerUrl(), TipoConfiguracao.STRING, "Agent", "URL do servidor A2A");
        add(configs, LlmModulePropertiesConstants.AGENT_A2A_AGENT_ID, llmProperties.getAgent().getA2a().getAgentId(), TipoConfiguracao.STRING, "Agent", "ID do agente A2A");
        add(configs, LlmModulePropertiesConstants.AGENT_MULTI_MODEL_ENABLED, llmProperties.getAgent().getMultiModel().isEnabled(), TipoConfiguracao.BOOLEAN, "Agent", "Habilitar multi-modelo");
        add(configs, LlmModulePropertiesConstants.AGENT_MULTI_MODEL_DEFAULT_MODEL, llmProperties.getAgent().getMultiModel().getDefaultModel(), TipoConfiguracao.STRING, "Agent", "Modelo padrão multi-modelo");

        // Skill
        add(configs, LlmModulePropertiesConstants.SKILL_PROGRESSIVE_DISCLOSURE, llmProperties.getFerramenta().isProgressiveDisclosure(), TipoConfiguracao.BOOLEAN, "Skill", "Progressive disclosure");

        // Ferramenta
        add(configs, LlmModulePropertiesConstants.FERRAMENTA_DISCOVERY_ENABLED, llmProperties.getFerramenta().getDiscovery().isEnabled(), TipoConfiguracao.BOOLEAN, "Ferramenta", "Habilitar descoberta");
        add(configs, LlmModulePropertiesConstants.FERRAMENTA_DISCOVERY_SCAN_PACKAGES, String.join(",", llmProperties.getFerramenta().getDiscovery().getScanPackages()), TipoConfiguracao.STRING, "Ferramenta", "Pacotes para scan");
        add(configs, LlmModulePropertiesConstants.FERRAMENTA_DISCOVERY_REFRESH_ON_STARTUP, llmProperties.getFerramenta().getDiscovery().isRefreshOnStartup(), TipoConfiguracao.BOOLEAN, "Ferramenta", "Refresh no startup");

        // Audit
        add(configs, LlmModulePropertiesConstants.AUDIT_ENABLED, llmProperties.getAudit().isEnabled(), TipoConfiguracao.BOOLEAN, "Audit", "Habilitar auditoria");
        add(configs, LlmModulePropertiesConstants.AUDIT_TABLE, llmProperties.getAudit().getTable(), TipoConfiguracao.STRING, "Audit", "Tabela de auditoria");

        // Model
        add(configs, LlmModulePropertiesConstants.SPRING_AI_MODEL_CHAT, springAiProperties.getModel().getChat(), TipoConfiguracao.STRING, "Model", "Modelo de chat");
        add(configs, LlmModulePropertiesConstants.SPRING_AI_MODEL_EMBEDDING, springAiProperties.getModel().getEmbedding(), TipoConfiguracao.STRING, "Model", "Modelo de embedding");

        // Ollama
        add(configs, LlmModulePropertiesConstants.SPRING_AI_OLLAMA_BASE_URL, springAiProperties.getOllama().getBaseUrl(), TipoConfiguracao.STRING, "Ollama", "URL base do Ollama");
        add(configs, LlmModulePropertiesConstants.SPRING_AI_OLLAMA_CHAT_OPTIONS_MODEL, springAiProperties.getOllama().getChat().getModel(), TipoConfiguracao.STRING, "Ollama", "Modelo de chat");
        add(configs, LlmModulePropertiesConstants.SPRING_AI_OLLAMA_CHAT_OPTIONS_TEMPERATURE, String.valueOf(springAiProperties.getOllama().getChat().getTemperature()), TipoConfiguracao.STRING, "Ollama", "Temperatura");
        add(configs, LlmModulePropertiesConstants.SPRING_AI_OLLAMA_EMBEDDING_OPTIONS_MODEL, springAiProperties.getOllama().getEmbedding().getModel(), TipoConfiguracao.STRING, "Ollama", "Modelo de embedding");

        // MCP Server
        add(configs, LlmModulePropertiesConstants.SPRING_AI_MCP_SERVER_ENABLED, springAiProperties.getMcp().isEnabled(), TipoConfiguracao.BOOLEAN, "MCP", "Habilitar MCP server");
        add(configs, LlmModulePropertiesConstants.SPRING_AI_MCP_SERVER_NAME, springAiProperties.getMcp().getName(), TipoConfiguracao.STRING, "MCP", "Nome do servidor MCP");
        add(configs, LlmModulePropertiesConstants.SPRING_AI_MCP_SERVER_VERSION, springAiProperties.getMcp().getVersion(), TipoConfiguracao.STRING, "MCP", "Versão do servidor MCP");
        add(configs, LlmModulePropertiesConstants.SPRING_AI_MCP_SERVER_SSE_ENDPOINT, springAiProperties.getMcp().getSseEndpoint(), TipoConfiguracao.STRING, "MCP", "Endpoint SSE");
        add(configs, LlmModulePropertiesConstants.SPRING_AI_MCP_SERVER_PATH, springAiProperties.getMcp().getPath(), TipoConfiguracao.STRING, "MCP", "Caminho base");
        add(configs, LlmModulePropertiesConstants.SPRING_AI_MCP_SERVER_AGENT_CARD_ENABLED, springAiProperties.getMcp().getAgentCard().isEnabled(), TipoConfiguracao.BOOLEAN, "MCP", "Habilitar agent-card");
        add(configs, LlmModulePropertiesConstants.SPRING_AI_MCP_SERVER_AGENT_CARD_PATH, springAiProperties.getMcp().getAgentCard().getPath(), TipoConfiguracao.STRING, "MCP", "Caminho do agent-card");
        add(configs, LlmModulePropertiesConstants.SPRING_AI_MCP_TOOLS_USECASE_SCAN_ENABLED, springAiProperties.getMcp().getTools().getUsecaseScan().isEnabled(), TipoConfiguracao.BOOLEAN, "MCP", "Habilitar usecase scan");
        add(configs, LlmModulePropertiesConstants.SPRING_AI_MCP_TOOLS_USECASE_SCAN_BASE_PACKAGE, springAiProperties.getMcp().getTools().getUsecaseScan().getBasePackage(), TipoConfiguracao.STRING, "MCP", "Base package para usecase scan");

        // HTTP
        add(configs, LlmModulePropertiesConstants.SPRING_HTTP_CLIENT_READ_TIMEOUT, llmProperties.getHttp().getReadTimeout(), TipoConfiguracao.INTEGER, "HTTP", "Read timeout");
        add(configs, LlmModulePropertiesConstants.SPRING_HTTP_CLIENT_CONNECT_TIMEOUT, llmProperties.getHttp().getConnectTimeout(), TipoConfiguracao.INTEGER, "HTTP", "Connect timeout");

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

        properties.setProperty(LlmModulePropertiesConstants.ENABLED, String.valueOf(llmProperties.isEnabled()));
        properties.setProperty(LlmModulePropertiesConstants.SECURITY_PROTECTED_PATHS, String.join(",", llmProperties.getSecurity().getProtectedPaths()));
        properties.setProperty(LlmModulePropertiesConstants.AGENT_ENABLED, String.valueOf(llmProperties.getAgent().isEnabled()));
        properties.setProperty(LlmModulePropertiesConstants.AGENT_ORCHESTRATOR_ID, llmProperties.getAgent().getOrchestratorId());
        properties.setProperty(LlmModulePropertiesConstants.AGENT_TOOL_SCAN_PACKAGES, String.join(",", llmProperties.getAgent().getToolScanPackages()));
        properties.setProperty(LlmModulePropertiesConstants.AGENT_MAX_SUB_AGENT_TURNS, String.valueOf(llmProperties.getAgent().getMaxSubAgentTurns()));
        properties.setProperty(LlmModulePropertiesConstants.AGENT_SUBAGENT_RESOLVER_ENABLED, String.valueOf(llmProperties.getAgent().getSubagentResolver().isEnabled()));
        properties.setProperty(LlmModulePropertiesConstants.AGENT_SUBAGENT_RESOLVER_TYPE, llmProperties.getAgent().getSubagentResolver().getType());
        properties.setProperty(LlmModulePropertiesConstants.AGENT_BUILT_IN_TOOLS_WEB_SEARCH_ENABLED, String.valueOf(llmProperties.getAgent().getBuiltInTools().getWebSearch().isEnabled()));
        properties.setProperty(LlmModulePropertiesConstants.AGENT_A2A_ENABLED, String.valueOf(llmProperties.getAgent().getA2a().isEnabled()));
        properties.setProperty(LlmModulePropertiesConstants.AGENT_A2A_SERVER_URL, llmProperties.getAgent().getA2a().getServerUrl());
        properties.setProperty(LlmModulePropertiesConstants.AGENT_A2A_AGENT_ID, llmProperties.getAgent().getA2a().getAgentId());
        properties.setProperty(LlmModulePropertiesConstants.AGENT_MULTI_MODEL_ENABLED, String.valueOf(llmProperties.getAgent().getMultiModel().isEnabled()));
        properties.setProperty(LlmModulePropertiesConstants.AGENT_MULTI_MODEL_DEFAULT_MODEL, llmProperties.getAgent().getMultiModel().getDefaultModel());
        properties.setProperty(LlmModulePropertiesConstants.SKILL_PROGRESSIVE_DISCLOSURE, String.valueOf(llmProperties.getFerramenta().isProgressiveDisclosure()));
        properties.setProperty(LlmModulePropertiesConstants.FERRAMENTA_DISCOVERY_ENABLED, String.valueOf(llmProperties.getFerramenta().getDiscovery().isEnabled()));
        properties.setProperty(LlmModulePropertiesConstants.FERRAMENTA_DISCOVERY_SCAN_PACKAGES, String.join(",", llmProperties.getFerramenta().getDiscovery().getScanPackages()));
        properties.setProperty(LlmModulePropertiesConstants.FERRAMENTA_DISCOVERY_REFRESH_ON_STARTUP, String.valueOf(llmProperties.getFerramenta().getDiscovery().isRefreshOnStartup()));
        properties.setProperty(LlmModulePropertiesConstants.AUDIT_ENABLED, String.valueOf(llmProperties.getAudit().isEnabled()));
        properties.setProperty(LlmModulePropertiesConstants.AUDIT_TABLE, llmProperties.getAudit().getTable());
        properties.setProperty(LlmModulePropertiesConstants.SPRING_AI_MODEL_CHAT, springAiProperties.getModel().getChat());
        properties.setProperty(LlmModulePropertiesConstants.SPRING_AI_MODEL_EMBEDDING, springAiProperties.getModel().getEmbedding());
        properties.setProperty(LlmModulePropertiesConstants.SPRING_AI_OLLAMA_BASE_URL, springAiProperties.getOllama().getBaseUrl());
        properties.setProperty(LlmModulePropertiesConstants.SPRING_AI_OLLAMA_CHAT_OPTIONS_MODEL, springAiProperties.getOllama().getChat().getModel());
        properties.setProperty(LlmModulePropertiesConstants.SPRING_AI_OLLAMA_CHAT_OPTIONS_TEMPERATURE, String.valueOf(springAiProperties.getOllama().getChat().getTemperature()));
        properties.setProperty(LlmModulePropertiesConstants.SPRING_AI_OLLAMA_EMBEDDING_OPTIONS_MODEL, springAiProperties.getOllama().getEmbedding().getModel());
        properties.setProperty(LlmModulePropertiesConstants.SPRING_AI_MCP_SERVER_ENABLED, String.valueOf(springAiProperties.getMcp().isEnabled()));
        properties.setProperty(LlmModulePropertiesConstants.SPRING_AI_MCP_SERVER_NAME, springAiProperties.getMcp().getName());
        properties.setProperty(LlmModulePropertiesConstants.SPRING_AI_MCP_SERVER_VERSION, springAiProperties.getMcp().getVersion());
        properties.setProperty(LlmModulePropertiesConstants.SPRING_AI_MCP_SERVER_SSE_ENDPOINT, springAiProperties.getMcp().getSseEndpoint());
        properties.setProperty(LlmModulePropertiesConstants.SPRING_AI_MCP_SERVER_PATH, springAiProperties.getMcp().getPath());
        properties.setProperty(LlmModulePropertiesConstants.SPRING_AI_MCP_SERVER_AGENT_CARD_ENABLED, String.valueOf(springAiProperties.getMcp().getAgentCard().isEnabled()));
        properties.setProperty(LlmModulePropertiesConstants.SPRING_AI_MCP_SERVER_AGENT_CARD_PATH, springAiProperties.getMcp().getAgentCard().getPath());
        properties.setProperty(LlmModulePropertiesConstants.SPRING_AI_MCP_TOOLS_USECASE_SCAN_ENABLED, String.valueOf(springAiProperties.getMcp().getTools().getUsecaseScan().isEnabled()));
        properties.setProperty(LlmModulePropertiesConstants.SPRING_AI_MCP_TOOLS_USECASE_SCAN_BASE_PACKAGE, springAiProperties.getMcp().getTools().getUsecaseScan().getBasePackage());
        properties.setProperty(LlmModulePropertiesConstants.SPRING_HTTP_CLIENT_READ_TIMEOUT, String.valueOf(llmProperties.getHttp().getReadTimeout()));
        properties.setProperty(LlmModulePropertiesConstants.SPRING_HTTP_CLIENT_CONNECT_TIMEOUT, String.valueOf(llmProperties.getHttp().getConnectTimeout()));

        // Spring AI Agent
        properties.setProperty(LlmModulePropertiesConstants.SPRING_AI_AGENT_ENABLED, String.valueOf(springAiProperties.getAgent().isEnabled()));
        properties.setProperty(LlmModulePropertiesConstants.SPRING_AI_AGENT_MAX_SUB_AGENT_TURNS, String.valueOf(springAiProperties.getAgent().getMaxSubAgentTurns()));

        return properties;
    }
}
