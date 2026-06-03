package com.ia.core.llm.service.agente;

import com.ia.core.owl.service.LLMCommunicator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Serviço para extração de entidades e relações de texto.
 * <p>
 * Utiliza LLM para identificar classes, propriedades e indivíduos
 * em descrições em linguagem natural.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@Service
public class ExtratorEntidadesRelacoes {

  private final ChatModel chatModel;
  private final LLMCommunicator llmCommunicator;

  public ExtratorEntidadesRelacoes(ChatModel chatModel, LLMCommunicator llmCommunicator) {
    this.chatModel = chatModel;
    this.llmCommunicator = llmCommunicator;
  }

  /**
   * Extrai classes de um texto.
   *
   * @param texto texto para análise
   * @return lista de classes encontradas
   */
  public List<String> extrairClasses(String texto) {
    List<String> classes = new ArrayList<>();

    // Se não encontrou com regex, usa LLM
    if (classes.isEmpty()) {
      classes.addAll(extrairClassesComLLM(texto));
    }

    return classes;
  }

  /**
   * Extrai propriedades de objeto de um texto.
   *
   * @param texto texto para análise
   * @return lista de propriedades encontradas
   */
  public List<String> extrairPropriedadesObjeto(String texto) {
    List<String> propriedades = new ArrayList<>();

    if (propriedades.isEmpty()) {
      propriedades.addAll(extrairPropriedadesComLLM(texto));
    }

    return propriedades;
  }

  /**
   * Extrai indivíduos de um texto.
   *
   * @param texto texto para análise
   * @return lista de indivíduos encontrados
   */
  public List<String> extrairIndividuos(String texto) {
    List<String> individuos = new ArrayList<>();

    if (individuos.isEmpty()) {
      individuos.addAll(extrairIndividuosComLLM(texto));
    }

    return individuos;
  }

  /**
   * Extrai relações entre entidades de um texto.
   *
   * @param texto texto para análise
   * @return lista de relações encontradas (formato: "entidade1:relação:entidade2")
   */
  public List<String> extrairRelacoes(String texto) {
    List<String> relacoes = new ArrayList<>();

    String prompt = String.format("""
        Você é um especialista em extração de relações de texto.
        Extraia as relações entre entidades do seguinte texto.
        Retorne as relações no formato: "entidade1:relação:entidade2"
        Uma relação por linha.

        Texto: %s

        Retorne APENAS as relações, sem explicações adicionais.
        """, texto);

    try {
      String resposta = llmCommunicator.sendPrompt(chatModel, prompt);
      String[] linhas = resposta.split("\n");

      for (String linha : linhas) {
        linha = linha.trim();
        if (!linha.isEmpty() && linha.contains(":")) {
          relacoes.add(linha);
          log.debug("Relação extraída: {}", linha);
        }
      }
    } catch (Exception e) {
      log.warn("Erro ao extrair relações com LLM: {}", e.getMessage());
    }

    return relacoes;
  }

  /**
   * Usa LLM para extrair classes quando regex não é suficiente.
   */
  private List<String> extrairClassesComLLM(String texto) {
    List<String> classes = new ArrayList<>();

    String prompt = String.format("""
        Você é um especialista em ontologias OWL.
        Extraia todas as classes mencionadas no seguinte texto.
        Retorne uma classe por linha.

        Texto: %s

        Retorne APENAS as classes, sem explicações adicionais.
        """, texto);

    try {
      String resposta = llmCommunicator.sendPrompt(chatModel, prompt);
      String[] linhas = resposta.split("\n");

      for (String linha : linhas) {
        linha = linha.trim();
        if (!linha.isEmpty()) {
          classes.add(linha);
        }
      }
    } catch (Exception e) {
      log.warn("Erro ao extrair classes com LLM: {}", e.getMessage());
    }

    return classes;
  }

  /**
   * Usa LLM para extrair propriedades quando regex não é suficiente.
   */
  private List<String> extrairPropriedadesComLLM(String texto) {
    List<String> propriedades = new ArrayList<>();

    String prompt = String.format("""
        Você é um especialista em ontologias OWL.
        Extraia todas as propriedades de objeto mencionadas no seguinte texto.
        Retorne uma propriedade por linha.

        Texto: %s

        Retorne APENAS as propriedades, sem explicações adicionais.
        """, texto);

    try {
      String resposta = llmCommunicator.sendPrompt(chatModel, prompt);
      String[] linhas = resposta.split("\n");

      for (String linha : linhas) {
        linha = linha.trim();
        if (!linha.isEmpty()) {
          propriedades.add(linha);
        }
      }
    } catch (Exception e) {
      log.warn("Erro ao extrair propriedades com LLM: {}", e.getMessage());
    }

    return propriedades;
  }

  /**
   * Usa LLM para extrair indivíduos quando regex não é suficiente.
   */
  private List<String> extrairIndividuosComLLM(String texto) {
    List<String> individuos = new ArrayList<>();

    String prompt = String.format("""
        Você é um especialista em ontologias OWL.
        Extraia todos os indivíduos/entidades mencionados no seguinte texto.
        Retorne um indivíduo por linha.

        Texto: %s

        Retorne APENAS os indivíduos, sem explicações adicionais.
        """, texto);

    try {
      String resposta = llmCommunicator.sendPrompt(chatModel, prompt);
      String[] linhas = resposta.split("\n");

      for (String linha : linhas) {
        linha = linha.trim();
        if (!linha.isEmpty()) {
          individuos.add(linha);
        }
      }
    } catch (Exception e) {
      log.warn("Erro ao extrair indivíduos com LLM: {}", e.getMessage());
    }

    return individuos;
  }
}
