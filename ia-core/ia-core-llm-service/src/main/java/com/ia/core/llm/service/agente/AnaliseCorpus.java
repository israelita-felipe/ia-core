package com.ia.core.llm.service.agente;

import com.ia.core.owl.service.LLMCommunicator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Serviço para análise de corpus de texto.
 * <p>
 * Processa grandes volumes de texto para identificar padrões,
 * extrair elementos ontológicos e preparar dados para construção de ontologias.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@Service
public class AnaliseCorpus {

  private final ChatModel chatModel;
  private final LLMCommunicator llmCommunicator;
  private final ExtratorEntidadesRelacoes extrator;

  private static final Pattern FRASE_PATTERN = Pattern.compile("[.!?]+\\s*");
  private static final Pattern PALAVRA_PATTERN = Pattern.compile("\\b[a-zA-ZÀ-ÿ]{3,}\\b");

  public AnaliseCorpus(ChatModel chatModel,
                      LLMCommunicator llmCommunicator,
                      ExtratorEntidadesRelacoes extrator) {
    this.chatModel = chatModel;
    this.llmCommunicator = llmCommunicator;
    this.extrator = extrator;
  }

  /**
   * Analisa um corpus completo e extrai elementos ontológicos.
   *
   * @param corpus texto do corpus
   * @return mapa com elementos extraídos
   */
  public Map<String, List<String>> analisarCorpus(String corpus) {
    log.info("Iniciando análise de corpus: tamanho={} caracteres", corpus.length());

    Map<String, List<String>> elementos = new TreeMap<>();

    // Divide em sentenças para análise
    List<String> sentencas = dividirEmSentencas(corpus);
    log.debug("Corpus dividido em {} sentenças", sentencas.size());

    // Extrai elementos de cada sentença
    List<String> todasClasses = new ArrayList<>();
    List<String> todasPropriedades = new ArrayList<>();
    List<String> todosIndividuos = new ArrayList<>();
    List<String> todasRelacoes = new ArrayList<>();

    for (String sentenca : sentencas) {
      if (sentenca.length() < 10) continue; // Ignora sentenças muito curtas

      todasClasses.addAll(extrator.extrairClasses(sentenca));
      todasPropriedades.addAll(extrator.extrairPropriedadesObjeto(sentenca));
      todosIndividuos.addAll(extrator.extrairIndividuos(sentenca));
      todasRelacoes.addAll(extrator.extrairRelacoes(sentenca));
    }

    // Remove duplicatas
    elementos.put("classes", new ArrayList<>(new java.util.LinkedHashSet<>(todasClasses)));
    elementos.put("propriedades", new ArrayList<>(new java.util.LinkedHashSet<>(todasPropriedades)));
    elementos.put("individuos", new ArrayList<>(new java.util.LinkedHashSet<>(todosIndividuos)));
    elementos.put("relacoes", new ArrayList<>(new java.util.LinkedHashSet<>(todasRelacoes)));

    log.info("Análise concluída: classes={}, propriedades={}, individuos={}, relacoes={}",
             elementos.get("classes").size(),
             elementos.get("propriedades").size(),
             elementos.get("individuos").size(),
             elementos.get("relacoes").size());

    return elementos;
  }

  /**
   * Extrai elementos ontológicos usando LLM para análise profunda.
   *
   * @param corpus texto do corpus
   * @return mapa com elementos extraídos pelo LLM
   */
  public Map<String, List<String>> analisarCorpusComLLM(String corpus) {
    log.info("Iniciando análise de corpus com LLM");

    String prompt = String.format("""
        Você é um especialista em ontologias OWL 2 DL.
        Analise o seguinte corpus de texto e extraia:
        1. Classes (conceitos principais)
        2. Propriedades de objeto (relações entre classes)
        3. Indivíduos (entidades específicas)
        4. Relações (instâncias de propriedades entre indivíduos)

        Retorne no formato JSON:
        {
          "classes": ["classe1", "classe2"],
          "propriedades": ["prop1", "prop2"],
          "individuos": ["individuo1", "individuo2"],
          "relacoes": ["individuo1:prop1:individuo2"]
        }

        Corpus: %s

        Retorne APENAS o JSON, sem explicações adicionais.
        """, corpus.length() > 4000 ? corpus.substring(0, 4000) : corpus);

    Map<String, List<String>> elementos = new TreeMap<>();

    try {
      String resposta = llmCommunicator.sendPrompt(chatModel, prompt);

      // Parse simplificado do JSON (na implementação completa, usaria biblioteca JSON)
      parseRespostaLLM(resposta, elementos);

      log.info("Análise LLM concluída: classes={}, propriedades={}, individuos={}, relacoes={}",
               elementos.getOrDefault("classes", List.of()).size(),
               elementos.getOrDefault("propriedades", List.of()).size(),
               elementos.getOrDefault("individuos", List.of()).size(),
               elementos.getOrDefault("relacoes", List.of()).size());

    } catch (Exception e) {
      log.warn("Erro ao analisar corpus com LLM: {}", e.getMessage());
    }

    return elementos;
  }

  /**
   * Identifica o domínio principal do corpus.
   *
   * @param corpus texto do corpus
   * @return domínio identificado
   */
  public String identificarDominio(String corpus) {
    String prompt = String.format("""
        Você é um especialista em análise de texto.
        Identifique o domínio principal do seguinte texto.
        Retorne APENAS o domínio em uma única palavra (ex: biologia, medicina, finanças, etc).

        Texto: %s

        Domínio:
        """, corpus.length() > 2000 ? corpus.substring(0, 2000) : corpus);

    try {
      String resposta = llmCommunicator.sendPrompt(chatModel, prompt);
      String dominio = resposta.trim().split("\n")[0];
      log.debug("Domínio identificado: {}", dominio);
      return dominio;
    } catch (Exception e) {
      log.warn("Erro ao identificar domínio: {}", e.getMessage());
      return "geral";
    }
  }

  /**
   * Calcula estatísticas do corpus.
   *
   * @param corpus texto do corpus
   * @return mapa com estatísticas
   */
  public Map<String, Object> calcularEstatisticas(String corpus) {
    Map<String, Object> stats = new TreeMap<>();

    List<String> sentencas = dividirEmSentencas(corpus);
    int totalPalavras = contarPalavras(corpus);
    int mediaPalavrasPorSentenca = sentencas.isEmpty() ? 0 : totalPalavras / sentencas.size();

    stats.put("totalCaracteres", corpus.length());
    stats.put("totalSentencas", sentencas.size());
    stats.put("totalPalavras", totalPalavras);
    stats.put("mediaPalavrasPorSentenca", mediaPalavrasPorSentenca);
    stats.put("vocabularioUnico", contarVocabularioUnico(corpus));

    return stats;
  }

  /**
   * Divide o texto em sentenças.
   */
  private List<String> dividirEmSentencas(String texto) {
    String[] partes = FRASE_PATTERN.split(texto);
    List<String> sentencas = new ArrayList<>();

    for (String parte : partes) {
      String sentenca = parte.trim();
      if (!sentenca.isEmpty()) {
        sentencas.add(sentenca);
      }
    }

    return sentencas;
  }

  /**
   * Conta palavras no texto.
   */
  private int contarPalavras(String texto) {
    Matcher matcher = PALAVRA_PATTERN.matcher(texto);
    int count = 0;
    while (matcher.find()) {
      count++;
    }
    return count;
  }

  /**
   * Conta vocabulário único.
   */
  private int contarVocabularioUnico(String texto) {
    java.util.Set<String> palavras = new java.util.HashSet<>();
    Matcher matcher = PALAVRA_PATTERN.matcher(texto.toLowerCase());
    while (matcher.find()) {
      palavras.add(matcher.group());
    }
    return palavras.size();
  }

  /**
   * Parse simplificado da resposta JSON do LLM.
   */
  private void parseRespostaLLM(String resposta, Map<String, List<String>> elementos) {
    // Parse simplificado - na implementação completa usaria Jackson/Gson
    String[] linhas = resposta.split("\n");
    List<String> classes = new ArrayList<>();
    List<String> propriedades = new ArrayList<>();
    List<String> individuos = new ArrayList<>();
    List<String> relacoes = new ArrayList<>();

    String chaveAtual = null;
    for (String linha : linhas) {
      linha = linha.trim();
      if (linha.contains("\"classes\":")) {
        chaveAtual = "classes";
      } else if (linha.contains("\"propriedades\":")) {
        chaveAtual = "propriedades";
      } else if (linha.contains("\"individuos\":")) {
        chaveAtual = "individuos";
      } else if (linha.contains("\"relacoes\":")) {
        chaveAtual = "relacoes";
      } else if (linha.startsWith("\"") && linha.endsWith("\",")) {
        String valor = linha.substring(1, linha.length() - 2);
        switch (chaveAtual) {
          case "classes" -> classes.add(valor);
          case "propriedades" -> propriedades.add(valor);
          case "individuos" -> individuos.add(valor);
          case "relacoes" -> relacoes.add(valor);
        }
      }
    }

    elementos.put("classes", classes);
    elementos.put("propriedades", propriedades);
    elementos.put("individuos", individuos);
    elementos.put("relacoes", relacoes);
  }
}
