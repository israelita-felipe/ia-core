package com.ia.core.security.view.user;

import com.ia.core.security.service.model.role.RoleDTO;
import com.ia.core.security.service.model.user.UserRoleDTO;
import com.ia.core.security.view.role.RoleManager;
import com.ia.core.view.client.collection.DefaultCollectionBaseClient;
import com.ia.core.view.manager.collection.DefaultCollectionManagerConfig;
import com.ia.core.view.utils.ManagerFactory;

/**
 *
 */
/**
 * Classe de configuração para user role manager.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a UserRoleManagerConfig
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
 */


public class UserRoleManagerConfig
  extends DefaultCollectionManagerConfig<UserRoleDTO> {

  /**
   * @param client {@link UserRoleClient} de comunicação
   */
  public UserRoleManagerConfig(RoleManager roleService) {
    super(createUserRoleClient(roleService));
  }

  /**
   * @param roleService
   * @return
   */
  public static DefaultCollectionBaseClient<UserRoleDTO> createUserRoleClient(RoleManager roleService) {
    return ManagerFactory.createCollectionBaseClient(() -> roleService
        .findAllUserRoles(RoleDTO.getSearchRequest()).getContent(),
                                                     UserRoleDTO::getId);
  }

}
