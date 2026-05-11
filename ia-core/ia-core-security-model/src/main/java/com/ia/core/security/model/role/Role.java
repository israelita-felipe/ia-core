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
/**
 * Classe que representa a entidade de domínio role.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a Role
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
 */

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

  /**
   * Retorna a coleção de usuários com esta role.
   * <p>
   * <b>Security Note:</b> Retorna visão imutável para evitar modificações
   * não autorizadas na associação role-usuário.
   *
   * @bugfix SECURITY: Retorna unmodifiableCollection (era mutável).
   *
   * @return coleção imutável de usuários
   */
  public Collection<User> getUsers() {
    return java.util.Collections.unmodifiableCollection(users);
  }

  @Default
  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY,
             mappedBy = "role", orphanRemoval = true)
  private Collection<RolePrivilege> privileges = new HashSet<>();

  /**
   * Retorna a coleção de privilégios desta role.
   * <p>
   * <b>Security Note:</b> Retorna visão imutável. A modificação de privilégios
   * deve ser feita através dos métodos de negócio (addPrivilege, removePrivilege)
   * que garantam auditoria e validação.
   *
   * @bugfix SECURITY: Retorna unmodifiableCollection (era mutável).
   *
   * @return coleção imutável de privilégios da role
   */
  public Collection<RolePrivilege> getPrivileges() {
    return java.util.Collections.unmodifiableCollection(privileges);
  }
}
