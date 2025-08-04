package com.ia.core.security.view.role;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.ia.core.model.filter.SearchRequest;
import com.ia.core.security.service.model.role.RoleDTO;
import com.ia.core.security.service.model.user.UserRoleDTO;
import com.ia.core.service.dto.DTO;
import com.ia.core.service.dto.request.SearchRequestDTO;
import com.ia.core.view.client.DefaultBaseClient;
import com.ia.core.view.service.ListBaseService;

/**
 * Cliente para {@link RoleDTO}
 *
 * @author Israel Araújo
 */
@FeignClient(name = RoleClient.NOME, url = RoleClient.URL)
public interface RoleClient
  extends DefaultBaseClient<RoleDTO> {

  /**
   * Nome do cliente.
   */
  public static final String NOME = "role";
  /**
   * URL do cliente.
   */
  public static final String URL = "${feign.host}/api/${api.version}/${feign.url.role}";

  /**
   * Busca os elementos.
   *
   * @param request {@link SearchRequest}
   * @return Page do tipo {@link DTO}.
   * @see ListBaseService#findAll(SearchRequestDTO)
   */
  @PostMapping("/allUserRoles")
  Page<UserRoleDTO> findAllUserRoles(@RequestBody SearchRequestDTO request);

}
