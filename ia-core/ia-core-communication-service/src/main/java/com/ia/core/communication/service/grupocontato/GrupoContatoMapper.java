package com.ia.core.communication.service.grupocontato;

import org.mapstruct.Mapper;

import com.ia.core.communication.model.GrupoContato;
import com.ia.core.communication.service.model.grupocontato.dto.GrupoContatoDTO;
import com.ia.core.service.mapper.BaseEntityMapper;

/**
 * {@link Mapper} de {@link GrupoContato} para {@link GrupoContatoDTO}
 *
 * @author Israel Araújo
 */
public interface GrupoContatoMapper
  extends BaseEntityMapper<GrupoContato, GrupoContatoDTO> {

}
