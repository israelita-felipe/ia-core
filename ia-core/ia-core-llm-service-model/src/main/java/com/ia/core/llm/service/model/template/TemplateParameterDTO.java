package com.ia.core.llm.service.model.template;

import java.util.Set;

import com.ia.core.llm.model.template.TemplateParameter;
import com.ia.core.service.dto.entity.AbstractBaseEntityDTO;
import com.ia.core.service.dto.request.SearchRequestDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author Israel Araújo
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
