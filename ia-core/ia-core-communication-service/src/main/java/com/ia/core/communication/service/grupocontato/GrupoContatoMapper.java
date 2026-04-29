package com.ia.core.communication.service.grupocontato;

import com.ia.core.communication.model.contato.GrupoContato;
import com.ia.core.communication.service.model.grupocontato.dto.GrupoContatoDTO;
import com.ia.core.service.mapper.BaseEntityMapper;
import org.mapstruct.Mapper;

/**
 * {@link Mapper} de {@link GrupoContato} para {@link GrupoContatoDTO}
 *
 * @author Israel Araújo
 */
/**
 * Classe que representa o mapeamento de dados para grupo contato.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a GrupoContatoMapper
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
 */
@Mapper(componentModel = "spring")
public interface GrupoContatoMapper
  extends BaseEntityMapper<GrupoContato, GrupoContatoDTO> {

}
