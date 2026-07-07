package com.ia.core.security.service.model.privilege;

import com.ia.core.security.model.privilege.PrivilegeOperationContext;
import com.ia.core.service.dto.entity.AbstractBaseEntityDTO;
import com.ia.core.service.dto.request.SearchRequestDTO;
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
 * Classe que representa o objeto de transferência de dados para privilege operation context.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a PrivilegeOperationContextDTO
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
 */

@Data
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class PrivilegeOperationContextDTO
  extends AbstractBaseEntityDTO<PrivilegeOperationContext> {

  private String contextKey;

  @Default
  private Set<String> values = new HashSet<>();

  /**
   * Retorna uma visão imutável dos valores do contexto.
   *
   * @return conjunto de valores não modificável
   * @bugfix SECURITY: Evita modificação externa não controlada do estado interno
   */
  public Set<String> getValues() {
    return Collections.unmodifiableSet(values);
  }

  /**
   * Define os valores (faz uma cópia defensiva).
   *
   * @param values novo conjunto de valores (não pode ser null)
   * @throws NullPointerException se values for null
   * @bugfix SECURITY: Cópia defensiva para evitar retenção de referência mutável
   */
  public void setValues(Set<String> values) {
    this.values = new HashSet<>(values);
  }

  @Override
  public PrivilegeOperationContextDTO cloneObject() {
    return toBuilder().values(values != null ? new HashSet<>(getValues()) : new HashSet<>()).build();
  }

  @Override
  public PrivilegeOperationContextDTO copyObject() {
    return ((PrivilegeOperationContextDTO) super.copyObject()).toBuilder()
        .values(values != null ? new HashSet<>(getValues()) : new HashSet<>()).build();
  }

  /**
   * @return
   */
  public static SearchRequestDTO getSearchRequest() {
    return new SearchRequestDTO();
  }

  @SuppressWarnings("javadoc")
  public static class CAMPOS
    extends AbstractBaseEntityDTO.CAMPOS {
    public static final String CONTEXT_KEY = "contextKey";
    public static final String VALUES = "values";
  }
}
