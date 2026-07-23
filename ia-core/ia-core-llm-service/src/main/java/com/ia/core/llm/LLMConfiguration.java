package com.ia.core.llm;

import com.ia.core.llm.service.chat.ChatSessionService;
import com.ia.core.llm.service.chat.ChatSessionServiceImpl;
import com.ia.core.llm.service.config.LlmConfigurationProvider;
import com.ia.core.llm.service.config.LlmModuleProperties;
import com.ia.core.llm.service.config.SpringAiProperties;
import com.ia.core.llm.service.template.PromptTemplateServiceImpl;
import com.ia.core.llm.service.template.TemplateRepository;
import com.ia.core.llm.service.vector.VectorStoreOperations;
import com.ia.core.llm.service.vector.VectorStoreOperationsImpl;
import com.ia.core.llm.service.vector.VectorStoreService;
import com.ia.core.owl.service.DefaultOwlService;
import com.ia.core.owl.service.LLMCommunicator;
import com.ia.core.owl.service.validation.ExplicadorInconsistencia;
import com.ia.core.owl.service.validation.LoopLLMRaciocinador;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.ChatMemoryRepository;
import org.springframework.ai.chat.memory.InMemoryChatMemoryRepository;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

/**
 * Classe de configuração central para módulo LLM.
 * <p>
 * Centraliza todos os beans de configuração do módulo Large Language Model,
 * eliminando a dependência circular causada por {@code BibliaLLMConfiguration}
 * estender esta classe em módulos downstream.
 * <p>
 * Beans que precisam de propriedades de configuração capturam de
 * {@link LlmConfigurationProvider} ao invés de {@code @Value}.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
public class LLMConfiguration {
    @Getter
    private final LlmConfigurationProvider configurationProvider;

    @Bean
    VectorStore vectorStore(EmbeddingModel embeddingModel) {
        return SimpleVectorStore.builder(embeddingModel).build();
    }

    /**
     * Configura ChatMemory com persistência em banco de dados.
     *
     * @param dataSource DataSource para conexão com banco de dados
     * @return ChatMemory configurado com JdbcChatMemoryRepository
     */
    @Bean
    ChatMemoryRepository jdbcChatMemoryRepository(DataSource dataSource) {
        return new InMemoryChatMemoryRepository();
    }

    /**
     * Configura ChatMemory com MessageWindowChatMemory.
     *
     * @param chatMemoryRepository Repositório de chat memory
     * @return ChatMemory configurado
     */
    @Bean
    ChatMemory chatMemory(ChatMemoryRepository chatMemoryRepository) {
        return MessageWindowChatMemory.builder()
            .chatMemoryRepository(chatMemoryRepository)
            .maxMessages(50)
            .build();
    }

    @Bean
    VectorStoreOperations vectorStoreOperations(VectorStoreService vectorStoreService) {
        return new VectorStoreOperationsImpl(vectorStoreService);
    }

    @Bean
    PromptTemplateServiceImpl promptTemplateService(TemplateRepository templateRepository) {
        return new PromptTemplateServiceImpl(templateRepository);
    }

    @Bean
    ChatSessionService chatSessionService() {
        return new ChatSessionServiceImpl();
    }

    @Bean
    public ExplicadorInconsistencia explicadorInconsistencia() {
        log.info("Configurando ExplicadorInconsistencia");
        return new ExplicadorInconsistencia();
    }

    @Bean
    public LoopLLMRaciocinador loopLLMRaciocinador(
        LLMCommunicator llmCommunicator,
        DefaultOwlService owlService,
        ExplicadorInconsistencia explicador) {
        log.info("Configurando LoopLLMRaciocinador");
        return new LoopLLMRaciocinador(llmCommunicator, owlService, explicador);
    }

    @Bean
    public LLMCommunicator llmCommunicator(ChatModel chatModel,
                                           ChatMemory chatMemory,
                                           VectorStoreOperations vectorStoreOperations) {
        SpringAiProperties springAiProperties = configurationProvider.getSpringAiProperties();
        String ollamaBaseUrl = springAiProperties.getOllama().getBaseUrl();
        String modelName = springAiProperties.getOllama().getChat().getModel();

        return new LLMCommunicator(chatModel, chatMemory, vectorStoreOperations, ollamaBaseUrl, modelName);
    }

}
