package com.ia.core.owl.service.model.axioma;

import java.util.Set;

import com.ia.core.owl.model.axiom.Axioma;
import com.ia.core.service.dto.entity.AbstractBaseEntityDTO;
import com.ia.core.service.dto.request.SearchRequestDTO;

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
public class AxiomaDTO
  extends AbstractBaseEntityDTO<Axioma> {

  public static final SearchRequestDTO getSearchRequest() {
    return new AxiomaSearchRequest();
  }

  public static Set<String> propertyFilters() {
    return getSearchRequest().propertyFilters();
  }

  private String uri;
  private String prefix;
  private String uriVersion;
  private String expressao;
  @Default
  private Boolean consistente = true;
  @Default
  private Boolean inferido = false;
  @Default
  private Boolean ativo = true;

  @Override
  public AxiomaDTO cloneObject() {
    return toBuilder().build();
  }

  public static final class CAMPOS
    extends AbstractBaseEntityDTO.CAMPOS {
    public static final String URI = "uri";
    public static final String PREFIX = "prefix";
    public static final String URI_VERSION = "uriVersion";
    public static final String EXPRESSAO = "expressao";
    public static final String IS_CONSISTENTE = "consistente";
    public static final String IS_INFERIDO = "inferido";
    public static final String IS_ATIVO = "ativo";
  }
}
