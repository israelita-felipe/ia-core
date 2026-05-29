package com.ia.core.communication.view.mensagem;

import com.ia.core.communication.service.model.mensagem.dto.MensagemDTO;
import com.ia.core.security.service.model.authorization.CoreSecurityAuthorizationManager;
import com.ia.core.security.view.manager.DefaultSecuredViewBaseMangerConfig;
import org.springframework.stereotype.Component;

/**
 * Configuração do MensagemManager.
 * <p>
 * Fornece as dependências necessárias para o manager, incluindo o cliente
 * Feign e o gerenciador de autorizações.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */

@Component
public class MensagemManagerConfig
  extends DefaultSecuredViewBaseMangerConfig<MensagemDTO> {

  private final MensagemClient client;

  public MensagemManagerConfig(MensagemClient client,
                               CoreSecurityAuthorizationManager authorizationManager) {
    super(client, authorizationManager);
    this.client = client;
  }

  @Override
  public MensagemClient getClient() {
    return client;
  }
}
