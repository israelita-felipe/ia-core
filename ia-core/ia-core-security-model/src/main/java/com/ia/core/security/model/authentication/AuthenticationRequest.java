package com.ia.core.security.model.authentication;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Israel Araújo
 */
/**
 * Classe que representa a entidade de domínio authentication request.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a AuthenticationRequest
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationRequest
  implements Serializable {
  private String codUsuario;
  private String senha;
}
