package com.ia.core.llm.service.chat;

import com.ia.core.llm.service.template.PromptTemplateServiceImpl;
import com.ia.core.llm.service.vector.VectorStoreOperations;
import com.ia.core.llm.service.vector.VectorStoreOperationsImpl;
import com.ia.core.llm.service.vector.VectorStoreService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuração de beans para serviços de chat.
 * <p>
 * Configura VectorStoreOperations, PromptTemplateService e ChatSessionService.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Configuration
public class ChatServiceConfig {

  @Bean
  VectorStoreOperations vectorStoreOperations(VectorStoreService vectorStoreService) {
    return new VectorStoreOperationsImpl(vectorStoreService);
  }

  @Bean
  PromptTemplateServiceImpl promptTemplateService() {
    return new PromptTemplateServiceImpl();
  }

  @Bean
  ChatSessionService chatSessionService() {
    return new ChatSessionServiceImpl();
  }
}
