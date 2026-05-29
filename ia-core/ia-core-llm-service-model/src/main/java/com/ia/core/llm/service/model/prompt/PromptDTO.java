package com.ia.core.llm.service.model.prompt;

import com.ia.core.llm.model.prompt.FinalidadePromptEnum;
import com.ia.core.llm.model.prompt.Prompt;
import com.ia.core.llm.service.model.template.TemplateDTO;
import com.ia.core.model.HasVersion;
import com.ia.core.service.dto.entity.AbstractBaseEntityDTO;
import com.ia.core.service.dto.request.SearchRequestDTO;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.Builder.Default;
import lombok.experimental.SuperBuilder;

import java.util.Set;

/**
 * DTO para Prompt.
 * <p>
 * Representa um prompt de catálogo utilizado em invocações de modelos de linguagem.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Data
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PromptDTO
  extends AbstractBaseEntityDTO<Prompt> {

  public static SearchRequestDTO getSearchRequest() {
    return new PromptSearchRequest();
  }

  public static Set<String> propertyFilters() {
    return getSearchRequest().propertyFilters();
  }

  @Default
  private FinalidadePromptEnum finalidade = FinalidadePromptEnum.RESPOSTA_TEXTUAL;

  @NotNull(message = PromptTranslator.VALIDATION.TITULO_REQUIRED)
  @Size(min = 3, max = 200, message = PromptTranslator.VALIDATION.TITULO_SIZE)
  private String titulo;

  @Size(max = 500, message = PromptTranslator.VALIDATION.ENTRADA_SIZE)
  private String entrada;

  @NotNull(message = PromptTranslator.VALIDATION.TEMPLATE_REQUIRED)
  private TemplateDTO template;

  @Override
  public PromptDTO cloneObject() {
    return toBuilder().id(null).version(HasVersion.DEFAULT_VERSION).build();
  }
}
