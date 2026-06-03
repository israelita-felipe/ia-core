package com.ia.core.llm.rest.web;

import com.ia.core.llm.service.agente.AgenteConversacionalOntologiaService;
import com.ia.core.llm.service.model.agente.ContextoConversacao;
import com.ia.core.llm.service.model.agente.RespostaAgente;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Controller REST para agente conversacional guiado por ontologia.
 * <p>
 * Expõe endpoints para conversação com validação ontológica.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/llm/agentes/conversacional")
@Tag(name = "Agente Conversacional", description = "Agente conversacional guiado por ontologia")
public class AgenteConversacionalController {

  private final AgenteConversacionalOntologiaService agenteService;
  private final Map<String, ContextoConversacao> sessoesAtivas = new ConcurrentHashMap<>();

  public AgenteConversacionalController(AgenteConversacionalOntologiaService agenteService) {
    this.agenteService = agenteService;
  }

  @Operation(summary = "Cria nova sessão de conversação")
  @PostMapping("/sessoes")
  public ResponseEntity<ContextoConversacao> criarSessao(
      @RequestBody CriarSessaoRequest request) {
    log.info("Criando sessão: userId={}, dominio={}", request.getUserId(), request.getDominio());

    ContextoConversacao contexto = agenteService.criarSessao(
        request.getUserId(), request.getDominio());

    sessoesAtivas.put(contexto.getSessionId(), contexto);

    return ResponseEntity.ok(contexto);
  }

  @Operation(summary = "Envia mensagem para agente")
  @PostMapping("/sessoes/{sessionId}/mensagens")
  public ResponseEntity<RespostaAgente> enviarMensagem(
      @PathVariable String sessionId,
      @Valid @RequestBody MensagemRequest request) {
    log.info("Enviando mensagem: sessionId={}", sessionId);

    ContextoConversacao contexto = sessoesAtivas.get(sessionId);
    if (contexto == null) {
      return ResponseEntity.notFound().build();
    }

    RespostaAgente resposta = agenteService.processarMensagem(contexto, request.getMensagem());

    return ResponseEntity.ok(resposta);
  }

  @Operation(summary = "Obtém ontologia atual da conversa")
  @GetMapping("/sessoes/{sessionId}/ontologia")
  public ResponseEntity<ContextoConversacao> obterOntologia(@PathVariable String sessionId) {
    ContextoConversacao contexto = sessoesAtivas.get(sessionId);
    if (contexto == null) {
      return ResponseEntity.notFound().build();
    }

    return ResponseEntity.ok(contexto);
  }

  @Operation(summary = "Encerra sessão")
  @DeleteMapping("/sessoes/{sessionId}")
  public ResponseEntity<Void> encerrarSessao(@PathVariable String sessionId) {
    log.info("Encerrando sessão: sessionId={}", sessionId);

    ContextoConversacao contexto = sessoesAtivas.remove(sessionId);
    if (contexto == null) {
      return ResponseEntity.notFound().build();
    }

    agenteService.encerrarSessao(sessionId);

    return ResponseEntity.noContent().build();
  }

  @Operation(summary = "Obtém histórico da conversa")
  @GetMapping("/sessoes/{sessionId}/historico")
  public ResponseEntity<ContextoConversacao> obterHistorico(@PathVariable String sessionId) {
    ContextoConversacao contexto = sessoesAtivas.get(sessionId);
    if (contexto == null) {
      return ResponseEntity.notFound().build();
    }

    return ResponseEntity.ok(contexto);
  }

  /**
   * DTO para requisição de criação de sessão.
   */
  @lombok.Data
  @lombok.NoArgsConstructor
  @lombok.AllArgsConstructor
  public static class CriarSessaoRequest {
    private String userId;
    private String dominio;
  }

  /**
   * DTO para requisição de mensagem.
   */
  @lombok.Data
  @lombok.NoArgsConstructor
  @lombok.AllArgsConstructor
  public static class MensagemRequest {
    private String mensagem;
  }
}
