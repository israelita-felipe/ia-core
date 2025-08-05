package com.ia.core.security.model.privilege;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.google.gson.Gson;
import com.ia.core.security.model.functionality.Context;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 *
 */
@RequiredArgsConstructor
public class PrivilegeTypeContext
  implements Context {
  @Getter
  private final PrivilegeType type;
  private final Map<String, Object> values = new HashMap<>();

  @Override
  public String getKey() {
    return type.name();
  }

  @Override
  public Object getValue() {
    return type.getCodigo();
  }

  @Override
  public int hashCode() {
    return Objects.hash(type, values);
  }

  @Override
  public Object get(String key) {
    return values.get(key);
  }

  @Override
  public boolean put(String key, Object value) {
    return values.put(key, value) != null;
  }

  @Override
  public boolean remove(String key) {
    return values.remove(key) != null;
  }

  @Override
  public boolean matches(Context context) {
    if (PrivilegeTypeContext.class.isInstance(context)) {
      PrivilegeTypeContext c = (PrivilegeTypeContext) context;
      return Objects.equals(type, c.type)
          && values.entrySet().stream().allMatch(entry -> {
            return c.values.entrySet().stream().anyMatch(privilegeEntry -> {
              return Objects.equals(privilegeEntry.getKey(), entry.getKey())
                  && Objects.equals(privilegeEntry.getValue(),
                                    entry.getValue());
            });
          });
    }
    return values.entrySet().stream().anyMatch(entry -> {
      return Objects.equals(context.getKey(), entry.getKey())
          && Objects.equals(context.getValue(), entry.getValue());
    });
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    PrivilegeTypeContext other = (PrivilegeTypeContext) obj;
    return type == other.type && values.equals(other.values);
  }

  @Override
  public String marshall() {
    return new Gson().toJson(this);
  }

  public static PrivilegeTypeContext of(String value) {
    return new Gson().fromJson(value, PrivilegeTypeContext.class);
  }
}
