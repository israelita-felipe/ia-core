package com.ia.core.llm.service.model.skill;

import com.ia.core.llm.model.skill.Skill;
import com.ia.core.llm.service.model.ferramenta.FerramentaDTO;
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

@Data
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SkillDTO
  extends AbstractBaseEntityDTO<Skill> {

  public static SearchRequestDTO getSearchRequest() {
    return new SkillSearchRequest();
  }

  public static Set<String> propertyFilters() {
    return getSearchRequest().propertyFilters();
  }

  @NotNull(message = SkillTranslator.VALIDATION.TITULO_REQUIRED)
  @Size(min = 2, max = 200)
  private String titulo;

  @Size(max = 1000)
  private String descricao;

  private String instrucoes;

  @Default
  private List<FerramentaDTO> ferramentas = new ArrayList<>();

  private TemplateDTO template;

  @Default
  private boolean ativo = true;

  @Override
  public SkillDTO cloneObject() {
    return toBuilder().id(null).version(HasVersion.DEFAULT_VERSION)
        .ferramentas(new ArrayList<>(ferramentas)).build();
  }
}
