package com.ia.core.communication.service.modelomensagem;

import com.ia.core.communication.model.mensagem.ModeloMensagem;
import com.ia.core.communication.service.grupocontato.GrupoContatoMapper;
import com.ia.core.communication.service.model.modelomensagem.dto.ModeloMensagemDTO;
import com.ia.core.service.mapper.BaseEntityMapper;
import org.mapstruct.Mapper;

/**
 * Mapper de {@link ModeloMensagem} para {@link ModeloMensagemDTO}.
 * <p>
 * Realiza o mapeamento entre a entidade de modelo de mensagem e o DTO
 * correspondente, utilizando MapStruct para conversão automática.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Mapper(componentModel = "spring")
public interface ModeloMensagemMapper
  extends BaseEntityMapper<ModeloMensagem, ModeloMensagemDTO> {

}
