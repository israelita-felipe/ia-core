package com.ia.core.communication.service.mensagem;

import com.ia.core.communication.model.mensagem.Mensagem;
import com.ia.core.communication.service.grupocontato.GrupoContatoMapper;
import com.ia.core.communication.service.model.mensagem.dto.MensagemDTO;
import com.ia.core.service.mapper.BaseEntityMapper;
import org.mapstruct.Mapper;

/**
 * Mapper de {@link Mensagem} para {@link MensagemDTO}.
 * <p>
 * Realiza o mapeamento entre a entidade de mensagem e o DTO
 * correspondente, utilizando MapStruct para conversão automática.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Mapper(componentModel = "spring")
public interface MensagemMapper
  extends BaseEntityMapper<Mensagem, MensagemDTO> {

}
