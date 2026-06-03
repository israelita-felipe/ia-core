package com.ia.core.llm.service.model.agente;

import com.ia.core.llm.model.agente.Agente;
import com.ia.core.llm.service.model.ferramenta.FerramentaDTO;
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
import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * DTO para transferência de dados de Agente.
 * <p>
 * Representa um agente especialista para orquestração multi-agente.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@Data
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AgenteDTO
  extends AbstractBaseEntityDTO<Agente> {

  public static SearchRequestDTO getSearchRequest() {
    return new AgenteSearchRequest();
  }

  public static Set<String> propertyFilters() {
    return getSearchRequest().propertyFilters();
  }

  /**
   * Identificador único do agente (ex: llm.core, pessoa.especialista).
   */
  @NotBlank(message = AgenteTranslator.VALIDATION.IDENTIFICADOR_REQUIRED)
  @Size(min = 2, max = 100)
  private String identificador;

  /**
   * Nome apresentável do agente na UI.
   */
  @NotNull(message = AgenteTranslator.VALIDATION.TITULO_REQUIRED)
  @Size(min = 2, max = 200)
  private String titulo;

  /**
   * Descrição do propósito do agente.
   */
  @Size(max = 1000)
  private String descricao;

  /**
   * Instruções do sistema (equivalente ao YAML frontmatter).
   */
  private String instrucoes;

  /**
   * Modelo LLM preferido para este agente.
   */
  @Size(max = 100)
  private String modelo;

  /**
   * Lista de ferramentas autorizadas para este agente.
   * Inclui tanto ferramentas atômicas quanto skills (tipo=SKILL).
   */
  @Default
  private List<FerramentaDTO> ferramentas = new ArrayList<>();

  /**
   * Indica se o agente está disponível para orquestração.
   */
  @Default
  private boolean ativo = true;

  /**
   * Módulo ou pacote fonte.
   */
  @Size(max = 200)
  private String moduloOrigem;

  /**
   * Mapa de metadados genéricos para armazenar especificidades
   * de diferentes tipos de agentes sem criar campos específicos.
   */
  @Default
  private Map<String, String> metadados = new HashMap<>();

  @Override
  public AgenteDTO cloneObject() {
    log.debug("Clonando AgenteDTO: identificador={}", identificador);
    return toBuilder()
        .id(null)
        .version(HasVersion.DEFAULT_VERSION)
        .ferramentas(new ArrayList<>(ferramentas))
        .metadados(new HashMap<>(metadados))
        .build();
  }
}
