package com.ia.core.communication.view.modelomensagem.page;

import com.ia.core.communication.service.model.grupocontato.dto.GrupoContatoDTO;
import com.ia.core.communication.service.model.contatomensagem.dto.ContatoMensagemDTO;
import com.ia.core.communication.service.model.modelomensagem.dto.ModeloMensagemDTO;
import com.ia.core.communication.view.contatomensagem.ContatoMensagemManager;
import com.ia.core.communication.view.grupocontato.GrupoContatoManager;
import com.ia.core.communication.view.modelomensagem.ModeloMensagemManager;
import com.ia.core.communication.view.modelomensagem.form.ModeloMensagemFormView;
import com.ia.core.communication.view.modelomensagem.list.ModeloMensagemListView;
import com.ia.core.view.components.form.IFormView;
import com.ia.core.view.components.form.viewModel.IFormViewModel;
import com.ia.core.view.components.list.IListView;
import com.ia.core.view.components.list.viewModel.IListViewModel;
import com.ia.core.view.components.page.PageView;
import com.ia.core.view.components.page.viewModel.IPageViewModel.PageAction;
import com.ia.core.view.utils.DataProviderFactory;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.icon.VaadinIcon;

import java.util.Collection;
import java.util.List;
/**
 * Classe que representa a interface visual para modelo mensagem page.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a ModeloMensagemPageView
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
 */

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
    List<PageAction<ModeloMensagemDTO>> actions = new java.util.ArrayList<>(super.createDefaultPageActions());
    actions.add(createEnviarParaGrupoAction());
    actions.add(createEnviarParaContatoAction());
    return actions;
  }

  /**
   * Cria a action para enviar modelo de mensagem para um grupo
   *
   * @return PageAction para envio a grupo
   */
  public PageAction<ModeloMensagemDTO> createEnviarParaGrupoAction() {
    return PageAction.<ModeloMensagemDTO> builder()
        .icon(VaadinIcon.USERS)
        .enableFunction(this::canEnviarParaGrupo)
        .action(() -> selecionarGrupoParaEnvio(getViewModel().getSelected()))
        .build();
  }

  /**
   * Cria a action para enviar modelo de mensagem para um contato individual
   *
   * @return PageAction para envio a contato
   */
  public PageAction<ModeloMensagemDTO> createEnviarParaContatoAction() {
    return PageAction.<ModeloMensagemDTO> builder()
        .icon(VaadinIcon.PAPERPLANE)
        .enableFunction(this::canEnviarParaContato)
        .action(() -> selecionarContatoParaEnvio(getViewModel().getSelected()))
        .build();
  }

  /**
   * Verifica se é possível enviar para um grupo
   *
   * @param modelo o modelo de mensagem
   * @return true se pode enviar
   */
  protected boolean canEnviarParaGrupo(ModeloMensagemDTO modelo) {
    return modelo != null && Boolean.TRUE.equals(modelo.getAtivo());
  }

  /**
   * Verifica se é possível enviar para um contato
   *
   * @param modelo o modelo de mensagem
   * @return true se pode enviar
   */
  protected boolean canEnviarParaContato(ModeloMensagemDTO modelo) {
    return modelo != null && Boolean.TRUE.equals(modelo.getAtivo());
  }

  /**
   * Abre um diálogo para selecionar o grupo de destino e enviar o modelo de mensagem
   *
   * @param modelo o modelo de mensagem a ser enviado
   */
  protected void selecionarGrupoParaEnvio(ModeloMensagemDTO modelo) {
    if (modelo == null) {
      return;
    }

    GrupoContatoManager grupoContatoManager = getGrupoContatoManager();

    ComboBox<GrupoContatoDTO> grupoComboBox = new ComboBox<>();
    grupoComboBox.setLabel($(getGrupoContatoManager().getFunctionalityTypeName() + ".nome"));
    grupoComboBox.setPlaceholder($(getGrupoContatoManager().getFunctionalityTypeName() + ".selecione"));
    grupoComboBox.setItems(DataProviderFactory.createBaseDataProviderFromManager(grupoContatoManager, GrupoContatoDTO.propertyFilters()));
    grupoComboBox.setItemLabelGenerator(GrupoContatoDTO::getNome);
    grupoComboBox.setWidthFull();

    ConfirmDialog dialog = new ConfirmDialog();
    dialog.setHeader($(getGrupoContatoManager().getFunctionalityTypeName() + ".selecionar"));
    dialog.setText(grupoComboBox);
    dialog.setConfirmText($(getGrupoContatoManager().getFunctionalityTypeName() + ".enviar"));
    dialog.setCancelText($("Cancelar"));

    dialog.addConfirmListener(event -> {
      GrupoContatoDTO grupoSelecionado = grupoComboBox.getValue();
      if (grupoSelecionado != null) {
        enviarParaGrupo(modelo, grupoSelecionado);
      }
    });

    dialog.open();
  }

  /**
   * Abre um diálogo para selecionar o contato de destino e enviar o modelo de mensagem
   *
   * @param modelo o modelo de mensagem a ser enviado
   */
  protected void selecionarContatoParaEnvio(ModeloMensagemDTO modelo) {
    if (modelo == null) {
      return;
    }

    ContatoMensagemManager contatoMensagemManager = getContatoMensagemManager();

    ComboBox<ContatoMensagemDTO> contatoComboBox = new ComboBox<>();
    contatoComboBox.setLabel($(contatoMensagemManager.getFunctionalityTypeName() + ".nome"));
    contatoComboBox.setPlaceholder($(contatoMensagemManager.getFunctionalityTypeName() + ".selecione"));
    contatoComboBox.setItems(DataProviderFactory.createBaseDataProviderFromManager(contatoMensagemManager,ContatoMensagemDTO.propertyFilters()));
    contatoComboBox.setItemLabelGenerator(ContatoMensagemDTO::getNome);
    contatoComboBox.setWidthFull();

    ConfirmDialog dialog = new ConfirmDialog();
    dialog.setHeader($(contatoMensagemManager.getFunctionalityTypeName() + ".selecionar"));
    dialog.setText(contatoComboBox);
    dialog.setConfirmText($(contatoMensagemManager.getFunctionalityTypeName() + ".enviar"));
    dialog.setCancelText($("Cancelar"));

    dialog.addConfirmListener(event -> {
      ContatoMensagemDTO contatoSelecionado = contatoComboBox.getValue();
      if (contatoSelecionado != null) {
        enviarParaContato(modelo, contatoSelecionado);
      }
    });

    dialog.open();
  }

  /**
   * Executa o envio para um grupo
   *
   * @param modelo o modelo de mensagem a ser enviado
   * @param grupo o grupo de destino
   */
  protected void enviarParaGrupo(ModeloMensagemDTO modelo, GrupoContatoDTO grupo) {
    if (modelo != null && grupo != null) {
      getModeloMensagemManager().enviarParaGrupo(modelo.getId(), grupo.getId());
      refreshAll();
    }
  }

  /**
   * Executa o envio para um contato individual
   *
   * @param modelo o modelo de mensagem a ser enviado
   * @param contato o contato de destino
   */
  protected void enviarParaContato(ModeloMensagemDTO modelo, ContatoMensagemDTO contato) {
    if (modelo != null && contato != null) {
      getModeloMensagemManager().enviarParaContato(modelo.getId(), contato.getId());
      refreshAll();
    }
  }

  @Override
  public ModeloMensagemPageViewModel getViewModel() {
    return super.getViewModel().cast();
  }

  protected ModeloMensagemManager getModeloMensagemManager() {
    return getViewModel().getConfig().getModeloMensagemManager();
  }

  protected GrupoContatoManager getGrupoContatoManager() {
    return getViewModel().getConfig().getGrupoContatoManager();
  }

  protected ContatoMensagemManager getContatoMensagemManager() {
    return getViewModel().getConfig().getContatoMensagemManager();
  }
}

