package com.ia.core.security.service.role;

import com.ia.core.security.model.role.Role;
import com.ia.core.service.repository.BaseEntityRepository;
/**
 * Classe que representa o acesso a dados de role.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a RoleRepository
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
 */

public interface RoleRepository
  extends BaseEntityRepository<Role> {

}
