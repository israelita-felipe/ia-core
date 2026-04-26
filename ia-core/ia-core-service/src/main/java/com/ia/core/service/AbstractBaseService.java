package com.ia.core.service;

import com.ia.core.model.BaseEntity;
import com.ia.core.service.contract.HasMapper;
import com.ia.core.service.contract.HasRepository;
import com.ia.core.service.contract.HasSearchRequestMapper;
import com.ia.core.service.contract.HasTranslator;
import com.ia.core.service.dto.DTO;
import com.ia.core.service.event.BaseServiceEvent;
import com.ia.core.service.event.EventType;
import com.ia.core.service.mapper.Mapper;
import com.ia.core.service.mapper.SearchRequestMapper;
import com.ia.core.service.repository.BaseEntityRepository;
import com.ia.core.service.translator.Translator;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;

/**
 * Classe base abstrata para serviços de entidades. Responsabilidade Única:
 * Orquestrar operações de negócio de forma segura. Implementa interfaces
 * segregadas para fornecer acesso a componentes específicos: -
 * {@link HasRepository} - Acesso ao repositório - {@link HasMapper} - Acesso ao
 * mapper - {@link HasSearchRequestMapper} - Acesso ao search request mapper -
 * {@link HasTranslator} - Acesso ao tradutor
 *
 * @author Israel Araújo
 * @param <T> {@link BaseEntity}
 * @param <D> {@link DTO}
 */
@RequiredArgsConstructor
@Slf4j
public abstract class AbstractBaseService<T extends BaseEntity, D extends DTO<?>>
  implements BaseService<T, D>, HasRepository<T>, HasMapper<T, D>,
  HasSearchRequestMapper, HasTranslator {
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

    // Validação de cada dependência usando Composite Validator Pattern
    validateRepository();
    validateMapper();
    validateTranslator();

    log.debug("Configuração validada para {}",
              this.getClass().getSimpleName());
  }

  /**
   * Valida se o repositório está configurado.
   *
   * @throws IllegalStateException se repository for nulo
   */
  private void validateRepository() {
    if (config.getRepository() == null) {
      throw new IllegalStateException("Repository não pode ser nulo em "
          + this.getClass().getSimpleName());
    }
  }

  /**
   * Valida se o mapper está configurado.
   *
   * @throws IllegalStateException se mapper for nulo
   */
  private void validateMapper() {
    if (config.getMapper() == null) {
      throw new IllegalStateException("Mapper não pode ser nulo em "
          + this.getClass().getSimpleName());
    }
  }

  /**
   * Valida se o translator está configurado.
   *
   * @throws IllegalStateException se translator for nulo
   */
  private void validateTranslator() {
    if (config.getTranslator() == null) {
      throw new IllegalStateException("Translator não pode ser nulo em "
          + this.getClass().getSimpleName());
    }
  }

  /**
   * Obtém o mapper de transformação de entidade para DTO.
   *
   * @param <M> Tipo do Mapper
   * @return {@link Mapper}
   */
  @Override
  public <M extends Mapper<T, D>> M getMapper() {
    Mapper<T, D> mapper = config.getMapper();
    if (mapper == null) {
      throw new IllegalStateException("Mapper não pode ser nulo em "
          + this.getClass().getSimpleName());
    }
    @SuppressWarnings("unchecked")
    M result = (M) mapper;
    return result;
  }

  /**
   * Obtém o repositório de persistência de entidades.
   *
   * @param <R> Tipo do Repositório.
   * @return {@link BaseEntityRepository}
   */
  @Override
  public <R extends BaseEntityRepository<T>> R getRepository() {
    BaseEntityRepository<T> repository = config.getRepository();
    if (repository == null) {
      throw new IllegalStateException("Repository não pode ser nulo em "
          + this.getClass().getSimpleName());
    }
    @SuppressWarnings("unchecked")
    R result = (R) repository;
    return result;
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

  // ========== MÉTODOS DE PUBLICAÇÃO DE EVENTOS ==========

  /**
   * Publica um evento de domínio relacionado ao DTO.
   * <p>
   * Este método permite que subclasses publiquem eventos quando operações
   * significativas são realizadas (criação, atualização, exclusão, etc.).
   * </p>
   * <p>
   * <b>Thread-Safety:</b> Este método é thread-safe. A publicação de eventos é
   * delegados ao ApplicationEventPublisher do Spring que gerencia a
   * sincronização.
   * </p>
   * <p>
   * <b>Comportamento Assíncrono:</b> Por padrão, os eventos são publicados de
   * forma síncrona no mesmo thread da transação. Para processamento assíncrono,
   * configure um {@code ApplicationEventMulticaster} customizado com executor
   * assíncrono.
   * </p>
   * <p>
   * <b>Garantias de Entrega:</b>
   * <ul>
   * <li>Se eventPublisher for nulo, o evento é ignorado silenciosamente</li>
   * <li>Eventos são publicados apenas se a transação estiver ativa</li>
   * <li>Exceções na publicação não interrompem a transação principal</li>
   * </ul>
   * </p>
   *
   * @param dto       DTO afetado pela operação
   * @param eventType Tipo de evento conforme {@link EventType}
   * @see BaseServiceEvent
   * @see EventType
   */
  protected void publishEvent(D dto, EventType eventType) {
    if (config.getEventPublisher() != null) {
      config.getEventPublisher()
          .publishEvent(new BaseServiceEvent<>(this, dto, eventType));
    }
  }

  /**
   * Publica um evento de domínio com dados adicionais.
   *
   * @param dto       DTO afetado
   * @param eventType Tipo de evento
   * @param data      Dados adicionais do evento
   */
  protected void publishEvent(D dto, EventType eventType, Object data) {
    if (config.getEventPublisher() != null) {
      config.getEventPublisher()
          .publishEvent(new BaseServiceEvent<>(this, dto, eventType, data));
    }
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

    /**
     * Publicador de eventos de domínio (opcional).
     */
    @Getter
    private final ApplicationEventPublisher eventPublisher;
  }
}
