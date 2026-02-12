package com.ia.core.service.event;

/**
 * Enum representando os tipos de operação CRUD padrão para eventos de domínio.
 * <p>
 * Este enum é usado como {@link EventType} para publicação de eventos
 * nas operações de Create, Update e Delete.
 * </p>
 * 
 * @author Israel Araújo
 */
public enum CrudOperationType implements EventType {

  /** Entidade foi criada */
  CREATED,

  /** Entidade foi atualizada */
  UPDATED,

  /** Entidade foi excluída */
  DELETED;

  /**
   * Verifica se a operação é de criação.
   *
   * @return true se for CREATED
   */
  public boolean isCreated() {
    return this == CREATED;
  }

  /**
   * Verifica se a operação é de atualização.
   *
   * @return true se for UPDATED
   */
  public boolean isUpdated() {
    return this == UPDATED;
  }

  /**
   * Verifica se a operação é de exclusão.
   *
   * @return true se for DELETED
   */
  public boolean isDeleted() {
    return this == DELETED;
  }
}
