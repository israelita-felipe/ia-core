package com.ia.core.security.service.model.authentication;

import com.ia.core.security.model.authentication.AuthenticationResponse;
import com.ia.core.service.dto.DTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * DTO (Data Transfer Object) para resposta de autenticação JWT.
 * <p>
 * Esta classe é utilizada para transferir dados de resposta de autenticação
 * entre as camadas de apresentação e serviço, contendo informações sobre
 * token de acesso e refresh token.
 *
 * @author Israel Araújo
 * @see AuthenticationResponse
 * @since 1.0.0
 */
@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class JwtAuthenticationResponseDTO
  implements AuthenticationResponse, DTO<Serializable> {

  /**
   * Serial UID.
   */
  private static final long serialVersionUID = -19560738760061623L;

  /**
   * Token de acesso JWT.
   */
  private String token;

  /**
   * Refresh token para renovação do token de acesso.
   */
  private String refreshToken;

  /**
   * Cria uma cópia superficial (clone) deste objeto DTO.
   *
   * @return novo objeto {@link JwtAuthenticationResponseDTO} com os mesmos valores
   */
  @Override
  public JwtAuthenticationResponseDTO cloneObject() {
    return toBuilder().build();
  }

  /**
   * Constantes para nomes dos campos deste DTO.
   */
  @SuppressWarnings("javadoc")
  public static class CAMPOS {
    public static final String TOKEN = "token";
    public static final String REFRESH_TOKEN = "refreshToken";

    /**
     * Retorna todos os nomes de campos deste DTO.
     *
     * @return conjunto de strings com os nomes dos campos
     */
    public static java.util.Set<String> values() {
      return java.util.Set.of(TOKEN, REFRESH_TOKEN);
    }
  }
}
