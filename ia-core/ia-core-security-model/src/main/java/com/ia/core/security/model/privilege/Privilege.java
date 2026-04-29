package com.ia.core.security.model.privilege;

import com.ia.core.model.BaseEntity;
import com.ia.core.security.model.SecurityModel;
import jakarta.persistence.*;
import lombok.*;
import lombok.Builder.Default;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;
/**
 * Classe que representa a entidade de domínio privilege.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a Privilege
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
 */

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
