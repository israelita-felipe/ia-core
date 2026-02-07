package com.ia.core.llm.service.chat;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Implementação de ChatSessionService.
 * Gerencia sessões de chat usando ConcurrentHashMap para thread-safety.
 *
 * @author Israel Araújo
 */
public class ChatSessionServiceImpl implements ChatSessionService {

  private final ConcurrentHashMap<String, Boolean> activeSessions;

  public ChatSessionServiceImpl() {
    this.activeSessions = new ConcurrentHashMap<>();
  }

  @Override
  public String createSession() {
    String sessionId = UUID.randomUUID().toString();
    activeSessions.put(sessionId, true);
    return sessionId;
  }

  @Override
  public void endSession(String sessionId) {
    activeSessions.remove(sessionId);
  }

  @Override
  public boolean isSessionActive(String sessionId) {
    return activeSessions.getOrDefault(sessionId, false);
  }

  @Override
  public void clearSessionHistory(String sessionId) {
    if (activeSessions.containsKey(sessionId)) {
      activeSessions.remove(sessionId);
    }
  }
}
