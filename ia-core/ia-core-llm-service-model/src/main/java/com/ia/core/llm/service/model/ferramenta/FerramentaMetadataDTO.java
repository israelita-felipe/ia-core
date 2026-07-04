package com.ia.core.llm.service.model.ferramenta;

import com.ia.core.llm.model.ferramenta.TipoFerramentaEnum;
import com.ia.core.service.dto.DTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FerramentaMetadataDTO implements DTO<Long> {
  private Long id;
  private String titulo;
  private String descricao;
  private TipoFerramentaEnum tipo;
  private int subFerramentaCount;
  private boolean ativo;

  @Override
  public FerramentaMetadataDTO cloneObject() {
    return FerramentaMetadataDTO.builder()
        .id(id)
        .titulo(titulo)
        .descricao(descricao)
        .tipo(tipo)
        .subFerramentaCount(subFerramentaCount)
        .ativo(ativo)
        .build();
  }

  public static class CAMPOS {
    public static final String ID = "id";
    public static final String TITULO = "titulo";
    public static final String DESCRICAO = "descricao";
    public static final String TIPO = "tipo";
    public static final String SUB_FERRAMENTA_COUNT = "subFerramentaCount";
    public static final String ATIVO = "ativo";
    public static final String PROPERTY_CHANGE_SUPPORT = "propertyChangeSupport";

    public static Set<String> values() {
      return Set.of(ID, TITULO, DESCRICAO, TIPO, SUB_FERRAMENTA_COUNT, ATIVO, PROPERTY_CHANGE_SUPPORT);
    }
  }
}
