package com.ia.core.security.view.manager;

import java.io.Serializable;
import java.util.Objects;

import com.ia.core.security.service.model.authorization.ContextManager.ContextDefinition;
import com.ia.core.security.service.model.authorization.CoreSecurityAuthorizationManager;
import com.ia.core.service.dto.DTO;
import com.ia.core.service.dto.entity.AbstractBaseEntityDTO;
import com.ia.core.service.dto.request.SearchRequestDTO;
import com.ia.core.view.client.DefaultBaseClient;
import com.ia.core.view.manager.DefaultBaseManager;

import lombok.extern.slf4j.Slf4j;

/**
 * Classe padrão para criação de um serviço da view com todos os clientes.
 *
 * @author Israel Araújo
 * @param <D> Tipo de dado {@link DTO}
 */
@Slf4j
public abstract class DefaultSecuredViewBaseManager<D extends DTO<? extends Serializable>>
  extends DefaultBaseManager<D>
  implements CountSecuredViewBaseManager<D>, FindSecuredViewBaseManager<D>,
  DeleteSecuredViewBaseManager<D>, ListSecuredViewBaseManager<D>,
  SaveSecuredViewBaseManager<D> {

  /**
   * @param client               Cliente de comunicação
   * @param authorizationManager Gestor de autorizações
   */
  public DefaultSecuredViewBaseManager(DefaultSecuredViewBaseMangerConfig<D> config) {
    super(config);
    createContext();
  }

  /**
   * Cria os contextos necessários. Por padrão não há ação sendo executada.
   */
  @Override
  public void createContext() {
    try {
      if (AbstractBaseEntityDTO.class
          .isAssignableFrom(extractGenericType())) {
        addContext(AbstractBaseEntityDTO.CAMPOS.ID);
        addContextDefinition(AbstractBaseEntityDTO.CAMPOS.ID,
                             () -> getClient()
                                 .findAll(new SearchRequestDTO())
                                 .getContent().stream()
                                 .map(AbstractBaseEntityDTO.class::cast)
                                 .map(object -> new ContextDefinition(Objects
                                     .toString(object.getId()),
                                                                      Objects
                                                                          .toString(object)))
                                 .toList());
      }
    } catch (Exception e) {
      log.error(e.getLocalizedMessage(), e);
    }
  }

  @SuppressWarnings("unchecked")
  @Override
  public DefaultBaseClient<D> getClient() {
    return super.getClient();
  }

  @Override
  public CoreSecurityAuthorizationManager getAuthorizationManager() {
    return getConfig().getAuthorizationManager();
  }

  @Override
  public DefaultSecuredViewBaseMangerConfig<D> getConfig() {
    return (DefaultSecuredViewBaseMangerConfig<D>) super.getConfig();
  }
}
