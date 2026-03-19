package com.ia.core.service;

import com.ia.core.model.BaseEntity;
import com.ia.core.service.context.ServiceExecutionContext;
import com.ia.core.service.dto.DTO;
import com.ia.core.service.dto.entity.AbstractBaseEntityDTO;
import com.ia.core.service.event.CrudOperationType;
import com.ia.core.service.exception.ServiceException;
import com.ia.core.service.repository.BaseEntityRepository;
import com.ia.core.service.strategy.OperationTypeStrategy;

/**
 * Interface que salva um {@link BaseEntity} por meio de um
 * {@link BaseEntityRepository}
 * <p>
 * Para publicação de eventos de domínio, sobrescreva os métodos:
 * </p>
 * <ul>
 *   <li>{@link #beforeSave(D)} - Chamado antes de salvar</li>
 *   <li>{@link #afterSave(D, D, CrudOperationType)} - Chamado após salvar</li>
 * </ul>
 *
 * @author Israel Araújo
 * @param <T> {@link BaseEntity}
 * @param <D> {@link DTO}
 */
public interface SaveBaseService<T extends BaseEntity, D extends DTO<?>>
  extends BaseService<T, D>, ValidationBaseService<T, D> {

  /**
   * Método chamado antes de salvar o DTO.
   * <p>
   * Sobrescreva este método para executar lógica antes do save,
   * como validações adicionais ou publicação de eventos.
   * </p>
   *
   * @param toSave DTO a ser salvo
   * @throws ServiceException em caso de erro
   */
  default void beforeSave(D toSave) throws ServiceException {
    // Default: sem ação
  }

  /**
   * Método chamado após salvar o DTO.
   * <p>
   * Sobrescreva este método para executar lógica após o save,
   * como publicação de eventos de domínio.
   * </p>
   *
   * @param original     DTO original (antes do save)
   * @param saved       DTO salvo (após o save)
   * @param operationType Tipo de operação (CREATED ou UPDATED)
   * @throws ServiceException em caso de erro
   */
  default void afterSave(D original, D saved, CrudOperationType operationType)
    throws ServiceException {
    // Default: sem ação
  }

  /**
   * Verifica se o objeto pode ser criado
   *
   * @param toSave Objeto a ser salvo
   * @return true se puder criar, false caso contrário
   */
  default boolean canCreate(D toSave) {
    return toSave != null;
  }

  /**
   * Verifica se o objeto pode ser atualizado
   *
   * @param toSave objeto a ser salvo
   * @return true se puder atualizar, false caso contrário
   */
  default boolean canUpdate(D toSave) {
    return toSave != null;
  }

  /**
   * Salva um {@link DTO} e retorna um {@link DTO}.
   *
   * @param toSave Objeto a ser salvo (criado ou atualizado).
   * @return {@link DTO}
   * @throws ServiceException exceção lançada ao validar o dto
   * @see ValidationBaseService
   */
  default D save(D toSave)
    throws ServiceException {
    beforeSave(toSave);
    ServiceException ex = new ServiceException();
    ServiceExecutionContext<T, D> context = new ServiceExecutionContext<>(toSave);
    D savedEntity = onTransaction(() -> executeInTransaction(context, ex));
    throwIfHasErrors(ex);
    if (savedEntity != null) {
      CrudOperationType operationType = determineOperationType(toSave, savedEntity);
      afterSave(toSave, savedEntity, operationType);
    }
    return savedEntity;
  }

  /**
   * Executa a lógica de transação para salvar a entidade.
   * <p>
   * Este método encapsula a lógica de validação, conversão, sincronização e persistência
   * dentro de um contexto de execução gerenciado.
   * </p>
   *
   * @param context Contexto de execução contendo o DTO e estado
   * @param ex Exceção para acumular erros
   * @return DTO salvo ou null se houver erros ou validação falhar
   */
  private D executeInTransaction(ServiceExecutionContext<T, D> context, ServiceException ex) {
    D toSave = context.getToSave();
    try {
      validate(toSave);
      T model = toModel(toSave);
      model = synchronize(model);
      boolean isUpdate = model.getId() != null;
      if (isUpdate && !canUpdate(toSave)) {
        return null;
      } else if (!isUpdate && !canCreate(toSave)) {
        return null;
      }
      T saved = getRepository().save(model);
      return toDTO(saved);
    } catch (Exception e) {
      ex.add(e);
    }
    return null;
  }

  /**
   * Determina o tipo de operação (CREATE ou UPDATE) com base nos DTOs.
   * <p>
   * Implementação que verifica se o DTO salvo extende AbstractBaseEntityDTO e tem ID.
   * </p>
   * <p>
   * Para comportamento customizado, sobrescreva este método em sua implementação.
   * </p>
   *
   * @param original DTO original antes do save
   * @param saved DTO salvo após o save
   * @return Tipo de operação determined
   */
  /**
   * Determina o tipo de operação CRUD usando OperationTypeStrategy.
   * <p>
   * Este método usa a strategy para determinar se a operação é CREATE ou UPDATE,
   * seguindo o padrão Strategy para permitir customização.
   * </p>
   *
   * @param original DTO original antes do save
   * @param saved DTO salvo após o save
   * @return Tipo de operação determinado
   */
  default CrudOperationType determineOperationType(D original, D saved) {    
      return createOperationTypeStrategy().determine(
        (AbstractBaseEntityDTO<?>) original,
        (AbstractBaseEntityDTO<?>) saved
      );    
  }

  /**
   * Cria uma estratégia para determinar o tipo de operação CRUD.
   * <p>
   * Use este método para obter uma estratégia customizável para determinar
   * se uma operação é CREATE ou UPDATE.
   * </p>
   * <pre>
   * OperationTypeStrategy&lt;MeuDTO&gt; strategy = createOperationTypeStrategy();
   * CrudOperationType type = strategy.determine(dtoOriginal, dtoSalvo);
   * </pre>
   *
   * @param <DTO> Tipo do DTO
   * @return Estratégia de determination de tipo de operação
   */
  static <DTO extends AbstractBaseEntityDTO<?>> OperationTypeStrategy<DTO> createOperationTypeStrategy() {
    return OperationTypeStrategy.defaultStrategy();
  }

  /**
   * Realiza a syncronização do modelo com a base de dados, ou outras
   * associações
   *
   * @param model Objeto a ser sincronizado
   * @return por padrão retorna o modelo passado como parâmetro sem modificações
   * @throws ServiceException caso ocorra algum erro no processo
   */
  default T synchronize(T model)
    throws ServiceException {
    return model;
  }

}
