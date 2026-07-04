package com.ia.core.llm.service.agente;

import com.ia.core.llm.service.chat.ChatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
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

  private final ChatService chatService;
  private final ExtratorEntidadesRelacoes extrator;

  private static final Pattern FRASE_PATTERN = Pattern.compile("[.!?]+\\s*");
  private static final Pattern PALAVRA_PATTERN = Pattern.compile("\\b[a-zA-ZÀ-ÿ]{3,}\\b");

  public AnaliseCorpus(ChatService chatService,
                      ExtratorEntidadesRelacoes extrator) {
    this.chatService = chatService;
    this.extrator = extrator;
  }

  /**
   * Analisa um corpus completo e extrai elementos ontológicos.
   *
   * @param corpus texto do corpus
   * @return mapa com elementos extraídos
   */
  @Tool(description = "Analisa um corpus de texto e extrai elementos ontológicos (classes, propriedades, indivíduos, relações). " +
                     "Divide o texto em sentenças e usa o ExtratorEntidadesRelacoes para identificar elementos em cada sentença. " +
                     "Remove duplicatas e retorna um mapa com listas de elementos únicos.")
  public Map<String, List<String>> analisarCorpus(@ToolParam(description = "Texto do corpus a ser analisado") String corpus,
                                                 @ToolParam(description = "ID da sessão para contexto") String sessionId) {
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

      todasClasses.addAll(extrator.extrairClasses(sentenca, sessionId));
      todasPropriedades.addAll(extrator.extrairPropriedadesObjeto(sentenca, sessionId));
      todosIndividuos.addAll(extrator.extrairIndividuos(sentenca, sessionId));
      todasRelacoes.addAll(extrator.extrairRelacoes(sentenca, sessionId));
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
  @Tool(description = "Analisa um corpus de texto usando LLM para extração profunda de elementos ontológicos. " +
                     "Usa o ChatService para enviar um prompt especializado ao LLM que identifica classes, propriedades, indivíduos e relações. " +
                     "Retorna um mapa JSON com os elementos extraídos.")
  public Map<String, List<String>> analisarCorpusComLLM(@ToolParam(description = "Texto do corpus a ser analisado pelo LLM") String corpus,
                                                        @ToolParam(description = "ID da sessão para contexto") String sessionId) {
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
      String resposta = chatService.ask("", prompt, sessionId);

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
  @Tool(description = "Identifica o domínio principal de um corpus de texto usando LLM. " +
                     "Analisa o conteúdo e retorna uma única palavra representando o domínio (ex: biologia, medicina, finanças). " +
                     "Útil para categorizar ontologias por domínio de conhecimento.")
  public String identificarDominio(@ToolParam(description = "Texto do corpus para identificar o domínio") String corpus,
                                 @ToolParam(description = "ID da sessão para contexto") String sessionId) {
    String prompt = String.format("""
        Você é um especialista em análise de texto.
        Identifique o domínio principal do seguinte texto.
        Retorne APENAS o domínio em uma única palavra (ex: biologia, medicina, finanças, etc).

        Texto: %s

        Domínio:
        """, corpus.length() > 2000 ? corpus.substring(0, 2000) : corpus);

    try {
      String resposta = chatService.ask("", prompt, sessionId);
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
  @Tool(description = "Calcula estatísticas detalhadas de um corpus de texto. " +
                     "Retorna métricas como total de caracteres, sentenças, palavras, média de palavras por sentença e vocabulário único. " +
                     "Útil para entender a complexidade e tamanho do corpus antes da análise ontológica.")
  public Map<String, Object> calcularEstatisticas(@ToolParam(description = "Texto do corpus para calcular estatísticas") String corpus) {
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
    try {
      // Extrai o JSON da resposta (pode estar entre ```json e ```)
      String jsonStr = resposta;
      if (resposta.contains("```json")) {
        jsonStr = resposta.substring(resposta.indexOf("```json") + 7, resposta.lastIndexOf("```"));
      } else if (resposta.contains("```")) {
        jsonStr = resposta.substring(resposta.indexOf("```") + 3, resposta.lastIndexOf("```"));
      }
      jsonStr = jsonStr.trim();

      // Parse manual do JSON (simplificado para evitar dependência de Jackson/Gson)
      List<String> classes = extrairArray(jsonStr, "classes");
      List<String> propriedades = extrairArray(jsonStr, "propriedades");
      List<String> individuos = extrairArray(jsonStr, "individuos");
      List<String> relacoes = extrairArray(jsonStr, "relacoes");

      elementos.put("classes", classes);
      elementos.put("propriedades", propriedades);
      elementos.put("individuos", individuos);
      elementos.put("relacoes", relacoes);

      log.debug("Parse JSON concluído: classes={}, propriedades={}, individuos={}, relacoes={}",
               classes.size(), propriedades.size(), individuos.size(), relacoes.size());

    } catch (Exception e) {
      log.warn("Erro ao parsear resposta JSON do LLM: {}", e.getMessage());
      // Em caso de erro, inicializa listas vazias
      elementos.put("classes", new ArrayList<>());
      elementos.put("propriedades", new ArrayList<>());
      elementos.put("individuos", new ArrayList<>());
      elementos.put("relacoes", new ArrayList<>());
    }
  }

  /**
   * Extrai um array JSON manualmente.
   */
  private List<String> extrairArray(String json, String chave) {
    List<String> valores = new ArrayList<>();
    String pattern = "\"" + chave + "\"\\s*:\\s*\\[";
    int startIdx = json.indexOf(pattern);
    if (startIdx == -1) {
      return valores;
    }

    int arrayStart = startIdx + pattern.length();
    int bracketCount = 0;
    StringBuilder currentValue = new StringBuilder();
    boolean inString = false;

    for (int i = arrayStart; i < json.length(); i++) {
      char c = json.charAt(i);

      if (c == '"' && (i == 0 || json.charAt(i - 1) != '\\')) {
        inString = !inString;
        if (!inString && currentValue.length() > 0) {
          valores.add(currentValue.toString());
          currentValue = new StringBuilder();
        }
      } else if (inString) {
        currentValue.append(c);
      } else if (c == '[') {
        bracketCount++;
      } else if (c == ']') {
        bracketCount--;
        if (bracketCount == 0) {
          break;
        }
      }
    }

    return valores;
  }
}
