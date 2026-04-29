package com.ia.core.security.model.user;

import com.ia.core.model.BaseEntity;
import com.ia.core.security.model.SecurityModel;
import com.ia.core.security.model.privilege.Privilege;
import com.ia.core.security.model.privilege.PrivilegeOperation;
import jakarta.persistence.*;
import lombok.*;
import lombok.Builder.Default;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;
/**
 * Classe que representa a entidade de domínio user privilege.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a UserPrivilege
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
 */

@Entity
@Table(name = UserPrivilege.TABLE_NAME, schema = UserPrivilege.SCHEMA_NAME)
@Getter
@Setter
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class UserPrivilege
  extends BaseEntity {

  /** NOME DA TABELA */
  public static final String TABLE_NAME = SecurityModel.TABLE_PREFIX
      + "USER_PRIVILEGE";
  /** NOME DA TABELA */
  public static final String USER_PRIVILEGE_OPERATION_TABLE_NAME = SecurityModel.TABLE_PREFIX
      + "USER_PRIVILEGE_OPERATION";
  /** NOME DO SCHEMA */
  public static final String SCHEMA_NAME = SecurityModel.SCHEMA;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "privilege_id", referencedColumnName = "id")
  private Privilege privilege;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "user_id", referencedColumnName = "id")
  private User user;

  @Default
  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY,
             orphanRemoval = true)
  @JoinTable(name = USER_PRIVILEGE_OPERATION_TABLE_NAME,
             schema = SCHEMA_NAME,
             joinColumns = @JoinColumn(name = "USER_PRIVILEGE",
                                       referencedColumnName = "id"),
             inverseJoinColumns = @JoinColumn(name = "PRIVILEGE_OPERATION",
                                              referencedColumnName = "id"),
             uniqueConstraints = @UniqueConstraint(columnNames = {
                 "USER_PRIVILEGE", "PRIVILEGE_OPERATION" }))
  private Set<PrivilegeOperation> operations = new HashSet<>();

}
