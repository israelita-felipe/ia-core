package com.ia.core.communication.service.contatomensagem;

import org.mapstruct.Mapper;

import com.ia.core.communication.model.ContatoMensagem;
import com.ia.core.communication.service.model.contatomensagem.dto.ContatoMensagemDTO;
import com.ia.core.service.mapper.BaseEntityMapper;

/**
 * {@link Mapper} de {@link ContatoMensagem} para {@link ContatoMensagemDTO}
 *
 * @author Israel Araújo
 */
public interface ContatoMensagemMapper
  extends BaseEntityMapper<ContatoMensagem, ContatoMensagemDTO> {

}
