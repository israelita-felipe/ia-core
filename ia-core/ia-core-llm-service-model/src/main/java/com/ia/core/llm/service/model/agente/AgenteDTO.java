package com.ia.core.llm.service.model.agente;

import com.ia.core.llm.model.agente.Agente;
import com.ia.core.llm.service.model.ferramenta.FerramentaDTO;
import com.ia.core.llm.service.model.skill.SkillDTO;
import com.ia.core.model.HasVersion;
import com.ia.core.service.dto.entity.AbstractBaseEntityDTO;
import com.ia.core.service.dto.request.SearchRequestDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
 * DTO para transferência de dados de Agente.
 * <p>
 * Representa um agente para orquestração de ferramentas e skills.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Data
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AgenteDTO
  extends AbstractBaseEntityDTO<Agente> {

  /** Serial UID */
  private static final long serialVersionUID = -828374920123456789L;

  public static SearchRequestDTO getSearchRequest() {
    return new AgenteSearchRequest();
  }

  public static Set<String> propertyFilters() {
    return getSearchRequest().propertyFilters();
  }

  /**
   * Identificador único do agente (ex: llm.core).
   */
  @NotBlank(message = AgenteTranslator.VALIDATION.IDENTIFICADOR_REQUIRED)
  @Size(min = 2, max = 100, message = AgenteTranslator.VALIDATION.IDENTIFICADOR_SIZE)
  private String identificador;

  /**
   * Nome apresentável do agente na UI.
   */
  @NotNull(message = AgenteTranslator.VALIDATION.TITULO_REQUIRED)
  @Size(min = 2, max = 200, message = AgenteTranslator.VALIDATION.TITULO_SIZE)
  private String titulo;

  /**
   * Descrição do propósito do agente.
   */
  @Size(max = 1000, message = AgenteTranslator.VALIDATION.DESCRICAO_SIZE)
  private String descricao;

  /**
   * Instruções do sistema (prompt system).
   */
  private String instrucoes;

  /**
   * Modelo LLM preferido para este agente.
   */
  @Size(max = 100, message = AgenteTranslator.VALIDATION.MODELO_SIZE)
  private String modelo;

  /**
   * Indica se o agente está disponível para uso.
   */
  @Default
  private Boolean ativo = true;

  /**
   * Módulo ou pacote fonte.
   */
  @Size(max = 200, message = AgenteTranslator.VALIDATION.MODULO_ORIGEM_SIZE)
  private String moduloOrigem;

  /**
   * Temperatura para geração de texto.
   */
  @Default
  private Double temperature = 0.7;

  /**
   * Número máximo de tokens para geração.
   */
  @Default
  private Integer maxTokens = 2048;

  /**
   * Conjunto de ferramentas que o agente pode usar.
   */
  @Default
  private Set<FerramentaDTO> ferramentas = new HashSet<>();

  /**
   * Conjunto de habilidades especializadas que o agente pode ter.
   */
  @Default
  private Set<SkillDTO> skills = new HashSet<>();

  @Override
  public AgenteDTO cloneObject() {
    return toBuilder()
        .ferramentas(new HashSet<>(ferramentas))
        .skills(new HashSet<>(skills))
        .build();
  }

  @Override
  public AgenteDTO copyObject() {
    AgenteDTO copy = (AgenteDTO) super.copyObject();
    copy.setId(null);
    copy.setVersion(HasVersion.DEFAULT_VERSION);
    return copy;
  }

  /**
   * Retorna uma representação em string deste objeto.
   *
   * @return string contendo o identificador e título do agente
   */
  @Override
  public String toString() {
    return String.format("AgenteDTO{identificador=%s, titulo=%s}", identificador, titulo);
  }

  /**
   * Constantes de campos para referência type-safe.
   */
  @SuppressWarnings("javadoc")
  public static class CAMPOS extends AbstractBaseEntityDTO.CAMPOS {
    public static final String IDENTIFICADOR = "identificador";
    public static final String TITULO = "titulo";
    public static final String DESCRICAO = "descricao";
    public static final String INSTRUCOES = "instrucoes";
    public static final String MODELO = "modelo";
    public static final String ATIVO = "ativo";
    public static final String MODULO_ORIGEM = "moduloOrigem";
    public static final String TEMPERATURE = "temperature";
    public static final String MAX_TOKENS = "maxTokens";
    public static final String FERRAMENTAS = "ferramentas";
    public static final String SKILLS = "skills";
    public static final String PROPERTY_CHANGE_SUPPORT = "propertyChangeSupport";

    public static Set<String> values() {
      var baseValues = AbstractBaseEntityDTO.CAMPOS.values();
      var currentValues = Set.of(IDENTIFICADOR, TITULO, DESCRICAO, INSTRUCOES, MODELO, ATIVO,
          MODULO_ORIGEM, TEMPERATURE, MAX_TOKENS, FERRAMENTAS, SKILLS);
      var allValues = new HashSet<String>();
      allValues.addAll(baseValues);
      allValues.addAll(currentValues);
      return Collections.unmodifiableSet(allValues);
    }
  }
}
