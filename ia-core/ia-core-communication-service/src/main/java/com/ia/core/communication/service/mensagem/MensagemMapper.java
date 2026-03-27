package com.ia.core.communication.service.mensagem;

import org.mapstruct.Mapper;

import com.ia.core.communication.model.Mensagem;
import com.ia.core.communication.service.model.mensagem.dto.MensagemDTO;
import com.ia.core.service.mapper.BaseEntityMapper;

/**
 * {@link Mapper} de {@link Mensagem} para {@link MensagemDTO}
 *
 * @author Israel Araújo
 */
public interface MensagemMapper
  extends BaseEntityMapper<Mensagem, MensagemDTO> {

}
