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
  private Skill skill = new Skill();
  private Ferramenta ferramenta = new Ferramenta();
  private Audit audit = new Audit();

  @Data
  public static class Security {
    private List<String> protectedPaths = List.of("/mcp/**", "/.well-known/agent-card.json");
  }

  @Data
  public static class Agent {
    private String orchestratorId = "ia-core-orchestrator";
    private List<String> toolScanPackages = new ArrayList<>(List.of("com.ia.core.llm.service.tool"));
    private boolean enabled = true;
    private int maxSubAgentTurns = 10;
  }

  @Data
  public static class Skill {
    private boolean progressiveDisclosure = true;
  }

  @Data
  public static class Ferramenta {
    private Discovery discovery = new Discovery();

    @Data
    public static class Discovery {
      private boolean enabled = true;
      private List<String> scanPackages = new ArrayList<>(List.of("com.ia.core.llm.service.tool"));
      private boolean refreshOnStartup = true;
    }
  }

  @Data
  public static class Audit {
    private boolean enabled = true;
    private String table = "LLM_AI_INTERACTION_LOG";
  }
}
