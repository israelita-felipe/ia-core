package com.ia.core.security.model.authentication;

import java.io.Serializable;

import lombok.Builder;
import lombok.Data;

/**
 * @author Israel Araújo
 */
@Data
@Builder
public class AuthenticationRequest
  implements Serializable {
  private String codUsuario;
  private String senha;
}
