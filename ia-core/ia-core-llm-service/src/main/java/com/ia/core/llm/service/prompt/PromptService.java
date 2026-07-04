package com.ia.core.llm.service.prompt;

import com.ia.core.llm.model.prompt.Prompt;
import com.ia.core.llm.service.model.prompt.PromptDTO;
import com.ia.core.llm.service.model.prompt.PromptUseCase;
import com.ia.core.service.CrudBaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * Serviço para gerenciamento de prompts de catálogo.
 * <p>
 * Implementa operações CRUD para prompts utilizados em invocações de modelos
 * de linguagem.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@Service
public class PromptService
  extends CrudBaseService<Prompt, PromptDTO>
  implements PromptUseCase {

  public PromptService(PromptServiceConfig config) {
    super(Objects.requireNonNull(config, "config não pode ser null"));
  }
}
