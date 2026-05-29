package com.ia.core.security.service.functionality;

import com.ia.core.security.model.functionality.Functionality;
import com.ia.core.security.model.privilege.Privilege;
import com.ia.core.security.service.model.functionality.FunctionalityManager;
import com.ia.core.security.service.model.functionality.FunctionalityMapper;
import com.ia.core.security.service.model.functionality.HasFunctionality;
import com.ia.core.security.service.privilege.PrivilegeRepository;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Israel Araújo
 */
@Slf4j
public class DefaultFunctionalityManager
  implements FunctionalityManager {

  /** Serviço de privilégios */
  private final PrivilegeRepository privilegeRepository;

  /** Mapper de funcionalidade */
  private final FunctionalityMapper functionalityMapper;

  /**
   * @param privilegeService
   * @param services
   * @param functionalityMapper
   */
  public DefaultFunctionalityManager(PrivilegeRepository privilegeService,
                                     Collection<HasFunctionality> services,
                                     FunctionalityMapper functionalityMapper) {
    super();
    this.privilegeRepository = privilegeService;
    this.functionalityMapper = functionalityMapper;
    registryFunctionalities(services);
  }

  @Override
  public void addFunctionality(Functionality functionality) {
    if (!privilegeRepository.existsByName(functionality.getName())) {
      try {
        privilegeRepository.save(Privilege.builder()
            .name(functionality.getName()).type(functionality.getType())
            .values(functionality.getValues()).build());
      } catch (org.springframework.dao.DataAccessException e) {
        log.error("Failed to save privilege: {}", e.getLocalizedMessage(), e);
      }
    }
  }

  @Override
  public Set<Functionality> getFunctionalities() {
    return this.privilegeRepository.findAll().stream()
        .map(functionalityMapper::toFunctionality)
        .collect(Collectors.toUnmodifiableSet());
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
