package com.ia.core.model;

/**
 * Interface que define o contrato para entidades com controle de versão.
 * <p>
 * Utilizada para implementar lock otimista no JPA, prevenindo conflitos de
 * concorrência em ambientes com múltiplas transações simultâneas.
 * <p>
 * <b>Por quê usar HasVersion?</b>
 * </p>
 * <ul>
 * <li>Garante integridade de dados em cenários de concorrência</li>
 * <li>Evita o problema de "lost update" em transações simultâneas</li>
 * <li>Proporciona detecção automática de conflitos pelo JPA</li>
 * </ul>
 *
 * @see BaseEntity
 * @since 1.0.0
 * @author Israel Araújo
 */
public interface HasVersion<T> {
  /**
   * Versão default utilizada quando uma nova entidade é criada.
   */
  public static final Long DEFAULT_VERSION = 1l;

  /**
   * Obtém a versão atual da entidade para controle de lock otimista.
   *
   * @return a versão atual da entidade, nunca {@code null}
   */
  T getVersion();

  /**
   * Define a versão da entidade.
   * <p>
   * Normalmente gerenciado automaticamente pelo JPA, mas pode ser manipulado em
   * cenários específicos de migracao ou testes.
   *
   * @param version a nova versão da entidade, não pode ser {@code null}
   * @throws IllegalArgumentException se a versão for {@code null} ou negativa
   */
  void setVersion(T version);
}
