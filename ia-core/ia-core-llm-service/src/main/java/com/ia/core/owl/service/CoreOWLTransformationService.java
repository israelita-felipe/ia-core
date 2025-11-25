package com.ia.core.owl.service;

import org.springframework.ai.chat.model.ChatModel;

import com.ia.core.owl.service.model.TransformacaoResultDTO;

/**
 *
 */
public interface CoreOWLTransformationService {

  /**
   * Template padrão para prompt de geração de OWL 2 DL.
   * <p>
   * O template inclui instruções específicas para garantir a geração de axiomas
   * OWL 2 DL válidos usando Manchester Syntax.
   * </p>
   */
  String OWL_PROMPT_TEMPLATE = """
      Você é um especialista em Ontologias OWL e Manchester Syntax. Converta a seguinte descrição em OWL-DL usando Manchester Syntax.

      REGRAS ESTRITAS:
      - Use APENAS Manchester Syntax válida
      - Defina classes, propriedades e restrições conforme a descrição
      - A ontologia deve ter a URI: {uri}
      - Não inclua explicações, comentários ou texto adicional
      - Retorne SOMENTE o código OWL-DL em Manchester Syntax

      MANCHESTER SYNTAX - EXEMPLOS VÁLIDOS:
      - Classe: Class: Person
      - Subclasse: Class: Student SubClassOf: Person
      - Propriedade: ObjectProperty: hasParent
      - Domínio/Range: ObjectProperty: hasTeacher Domain: Student Range: Teacher
      - Restrição: Class: Child SubClassOf: hasParent only Adult
      - Equivalência: Class: Human EquivalentTo: Person
      - Indivíduo: Individual: john Types: Person

      DESCRIÇÃO PARA CONVERSÃO:
      {descricao}
      """;

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
