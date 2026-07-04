package com.ia.core.llm.service.chat;

import com.ia.core.llm.model.agente.Agente;
import com.ia.core.llm.model.chat.ChatSession;
import com.ia.core.llm.model.chat.ChatSession.ChatSessionStatus;
import com.ia.core.llm.service.agente.AgenteRepository;
import com.ia.core.llm.service.model.chat.ChatSessionDTO;
import com.ia.core.llm.service.model.chat.ChatSessionUseCase;
import com.ia.core.service.CrudBaseService;
import com.ia.core.service.annotations.TransactionalReadOnly;
import com.ia.core.service.annotations.TransactionalWrite;
import com.ia.core.service.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Serviço CRUD para gerenciamento de sessões de chat.
 * <p>
 * Implementa operações CRUD para sessões de chat utilizadas em conversações
 * com agentes LLM.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@Service
public class ChatSessionCrudService
  extends CrudBaseService<ChatSession, ChatSessionDTO>
  implements ChatSessionUseCase {

  private final AgenteRepository agenteRepository;

  public ChatSessionCrudService(ChatSessionServiceConfig config) {
    super(config);
    this.agenteRepository = config.getAgenteRepository();
  }

  @Override
  public ChatSessionRepository getRepository() {
    return (ChatSessionRepository) super.getRepository();
  }

  @Override
  @TransactionalReadOnly
  public Optional<ChatSessionDTO> findBySessionId(String sessionId) {
    log.debug("Buscando sessão de chat por session ID: {}", sessionId);
    return getRepository().findBySessionId(sessionId)
        .map(session -> {
          log.debug("Sessão encontrada: {}", session.getSessionId());
          return getMapper().toDTO(session);
        });
  }

  @Override
  @TransactionalReadOnly
  public List<ChatSessionDTO> listAtivasByUsuario(String usuarioId) {
    log.debug("Listando sessões ativas do usuário: {}", usuarioId);
    return getRepository().findByUsuarioIdAndStatus(usuarioId, ChatSessionStatus.ATIVA).stream()
        .map(session -> {
          log.debug("Sessão ativa encontrada: {}", session.getSessionId());
          return getMapper().toDTO(session);
        })
        .toList();
  }

  @Override
  @TransactionalWrite
  public ChatSessionDTO iniciarSessao(Long agenteId, String usuarioId, String titulo) {
    log.debug("Iniciando sessão de chat: agenteId={}, usuarioId={}, titulo={}", agenteId, usuarioId, titulo);

    Agente agente = agenteRepository.findById(agenteId)
        .orElseThrow(() -> new ServiceException("Agente não encontrado"));

    ChatSession session = ChatSession.builder()
        .sessionId(UUID.randomUUID().toString())
        .titulo(titulo)
        .dataInicio(LocalDateTime.now())
        .status(ChatSessionStatus.ATIVA)
        .agente(agente)
        .usuarioId(usuarioId)
        .build();

    ChatSession saved = getRepository().save(session);
    log.debug("Sessão iniciada com sucesso: sessionId={}", saved.getSessionId());

    return getMapper().toDTO(saved);
  }

  @Override
  @TransactionalWrite
  public ChatSessionDTO encerrarSessao(String sessionId) {
    log.debug("Encerrando sessão de chat: sessionId={}", sessionId);

    ChatSession session = getRepository().findBySessionId(sessionId)
        .orElseThrow(() -> new ServiceException("Sessão não encontrada"));

    session.setDataFim(LocalDateTime.now());
    session.setStatus(ChatSessionStatus.ENCERRADA);

    ChatSession saved = getRepository().save(session);
    log.debug("Sessão encerrada com sucesso: sessionId={}", saved.getSessionId());

    return getMapper().toDTO(saved);
  }
}
