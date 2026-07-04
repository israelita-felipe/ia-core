package com.ia.core.llm.service.model.ferramenta;

import com.ia.core.llm.service.model.template.TemplateDTO;
import com.ia.core.service.dto.DTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FerramentaActivationDTO implements DTO<Long> {
  private Long id;
  private String titulo;
  private String descricao;
  private String instrucoes;
  private TemplateDTO template;
  @Builder.Default
  private List<FerramentaDTO> subFerramentas = new ArrayList<>();

  @Override
  public FerramentaActivationDTO cloneObject() {
    return FerramentaActivationDTO.builder()
        .id(id)
        .titulo(titulo)
        .descricao(descricao)
        .instrucoes(instrucoes)
        .template(template)
        .subFerramentas(subFerramentas)
        .build();
  }

  public static class CAMPOS {
    public static final String ID = "id";
    public static final String TITULO = "titulo";
    public static final String DESCRICAO = "descricao";
    public static final String INSTRUCOES = "instrucoes";
    public static final String TEMPLATE = "template";
    public static final String SUB_FERRAMENTAS = "subFerramentas";
    public static final String PROPERTY_CHANGE_SUPPORT = "propertyChangeSupport";

    public static Set<String> values() {
      return Set.of(ID, TITULO, DESCRICAO, INSTRUCOES, TEMPLATE, SUB_FERRAMENTAS, PROPERTY_CHANGE_SUPPORT);
    }
  }
}
