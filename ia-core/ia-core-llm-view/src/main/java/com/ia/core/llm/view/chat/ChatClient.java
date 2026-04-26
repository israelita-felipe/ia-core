package com.ia.core.llm.view.chat;

import com.ia.core.llm.service.model.chat.ChatRequestDTO;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author Israel Araújo
 */
public interface ChatClient {

  /**
   * Nome do cliente.
   */
  public static final String NOME = "chat";
  /**
   * URL do cliente.
   */
  public static final String URL = "${feign.host}/api/${api.version}/${feign.url.chat}";

  /**
   * Chat
   *
   * @param dto {@link ChatRequestDTO}
   * @return String contendo a resposta
   */
  @PostMapping
  String ask(@RequestBody @Valid ChatRequestDTO dto);
}
