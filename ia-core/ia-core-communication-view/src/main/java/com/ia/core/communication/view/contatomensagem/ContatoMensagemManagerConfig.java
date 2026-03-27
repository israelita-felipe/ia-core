package com.ia.core.communication.view.contatomensagem;

import org.springframework.stereotype.Component;

import com.ia.core.communication.service.model.contatomensagem.dto.ContatoMensagemDTO;
import com.ia.core.security.service.model.authorization.CoreSecurityAuthorizationManager;
import com.ia.core.security.view.manager.DefaultSecuredViewBaseMangerConfig;

/**
 * Configuração do ContatoMensagemManager.
 * <p>
 * Fornece as dependências necessárias para o manager, incluindo o cliente
 * Feign e o gerenciador de autorizações.
 *
 * @author Israel Araújo
 * @see ContatoMensagemManager
 */
@Component
public class ContatoMensagemManagerConfig
  extends DefaultSecuredViewBaseMangerConfig<ContatoMensagemDTO> {

  /**
   * Construtor com dependências.
   *
   * @param client              Cliente Feign para operações de ContatoMensagem
   * @param authorizationManager Gerenciador de autorizações de segurança
   */
  public ContatoMensagemManagerConfig(ContatoMensagemClient client,
                                      CoreSecurityAuthorizationManager authorizationManager) {
    super(client, authorizationManager);
  }

  @Override
  public ContatoMensagemClient getClient() {
    return (ContatoMensagemClient) super.getClient();
  }
}
