package com.ia.core.llm;

import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.ollama.api.OllamaApi;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

/**
 * @author Israel Araújo
 */
public class LLMConfiguration {

  @Bean
  static RestClient.Builder restClientBuilder() {
    return RestClient.builder().requestFactory((uri, method) -> {
      HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
      factory.setConnectTimeout(3600000);
      factory.setReadTimeout(3600000);
      return factory.createRequest(uri, method);
    });
  }

  @Bean
  static OllamaApi ollamaApi(RestClient.Builder builder) {
    return OllamaApi.builder().restClientBuilder(builder).build();
  }

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
