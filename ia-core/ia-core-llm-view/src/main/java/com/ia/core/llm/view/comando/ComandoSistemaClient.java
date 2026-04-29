package com.ia.core.llm.view.comando;

import com.ia.core.llm.service.model.comando.ComandoSistemaDTO;
import com.ia.core.view.client.DefaultBaseClient;
/**
 * Cliente Feign para comunicação com o serviço de comando sistema.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a ComandoSistemaClient
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
 */
public interface ComandoSistemaClient
  extends DefaultBaseClient<ComandoSistemaDTO> {

  /**
   * Nome do cliente.
   */
  public static final String NOME = "comando.sistema";
  /**
   * URL do cliente.
   */
  public static final String URL = "${feign.host}/api/${api.version}/${feign.url.comando.sistema}";

}
