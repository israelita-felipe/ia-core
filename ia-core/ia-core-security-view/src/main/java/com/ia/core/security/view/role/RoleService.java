package com.ia.core.security.view.role;

import java.util.Objects;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.ia.core.model.filter.SearchRequest;
import com.ia.core.security.service.model.role.RoleDTO;
import com.ia.core.security.service.model.role.RoleTranslator;
import com.ia.core.security.service.model.user.UserRoleDTO;
import com.ia.core.security.view.service.DefaultSecuredViewBaseService;
import com.ia.core.service.dto.DTO;
import com.ia.core.service.dto.request.SearchRequestDTO;
import com.ia.core.view.service.ListBaseService;

/**
 * Serviço para {@link RoleDTO}
 *
 * @author Israel Araújo
 */
@Service
public class RoleService
  extends DefaultSecuredViewBaseService<RoleDTO> {

  /**
   * @param client               cliente de comunicação
   * @param authorizationManager gestor de autorizações
   */
  public RoleService(RoleServiceConfig config) {
    super(config);
  }

  /**
   * Busca os elementos.
   *
   * @param request {@link SearchRequest}
   * @return Page do tipo {@link DTO}.
   * @see ListBaseService#findAll(SearchRequestDTO)
   */

  public Page<UserRoleDTO> findAllUserRoles(SearchRequestDTO request) {
    if (Objects.isNull(request)) {
      request = SearchRequestDTO.builder().build();
    }
    return getClient().findAllUserRoles(request);
  }

  @Override
  public RoleClient getClient() {
    return (RoleClient) super.getClient();
  }

  @Override
  public String getFunctionalityTypeName() {
    return RoleTranslator.ROLE;
  }
}
