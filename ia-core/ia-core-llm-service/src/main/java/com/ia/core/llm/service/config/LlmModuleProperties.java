package com.ia.core.llm.service.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * Propriedades de configuração do módulo LLM.
 * <p>
 * Define as propriedades configuráveis para o módulo de Large Language Model,
 * incluindo segurança, agentes, skills, ferramentas e auditoria.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Data
@ConfigurationProperties(prefix = "ia-core.llm")
public class LlmModuleProperties {

  private boolean enabled = true;
  private Security security = new Security();
  private Agent agent = new Agent();
  private Ferramenta ferramenta = new Ferramenta();
  private Audit audit = new Audit();
  private HttpConfig http = new HttpConfig();

  @Data
  public static class Security {
    private List<String> protectedPaths = List.of("/mcp/**", "/.well-known/agent-card.json");
  }

  @Data
  public static class Agent {
    private String orchestratorId = "ia-core-orchestrator";
    private List<String> toolScanPackages = new ArrayList<>(List.of("com.ia"));
    private boolean enabled = true;
    private int maxSubAgentTurns = 10;
    private SubagentResolver subagentResolver = new SubagentResolver();
    private BuiltInTools builtInTools = new BuiltInTools();
    private A2A a2a = new A2A();
    private MultiModel multiModel = new MultiModel();

    @Data
    public static class SubagentResolver {
      private boolean enabled = true;
      private String type = "database";
    }

    @Data
    public static class BuiltInTools {
      private WebSearch webSearch = new WebSearch();

      @Data
      public static class WebSearch {
        private boolean enabled = true;
      }
    }

    @Data
    public static class A2A {
      private boolean enabled = true;
      private String serverUrl = "http://localhost:8080";
      private String agentId = "ia-core-llm-agent";
    }

    @Data
    public static class MultiModel {
      private boolean enabled = true;
      private String defaultModel = "llama3.2-vision";
    }
  }

  @Data
  public static class Ferramenta {
    private boolean progressiveDisclosure = true;
    private Discovery discovery = new Discovery();

    @Data
    public static class Discovery {
      private boolean enabled = true;
      private List<String> scanPackages = new ArrayList<>(List.of("com.ia"));
      private boolean refreshOnStartup = true;
    }
  }

  @Data
  public static class Audit {
    private boolean enabled = true;
    private String table = "LLM_AI_INTERACTION_LOG";
  }

  @Data
  public static class HttpConfig {
    private int readTimeout = 600000;
    private int connectTimeout = 600000;
  }
}
