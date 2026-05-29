package com.ia.core.llm.service.model.ferramenta;

import com.ia.core.llm.model.ferramenta.Ferramenta;
import com.ia.core.llm.model.ferramenta.TipoFerramentaEnum;
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
 * DTO para Ferramenta.
 * <p>
 * Representa uma ferramenta que pode ser utilizada por agentes de IA.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Data
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class FerramentaDTO
  extends AbstractBaseEntityDTO<Ferramenta> {

  public static SearchRequestDTO getSearchRequest() {
    return new FerramentaSearchRequest();
  }

  public static Set<String> propertyFilters() {
    return getSearchRequest().propertyFilters();
  }

  @NotNull(message = FerramentaTranslator.VALIDATION.TITULO_REQUIRED)
  @Size(min = 2, max = 200)
  private String titulo;

  @Size(max = 1000)
  private String descricao;

  @NotNull
  private TipoFerramentaEnum tipo;

  @NotNull(message = FerramentaTranslator.VALIDATION.IDENTIFICADOR_REQUIRED)
  @Size(max = 255)
  private String identificador;

  private String moduloOrigem;

  @Default
  private boolean ativo = true;

  @Default
  private boolean descobertaAutomatica = false;

  @Override
  public FerramentaDTO cloneObject() {
    return toBuilder().id(null).version(HasVersion.DEFAULT_VERSION).build();
  }
}
