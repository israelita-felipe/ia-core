package com.ia.core.security.view.authorization;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public abstract class CoreViewAuthorizationManager
  implements CoreSecurityViewAuthorizationManager {

  @Getter
  private Map<Class<?>, Set<String>> accessMap = new ConcurrentHashMap<>();
  @Getter
  private Map<Class<?>, Boolean> authenticationMap = new ConcurrentHashMap<>();

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
