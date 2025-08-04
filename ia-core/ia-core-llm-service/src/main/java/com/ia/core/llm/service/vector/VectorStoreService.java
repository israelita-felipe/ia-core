package com.ia.core.llm.service.vector;

import java.io.ByteArrayInputStream;

import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.reader.TextReader;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

/**
 * @author Israel Araújo
 */
@RequiredArgsConstructor
@Service
public class VectorStoreService {
  /**
   *
   */
  private static final double DEFAULT_THREASHOLD = .85;

  private final VectorStore vectorStore;

  /**
   * @return
   */
  public QuestionAnswerAdvisor getQuestionAnswerAdvisor() {
    return new QuestionAnswerAdvisor(vectorStore);
  }

  /**
   * @return
   */
  protected double getSimilarityThreshold() {
    return DEFAULT_THREASHOLD;
  }

  public void learn(String text) {
    TextReader reader = new TextReader(new InputStreamResource(new ByteArrayInputStream(text
        .getBytes())));
    TextSplitter splitter = new TokenTextSplitter();
    this.vectorStore.accept(splitter.apply(reader.read()));
  }
}
