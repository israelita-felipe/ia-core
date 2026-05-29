package com.ia.core.communication.service.grupocontato;

import com.ia.core.communication.model.contato.GrupoContato;
import com.ia.core.communication.service.model.grupocontato.dto.GrupoContatoDTO;
import com.ia.core.service.mapper.BaseEntityMapper;
import org.mapstruct.Mapper;

/**
 * Mapper de {@link GrupoContato} para {@link GrupoContatoDTO}.
 * <p>
 * Realiza o mapeamento entre a entidade de grupo de contato e o DTO
 * correspondente, utilizando MapStruct para conversão automática.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Mapper(componentModel = "spring")
public interface GrupoContatoMapper
  extends BaseEntityMapper<GrupoContato, GrupoContatoDTO> {

}
