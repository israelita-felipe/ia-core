package com.ia.core.llm.service.config;

/**
 * Constantes para as chaves de propriedades do módulo LLM.
 *
 * <p>Estas constantes são usadas internamente por {@link LlmModuleProperties}
 * e podem ser usadas para referenciar as chaves de configuração.</p>
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
public final class LlmModulePropertiesConstants {

    // =========================================================================
    // Core Properties (ia-core.llm.*)
    // =========================================================================
    /**
     * Property key: ia-core.llm.enabled
     */
    public static final String ENABLED = "llm.enabled";

    // =========================================================================
    // Security Properties (ia-core.llm.security.*)
    // =========================================================================
    /**
     * Property key: ia-core.llm.security.protected-paths
     */
    public static final String SECURITY_PROTECTED_PATHS = "security.protected-paths";

    // =========================================================================
    // Agent Properties (ia-core.llm.agent.*)
    // =========================================================================
    /**
     * Property key: ia-core.llm.agent.orchestrator-id
     */
    public static final String AGENT_ORCHESTRATOR_ID = "agent.orchestrator-id";

    /**
     * Property key: ia-core.llm.agent.tool-scan-packages
     */
    public static final String AGENT_TOOL_SCAN_PACKAGES = "agent.tool-scan-packages";

    /**
     * Property key: ia-core.llm.agent.enabled
     */
    public static final String AGENT_ENABLED = "agent.enabled";

    /**
     * Property key: ia-core.llm.agent.max-sub-agent-turns
     */
    public static final String AGENT_MAX_SUB_AGENT_TURNS = "agent.max-sub-agent-turns";

    /**
     * Property key: ia-core.llm.agent.subagent-resolver.enabled
     */
    public static final String AGENT_SUBAGENT_RESOLVER_ENABLED = "agent.subagent-resolver.enabled";

    /**
     * Property key: ia-core.llm.agent.subagent-resolver.type
     */
    public static final String AGENT_SUBAGENT_RESOLVER_TYPE = "agent.subagent-resolver.type";

    /**
     * Property key: ia-core.llm.agent.built-in-tools.web-search.enabled
     */
    public static final String AGENT_BUILT_IN_TOOLS_WEB_SEARCH_ENABLED = "agent.built-in-tools.web-search.enabled";

    /**
     * Property key: ia-core.llm.agent.a2a.enabled
     */
    public static final String AGENT_A2A_ENABLED = "agent.a2a.enabled";

    /**
     * Property key: ia-core.llm.agent.a2a.server-url
     */
    public static final String AGENT_A2A_SERVER_URL = "agent.a2a.server-url";

    /**
     * Property key: ia-core.llm.agent.a2a.agent-id
     */
    public static final String AGENT_A2A_AGENT_ID = "agent.a2a.agent-id";

    /**
     * Property key: ia-core.llm.agent.multi-model.enabled
     */
    public static final String AGENT_MULTI_MODEL_ENABLED = "agent.multi-model.enabled";

    /**
     * Property key: ia-core.llm.agent.multi-model.default-model
     */
    public static final String AGENT_MULTI_MODEL_DEFAULT_MODEL = "agent.multi-model.default-model";

    // =========================================================================
    // Skill Properties (ia-core.llm.skill.*)
    // =========================================================================
    /**
     * Property key: ia-core.llm.skill.progressive-disclosure
     */
    public static final String SKILL_PROGRESSIVE_DISCLOSURE = "skill.progressive-disclosure";

    // =========================================================================
    // Ferramenta Properties (ia-core.llm.ferramenta.*)
    // =========================================================================
    /**
     * Property key: ia-core.llm.ferramenta.discovery.enabled
     */
    public static final String FERRAMENTA_DISCOVERY_ENABLED = "ferramenta.discovery.enabled";

    /**
     * Property key: ia-core.llm.ferramenta.discovery.scan-packages
     */
    public static final String FERRAMENTA_DISCOVERY_SCAN_PACKAGES = "ferramenta.discovery.scan-packages";

    /**
     * Property key: ia-core.llm.ferramenta.discovery.refresh-on-startup
     */
    public static final String FERRAMENTA_DISCOVERY_REFRESH_ON_STARTUP = "ferramenta.discovery.refresh-on-startup";

    // =========================================================================
    // Audit Properties (ia-core.llm.audit.*)
    // =========================================================================
    /**
     * Property key: ia-core.llm.audit.enabled
     */
    public static final String AUDIT_ENABLED = "audit.enabled";

    /**
     * Property key: ia-core.llm.audit.table
     */
    public static final String AUDIT_TABLE = "audit.table";

    // =========================================================================
    // Spring AI Properties (spring.ai.*)
    // =========================================================================
    /**
     * Property key: spring.ai.agent.enabled
     */
    public static final String SPRING_AI_AGENT_ENABLED = "spring.ai.agent.enabled";

    /**
     * Property key: spring.ai.agent.max-sub-agent-turns
     */
    public static final String SPRING_AI_AGENT_MAX_SUB_AGENT_TURNS = "spring.ai.agent.max-sub-agent-turns";

    // =========================================================================
    // Spring AI Model Properties (spring.ai.*)
    // =========================================================================
    /**
     * Property key: spring.ai.model.chat
     */
    public static final String SPRING_AI_MODEL_CHAT = "spring.ai.model.chat";

    /**
     * Property key: spring.ai.model.embedding
     */
    public static final String SPRING_AI_MODEL_EMBEDDING = "spring.ai.model.embedding";

    /**
     * Property key: spring.ai.ollama.base-url
     */
    public static final String SPRING_AI_OLLAMA_BASE_URL = "spring.ai.ollama.base-url";

    /**
     * Property key: spring.ai.ollama.chat.options.model
     */
    public static final String SPRING_AI_OLLAMA_CHAT_OPTIONS_MODEL = "spring.ai.ollama.chat.options.model";

    /**
     * Property key: spring.ai.ollama.chat.options.temperature
     */
    public static final String SPRING_AI_OLLAMA_CHAT_OPTIONS_TEMPERATURE = "spring.ai.ollama.chat.options.temperature";

    /**
     * Property key: spring.ai.ollama.embedding.options.model
     */
    public static final String SPRING_AI_OLLAMA_EMBEDDING_OPTIONS_MODEL = "spring.ai.ollama.embedding.options.model";

    /**
     * Property key: spring.ai.mcp.server.enabled
     */
    public static final String SPRING_AI_MCP_SERVER_ENABLED = "spring.ai.mcp.server.enabled";

    /**
     * Property key: spring.ai.mcp.server.name
     */
    public static final String SPRING_AI_MCP_SERVER_NAME = "spring.ai.mcp.server.name";

    /**
     * Property key: spring.ai.mcp.server.version
     */
    public static final String SPRING_AI_MCP_SERVER_VERSION = "spring.ai.mcp.server.version";

    /**
     * Property key: spring.ai.mcp.server.sse-endpoint
     */
    public static final String SPRING_AI_MCP_SERVER_SSE_ENDPOINT = "spring.ai.mcp.server.sse-endpoint";

    /**
     * Property key: spring.ai.mcp.server.path
     */
    public static final String SPRING_AI_MCP_SERVER_PATH = "spring.ai.mcp.server.path";

    /**
     * Property key: spring.ai.mcp.server.agent-card.enabled
     */
    public static final String SPRING_AI_MCP_SERVER_AGENT_CARD_ENABLED = "spring.ai.mcp.server.agent-card.enabled";

    /**
     * Property key: spring.ai.mcp.server.agent-card.path
     */
    public static final String SPRING_AI_MCP_SERVER_AGENT_CARD_PATH = "spring.ai.mcp.server.agent-card.path";

    /**
     * Property key: spring.ai.mcp.tools.usecase-scan.enabled
     */
    public static final String SPRING_AI_MCP_TOOLS_USECASE_SCAN_ENABLED = "spring.ai.mcp.tools.usecase-scan.enabled";

    /**
     * Property key: spring.ai.mcp.tools.usecase-scan.base-package
     */
    public static final String SPRING_AI_MCP_TOOLS_USECASE_SCAN_BASE_PACKAGE = "spring.ai.mcp.tools.usecase-scan.base-package";

    /**
     * Property key: spring.ai.mcp.server.sse-endpoint
     */
    public static final String SPRING_AI_MCP_SERVER_ENDPOINT = "spring.ai.mcp.server.sse-endpoint";

    /**
     * Property key: spring.http.client.read-timeout
     */
    public static final String SPRING_HTTP_CLIENT_READ_TIMEOUT = "spring.http.client.read-timeout";

    /**
     * Property key: spring.http.client.connect-timeout
     */
    public static final String SPRING_HTTP_CLIENT_CONNECT_TIMEOUT = "spring.http.client.connect-timeout";

    private LlmModulePropertiesConstants() {
        // Utility class
    }
}
