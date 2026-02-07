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
 * Interface para serviços de raciocínio OWL.
 * <p>
 * Define operações para verificação de consistência, inferências e detecção
 * de inconsistências em ontologias OWL.
 * </p>
 *
 * @author Israel Araújo
 */
public interface OWLReasoningService {

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
   * @return conjunto de axiomas inferidos
   * @throws OWLParserException se ocorrer erro no parsing
   * @throws IllegalStateException se a ontologia não estiver carregada ou for
   *                               inconsistente
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
   * Retorna o reasoner OWL em uso.
   *
   * @return o reasoner OWL, ou {@code null} se nenhum estiver inicializado
   */
  OWLReasoner getReasoner();

  /**
   * Reinicializa o reasoner com a ontologia atual.
   */
  void refreshReasoner();

  /**
   * Retorna os tipos de inferências configurados.
   *
   * @return array de tipos de inferências
   */
  InferenceType[] getInferenceTypes();

  /**
   * Retorna os geradores de axiomas inferidos configurados.
   *
   * @return lista de geradores de axiomas
   */
  List<InferredAxiomGenerator<? extends OWLAxiom>> getAxiomGenerators();
}
