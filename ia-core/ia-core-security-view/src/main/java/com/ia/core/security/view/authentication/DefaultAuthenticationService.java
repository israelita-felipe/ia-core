package com.ia.core.security.view.authentication;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author Israel Araújo
 */
@RequiredArgsConstructor
public class DefaultAuthenticationService
  implements AuthenticationService {

  @Getter
  private final AuthenticationBaseClient authenticationClient;

}
