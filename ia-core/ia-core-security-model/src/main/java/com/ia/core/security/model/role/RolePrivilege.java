package com.ia.core.security.model.role;

import java.util.HashSet;
import java.util.Set;

import com.ia.core.model.BaseEntity;
import com.ia.core.security.model.SecurityModel;
import com.ia.core.security.model.privilege.Privilege;
import com.ia.core.security.model.privilege.PrivilegeOperation;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder.Default;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = RolePrivilege.TABLE_NAME, schema = RolePrivilege.SCHEMA_NAME)
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class RolePrivilege
  extends BaseEntity {

  /** NOME DA TABELA */
  public static final String TABLE_NAME = SecurityModel.TABLE_PREFIX
      + "ROLE_PRIVILEGE";
  /** NOME DA TABELA */
  public static final String USER_PRIVILEGE_OPERATION_TABLE_NAME = SecurityModel.TABLE_PREFIX
      + "ROLE_PRIVILEGE_OPERATION";
  /** NOME DO SCHEMA */
  public static final String SCHEMA_NAME = SecurityModel.SCHEMA;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "privilege_id", referencedColumnName = "id")
  private Privilege privilege;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "role_id", referencedColumnName = "id")
  private Role role;

  @Default
  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY,
             orphanRemoval = true)
  @JoinTable(name = USER_PRIVILEGE_OPERATION_TABLE_NAME,
             schema = SCHEMA_NAME,
             joinColumns = @JoinColumn(name = "ROLE_PRIVILEGE",
                                       referencedColumnName = "id"),
             inverseJoinColumns = @JoinColumn(name = "PRIVILEGE_OPERATION",
                                              referencedColumnName = "id"),
             uniqueConstraints = @UniqueConstraint(columnNames = {
                 "ROLE_PRIVILEGE", "PRIVILEGE_OPERATION" }))
  private Set<PrivilegeOperation> operations = new HashSet<>();

}
