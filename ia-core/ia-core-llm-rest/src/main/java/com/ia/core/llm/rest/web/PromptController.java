package com.ia.core.llm.rest.web;

import com.ia.core.llm.model.prompt.Prompt;
import com.ia.core.llm.service.model.prompt.PromptDTO;
import com.ia.core.llm.service.prompt.PromptService;
import com.ia.core.rest.control.DefaultBaseController;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller REST para gerenciamento de prompts.
 * <p>
 * Expõe endpoints para operações CRUD em prompts de catálogo.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/llm/prompts")
@Tag(name = "Prompt", description = "Gerenciamento de prompts de catálogo")
public class PromptController
  extends DefaultBaseController<Prompt,PromptDTO> {


  public PromptController(PromptService promptService) {
    super(promptService);
  }

}
