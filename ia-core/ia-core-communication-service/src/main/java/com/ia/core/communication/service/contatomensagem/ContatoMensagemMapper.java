package com.ia.core.communication.service.contatomensagem;

import com.ia.core.communication.model.contato.ContatoMensagem;
import com.ia.core.communication.service.grupocontato.GrupoContatoMapper;
import com.ia.core.communication.service.model.contatomensagem.dto.ContatoMensagemDTO;
import com.ia.core.service.mapper.BaseEntityMapper;
import org.mapstruct.Mapper;

/**
 * {@link Mapper} de {@link ContatoMensagem} para {@link ContatoMensagemDTO}
 *
 * @author Israel Araújo
 */
@Mapper(componentModel = "spring",uses = {GrupoContatoMapper.class})
public interface ContatoMensagemMapper
  extends BaseEntityMapper<ContatoMensagem, ContatoMensagemDTO> {

}
