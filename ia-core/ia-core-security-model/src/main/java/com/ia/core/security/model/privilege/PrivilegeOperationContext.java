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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder.Default;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(schema = PrivilegeOperationContext.SCHEMA_NAME,
       name = PrivilegeOperationContext.TABLE_NAME)
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class PrivilegeOperationContext
  extends BaseEntity {
  /** Serial UID */
  private static final long serialVersionUID = 1002971063026215644L;
  /** NOME DA TABELA */
  public static final String TABLE_NAME = SecurityModel.TABLE_PREFIX
      + "PRIVILEGE_OPERATION_CONTEXT";
  /** NOME DA TABELA */
  public static final String PRIVILEGE_OPERATION_CONTEXT_VALUE_TABLE_NAME = SecurityModel.TABLE_PREFIX
      + "PRIVILEGE_OPERATION_CONTEXT_VALUE";

  /** NOME DO SCHEMA */
  public static final String SCHEMA_NAME = SecurityModel.SCHEMA;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "privilege_operation", referencedColumnName = "id")
  private PrivilegeOperation privilegeOperation;

  @Column(name = "context_key")
  private String contextKey;

  @Default
  @ElementCollection(fetch = FetchType.LAZY)
  @CollectionTable(schema = SCHEMA_NAME, name = PRIVILEGE_OPERATION_CONTEXT_VALUE_TABLE_NAME,
                   joinColumns = @JoinColumn(name = "privilege_operation_context",
                                             referencedColumnName = "id"))
  @Column(name = "privilege_operation_context_value")
  private Set<String> values = new HashSet<>();

}
