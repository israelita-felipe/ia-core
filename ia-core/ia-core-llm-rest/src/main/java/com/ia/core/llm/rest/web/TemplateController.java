package com.ia.core.llm.rest.web;

import com.ia.core.llm.model.template.Template;
import com.ia.core.llm.service.model.template.TemplateDTO;
import com.ia.core.llm.service.template.TemplateService;
import com.ia.core.rest.control.DefaultBaseController;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller REST para gerenciamento de templates.
 * <p>
 * Expõe endpoints para operações CRUD em templates de prompt.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/llm/templates")
@Tag(name = "Template", description = "Gerenciamento de templates de prompt")
public class TemplateController
  extends DefaultBaseController<Template,TemplateDTO> {

  public TemplateController(TemplateService templateService) {
    super(templateService);
  }
}
