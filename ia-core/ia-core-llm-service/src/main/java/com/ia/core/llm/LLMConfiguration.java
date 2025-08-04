package com.ia.core.llm;

import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.context.annotation.Bean;

/**
 * @author Israel Araújo
 */
public class LLMConfiguration {

  @Bean
  static VectorStore vectorStore(EmbeddingModel embeddingModel) {
    return SimpleVectorStore.builder(embeddingModel).build();
  }

  private final VectorStore vectorStore;

  /**
   * @param vectorStore
   */
  public LLMConfiguration(VectorStore vectorStore) {
    super();
    this.vectorStore = vectorStore;
    configure(vectorStore);
  }

  /**
   * @param vectorStore2
   */
  public void configure(VectorStore vectorStore2) {

  }
}
