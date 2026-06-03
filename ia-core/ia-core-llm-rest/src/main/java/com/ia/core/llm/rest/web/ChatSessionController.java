package com.ia.core.llm.rest.web;

import com.ia.core.llm.model.chat.ChatSession;
import com.ia.core.llm.service.chat.ChatSessionCrudService;
import com.ia.core.llm.service.model.chat.ChatSessionDTO;
import com.ia.core.rest.control.DefaultBaseController;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller REST para gerenciamento de sessões de chat.
 * <p>
 * Expõe endpoints para operações CRUD em sessões de chat com agentes LLM.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/llm/chat/sessao")
@Tag(name = "ChatSession", description = "Gerenciamento de sessões de chat com agentes LLM")
public class ChatSessionController
  extends DefaultBaseController<ChatSession,ChatSessionDTO> {

  private final ChatSessionCrudService chatSessionService;

  public ChatSessionController(ChatSessionCrudService chatSessionService) {
    super(chatSessionService);
    this.chatSessionService = chatSessionService;
  }

  /**
   * Busca uma sessão pelo session ID.
   *
   * @param sessionId identificador da sessão
   * @return ChatSessionDTO da sessão encontrada
   */
  @GetMapping("/session/{sessionId}")
  public ChatSessionDTO findBySessionId(@PathVariable String sessionId) {
    log.debug("Buscando sessão de chat por session ID: {}", sessionId);
    return chatSessionService.findBySessionId(sessionId)
        .orElseThrow(() -> new RuntimeException("Sessão não encontrada"));
  }

  /**
   * Lista todas as sessões ativas de um usuário.
   *
   * @param usuarioId ID do usuário
   * @return lista de sessões ativas
   */
  @GetMapping("/usuario/{usuarioId}/ativas")
  public List<ChatSessionDTO> listAtivasByUsuario(@PathVariable String usuarioId) {
    log.debug("Listando sessões ativas do usuário: {}", usuarioId);
    return chatSessionService.listAtivasByUsuario(usuarioId);
  }

  /**
   * Inicia uma nova sessão de chat.
   *
   * @param agenteId ID do agente
   * @param usuarioId ID do usuário
   * @param titulo título da sessão
   * @return ChatSessionDTO da sessão criada
   */
  @PostMapping("/iniciar")
  public ChatSessionDTO iniciarSessao(@RequestParam Long agenteId,
                                     @RequestParam String usuarioId,
                                     @RequestParam(required = false) String titulo) {
    log.debug("Iniciando sessão de chat: agenteId={}, usuarioId={}", agenteId, usuarioId);
    return chatSessionService.iniciarSessao(agenteId, usuarioId, titulo);
  }

  /**
   * Encerra uma sessão de chat.
   *
   * @param sessionId identificador da sessão
   * @return ChatSessionDTO da sessão encerrada
   */
  @PostMapping("/{sessionId}/encerrar")
  public ChatSessionDTO encerrarSessao(@PathVariable String sessionId) {
    log.debug("Encerrando sessão de chat: sessionId={}", sessionId);
    return chatSessionService.encerrarSessao(sessionId);
  }
}
