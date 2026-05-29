package com.ia.core.llm.rest.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller REST para busca na internet.
 * <p>
 * Expõe endpoints para realizar buscas na internet usando BraveWebSearchTool do spring-ai-agent-utils,
 * conforme especificado nos CDUs Interface-Agente-Conversacional e Manter-LLM.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/api/${api.version}/llm/web")
@RequiredArgsConstructor
public class WebSearchController {

  private final WebSearchTool webSearchTool;

  /**
   * Realiza busca na internet.
   *
   * @param query termo de busca
   * @return resultados da busca em formato de texto
   */
  @PostMapping("/search")
  public ResponseEntity<String> search(
      @RequestParam String query) {

    log.info("Requisição de busca na internet: query={}, maxResults={}", query);

    try {
      String results = webSearchTool.searchWeb(query);
      log.info("Busca concluída com sucesso: query={}", query);
      return ResponseEntity.ok(results);
    } catch (Exception e) {
      log.error("Erro durante busca na internet: query={}", query, e);
      return ResponseEntity.internalServerError()
          .body("Erro durante busca na internet: " + e.getMessage());
    }
  }

  /**
   * Verifica o status do serviço de busca.
   *
   * @return status do serviço
   */
  @GetMapping("/status")
  public ResponseEntity<String> status() {
    log.debug("Verificando status do serviço de busca na internet");
    return ResponseEntity.ok("Serviço de busca na internet disponível");
  }
}
