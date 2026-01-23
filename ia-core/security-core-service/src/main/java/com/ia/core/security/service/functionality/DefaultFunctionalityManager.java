package com.ia.core.security.service.functionality;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import com.ia.core.security.model.functionality.Functionality;
import com.ia.core.security.model.privilege.Privilege;
import com.ia.core.security.model.privilege.PrivilegeType;
import com.ia.core.security.service.model.functionality.FunctionalityManager;
import com.ia.core.security.service.model.functionality.HasFunctionality;
import com.ia.core.security.service.privilege.PrivilegeRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Israel Araújo
 */
@Slf4j
public class DefaultFunctionalityManager
  implements FunctionalityManager {

  /** Serviço de privilégios */
  private final PrivilegeRepository privilegeRepository;

  /**
   * @param services
   */
  public DefaultFunctionalityManager(PrivilegeRepository privilegeService,
                                     Collection<HasFunctionality> services) {
    super();
    this.privilegeRepository = privilegeService;
    registryFunctionalities(services);
  }

  @Override
  public void addFunctionality(Functionality functionality) {
    if (!privilegeRepository.existsByName(functionality.getName())) {
      try {
        privilegeRepository.save(Privilege.builder()
            .name(functionality.getName()).type(functionality.getType())
            .values(functionality.getValues()).build());
      } catch (Exception e) {
        log.error(e.getLocalizedMessage(), e);
      }
    }
  }

  @Override
  public Set<Functionality> getFunctionalities() {
    return this.privilegeRepository.findAll().stream().map(privilege -> {
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
    }).collect(Collectors.toUnmodifiableSet());
  }

  /**
   * @param services Registra uma coleção de funcionalidades
   */
  public void registryFunctionalities(Collection<HasFunctionality> services) {
    log.debug("Registrando as funcionalidades");
    services.forEach(service -> {
      service.registryFunctionalities(this);
    });
  }

}
