package com.ia.core.llm.service.template;

import com.ia.core.llm.model.template.TemplateParameter;
import com.ia.core.llm.service.model.template.TemplateParameterDTO;
import com.ia.core.service.mapper.BaseEntityMapper;
import org.mapstruct.Mapper;

/**
 * Mapper MapStruct para conversão entre {@link TemplateParameter} e {@link TemplateParameterDTO}.
 * <p>
 * Utiliza o MapStruct para geração automática de implementação de mapeamento.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Mapper(componentModel = "spring")
public interface TemplateParameterMapper
  extends BaseEntityMapper<TemplateParameter, TemplateParameterDTO> {

}
