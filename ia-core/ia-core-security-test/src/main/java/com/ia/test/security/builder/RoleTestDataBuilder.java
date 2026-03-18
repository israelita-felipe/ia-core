package com.ia.test.security.builder;

import java.util.Collection;
import java.util.HashSet;
import java.util.UUID;

import com.ia.core.security.model.privilege.Privilege;
import com.ia.core.security.model.role.Role;
import com.ia.core.security.model.role.RolePrivilege;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Builder para criar instâncias de {@link Role} com dados de teste.
 * Segue o padrão Builder para facilitar a criação de dados de teste com valores padrão.
 *
 * @author Israel Araújo
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RoleTestDataBuilder {

  private String name = "ROLE_" + UUID.randomUUID().toString().substring(0, 8);
  private Collection<RolePrivilege> privileges = new HashSet<>();

  /**
   * Cria uma nova instância do builder.
   *
   * @return novo RoleTestDataBuilder
   */
  public static RoleTestDataBuilder role() {
    return new RoleTestDataBuilder();
  }

  /**
   * Define o nome da role.
   *
   * @param name nome da role
   * @return this para encadeamento
   */
  public RoleTestDataBuilder withName(String name) {
    this.name = name;
    return this;
  }

  /**
   * Adiciona um privilégio à role.
   *
   * @param rolePrivilege privilégio a adicionar
   * @return this para encadeamento
   */
  public RoleTestDataBuilder withRolePrivilege(RolePrivilege rolePrivilege) {
    this.privileges.add(rolePrivilege);
    return this;
  }

  /**
   * Define os privilégios da role.
   *
   * @param rolePrivileges coleção de privilégios
   * @return this para encadeamento
   */
  public RoleTestDataBuilder withRolePrivileges(Collection<RolePrivilege> rolePrivileges) {
    this.privileges = new HashSet<>(rolePrivileges);
    return this;
  }

  /**
   * Constrói e retorna uma nova instância de {@link Role} com os dados configurados.
   *
   * @return nova instância de Role
   */
  public Role build() {
    Role role = new Role();
    role.setName(this.name);
    role.setPrivileges(this.privileges);
    return role;
  }
}
