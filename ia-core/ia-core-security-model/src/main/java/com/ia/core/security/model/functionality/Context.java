package com.ia.core.security.model.functionality;

/**
 *
 */
public interface Context {

  String getKey();

  Object getValue();

  boolean put(String key, Object value);

  Object get(String key);

  boolean remove(String key);

  boolean matches(Context context);

  String marshall();

}
