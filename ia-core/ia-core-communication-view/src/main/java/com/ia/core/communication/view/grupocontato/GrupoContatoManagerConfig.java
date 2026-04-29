package com.ia.core.communication.view.grupocontato;

import com.ia.core.communication.service.model.grupocontato.dto.GrupoContatoDTO;
import com.ia.core.security.service.model.authorization.CoreSecurityAuthorizationManager;
import com.ia.core.security.view.manager.DefaultSecuredViewBaseMangerConfig;
import org.springframework.stereotype.Component;

/**
 * Configuração do GrupoContatoManager.
 * <p>
 * Fornece as dependências necessárias para o manager, incluindo o cliente
 * Feign e o gerenciador de autorizações.
 *
 * @author Israel Araújo
 * @see GrupoContatoManager
 */

@Component
public class GrupoContatoManagerConfig
  extends DefaultSecuredViewBaseMangerConfig<GrupoContatoDTO> {

  private final GrupoContatoClient client;

  /**
   * Construtor com dependências.
   *
   * @param client              Cliente Feign para operações de GrupoContato
   * @param authorizationManager Gerenciador de autorizações de segurança
   */
  public GrupoContatoManagerConfig(GrupoContatoClient client,
                                   CoreSecurityAuthorizationManager authorizationManager) {
    super(client, authorizationManager);
    this.client = client;
  }

  @Override
  public GrupoContatoClient getClient() {
    return client;
  }
}
