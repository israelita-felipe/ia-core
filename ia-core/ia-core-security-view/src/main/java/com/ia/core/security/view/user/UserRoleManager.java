package com.ia.core.security.view.user;

import com.ia.core.security.service.model.user.UserRoleDTO;
import com.ia.core.security.view.role.RoleManager;
import com.ia.core.view.manager.collection.DefaultCollectionBaseManager;

/**
 * Serviço de {@link UserRoleDTO}
 *
 * @author Israel Araújo
 */
public class UserRoleManager
  extends DefaultCollectionBaseManager<UserRoleDTO> {

  /**
   * @param roleService {@link RoleManager}
   */
  public UserRoleManager(UserRoleManagerConfig config) {
    super(config);
  }

}
