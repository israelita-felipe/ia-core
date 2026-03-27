package com.ia.core.communication.service.modelomensagem;

import org.mapstruct.Mapper;

import com.ia.core.communication.model.ModeloMensagem;
import com.ia.core.communication.service.model.modelomensagem.dto.ModeloMensagemDTO;
import com.ia.core.service.mapper.BaseEntityMapper;

/**
 * {@link Mapper} de {@link ModeloMensagem} para {@link ModeloMensagemDTO}
 *
 * @author Israel Araújo
 */
public interface ModeloMensagemMapper
  extends BaseEntityMapper<ModeloMensagem, ModeloMensagemDTO> {

}
