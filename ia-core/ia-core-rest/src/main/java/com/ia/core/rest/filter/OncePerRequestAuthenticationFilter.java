package com.ia.core.rest.filter;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ia.core.security.service.exception.UserNotFountException;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Israel Araújo
 */
@Slf4j
public abstract class OncePerRequestAuthenticationFilter
  extends OncePerRequestFilter {

  private boolean enabled = true;
  /** Token inválido */
  private static final String HEADER_INVALID_TOKEN = "x-service-error-invalid-token";

  /**
   *
   */
  public OncePerRequestAuthenticationFilter() {
    super();
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request,
                                  HttpServletResponse response,
                                  FilterChain filterChain)
    throws ServletException, IOException {
    if (enabled) {
      try {
        Authentication userAuthentication = getAuthentication(request);
        if (userAuthentication != null) {
          SecurityContextHolder.getContext()
              .setAuthentication(userAuthentication);
        }
      } catch (UserNotFountException e) {
        log.info("Autenticação falhou para o usuário {}", e.getUserCode());
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.addHeader(HEADER_INVALID_TOKEN, "Usuário não localizado");
      } catch (ExpiredJwtException e) {
        log.info("Autenticação falhou, token expirado");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.addHeader(HEADER_INVALID_TOKEN, "Token expirado");
      } catch (Exception e) {
        log.error(e.getLocalizedMessage(), e);
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.addHeader("x-error", e.getLocalizedMessage());
      }
    }
    filterChain.doFilter(request, response);
  }

  /**
   * @param request
   * @param token
   * @return
   * @throws UserNotFountException
   */
  public abstract Authentication getAuthentication(HttpServletRequest request)
    throws UserNotFountException;

  /**
   * @return {@link #enabled}
   */
  public boolean isEnabled() {
    return enabled;
  }

  /**
   * @param enabled atualiza {@link #enabled}.
   */
  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

}
