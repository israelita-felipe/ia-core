package com.ia.core.llm.service.ferramenta;

import com.ia.core.llm.model.ferramenta.Ferramenta;
import com.ia.core.llm.service.model.ferramenta.FerramentaDTO;
import com.ia.core.service.mapper.BaseEntityMapper;
import org.mapstruct.Mapper;

/**
 * Mapper MapStruct para conversão entre {@link Ferramenta} e {@link FerramentaDTO}.
 * <p>
 * Utiliza o MapStruct para geração automática de implementação de mapeamento.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Mapper(componentModel = "spring")
public interface FerramentaMapper
  extends BaseEntityMapper<Ferramenta, FerramentaDTO> {
}
