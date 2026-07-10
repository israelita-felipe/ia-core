package com.ia.core.owl.service.tool.base;

import com.ia.core.llm.service.agente.ContextoConversacaoService;
import com.ia.core.llm.service.model.agente.ContextConversacaoDTO;
import com.ia.core.llm.service.model.ontologia.OntologiaDTO;
import com.ia.core.owl.service.DefaultOwlService;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * Classe base abstrata para tools OWL 2 DL com gerenciamento de sessão.
 * <p>
 * Implementa a interface OwlToolBase fornecendo funcionalidade comum
 * para gerenciamento de ontologia por sessão, validação de consistência
 * e detecção de inconsistências.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
public abstract class AbstractOwlToolBase implements OwlToolBase {

  protected final ContextoConversacaoService contextoConversacaoService;

  public AbstractOwlToolBase(ContextoConversacaoService contextoConversacaoService) {
    this.contextoConversacaoService = contextoConversacaoService;
  }

  /**
   * Obtém a ontologia DTO para uma sessão específica.
   *
   * @param sessionId identificador da sessão
   * @return ontologia DTO para a sessão
   */
  @Override
  public OntologiaDTO getOntologyForSession(String sessionId) {
    return contextoConversacaoService.getContextOntology(sessionId)
        .map(ContextConversacaoDTO::getOntologia)
        .orElseThrow(() -> new IllegalArgumentException("Session not found: " + sessionId));
  }

  /**
   * Salva a ontologia DTO para uma sessão específica.
   *
   * @param sessionId identificador da sessão
   * @param ontologiaDTO ontologia DTO a ser salva
   */
  @Override
  public void saveOntologyForSession(String sessionId, OntologiaDTO ontologiaDTO) {
    contextoConversacaoService.updateOntologyDTO(sessionId, ontologiaDTO);
    log.debug("Ontologia salva para sessão: {}", sessionId);
  }

  /**
   * Valida a consistência da ontologia usando o OWLService.
   *
   * @param ontologiaDTO ontologia DTO a ser validada
   * @return true se a ontologia for consistente, false caso contrário
   */
  @Override
  public boolean validateOntology(OntologiaDTO ontologiaDTO) {
    try {
      DefaultOwlService owlService = new DefaultOwlService();
      return owlService.checkInferrences(ontologiaDTO).isConsistente();
    } catch (Exception e) {
      log.error("Erro ao validar consistência da ontologia", e);
      return false;
    }
  }

  /**
   * Detecta inconsistências na ontologia.
   *
   * @param ontologiaDTO ontologia DTO a ser analisada
   * @return lista de descrições de inconsistências
   */
  protected List<String> detectInconsistencies(OntologiaDTO ontologiaDTO) {
    try {
      DefaultOwlService owlService = new DefaultOwlService();
      var analise = owlService.checkInferrences(ontologiaDTO);
      if (!analise.isConsistente()) {
        return analise.getInconsistencias();
      }
      return new java.util.ArrayList<>();
    } catch (Exception e) {
      log.error("Erro ao detectar inconsistências", e);
      return java.util.List.of("Erro na validação: " + e.getMessage());
    }
  }
}
