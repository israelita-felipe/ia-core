package com.ia.core.communication.service.contatomensagem;

import com.ia.core.communication.model.contato.ContatoMensagem;
import com.ia.core.communication.service.grupocontato.GrupoContatoMapper;
import com.ia.core.communication.service.model.contatomensagem.dto.ContatoMensagemDTO;
import com.ia.core.service.mapper.BaseEntityMapper;
import org.mapstruct.Mapper;

/**
 * Mapper de {@link ContatoMensagem} para {@link ContatoMensagemDTO}.
 * <p>
 * Realiza o mapeamento entre a entidade de contato de mensagem e o DTO
 * correspondente, utilizando MapStruct para conversão automática.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Mapper(componentModel = "spring",uses = {GrupoContatoMapper.class})
public interface ContatoMensagemMapper
  extends BaseEntityMapper<ContatoMensagem, ContatoMensagemDTO> {

}
