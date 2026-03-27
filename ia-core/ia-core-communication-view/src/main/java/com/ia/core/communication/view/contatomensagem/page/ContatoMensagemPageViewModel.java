package com.ia.core.communication.view.contatomensagem.page;

import com.ia.core.communication.service.model.contatomensagem.dto.ContatoMensagemDTO;
import com.ia.core.communication.view.contatomensagem.form.ContatoMensagemFormViewModel;
import com.ia.core.communication.view.contatomensagem.form.ContatoMensagemFormViewModelConfig;
import com.ia.core.service.dto.request.SearchRequestDTO;
import com.ia.core.view.components.form.viewModel.FormViewModelConfig;
import com.ia.core.view.components.form.viewModel.IFormViewModel;
import com.ia.core.view.components.page.viewModel.PageViewModel;

/**
 * ViewModel para a página de ContatoMensagem.
 *
 * @author Israel Araújo
 */
public class ContatoMensagemPageViewModel
  extends PageViewModel<ContatoMensagemDTO> {

  public ContatoMensagemPageViewModel(ContatoMensagemPageViewModelConfig config) {
    super(config);
  }

  @Override
  public Long getId(ContatoMensagemDTO object) {
    return object.getId();
  }

  @Override
  public ContatoMensagemDTO createNewObject() {
    return ContatoMensagemDTO.builder().build();
  }

  @Override
  protected SearchRequestDTO createSearchRequest() {
    return ContatoMensagemDTO.getSearchRequest();
  }

  @Override
  public Class<ContatoMensagemDTO> getType() {
    return ContatoMensagemDTO.class;
  }

  @Override
  public IFormViewModel<ContatoMensagemDTO> createFormViewModel(FormViewModelConfig<ContatoMensagemDTO> config) {
    return new ContatoMensagemFormViewModel((ContatoMensagemFormViewModelConfig) config.cast());
  }

  @Override
  public ContatoMensagemDTO cloneObject(ContatoMensagemDTO object) {
    return object != null ? object.cloneObject() : null;
  }

  @Override
  public ContatoMensagemDTO copyObject(ContatoMensagemDTO object) {
    return object != null ? object.copyObject() : null;
  }

  @Override
  public ContatoMensagemPageViewModelConfig getConfig() {
    return super.getConfig().cast();
  }
}