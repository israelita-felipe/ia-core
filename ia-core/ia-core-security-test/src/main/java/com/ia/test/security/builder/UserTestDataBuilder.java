package com.ia.test.security.builder;

import com.ia.core.security.model.role.Role;
import com.ia.core.security.model.user.User;
import com.ia.core.security.model.user.UserPrivilege;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.HashSet;
import java.util.UUID;

/**
 * Builder para criar instâncias de {@link User} com dados de teste.
 * Segue o padrão Builder para facilitar a criação de dados de teste com valores padrão.
 *
 * @author Israel Araújo
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserTestDataBuilder {

  private String userName = "testuser-" + UUID.randomUUID();
  private String userCode = "USR-" + UUID.randomUUID().toString().substring(0, 8);
  private String password = "encoded_password_123";
  private boolean enabled = true;
  private boolean accountNotExpired = true;
  private boolean accountNotLocked = true;
  private boolean credentialsNotExpired = true;
  private Collection<Role> roles = new HashSet<>();
  private Collection<UserPrivilege> privileges = new HashSet<>();

  /**
   * Cria uma nova instância do builder.
   *
   * @return novo UserTestDataBuilder
   */
  public static UserTestDataBuilder user() {
    return new UserTestDataBuilder();
  }

  /**
   * Define o nome do usuário.
   *
   * @param userName nome do usuário
   * @return this para encadeamento
   */
  public UserTestDataBuilder withUserName(String userName) {
    this.userName = userName;
    return this;
  }

  /**
   * Define o código do usuário.
   *
   * @param userCode código único do usuário
   * @return this para encadeamento
   */
  public UserTestDataBuilder withUserCode(String userCode) {
    this.userCode = userCode;
    return this;
  }

  /**
   * Define a senha do usuário.
   *
   * @param password senha codificada
   * @return this para encadeamento
   */
  public UserTestDataBuilder withPassword(String password) {
    this.password = password;
    return this;
  }

  /**
   * Define se o usuário está habilitado.
   *
   * @param enabled estado habilitado
   * @return this para encadeamento
   */
  public UserTestDataBuilder withEnabled(boolean enabled) {
    this.enabled = enabled;
    return this;
  }

  /**
   * Define se a conta não está expirada.
   *
   * @param accountNotExpired estado da expiração
   * @return this para encadeamento
   */
  public UserTestDataBuilder withAccountNotExpired(boolean accountNotExpired) {
    this.accountNotExpired = accountNotExpired;
    return this;
  }

  /**
   * Define se a conta não está bloqueada.
   *
   * @param accountNotLocked estado de bloqueio
   * @return this para encadeamento
   */
  public UserTestDataBuilder withAccountNotLocked(boolean accountNotLocked) {
    this.accountNotLocked = accountNotLocked;
    return this;
  }

  /**
   * Define se as credenciais não estão expiradas.
   *
   * @param credentialsNotExpired estado das credenciais
   * @return this para encadeamento
   */
  public UserTestDataBuilder withCredentialsNotExpired(boolean credentialsNotExpired) {
    this.credentialsNotExpired = credentialsNotExpired;
    return this;
  }

  /**
   * Adiciona uma role ao usuário.
   *
   * @param role role a adicionar
   * @return this para encadeamento
   */
  public UserTestDataBuilder withRole(Role role) {
    this.roles.add(role);
    return this;
  }

  /**
   * Define as roles do usuário.
   *
   * @param roles coleção de roles
   * @return this para encadeamento
   */
  public UserTestDataBuilder withRoles(Collection<Role> roles) {
    this.roles = new HashSet<>(roles);
    return this;
  }

  /**
   * Constrói e retorna uma nova instância de {@link User} com os dados configurados.
   *
   * @return nova instância de User
   */
  public User build() {
    return User.builder()
        .userName(this.userName)
        .userCode(this.userCode)
        .password(this.password)
        .enabled(this.enabled)
        .accountNotExpired(this.accountNotExpired)
        .accountNotLocked(this.accountNotLocked)
        .credentialsNotExpired(this.credentialsNotExpired)
        .roles(this.roles)
        .privileges(this.privileges)
        .build();
  }
}
