package com.ia.core.nlp.model.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import org.springframework.core.io.Resource;

import java.io.InputStream;

/**
 * Serviço de tokenização de texto usando Apache OpenNLP.
 *
 * <p>Esta classe implementa {@link CoreTokenizerService} usando o modelo
 * de tokenização do OpenNLP para dividir texto em tokens (palavras).
 *
 * <p><b>Características:</b></p>
 * <ul>
 *   <li>Carregamento lazy do modelo de tokenização</li>
 *   <li>Cache do modelo para evitar reload</li>
 *   <li>Suporte a modelos em português (pt-token.bin)</li>
 * </ul>
 *
 * <p><b>Por quê usar TokenizerService?</b></p>
 * <ul>
 *   <li>Tokenização baseada em ML com OpenNLP</li>
 *   <li>Modelo pré-treinado para português</li>
 *   <li>Integração com Spring via {@link Resource}</li>
 * </ul>
 *
 * @author Israel Araújo
 * @see CoreTokenizerService
 * @see TokenizerME
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
public class TokenizerService
  implements CoreTokenizerService {

  /** Caminho do modelo de tokenização no classpath. */
  public static final String MODEL_RESOURCE = "classpath:com/ia/core/nlp/apache/models/pt-token.bin";

  /** Recurso do modelo de tokenização. */
  private final Resource modelResource;

  /** Modelo de tokenização carregado (lazy). */
  private Tokenizer model = null;

  /**
   * Obtém o modelo de tokenização, carregando se necessário.
   *
   * <p>O modelo é carregado lazy na primeira chamada e cacheado
   * para chamadas subsequentes.
   *
   * @return modelo de tokenização OpenNLP
   * @throws RuntimeException se não for possível carregar o modelo
   */
  private Tokenizer getTokenizer() {
    try {
      if (this.model == null) {
        log.debug("Carregando modelo de tokenização: {}", modelResource);
        try (InputStream modelIn = modelResource.getInputStream()) {
          this.model = new TokenizerME(new TokenizerModel(modelIn));
        }
        log.info("Modelo de tokenização carregado com sucesso");
      }
      return this.model;
    } catch (Exception e) {
      log.error("Não foi possível carregar o modelo de tokenização: {}", modelResource, e);
      throw new RuntimeException("Não foi possível carregar o modelo", e);
    }
  }

  @Override
  public String[] tokenize(String text) {
    log.debug("Tokenizando texto: {} caracteres", text != null ? text.length() : 0);
    String[] tokens = getTokenizer().tokenize(text);
    log.debug("Texto tokenizado em {} tokens", tokens.length);
    return tokens;
  }
}
