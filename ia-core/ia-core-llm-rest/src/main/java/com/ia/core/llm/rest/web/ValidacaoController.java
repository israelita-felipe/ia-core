package com.ia.core.llm.rest.web;

import com.ia.core.llm.service.model.validacao.ResultadoValidacao;
import com.ia.core.owl.service.validation.ValidadorOntologia;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller REST para validação de ontologias.
 * <p>
 * Expõe endpoints para verificação de consistência e explicação de inconsistências.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/llm/validacao")
@Tag(name = "Validação", description = "Validação de ontologias")
public class ValidacaoController {

  private final ValidadorOntologia validador;

  public ValidacaoController(ValidadorOntologia validador) {
    this.validador = validador;
  }

  @Operation(summary = "Verifica consistência de axioma ou ontologia")
  @PostMapping("/verificar-consistencia")
  public ResponseEntity<ResultadoValidacao> verificarConsistencia(
      @Valid @RequestBody ValidacaoRequest request) {
    log.info("Verificando consistência");

    ResultadoValidacao resultado;
    if (request.getAxioma() != null && !request.getAxioma().isEmpty()) {
      // Na implementação completa, parsear o axioma string para AxiomaDTO
      resultado = validador.validarOntologiaAtual();
    } else {
      resultado = validador.validarOntologiaAtual();
    }

    return ResponseEntity.ok(resultado);
  }

  @Operation(summary = "Explica inconsistência em linguagem natural")
  @PostMapping("/explicar-inconsistencia")
  public ResponseEntity<ExplicacaoResponse> explicarInconsistencia(
      @Valid @RequestBody ValidacaoRequest request) {
    log.info("Explicando inconsistência");

    // Na implementação completa, usaria ExplicadorInconsistencia
    ExplicacaoResponse resposta = new ExplicacaoResponse(
        "Inconsistência detectada",
        "A ontologia contém classes insatisfatíveis",
        List.of("Classe1", "Classe2"),
        "CLASSE_INSATISFATIVEL",
        List.of("Revise as definições de subclasse", "Verifique restrições contraditórias"),
        "ERROR"
    );

    return ResponseEntity.ok(resposta);
  }

  /**
   * DTO para requisição de validação.
   */
  @lombok.Data
  @lombok.NoArgsConstructor
  @lombok.AllArgsConstructor
  public static class ValidacaoRequest {
    private String axioma;
    private String ontologia;
  }

  /**
   * DTO para resposta de explicação.
   */
  @lombok.Data
  @lombok.AllArgsConstructor
  @lombok.NoArgsConstructor
  public static class ExplicacaoResponse {
    private String mensagemTecnica;
    private String explicacaoNatural;
    private java.util.List<String> axiomasCausadores;
    private String tipoInconsistencia;
    private java.util.List<String> sugestoesCorrecao;
    private String gravidade;
  }
}
