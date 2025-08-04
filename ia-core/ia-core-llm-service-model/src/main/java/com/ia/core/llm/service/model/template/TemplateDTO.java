package com.ia.core.llm.service.model.template;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.ia.core.llm.model.template.Template;
import com.ia.core.model.HasVersion;
import com.ia.core.service.dto.entity.AbstractBaseEntityDTO;
import com.ia.core.service.dto.request.SearchRequestDTO;

import lombok.AllArgsConstructor;
import lombok.Builder.Default;
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
public class TemplateDTO
  extends AbstractBaseEntityDTO<Template> {

  public static final SearchRequestDTO getSearchRequest() {
    return new TemplateSearchRequest();
  }

  public static Set<String> propertyFilters() {
    return getSearchRequest().propertyFilters();
  }

  private String titulo;

  /**
   * Conteúdo do template
   */
  private String conteudo;

  /**
   * Flag indicativa de exigência de contexto
   */
  @Default
  private boolean exigeContexto = false;

  /**
   * Parâmetros do template
   */
  @Default
  private List<TemplateParameterDTO> parametros = new ArrayList<>();

  @Override
  public TemplateDTO cloneObject() {
    return toBuilder().parametros(new ArrayList<>(parametros.stream()
        .map(TemplateParameterDTO::cloneObject).toList())).build();
  }

  @Override
  public TemplateDTO copyObject() {
    return toBuilder().id(null).version(HasVersion.DEFAULT_VERSION).parametros(new ArrayList<>(parametros.stream()
        .map(TemplateParameterDTO::copyObject).toList())).build();
  }
}
