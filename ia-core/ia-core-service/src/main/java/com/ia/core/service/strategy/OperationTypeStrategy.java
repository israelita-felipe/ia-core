package com.ia.core.service.strategy;

import com.ia.core.service.dto.entity.AbstractBaseEntityDTO;
import com.ia.core.service.event.CrudOperationType;

/**
 * Strategy interface para determinar o tipo de operação CRUD.
 * <p>
 * Implementações desta interface definem a lógica para determinar se uma
 * operação é CREATE ou UPDATE com base nos DTOs original e salvo.
 * </p>
 *
 * <h2>Uso</h2>
 * <pre>
 * OperationTypeStrategy<MeuDTO> strategy = new DefaultOperationTypeStrategy<>();
 * CrudOperationType type = strategy.determine(dtoOriginal, dtoSalvo);
 * </pre>
 *
 * @param <D> Tipo do DTO que extende {@link AbstractBaseEntityDTO}
 */
/**
 * Classe que representa a estratégia de operation type.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a OperationTypeStrategy
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
 */
@FunctionalInterface
public interface OperationTypeStrategy<D extends AbstractBaseEntityDTO<?>> {

  /**
   * Determina o tipo de operação CRUD.
   *
   * @param original DTO original antes da operação de save
   * @param saved DTO após a operação de save
   * @return {@link CrudOperationType#CREATED} se for uma nova entidade,
   *         ou {@link CrudOperationType#UPDATED} se for uma atualização
   */
  CrudOperationType determine(D original, D saved);

  /**
   * Implementação padrão que verifica se o DTO salvo tem ID.
   * <p>
   * Esta implementação considera que:
   * </p>
   * <ul>
   *   <li>Se saved.getId() != null → UPDATE</li>
   *   <li>Caso contrário → CREATE</li>
   * </ul>
   *
   * @param <D> Tipo do DTO que extende {@link AbstractBaseEntityDTO}
   * @return Strategy padrão
   */
  static <D extends AbstractBaseEntityDTO<?>> OperationTypeStrategy<D> defaultStrategy() {
    return (original, saved) -> {
      if (saved != null && saved.getId() != null) {
        return CrudOperationType.UPDATED;
      }
      return CrudOperationType.CREATED;
    };
  }
}
