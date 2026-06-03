package com.ia.core.llm.rest.web;

import lombok.extern.slf4j.Slf4j;

import org.springaicommunity.agent.tools.BraveWebSearchTool;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * Built-in tool para busca na internet usando BraveWebSearchTool do spring-ai-agent-utils.
 * <p>
 * Wrapper para o BraveWebSearchTool nativo do spring-ai-agent-utils, que usa a API do Brave Search
 * para realizar buscas na internet. Esta é a única built-in tool do padrão ia-core para spring-ai-agent-utils.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@Service
public class WebSearchTool {

    public static final int MAX_RESULT_COUNT = 10;
    private final BraveWebSearchTool braveWebSearchTool;

  public WebSearchTool(@Value("${brave.api.key:}") String apiKey) {
    if (apiKey == null || apiKey.isEmpty()) {
      log.warn("Brave API key não configurada. WebSearchTool não estará funcional.");
      this.braveWebSearchTool = null;
    } else {
      this.braveWebSearchTool = BraveWebSearchTool.builder(apiKey)
          .resultCount(MAX_RESULT_COUNT)
          .build();
      log.info("BraveWebSearchTool inicializado com sucesso");
    }
  }

  /**
   * Realiza busca na internet usando BraveWebSearchTool.
   *
   * @param query termo de busca a ser pesquisado na internet*
   * @return resultados da busca em formato de texto
   */
  @Tool(
      description = "Realiza busca na internet usando Brave Search API e retorna os resultados mais relevantes. " +
                   "Útil para obter informações atualizadas, verificar fatos e realizar pesquisas."
  )
  public String searchWeb(
      @ToolParam(description = "Termo de busca a ser pesquisado na internet") String query) {

    if (braveWebSearchTool == null) {
      log.error("BraveWebSearchTool não inicializado. Configure brave.api.key");
      return "Erro: BraveWebSearchTool não inicializado. Configure brave.api.key no application.yml";
    }

    log.info("Iniciando busca na internet: query={}, maxResults={}", query, MAX_RESULT_COUNT);

    try {
      String results = braveWebSearchTool.webSearch(query, Collections.emptyList(), Collections.emptyList());
      log.info("Busca concluída com sucesso: query={}", query);
      return results;
    } catch (Exception e) {
      log.error("Erro durante busca na internet: query={}", query, e);
      return "Erro durante busca na internet: " + e.getMessage();
    }
  }
}
