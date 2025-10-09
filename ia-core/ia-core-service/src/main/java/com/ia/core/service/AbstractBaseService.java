package com.ia.core.service;

import org.springframework.transaction.PlatformTransactionManager;

import com.ia.core.model.BaseEntity;
import com.ia.core.service.dto.DTO;
import com.ia.core.service.mapper.BaseMapper;
import com.ia.core.service.mapper.SearchRequestMapper;
import com.ia.core.service.repository.BaseEntityRepository;
import com.ia.core.service.translator.Translator;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Classe base para um serviço.
 *
 * @author Israel Araújo
 * @param <T> {@link BaseEntity}
 * @param <D> {@link DTO}
 */
@RequiredArgsConstructor
@Slf4j
public abstract class AbstractBaseService<T extends BaseEntity, D extends DTO<T>>
  implements BaseService<T, D> {
  /**
   * Configuração do serviço base.
   */
  @Getter
  private final AbstractBaseServiceConfig<T, D> config;

  /** execução após inicializar o serviço */
  @PostConstruct
  public void baseServiceInit() {
    log.info("{} inicializado", this.getClass());
  }

  /**
   * {@link BaseMapper}
   *
   * @param <M> Tipo do Mapper
   * @return {@link BaseMapper}
   */
  @Override
  public <M extends BaseMapper<T, D>> M getMapper() {
    return (M) config.getMapper();
  }

  /**
   * {@link BaseEntityRepository}
   *
   * @param <R> Tipo do Repositório.
   * @return {@link BaseEntityRepository}
   */
  @Override
  public <R extends BaseEntityRepository<T>> R getRepository() {

    return (R) config.getRepository();
  }

  /**
   * @return {@link SearchRequestMapper}
   */
  @Override
  public SearchRequestMapper getSearchRequestMapper() {

    return config.getSearchRequestMapper();
  }

  /**
   * @return {@link Translator} padrão
   */
  @Override
  public Translator getTranslator() {
    return config.getTranslator();
  }

  @Override
  public PlatformTransactionManager getTransactionManager() {
    return getConfig().getTransactionManager();
  }

  /**
   * Classe de configuração do serviço base.
   *
   * @param <T> {@link BaseEntity}
   * @param <D> {@link DTO}
   */
  @RequiredArgsConstructor
  @Slf4j
  public static class AbstractBaseServiceConfig<T extends BaseEntity, D extends DTO<T>> {

    /** Gestor de transação */
    @Getter
    private final PlatformTransactionManager transactionManager;
    /**
     * Repositório
     */
    @Getter
    private final BaseEntityRepository<T> repository;
    /**
     * Mapper
     */
    @Getter
    private final BaseMapper<T, D> mapper;

    /**
     * Search request
     */
    @Getter
    private final SearchRequestMapper searchRequestMapper;
    /** Tradutor do serviço */
    @Getter
    private final Translator translator;
  }
}
