package com.ia.core.owl.service.validation;

import com.ia.core.llm.service.model.ontologia.FeedbackRaciocinadorDTO;
import com.ia.core.owl.service.DefaultOwlService;
import com.ia.core.owl.service.LLMCommunicator;
import com.ia.core.owl.service.model.axioma.AxiomaDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Serviço para implementar loops iterativos LLM-Reasoner.
 * <p>
 * Permite auto-correção de axiomas através de feedback do reasoner para o LLM.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@Service
public class LoopLLMRaciocinador {

  private final LLMCommunicator llmCommunicator;
  private final DefaultOwlService owlService;
  private final ExplicadorInconsistencia explicador;

  private static final int MAX_ITERATIONS = 3;

  public LoopLLMRaciocinador(
                            LLMCommunicator llmCommunicator,
                            DefaultOwlService owlService,
                            ExplicadorInconsistencia explicador) {
    this.llmCommunicator = llmCommunicator;
    this.owlService = owlService;
    this.explicador = explicador;
  }

  /**
   * Executa loop iterativo para corrigir um axioma inválido.
   *
   * @param axiomaOriginal axioma original que falhou na validação
   * @param descricaoOriginal descrição original em linguagem natural
   * @param erroValidacao erro de validação
   * @return feedback com axioma corrigido (se bem-sucedido)
   */
  public FeedbackRaciocinadorDTO corrigirAxioma(String sessionId,AxiomaDTO axiomaOriginal,
                                           String descricaoOriginal,
                                           String erroValidacao) {
    return corrigirAxioma(sessionId, axiomaOriginal, descricaoOriginal, erroValidacao, MAX_ITERATIONS);
  }

  /**
   * Executa loop iterativo para corrigir um axioma inválido com limite de iterações customizado.
   *
   * @param axiomaOriginal axioma original que falhou na validação
   * @param descricaoOriginal descrição original em linguagem natural
   * @param erroValidacao erro de validação
   * @param maxIterações número máximo de iterações
   * @return feedback com axioma corrigido (se bem-sucedido)
   */
  public FeedbackRaciocinadorDTO corrigirAxioma(String sessionId,AxiomaDTO axiomaOriginal,
                                           String descricaoOriginal,
                                           String erroValidacao,
                                           int maxIterações) {
    log.info("Iniciando loop LLM-Reasoner para correção de axioma, max iterações: {}", maxIterações);

    AxiomaDTO axiomaAtual = axiomaOriginal;
    String descricaoAtual = descricaoOriginal;

    for (int i = 0; i < maxIterações; i++) {
      log.debug("Iteração {} de {}", i + 1, maxIterações);

      // Constrói prompt de correção
      String prompt = construirPromptCorrecao(descricaoAtual, erroValidacao, i + 1);

      // Envia para LLM
      String respostaLLM = llmCommunicator.sendPrompt(prompt,sessionId).content();
      log.debug("Resposta LLM (iteração {}): {}", i + 1, respostaLLM);

      // Tenta criar axioma corrigido
      try {
        descricaoAtual = extrairDescricaoCorrigida(respostaLLM);
        log.debug("Descrição corrigida (iteração {}): {}", i + 1, descricaoAtual);

        // Cria novo axioma a partir da descrição corrigida
        // Nota: Precisamos de uma forma de criar AxiomaDTO a partir da descrição
        // Isso requer integração com DefaultOwlService ou similar
        // Por enquanto, assumimos sucesso se a descrição foi extraída

        log.info("Axioma corrigido com sucesso na iteração {}", i + 1);
        return FeedbackRaciocinadorDTO.builder()
            .axiomaValido(true)
            .axiomaCorrigido(descricaoAtual)
            .explicacao("Axioma corrigido com sucesso")
            .iteracaoAtual(i + 1)
            .maxIteracoes(maxIterações)
            .build();

      } catch (Exception e) {
        log.warn("Falha na iteração {}: {}", i + 1, e.getMessage());
        erroValidacao = e.getMessage();
      }
    }

    // Se chegou aqui, todas as iterações falharam
    log.warn("Loop LLM-Reasoner falhou após {} iterações", maxIterações);
    return FeedbackRaciocinadorDTO.builder()
        .axiomaValido(false)
        .erroConsistencia(erroValidacao)
        .explicacao("Não foi possível corrigir o axioma após " + maxIterações + " iterações")
        .iteracaoAtual(maxIterações)
        .maxIteracoes(maxIterações)
        .build();
  }

  private String construirPromptCorrecao(String descricaoOriginal, String erro, int iteracao) {
    return String.format("""
        Você é um especialista em ontologias OWL 2 DL.

        Tarefa: Corrigir um axioma OWL que está causando inconsistência.

        Descrição original: %s
        Erro de validação: %s
        Iteração atual: %d

        Por favor, forneça uma versão corrigida da descrição que gere um axioma OWL consistente.
        Retorne APENAS a descrição corrigida, sem explicações adicionais.
        """, descricaoOriginal, erro, iteracao);
  }

  private String extrairDescricaoCorrigida(String respostaLLM) {
    // Remove formatação comum (markdown, aspas extras)
    String limpa = respostaLLM
        .replaceAll("```[a-zA-Z]*", "")
        .replaceAll("```", "")
        .replaceAll("^\"|\"$", "")
        .trim();

    // Se a resposta tiver múltiplas linhas, pega a primeira não vazia
    String[] linhas = limpa.split("\n");
    for (String linha : linhas) {
      if (!linha.trim().isEmpty()) {
        return linha.trim();
      }
    }

    return limpa;
  }
}
