package com.ia.core.llm.service.model.prompt;

import com.ia.core.llm.model.prompt.FinalidadePromptEnum;
import com.ia.core.llm.model.prompt.Prompt;
import com.ia.core.llm.service.model.template.TemplateDTO;
import com.ia.core.service.dto.entity.AbstractBaseEntityDTO;
import com.ia.core.service.dto.request.SearchRequestDTO;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.Builder.Default;
import lombok.experimental.SuperBuilder;

import java.util.Collections;
import java.util.HashSet;
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

  /** Serial UID */
  private static final long serialVersionUID = -774920123456789123L;

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
    return toBuilder().build();
  }

  /**
   * Retorna uma representação em string deste objeto.
   *
   * @return string contendo o título
   */
  @Override
  public String toString() {
    return String.format("PromptDTO{titulo=%s}", titulo);
  }

  /**
   * Constantes de campos para referência type-safe.
   */
  @SuppressWarnings("javadoc")
  public static class CAMPOS extends AbstractBaseEntityDTO.CAMPOS {
    public static final String FINALIDADE = "finalidade";
    public static final String TITULO = "titulo";
    public static final String ENTRADA = "entrada";
    public static final String TEMPLATE = "template";

    public static Set<String> values() {
      var baseValues = AbstractBaseEntityDTO.CAMPOS.values();
      var currentValues = Set.of(FINALIDADE, TITULO, ENTRADA, TEMPLATE);
      var allValues = new HashSet<String>();
      allValues.addAll(baseValues);
      allValues.addAll(currentValues);
      return Collections.unmodifiableSet(allValues);
    }
  }
}