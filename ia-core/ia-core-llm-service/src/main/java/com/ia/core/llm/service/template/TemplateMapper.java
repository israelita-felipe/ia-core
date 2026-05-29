package com.ia.core.llm.service.template;

import com.ia.core.llm.model.template.Template;
import com.ia.core.llm.service.model.template.TemplateDTO;
import com.ia.core.service.mapper.BaseEntityMapper;
import org.mapstruct.Mapper;

/**
 * Mapper MapStruct para conversão entre {@link Template} e {@link TemplateDTO}.
 * <p>
 * Utiliza o MapStruct para geração automática de implementação de mapeamento.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Mapper(componentModel = "spring", uses = { TemplateParameterMapper.class })
public interface TemplateMapper
  extends BaseEntityMapper<Template, TemplateDTO> {

}
