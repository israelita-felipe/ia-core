package com.ia.core.communication.view.mensagem.page;

import com.ia.core.communication.service.model.mensagem.dto.MensagemDTO;
import com.ia.core.communication.view.mensagem.MensagemManager;
import com.ia.core.communication.view.mensagem.form.MensagemFormView;
import com.ia.core.communication.view.mensagem.list.MensagemListView;
import com.ia.core.view.components.form.IFormView;
import com.ia.core.view.components.form.viewModel.IFormViewModel;
import com.ia.core.view.components.list.IListView;
import com.ia.core.view.components.list.viewModel.IListViewModel;
import com.ia.core.view.components.page.PageView;
import com.ia.core.view.components.page.viewModel.IPageViewModel.PageAction;
import com.vaadin.flow.component.icon.VaadinIcon;

import java.util.Collection;
import java.util.List;

/**
 * Página principal para visualização de Mensagens.
 *
 * @author Israel Araújo
 */
public class MensagemPageView
  extends PageView<MensagemDTO> {
  /** Rota */
  public static final String ROUTE = "mensagem";

  public MensagemPageView(MensagemPageViewModel viewModel) {
    super(viewModel);
  }

  @Override
  public IFormView<MensagemDTO> createFormView(IFormViewModel<MensagemDTO> formViewModel) {
    return new MensagemFormView(formViewModel);
  }

  @Override
  public IListView<MensagemDTO> createListView(IListViewModel<MensagemDTO> listViewModel) {
    return new MensagemListView(listViewModel);
  }

  @Override
  public Collection<PageAction<MensagemDTO>> createDefaultPageActions() {
    List<PageAction<MensagemDTO>> actions = new java.util.ArrayList<>(super.createDefaultPageActions());
    actions.add(createEnviarMensagemAction());
    return actions;
  }

    @Override
    public boolean isEditButtonVisible() {
        return false;
    }

    @Override
    public boolean isNewButtonVisible() {
        return false;
    }

    public PageAction<MensagemDTO> createEnviarMensagemAction() {
    return PageAction.<MensagemDTO> builder()
        .icon(VaadinIcon.PAPERPLANE)
        .enableFunction(this::canEnviar)
        .action(() -> enviarMensagem(getViewModel().getSelected()))
        .build();
  }

  protected boolean canEnviar(MensagemDTO mensagem) {
    return mensagem != null;
  }

  protected void enviarMensagem(MensagemDTO mensagem) {
    if (mensagem != null) {
      getMensagemManager().enviar(mensagem);
      refreshAll();
    }
  }

  @Override
  public MensagemPageViewModel getViewModel() {
    return super.getViewModel().cast();
  }

  protected MensagemManager getMensagemManager() {
    return getViewModel().getConfig().getMensagemManager();
  }
}
