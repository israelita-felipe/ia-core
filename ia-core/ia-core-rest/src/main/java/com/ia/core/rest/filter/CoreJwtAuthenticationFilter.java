package com.ia.core.rest.filter;

import com.ia.core.security.model.authentication.JwtManager;
import com.ia.core.security.service.exception.UserNotFountException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * @author Israel Araújo
 */
@RequiredArgsConstructor
@RestController
public class CoreJwtAuthenticationFilter
  extends OncePerRequestAuthenticationFilter {

  private static final String BEARER_HEADER = "Bearer ";

  private static final String AUTHORIZATION_HEADER = "Authorization";

  private static final String REFRESH_TOKEN_HEADER = "X-Refresh-Token";

  private final UserDetailsService userDetailService;

  @Override
  public Authentication getAuthentication(HttpServletRequest request)
    throws UserNotFountException {
    String token = getTokenFromRequest(request);
    token = token ==null?getRefreshTokenFromRequest(request):token;
    UsernamePasswordAuthenticationToken userDetailsFromToken = getUserDetailsFromToken(token);
    userDetailsFromToken.setDetails(new WebAuthenticationDetails(request));
    return userDetailsFromToken;
  }

  /**
   * @param request
   * @return
   */
  String getTokenFromRequest(HttpServletRequest request) {
    String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
    if (Objects.nonNull(bearerToken)
        && bearerToken.startsWith(BEARER_HEADER)) {
      return bearerToken.substring(BEARER_HEADER.length(),
                                   bearerToken.length());
    }
    return null;
  }
    String getRefreshTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(REFRESH_TOKEN_HEADER);
        if (Objects.nonNull(bearerToken)) {
            if (bearerToken.startsWith(BEARER_HEADER)) {
                return bearerToken.substring(BEARER_HEADER.length(),
                    bearerToken.length());
            }
            return bearerToken;
        }
        return null;
    }

  /**
   * @param userCode
   * @return
   */
  public UserDetails getUserByUserCode(String userCode) {
    return userDetailService.loadUserByUsername(userCode);
  }

  /**
   * @param token
   * @throws UserNotFountException
   */
  protected UsernamePasswordAuthenticationToken getUserDetailsFromToken(String token)
    throws UserNotFountException {
    if (token == null) {
      throw new IllegalArgumentException("Token jwt não informado");
    }
    String userCode = JwtManager.get().getUserCodeFromJWT(token);
    UserDetails user = getUserByUserCode(userCode);
    if (user == null) {
      throw new UserNotFountException(userCode);
    }
    return new UsernamePasswordAuthenticationToken(token, null,
                                                   user.getAuthorities());
  }

}
