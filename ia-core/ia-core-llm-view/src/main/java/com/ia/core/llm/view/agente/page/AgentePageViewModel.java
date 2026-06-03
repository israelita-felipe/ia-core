package com.ia.core.llm.view.agente.page;

import com.ia.core.llm.service.model.agente.AgenteDTO;
import com.ia.core.llm.view.agente.form.AgenteFormViewModel;
import com.ia.core.security.view.log.operation.page.EntityPageViewModel;
import com.ia.core.service.dto.request.SearchRequestDTO;
import com.ia.core.view.components.form.viewModel.FormViewModelConfig;
import com.ia.core.view.components.form.viewModel.IFormViewModel;
import lombok.extern.slf4j.Slf4j;

/**
 * Model de dados para a view de agentes page.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a AgentePageViewModel
 * dentro do sistema.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
public class AgentePageViewModel
  extends EntityPageViewModel<AgenteDTO> {

  /**
   * @param config configuração do view model
   */
  public AgentePageViewModel(AgentePageViewModelConfig config) {
    super(config);
    log.debug("AgentePageViewModel inicializado");
  }

  @Override
  public AgenteDTO cloneObject(AgenteDTO object) {
    log.debug("Clonando AgenteDTO: identificador={}", object.getIdentificador());
    return object.cloneObject();
  }

  @Override
  public IFormViewModel<AgenteDTO> createFormViewModel(FormViewModelConfig<AgenteDTO> config) {
    log.debug("Criando AgenteFormViewModel");
    return new AgenteFormViewModel(config.cast());
  }

  @Override
  public AgentePageViewModelConfig getConfig() {
    return (AgentePageViewModelConfig) super.getConfig();
  }

  @Override
  public AgenteDTO createNewObject() {
    log.debug("Criando novo AgenteDTO");
    return AgenteDTO.builder().build();
  }

  @Override
  protected SearchRequestDTO createSearchRequest() {
    log.debug("Criando SearchRequestDTO para Agente");
    return AgenteDTO.getSearchRequest();
  }

  @Override
  public Long getId(AgenteDTO object) {
    return object.getId();
  }

  @Override
  public Class<AgenteDTO> getType() {
    return AgenteDTO.class;
  }
}
