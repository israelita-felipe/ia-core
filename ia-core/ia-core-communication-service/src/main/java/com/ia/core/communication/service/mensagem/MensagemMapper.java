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
/**
 * Classe que representa o mapeamento de dados para mensagem.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a MensagemMapper
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
 */
@Mapper(componentModel = "spring")
public interface MensagemMapper
  extends BaseEntityMapper<Mensagem, MensagemDTO> {

}
