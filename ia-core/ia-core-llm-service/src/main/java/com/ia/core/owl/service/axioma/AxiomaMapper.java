package com.ia.core.owl.service.axioma;

import com.ia.core.owl.model.axiom.Axioma;
import com.ia.core.owl.service.model.axioma.AxiomaDTO;
import com.ia.core.service.mapper.BaseEntityMapper;
import org.mapstruct.Mapper;

/**
 * Mapper MapStruct para conversão entre {@link Axioma} e {@link AxiomaDTO}.
 * <p>
 * Utiliza o MapStruct para geração automática de implementação de mapeamento.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Mapper(componentModel = "spring")
public interface AxiomaMapper
  extends BaseEntityMapper<Axioma, AxiomaDTO> {

}
