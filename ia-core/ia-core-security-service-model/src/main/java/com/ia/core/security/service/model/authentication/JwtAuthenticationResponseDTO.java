package com.ia.core.security.service.model.authentication;

import com.ia.core.security.model.authentication.AuthenticationResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Israel Araújo
 */
/**
 * Classe que representa o objeto de transferência de dados para jwt authentication response.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a JwtAuthenticationResponseDTO
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JwtAuthenticationResponseDTO
  implements AuthenticationResponse {
  private String token;
}
