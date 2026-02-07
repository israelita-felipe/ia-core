package com.ia.core.security.model.role;

import java.util.Collection;
import java.util.HashSet;

import com.ia.core.model.BaseEntity;
import com.ia.core.security.model.SecurityModel;
import com.ia.core.security.model.user.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder.Default;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = Role.TABLE_NAME, schema = Role.SCHEMA_NAME)
@Getter
@Setter
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Role
  extends BaseEntity {

  /** NOME DA TABELA */
  public static final String TABLE_NAME = SecurityModel.TABLE_PREFIX
      + "ROLE";
  /** NOME DO SCHEMA */
  public static final String SCHEMA_NAME = SecurityModel.SCHEMA;

  @Column(name = "name", length = 500, nullable = false)
  private String name;

  @ManyToMany
  @JoinTable(name = SecurityModel.TABLE_PREFIX + "users_roles",
             schema = SCHEMA_NAME,
             joinColumns = @JoinColumn(name = "role_id",
                                       referencedColumnName = "id"),
             inverseJoinColumns = @JoinColumn(name = "user_id",
                                              referencedColumnName = "id"),
             uniqueConstraints = @UniqueConstraint(columnNames = {
                 "user_id", "role_id" }))
  private Collection<User> users = new HashSet<>();

  @Default
  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY,
             mappedBy = "role", orphanRemoval = true)
  private Collection<RolePrivilege> privileges = new HashSet<>();
}
