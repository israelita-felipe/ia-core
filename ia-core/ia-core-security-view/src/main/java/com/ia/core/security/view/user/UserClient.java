package com.ia.core.security.view.user;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.ia.core.security.service.model.user.UserDTO;
import com.ia.core.security.service.model.user.UserPasswordChangeDTO;
import com.ia.core.security.service.model.user.UserPasswordResetDTO;
import com.ia.core.view.client.DefaultBaseClient;

/**
 * Cliente para {@link UserDTO}
 *
 * @author Israel Araújo
 */
@FeignClient(name = UserClient.NOME, url = UserClient.URL)
public interface UserClient
  extends DefaultBaseClient<UserDTO> {

  /**
   * Nome do cliente.
   */
  public static final String NOME = "user";
  /**
   * URL do cliente.
   */
  public static final String URL = "${feign.host}/api/${api.version}/${feign.url.user}";

  /**
   * Altera a senha do usuário
   *
   * @param dto {@link UserPasswordChangeDTO}
   */
  @PostMapping("/changePassword")
  public void changePassword(@RequestBody UserPasswordChangeDTO dto);

  /**
   * Reseta a senha de um usuário
   *
   * @param dto {@link UserPasswordResetDTO}
   */
  @PostMapping("/resetPassword")
  public void resetPassword(@RequestBody UserPasswordResetDTO dto);
}
