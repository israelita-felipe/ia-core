package com.ia.core.communication.view.modelomensagem.form;

import com.ia.core.communication.model.mensagem.TipoCanal;
import com.ia.core.communication.service.model.modelomensagem.dto.ModeloMensagemDTO;
import com.ia.core.communication.service.model.modelomensagem.dto.ModeloMensagemTranslator;
import com.ia.core.communication.view.mensagem.component.VariableSidebarComponent;
import com.ia.core.view.components.form.FormView;
import com.ia.core.view.components.form.viewModel.IFormViewModel;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;

/**
 * Formulário de Modelo de Mensagem.
 * Segue o padrão de implementação com métodos separados para criação de campos.
 *
 * @author Israel Araújo
 */
public class ModeloMensagemFormView extends FormView<ModeloMensagemDTO> {
  private TextField nomeField;
  private TextField descricaoField;
  private TextArea corpoModeloField;
  private ComboBox<TipoCanal> tipoCanalField;
  private Checkbox ativoField;

  private VariableSidebarComponent variableSidebar;

  /**
   * @param formViewModel ViewModel do formulário
   */
  public ModeloMensagemFormView(IFormViewModel<ModeloMensagemDTO> formViewModel) {
    super(formViewModel);
  }

  @Override
  public void createLayout() {
    super.createLayout();

    // Criar componentes auxiliares
    variableSidebar = new VariableSidebarComponent(getViewModel()::getVariaveisDisponiveis);

    // Adicionar campos do formulário
    bind(ModeloMensagemDTO.CAMPOS.NOME, createNomeField());
    bind(ModeloMensagemDTO.CAMPOS.DESCRICAO, createDescricaoField());
    bind(ModeloMensagemDTO.CAMPOS.CORPO_MODELO, createCorpoModeloField());
    bind(ModeloMensagemDTO.CAMPOS.TIPO_CANAL, createTipoCanalField());
    bind(ModeloMensagemDTO.CAMPOS.ATIVO, createAtivoField());

    // Configurar sidebar como helper do campo de corpo do modelo
    corpoModeloField.setHelperComponent(variableSidebar);

    // Configurar listener para inserção de variáveis
    variableSidebar.setVariableInsertListener(variableKey -> {
        if (corpoModeloField != null) {
            String currentValue = corpoModeloField.getValue();
            String newValue = currentValue + variableKey;
            corpoModeloField.setValue(newValue);
            // Move cursor to the end of the newly added placeholder
            corpoModeloField.focus();
        }
    });
  }

  /**
   * Cria o campo de nome.
   *
   * @return TextField configurado para nome
   */
  protected TextField createNomeField() {
    nomeField = createTextField(
        $(ModeloMensagemTranslator.NOME),
        $(ModeloMensagemTranslator.HELP.NOME));
    add(nomeField, 3);
    return nomeField;
  }

  /**
   * Cria o campo de descrição.
   *
   * @return TextField configurado para descrição
   */
  protected TextField createDescricaoField() {
    descricaoField = createTextField(
        $(ModeloMensagemTranslator.DESCRICAO),
        $(ModeloMensagemTranslator.HELP.DESCRICAO));
    add(descricaoField, 3);
    return descricaoField;
  }

  /**
   * Cria o campo de corpo do modelo.
   *
   * @return TextArea configurado para corpo da mensagem
   */
  protected TextArea createCorpoModeloField() {
    corpoModeloField = createTextArea(
        $(ModeloMensagemTranslator.CORPO_MODELO),
        $(ModeloMensagemTranslator.HELP.CORPO_MODELO));
    add(corpoModeloField, 3);
    return corpoModeloField;
  }

  /**
   * Cria o campo de tipo de canal.
   *
   * @return ComboBox configurado para TipoCanal
   */
  protected ComboBox<TipoCanal> createTipoCanalField() {
    tipoCanalField = createComboBox(
        $(ModeloMensagemTranslator.TIPO_CANAL),
        $(ModeloMensagemTranslator.HELP.TIPO_CANAL));
    tipoCanalField.setItems(TipoCanal.values());
    add(tipoCanalField, 3);
    return tipoCanalField;
  }

  /**
   * Cria o campo de status ativo.
   *
   * @return Checkbox configurado para ativo
   */
  protected Checkbox createAtivoField() {
    ativoField = createCheckBoxField(
        $(ModeloMensagemTranslator.ATIVO),
        null);
    add(ativoField, 3);
    return ativoField;
  }

  @Override
  public ModeloMensagemFormViewModel getViewModel() {
    return super.getViewModel().cast();
  }
}
