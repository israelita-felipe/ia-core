package com.ia.core.llm.service.model.skill;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SkillMetadataDTO {
  private Long id;
  private String titulo;
  private String descricao;
  private int ferramentaCount;
  private boolean ativo;
}
