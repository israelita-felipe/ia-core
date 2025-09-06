package com.ia.core.security.view.user;

import java.util.Collection;
import java.util.UUID;

import com.ia.core.security.service.model.role.RoleDTO;
import com.ia.core.security.service.model.user.UserRoleDTO;
import com.ia.core.security.view.role.RoleService;
import com.ia.core.view.client.collection.DefaultCollectionBaseClient;
import com.ia.core.view.service.collection.DefaultCollectionServiceConfig;

/**
 *
 */

public class UserRoleServiceConfig
  extends DefaultCollectionServiceConfig<UserRoleDTO> {

  /**
   * @param client {@link UserRoleClient} de comunicação
   */
  public UserRoleServiceConfig(RoleService roleService) {
    super(new DefaultCollectionBaseClient<UserRoleDTO>() {

      @Override
      public Collection<UserRoleDTO> getData() {
        return roleService.findAllUserRoles(RoleDTO.getSearchRequest())
            .getContent();
      }

      @Override
      public UUID getId(UserRoleDTO object) {
        return object.getId();
      }
    });
  }

}
