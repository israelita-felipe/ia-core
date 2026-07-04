package com.ia.core.owl.service.tool.base;

import com.ia.core.llm.service.model.ontologia.OntologiaDTO;

/**
 * Interface base para tools OWL 2 DL com gerenciamento de sessão.
 * <p>
 * Fornece métodos comuns para gerenciamento de ontologia por sessão usando apenas OntologiaDTO,
 * encapsulando completamente a OWL API.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
public interface OwlToolBase {

  /**
   * Obtém a ontologia DTO para uma sessão específica.
   *
   * @param sessionId identificador da sessão
   * @return ontologia DTO para a sessão
   */
  OntologiaDTO getOntologyForSession(String sessionId);

  /**
   * Salva a ontologia DTO para uma sessão específica.
   *
   * @param sessionId identificador da sessão
   * @param ontologiaDTO ontologia DTO a ser salva
   */
  void saveOntologyForSession(String sessionId, OntologiaDTO ontologiaDTO);

  /**
   * Valida a consistência da ontologia.
   *
   * @param ontologiaDTO ontologia DTO a ser validada
   * @return true se a ontologia for consistente, false caso contrário
   */
  boolean validateOntology(OntologiaDTO ontologiaDTO);
}
