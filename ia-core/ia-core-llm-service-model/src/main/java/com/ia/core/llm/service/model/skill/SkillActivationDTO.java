package com.ia.core.llm.service.model.skill;

import com.ia.core.llm.service.model.ferramenta.FerramentaDTO;
import com.ia.core.llm.service.model.template.TemplateDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SkillActivationDTO {
  private Long id;
  private String titulo;
  private String descricao;
  private String instrucoes;
  private TemplateDTO template;
  @Builder.Default
  private List<FerramentaDTO> ferramentas = new ArrayList<>();
}
