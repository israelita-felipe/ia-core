package com.ia.core.llm.service.config;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * Propriedades nativas do Spring AI.
 * <p>
 * Captura as propriedades do namespace {@code spring.ai.*},
 * incluindo configurações de modelo, Ollama e servidor MCP.
 * <p>
 * Estrutura de configuração YAML:
 * <pre>
 * spring:
 *   ai:
 *     model:
 *       chat: ollama
 *       embedding: ollama
 *     ollama:
 *       base-url: http://localhost:11434
 *       chat:
 *         options:
 *           model: llama3.2-vision
 *           temperature: 0.3
 *       embedding:
 *         options:
 *           model: llama3.2-vision
 *     mcp:
 *       server:
 *         enabled: true
 *         name: ia-core-llm
 *         version: 1.0.0
 *         sse-endpoint: /mcp/sse
 *         path: /mcp
 *         agent-card:
 *           enabled: true
 *           path: /.well-known/agent-card.json
 *         tools:
 *           usecase-scan:
 *             enabled: true
 *             base-package: com.ia
 * </pre>
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Data
@ConfigurationProperties(prefix = "spring.ai")
public class SpringAiProperties {

    /**
     * Configuração de modelos.
     */
    private ModelConfig model = new ModelConfig();

    /**
     * Configuração do Ollama.
     */
    private OllamaConfig ollama = new OllamaConfig();

    /**
     * Configuração de agentes.
     */
    private AgentConfig agent = new AgentConfig();

    /**
     * Configuração do servidor MCP.
     */
    private McpServerConfig mcp = new McpServerConfig();

    @Getter
    @Setter
    public static class ModelConfig {
        /**
         * Modelo de chat.
         */
        private String chat = "ollama";

        /**
         * Modelo de embedding.
         */
        private String embedding = "ollama";
    }

    @Getter
    @Setter
    public static class OllamaConfig {
        /**
         * URL base do Ollama.
         */
        private String baseUrl = "http://localhost:11434";

        /**
         * Opções de chat.
         */
        private ChatOptions chat = new ChatOptions();

        /**
         * Opções de embedding.
         */
        private EmbeddingOptions embedding = new EmbeddingOptions();

        @Getter
        @Setter
        public static class ChatOptions {
            /**
             * Modelo de chat.
             */
            private String model = "llama3.2-vision";

            /**
             * Temperatura do chat.
             */
            private double temperature = 0.3;
        }

        @Getter
        @Setter
        public static class EmbeddingOptions {
            /**
             * Modelo de embedding.
             */
            private String model = "llama3.2-vision";
        }
    }

    @Getter
    @Setter
    public static class AgentConfig {
        /**
         * Habilitar agentes.
         */
        private boolean enabled = true;

        /**
         * Máximo de turnos de sub-agentes.
         */
        private int maxSubAgentTurns = 10;
    }

    @Getter
    @Setter
    public static class McpServerConfig {
        /**
         * Habilitar servidor MCP.
         */
        private boolean enabled = true;

        /**
         * Nome do servidor MCP.
         */
        private String name = "ia-core-llm";

        /**
         * Versão do servidor MCP.
         */
        private String version = "1.0.0";

        /**
         * Endpoint SSE.
         */
        private String sseEndpoint = "/mcp/sse";

        /**
         * Caminho base.
         */
        private String path = "/mcp";

        /**
         * Configuração do agent-card.
         */
        private AgentCard agentCard = new AgentCard();

        /**
         * Configuração de ferramentas.
         */
        private Tools tools = new Tools();

        @Getter
        @Setter
        public static class AgentCard {
            /**
             * Habilitar agent-card.
             */
            private boolean enabled = true;

            /**
             * Caminho do agent-card.
             */
            private String path = "/.well-known/agent-card.json";
        }

        @Getter
        @Setter
        public static class Tools {
            /**
             * Configuração de usecase scan.
             */
            private UsecaseScan usecaseScan = new UsecaseScan();

            @Getter
            @Setter
            public static class UsecaseScan {
                /**
                 * Habilitar usecase scan.
                 */
                private boolean enabled = true;

                /**
                 * Pacote base para scan.
                 */
                private String basePackage = "com.ia";
            }
        }
    }
}
