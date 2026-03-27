package com.ia.core.communication.view.modelomensagem;

import org.springframework.stereotype.Component;

import com.ia.core.communication.service.model.modelomensagem.dto.ModeloMensagemDTO;
import com.ia.core.security.service.model.authorization.CoreSecurityAuthorizationManager;
import com.ia.core.security.view.manager.DefaultSecuredViewBaseMangerConfig;

/**
 * Configuração do ModeloMensagemManager.
 * <p>
 * Fornece as dependências necessárias para o manager, incluindo o cliente
 * Feign e o gerenciador de autorizações.
 *
 * @author Israel Araújo
 * @see ModeloMensagemManager
 */
@Component
public class ModeloMensagemManagerConfig
  extends DefaultSecuredViewBaseMangerConfig<ModeloMensagemDTO> {

  /**
   * Construtor com dependências.
   *
   * @param client              Cliente Feign para operações de ModeloMensagem
   * @param authorizationManager Gerenciador de autorizações de segurança
   */
  public ModeloMensagemManagerConfig(ModeloMensagemClient client,
                                     CoreSecurityAuthorizationManager authorizationManager) {
    super(client, authorizationManager);
  }

  @Override
  public ModeloMensagemClient getClient() {
    return (ModeloMensagemClient) super.getClient();
  }
}
