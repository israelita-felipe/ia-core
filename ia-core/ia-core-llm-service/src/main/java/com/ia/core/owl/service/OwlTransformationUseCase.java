package com.ia.core.owl.service;

import org.springframework.ai.chat.model.ChatModel;

import com.ia.core.owl.service.model.TransformacaoResultDTO;

/**
 * Interface de Use Case para transformação de descrições em linguagem natural
 * em axiomas OWL 2 DL usando modelos de linguagem.
 *
 * @author Israel Araújo
 */
public interface OwlTransformationUseCase {

  /**
   * Transforma uma descrição em linguagem natural em axiomas OWL usando um
   * template customizado.
   *
   * @param template         o template de prompt a ser usado (não pode ser nulo
   *                         ou vazio)
   * @param descricaoNatural a descrição em linguagem natural a ser transformada
   *                         (não pode ser nula ou vazia)
   * @return o resultado da transformação contendo os axiomas gerados e
   *         informações de status
   * @throws IllegalArgumentException se algum parâmetro for nulo ou vazio
   */
  TransformacaoResultDTO transformarParaOWL(String template,
                                            String descricaoNatural);

  /**
   * Transforma uma descrição em linguagem natural em axiomas OWL usando o
   * template padrão.
   *
   * @param descricaoNatural a descrição em linguagem natural a ser transformada
   *                         (não pode ser nula ou vazia)
   * @return o resultado da transformação contendo os axiomas gerados e
   *         informações de status
   * @throws IllegalArgumentException se algum parâmetro for nulo ou vazio
   */
  TransformacaoResultDTO transformarParaOWL(String descricaoNatural);

  /**
   * @return o template padrão usado para prompts de geração de OWL.
   */
  String getPromptTemplate();

  /**
   * Retorna o ChatClient utilizado pelo serviço.
   *
   * @return o ChatClient instance
   */
  ChatModel getChatModel();
}
