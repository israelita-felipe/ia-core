package com.ia.core.communication.view.grupocontato.page;

import com.ia.core.communication.service.model.grupocontato.dto.GrupoContatoDTO;
import com.ia.core.communication.view.grupocontato.GrupoContatoManager;
import com.ia.core.communication.view.grupocontato.form.GrupoContatoFormViewModelConfig;
import com.ia.core.view.components.form.viewModel.FormViewModelConfig;
import com.ia.core.view.components.page.viewModel.PageViewModelConfig;
import com.ia.core.view.manager.DefaultBaseManager;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.stereotype.Component;
/**
 * Configuração do ViewModel para a página de GrupoContato.
 * <p>
 * Responsável por fornecer as dependências necessárias para o ViewModel da página,
 * incluindo o GrupoContatoManager.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */

@UIScope
@Component
public class GrupoContatoPageViewModelConfig extends PageViewModelConfig<GrupoContatoDTO> {

  private final GrupoContatoManager grupoContatoManager;

  public GrupoContatoPageViewModelConfig(DefaultBaseManager<GrupoContatoDTO> service, GrupoContatoManager grupoContatoManager) {
    super(service);
    this.grupoContatoManager = grupoContatoManager;
  }

  @Override
  protected FormViewModelConfig<GrupoContatoDTO> createFormViewModelConfig(boolean readOnly) {
    return new GrupoContatoFormViewModelConfig(readOnly, grupoContatoManager);
  }

  public GrupoContatoManager getGrupoContatoManager() {
    return grupoContatoManager;
  }
}
