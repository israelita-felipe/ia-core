package com.ia.core.security.view.authorization;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
/**
 * Classe que representa o gerenciamento de core view authorization.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a CoreViewAuthorizationManager
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
 */

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
