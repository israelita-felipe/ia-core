package com.ia.core.service;

import org.springframework.transaction.PlatformTransactionManager;

import com.ia.core.model.BaseEntity;
import com.ia.core.service.contract.HasMapper;
import com.ia.core.service.contract.HasRepository;
import com.ia.core.service.contract.HasSearchRequestMapper;
import com.ia.core.service.contract.HasTransactionManager;
import com.ia.core.service.contract.HasTranslator;
import com.ia.core.service.dto.DTO;
import com.ia.core.service.mapper.Mapper;
import com.ia.core.service.mapper.SearchRequestMapper;
import com.ia.core.service.repository.BaseEntityRepository;
import com.ia.core.service.translator.Translator;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Classe base abstrata para serviços de entidades. Responsabilidade Única:
 * Orquestrar operações de negócio de forma segura. Implementa interfaces
 * segregadas para fornecer acesso a componentes específicos: -
 * {@link HasRepository} - Acesso ao repositório - {@link HasMapper} - Acesso ao
 * mapper - {@link HasSearchRequestMapper} - Acesso ao search request mapper -
 * {@link HasTranslator} - Acesso ao tradutor - {@link HasTransactionManager} -
 * Acesso ao gerenciador de transações
 *
 * @author Israel Araújo
 * @param <T> {@link BaseEntity}
 * @param <D> {@link DTO}
 */
@RequiredArgsConstructor
@Slf4j
public abstract class AbstractBaseService<T extends BaseEntity, D extends DTO<?>>
  implements BaseService<T, D>, HasRepository<T>, HasMapper<T, D>,
  HasSearchRequestMapper, HasTranslator, HasTransactionManager {
  /**
   * Configuração do serviço base.
   */
  @Getter
  private final AbstractBaseServiceConfig<T, D> config;

  /**
   * Execução pós-inicialização do serviço. Responsabilidade: Inicialização e
   * validação da configuração.
   */
  @PostConstruct
  public void baseServiceInit() {
    log.info("{} inicializado", this.getClass());
    validateConfiguration();
  }

  /**
   * Valida se a configuração do serviço é válida.
   *
   * @throws IllegalStateException se configuração inválida ou nula
   */
  private void validateConfiguration() {
    if (config == null) {
      throw new IllegalStateException("Configuração não pode ser nula em "
          + this.getClass().getSimpleName());
    }
    log.debug("Configuração validada para {}",
              this.getClass().getSimpleName());
  }

  /**
   * Obtém o mapper de transformação de entidade para DTO.
   *
   * @param <M> Tipo do Mapper
   * @return {@link Mapper}
   */
  @SuppressWarnings("unchecked")
  @Override
  public <M extends Mapper<T, D>> M getMapper() {
    return (M) config.getMapper();
  }

  /**
   * Obtém o repositório de persistência de entidades.
   *
   * @param <R> Tipo do Repositório.
   * @return {@link BaseEntityRepository}
   */
  @SuppressWarnings("unchecked")
  @Override
  public <R extends BaseEntityRepository<T>> R getRepository() {
    return (R) config.getRepository();
  }

  /**
   * Obtém o mapper de requisição de busca.
   *
   * @return {@link SearchRequestMapper}
   */
  @Override
  public SearchRequestMapper getSearchRequestMapper() {
    return config.getSearchRequestMapper();
  }

  /**
   * Obtém o tradutor padrão do serviço.
   *
   * @return {@link Translator}
   */
  @Override
  public Translator getTranslator() {
    return config.getTranslator();
  }

  /**
   * Obtém o gerenciador de transações do serviço.
   *
   * @return {@link PlatformTransactionManager}
   */
  @Override
  public PlatformTransactionManager getTransactionManager() {
    return config.getTransactionManager();
  }

  /**
   * Classe de configuração do serviço base. Responsabilidade: Encapsular todas
   * as dependências necessárias para o serviço. Esta classe utiliza o padrão
   * Builder com Lombok (@SuperBuilder) para facilitar a criação e herança em
   * subclasses.
   *
   * @param <T> {@link BaseEntity}
   * @param <D> {@link DTO}
   */
  @RequiredArgsConstructor
  @Slf4j
  public static class AbstractBaseServiceConfig<T extends BaseEntity, D extends DTO<?>> {

    /**
     * Gestor de transação Spring.
     */
    @Getter
    private final PlatformTransactionManager transactionManager;

    /**
     * Repositório para operações de persistência.
     */
    @Getter
    private final BaseEntityRepository<T> repository;

    /**
     * Mapper para transformação entidade-DTO.
     */
    @Getter
    private final Mapper<T, D> mapper;

    /**
     * Mapper para requisições de busca.
     */
    @Getter
    private final SearchRequestMapper searchRequestMapper;

    /**
     * Tradutor para mensagens e labels.
     */
    @Getter
    private final Translator translator;
  }
}
