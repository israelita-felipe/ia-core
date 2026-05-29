package com.ia.core.llm.rest.a2a;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Controller REST para gerenciamento do protocolo A2A (Agent-to-Agent).
 * <p>
 * Expõe endpoints para gerenciar conexões com servidores A2A remotos,
 * conforme especificado nos CDUs Interface-Agente-Conversacional e Manter-LLM.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/api/${api.version}/llm/a2a")
@RequiredArgsConstructor
public class A2AController {

  @Value("${ia-core.llm.agent.a2a.enabled:false}")
  private boolean a2aEnabled;

  @Value("${ia-core.llm.agent.a2a.server-url:http://localhost:8080}")
  private String serverUrl;

  @Value("${ia-core.llm.agent.a2a.agent-id:ia-core-llm-agent}")
  private String agentId;

  private boolean connected = false;

  /**
   * Conecta a um servidor A2A remoto.
   *
   * @param serverUrl URL do servidor A2A (opcional, usa configuração padrão)
   * @param agentId ID do agente (opcional, usa configuração padrão)
   * @return status da conexão
   */
  @PostMapping("/connect")
  public ResponseEntity<Map<String, Object>> connect(
      @RequestParam(required = false) String serverUrl,
      @RequestParam(required = false) String agentId) {

    log.info("Requisição de conexão A2A: serverUrl={}, agentId={}", serverUrl, agentId);

    if (!a2aEnabled) {
      log.warn("A2A não está habilitado na configuração");
      return ResponseEntity.badRequest()
          .body(Map.of(
              "success", false,
              "message", "A2A não está habilitado na configuração"));
    }

    String targetServerUrl = serverUrl != null ? serverUrl : this.serverUrl;
    String targetAgentId = agentId != null ? agentId : this.agentId;

    try {
      // Simulação de conexão - implementação real usaria spring-ai-agent-utils-a2a
      this.connected = true;
      log.info("Conexão A2A estabelecida: serverUrl={}, agentId={}", targetServerUrl, targetAgentId);

      return ResponseEntity.ok(Map.of(
          "success", true,
          "message", "Conexão A2A estabelecida com sucesso",
          "serverUrl", targetServerUrl,
          "agentId", targetAgentId));
    } catch (Exception e) {
      log.error("Erro ao conectar ao servidor A2A: serverUrl={}", targetServerUrl, e);
      this.connected = false;
      return ResponseEntity.internalServerError()
          .body(Map.of(
              "success", false,
              "message", "Erro ao conectar ao servidor A2A: " + e.getMessage()));
    }
  }

  /**
   * Verifica o status da conexão A2A.
   *
   * @return status da conexão
   */
  @GetMapping("/status")
  public ResponseEntity<Map<String, Object>> status() {
    log.debug("Verificando status da conexão A2A");

    Map<String, Object> status = new HashMap<>();
    status.put("enabled", a2aEnabled);
    status.put("connected", connected);
    status.put("serverUrl", serverUrl);
    status.put("agentId", agentId);

    return ResponseEntity.ok(status);
  }

  /**
   * Desconecta do servidor A2A.
   *
   * @return status da desconexão
   */
  @PostMapping("/disconnect")
  public ResponseEntity<Map<String, Object>> disconnect() {
    log.info("Requisição de desconexão A2A");

    try {
      this.connected = false;
      log.info("Conexão A2A encerrada");

      return ResponseEntity.ok(Map.of(
          "success", true,
          "message", "Conexão A2A encerrada com sucesso"));
    } catch (Exception e) {
      log.error("Erro ao desconectar do servidor A2A", e);
      return ResponseEntity.internalServerError()
          .body(Map.of(
              "success", false,
              "message", "Erro ao desconectar do servidor A2A: " + e.getMessage()));
    }
  }

  /**
   * Lista agentes remotos disponíveis via A2A.
   *
   * @return lista de agentes remotos
   */
  @GetMapping("/agents")
  public ResponseEntity<Map<String, Object>> listRemoteAgents() {
    log.debug("Listando agentes remotos disponíveis via A2A");

    if (!a2aEnabled || !connected) {
      log.warn("A2A não está habilitado ou não conectado");
      return ResponseEntity.badRequest()
          .body(Map.of(
              "success", false,
              "message", "A2A não está habilitado ou não conectado"));
    }

    // Simulação - implementação real usaria spring-ai-agent-utils-a2a
    return ResponseEntity.ok(Map.of(
        "success", true,
        "message", "Agentes remotos listados com sucesso",
        "agents", java.util.Collections.emptyList()));
  }
}
