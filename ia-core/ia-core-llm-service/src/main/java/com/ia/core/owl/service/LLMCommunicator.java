package com.ia.core.owl.service;

import com.ia.core.llm.model.ferramenta.Ferramenta;
import com.ia.core.llm.service.vector.VectorStoreOperations;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.content.Media;
import org.springframework.ai.ollama.api.OllamaChatOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
/**
 * Serviço de comunicação com modelos de linguagem (LLM).
 * <p>
 * Responsável por enviar prompts para o modelo de linguagem e processar as
 * respostas. Suporta diferentes estratégias de comunicação e validação de respostas.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */

@Slf4j
@Component
@RequiredArgsConstructor
public class LLMCommunicator {

  private final ChatModel chatModel;
  private final ChatMemory chatMemory;
  private final VectorStoreOperations vectorStoreOperations;

  @Value("${spring.ai.ollama.base-url}")
  private String ollamaBaseUrl;
  @Value("${spring.ai.ollama.chat.options.model}")
  private String modelName;

  public ChatClient.CallResponseSpec sendPrompt(String prompt, String chatSessionId, Object...tools) {
    log.info("Enviando prompt para LLM");
    return createClient(chatSessionId, null, tools).prompt(new Prompt(new UserMessage(prompt), OllamaChatOptions
        .builder().model(modelName).temperature(0.1).build())).call();
  }

  public ChatClient.CallResponseSpec sendPrompt(Prompt prompt, String chatSessionId, Object...tools) {
    log.info("Enviando prompt para LLM");
    return createClient(chatSessionId, null, tools).prompt(prompt).call();
  }

  public ChatClient.CallResponseSpec sendPrompt(String prompt, String chatSessionId, List<Ferramenta> skills, List<Object> tools) {
    log.info("Enviando prompt para LLM com skills e tools");
    return createClient(chatSessionId, skills, tools.toArray(new Object[0])).prompt(new Prompt(new UserMessage(prompt), OllamaChatOptions
        .builder().model(modelName).temperature(0.1).build())).call();
  }

  public ChatClient.CallResponseSpec sendPrompt(Prompt prompt, String chatSessionId, List<Ferramenta> skills, List<Object> tools) {
    log.info("Enviando prompt para LLM com skills e tools");
    return createClient(chatSessionId, skills, tools.toArray(new Object[0])).prompt(prompt).call();
  }

  public ChatClient.CallResponseSpec sendPrompt(String prompt, List<Media> media, String chatSessionId, Object...tools) {
    log.info("Enviando prompt com media para LLM");
    return createClient(chatSessionId, null, tools).prompt(new Prompt(UserMessage.builder().text(prompt)
        .media(media).build(),
        OllamaChatOptions.builder().model(modelName).temperature(0.1).build())).call();
  }

  public ChatClient.CallResponseSpec sendPrompt(Prompt prompt, List<Media> media, String chatSessionId, Object...tools) {
    log.info("Enviando prompt com media para LLM");
    return createClient(chatSessionId, null, tools).prompt(prompt).call();
  }

  /**
   * Cria um novo cliente de chat com todas as configurações necessárias.
   *
   * @param chatSessionId identificador da sessão de chat
   * @param skills lista de skills (Ferramenta com tipo SKILL)
   * @param tools array de tools a serem registradas
   * @return cliente de chat configurado
   */
  private ChatClient createClient(String chatSessionId, List<Ferramenta> skills, Object...tools) {

    ChatClient.Builder builder = ChatClient.builder(chatModel);

    if (tools != null && tools.length > 0) {
      builder.defaultTools(tools);
    }

    if (skills != null && !skills.isEmpty()) {
      builder.defaultTools(skills.toArray(new Object[0]));
    }

    builder.defaultAdvisors(
        MessageChatMemoryAdvisor.builder(chatMemory).conversationId(chatSessionId).build(),
        vectorStoreOperations.getQuestionAnswerAdvisor()
    );

    return builder.build();
  }
}
