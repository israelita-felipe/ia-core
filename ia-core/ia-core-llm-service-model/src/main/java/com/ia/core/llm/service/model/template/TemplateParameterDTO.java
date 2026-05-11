package com.ia.core.llm.service.model.template;

import com.ia.core.llm.model.template.TemplateParameter;
import com.ia.core.service.dto.entity.AbstractBaseEntityDTO;
import com.ia.core.service.dto.request.SearchRequestDTO;
import com.ia.core.llm.service.model.template.TemplateParameterTranslator;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Set;

/**
 * @author Israel Araújo
 */
/**
 * Classe que representa o objeto de transferência de dados para template parameter.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a TemplateParameterDTO
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
 * @see TemplateParameterTranslator
 */
@Data
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TemplateParameterDTO
  extends AbstractBaseEntityDTO<TemplateParameter> {

  public static final SearchRequestDTO getSearchRequest() {
    return new TemplateParameterSearchRequest();
  }

  public static Set<String> propertyFilters() {
    return getSearchRequest().propertyFilters();
  }

  @NotNull(message = TemplateParameterTranslator.VALIDATION.NOME_REQUIRED)
  @Size(min = 1, max = 100, message = TemplateParameterTranslator.VALIDATION.NOME_SIZE)
  private String nome;

  @Override
  public TemplateParameterDTO cloneObject() {
    return toBuilder().build();
  }

  @Override
  public TemplateParameterDTO copyObject() {
    return (TemplateParameterDTO) super.copyObject();
  }
}
