package com.ia.core.llm.service.model.template;

import com.ia.core.llm.model.template.Template;
import com.ia.core.model.HasVersion;
import com.ia.core.service.dto.entity.AbstractBaseEntityDTO;
import com.ia.core.service.dto.request.SearchRequestDTO;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder.Default;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Classe que representa o objeto de transferência de dados para template.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a TemplateDTO
 * dentro do sistema.
 *
 * @author Israel Araújo
 * @since 1.0
 * @see TemplateTranslator
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

  /**
   * Título do template
   */
  @NotNull(message = TemplateTranslator.VALIDATION.TITULO_NOT_BLANK)
  @Size(min = 3, max = 200, message = TemplateTranslator.VALIDATION.TITULO_SIZE)
  private String titulo;

  /**
   * Identificador único do template
   */
  @NotNull(message = TemplateTranslator.VALIDATION.IDENTIFICADOR_NOT_BLANK)
  @Size(max = 255, message = TemplateTranslator.VALIDATION.IDENTIFICADOR_SIZE)
  private String identificador;

  /**
   * Conteúdo do template
   */
  @NotNull(message = TemplateTranslator.VALIDATION.CONTEUDO_NOT_BLANK)
  @Size(max = 10000, message = TemplateTranslator.VALIDATION.CONTEUDO_SIZE)
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
  public void setVersion(Long version) {
    super.setVersion(version);
  }

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

  /**
   * Constantes de campos para referência type-safe.
   */
  @SuppressWarnings("javadoc")
  public static class CAMPOS extends AbstractBaseEntityDTO.CAMPOS {
    public static final String TITULO = "titulo";
    public static final String IDENTIFICADOR = "identificador";
    public static final String CONTEUDO = "conteudo";
    public static final String EXIGE_CONTEXTO = "exigeContexto";
    public static final String PARAMETROS = "parametros";
    public static final String PROPERTY_CHANGE_SUPPORT = "propertyChangeSupport";

    public static Set<String> values() {
      return Set.of(TITULO, IDENTIFICADOR, CONTEUDO, EXIGE_CONTEXTO, PARAMETROS, PROPERTY_CHANGE_SUPPORT);
    }
  }
}
