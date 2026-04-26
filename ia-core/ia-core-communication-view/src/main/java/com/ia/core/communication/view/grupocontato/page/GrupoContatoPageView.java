package com.ia.core.communication.view.grupocontato.page;

import com.ia.core.communication.service.model.grupocontato.dto.GrupoContatoDTO;
import com.ia.core.communication.view.grupocontato.GrupoContatoManager;
import com.ia.core.communication.view.grupocontato.form.GrupoContatoFormView;
import com.ia.core.communication.view.grupocontato.list.GrupoContatoListView;
import com.ia.core.view.components.form.IFormView;
import com.ia.core.view.components.form.viewModel.IFormViewModel;
import com.ia.core.view.components.list.IListView;
import com.ia.core.view.components.list.viewModel.IListViewModel;
import com.ia.core.view.components.page.PageView;
import com.ia.core.view.components.page.viewModel.IPageViewModel.PageAction;

import java.util.Collection;
import java.util.List;

public class GrupoContatoPageView extends PageView<GrupoContatoDTO> {
  public static final String ROUTE = "grupo-contato";

  public GrupoContatoPageView(GrupoContatoPageViewModel viewModel) {
    super(viewModel);
  }

  @Override
  public IFormView<GrupoContatoDTO> createFormView(IFormViewModel<GrupoContatoDTO> formViewModel) {
    return new GrupoContatoFormView(formViewModel);
  }

  @Override
  public IListView<GrupoContatoDTO> createListView(IListViewModel<GrupoContatoDTO> listViewModel) {
    return new GrupoContatoListView(listViewModel);
  }

  @Override
  public Collection<PageAction<GrupoContatoDTO>> createDefaultPageActions() {
    List<PageAction<GrupoContatoDTO>> actions = new java.util.ArrayList<>();
    actions.add(createViewAction());
    actions.add(createPrintAction());
    return actions;
  }

  @Override
  public GrupoContatoPageViewModel getViewModel() {
    return super.getViewModel().cast();
  }

  protected GrupoContatoManager getGrupoContatoManager() {
    return getViewModel().getConfig().getGrupoContatoManager();
  }
}
