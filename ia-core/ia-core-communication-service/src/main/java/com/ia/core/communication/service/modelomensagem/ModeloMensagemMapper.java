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
/**
 * Classe que representa o mapeamento de dados para modelo mensagem.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a ModeloMensagemMapper
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
 */
@Mapper(componentModel = "spring")
public interface ModeloMensagemMapper
  extends BaseEntityMapper<ModeloMensagem, ModeloMensagemDTO> {

}
