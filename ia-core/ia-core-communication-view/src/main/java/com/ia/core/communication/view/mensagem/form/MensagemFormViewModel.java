package com.ia.core.communication.view.mensagem.form;

import com.ia.core.communication.service.model.mensagem.dto.MensagemDTO;
import com.ia.core.communication.service.model.modelomensagem.dto.HasVariavel;
import com.ia.core.communication.service.model.modelomensagem.dto.Variavel;
import com.ia.core.communication.view.mensagem.MensagemManager;
import com.ia.core.view.components.form.viewModel.FormViewModel;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * ViewModel para o formulário de Mensagens.
 *
 * @author Israel Araújo
 */
public class MensagemFormViewModel
  extends FormViewModel<MensagemDTO> {

  public MensagemFormViewModel(MensagemFormViewModelConfig config) {
    super(config);
  }

  @Override
  public void setModel(MensagemDTO model) {
    super.setModel(model);
  }

  @Override
  public MensagemFormViewModelConfig getConfig() {
    return super.getConfig().cast();
  }

  public MensagemManager getMensagemManager() {
    return getConfig().getMensagemManager();
  }

    /**
     * Retorna a lista de variáveis disponíveis para edição de templates.
     * Cada variável contém chave e descrição para exibição na UI.
     *
     * @return lista de VariavelTemplate
     */
    public List<Variavel> getVariaveisDisponiveis() {
        if (getModel() instanceof HasVariavel) {
            HasVariavel hasVariavel = (HasVariavel) getModel();
            return hasVariavel.getVariaveis();
        }
        return Collections.emptyList();
    }
    public String processarVariaveis(String value){
        return getConfig().getProcessadorVariaveis().processar(value, getContextoMensagem());
    }

    @NotNull
    private HashMap<Variavel, Object> getContextoMensagem() {
        return new HashMap<>();
    }
}
