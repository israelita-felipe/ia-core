package com.ia.core.llm.service.comando;

import com.ia.core.llm.model.comando.ComandoSistema;
import com.ia.core.llm.service.model.comando.ComandoSistemaDTO;
import com.ia.core.service.mapper.BaseEntityMapper;
import org.mapstruct.Mapper;

/**
 * Mapper para conversão entre {@link ComandoSistema} e {@link ComandoSistemaDTO}.
 * <p>
 * Responsável por gerenciar o mapeamento entre a entidade ComandoSistema
 * e seu respectivo DTO.
 *
 * @author Israel Araújo
 * @since 1.0
 */
@Mapper(componentModel = "spring")
public interface ComandoSistemaMapper
  extends BaseEntityMapper<ComandoSistema, ComandoSistemaDTO> {

}
