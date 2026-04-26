package com.ia.core.llm.service.template;

import com.ia.core.llm.model.template.Template;
import com.ia.core.llm.service.model.template.TemplateDTO;
import com.ia.core.service.mapper.BaseEntityMapper;
import org.mapstruct.Mapper;

/**
 * {@link Mapper} de {@link Template} para {@link TemplateDTO}
 *
 * @author Israel Araújo
 */
@Mapper(componentModel = "spring", uses = { TemplateParameterMapper.class })
public interface TemplateMapper
  extends BaseEntityMapper<Template, TemplateDTO> {

}
