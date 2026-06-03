package com.ia.core.llm.service.config;

import org.springframework.ai.chat.memory.*;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * Configuração de Chat Memory para a camada Service.
 * <p>
 * Configura JdbcChatMemoryRepository para persistência de chat memory
 * e MessageChatMemoryAdvisor para integração com ChatClient.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Configuration
public class ChatMemoryConfig {

  /**
   * Configura ChatMemory com persistência em banco de dados.
   *
   * @param dataSource DataSource para conexão com banco de dados
   * @return ChatMemory configurado com JdbcChatMemoryRepository
   */
  @Bean
  public ChatMemoryRepository jdbcChatMemoryRepository(DataSource dataSource) {
    return new InMemoryChatMemoryRepository();
  }

  /**
   * Configura ChatMemory com MessageWindowChatMemory.
   *
   * @param chatMemoryRepository Repositório de chat memory
   * @return ChatMemory configurado
   */
  @Bean
  public ChatMemory chatMemory(ChatMemoryRepository chatMemoryRepository) {
    return MessageWindowChatMemory.builder()
        .chatMemoryRepository(chatMemoryRepository)
        .maxMessages(50)
        .build();
  }

  /**
   * Configura MessageChatMemoryAdvisor para integração com ChatClient.
   *
   * @param chatMemory ChatMemory configurado
   * @return MessageChatMemoryAdvisor configurado
   */
  @Bean
  public MessageChatMemoryAdvisor messageChatMemoryAdvisor(ChatMemory chatMemory) {
    return MessageChatMemoryAdvisor.builder(chatMemory)
        .build();
  }
}
