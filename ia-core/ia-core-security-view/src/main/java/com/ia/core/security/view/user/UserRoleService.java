package com.ia.core.security.view.user;

import com.ia.core.security.service.model.user.UserRoleDTO;
import com.ia.core.security.view.role.RoleService;
import com.ia.core.view.service.collection.DefaultCollectionBaseService;

/**
 * Serviço de {@link UserRoleDTO}
 *
 * @author Israel Araújo
 */
public class UserRoleService
  extends DefaultCollectionBaseService<UserRoleDTO> {

  /**
   * @param roleService {@link RoleService}
   */
  public UserRoleService(UserRoleServiceConfig config) {
    super(config);
  }

}
