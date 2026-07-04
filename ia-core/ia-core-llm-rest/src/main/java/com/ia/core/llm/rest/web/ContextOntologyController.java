package com.ia.core.llm.rest.web;

import com.ia.core.llm.model.agente.ContextoConversacao;
import com.ia.core.llm.service.agente.ContextoConversacaoService;
import com.ia.core.llm.service.model.agente.ContextConversacaoDTO;
import com.ia.core.rest.control.DefaultBaseController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * Controller REST para gerenciamento de ontologia de contexto.
 * <p>
 * Expõe endpoints para gerenciar ontologias de contexto de conversação.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/api/${api.version}/llm/context-ontology")
@Tag(name = "Context Ontology", description = "Gerenciamento de ontologia de contexto de conversação")
public class ContextOntologyController
  extends DefaultBaseController<ContextoConversacao, ContextConversacaoDTO> {

  private final ContextoConversacaoService contextoConversacaoService;

  public ContextOntologyController(ContextoConversacaoService contextoConversacaoService) {
    super(contextoConversacaoService);
    this.contextoConversacaoService = contextoConversacaoService;
  }

  @PostMapping("/create")
  @Operation(summary = "Criar ontologia de contexto", description = "Cria uma nova ontologia de contexto para uma sessão")
  public ContextConversacaoDTO createContextOntology(
      @Parameter(description = "ID da sessão de conversação")
      @RequestParam String sessionId,
      @Parameter(description = "ID do usuário")
      @RequestParam String userId,
      @Parameter(description = "Domínio da conversação")
      @RequestParam String dominio) {
    log.debug("Criando ontologia de contexto: sessionId={}, userId={}, dominio={}", sessionId, userId, dominio);
    return contextoConversacaoService.createContextOntology(sessionId, userId, dominio);
  }

  @GetMapping("/{sessionId}")
  @Operation(summary = "Recuperar ontologia de contexto", description = "Recupera a ontologia de contexto para uma sessão específica")
  public ContextConversacaoDTO getContextOntology(
      @Parameter(description = "ID da sessão de conversação")
      @PathVariable String sessionId) {
    log.debug("Recuperando ontologia de contexto: sessionId={}", sessionId);
    return contextoConversacaoService.getContextOntology(sessionId)
        .orElseThrow(() -> new IllegalArgumentException("Contexto não encontrado para sessionId: " + sessionId));
  }

  @PutMapping("/{sessionId}/update")
  @Operation(summary = "Atualizar ontologia de contexto", description = "Atualiza dinamicamente a ontologia de contexto durante a conversação")
  public ContextConversacaoDTO updateContextOntology(
      @Parameter(description = "ID da sessão de conversação")
      @PathVariable String sessionId,
      @Parameter(description = "Axioma em Manchester OWL Syntax")
      @RequestBody String manchesterAxiom)
    throws com.ia.core.owl.service.exception.OWLParserException,
           org.semanticweb.owlapi.model.OWLOntologyCreationException {
    log.debug("Atualizando ontologia de contexto: sessionId={}, axiom={}", sessionId, manchesterAxiom);
    return contextoConversacaoService.updateContextOntology(sessionId, manchesterAxiom);
  }

  @DeleteMapping("/{sessionId}")
  @Operation(summary = "Remover ontologia de contexto", description = "Remove a ontologia de contexto para uma sessão específica")
  public void deleteContextOntology(
      @Parameter(description = "ID da sessão de conversação")
      @PathVariable String sessionId) {
    log.debug("Removendo ontologia de contexto: sessionId={}", sessionId);
    contextoConversacaoService.deleteContextOntology(sessionId);
  }

  @GetMapping("/{sessionId}/exists")
  @Operation(summary = "Verificar existência de ontologia", description = "Verifica se existe uma ontologia de contexto para uma sessão")
  public boolean existsContextOntology(
      @Parameter(description = "ID da sessão de conversação")
      @PathVariable String sessionId) {
    log.debug("Verificando existência de ontologia de contexto: sessionId={}", sessionId);
    return contextoConversacaoService.existsContextOntology(sessionId);
  }
}
