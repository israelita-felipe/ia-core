package com.ia.core.llm.service.agente.mapper;

import com.ia.core.llm.model.agente.ContextoConversacao;
import com.ia.core.llm.service.model.agente.ContextConversacaoDTO;
import com.ia.core.service.mapper.BaseEntityMapper;
import org.mapstruct.Mapper;

/**
 * Mapeador bidirecional entre ContextoConversacao e ContextConversacaoDTO.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Mapper(componentModel = "spring", uses = {OntologiaMapper.class})
public interface ContextoConversacaoMapper extends BaseEntityMapper<ContextoConversacao, ContextConversacaoDTO> {

}
