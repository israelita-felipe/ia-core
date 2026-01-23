package com.ia.core.security.service.role;

import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ia.core.model.filter.SearchRequest;
import com.ia.core.model.specification.SearchSpecification;
import com.ia.core.security.model.role.Role;
import com.ia.core.security.service.DefaultSecuredBaseService;
import com.ia.core.security.service.model.role.RoleDTO;
import com.ia.core.security.service.model.role.RoleTranslator;
import com.ia.core.security.service.model.user.UserRoleDTO;
import com.ia.core.service.dto.DTO;
import com.ia.core.service.dto.request.SearchRequestDTO;
import com.ia.core.service.exception.ServiceException;

/**
 * @author Israel Araújo
 */
@Service
public class RoleService
  extends DefaultSecuredBaseService<Role, RoleDTO> {

  /**
   * @param repository
   * @param mapper
   * @param searchRequestMapper
   * @param translator
   * @param authorizationManager
   * @param logOperationService
   */
  public RoleService(RoleServiceConfig config) {
    super(config);
  }

  @Override
  public RoleServiceConfig getConfig() {
    return (RoleServiceConfig) super.getConfig();
  }

  @Override
  public Role synchronize(Role model)
    throws ServiceException {
    Role role = super.synchronize(model);
    role.getPrivileges().forEach(privilege -> {
      privilege.setRole(role);
      privilege.getOperations().forEach(operation -> {
        operation.getContext().forEach(context -> {
          context.setPrivilegeOperation(operation);
        });
      });
    });
    return role;
  }

  /**
   * Deleta um {@link DTO} pelo seu {@link UUID}.
   *
   * @param requestDTO {@link SearchRequest}
   * @return {@link Page} de dados do tipo <T>
   */
  public Page<UserRoleDTO> findAllUserRoles(SearchRequestDTO requestDTO) {
    if (canList(requestDTO)) {
      SearchRequest request = getSearchRequestMapper().toModel(requestDTO);
      request.setFilters(request.getFilters().stream()
          .filter(filter -> filter.getKey() != null
              && filter.getOperator() != null)
          .collect(Collectors.toList()));
      // cria a especificação
      SearchSpecification<Role> specification = new SearchSpecification<>(request);
      // cria a paginação
      Pageable pageable = SearchSpecification
          .getPageable(request.getPage(), request.getSize());
      // realiza a busca convertendo para o dto.
      return getRepository().findAll(specification, pageable)
          .map(getConfig().getUserRoleMapper()::toDTO);
    }
    return Page.empty();
  }

  @Override
  public String getFunctionalityTypeName() {
    return RoleTranslator.ROLE;
  }
}
