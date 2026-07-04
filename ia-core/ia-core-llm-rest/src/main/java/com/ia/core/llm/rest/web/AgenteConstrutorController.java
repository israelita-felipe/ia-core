package com.ia.core.llm.rest.web;

import com.ia.core.llm.service.agente.AgenteConstrutorOntologiaService;
import com.ia.core.llm.service.model.agente.RequisicaoConstrucaoOntologia;
import com.ia.core.llm.service.model.agente.ResultadoConstrucaoOntologia;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller REST para agente construtor de ontologias.
 * <p>
 * Expõe endpoints para construção autônoma de ontologias OWL 2 DL.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/api/${api.version}/llm/agentes/construtor")
@Tag(name = "Agente Construtor", description = "Agente construtor de ontologias")
public class AgenteConstrutorController {

  private final AgenteConstrutorOntologiaService agenteService;

  public AgenteConstrutorController(AgenteConstrutorOntologiaService agenteService) {
    this.agenteService = agenteService;
  }

  @Operation(summary = "Inicia construção de ontologia")
  @PostMapping("/jobs/{sessionId}")
  public ResponseEntity<ResultadoConstrucaoOntologia> iniciarConstrucao(
      @Valid @RequestBody RequisicaoConstrucaoOntologia requisicao) {
    log.info("Iniciando construção: dominio={}", requisicao.getDomain());

    ResultadoConstrucaoOntologia resultado = agenteService.iniciarConstrucao(requisicao);

    return ResponseEntity.ok(resultado);
  }

  @Operation(summary = "Obtém progresso do job")
  @GetMapping("/jobs/{jobId}/progresso")
  public ResponseEntity<ResultadoConstrucaoOntologia> obterProgresso(@PathVariable String jobId) {
    log.debug("Obtendo progresso: jobId={}", jobId);

    ResultadoConstrucaoOntologia resultado = agenteService.obterProgresso(jobId);

    return ResponseEntity.ok(resultado);
  }

  @Operation(summary = "Obtém resultado final do job")
  @GetMapping("/jobs/{jobId}/resultado")
  public ResponseEntity<ResultadoConstrucaoOntologia> obterResultado(@PathVariable String jobId) {
    log.debug("Obtendo resultado: jobId={}", jobId);

    ResultadoConstrucaoOntologia resultado = agenteService.obterResultado(jobId);

    return ResponseEntity.ok(resultado);
  }

  @Operation(summary = "Cancela job em execução")
  @PostMapping("/jobs/{jobId}/cancelar")
  public ResponseEntity<ResultadoConstrucaoOntologia> cancelarJob(@PathVariable String jobId) {
    log.info("Cancelando job: jobId={}", jobId);

    ResultadoConstrucaoOntologia resultado = agenteService.cancelarJob(jobId);

    return ResponseEntity.ok(resultado);
  }
}
