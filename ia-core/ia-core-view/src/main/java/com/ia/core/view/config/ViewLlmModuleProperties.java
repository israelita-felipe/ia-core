package com.ia.core.view.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * Propriedades de configuração do módulo View com suporte MCP.
 * <p>
 * Define as propriedades configuráveis para o módulo View com suporte a Model Context Protocol (MCP),
 * incluindo segurança, agentes e descoberta de ferramentas em classes *Manager.
 * Esta configuração é independente do ia-core-llm-service para evitar dependência circular.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Data
@ConfigurationProperties(prefix = "ia-core.view.llm")
public class ViewLlmModuleProperties {

  private boolean enabled = true;
  private Security security = new Security();
  private Agent agent = new Agent();
  private Ferramenta ferramenta = new Ferramenta();

  @Data
  public static class Security {
    private List<String> protectedPaths = List.of("/mcp/**", "/.well-known/agent-card.json");
  }

  @Data
  public static class Agent {
    private String orchestratorId = "ia-core-view-orchestrator";
    private List<String> toolScanPackages = new ArrayList<>(List.of("com.ia"));
    private boolean enabled = true;
    private int maxSubAgentTurns = 10;
  }

  @Data
  public static class Ferramenta {
    private Discovery discovery = new Discovery();

    @Data
    public static class Discovery {
      private boolean enabled = true;
      private List<String> scanPackages = new ArrayList<>(List.of("com.ia"));
      private boolean refreshOnStartup = true;
    }
  }
}
