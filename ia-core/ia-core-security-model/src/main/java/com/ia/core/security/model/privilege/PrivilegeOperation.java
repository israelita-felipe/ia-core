package com.ia.core.security.model.privilege;

import com.ia.core.model.BaseEntity;
import com.ia.core.security.model.SecurityModel;
import com.ia.core.security.model.functionality.OperationEnum;
import jakarta.persistence.*;
import lombok.*;
import lombok.Builder.Default;
import lombok.experimental.SuperBuilder;

import java.util.Collection;
import java.util.HashSet;

@Entity
@Table(name = PrivilegeOperation.TABLE_NAME,
       schema = PrivilegeOperation.SCHEMA_NAME)
/**
 * Classe que representa a entidade de domínio privilege operation.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a PrivilegeOperation
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
 */
@Getter
@Setter
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class PrivilegeOperation
  extends BaseEntity {
  /** Serial UID */
  private static final long serialVersionUID = 8203818908291338195L;

  /** NOME DA TABELA */
  public static final String TABLE_NAME = SecurityModel.TABLE_PREFIX
      + "PRIVILEGE_OPERATION";

  /** NOME DO SCHEMA */
  public static final String SCHEMA_NAME = SecurityModel.SCHEMA;

  @Column(name = "operation", nullable = false)
  private OperationEnum operation;

  @Default
  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL,
             orphanRemoval = true, mappedBy = "privilegeOperation")
  private Collection<PrivilegeOperationContext> context = new HashSet<>();

  /**
   * Retorna a coleção de contextos de operação.
   * <p>
   * <b>Security Note:</b> Retorna uma visão imutável da coleção para
   * evitar modificações externas não autorizadas.
   *
   * @bugfix SECURITY: Retorna unmodifiableCollection para proteger
   *         a coleção de contextos de operação de privilégio.
   *
   * @return coleção imutável de contextos
   */
  public Collection<PrivilegeOperationContext> getContext() {
    return java.util.Collections.unmodifiableCollection(context);
  }

}
