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
/**
 * Classe que representa o mapeamento de dados para template parameter.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a TemplateParameterMapper
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
 */
@Mapper(componentModel = "spring")
public interface TemplateParameterMapper
  extends BaseEntityMapper<TemplateParameter, TemplateParameterDTO> {

}
