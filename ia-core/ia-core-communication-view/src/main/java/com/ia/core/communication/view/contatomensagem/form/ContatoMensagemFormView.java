package com.ia.core.communication.view.contatomensagem.form;

import com.ia.core.communication.service.model.contatomensagem.dto.ContatoMensagemDTO;
import com.ia.core.communication.service.model.contatomensagem.dto.ContatoMensagemTranslator;
import com.ia.core.view.components.form.FormView;
import com.ia.core.view.components.form.viewModel.IFormViewModel;
import com.vaadin.flow.component.textfield.TextField;

/**
 * FormView para entidade ContatoMensagem.
 * Segue o padrão de implementação com métodos separados para criação de campos.
 *
 * @author Israel Araújo
 */
public class ContatoMensagemFormView extends FormView<ContatoMensagemDTO> {

  private TextField telefoneField;
  private TextField nomeField;
  private TextField grupoContatoField;

  /**
   * @param formViewModel ViewModel do formulário
   */
  public ContatoMensagemFormView(IFormViewModel<ContatoMensagemDTO> formViewModel) {
    super(formViewModel);
  }

  @Override
  public void createLayout() {
    super.createLayout();
    bind(ContatoMensagemDTO.CAMPOS.TELEFONE, createTelefoneField());
    bind(ContatoMensagemDTO.CAMPOS.NOME, createNomeField());
    bind(ContatoMensagemDTO.CAMPOS.GRUPO_CONTATO, createGrupoContatoField());
  }

  /**
   * Cria o campo de telefone.
   *
   * @return TextField configurado para telefone
   */
  protected TextField createTelefoneField() {
    telefoneField = createTextField($(ContatoMensagemTranslator.TELEFONE),
                                   $(ContatoMensagemTranslator.HELP.TELEFONE));
    add(telefoneField, 3);
    return telefoneField;
  }

  /**
   * Cria o campo de nome.
   *
   * @return TextField configurado para nome
   */
  protected TextField createNomeField() {
    nomeField = createTextField($(ContatoMensagemTranslator.NOME),
                               $(ContatoMensagemTranslator.HELP.NOME));
    add(nomeField, 3);
    return nomeField;
  }

  /**
   * Cria o campo de grupo de contato.
   *
   * @return TextField configurado para grupo de contato
   */
  protected TextField createGrupoContatoField() {
    grupoContatoField = createTextField($(ContatoMensagemTranslator.GRUPO_CONTATO),
                                        $(ContatoMensagemTranslator.HELP.GRUPO_CONTATO));
    add(grupoContatoField, 3);
    return grupoContatoField;
  }

  @Override
  public ContatoMensagemFormViewModel getViewModel() {
    return super.getViewModel().cast();
  }
}
