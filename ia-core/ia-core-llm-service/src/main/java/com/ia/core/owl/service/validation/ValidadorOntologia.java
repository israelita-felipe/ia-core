package com.ia.core.owl.service.validation;

import com.ia.core.llm.service.model.validacao.ResultadoValidacao;
import com.ia.core.owl.service.DefaultOwlService;
import com.ia.core.owl.service.OpenlletReasonerService;
import com.ia.core.owl.service.model.axioma.AxiomaDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Serviço para validação de ontologias e axiomas.
 * <p>
 * Integra com OpenlletReasonerService para verificar consistência OWL 2 DL.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@Service
public class ValidadorOntologia {

  private final DefaultOwlService owlService;
  private final OpenlletReasonerService reasonerService;

  public ValidadorOntologia(DefaultOwlService owlService,
                           OpenlletReasonerService reasonerService) {
    this.owlService = owlService;
    this.reasonerService = reasonerService;
  }

  /**
   * Valida a consistência de um axioma individual.
   *
   * @param axiom axioma a validar
   * @return resultado da validação
   */
  public ResultadoValidacao validarAxioma(AxiomaDTO axiom) {
    long startTime = System.currentTimeMillis();
    log.debug("Validando axioma: {}", axiom);

    try {
      // Adiciona axioma temporariamente para teste
      owlService.addAxioms(() -> List.of(axiom));

      // Verifica consistência
      boolean consistente = reasonerService.isConsistent();

      long processingTime = System.currentTimeMillis() - startTime;

      if (consistente) {
        log.debug("Axioma consistente: {}", axiom);
        return ResultadoValidacao.builder()
            .consistente(true)
            .explicacao("Axioma é consistente com a ontologia atual")
            .iteracoesUsadas(1)
            .tempoProcessamentoMs(processingTime)
            .build();
      } else {
        // Obtém classes insatisfatíveis
        List<String> unsatisfiableClasses = reasonerService.getUnsatisfiableClasses().stream()
            .map(owlClass -> owlClass.toString())
            .toList();

        log.warn("Axioma inconsistente: {}, classes insatisfatíveis: {}",
                 axiom, unsatisfiableClasses);

        return ResultadoValidacao.builder()
            .consistente(false)
            .classesInsatisfativeis(unsatisfiableClasses)
            .explicacao("Axioma causa inconsistência na ontologia")
            .iteracoesUsadas(1)
            .tempoProcessamentoMs(processingTime)
            .build();
      }
    } catch (Exception e) {
      long processingTime = System.currentTimeMillis() - startTime;
      log.error("Erro ao validar axioma: {}", axiom, e);

      return ResultadoValidacao.builder()
          .consistente(false)
          .explicacao("Erro na validação: " + e.getMessage())
          .iteracoesUsadas(1)
          .tempoProcessamentoMs(processingTime)
          .build();
    }
  }

  /**
   * Valida a consistência de uma lista de axiomas.
   *
   * @param axioms lista de axiomas a validar
   * @return resultado da validação
   */
  public ResultadoValidacao validarAxiomas(List<AxiomaDTO> axioms) {
    long startTime = System.currentTimeMillis();
    log.debug("Validando {} axiomas", axioms.size());

    try {
      // Adiciona axiomas temporariamente para teste
      owlService.addAxioms(() -> axioms);

      // Verifica consistência
      boolean consistente = reasonerService.isConsistent();

      long processingTime = System.currentTimeMillis() - startTime;

      if (consistente) {
        log.debug("Axiomas consistentes: {}", axioms.size());
        return ResultadoValidacao.builder()
            .consistente(true)
            .explicacao("Todos os axiomas são consistentes com a ontologia atual")
            .iteracoesUsadas(1)
            .tempoProcessamentoMs(processingTime)
            .build();
      } else {
        // Obtém classes insatisfatíveis
        List<String> unsatisfiableClasses = reasonerService.getUnsatisfiableClasses().stream()
            .map(owlClass -> owlClass.toString())
            .toList();

        log.warn("Axiomas inconsistentes, classes insatisfatíveis: {}", unsatisfiableClasses);

        return ResultadoValidacao.builder()
            .consistente(false)
            .classesInsatisfativeis(unsatisfiableClasses)
            .explicacao("Axiomas causam inconsistência na ontologia")
            .iteracoesUsadas(1)
            .tempoProcessamentoMs(processingTime)
            .build();
      }
    } catch (Exception e) {
      long processingTime = System.currentTimeMillis() - startTime;
      log.error("Erro ao validar axiomas", e);

      return ResultadoValidacao.builder()
          .consistente(false)
          .explicacao("Erro na validação: " + e.getMessage())
          .iteracoesUsadas(1)
          .tempoProcessamentoMs(processingTime)
          .build();
    }
  }

  /**
   * Valida a consistência da ontologia atual.
   *
   * @return resultado da validação
   */
  public ResultadoValidacao validarOntologiaAtual() {
    long startTime = System.currentTimeMillis();
    log.debug("Validando ontologia atual");

    try {
      boolean consistente = reasonerService.isConsistent();
      long processingTime = System.currentTimeMillis() - startTime;

      if (consistente) {
        log.debug("Ontologia atual é consistente");
        return ResultadoValidacao.builder()
            .consistente(true)
            .explicacao("Ontologia é consistente")
            .iteracoesUsadas(1)
            .tempoProcessamentoMs(processingTime)
            .build();
      } else {
        List<String> unsatisfiableClasses = reasonerService.getUnsatisfiableClasses().stream()
            .map(owlClass -> owlClass.toString())
            .toList();

        log.warn("Ontologia inconsistente, classes insatisfatíveis: {}", unsatisfiableClasses);

        return ResultadoValidacao.builder()
            .consistente(false)
            .classesInsatisfativeis(unsatisfiableClasses)
            .explicacao("Ontologia contém inconsistências")
            .iteracoesUsadas(1)
            .tempoProcessamentoMs(processingTime)
            .build();
      }
    } catch (Exception e) {
      long processingTime = System.currentTimeMillis() - startTime;
      log.error("Erro ao validar ontologia atual", e);

      return ResultadoValidacao.builder()
          .consistente(false)
          .explicacao("Erro na validação: " + e.getMessage())
          .iteracoesUsadas(1)
          .tempoProcessamentoMs(processingTime)
          .build();
    }
  }
}
