package com.ia.core.llm.service.template;

import org.mapstruct.Mapper;

import com.ia.core.llm.model.template.Template;
import com.ia.core.llm.service.model.template.TemplateDTO;
import com.ia.core.service.mapper.BaseMapper;

/**
 * {@link Mapper} de {@link Template} para {@link TemplateDTO}
 *
 * @author Israel Araújo
 */
@Mapper(componentModel = "spring", uses = { TemplateParameterMapper.class })
public interface TemplateMapper
  extends BaseMapper<Template, TemplateDTO> {

}
