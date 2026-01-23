package com.ia.core.security.view.functionality;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import com.ia.core.security.model.functionality.Functionality;
import com.ia.core.security.model.privilege.PrivilegeType;
import com.ia.core.security.service.model.functionality.FunctionalityManager;
import com.ia.core.security.service.model.functionality.HasFunctionality;
import com.ia.core.security.service.model.privilege.PrivilegeDTO;
import com.ia.core.security.view.privilege.PrivilegeManager;
import com.ia.core.view.exception.ValidationException;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Israel Araújo
 */
@Slf4j
public class DefaultViewFunctionalityManager
  implements FunctionalityManager {

  /**
   * Servico de privilégios.
   */
  private final PrivilegeManager privilegeService;

  /**
   * @param hasFunctionalities
   */
  public DefaultViewFunctionalityManager(PrivilegeManager privilegeService,
                                         Collection<HasFunctionality> hasFunctionalities) {
    super();
    this.privilegeService = privilegeService;
    registryFunctionalities(hasFunctionalities);
  }

  @Override
  public void addFunctionality(Functionality functionality) {
    if (!privilegeService.existsByName(functionality.getName())) {
      try {
        privilegeService.save(PrivilegeDTO.builder()
            .type(functionality.getType()).name(functionality.getName())
            .values(functionality.getValues()).build());
      } catch (ValidationException e) {
        log.error(e.getLocalizedMessage(), e);
      }
    }
  }

  @Override
  public Set<Functionality> getFunctionalities() {
    return this.privilegeService.findAll(PrivilegeDTO.getSearchRequest())
        .map(privilege -> {
          return new Functionality() {

            @Override
            public Set<String> getValues() {
              return privilege.getValues();
            }

            @Override
            public PrivilegeType getType() {
              return privilege.getType();
            }

            @Override
            public String getName() {
              return privilege.getName();
            }
          };
        }).stream().collect(Collectors.toUnmodifiableSet());
  }

  public void registryFunctionalities(Collection<HasFunctionality> services) {
    log.info("Registrando as funcionalidades");
    services.forEach(service -> {
      service.registryFunctionalities(this);
    });
  }
}
