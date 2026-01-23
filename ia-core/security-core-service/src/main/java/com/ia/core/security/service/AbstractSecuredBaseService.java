package com.ia.core.security.service;

import java.util.Collection;
import java.util.Objects;

import org.springframework.transaction.PlatformTransactionManager;

import com.ia.core.model.BaseEntity;
import com.ia.core.security.service.log.operation.LogOperationService;
import com.ia.core.security.service.model.authorization.CoreSecurityAuthorizationManager;
import com.ia.core.service.AbstractBaseService;
import com.ia.core.service.dto.DTO;
import com.ia.core.service.dto.entity.AbstractBaseEntityDTO;
import com.ia.core.service.mapper.Mapper;
import com.ia.core.service.mapper.SearchRequestMapper;
import com.ia.core.service.repository.BaseEntityRepository;
import com.ia.core.service.translator.Translator;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * Classe base para um serviço com capacidades de segurança. Herda de
 * {@link AbstractBaseService} e adiciona funcionalidades de segurança: -
 * Gerenciamento de autorização baseado em funcionalidades - Gerenciamento de
 * logs de operações - Contexto de segurança dinâmico via estratégias Padrões
 * Aplicados: - Strategy Pattern: Diferentes tipos de contexto - Decorator
 * Pattern: Adicionar responsabilidades de segurança - SRP: Cada
 * responsabilidade em um serviço específico
 *
 * @author Israel Araújo
 * @param <T> {@link BaseEntity}
 * @param <D> {@link DTO}
 */
@Slf4j
public abstract class AbstractSecuredBaseService<T extends BaseEntity, D extends DTO<?>>
  extends AbstractBaseService<T, D>
  implements BaseSecuredService<T, D> {

  @Getter
  private final AbstractSecuredBaseServiceConfig<T, D> config;

  /**
   * Obtém o gerenciador de autorização baseado em funcionalidades.
   *
   * @return {@link CoreSecurityAuthorizationManager}
   */
  @Override
  public CoreSecurityAuthorizationManager getAuthorizationManager() {
    return getConfig().getAuthorizationManager();
  }

  /**
   * Obtém o serviço de log de operações.
   *
   * @return {@link LogOperationService}
   */
  @Override
  public LogOperationService getLogOperationService() {
    return getConfig().getLogOperationService();
  }

  /**
   * Construtor que inicializa o serviço com configuração de segurança.
   *
   * @param config Configuração do serviço de segurança
   */
  public AbstractSecuredBaseService(AbstractSecuredBaseServiceConfig<T, D> config) {
    super(config);
    this.config = config;
    createContext();
  }

  /**
   * Cria os contextos de segurança padrão.
   */
  @Override
  public void createContext() {
    addContext(AbstractBaseEntityDTO.CAMPOS.ID);
  }

  /**
   * Obtém os valores de contexto para uma chave específica. Delega para o
   * serviço de contexto que usa estratégias.
   *
   * @param key    Chave do contexto
   * @param values Valores iniciais
   * @return Coleção de valores de contexto resolvidos
   */
  @Override
  public Collection<Object> getContextDefinitionValue(String key,
                                                      Collection<String> values) {
    Objects.requireNonNull(key, "Context key não pode ser nula");
    Objects.requireNonNull(values, "Values não pode ser nula");

    log.debug("Resolvendo contexto '{}' para valores", key);
    return getConfig().getSecurityContextService()
        .resolveContextValues(key, values, getRepository());
  }

  /**
   * Valida correspondência de contexto. Delega para o serviço de contexto que
   * usa estratégias.
   *
   * @param contextKey          Chave do contexto
   * @param serviceContextValue Valor do serviço
   * @param userContextValue    Valor do usuário
   * @return true se corresponde, false caso contrário
   */
  @Override
  public boolean matches(String contextKey, String serviceContextValue,
                         String userContextValue) {
    Objects.requireNonNull(contextKey, "Context key não pode ser nula");
    Objects.requireNonNull(serviceContextValue,
                           "Service context value não pode ser nula");
    Objects.requireNonNull(userContextValue,
                           "User context value não pode ser nula");

    log.debug("Validando correspondência de contexto '{}'", contextKey);
    return getConfig().getSecurityContextService()
        .matches(contextKey, serviceContextValue, userContextValue);
  }

  /**
   * Classe de configuração do serviço de segurança. Estende
   * {@link AbstractBaseServiceConfig} adicionando componentes de segurança: -
   * Gerenciador de autorização - Serviço de log de operações
   *
   * @param <T> {@link BaseEntity}
   * @param <D> {@link DTO}
   */
  public static class AbstractSecuredBaseServiceConfig<T extends BaseEntity, D extends DTO<?>>
    extends AbstractBaseServiceConfig<T, D> {

    /**
     * Gerenciador de autorização baseado em funcionalidades.
     */
    @Getter
    private final CoreSecurityAuthorizationManager authorizationManager;

    /**
     * Serviço para registrar operações no sistema.
     */
    @Getter
    private final LogOperationService logOperationService;

    /**
     * Serviço de contexto de segurança (segregado).
     */
    @Getter
    private final SecurityContextService securityContextService;

    /**
     * Construtor da configuração de serviço de segurança.
     *
     * @param transactionManager     Gerenciador de transações
     * @param repository             Repositório de entidades
     * @param mapper                 Mapper entidade-DTO
     * @param searchRequestMapper    Mapper de requisição de busca
     * @param translator             Tradutor de mensagens
     * @param authorizationManager   Gerenciador de autorização
     * @param securityContextService Serviço de contexto de segurança
     * @param logOperationService    Serviço de log de operações
     */
    public AbstractSecuredBaseServiceConfig(PlatformTransactionManager transactionManager,
                                            BaseEntityRepository<T> repository,
                                            Mapper<T, D> mapper,
                                            SearchRequestMapper searchRequestMapper,
                                            Translator translator,
                                            CoreSecurityAuthorizationManager authorizationManager,
                                            SecurityContextService securityContextService,
                                            LogOperationService logOperationService) {
      super(transactionManager, repository, mapper, searchRequestMapper,
            translator);
      this.authorizationManager = authorizationManager;
      this.logOperationService = logOperationService;
      this.securityContextService = securityContextService;
    }
  }
}
