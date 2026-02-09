package com.ia.core.security.view.authorization;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import com.ia.core.security.service.model.authorization.CoreSecurityAuthorizationManager;

/**
 * @author Israel Araújo
 */
public interface CoreSecurityViewAuthorizationManager
  extends CoreSecurityAuthorizationManager {

  default boolean check(Class<?> root, boolean authenticated) {
    if (isEnabledAll()) {
      if (!authenticated && mustBeAuthenticated(root)) {
        return false;
      }
      return true;
    }
    if (mustBeAuthenticated(root)) {
      if (!authenticated) {
        return false;
      }
      Set<String> roleAccess = getRoleAccess(root);
      if (roleAccess == null) {
        return false;
      } else if (roleAccess.isEmpty()) {
        return true;
      }
      return roleAccess.stream()
          .anyMatch(role -> getCurrentRoles().roles().contains(role));
    }
    return true;
  }

  Map<Class<?>, Set<String>> getAccessMap();

  Map<Class<?>, Boolean> getAuthenticationMap();

  default Set<String> getRoleAccess(Class<?> target) {
    return getAccessMap().get(target);
  }

  default boolean mustBeAuthenticated(Class<?> target) {
    return getAuthenticationMap().getOrDefault(target, true);
  }

  default void registerAccess(Class<?> target, boolean authenticated,
                              String... roles) {
    Set<String> accessSet = getAccessMap().get(target);
    if (accessSet == null) {
      accessSet = new HashSet<>();
      getAccessMap().put(target, accessSet);
    }
    Stream.of(roles).forEach(accessSet::add);
    getAuthenticationMap().put(target, authenticated);
  }
}
