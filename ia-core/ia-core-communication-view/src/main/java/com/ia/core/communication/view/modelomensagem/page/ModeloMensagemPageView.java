package com.ia.core.communication.view.modelomensagem.page;

import java.util.Collection;
import java.util.List;

import com.ia.core.communication.view.modelomensagem.form.ModeloMensagemFormView;
import com.ia.core.communication.view.modelomensagem.list.ModeloMensagemListView;
import com.ia.core.communication.service.model.modelomensagem.dto.ModeloMensagemDTO;
import com.ia.core.communication.view.modelomensagem.ModeloMensagemManager;
import com.ia.core.view.components.form.IFormView;
import com.ia.core.view.components.form.viewModel.IFormViewModel;
import com.ia.core.view.components.list.IListView;
import com.ia.core.view.components.list.viewModel.IListViewModel;
import com.ia.core.view.components.page.PageView;
import com.ia.core.view.components.page.viewModel.IPageViewModel.PageAction;

public class ModeloMensagemPageView extends PageView<ModeloMensagemDTO> {
  public static final String ROUTE = "modelo-mensagem";

  public ModeloMensagemPageView(ModeloMensagemPageViewModel viewModel) {
    super(viewModel);
  }

  @Override
  public IFormView<ModeloMensagemDTO> createFormView(IFormViewModel<ModeloMensagemDTO> formViewModel) {
    return new ModeloMensagemFormView(formViewModel);
  }

  @Override
  public IListView<ModeloMensagemDTO> createListView(IListViewModel<ModeloMensagemDTO> listViewModel) {
    return new ModeloMensagemListView(listViewModel);
  }

  @Override
  public Collection<PageAction<ModeloMensagemDTO>> createDefaultPageActions() {
    List<PageAction<ModeloMensagemDTO>> actions = new java.util.ArrayList<>();
    actions.add(createViewAction());
    actions.add(createPrintAction());
    return actions;
  }

  @Override
  public ModeloMensagemPageViewModel getViewModel() {
    return super.getViewModel().cast();
  }

  protected ModeloMensagemManager getModeloMensagemManager() {
    return getViewModel().getConfig().getModeloMensagemManager();
  }
}
