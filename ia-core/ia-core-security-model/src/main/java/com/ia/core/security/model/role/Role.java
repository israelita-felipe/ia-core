package com.ia.core.security.model.role;

import com.ia.core.model.BaseEntity;
import com.ia.core.security.model.SecurityModel;
import com.ia.core.security.model.user.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.Builder.Default;
import lombok.experimental.SuperBuilder;

import java.util.Collection;
import java.util.HashSet;

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
