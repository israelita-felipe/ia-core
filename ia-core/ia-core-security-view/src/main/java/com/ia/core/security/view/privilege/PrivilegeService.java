package com.ia.core.security.view.privilege;

import org.springframework.stereotype.Service;

import com.ia.core.security.service.model.authorization.CoreSecurityAuthorizationManager;
import com.ia.core.security.service.model.privilege.PrivilegeDTO;
import com.ia.core.security.service.model.privilege.PrivilegeTranslator;
import com.ia.core.security.view.service.DefaultSecuredViewBaseService;
import com.ia.core.service.dto.filter.FieldTypeDTO;
import com.ia.core.service.dto.filter.FilterRequestDTO;
import com.ia.core.service.dto.filter.OperatorDTO;
import com.ia.core.service.dto.request.SearchRequestDTO;
import com.ia.core.view.client.BaseClient;

/**
 * Serviço para {@link PrivilegeDTO}
 *
 * @author Israel Araújo
 */
@Service
public class PrivilegeService
  extends DefaultSecuredViewBaseService<PrivilegeDTO> {

  /**
   * @param client               cliente de comunicação
   * @param authorizationManager gerente de autorização
   */
  public PrivilegeService(BaseClient<PrivilegeDTO> client,
                          CoreSecurityAuthorizationManager authorizationManager) {
    super(client, authorizationManager);
  }

  /**
   * @param name Nome do privilégio
   * @return <code>true</code> se o mesmo existir
   */
  public boolean existsByName(String name) {
    SearchRequestDTO searchRequest = PrivilegeDTO.getSearchRequest();
    searchRequest.getFilters()
        .add(FilterRequestDTO.builder().fieldType(FieldTypeDTO.STRING)
            .key("name").operator(OperatorDTO.EQUAL).value(name).build());
    return count(searchRequest) > 0;
  }

  @Override
  public String getFunctionalityTypeName() {
    return PrivilegeTranslator.PRIVILEGE;
  }
}
