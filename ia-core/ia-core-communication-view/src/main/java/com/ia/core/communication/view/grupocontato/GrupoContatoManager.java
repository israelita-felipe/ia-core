package com.ia.core.communication.view.grupocontato;

import org.springframework.stereotype.Service;

import com.ia.core.communication.service.model.grupocontato.GrupoContatoUseCase;
import com.ia.core.communication.service.model.grupocontato.dto.GrupoContatoDTO;
import com.ia.core.communication.service.model.grupocontato.dto.GrupoContatoTranslator;
import com.ia.core.security.view.manager.DefaultSecuredViewBaseManager;

/**
 * Manager para operações de GrupoContato.
 * <p>
 * Implementa o caso de uso para gerenciamento de grupos de contatos na camada
 * de visualização. Atua como proxy para as operações do serviço, delegando
 * chamadas ao cliente Feign.
 *
 * @author Israel Araújo
 * @see GrupoContatoUseCase
 */
@Service
public class GrupoContatoManager
  extends DefaultSecuredViewBaseManager<GrupoContatoDTO>
  implements GrupoContatoUseCase {

  public GrupoContatoManager(GrupoContatoManagerConfig config) {
    super(config);
  }

  @Override
  public GrupoContatoManagerConfig getConfig() {
    return (GrupoContatoManagerConfig) super.getConfig();
  }

  @Override
  public GrupoContatoClient getClient() {
    return getConfig().getClient();
  }

  @Override
  public String getFunctionalityTypeName() {
    return GrupoContatoTranslator.GRUPO_CONTATO;
  }

}
