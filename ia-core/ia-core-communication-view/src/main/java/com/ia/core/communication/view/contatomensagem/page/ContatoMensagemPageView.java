package com.ia.core.communication.view.contatomensagem.page;

import com.ia.core.communication.service.model.contatomensagem.dto.ContatoMensagemDTO;
import com.ia.core.communication.view.contatomensagem.ContatoMensagemManager;
import com.ia.core.communication.view.contatomensagem.form.ContatoMensagemFormView;
import com.ia.core.communication.view.contatomensagem.list.ContatoMensagemListView;
import com.ia.core.view.components.form.IFormView;
import com.ia.core.view.components.form.viewModel.IFormViewModel;
import com.ia.core.view.components.list.IListView;
import com.ia.core.view.components.list.viewModel.IListViewModel;
import com.ia.core.view.components.page.PageView;
import com.ia.core.view.components.page.viewModel.IPageViewModel.PageAction;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Página principal para visualização de ContatoMensagem.
 *
 * @author Israel Araújo
 */
public class ContatoMensagemPageView
  extends PageView<ContatoMensagemDTO> {
  /** Rota */
  public static final String ROUTE = "contato-mensagem";

  public ContatoMensagemPageView(ContatoMensagemPageViewModel viewModel) {
    super(viewModel);
  }

  @Override
  public IFormView<ContatoMensagemDTO> createFormView(IFormViewModel<ContatoMensagemDTO> formViewModel) {
    return new ContatoMensagemFormView(formViewModel);
  }

  @Override
  public IListView<ContatoMensagemDTO> createListView(IListViewModel<ContatoMensagemDTO> listViewModel) {
    return new ContatoMensagemListView(listViewModel);
  }

  @Override
  public Collection<PageAction<ContatoMensagemDTO>> createDefaultPageActions() {
    List<PageAction<ContatoMensagemDTO>> actions = new ArrayList<>();
    actions.add(createViewAction());
    return actions;
  }

  @Override
  public ContatoMensagemPageViewModel getViewModel() {
    return super.getViewModel().cast();
  }

  protected ContatoMensagemManager getContatoMensagemManager() {
    return getViewModel().getConfig().getContatoMensagemManager();
  }
}
