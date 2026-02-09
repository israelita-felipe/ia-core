package com.ia.core.owl.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.springframework.ai.chat.model.ChatModel;

import com.ia.core.owl.service.model.AnaliseInferenciaDTO;
import com.ia.core.owl.service.model.TransformacaoResultDTO;
import com.ia.core.owl.service.model.axioma.AxiomaDTO;

/**
 * Serviço para transformação de descrições em linguagem natural em axiomas OWL
 * 2 DL usando modelos de linguagem através do Spring AI.
 * <p>
 * Esta classe utiliza um ChatModel para converter texto em linguagem natural em
 * axiomas OWL válidos usando Manchester Syntax, seguindo as regras do OWL 2 DL.
 * </p>
 * <p>
 * <b>Exemplo de uso:</b>
 *
 * <pre>{@code
 * ChatModel chatModel = ...;
 * OwlTransformationService service = new OwlTransformationService(chatModel, vectorStore, owlService);
 * TransformacaoResult result = service.transformarParaOWL(
 *     "Pessoas são seres humanos. João é uma pessoa.",
 *     "ex", "http://example.com/ontology#");
 * }</pre>
 * </p>
 *
 * @author Israel Araújo
 * @version 1.0
 */
public class OwlTransformationService
  implements CoreOWLTransformationService {

  /**
   * Parametro de descrição no template
   */
  private static final String PARAM_DESCRICAO = "\\{descricao\\}";
  /**
   * Parâmetro de uri da ontologia
   */
  private static final String PARAM_URI = "\\{uri\\}";
  /**
   * Modelo de chat para interagir com o modelo de linguagem.
   */
  private final ChatModel chatModel;

  /**
   * Vector store para contexto adicional (se necessário).
   */
  private LLMCommunicator communicationService;
  /** Serviço OWL */
  private CoreOWLService owlService;

  /**
   * Constrói um novo serviço de transformação OWL.
   *
   * @param chatModel  o modelo de chat para criar o ChatClient (não pode ser
   *                   nulo)
   * @param owlService o serviço OWL para validação dos axiomas (não pode ser
   *                   nulo)
   * @throws IllegalArgumentException se o chatModel for nulo
   */
  public OwlTransformationService(ChatModel chatModel,
                                  CoreOWLService owlService,
                                  LLMCommunicator llmCommunicator) {
    Objects.requireNonNull(chatModel, "ChatModel cannot be null");
    this.chatModel = chatModel;
    this.owlService = owlService;
    this.communicationService = llmCommunicator;
  }

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
  @Override
  public TransformacaoResultDTO transformarParaOWL(String template,
                                                   String descricaoNatural) {
    validateTransformParameters(template, descricaoNatural);

    try {
      String processedPrompt = processPromptTemplate(template,
                                                     descricaoNatural);

      String resposta = callChatModel(processedPrompt);
      resposta = cleanOwlResponse(resposta);
      List<AxiomaDTO> axiomas = processarRespostaLLM(resposta);
      this.owlService.addAxioms(() -> axiomas);
      return createSuccessResult(axiomas);
    } catch (Exception e) {
      return createErrorResult("Erro na transformação: " + e.getMessage(),
                               e);
    }
  }

  /**
   * Limpa a resposta do modelo de linguagem, removendo conteúdo indesejado.
   *
   * @param response a resposta bruta do modelo de linguagem
   * @return a resposta limpa, ou string vazia se a resposta for nula
   */
  public String cleanOwlResponse(String response) {
    if (response == null) {
      return "";
    }
    response = response.replaceAll("(?i)<thinking>.*?</thinking>", "")
        .replaceAll("\\b(thinking|reasoning):.*?(\\.|\\n)", "")
        .replaceAll("```", "").trim();
    if (!parentesesBalanceados(response)) {
      response += " )";
    }
    return response;
  }

  public boolean parentesesBalanceados(String expressao) {
    int contador = 0;

    for (char caractere : expressao.toCharArray()) {
      if (caractere == '(') {
        contador++;  // Encontrou abertura - incrementa
      } else if (caractere == ')') {
        contador--;  // Encontrou fechamento - decrementa

        // Se contador ficar negativo, há fechamento sem abertura
        if (contador < 0) {
          return false;
        }
      }
    }

    // Balanceado se contador == 0
    return contador == 0;
  }

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
  @Override
  public TransformacaoResultDTO transformarParaOWL(String descricaoNatural) {
    return transformarParaOWL(getPromptTemplate(), descricaoNatural);
  }

  /**
   * @return o template padrão usado para prompts de geração de OWL.
   */
  @Override
  public String getPromptTemplate() {
    return OWL_PROMPT_TEMPLATE;
  }

  /**
   * Processa a resposta do serviço de IA e converte para objetos AxiomaDTO.
   *
   * @param resposta a resposta bruta do serviço de IA (não pode ser nula)
   * @return lista de axiomas processados e validados
   * @throws IllegalArgumentException se a resposta for nula
   */
  private List<AxiomaDTO> processarRespostaLLM(String resposta) {
    Objects.requireNonNull(resposta, "Resposta da IA não pode ser nula");

    List<AxiomaDTO> axiomas = new ArrayList<>();

    AxiomaDTO axioma = this.owlService.criarAxioma(resposta);
    if (axioma != null) {
      axiomas.add(axioma);
    }

    return Collections.unmodifiableList(axiomas);
  }

  /**
   * Valida os parâmetros para a transformação.
   *
   * @param template         o template de prompt (não pode ser nulo ou vazio)
   * @param descricaoNatural a descrição em linguagem natural (não pode ser nula
   *                         ou vazia)
   * @param uri              a URI base (não pode ser nula ou vazia)
   * @throws IllegalArgumentException se algum parâmetro for nulo ou vazio
   */
  private void validateTransformParameters(String template,
                                           String descricaoNatural) {
    if (template == null || template.trim().isEmpty()) {
      throw new IllegalArgumentException("Template não pode ser nulo ou vazio");
    }
    if (descricaoNatural == null || descricaoNatural.trim().isEmpty()) {
      throw new IllegalArgumentException("Descrição natural não pode ser nula ou vazia");
    }
  }

  /**
   * * Processa o template de prompt substituindo os placeholders pelos valores
   * reais.
   *
   * @param template  o template de prompt (não pode ser nulo)
   * @param descricao Descrição em linguagem natural (não pode ser nula)
   * @return o prompt processado com os valores substituídos
   * @throws IllegalArgumentException se algum parâmetro for nulo
   */
  private String processPromptTemplate(String template, String descricao) {
    return template.replaceAll(PARAM_DESCRICAO, descricao)
        .replaceAll(PARAM_URI, owlService.getUri());
  }

  /**
   * * Chama o modelo de chat com o prompt processado e retorna a resposta.
   *
   * @param promptRequest o prompt a ser enviado ao modelo de chat (não pode ser
   *                      nulo)
   * @return a resposta do modelo de chat
   * @throws IllegalArgumentException se o prompt for nulo
   */
  public String callChatModel(String promptRequest) {
    Objects.requireNonNull(promptRequest, "Prompt não pode ser nulo");

    // Use o serviço híbrido
    return communicationService.sendPrompt(getChatModel(), promptRequest);
  }

  /**
   * * Cria um resultado de transformação bem-sucedido.
   *
   * @param axiomas a lista de axiomas gerados (não pode ser nula)
   * @return o resultado da transformação
   * @throws IllegalArgumentException se axiomas for nulo
   */
  private TransformacaoResultDTO createSuccessResult(List<AxiomaDTO> axiomas) {
    try {
      AnaliseInferenciaDTO check = owlService.checkInferrences();
      return new TransformacaoResultDTO(axiomas, Collections
          .emptyList(), true, "Transformação realizada com sucesso usando Spring AI",
                                        check);
    } catch (Exception e) {
      return new TransformacaoResultDTO(axiomas, Collections.emptyList(),
                                        false, e.getLocalizedMessage(),
                                        null);
    }
  }

  /**
   * * Cria um resultado de transformação com erro.
   *
   * @param errorMessage a mensagem de erro (não pode ser nula)
   * @param cause        a causa do erro (pode ser nula)
   * @return o resultado da transformação com erro
   * @throws IllegalArgumentException se errorMessage for nulo
   */
  private TransformacaoResultDTO createErrorResult(String errorMessage,
                                                   Exception cause) {
    String detailedMessage = cause != null ? String
        .format("%s: %s", errorMessage, cause.getMessage()) : errorMessage;

    return new TransformacaoResultDTO(Collections.emptyList(),
                                      List.of(detailedMessage), false,
                                      "Falha na transformação", null);
  }

  /**
   * Retorna o ChatModel utilizado pelo serviço.
   *
   * @return o ChatModel instance
   */
  @Override
  public ChatModel getChatModel() {
    return chatModel;
  }
}
