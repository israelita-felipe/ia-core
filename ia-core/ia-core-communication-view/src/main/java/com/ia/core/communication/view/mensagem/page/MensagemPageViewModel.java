package com.ia.core.communication.view.mensagem.page;


import com.ia.core.communication.service.model.mensagem.dto.MensagemDTO;
import com.ia.core.communication.view.mensagem.form.MensagemFormViewModel;
import com.ia.core.communication.view.mensagem.form.MensagemFormViewModelConfig;
import com.ia.core.service.dto.request.SearchRequestDTO;
import com.ia.core.view.components.form.viewModel.FormViewModelConfig;
import com.ia.core.view.components.form.viewModel.IFormViewModel;
import com.ia.core.view.components.page.viewModel.PageViewModel;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.stereotype.Component;

/**
 * ViewModel para a página de Mensagens.
 *
 * @author Israel Araújo
 */
/**
 * Classe que representa o modelo de dados para a view de mensagem page.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a MensagemPageViewModel
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
 */
@UIScope
@Component
public class MensagemPageViewModel
  extends PageViewModel<MensagemDTO> {

  public MensagemPageViewModel(MensagemPageViewModelConfig config) {
    super(config);
  }

  @Override
  public Long getId(MensagemDTO object) {
    return object.getId();
  }

  @Override
  public MensagemDTO createNewObject() {
    return MensagemDTO.builder().build();
  }

  @Override
  protected SearchRequestDTO createSearchRequest() {
    return MensagemDTO.getSearchRequest();
  }

  @Override
  public Class<MensagemDTO> getType() {
    return MensagemDTO.class;
  }

  @Override
  public IFormViewModel<MensagemDTO> createFormViewModel(FormViewModelConfig<MensagemDTO> config) {
    return new MensagemFormViewModel((MensagemFormViewModelConfig) config.cast());
  }

  @Override
  public MensagemDTO cloneObject(MensagemDTO object) {
    return object != null ? object.cloneObject() : null;
  }

  @Override
  public MensagemDTO copyObject(MensagemDTO object) {
    return object != null ? object.copyObject() : null;
  }

  @Override
  public MensagemPageViewModelConfig getConfig() {
    return super.getConfig().cast();
  }
}
