package com.ia.core.owl.service;

import java.util.List;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.reasoner.InferenceType;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.util.InferredAxiomGenerator;

import com.ia.core.owl.service.exception.OWLParserException;

/**
 *
 */
public interface CoreOWLReasoner {

  /**
   * Verifica se a ontologia carregada é consistente.
   *
   * @return {@code true} se a ontologia for consistente, {@code false} caso
   *         contrário
   * @throws IllegalStateException se nenhuma ontologia estiver carregada
   */
  boolean isConsistent();

  /**
   * Retorna todas as classes insatisfatíveis (inconsistentes) na ontologia.
   *
   * @return conjunto de classes insatisfatíveis, excluindo a classe bottom
   *         (owl:Nothing)
   * @throws IllegalStateException se nenhuma ontologia estiver carregada
   */
  Set<OWLClass> getUnsatisfiableClasses();

  /**
   * Realiza inferências sobre a ontologia e retorna novos axiomas inferidos.
   * <p>
   * Os axiomas retornados são aqueles que não estavam presentes na ontologia
   * original mas foram inferidos pelo reasoner.
   * </p>
   *
   * @return lista de axiomas inferidos em formato Manchester
   * @throws OWLParserException
   * @throws IllegalStateException se a ontologia não estiver carregada ou for
   *                               inconsistente
   * @throws RuntimeException      se ocorrer erro durante a geração da
   *                               ontologia inferida
   */
  Set<OWLAxiom> performInferences()
    throws OWLParserException;

  /**
   * Detecta e retorna uma lista de inconsistências na ontologia.
   * <p>
   * As inconsistências detectadas incluem:
   * <ul>
   * <li>Ontologia inconsistente</li>
   * <li>Classes insatisfatíveis</li>
   * <li>Indivíduos pertencendo a classes disjuntas</li>
   * </ul>
   * </p>
   *
   * @return lista de mensagens descrevendo as inconsistências encontradas
   * @throws IllegalStateException se nenhuma ontologia estiver carregada
   */
  List<String> detectInconsistencies();

  /**
   * Classifica a ontologia, computando a hierarquia completa de classes.
   *
   * @throws IllegalStateException se nenhuma ontologia estiver carregada
   */
  void classify();

  /**
   * Libera os recursos utilizados pelo reasoner.
   * <p>
   * Este método deve ser chamado quando o reasoner não for mais necessário para
   * garantir a liberação adequada de recursos.
   * </p>
   */
  void dispose();

  /**
   * Retorna o reasoner Openllet em uso.
   *
   * @return o reasoner OWL, ou {@code null} se nenhum estiver inicializado
   */
  OWLReasoner getReasoner();

  /**
   *
   */
  void refreshReasoner();

  /**
   * @return
   */
  InferenceType[] getInferenceTypes();

  /**
   * @return
   */
  List<InferredAxiomGenerator<? extends OWLAxiom>> getAxiomGenerators();

}
