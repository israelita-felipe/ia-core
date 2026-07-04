package com.ia.core.llm.service.agente;

import com.ia.core.llm.model.agente.Agente;
import com.ia.core.llm.service.ferramenta.FerramentaMapper;
import com.ia.core.llm.service.model.agente.AgenteDTO;
import com.ia.core.llm.service.skill.SkillMapper;
import com.ia.core.service.mapper.BaseEntityMapper;
import org.mapstruct.Mapper;

/**
 * Mapper MapStruct para conversão entre {@link Agente} e {@link AgenteDTO}.
 * <p>
 * Utiliza o MapStruct para geração automática de implementação de mapeamento.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Mapper(componentModel = "spring", uses = { FerramentaMapper.class, SkillMapper.class })
public interface AgenteMapper
  extends BaseEntityMapper<Agente, AgenteDTO> {
}
