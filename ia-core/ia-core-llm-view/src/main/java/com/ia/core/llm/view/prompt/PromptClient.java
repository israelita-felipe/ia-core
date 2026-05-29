package com.ia.core.llm.view.prompt;

import com.ia.core.llm.service.model.prompt.PromptDTO;
import com.ia.core.resilience4j.annotation.Resilient;
import com.ia.core.resilience4j.profile.ResilienceProfile;
import com.ia.core.view.client.DefaultBaseClient;
/**
 * Cliente Feign para comunicação com o serviço de comando sistema.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a PromptClient
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
 */
public interface PromptClient
  extends DefaultBaseClient<PromptDTO> {

  /**
   * Nome do cliente.
   */
  public static final String NOME = "prompt";
  /**
   * URL do cliente.
   */
  public static final String URL = "${feign.host}/api/${api.version}/${feign.url.prompt}";

}
