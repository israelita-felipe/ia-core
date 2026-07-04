package com.ia.core.llm.service.chat;

import com.ia.core.llm.model.prompt.FinalidadePromptEnum;
import com.ia.core.llm.service.model.chat.ChatRequestDTO;
import com.ia.core.llm.service.prompt.PromptRepository;
import com.ia.core.resilience4j.annotation.Resilient;
import com.ia.core.resilience4j.profile.ResilienceProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;

/**
 * Serviço de aplicação para operações de chat.
 * <p>
 * Orquestra chamadas ao serviço de chat com suporte a templates e
 * resiliência configurada.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Service
@RequiredArgsConstructor
public class ChatApplicationService {

  private final ChatService chatService;
  private final PromptRepository promptRepository;

  @Resilient(ResilienceProfile.LLM_SERVICE)
  public String ask(ChatRequestDTO dto) {
    return ask(dto, null);
  }

  @Resilient(ResilienceProfile.LLM_SERVICE)
  public String ask(ChatRequestDTO dto, String systemPromptOverride) {
    String text = dto.getRequest() != null ? dto.getRequest() : dto.getText();
    if (text == null || text.isBlank()) {
      return "";
    }
    Long promptId = dto.getPromptId();
    String sessionId = dto.getSessionId() != null ? dto.getSessionId() : "default";
    if (promptId != null) {
      promptRepository.findById(promptId).ifPresent(prompt -> {
        if (prompt.getTemplate() != null) {
          String system = systemPromptOverride != null ? systemPromptOverride : prompt.getTemplate().getConteudo();
          chatService.ask("", text, system, prompt.getFinalidade(), prompt.getTemplate().isExigeContexto(),
              Collections.emptyMap(), sessionId);
        }
      });
    }
    if (systemPromptOverride != null) {
      return chatService.ask("", text, systemPromptOverride, FinalidadePromptEnum.RESPOSTA_TEXTUAL, false,
          Map.of(), sessionId);
    }
    return chatService.ask("", text, sessionId);
  }
}
