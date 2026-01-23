package com.ia.core.security.model.privilege;

import java.util.Collection;
import java.util.HashSet;

import com.ia.core.model.BaseEntity;
import com.ia.core.security.model.SecurityModel;
import com.ia.core.security.model.functionality.OperationEnum;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder.Default;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = PrivilegeOperation.TABLE_NAME,
       schema = PrivilegeOperation.SCHEMA_NAME)
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class PrivilegeOperation
  extends BaseEntity {
  /** Serial UID */
  private static final long serialVersionUID = 8203818908291338195L;

  /** NOME DA TABELA */
  public static final String TABLE_NAME = SecurityModel.TABLE_PREFIX
      + "PRIVILEGE_OPERATION";

  /** NOME DO SCHEMA */
  public static final String SCHEMA_NAME = SecurityModel.SCHEMA;

  @Column(name = "operation", nullable = false)
  private OperationEnum operation;

  @Default
  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL,
             orphanRemoval = true, mappedBy = "privilegeOperation")
  private Collection<PrivilegeOperationContext> context = new HashSet<>();

}
