package com.ia.core.llm.rest.web;

import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Controller REST para expor Agent Card (MCP).
 * <p>
 * Expõe o endpoint /.well-known/agent-card.json conforme especificado no ADR-048
 * para compatibilidade com o protocolo MCP.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@RestController
@RequestMapping("/.well-known")
public class LlmAgentCardController {

  /**
   * Retorna o Agent Card em formato JSON.
   *
   * @return Map com informações do Agent Card
   */
  @GetMapping("/agent-card.json")
  public Map<String, Object> getAgentCard() {
    return Map.of(
        "name", "ia-core-llm",
        "version", "1.0.0",
        "description", "IA Core LLM Agent with MCP support",
        "capabilities", Map.of(
            "streaming", true,
            "tools", true,
            "memory", true
        )
    );
  }
}
