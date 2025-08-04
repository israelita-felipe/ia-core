package com.ia.core.security.service.privilege;

import org.springframework.stereotype.Service;

import com.ia.core.security.model.privilege.Privilege;
import com.ia.core.security.service.DefaultSecuredBaseService;
import com.ia.core.security.service.model.privilege.PrivilegeDTO;
import com.ia.core.security.service.model.privilege.PrivilegeTranslator;

/**
 * @author Israel Araújo
 */
@Service
public class PrivilegeService
  extends DefaultSecuredBaseService<Privilege, PrivilegeDTO> {

  /**
   * @param repository
   * @param mapper
   * @param searchRequestMapper
   * @param translator
   * @param authorizationManager
   * @param logOperationService
   */
  public PrivilegeService(PrivilegeServiceConfig config) {
    super(config);
  }

  /**
   * Verifica se existe o privilégio pelo nome
   *
   * @param name nome do privilégio
   * @return <code>true</code> caso exista privilégio com mesmo nome.
   */
  public boolean exitsByName(String name) {
    return getRepository().existsByName(name);
  }

  @Override
  public String getFunctionalityTypeName() {
    return PrivilegeTranslator.PRIVILEGE;
  }

  @Override
  public PrivilegeRepository getRepository() {
    return (PrivilegeRepository) super.getRepository();
  }
}
