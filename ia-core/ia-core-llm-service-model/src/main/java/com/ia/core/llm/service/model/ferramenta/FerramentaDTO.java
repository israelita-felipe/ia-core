package com.ia.core.llm.service.model.ferramenta;

import com.ia.core.llm.model.ferramenta.Ferramenta;
import com.ia.core.llm.model.ferramenta.TipoFerramentaEnum;
import com.ia.core.llm.service.model.template.TemplateDTO;
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
import java.util.Collections;
import java.util.HashSet;
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
 * @see Ferramenta
 * @see FerramentaTranslator
 */
@Data
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class FerramentaDTO
  extends AbstractBaseEntityDTO<Ferramenta>
  implements FerramentaDiscoverable {

  /** Serial UID */
  private static final long serialVersionUID = 774920123456789123L;

  /**
   * Retorna o request de pesquisa para este DTO.
   *
   * @return request de pesquisa
   */
  public static SearchRequestDTO getSearchRequest() {
    return new FerramentaSearchRequestDTO();
  }

  /**
   * Retorna os filtros de propriedade para pesquisa.
   *
   * @return conjunto de filtros
   */
  public static Set<String> propertyFilters() {
    return getSearchRequest().propertyFilters();
  }

  @NotNull(message = FerramentaTranslator.VALIDATION.TITULO_REQUIRED)
  @Size(min = 2, max = 200, message = FerramentaTranslator.VALIDATION.TITULO_SIZE)
  private String titulo;

  @Size(max = 1000, message = FerramentaTranslator.VALIDATION.DESCRICAO_SIZE)
  private String descricao;

  @NotNull(message = FerramentaTranslator.VALIDATION.TIPO_REQUIRED)
  private TipoFerramentaEnum tipo;

  @NotNull(message = FerramentaTranslator.VALIDATION.IDENTIFICADOR_REQUIRED)
  @Size(max = 255, message = FerramentaTranslator.VALIDATION.IDENTIFICADOR_SIZE)
  private String identificador;

  @Size(max = 200, message = FerramentaTranslator.VALIDATION.MODULO_ORIGEM_SIZE)
  private String moduloOrigem;

  @Default
  private boolean ativo = true;

  @Default
  private boolean descobertaAutomatica = false;

  private String instrucoes;

  private TemplateDTO template;

  @Default
  private List<FerramentaDTO> subFerramentas = new ArrayList<>();

  /**
   * Cria uma cópia superficial (clone) deste DTO.
   *
   * @return nova instância com os mesmos valores
   */
  @Override
  public FerramentaDTO cloneObject() {
    return toBuilder()
        .subFerramentas(new ArrayList<>(subFerramentas))
        .build();
  }

  /**
   * Retorna uma representação em string deste objeto.
   *
   * @return string contendo o título e identificador
   */
  @Override
  public String toString() {
    return String.format("FerramentaDTO{titulo=%s, identificador=%s}", titulo, identificador);
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

    public static Set<String> values() {
      var baseValues = AbstractBaseEntityDTO.CAMPOS.values();
      var currentValues = Set.of(TITULO, DESCRICAO, TIPO, IDENTIFICADOR, MODULO_ORIGEM, ATIVO,
          DESCOBERTA_AUTOMATICA, INSTRUCOES, TEMPLATE, SUB_FERRAMENTAS);
      var allValues = new HashSet<String>();
      allValues.addAll(baseValues);
      allValues.addAll(currentValues);
      return Collections.unmodifiableSet(allValues);
    }
  }
}