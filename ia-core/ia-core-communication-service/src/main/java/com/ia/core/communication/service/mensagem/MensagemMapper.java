package com.ia.core.communication.service.mensagem;

import com.ia.core.communication.model.mensagem.Mensagem;
import com.ia.core.communication.service.grupocontato.GrupoContatoMapper;
import com.ia.core.communication.service.model.mensagem.dto.MensagemDTO;
import com.ia.core.service.mapper.BaseEntityMapper;
import org.mapstruct.Mapper;

/**
 * {@link Mapper} de {@link Mensagem} para {@link MensagemDTO}
 *
 * @author Israel Araújo
 */
@Mapper(componentModel = "spring")
public interface MensagemMapper
  extends BaseEntityMapper<Mensagem, MensagemDTO> {

}
