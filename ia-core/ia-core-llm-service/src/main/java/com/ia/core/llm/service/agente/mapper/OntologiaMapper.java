package com.ia.core.llm.service.agente.mapper;

import com.ia.core.llm.model.ontologia.Ontologia;
import com.ia.core.llm.service.model.ontologia.OntologiaDTO;
import com.ia.core.service.mapper.BaseEntityMapper;
import org.mapstruct.Mapper;

/**
 * Mapeador bidirecional entre Ontologia e OntologiaDTO.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Mapper(componentModel = "spring")
public interface OntologiaMapper extends BaseEntityMapper<Ontologia, OntologiaDTO> {

}
