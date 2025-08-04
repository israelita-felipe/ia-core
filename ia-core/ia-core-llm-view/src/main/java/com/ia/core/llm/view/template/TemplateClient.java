package com.ia.core.llm.view.template;

import com.ia.core.llm.service.model.template.TemplateDTO;
import com.ia.core.view.client.DefaultBaseClient;

/**
 * @author Israel Araújo
 */
public interface TemplateClient
  extends DefaultBaseClient<TemplateDTO> {

  /**
   * Nome do cliente.
   */
  public static final String NOME = "template";
  /**
   * URL do cliente.
   */
  public static final String URL = "${feign.host}/api/${api.version}/${feign.url.template}";

}
