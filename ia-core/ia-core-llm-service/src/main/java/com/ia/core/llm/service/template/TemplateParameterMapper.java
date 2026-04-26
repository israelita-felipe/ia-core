package com.ia.core.llm.service.template;

import com.ia.core.llm.model.template.TemplateParameter;
import com.ia.core.llm.service.model.template.TemplateParameterDTO;
import com.ia.core.service.mapper.BaseEntityMapper;
import org.mapstruct.Mapper;

/**
 * {@link Mapper} de {@link TemplateParameter} para {@link TemplateParameterDTO}
 *
 * @author Israel Araújo
 */
@Mapper(componentModel = "spring")
public interface TemplateParameterMapper
  extends BaseEntityMapper<TemplateParameter, TemplateParameterDTO> {

}
