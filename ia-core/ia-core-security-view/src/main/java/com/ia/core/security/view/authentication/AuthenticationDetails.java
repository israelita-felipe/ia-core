package com.ia.core.security.view.authentication;

import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;

import com.ia.core.security.model.authentication.AuthenticationRequest;
import com.ia.core.security.service.model.authentication.JwtAuthenticationResponseDTO;
import com.ia.core.security.service.model.privilege.PrivilegeDTO;
import com.ia.core.security.service.model.user.UserDTO;
import com.vaadin.flow.server.VaadinRequest;
import com.vaadin.flow.server.VaadinServletRequest;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

/**
 * @author Israel Araújo
 */
@RequiredArgsConstructor
public class AuthenticationDetails {

  private JwtAuthenticationResponseDTO authentication = null;

  private final AuthenticationService service;

  public void autenticate(AuthenticationRequest request) {
    service.autenticate(request, this);
  }

  /**
   * @param user
   * @return
   */
  protected UsernamePasswordAuthenticationToken createAuthenticationToken(UserDTO user) {
    return UsernamePasswordAuthenticationToken
        .authenticated(user, getToken(), getAuthorities(user));
  }

  /**
   * @return
   */
  protected UserDTO createUser() {
    JwtUserProvider userProvider = new JwtUserProvider();
    UserDTO userDTO = userProvider.get(authentication);
    return userDTO;
  }

  /**
   * @return
   */
  public UserDTO getAuthenticatedUser() {
    if (authentication == null) {
      return null;
    }
    return createUser();
  }

  /**
   * @param userDTO
   * @return
   */
  private List<? extends GrantedAuthority> getAuthorities(UserDTO userDTO) {
    return userDTO.getPrivileges().stream().map(PrivilegeDTO::getName)
        .map(SimpleGrantedAuthority::new).toList();
  }

  /**
   * @return
   */
  protected VaadinServletRequest getCurrentRequest() {
    return (VaadinServletRequest) VaadinRequest.getCurrent();
  }

  public String getToken() {
    if (isAuthenticated()) {
      return authentication.getToken();
    }
    return null;
  }

  public boolean isAuthenticated() {
    return authentication != null;
  }

  /**
   * @param authentication atualiza {@link #authentication}.
   */
  public void setAuthentication(JwtAuthenticationResponseDTO authentication) {
    this.authentication = authentication;
    UserDTO user = createUser();
    UsernamePasswordAuthenticationToken authenticated = createAuthenticationToken(user);
    setAuthenticationOnSecurityContext(authenticated);
  }

  /**
   * @param authenticated
   */
  protected void setAuthenticationOnSecurityContext(UsernamePasswordAuthenticationToken authenticated) {
    VaadinServletRequest currentRequest = getCurrentRequest();
    // previne o monitoramento de sessão
    currentRequest.changeSessionId();
    WebAuthenticationDetails webAuthenticationDetails = new WebAuthenticationDetailsSource()
        .buildDetails(currentRequest);
    authenticated.setDetails(webAuthenticationDetails);
    setAuthenticationOnSecurityContext(authenticated, currentRequest);
  }

  /**
   * @param authenticated
   * @param currentRequest
   */
  protected void setAuthenticationOnSecurityContext(UsernamePasswordAuthenticationToken authenticated,
                                                    VaadinServletRequest currentRequest) {
    SecurityContext context = SecurityContextHolder.getContext();
    context.setAuthentication(authenticated);
    HttpSession session = currentRequest.getSession(false);
    session
        .setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                      context);
  }
}
