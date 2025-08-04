package com.ia.core.security.service.authorization;

import com.ia.core.security.service.model.authorization.CoreSecurityAuthorizationManager;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class CoreAuthorizationManager
  implements CoreSecurityAuthorizationManager {

  @Getter
  @Setter
  private boolean updateEnabled = false;
  @Getter
  @Setter
  private boolean deleteEnabled = false;
  @Getter
  @Setter
  private boolean createEnabled = false;
  @Getter
  @Setter
  private boolean readEnabled = false;

}
