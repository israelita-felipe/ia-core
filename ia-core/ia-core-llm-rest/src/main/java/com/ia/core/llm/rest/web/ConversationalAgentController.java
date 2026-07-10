package com.ia.core.llm.rest.web;

import com.ia.core.llm.service.agente.ConversationalAgentService;
import com.ia.core.llm.service.model.agente.ContextConversacaoDTO;
import com.ia.core.llm.service.model.agente.RespostaAgenteDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * Controller REST para agente conversacional guiado por ontologia.
 * <p>
 * Expõe endpoints para gerenciar conversações com agente conversacional.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/api/${api.version}/llm/conversational-agent")
@Tag(name = "Conversational Agent", description = "Agente conversacional guiado por ontologia")
@RequiredArgsConstructor
public class ConversationalAgentController {

  private final ConversationalAgentService conversationalAgentService;

  @PostMapping("/session")
  @Operation(summary = "Criar sessão conversacional", description = "Cria uma nova sessão de conversação com ontologia de contexto")
  public ContextConversacaoDTO createSession(
      @Parameter(description = "ID do usuário")
      @RequestParam String userId,
      @Parameter(description = "Domínio da conversação (ex: biologia, biblioteca, medicina)")
      @RequestParam String dominio) {
    log.debug("Criando sessão conversacional: userId={}, dominio={}", userId, dominio);
    return conversationalAgentService.createSession(userId, dominio);
  }

  @PostMapping("/message")
  @Operation(summary = "Processar mensagem", description = "Processa uma mensagem do usuário na conversação")
  public RespostaAgenteDTO processMessage(
      @Parameter(description = "ID da sessão de conversação")
      @RequestParam String sessionId,
      @Parameter(description = "Mensagem do usuário")
      @RequestBody String mensagem) {
    log.debug("Processando mensagem: sessionId={}", sessionId);
    return conversationalAgentService.processMessage(sessionId, mensagem);
  }

  @DeleteMapping("/session/{sessionId}")
  @Operation(summary = "Encerrar sessão", description = "Encerra uma sessão de conversação")
  public void endSession(
      @Parameter(description = "ID da sessão de conversação")
      @PathVariable String sessionId) {
    log.debug("Encerrando sessão: sessionId={}", sessionId);
    conversationalAgentService.endSession(sessionId);
  }
}
