package com.ia.core.communication.view.mensagem.form;


import com.ia.core.communication.model.mensagem.StatusMensagem;
import com.ia.core.communication.model.mensagem.TipoCanal;
import com.ia.core.communication.service.model.mensagem.dto.MensagemDTO;
import com.ia.core.communication.service.model.mensagem.dto.MensagemTranslator;
import com.ia.core.communication.view.mensagem.component.VariableSidebarComponent;
import com.ia.core.view.components.form.FormView;
import com.ia.core.view.components.form.viewModel.IFormViewModel;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;

/**
 * Formulário de Mensagens.
 * Segue o padrão de implementação com métodos separados para criação de campos.
 *
 * @author Israel Araújo
 */
public class MensagemFormView extends FormView<MensagemDTO> {

  private TextField telefoneDestinatarioField;
  private TextField nomeDestinatarioField;
  private TextArea corpoMensagemField;
  private ComboBox<TipoCanal> tipoCanalField;
  private ComboBox<StatusMensagem> statusField;

  private VariableSidebarComponent variableSidebar;

  /**
   * @param formViewModel ViewModel do formulário
   */
  public MensagemFormView(IFormViewModel<MensagemDTO> formViewModel) {
    super(formViewModel);
  }

  @Override
  public void createLayout() {
    super.createLayout();

    // Criar componentes auxiliares
    variableSidebar = new VariableSidebarComponent(getViewModel()::getVariaveisDisponiveis);

    // Adicionar campos do formulário
    add(createTelefoneDestinatarioField());
    add(createNomeDestinatarioField());
    add(createCorpoMensagemField());
    add(createTipoCanalField());
    add(createStatusField());

    corpoMensagemField.setHelperComponent(variableSidebar);
      // Configurar listener para inserção de variáveis
      variableSidebar.setVariableInsertListener(variableKey -> {
          if (corpoMensagemField != null) {
              String currentValue = corpoMensagemField.getValue();
              String newValue = currentValue + variableKey ;
              corpoMensagemField.setValue(newValue);
              // Move cursor to the end of the newly added placeholder
              corpoMensagemField.focus();
          }
      });


      // Manter os bindings existentes
    bind(MensagemDTO.CAMPOS.TELEFONE_DESTINATARIO, telefoneDestinatarioField);
    bind(MensagemDTO.CAMPOS.NOME_DESTINATARIO, nomeDestinatarioField);
    bind(MensagemDTO.CAMPOS.CORPO_MENSAGEM, corpoMensagemField);
    bind(MensagemDTO.CAMPOS.TIPO_CANAL, tipoCanalField);
    bind(MensagemDTO.CAMPOS.STATUS_MENSAGEM, statusField);
  }

  /**
   * Cria o campo de telefone do destinatário.
   *
   * @return TextField configurado para telefone
   */
  protected TextField createTelefoneDestinatarioField() {
    telefoneDestinatarioField = createTextField(
        $(MensagemTranslator.TELEFONE_DESTINATARIO),
        $(MensagemTranslator.HELP.TELEFONE_DESTINATARIO));
    add(telefoneDestinatarioField, 3);
    return telefoneDestinatarioField;
  }

  /**
   * Cria o campo de nome do destinatário.
   *
   * @return TextField configurado para nome
   */
  protected TextField createNomeDestinatarioField() {
    nomeDestinatarioField = createTextField(
        $(MensagemTranslator.NOME_DESTINATARIO),
        $(MensagemTranslator.HELP.NOME_DESTINATARIO));
    add(nomeDestinatarioField, 3);
    return nomeDestinatarioField;
  }

  /**
   * Cria o campo de corpo da mensagem.
   *
   * @return TextArea configurado para mensagem
   */
  protected TextArea createCorpoMensagemField() {
    corpoMensagemField = createTextArea(
        $(MensagemTranslator.CORPO_MENSAGEM),
        $(MensagemTranslator.HELP.CORPO_MENSAGEM));
    add(corpoMensagemField, 6);
    return corpoMensagemField;
  }

  /**
   * Cria o campo de tipo de canal.
   *
   * @return ComboBox configurado para TipoCanal
   */
  protected ComboBox<TipoCanal> createTipoCanalField() {
    tipoCanalField = createComboBox(
        $(MensagemTranslator.TIPO_CANAL),
        $(MensagemTranslator.HELP.TIPO_CANAL));
    tipoCanalField.setItems(TipoCanal.values());
    add(tipoCanalField, 3);
    return tipoCanalField;
  }

  /**
   * Cria o campo de status da mensagem.
   *
   * @return ComboBox configurado para StatusMensagem
   */
  protected ComboBox<StatusMensagem> createStatusField() {
    statusField = createComboBox(
        $(MensagemTranslator.STATUS_MENSAGEM),
        $(MensagemTranslator.HELP.STATUS_MENSAGEM));
    statusField.setItems(StatusMensagem.values());
    add(statusField, 3);
    return statusField;
  }

  @Override
  public MensagemFormViewModel getViewModel() {
    return super.getViewModel().cast();
  }
}
