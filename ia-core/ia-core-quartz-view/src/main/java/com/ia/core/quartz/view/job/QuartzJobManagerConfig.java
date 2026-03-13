package com.ia.core.quartz.view.job;

import org.springframework.stereotype.Component;

import com.ia.core.quartz.service.model.job.QuartzJobDTO;
import com.ia.core.security.service.model.authorization.CoreSecurityAuthorizationManager;
import com.ia.core.security.view.manager.DefaultSecuredViewBaseMangerConfig;
import com.ia.core.view.client.BaseClient;

/**
 * Configuração do QuartzJobManager.
 * 
 * @author Israel Araújo
 */
@Component
public class QuartzJobManagerConfig
  extends DefaultSecuredViewBaseMangerConfig<QuartzJobDTO> {

  /**
   * Construtor com as dependências necessárias.
   * 
   * @param client               Cliente
   * @param authorizationManager Gerenciador de autorização
   */
  public QuartzJobManagerConfig(BaseClient<QuartzJobDTO> client,
                               CoreSecurityAuthorizationManager authorizationManager) {
    super(client, authorizationManager);
  }
}
