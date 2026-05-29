package com.ia.core.llm.service.prompt;

import com.ia.core.llm.model.prompt.Prompt;
import com.ia.core.llm.service.model.prompt.PromptDTO;
import com.ia.core.llm.service.template.TemplateMapper;
import com.ia.core.service.mapper.BaseEntityMapper;
import org.mapstruct.Mapper;

/**
 * Mapper MapStruct para conversão entre {@link Prompt} e {@link PromptDTO}.
 * <p>
 * Utiliza o MapStruct para geração automática de implementação de mapeamento.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Mapper(componentModel = "spring", uses = TemplateMapper.class)
public interface PromptMapper
  extends BaseEntityMapper<Prompt, PromptDTO> {
}
