package com.ia.core.llm.service.model.ferramenta;

import com.ia.core.llm.model.ferramenta.Ferramenta;
import com.ia.core.llm.model.ferramenta.TipoFerramentaEnum;
import com.ia.core.llm.service.model.template.TemplateDTO;
import com.ia.core.model.HasVersion;
import com.ia.core.service.dto.entity.AbstractBaseEntityDTO;
import com.ia.core.service.dto.request.SearchRequestDTO;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.Builder.Default;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * DTO para Ferramenta.
 * <p>
 * Representa qualquer capacidade que pode ser utilizada por agentes de IA,
 * incluindo ferramentas atômicas e skills (capacidades compostas).
 * Implementa FerramentaDiscoverable para descoberta automática unificada.
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
  extends AbstractBaseEntityDTO<Ferramenta>
  implements FerramentaDiscoverable {

  public static SearchRequestDTO getSearchRequest() {
    return new FerramentaSearchRequest();
  }

  public static Set<String> propertyFilters() {
    return getSearchRequest().propertyFilters();
  }

  @NotNull(message = FerramentaTranslator.VALIDATION.TITULO_REQUIRED)
  @Size(min = 2, max = 200, message = FerramentaTranslator.VALIDATION.TITULO_SIZE)
  private String titulo;

  @Size(max = 1000)
  private String descricao;

  @NotNull
  private TipoFerramentaEnum tipo;

  @NotNull(message = FerramentaTranslator.VALIDATION.IDENTIFICADOR_REQUIRED)
  @Size(max = 255, message = FerramentaTranslator.VALIDATION.IDENTIFICADOR_SIZE)
  private String identificador;

  private String moduloOrigem;

  @Default
  private boolean ativo = true;

  @Default
  private boolean descobertaAutomatica = false;

  private String instrucoes;

  private TemplateDTO template;

  @Default
  private List<FerramentaDTO> subFerramentas = new ArrayList<>();

  @Override
  public void setVersion(Long version) {
    super.setVersion(version);
  }

  @Override
  public FerramentaDTO cloneObject() {
    return toBuilder().id(null).version(HasVersion.DEFAULT_VERSION)
        .subFerramentas(new ArrayList<>(subFerramentas)).build();
  }

  /**
   * Constantes de campos para referência type-safe.
   */
  @SuppressWarnings("javadoc")
  public static class CAMPOS extends AbstractBaseEntityDTO.CAMPOS {
    public static final String TITULO = "titulo";
    public static final String DESCRICAO = "descricao";
    public static final String TIPO = "tipo";
    public static final String IDENTIFICADOR = "identificador";
    public static final String MODULO_ORIGEM = "moduloOrigem";
    public static final String ATIVO = "ativo";
    public static final String DESCOBERTA_AUTOMATICA = "descobertaAutomatica";
    public static final String INSTRUCOES = "instrucoes";
    public static final String TEMPLATE = "template";
    public static final String SUB_FERRAMENTAS = "subFerramentas";
    public static final String PROPERTY_CHANGE_SUPPORT = "propertyChangeSupport";

    public static Set<String> values() {
      return Set.of(TITULO, DESCRICAO, TIPO, IDENTIFICADOR, MODULO_ORIGEM, ATIVO,
          DESCOBERTA_AUTOMATICA, INSTRUCOES, TEMPLATE, SUB_FERRAMENTAS, PROPERTY_CHANGE_SUPPORT);
    }
  }
}
