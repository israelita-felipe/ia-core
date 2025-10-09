package com.ia.core.security.model.role;

import java.util.Collection;
import java.util.HashSet;

import com.ia.core.model.BaseEntity;
import com.ia.core.security.model.SecurityModel;
import com.ia.core.security.model.privilege.Privilege;
import com.ia.core.security.model.user.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = Role.TABLE_NAME, schema = Role.SCHEMA_NAME)
@Data
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

  @ManyToMany
  @JoinTable(name = SecurityModel.TABLE_PREFIX + "roles_privileges",
             schema = SCHEMA_NAME,
             joinColumns = @JoinColumn(name = "role_id",
                                       referencedColumnName = "id"),
             inverseJoinColumns = @JoinColumn(name = "privilege_id",
                                              referencedColumnName = "id"),
             uniqueConstraints = @UniqueConstraint(columnNames = {
                 "role_id", "privilege_id" }))
  private Collection<Privilege> privileges = new HashSet<>();
}
