package com.ia.core.llm.service.model.comando;

import java.util.Set;

import com.ia.core.llm.model.comando.ComandoSistema;
import com.ia.core.llm.model.comando.FinalidadeComandoEnum;
import com.ia.core.llm.service.model.template.TemplateDTO;
import com.ia.core.service.dto.entity.AbstractBaseEntityDTO;
import com.ia.core.service.dto.request.SearchRequestDTO;

import jakarta.validation.constraints.NotNull;
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
public class ComandoSistemaDTO
  extends AbstractBaseEntityDTO<ComandoSistema> {
  public static final SearchRequestDTO getSearchRequest() {
    return new ComandoSistemaSearchRequest();
  }

  public static Set<String> propertyFilters() {
    return getSearchRequest().propertyFilters();
  }

  /** Finalidade do comando */
  @Default
  private FinalidadeComandoEnum finalidade = FinalidadeComandoEnum.RESPOSTA_TEXTUAL;

  /**
   * Título do comando
   */
  @NotNull
  private String titulo;

  /**
   * Comando a ser executado, expresso em linguagem natural
   */
  private String comando;

  @NotNull
  private TemplateDTO template;

  @Override
  public ComandoSistemaDTO cloneObject() {
    return toBuilder().build();
  }
}
