package com.ia.core.llm.view.agenteconversacional.dialog;

import com.ia.core.llm.service.model.agente.ContextConversacaoDTO;
import com.ia.core.llm.service.model.agente.RespostaAgente;
import com.ia.core.llm.view.agenteconversacional.AgenteConversacionalManager;
import com.ia.core.view.components.IViewModel;
import lombok.extern.slf4j.Slf4j;

/**
 * ViewModel para ChatOntologiaDialog.
 * <p>
 * Segue o padrão ADR-008 MVVM com separação View-ViewModel.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
public class ChatOntologiaDialogViewModel implements IViewModel {

  private final AgenteConversacionalManager manager;
  private ContextConversacaoDTO contextoAtual;
  private RespostaAgente ultimaResposta;

  public ChatOntologiaDialogViewModel(AgenteConversacionalManager manager) {
    this.manager = manager;
  }

  public void criarNovaSessao(String userId, String dominio) {
    // Na implementação completa, chamaria o serviço REST
  }

  public void enviarMensagem(String mensagem) {
    // Na implementação completa, chamaria o serviço REST
  }

  public ContextConversacaoDTO getContextoAtual() {
    return contextoAtual;
  }

  public RespostaAgente getUltimaResposta() {
    return ultimaResposta;
  }

  @Override
  public boolean isReadOnly() {
    return false;
  }

  @Override
  public void setReadOnly(boolean readOnly) {
    // Implementação opcional
  }
}
