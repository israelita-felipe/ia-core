package com.ia.core.llm.view.chat;

import com.ia.core.llm.service.model.chat.ChatRequestDTO;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Israel Araújo
 */
@RequiredArgsConstructor
@Slf4j
public class ChatManager {

  @Getter
  private final ChatClient client;

  public String ask(ChatRequestDTO request) {
    return client.ask(request);
  }

  @PostConstruct
  public void initBaseService() {
    log.info("{} inicializado com client {}", this.getClass(), client);
  }
}
