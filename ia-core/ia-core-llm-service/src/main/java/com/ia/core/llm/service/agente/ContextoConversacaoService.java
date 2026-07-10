package com.ia.core.llm.service.agente;

import com.ia.core.llm.model.agente.ContextoConversacao;
import com.ia.core.llm.model.ontologia.Ontologia;
import com.ia.core.llm.service.agente.mapper.ContextoConversacaoMapper;
import com.ia.core.llm.service.agente.mapper.OntologiaMapper;
import com.ia.core.llm.service.config.LlmModuleProperties;
import com.ia.core.llm.service.model.agente.ContextConversacaoDTO;
import com.ia.core.llm.service.model.ontologia.OntologiaDTO;
import com.ia.core.llm.service.repository.ContextoConversacaoRepository;
import com.ia.core.owl.service.DefaultOwlService;
import com.ia.core.owl.service.model.axioma.AxiomaDTO;
import com.ia.core.service.CrudBaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Serviço para gerenciamento de ontologia de contexto de conversação.
 * <p>
 * Extende CrudSecuredBaseService e fornece funcionalidades específicas para
 * gerenciar ontologias de contexto vinculadas a sessões de conversação.
 * Integra funcionalidades de auditoria de interações com IA.
 * <p>
 * Responsabilidades:
 * <ul>
 * <li>Criação de ontologia de contexto vinculada à sessão</li>
 * <li>Atualização dinâmica de ontologia durante conversação</li>
 * <li>Recuperação de ontologia por sessão</li>
 * <li>Garantir privacidade (ontologia não exposta ao usuário via API)</li>
 * <li>Auditoria de interações com IA (integrado de AiInteractionAuditService)</li>
 * </ul>
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@Service
public class ContextoConversacaoService extends CrudBaseService<ContextoConversacao, ContextConversacaoDTO> {

  private final OntologiaMapper ontologiaMapper;
  private final ContextoConversacaoRepository contextoConversacaoRepository;
  private final LlmModuleProperties llmModuleProperties;

  public ContextoConversacaoService(ContextOntologyServiceConfig config,
                                      LlmModuleProperties llmModuleProperties) {
    super(config);
    this.ontologiaMapper = config.getOntologiaMapper();
    this.contextoConversacaoRepository = (ContextoConversacaoRepository) super.getRepository();
    this.llmModuleProperties = llmModuleProperties;
  }

  @Override
  public ContextoConversacaoRepository getRepository() {
    return contextoConversacaoRepository;
  }

  @Override
  public ContextoConversacaoMapper getMapper() {
    return (ContextoConversacaoMapper) super.getMapper();
  }

  @Override
  public ContextOntologyServiceConfig getConfig() {
    return (ContextOntologyServiceConfig) super.getConfig();
  }

  /**
   * Cria uma nova ontologia de contexto para uma sessão de conversação.
   *
   * @param sessionId ID da sessão de conversação
   * @param userId ID do usuário
   * @param dominio Domínio da conversação (ex: biologia, biblioteca, medicina)
   * @return ContextConversacaoDTO criado
   */
  public ContextConversacaoDTO createContextOntology(String sessionId, String userId, String dominio) {
    log.debug("Criando ontologia de contexto: sessionId={}, userId={}, dominio={}", sessionId, userId, dominio);

    // Cria ontologia DTO inicial vazia
    Ontologia ontologia = Ontologia.builder()
        .iri("http://example.com/ontologia/" + sessionId)
        .nome("Ontologia de Contexto: " + dominio)
        .descricao("Ontologia construída incrementalmente durante a conversação")
        .versao("1.0")
        .prefixo("ctx")
        .namespace("http://example.com/ontologia/" + sessionId + "#")
        .formato(com.ia.core.llm.model.ontologia.OntologyFormat.MANCHESTER)
        .conteudo("")
        .consistente(true)
        .dataCriacao(LocalDateTime.now())
        .ultimaModificacao(LocalDateTime.now())
        .build();

    // Cria entidade de contexto
    ContextoConversacao entity = ContextoConversacao.builder()
        .sessionId(sessionId)
        .userId(userId)
        .dominio(dominio)
        .ontologia(ontologia)
        .dataInicio(LocalDateTime.now())
        .ultimaAtividade(LocalDateTime.now())
        .totalAxiomasExtraidos(0)
        .ontologiaConsistente(true)
        .inconsistenciasCorrigidas(0)
        .build();

    ContextoConversacao saved = getRepository().save(entity);
    ContextConversacaoDTO dto = getMapper().toDTO(saved);

    log.debug("Ontologia de contexto criada: sessionId={}", sessionId);
    return dto;
  }

  /**
   * Recupera a ontologia de contexto para uma sessão específica.
   *
   * @param sessionId ID da sessão de conversação
   * @return Optional com o contexto encontrado
   */
  public Optional<ContextConversacaoDTO> getContextOntology(String sessionId) {
    log.debug("Recuperando ontologia de contexto: sessionId={}", sessionId);
    return getRepository().findBySessionId(sessionId)
        .map(getMapper()::toDTO);
  }

  /**
   * Atualiza dinamicamente a ontologia de contexto durante a conversação.
   *
   * @param sessionId ID da sessão de conversação
   * @param manchesterAxiom Axioma em Manchester OWL Syntax
   * @return ContextConversacaoDTO atualizado
   * @throws com.ia.core.owl.service.exception.OWLParserException se ocorrer erro no parsing
   * @throws org.semanticweb.owlapi.model.OWLOntologyCreationException se ocorrer erro na criação
   */
  public ContextConversacaoDTO updateContextOntology(String sessionId, String manchesterAxiom)
    throws com.ia.core.owl.service.exception.OWLParserException,
           org.semanticweb.owlapi.model.OWLOntologyCreationException {
    log.debug("Atualizando ontologia de contexto: sessionId={}, axiom={}", sessionId, manchesterAxiom);
      DefaultOwlService owlService = new DefaultOwlService();
    Optional<ContextoConversacao> optional = getRepository().findBySessionId(sessionId);
    if (optional.isEmpty()) {
      throw new IllegalArgumentException("Contexto não encontrado para sessionId: " + sessionId);
    }

    ContextConversacaoDTO entity = optional.map(getMapper()::toDTO).get();
    OntologiaDTO ontologiaDTO = entity.getOntologia();
    if (ontologiaDTO == null) {
      throw new IllegalStateException("Ontologia not found for context: " + sessionId);
    }
    // Adiciona axioma ao conteúdo da ontologia
    AxiomaDTO axiomaDTO = owlService.addAxiom(ontologiaDTO, manchesterAxiom);
    // The ontology content is already updated by the addAxiom method
    ontologiaDTO.setUltimaModificacao(LocalDateTime.now());

    // Usa DefaultOwlService para verificar consistência

    var analise = owlService.checkInferrences(ontologiaDTO);
    boolean isConsistent = analise.isConsistente();
    ontologiaDTO.setConsistente(isConsistent);

    if (!isConsistent) {
      log.warn("Ontologia inconsistente após adicionar axioma: {}", manchesterAxiom);
    }

    // Atualiza metadados da entidade
    // entity.getOntologia() already returns Ontologia (entity model), so we don't need to convert
    entity.setUltimaAtividade(LocalDateTime.now());
    entity.setTotalAxiomasExtraidos(entity.getTotalAxiomasExtraidos() + 1);
    entity.setOntologiaConsistente(ontologiaDTO.isConsistente());

    if (!ontologiaDTO.isConsistente()) {
      entity.setInconsistenciasCorrigidas(entity.getInconsistenciasCorrigidas() + 1);
    }

    // Convert DTO to entity before saving
    ContextoConversacao entityToSave = getMapper().toModel(entity);
    ContextoConversacao saved = getRepository().save(entityToSave);
    ContextConversacaoDTO dto = getMapper().toDTO(saved);

    log.debug("Ontologia de contexto atualizada: sessionId={}, consistente={}", sessionId, ontologiaDTO.isConsistente());
    return dto;
  }

  /**
   * Remove a ontologia de contexto para uma sessão específica.
   *
   * @param sessionId ID da sessão de conversação
   */
  public void deleteContextOntology(String sessionId) {
    log.debug("Removendo ontologia de contexto: sessionId={}", sessionId);

    // Remove contexto do banco
    getRepository().deleteBySessionId(sessionId);

    log.debug("Ontologia de contexto removida: sessionId={}", sessionId);
  }

  /**
   * Atualiza a ontologia de contexto para uma sessão específica.
   *
   * @param sessionId ID da sessão de conversação
   * @param ontologiaDTO ontologia DTO a ser atualizada
   * @return ContextConversacaoDTO atualizado
   */
  public ContextConversacaoDTO updateOntologyDTO(String sessionId, OntologiaDTO ontologiaDTO) {
    log.debug("Atualizando ontologia DTO: sessionId={}", sessionId);

    Optional<ContextoConversacao> optional = getRepository().findBySessionId(sessionId);
    if (optional.isEmpty()) {
      throw new IllegalArgumentException("Contexto não encontrado para sessionId: " + sessionId);
    }

    ContextoConversacao entity = optional.get();
    entity.setOntologia(ontologiaMapper.toModel(ontologiaDTO));
    entity.setUltimaAtividade(LocalDateTime.now());

    ContextoConversacao saved = getRepository().save(entity);
    ContextConversacaoDTO dto = getMapper().toDTO(saved);

    log.debug("Ontologia DTO atualizada: sessionId={}", sessionId);
    return dto;
  }

  /**
   * Verifica se existe uma ontologia de contexto para uma sessão.
   *
   * @param sessionId ID da sessão de conversação
   * @return true se existe, false caso contrário
   */
  public boolean existsContextOntology(String sessionId) {
    return getRepository().existsBySessionId(sessionId);
  }


}
