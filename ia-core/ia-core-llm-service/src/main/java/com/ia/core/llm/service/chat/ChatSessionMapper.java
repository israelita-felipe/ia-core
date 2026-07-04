package com.ia.core.llm.service.chat;

import com.ia.core.llm.model.chat.ChatSession;
import com.ia.core.llm.service.agente.AgenteMapper;
import com.ia.core.llm.service.model.chat.ChatSessionDTO;
import com.ia.core.service.mapper.BaseEntityMapper;
import org.mapstruct.Mapper;

/**
 * Mapper MapStruct para conversão entre {@link ChatSession} e {@link ChatSessionDTO}.
 * <p>
 * Utiliza o MapStruct para geração automática de implementação de mapeamento.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Mapper(componentModel = "spring", uses = { AgenteMapper.class })
public interface ChatSessionMapper
  extends BaseEntityMapper<ChatSession, ChatSessionDTO> {

}
