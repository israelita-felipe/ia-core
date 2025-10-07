package com.ia.core.security.model.privilege;

import com.ia.core.model.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder.Default;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = Privilege.TABLE_NAME, schema = Privilege.SCHEMA_NAME)
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Privilege
  extends BaseEntity {

  /** NOME DA TABELA */
  public static final String TABLE_NAME = "SEC_PRIVILEGE";
  /** NOME DO SCHEMA */
  public static final String SCHEMA_NAME = "SECURITY";

  @Column(name = "name", length = 500, nullable = false, unique = true)
  private String name;

  @Default
  @Column(name = "type")
  private PrivilegeType type = PrivilegeType.USER;

}
