package com.ia.core.owl.service;

import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDocumentFormat;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.PrefixManager;

import com.ia.core.owl.service.exception.OWLParserException;
import com.ia.core.owl.service.model.AnaliseInferenciaDTO;
import com.ia.core.owl.service.model.axioma.AxiomaDTO;
import com.ia.core.owl.service.model.axioma.HasAxiomas;

/**
 * Interface para serviços de gerenciamento de ontologias OWL.
 * <p>
 * Define operações para criação, manipulação, salvamento e análise de ontologias
 * OWL, incluindo integração com serviços de parsing e raciocínio.
 * </p>
 *
 * @author Israel Araújo
 */
public interface OWLOntologyManagementService {

  /**
   * Analisa um hasAxiomas completo: verifica consistência e realiza inferências.
   *
   * @return resultado da análise de inferências
   */
  AnaliseInferenciaDTO checkInferrences();

  /**
   * Retorna a URI da ontologia.
   *
   * @return URI da ontologia
   */
  String getUri();

  /**
   * Retorna o prefixo da ontologia.
   *
   * @return prefixo da ontologia
   */
  String getPrefix();

  /**
   * Retorna a versão da ontologia.
   *
   * @return versão da ontologia
   */
  String getVersion();

  /**
   * Retorna o gerenciador de ontologia.
   *
   * @return OWLOntologyManager
   */
  OWLOntologyManager getManager();

  /**
   * Retorna a ontologia atual.
   *
   * @return a ontologia OWL
   */
  OWLOntology getOntology();

  /**
   * Retorna a fábrica de dados OWL.
   *
   * @return OWLDataFactory
   */
  OWLDataFactory getDataFactory();

  /**
   * Retorna o gerenciador de prefixos.
   *
   * @return PrefixManager
   */
  PrefixManager getPrefixManager();

  /**
   * Retorna o serviço de raciocínio OWL.
   *
   * @return OWLReasoningService
   */
  OWLReasoningService getReasoningService();

  /**
   * Adiciona axiomas à ontologia atual.
   *
   * @param hasAxiomas objeto contendo os axiomas
   * @throws OWLParserException se ocorrer erro no parsing
   * @throws OWLOntologyCreationException se ocorrer erro na criação
   */
  void addAxioms(HasAxiomas hasAxiomas)
    throws OWLParserException, OWLOntologyCreationException;

  /**
   * Adiciona um axioma à ontologia atual.
   *
   * @param hasAxiomas objeto contendo os axiomas
   * @param axioma o axioma a ser adicionado
   */
  void addAxiom(HasAxiomas hasAxiomas, AxiomaDTO axioma);

  /**
   * Cria um axioma DTO.
   *
   * @param expressao a expressão Manchester
   * @return o AxiomaDTO criado
   */
  AxiomaDTO criarAxioma(String expressao);

  /**
   * Cria um formato de documento OWL.
   *
   * @return OWLDocumentFormat
   */
  OWLDocumentFormat createDocumentFormat();
}
