package com.ia.core.security.view.log.operation;

import org.springframework.cloud.openfeign.FeignClient;

import com.ia.core.security.service.model.log.operation.LogOperationDTO;
import com.ia.core.view.client.DefaultBaseClient;

/**
 * Cliente de log de operações
 *
 * @author Israel Araújo
 */
@FeignClient(name = LogOperationClient.NOME, url = LogOperationClient.URL)
public interface LogOperationClient
  extends DefaultBaseClient<LogOperationDTO> {

  /**
   * Nome do cliente.
   */
  public static final String NOME = "log.operation";
  /**
   * URL do cliente.
   */
  public static final String URL = "${feign.host}/api/${api.version}/${feign.url.log.operation}";

}
