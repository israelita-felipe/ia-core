package com.ia.core.security.view.privilege;

import com.ia.core.security.service.model.privilege.PrivilegeDTO;
import com.ia.core.view.client.DefaultBaseClient;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * Cliente para {@link PrivilegeDTO}
 *
 * @author Israel Araújo
 */

@FeignClient(name = PrivilegeClient.NOME, url = PrivilegeClient.URL)
public interface PrivilegeClient
  extends DefaultBaseClient<PrivilegeDTO> {

  /**
   * Nome do cliente.
   */
  public static final String NOME = "privilege";
  /**
   * URL do cliente.
   */
  public static final String URL = "${feign.host}/api/${api.version}/${feign.url.privilege}";

}
