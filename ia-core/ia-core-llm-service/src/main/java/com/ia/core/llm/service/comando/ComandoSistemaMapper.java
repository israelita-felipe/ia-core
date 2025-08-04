package com.ia.core.llm.service.comando;

import org.mapstruct.Mapper;

import com.ia.core.llm.model.comando.ComandoSistema;
import com.ia.core.llm.model.template.Template;
import com.ia.core.llm.service.model.comando.ComandoSistemaDTO;
import com.ia.core.llm.service.model.template.TemplateDTO;
import com.ia.core.llm.service.template.TemplateMapper;
import com.ia.core.service.mapper.BaseMapper;

/**
 * {@link Mapper} de {@link Template} para {@link TemplateDTO}
 *
 * @author Israel Araújo
 */
@Mapper(componentModel = "spring", uses = { TemplateMapper.class })
public interface ComandoSistemaMapper
  extends BaseMapper<ComandoSistema, ComandoSistemaDTO> {

}
