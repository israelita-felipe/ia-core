package com.ia.core.nlp.model.services;

import java.io.FileInputStream;
import java.io.InputStream;

import org.springframework.core.io.Resource;

import lombok.RequiredArgsConstructor;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;

/**
 * @author Israel Araújo
 */
@RequiredArgsConstructor
public class TokenizerService
  implements CoreTokenizerService {

  public static final String MODEL_RESOURCE = "classpath:com/ia/core/nlp/apache/models/pt-token.bin";

  private final Resource modelResource;

  private Tokenizer model = null;

  private Tokenizer getTokenizer() {
    try {
      if (this.model == null) {
        try (InputStream modelIn = new FileInputStream(modelResource
            .getFile())) {
          this.model = new TokenizerME(new TokenizerModel(modelIn));
        }
      }
      return this.model;
    } catch (Exception e) {
      throw new RuntimeException("Não foi possível carregar o modelo");
    }
  }

  @Override
  public String[] tokenize(String text) {
    return getTokenizer().tokenize(text);
  }
}
