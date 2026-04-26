package com.ia.core.rest.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

/**
 * Entry point para autenticação JWT.
 * <p>
 * Implementa AuthenticationEntryPoint para tratar respostas
 * de autenticação não autorizada (401).
 *
 * @author Israel Araújo
 * @see AuthenticationEntryPoint
 */
public class CoreJwtAuthenticationEntryPoint
  implements AuthenticationEntryPoint {
  @Override
  public void commence(HttpServletRequest request,
                       HttpServletResponse response,
                       AuthenticationException authException)
    throws IOException, ServletException {
    response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                       authException.getMessage());
  }
}
