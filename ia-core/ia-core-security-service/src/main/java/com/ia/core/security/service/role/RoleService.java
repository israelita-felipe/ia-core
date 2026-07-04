package com.ia.core.security.service.role;

import com.ia.core.model.filter.SearchRequest;
import com.ia.core.model.specification.SearchSpecification;
import com.ia.core.security.model.role.Role;
import com.ia.core.security.service.CrudSecuredBaseService;
import com.ia.core.security.service.model.role.RoleDTO;
import com.ia.core.security.service.model.role.RoleTranslator;
import com.ia.core.security.service.model.role.RoleUseCase;
import com.ia.core.security.service.model.user.UserRoleDTO;
import com.ia.core.service.dto.DTO;
import com.ia.core.service.dto.request.SearchRequestDTO;
import com.ia.core.service.exception.ServiceException;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
/**
 * Serviço de negócio para gerenciamento de role.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a RoleService
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
 */

@Service
public class RoleService
  extends CrudSecuredBaseService<Role, RoleDTO>
  implements RoleUseCase {

  /**
   * @param repository
   * @param mapper
   * @param searchRequestMapper
   * @param translator
   * @param authorizationManager
   * @param logOperationService
   */
  public RoleService(RoleServiceConfig config) {
    super(Objects.requireNonNull(config, "config não pode ser null"));
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
  @Tool(description = "Lista todos os usuários associados a roles com paginação. " +
                     "Retorna uma página contendo UserRoleDTO com informações sobre usuários e suas roles. " +
                     "Suporta filtros e paginação para controlar o conjunto de resultados. " +
                     "Verifica permissões de listagem antes de executar a busca. " +
                     "Útil para visualizar quais usuários possuem quais roles no sistema.")
  public Page<UserRoleDTO> findAllUserRoles(
          @ToolParam(description = "DTO de requisição de busca contendo filtros, paginação (page, size) e ordenação. " +
                                   "page (Integer, opcional, padrão 0), size (Integer, opcional, padrão 10), " +
                                   "filters (List<FilterRequestDTO>, opcional, filtros para refinar busca). " +
                                   "Se não houver filtros, retorna todos os usuários com roles.",
                      required = false) SearchRequestDTO requestDTO) {
    Objects.requireNonNull(requestDTO, "requestDTO não pode ser null");
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
