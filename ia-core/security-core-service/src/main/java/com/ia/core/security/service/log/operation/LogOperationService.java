package com.ia.core.security.service.log.operation;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

import org.springframework.stereotype.Service;

import com.ia.core.model.BaseEntity;
import com.ia.core.security.model.functionality.OperationEnum;
import com.ia.core.security.model.log.operation.LogOperation;
import com.ia.core.security.service.model.log.operation.LogOperationDTO;
import com.ia.core.security.service.model.log.operation.LogOperationTranslator;
import com.ia.core.service.DefaultBaseService;
import com.ia.core.service.dto.DTO;
import com.ia.core.service.dto.entity.BaseEntityDTO;
import com.ia.core.service.mapper.BaseMapper;
import com.ia.core.service.repository.BaseEntityRepository;
import com.ia.core.service.util.JsonUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Israel Araújo
 */
@Slf4j
@Service
public class LogOperationService
  extends DefaultBaseService<LogOperation, LogOperationDTO> {
  /** Parâmetro - nome do usuário */
  public static final String USER_NAME_PARAMETER = LogOperationTranslator.USER_NAME;
  /** Parâmetro - código do usuário */
  public static final String USER_CODE_PARAMETER = LogOperationTranslator.USER_CODE;
  /** Parâmetro - objeto excluído */
  public static final String DELETE_ENTITY_PARAMETER = "DELETE_ENTITY_PARAMETER";
  /** Parâmetro - objeto inserido */
  public static final String INSERT_ENTITY_PARAMETER = "INSERT_ENTITY_PARAMETER";
  /** Parâmetro - objeto antigo */
  public static final String UPDATE_ENTITY_OLD_OBJECT_PARAMETER = "UPDATE_ENTITY_OLD_OBJECT_PARAMETER";
  /** Parâmetro - objeto atualizado */
  public static final String UPDATE_ENTITY_UPDATED_OBJECT_PARAMETER = "UPDATE_ENTITY_UPDATED_OBJECT_PARAMETER";
  /** Escutadores das operações */
  private static final Map<LogOperationEnum, Collection<BiConsumer<Map<String, Object>, DTO<? extends BaseEntity>>>> listeners = new ConcurrentHashMap<>();
  /** Avaliadores */
  private static BiFunction<Map<String, Object>, DTO<? extends BaseEntity>, Boolean> listenerEvaluator = (map,
                                                                                                          entity) -> true;
  /** Escutador da operação que define nome de usuário e código */
  private Runnable operationSetUserDetailsListenerRemover;
  /** Contexto */
  private static InheritableThreadLocal<Map<String, Object>> context;
  /**
   * Construtor estático para contexto no escopo de thread
   */
  static {
    context = new InheritableThreadLocal<>();
  }

  /**
   * Construtor padrão
   *
   * @param repository          repositório do tipo do objeto
   * @param mapper              mapeador do objeto
   * @param searchRequestMapper mapeador de busca
   * @param translator          tradutor
   * @param validators          validadores
   */
  public LogOperationService(LogOperationServiceConfig config) {
    super(config);
    initLogOperationListeners();
  }

  /**
   * Realiza o log da operação de exclusão
   *
   * @param map    parâmetros
   * @param entity entidade que sofreu exclusão
   */
  public void logDelete(final Map<String, Object> map,
                        final BaseEntityDTO<?> entity) {
    try {
      String userCode = getUserCode(map);
      String userName = getUserName(map);
      BaseEntityDTO<?> dto = getDeletedObject(map);
      if (dto != null && Objects.equals(entity.getId(), dto.getId())) {
        LogOperationDTO logOperation = LogOperationDTO.builder()
            .dateTimeOperation(LocalDateTime.now()).oldValue(toJson(dto))
            .operation(OperationEnum.DELETE)
            .type(dto.getClass().getCanonicalName()).valueId(dto.getId())
            .userCode(userCode).userName(userName).build();
        log(logOperation, entity);
      }
    } catch (Exception e) {
      log.error(e.getLocalizedMessage(), e);
      throw new RuntimeException(e.getLocalizedMessage(), e);
    } finally {
      getContext().clear();
    }
  }

  /**
   * @return
   */
  public static Map<String, Object> getContext() {
    Map<String, Object> map = context.get();
    if (map == null) {
      map = new ConcurrentHashMap<>();
      context.set(map);
    }
    return map;
  }

  /**
   * Realiza o log da operação de inserção
   *
   * @param map    parâmetros
   * @param entity entidade que sofreu inserção
   */
  public void logInsert(final Map<String, Object> map,
                        final BaseEntityDTO<?> entity) {
    try {
      String userCode = getUserCode(map);
      String userName = getUserName(map);
      BaseEntityDTO<?> dto = getInsertedObject(map);
      if (dto != null && Objects.equals(entity.getId(), dto.getId())) {
        LogOperationDTO logOperation = LogOperationDTO.builder()
            .dateTimeOperation(LocalDateTime.now()).newValue(toJson(dto))
            .operation(OperationEnum.CREATE)
            .type(dto.getClass().getCanonicalName()).valueId(dto.getId())
            .userCode(userCode).userName(userName).build();
        log(logOperation, entity);
      }
    } catch (Exception e) {
      log.error(e.getLocalizedMessage(), e);
      throw new RuntimeException(e.getLocalizedMessage(), e);
    } finally {
      getContext().clear();
    }
  }

  /**
   * Realiza o log da operação de update
   *
   * @param map    parâmetros
   * @param entity entidade que sofreu atualização
   */
  public void logUpdate(final Map<String, Object> map,
                        final BaseEntityDTO<?> entity) {
    try {
      String userCode = getUserCode(map);
      String userName = getUserName(map);
      BaseEntityDTO<?> dtoOld = getOldObject(map);
      BaseEntityDTO<?> dtoNew = getUpdatedObject(map);
      if (dtoOld != null && dtoNew != null
          && Objects.equals(dtoOld.getId(), dtoNew.getId())
          && Objects.equals(dtoOld.getId(), entity.getId())) {
        LogOperationDTO logOperation = LogOperationDTO.builder()
            .dateTimeOperation(LocalDateTime.now()).oldValue(toJson(dtoOld))
            .newValue(toJson(dtoNew)).operation(OperationEnum.UPDATE)
            .type(dtoNew.getClass().getCanonicalName())
            .valueId(dtoNew.getId()).userCode(userCode).userName(userName)
            .build();
        log(logOperation, entity);
      }
    } catch (Exception e) {
      log.error(e.getLocalizedMessage(), e);
      throw new RuntimeException(e.getLocalizedMessage(), e);
    } finally {
      getContext().clear();
    }
  }

  /**
   * Avalia se é necessário realizar o log da entidade
   *
   * @param params {@link Map} de parâmetros
   * @param entity {@link BaseEntity} a ser salvo
   * @return <code>true</code> se for para realizar o log da entidade
   */
  private boolean evaluate(final Map<String, Object> params,
                           final BaseEntityDTO<?> entity) {
    if (entity == null) {
      return false;
    }
    return !LogOperationDTO.class.isAssignableFrom(entity.getClass());
  }

  /**
   * @param map parametros
   * @return objeto excluído
   */
  private BaseEntityDTO<?> getDeletedObject(Map<String, Object> map) {
    return (BaseEntityDTO<?>) map.get(DELETE_ENTITY_PARAMETER);
  }

  /**
   * @param map parametros
   * @return objeto inserido
   */
  private BaseEntityDTO<?> getInsertedObject(Map<String, Object> map) {
    return (BaseEntityDTO<?>) map.get(INSERT_ENTITY_PARAMETER);
  }

  /**
   * @param map parametros
   * @return objeto antigo
   */
  private BaseEntityDTO<?> getOldObject(Map<String, Object> map) {
    return (BaseEntityDTO<?>) map.get(UPDATE_ENTITY_OLD_OBJECT_PARAMETER);
  }

  /**
   * @param map parametros
   * @return objeto atualizado
   */
  private BaseEntityDTO<?> getUpdatedObject(Map<String, Object> map) {
    return (BaseEntityDTO<?>) map
        .get(UPDATE_ENTITY_UPDATED_OBJECT_PARAMETER);
  }

  /**
   * @param map parâmetros
   * @return código do usuário
   */
  private String getUserCode(Map<String, Object> map) {
    return (String) map.get(USER_CODE_PARAMETER);
  }

  /**
   * @param map parâmetros
   * @return nome do usuário
   */
  private String getUserName(Map<String, Object> map) {
    return (String) map.get(USER_NAME_PARAMETER);
  }

  /**
   * Inicializa os escutadores para log das operações
   */
  protected void initLogOperationListeners() {
    setEvaluator((map, entity) -> this.evaluate(map,
                                                (BaseEntityDTO<?>) entity));
    addOperationListener(LogOperationEnum.AFTER_DELETE,
                         (map, entity) -> this
                             .logDelete(map, (BaseEntityDTO<?>) entity));
    addOperationListener(LogOperationEnum.AFTER_PERSIST,
                         (map, entity) -> this
                             .logInsert(map, (BaseEntityDTO<?>) entity));
    addOperationListener(LogOperationEnum.AFTER_UPDATE,
                         (map, entity) -> this
                             .logUpdate(map, (BaseEntityDTO<?>) entity));
  }

  /**
   * @param dto DTO a ser convertido
   * @return String contendo a representação em JSON do DTO
   * @throws IOException caso ocorra exceção ao converter o objeto para JSON.
   */
  protected String toJson(DTO<?> dto)
    throws IOException {
    return JsonUtil.toJson(dto);

  }

  /**
   * Realiza a operação de log sobre um objeto. Este método deve ser executado
   * de forma síncrona para garantir que o log seja persistido na mesma
   * transação que o objeto logado.
   *
   * @param logOperation {@link LogOperationDTO}
   * @param entity       {@link BaseEntityDTO} salva
   * @return {@link LogOperationDTO} salvo.
   */
  protected LogOperationDTO log(final LogOperationDTO logOperation,
                                BaseEntityDTO<?> entity) {
    try {
      LogOperationDTO saved = save(logOperation);
      log.info("{}", saved);
      return saved;
    } catch (Exception e) {
      log.error(e.getLocalizedMessage(), e);
      throw new RuntimeException(e.getLocalizedMessage(), e);
    }
  }

  /**
   * @param <D> Tipo do objeto
   * @param dto objeto excluído. Geralmente utilizado na exclusão.
   */
  private <D extends DTO<?>> void putDeleteObject(D dto) {
    putObject(LogOperationService.DELETE_ENTITY_PARAMETER, dto);
  }

  /**
   * @param <D> Tipo do objeto
   * @param dto o objeto inserido. Geralmente utilizado para inserção
   */
  private <D extends DTO<?>> void putPersistedObject(D dto) {
    putObject(LogOperationService.INSERT_ENTITY_PARAMETER, dto);
  }

  /**
   * @param <D> Tipo do objeto
   * @param dto objeto antigo. Geralmente utilizando para atualização.
   */
  private <D extends DTO<?>> void putOldObject(D dto) {
    putObject(LogOperationService.UPDATE_ENTITY_OLD_OBJECT_PARAMETER, dto);
  }

  /**
   * @param <D> Tipo do objeto
   * @param dto objeto atualizado. Geralmente utilizado na atualização.
   */
  private <D extends DTO<?>> void putUpdatedObject(D dto) {
    putObject(LogOperationService.UPDATE_ENTITY_UPDATED_OBJECT_PARAMETER,
              dto);
  }

  /**
   * Atribui um consumidor dos parâmetros para composição de detalhes do
   * usuário.
   *
   * @param consumer {@link UserDetailsConsumer}
   */
  public void setUserDetails(UserDetailsConsumer consumer) {
    if (operationSetUserDetailsListenerRemover != null) {
      operationSetUserDetailsListenerRemover.run();
    }
    operationSetUserDetailsListenerRemover = addOperationListener(LogOperationEnum.ANY,
                                                                  (map,
                                                                   object) -> {
                                                                    consumer
                                                                        .accept(USER_CODE_PARAMETER,
                                                                                USER_NAME_PARAMETER,
                                                                                map);
                                                                  });
  }

  /**
   * Adiciona um escutador de operações
   *
   * @param operation {@link LogOperationEnum}
   * @param consumer  {@link BiConsumer}
   * @return {@link Runnable} para remoção do escutador.
   */
  public static Runnable addOperationListener(final LogOperationEnum operation,
                                              final BiConsumer<Map<String, Object>, DTO<? extends BaseEntity>> consumer) {
    Collection<BiConsumer<Map<String, Object>, DTO<? extends BaseEntity>>> operationListener = listeners
        .getOrDefault(operation, new CopyOnWriteArraySet<>());
    operationListener.add(consumer);
    listeners.put(operation, operationListener);
    return () -> operationListener.remove(consumer);
  }

  /**
   * Atribui um objeto de mapa no contexto
   *
   * @param key   chave
   * @param value valor
   */
  private static void putObject(String key, Object value) {
    if (value != null && key != null) {
      getContext().put(key, value);
    }
  }

  /**
   * Avaliador de log. Permite que seja atribuído mecanismo para decidir se um
   * objeto será logado ou não.
   *
   * @param evaluator {@link BiFunction} com mapa de parâmetros e objeto no
   *                  contexto
   */
  public static void setEvaluator(BiFunction<Map<String, Object>, DTO<? extends BaseEntity>, Boolean> evaluator) {
    listenerEvaluator = evaluator;
  }

  /**
   * Dispara uma ação para um dado objeto
   *
   * @param <D>       Tipo do DTO
   * @param entity    objeto da ação
   * @param operation {@link LogOperationEnum} da ação
   */
  private <D extends DTO<? extends BaseEntity>> void dispatch(final D entity,
                                                              final LogOperationEnum operation) {
    Map<String, Object> params = getContext();
    if (listenerEvaluator.apply(params, entity)) {
      log.info("{} -> {}", operation, entity.getClass());
      Collection<BiConsumer<Map<String, Object>, DTO<? extends BaseEntity>>> operationListener = listeners
          .getOrDefault(operation, new CopyOnWriteArraySet<>());
      operationListener.forEach(consumer -> {
        consumer.accept(params, entity);
      });
    }
  }

  /**
   * Depois de persistir
   *
   * @param <D>    Tipo do dado
   * @param entity entidade atual
   */
  private <D extends DTO<? extends BaseEntity>> void postPersisted(final D entity) {
    dispatch(entity, LogOperationEnum.ANY);
    dispatch(entity, LogOperationEnum.AFTER_PERSIST);
  }

  /**
   * Depois de excluir
   *
   * @param <D>    Tipo do dado
   * @param entity entidade atual
   */
  private <D extends DTO<? extends BaseEntity>> void postDeleted(final D entity) {
    dispatch(entity, LogOperationEnum.ANY);
    dispatch(entity, LogOperationEnum.AFTER_DELETE);
  }

  /**
   * Depois de atualizar
   *
   * @param <D>    Tipo do dado
   * @param entity entidade atual
   */
  private <D extends DTO<? extends BaseEntity>> void postUpdated(final D entity) {
    dispatch(entity, LogOperationEnum.ANY);
    dispatch(entity, LogOperationEnum.AFTER_UPDATE);
  }

  /**
   * Antes de excluir
   *
   * @param <D>    Tipo do dado
   * @param entity entidade atual
   */
  private <D extends DTO<? extends BaseEntity>> void preDelete(final D entity) {
    dispatch(entity, LogOperationEnum.ANY);
    dispatch(entity, LogOperationEnum.BEFORE_DELETE);
  }

  /**
   * Antes de persistir
   *
   * @param <D>    Tipo do dado
   * @param entity entidade atual
   */
  private <D extends DTO<? extends BaseEntity>> void prePersist(final D entity) {
    dispatch(entity, LogOperationEnum.ANY);
    dispatch(entity, LogOperationEnum.BEFORE_PERSIST);
  }

  /**
   * Antes de atualizar
   *
   * @param <D>    Tipo do dado
   * @param entity entidade atual
   */
  private <D extends DTO<? extends BaseEntity>> void preUpdate(final D entity) {
    dispatch(entity, LogOperationEnum.ANY);
    dispatch(entity, LogOperationEnum.BEFORE_UPDATE);
  }

  /**
   * Antes de salvar
   *
   * @param <T>        Tipo da entidade
   * @param <D>        Tipo do DTO
   * @param toSave     DTO a ser salvo
   * @param repository {@link BaseEntityRepository}
   * @param mapper     {@link BaseMapper}
   * @return {@link DTO} a ser salvo.
   */
  public <T extends BaseEntity, D extends DTO<T>> D logBeforeSave(final D toSave,
                                                                  BaseEntityRepository<T> repository,
                                                                  BaseMapper<T, D> mapper) {
    T model = mapper.toModel(toSave);
    boolean isUpdating = model.getId() != null;
    if (isUpdating) {
      model = repository.findById(model.getId()).orElse(null);
      D oldDTO = mapper.toDTO(model);
      putOldObject(oldDTO);
      preUpdate(toSave);
    } else {
      prePersist(toSave);
    }
    return toSave;
  }

  /**
   * Depois de salvar
   *
   * @param <T>        Tipo da entidade
   * @param <D>        Tipo do DTO
   * @param toSave     DTO a ser salvo
   * @param saved      DTO salvo
   * @param repository {@link BaseEntityRepository}
   * @param mapper     {@link BaseMapper}
   * @return {@link DTO} que foi salvo
   */
  public <T extends BaseEntity, D extends DTO<T>> D logAfterSave(final D toSave,
                                                                 final D saved,
                                                                 BaseEntityRepository<T> repository,
                                                                 BaseMapper<T, D> mapper) {
    T model = mapper.toModel(toSave);
    boolean isUpdating = model.getId() != null;
    if (isUpdating) {
      putUpdatedObject(saved);
      postUpdated(saved);
    } else {
      putPersistedObject(saved);
      postPersisted(saved);
    }
    return saved;
  }

  /**
   * Depois de excluir
   *
   * @param <T>        Tipo da entidade
   * @param <D>        Tipo do DTO
   * @param entity     Entidade excluída
   * @param repository {@link BaseEntityRepository}
   * @param mapper     {@link BaseMapper}
   * @return {@link DTO} que foi excluído
   */
  public <T extends BaseEntity, D extends DTO<T>> D logAfterDelete(final D entity,
                                                                   BaseEntityRepository<T> repository,
                                                                   BaseMapper<T, D> mapper) {
    postDeleted(entity);
    return entity;
  }

  /**
   * Antes de excluir
   *
   * @param <T>        Tipo da entidade
   * @param <D>        Tipo do DTO
   * @param id         identificador
   * @param repository {@link BaseEntityRepository}
   * @param mapper     {@link BaseMapper}
   * @return {@link DTO} que será excluído
   */
  public <T extends BaseEntity, D extends DTO<T>> D logBeforeDelete(final UUID id,
                                                                    BaseEntityRepository<T> repository,
                                                                    BaseMapper<T, D> mapper) {
    T model = repository.findById(id).orElse(null);
    D dto = mapper.toDTO(model);
    putDeleteObject(dto);
    preDelete(dto);
    return dto;
  }

  /**
   * Interface funcional que define os parâmetros de serão inseridos do mapa de
   * parâmetros para utilização do log
   *
   * @author Israel Araújo
   */
  @FunctionalInterface
  public static interface UserDetailsConsumer {
    /**
     * Aceita os dados
     *
     * @param userCodeLabel Nome do parâmetro de código do usuário
     * @param userNameLabel Nome do parâmetro de nome do usuário
     * @param map           Mapa de parâmetros
     */
    void accept(String userCodeLabel, String userNameLabel,
                Map<String, Object> map);
  }

  /**
   * Operações de LOG
   */
  public static enum LogOperationEnum {
    /** Antes de persistir */
    BEFORE_PERSIST,
    /** Antes de atualizar */
    BEFORE_UPDATE,
    /** Antes de deletar */
    BEFORE_DELETE,
    /** Depois de persistir */
    AFTER_PERSIST,
    /** Depois de atualizar */
    AFTER_UPDATE,
    /** Depois de deletar */
    AFTER_DELETE,
    /** Em qualquer operação */
    ANY;
  }

}
