package com.ia.core.llm.service.template;

import com.ia.core.llm.model.template.Template;
import com.ia.core.llm.service.model.template.TemplateDTO;
import com.ia.core.service.mapper.BaseEntityMapper;
import org.mapstruct.Mapper;

/**
 * Mapper para conversão entre {@link Template} e {@link TemplateDTO}.
 * <p>
 * Responsável por gerenciar o mapeamento entre a entidade Template
 * e seu respectivo DTO.
 *
 * @author Israel Araújo
 * @since 1.0
 */
@Mapper(componentModel = "spring", uses = { TemplateParameterMapper.class })
public interface TemplateMapper
  extends BaseEntityMapper<Template, TemplateDTO> {

}
