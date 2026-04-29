package com.ia.core.quartz.view.job;

import com.ia.core.quartz.service.model.job.dto.QuartzJobDTO;
import com.ia.core.security.service.model.authorization.CoreSecurityAuthorizationManager;
import com.ia.core.security.view.manager.DefaultSecuredViewBaseMangerConfig;
import org.springframework.stereotype.Component;

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
  public QuartzJobManagerConfig(QuartzJobClient client,
                                CoreSecurityAuthorizationManager authorizationManager) {
    super(client, authorizationManager);
  }
}
