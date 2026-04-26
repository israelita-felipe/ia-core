package com.ia.core.communication.service.modelomensagem;

import com.ia.core.communication.model.mensagem.ModeloMensagem;
import com.ia.core.communication.service.grupocontato.GrupoContatoMapper;
import com.ia.core.communication.service.model.modelomensagem.dto.ModeloMensagemDTO;
import com.ia.core.service.mapper.BaseEntityMapper;
import org.mapstruct.Mapper;

/**
 * {@link Mapper} de {@link ModeloMensagem} para {@link ModeloMensagemDTO}
 *
 * @author Israel Araújo
 */
@Mapper(componentModel = "spring")
public interface ModeloMensagemMapper
  extends BaseEntityMapper<ModeloMensagem, ModeloMensagemDTO> {

}
