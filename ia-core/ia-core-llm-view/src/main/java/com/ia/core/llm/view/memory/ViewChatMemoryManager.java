package com.ia.core.llm.view.memory;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;
import java.util.UUID;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.List;

/**
 * Gerenciador de chat memory específico para a camada View.
 * <p>
 * Gerencia sessões de conversa na interface Vaadin usando memória local (in-memory),
 * permitindo que o usuário mantenha contexto entre interações na view sem persistência em banco.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Component
@RequiredArgsConstructor
public class ViewChatMemoryManager {

  private final ChatClient.Builder chatClientBuilder;
  private final Map<String, ChatMemory> sessionMemories = new ConcurrentHashMap<>();

  /**
   * Cria uma nova sessão de conversa para a view com chat memory in-memory.
   *
   * @return Conversation ID único
   */
  public String criarSessaoView() {
    String conversationId = UUID.randomUUID().toString();
    ChatMemory chatMemory = MessageWindowChatMemory.builder()
        .maxMessages(50)
        .build();
    sessionMemories.put(conversationId, chatMemory);
    return conversationId;
  }

  /**
   * Envia mensagem para o agente com contexto de memória da view (in-memory).
   *
   * @param mensagem Mensagem do usuário
   * @param conversationId ID da conversa
   * @return Resposta do agente
   */
  public String chatComMemoriaView(String mensagem, String conversationId) {
    ChatMemory chatMemory = sessionMemories.get(conversationId);
    if (chatMemory == null) {
      throw new IllegalArgumentException("Sessão não encontrada: " + conversationId);
    }

    ChatClient chatClient = chatClientBuilder
        .defaultAdvisors(
            MessageChatMemoryAdvisor.builder(chatMemory).build()
        )
        .build();

    return chatClient.prompt()
        .user(mensagem)
        .advisors(a -> a.param(ChatMemory.CONVERSATION_ID, conversationId))
        .call()
        .content();
  }

  /**
   * Limpa o histórico de uma conversa específica da view.
   *
   * @param conversationId ID da conversa
   */
  public void limparConversaView(String conversationId) {
    ChatMemory chatMemory = sessionMemories.get(conversationId);
    if (chatMemory != null) {
      chatMemory.clear(conversationId);
    }
  }

  /**
   * Remove completamente uma sessão de conversa da memória.
   *
   * @param conversationId ID da conversa
   */
  public void removerSessaoView(String conversationId) {
    sessionMemories.remove(conversationId);
  }

  /**
   * Obtém o histórico de uma conversa da view.
   *
   * @param conversationId ID da conversa
   * @return Lista de mensagens
   */
  public List<org.springframework.ai.chat.messages.Message> getHistoricoView(String conversationId) {
    ChatMemory chatMemory = sessionMemories.get(conversationId);
    if (chatMemory == null) {
      return List.of();
    }
    return chatMemory.get(conversationId);
  }
}
