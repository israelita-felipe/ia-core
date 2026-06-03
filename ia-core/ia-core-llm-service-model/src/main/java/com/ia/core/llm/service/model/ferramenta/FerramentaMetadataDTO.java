package com.ia.core.llm.service.model.ferramenta;

import com.ia.core.llm.model.ferramenta.TipoFerramentaEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FerramentaMetadataDTO {
  private Long id;
  private String titulo;
  private String descricao;
  private TipoFerramentaEnum tipo;
  private int subFerramentaCount;
  private boolean ativo;
}
