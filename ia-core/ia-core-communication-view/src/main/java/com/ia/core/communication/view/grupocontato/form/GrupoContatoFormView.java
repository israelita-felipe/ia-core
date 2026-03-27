package com.ia.core.communication.view.grupocontato.form;

import com.ia.core.communication.service.model.grupocontato.dto.GrupoContatoDTO;
import com.ia.core.communication.service.model.grupocontato.dto.GrupoContatoTranslator;
import com.ia.core.view.components.form.FormView;
import com.ia.core.view.components.form.viewModel.IFormViewModel;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.textfield.TextField;

/**
 * FormView para entidade GrupoContato.
 * Segue o padrão de implementação com métodos separados para criação de campos.
 *
 * @author Israel Araújo
 */
public class GrupoContatoFormView extends FormView<GrupoContatoDTO> {

  private TextField nomeField;
  private TextField descricaoField;
  private Checkbox ativoField;

  /**
   * @param formViewModel ViewModel do formulário
   */
  public GrupoContatoFormView(IFormViewModel<GrupoContatoDTO> formViewModel) {
    super(formViewModel);
  }

  @Override
  public void createLayout() {
    super.createLayout();
    bind(GrupoContatoDTO.CAMPOS.NOME, createNomeField());
    bind(GrupoContatoDTO.CAMPOS.DESCRICAO, createDescricaoField());
    bind(GrupoContatoDTO.CAMPOS.ATIVO, createAtivoField());
  }

  /**
   * Cria o campo de nome.
   *
   * @return TextField configurado para nome
   */
  protected TextField createNomeField() {
    nomeField = createTextField($(GrupoContatoTranslator.NOME),
                               $(GrupoContatoTranslator.HELP.NOME));
    add(nomeField, 3);
    return nomeField;
  }

  /**
   * Cria o campo de descrição.
   *
   * @return TextField configurado para descrição
   */
  protected TextField createDescricaoField() {
    descricaoField = createTextField($(GrupoContatoTranslator.DESCRICAO),
                                    $(GrupoContatoTranslator.HELP.DESCRICAO));
    add(descricaoField, 3);
    return descricaoField;
  }

  /**
   * Cria o campo de ativo.
   *
   * @return Checkbox configurado para ativo
   */
  protected Checkbox createAtivoField() {
    ativoField = createCheckBoxField($(GrupoContatoTranslator.ATIVO), null);
    add(ativoField, 3);
    return ativoField;
  }

  @Override
  public GrupoContatoFormViewModel getViewModel() {
    return super.getViewModel().cast();
  }
}
