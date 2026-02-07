package com.ia.core.security.model.privilege;

import java.util.HashSet;
import java.util.Set;

import com.ia.core.model.BaseEntity;
import com.ia.core.security.model.SecurityModel;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder.Default;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = Privilege.TABLE_NAME, schema = Privilege.SCHEMA_NAME)
@Getter
@Setter
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Privilege
  extends BaseEntity {

  /** NOME DA TABELA */
  public static final String TABLE_NAME = SecurityModel.TABLE_PREFIX
      + "PRIVILEGE";
  /** NOME DA TABELA */
  public static final String PRIVILEGE_CONTEXT_TABLE_NAME = SecurityModel.TABLE_PREFIX
      + "PRIVILEGE_CONTEXT";
  /** NOME DO SCHEMA */
  public static final String SCHEMA_NAME = SecurityModel.SCHEMA;

  @Column(name = "name", length = 500, nullable = false, unique = true)
  private String name;

  @Default
  @Column(name = "type", nullable = false)
  private PrivilegeType type = PrivilegeType.SYSTEM;

  @Default
  @ElementCollection(fetch = FetchType.LAZY)
  @CollectionTable(schema = SCHEMA_NAME,
                   name = PRIVILEGE_CONTEXT_TABLE_NAME,
                   joinColumns = @JoinColumn(name = "privilege",
                                             referencedColumnName = "id"))
  @Column(name = "context")
  private Set<String> values = new HashSet<>();

}
