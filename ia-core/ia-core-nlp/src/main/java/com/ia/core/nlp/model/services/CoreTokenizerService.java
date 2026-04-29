package com.ia.core.nlp.model.services;

/**
 * Interface para serviço de tokenização de texto.
 *
 * <p>Define o contrato para serviços que realizam tokenização de texto
 * usando modelos de processamento de linguagem natural.
 *
 * <p><b>Por quê usar CoreTokenizerService?</b></p>
 * <ul>
 *   <li>Abstrai a implementação de tokenização</li>
 *   <li>Permite troca de modelos de tokenização</li>
 *   <li>Integra com Apache OpenNLP para processamento NLP</li>
 * </ul>
 *
 * @author Israel Araújo
 * @see TokenizerService
 * @since 1.0.0
 */
public interface CoreTokenizerService {

  /**
   * Tokeniza o texto informado em palavras individuais.
   *
   * @param text texto a ser tokenizado (não pode ser null)
   * @return array de tokens extraídos do texto
   * @throws IllegalArgumentException se o texto for null
   */
  String[] tokenize(String text);

}
