package com.ia.core.security.service.model.role;

import com.ia.core.security.model.role.RolePrivilege;
import com.ia.core.security.service.model.privilege.PrivilegeDTO;
import com.ia.core.security.service.model.privilege.PrivilegeOperationDTO;
import com.ia.core.service.dto.entity.AbstractBaseEntityDTO;
import com.ia.core.service.dto.request.SearchRequestDTO;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder.Default;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Classe que representa o objeto de transferência de dados para role privilege.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a RolePrivilegeDTO
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
 */

@Data
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class RolePrivilegeDTO
  extends AbstractBaseEntityDTO<RolePrivilege> {

  @NotNull(message = "validation.role.privilege.required")
  private PrivilegeDTO privilege;

  @Default
  private Set<PrivilegeOperationDTO> operations = new HashSet<>();

  /**
   * Retorna uma visão imutável das operações do privilégio.
   *
   * @return conjunto de operações não modificável
   * @bugfix SECURITY: Evita modificação externa não controlada do estado interno
   */
  public Set<PrivilegeOperationDTO> getOperations() {
    return Collections.unmodifiableSet(operations);
  }

  /**
   * Define as operações (faz uma cópia defensiva).
   *
   * @param operations novo conjunto de operações (não pode ser null)
   * @throws NullPointerException se operations for null
   * @bugfix SECURITY: Cópia defensiva para evitar retenção de referência mutável
   */
  public void setOperations(Set<PrivilegeOperationDTO> operations) {
    this.operations = new HashSet<>(operations);
  }

  public static class CAMPOS
    extends AbstractBaseEntityDTO.CAMPOS {
    public static final String PRIVILEGE = "privilege";
    public static final String OPERATIONS = "operations";
  }

  /**
   * @return
   */
  public static SearchRequestDTO getSearchRequest() {
    return SearchRequestDTO.builder().build();
  }

  @Override
  public RolePrivilegeDTO cloneObject() {
    return toBuilder()
        .privilege(privilege != null ? privilege.cloneObject() : null)
        .operations(new HashSet<>(getOperations().stream()
            .map(PrivilegeOperationDTO::cloneObject).toList()))
        .build();
  }

  @Override
  public RolePrivilegeDTO copyObject() {
    return ((RolePrivilegeDTO) super.copyObject()).toBuilder()
        .privilege(privilege != null ? privilege.copyObject() : null)
        .operations(new HashSet<>(getOperations().stream()
            .map(PrivilegeOperationDTO::copyObject).toList()))
        .build();
  }
}
