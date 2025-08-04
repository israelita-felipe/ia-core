package com.ia.core.security.service;

import com.ia.core.model.BaseEntity;
import com.ia.core.security.service.log.operation.LogOperationService;
import com.ia.core.security.service.model.authorization.CoreSecurityAuthorizationManager;
import com.ia.core.service.AbstractBaseService;
import com.ia.core.service.dto.DTO;
import com.ia.core.service.mapper.BaseMapper;
import com.ia.core.service.mapper.SearchRequestMapper;
import com.ia.core.service.repository.BaseEntityRepository;
import com.ia.core.service.translator.Translator;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * Classe base para um serviço.
 *
 * @author Israel Araújo
 * @param <T> {@link BaseEntity}
 * @param <D> {@link DTO}
 */
@Slf4j
public abstract class AbstractSecuredBaseService<T extends BaseEntity, D extends DTO<T>>
  extends AbstractBaseService<T, D>
  implements BaseSecuredService<T, D> {

  @Getter
  private final AbstractSecuredBaseServiceConfig<T, D> config;

  
  public CoreSecurityAuthorizationManager getAuthorizationManager() {
    return config.getAuthorizationManager();
  }
  public LogOperationService getLogOperationService() {
    return config.getLogOperationService();
  }
  /**
   * @param repository
   * @param mapper
   * @param searchRequestMapper
   * @param translator
   */
  public AbstractSecuredBaseService(AbstractSecuredBaseServiceConfig<T, D> config) {
    super(config);
    this.config = config;
  }

  public static class AbstractSecuredBaseServiceConfig<T extends BaseEntity, D extends DTO<T>>
    extends AbstractBaseServiceConfig<T, D> {

    @Getter
    private final CoreSecurityAuthorizationManager authorizationManager;

    @Getter
    private final LogOperationService logOperationService;

    /**
     * @param repository
     * @param mapper
     * @param searchRequestMapper
     * @param translator
     * @param authorizationManager
     * @param logOperationService
     */
    public AbstractSecuredBaseServiceConfig(BaseEntityRepository<T> repository,
                                            BaseMapper<T, D> mapper,
                                            SearchRequestMapper searchRequestMapper,
                                            Translator translator,
                                            CoreSecurityAuthorizationManager authorizationManager,
                                            LogOperationService logOperationService) {
      super(repository, mapper, searchRequestMapper, translator);
      this.authorizationManager = authorizationManager;
      this.logOperationService = logOperationService;
    }
  }
}
