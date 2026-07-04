package com.ia.core.llm.rest.web;

import com.ia.core.llm.service.agente.OntologyBuilderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * Controller REST para construção de ontologias.
 * <p>
 * Expõe endpoints para construir ontologias usando ChatService com tools OWL.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/api/${api.version}/llm/ontology-builder")
@Tag(name = "Ontology Builder", description = "Construção de ontologias usando ChatService com tools OWL")
@RequiredArgsConstructor
public class OntologyBuilderController {

  private final OntologyBuilderService ontologyBuilderService;

  @PostMapping("/build")
  @Operation(summary = "Construir ontologia", description = "Constrói uma ontologia a partir de texto/corpus fornecido")
  public String buildOntology(
      @Parameter(description = "ID da sessão de conversação")
      @RequestParam String sessionId,
      @Parameter(description = "Texto/corpus para extração de conceitos e relações")
      @RequestBody String corpus,
      @Parameter(description = "Domínio da ontologia (ex: biologia, biblioteca, medicina)")
      @RequestParam String domain) {
    log.debug("Construindo ontologia: sessionId={}, domain={}", sessionId, domain);
    return ontologyBuilderService.buildOntology(sessionId, corpus, domain);
  }

  @PostMapping("/refine")
  @Operation(summary = "Refinar ontologia", description = "Refina uma ontologia existente com base em feedback")
  public String refineOntology(
      @Parameter(description = "ID da sessão de conversação")
      @RequestParam String sessionId,
      @Parameter(description = "Feedback para refinamento")
      @RequestBody String feedback) {
    log.debug("Refinando ontologia: sessionId={}", sessionId);
    return ontologyBuilderService.refineOntology(sessionId, feedback);
  }
}
