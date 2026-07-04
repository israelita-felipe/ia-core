package com.ia.core.owl.service.tool.base;

import com.ia.core.llm.model.ontologia.OntologyFormat;
import com.ia.core.llm.service.agente.ContextoConversacaoService;
import com.ia.core.llm.service.model.ontologia.OntologiaDTO;
import com.ia.core.owl.service.DefaultOwlService;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * Classe base abstrata para todas as tools OWL 2 DL.
 * <p>
 * Todas as tools OWL (OwlClassCreatorTool, OwlPropertyCreatorTool, SubClassOfTool, etc.)
 * devem estender esta classe, que por sua vez estende AbstractOwlToolBase.
 * <p>
 * Esta classe fornece funcionalidade comum para criação de axiomas em Manchester OWL Syntax
 * e validação automática de consistência após cada operação.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
public abstract class OwlConstructorTool extends AbstractOwlToolBase {

  public OwlConstructorTool(ContextoConversacaoService contextoConversacaoService) {
    super(contextoConversacaoService);
  }

  /**
   * Cria um axioma na ontologia da sessão usando Manchester OWL Syntax.
   * <p>
   * Este método template adiciona o axioma, valida a consistência da ontologia,
   * e retorna feedback ao usuário caso haja inconsistências.
   * <p>
   * Subclasses podem chamar este método após processar seus parâmetros específicos.
   *
   * @param sessionId identificador da sessão
   * @param manchesterAxiom axioma em Manchester OWL Syntax
   * @return resultado da operação com feedback sobre consistência
   */
  protected String createAxiom(String sessionId, String manchesterAxiom) {
    DefaultOwlService owlService = new DefaultOwlService();

    // Get current ontology DTO
    OntologiaDTO ontologiaDTO = getOntologyForSession(sessionId);

    // Add axiom using the proper method
    try {
      owlService.addAxiom(ontologiaDTO, manchesterAxiom);
    } catch (Exception e) {
      log.error("Failed to add axiom: {}", e.getMessage(), e);
      return "Erro ao adicionar axioma: " + e.getMessage();
    }

    // Save updated ontology
    saveOntologyForSession(sessionId, ontologiaDTO);

    if (!validateOntology(ontologiaDTO)) {
      List<String> inconsistencies = detectInconsistencies(ontologiaDTO);
      return "Axioma criado: " + manchesterAxiom + "\n" +
             "\nALERTA: Ontologia inconsistente. Inconsistências detectadas:\n" +
             String.join("\n", inconsistencies) +
             "\n\nO agente deve iniciar diálogo com o usuário para refinar os conceitos.";
    }

    return "Axioma criado: " + manchesterAxiom;
  }

  /**
   * Cria um axioma em uma ontologia específica usando Manchester OWL Syntax.
   * <p>
   * Este método usa o novo approach stateless onde a ontologia é especificada
   * por formato, conteúdo, prefixo e URI.
   * <p>
   * Este método template adiciona o axioma, valida a consistência da ontologia,
   * e retorna feedback ao usuário caso haja inconsistências.
   *
   * @param format formato da ontologia (e.g., "MANCHESTER", "RDF/XML", "TURTLE")
   * @param content conteúdo da ontologia
   * @param prefix prefixo da ontologia
   * @param uri IRI da ontologia
   * @param manchesterAxiom axioma em Manchester OWL Syntax
   * @return resultado da operação com feedback sobre consistência
   */
  protected String createAxiomWithOntology(String format, String content, String prefix, String uri, String manchesterAxiom) {
    DefaultOwlService owlService = new DefaultOwlService();

    // Create OntologiaDTO
    OntologiaDTO ontologiaDTO = OntologiaDTO.builder()
        .conteudo(content)
        .formato(OntologyFormat.valueOf(format))
        .iri(uri)
        .prefixo(prefix)
        .dataCriacao(java.time.LocalDateTime.now())
        .ultimaModificacao(java.time.LocalDateTime.now())
        .build();

    // Add axiom using the proper method
    try {
      owlService.addAxiom(ontologiaDTO, manchesterAxiom);
    } catch (Exception e) {
      log.error("Failed to add axiom: {}", e.getMessage(), e);
      return "Erro ao adicionar axioma: " + e.getMessage();
    }

    if (!validateOntology(ontologiaDTO)) {
      List<String> inconsistencies = detectInconsistencies(ontologiaDTO);
      return "Axioma criado: " + manchesterAxiom + "\n" +
             "\nALERTA: Ontologia inconsistente. Inconsistências detectadas:\n" +
             String.join("\n", inconsistencies) +
             "\n\nO agente deve iniciar diálogo com o usuário para refinar os conceitos.";
    }

    return "Axioma criado: " + manchesterAxiom;
  }
}
