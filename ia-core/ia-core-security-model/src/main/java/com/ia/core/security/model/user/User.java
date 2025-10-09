package com.ia.core.security.model.user;

import java.util.Collection;
import java.util.HashSet;

import com.ia.core.model.BaseEntity;
import com.ia.core.security.model.SecurityModel;
import com.ia.core.security.model.privilege.Privilege;
import com.ia.core.security.model.role.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder.Default;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = User.TABLE_NAME, schema = User.SCHEMA_NAME)
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class User
  extends BaseEntity {

  /** NOME DA TABELA */
  public static final String TABLE_NAME = SecurityModel.TABLE_PREFIX
      + "USER";
  /** NOME DO SCHEMA */
  public static final String SCHEMA_NAME = SecurityModel.SCHEMA;

  @Column(name = "user_name", length = 500, nullable = false)
  private String userName;
  @Column(name = "user_code", length = 500, nullable = false)
  private String userCode;
  @Column(name = "password", length = 500, nullable = true)
  private String password;
  @Column(name = "enabled", length = 1, nullable = false)
  private boolean enabled;
  @Column(name = "account_not_expired", length = 1, nullable = false)
  protected boolean accountNotExpired = true;
  @Column(name = "account_not_locked", length = 1, nullable = false)
  protected boolean accountNotLocked = false;
  @Column(name = "credentials_not_expired", length = 1, nullable = false)
  protected boolean credentialsNotExpired = true;

  @Default
  @ManyToMany
  @JoinTable(name = SecurityModel.TABLE_PREFIX + "users_roles",
             schema = SCHEMA_NAME,
             joinColumns = @JoinColumn(name = "user_id",
                                       referencedColumnName = "id"),
             inverseJoinColumns = @JoinColumn(name = "role_id",
                                              referencedColumnName = "id"),
             uniqueConstraints = @UniqueConstraint(columnNames = {
                 "user_id", "role_id" }))
  private Collection<Role> roles = new HashSet<>();

  @Default
  @ManyToMany
  @JoinTable(name = SecurityModel.TABLE_PREFIX + "users_privileges",
             schema = SCHEMA_NAME,
             joinColumns = @JoinColumn(name = "user_id",
                                       referencedColumnName = "id"),
             inverseJoinColumns = @JoinColumn(name = "privilege_id",
                                              referencedColumnName = "id"),
             uniqueConstraints = @UniqueConstraint(columnNames = {
                 "user_id", "privilege_id" }))
  private Collection<Privilege> privileges = new HashSet<>();

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("User [");
    if (userName != null) {
      builder.append("userName=");
      builder.append(userName);
      builder.append(", ");
    }
    if (userCode != null) {
      builder.append("userCode=");
      builder.append(userCode);
      builder.append(", ");
    }
    builder.append("enabled=");
    builder.append(enabled);
    builder.append(", accountNotExpired=");
    builder.append(accountNotExpired);
    builder.append(", accountNotLocked=");
    builder.append(accountNotLocked);
    builder.append(", credentialsNotExpired=");
    builder.append(credentialsNotExpired);
    builder.append("]");
    return builder.toString();
  }

}
