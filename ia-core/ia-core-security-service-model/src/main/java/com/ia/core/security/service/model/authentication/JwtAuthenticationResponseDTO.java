package com.ia.core.security.service.model.authentication;

import com.ia.core.security.model.authentication.AuthenticationResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Israel Araújo
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JwtAuthenticationResponseDTO
  implements AuthenticationResponse {
  private String token;
}
