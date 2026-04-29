package com.ia.core.llm.view.chat;

import com.ia.core.llm.service.model.chat.ChatRequestDTO;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Interface do cliente Feign para comunicação com o serviço de chat.
 * <p>
 * Responsável por definir os endpoints de comunicação com o serviço de chat
 * para envio de perguntas e recebimento de respostas.
 *
 * @author Israel Araújo
 * @since 1.0
 */
public interface ChatClient {

  /** Nome do cliente */
  String NOME = "chat";

  /** URL do cliente */
  String URL = "${feign.host}/api/${api.version}/${feign.url.chat}";

  /**
   * Realiza uma pergunta ao modelo de linguagem.
   *
   * @param dto {@link ChatRequestDTO} contendo os dados da pergunta
   * @return String contendo a resposta do modelo
   */
  @PostMapping
  String ask(@RequestBody @Valid ChatRequestDTO dto);
}
