package com.ia.core.llm.service.model.skill;

import com.ia.core.llm.model.skill.Skill;
import com.ia.core.llm.model.skill.SkillTipo;
import com.ia.core.model.HasVersion;
import com.ia.core.service.dto.entity.AbstractBaseEntityDTO;
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

import java.util.Set;

/**
 * DTO para transferência de dados de Skill.
 * <p>
 * Representa uma habilidade especializada que pode ser atribuída a um agente.
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
public class SkillDTO extends AbstractBaseEntityDTO<Skill> {

  /**
   * Identificador único da skill (ex: ONTOLOGY_BUILDER, KNOWLEDGE_EXTRACTION).
   */
  @NotBlank(message = SkillTranslator.VALIDATION.IDENTIFICADOR_REQUIRED)
  @Size(min = 2, max = 100, message = SkillTranslator.VALIDATION.IDENTIFICADOR_SIZE)
  private String identificador;

  /**
   * Nome apresentável da skill na UI.
   */
  @NotNull(message = SkillTranslator.VALIDATION.TITULO_REQUIRED)
  @Size(min = 2, max = 200, message = SkillTranslator.VALIDATION.TITULO_SIZE)
  private String titulo;

  /**
   * Descrição do propósito da skill.
   */
  @Size(max = 1000)
  private String descricao;

  /**
   * Tipo da skill.
   */
  private SkillTipo tipo;

  /**
   * Indica se a skill está disponível para uso.
   */
  @Default
  private Boolean ativo = true;

  /**
   * Módulo ou pacote fonte.
   */
  @Size(max = 200)
  private String moduloOrigem;

  @Override
  public void setVersion(Long version) {
    super.setVersion(version);
  }

  @Override
  public SkillDTO cloneObject() {
    log.debug("Clonando SkillDTO: identificador={}", identificador);
    return toBuilder()
        .id(null)
        .version(HasVersion.DEFAULT_VERSION)
        .build();
  }

  @Override
  public SkillDTO copyObject() {
    return toBuilder()
        .id(null)
        .version(HasVersion.DEFAULT_VERSION)
        .build();
  }

  /**
   * Constantes de campos para referência type-safe.
   */
  @SuppressWarnings("javadoc")
  public static class CAMPOS extends AbstractBaseEntityDTO.CAMPOS {
    public static final String IDENTIFICADOR = "identificador";
    public static final String TITULO = "titulo";
    public static final String DESCRICAO = "descricao";
    public static final String TIPO = "tipo";
    public static final String ATIVO = "ativo";
    public static final String MODULO_ORIGEM = "moduloOrigem";
    public static final String PROPERTY_CHANGE_SUPPORT = "propertyChangeSupport";

    public static Set<String> values() {
      return Set.of(IDENTIFICADOR, TITULO, DESCRICAO, TIPO, ATIVO, MODULO_ORIGEM, PROPERTY_CHANGE_SUPPORT);
    }
  }
}
