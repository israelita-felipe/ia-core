package com.ia.core.llm.test.support;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Collectors;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public final class DtoTestSupport {

  private DtoTestSupport() {
  }

  public static Object createDto(Class<?> dtoType, List<String> fields) throws Exception {
    if (dtoType.isRecord()) {
      return createRecordDto(dtoType);
    }

    Object dto = dtoType.getDeclaredConstructor().newInstance();
    for (String field : fields) {
      Class<?> type = findField(dtoType, field).getType();
      setValue(dto, field, valueForField(type));
    }
    return dto;
  }

  public static Object valueForField(Class<?> type) {
    if (type == String.class) {
      return "valor";
    }
    if (type == boolean.class) {
      return true;
    }
    if (type == Boolean.class) {
      return true;
    }
    if (type == int.class) {
      return 1;
    }
    if (type == Integer.class) {
      return 1;
    }
    if (type == long.class) {
      return 1L;
    }
    if (type == Long.class) {
      return 1L;
    }
    if (type == double.class) {
      return 1.0d;
    }
    if (type == Double.class) {
      return 1.0d;
    }
    if (type == LocalDate.class) {
      return LocalDate.of(2026, 1, 1);
    }
    if (type == LocalDateTime.class) {
      return LocalDateTime.of(2026, 1, 1, 10, 0);
    }
    if (type.isEnum()) {
      Object[] constants = type.getEnumConstants();
      return constants.length == 0 ? null : constants[0];
    }
    if (Set.class.isAssignableFrom(type)) {
      return new HashSet<>();
    }
    if (List.class.isAssignableFrom(type)) {
      return new ArrayList<>();
    }
    if (Collection.class.isAssignableFrom(type)) {
      return new ArrayList<>();
    }
    if (Map.class.isAssignableFrom(type)) {
      return Map.of();
    }
    return null;
  }

  public static void assertCloneObject(Class<?> dtoType, Object dto, List<String> fields) throws Exception {
    Optional<Method> cloneMethod = findMethod(dtoType, "cloneObject");
    if (cloneMethod.isEmpty()) {
      assertThat(hasMethod(dtoType, "cloneObject")).as("cloneObject").isFalse();
      return;
    }

    Object cloned = cloneMethod.get().invoke(dto);
    assertThat(cloned).isNotNull().isNotSameAs(dto);
    for (String field : fields) {
      Object expected = getValue(dto, field);
      Object actual = getValue(cloned, field);
      assertThat(actual).as(field).isEqualTo(expected);
    }
  }

  public static void assertCopyObject(Class<?> dtoType, Object dto, List<String> fields) throws Exception {
    Optional<Method> copyMethod = findMethod(dtoType, "copyObject");
    if (copyMethod.isEmpty()) {
      assertThat(hasMethod(dtoType, "copyObject")).as("copyObject").isFalse();
      return;
    }

    Object copied = copyMethod.get().invoke(dto);
    assertThat(copied).isNotNull().isNotSameAs(dto);
    for (String field : fields) {
      Object expected = getValue(dto, field);
      Object actual = getValue(copied, field);
      assertThat(actual).as(field).isEqualTo(expected);
    }
  }

  public static void assertCampos(Class<?> dtoType, List<String> fields) throws Exception {
    Optional<Class<?>> camposType = findCampos(dtoType);
    if (camposType.isEmpty()) {
      assertThat(hasCampos(dtoType)).as("CAMPOS").isFalse();
      return;
    }

    try {
      Method values = camposType.get().getMethod("values");
      Object rawValues = values.invoke(null);
      Set<String> actual = collectCamposConstants(camposType.get());
      if (rawValues instanceof Collection<?> collection) {
        actual.addAll(collection.stream().map(String::valueOf).toList());
        assertThat(actual).containsAll(fields);
        return;
      }
      if (rawValues instanceof Object[] array) {
        actual.addAll(Arrays.stream(array).map(String::valueOf).toList());
        assertThat(actual).containsAll(fields);
        return;
      }
      throw new AssertionError("CAMPOS.values() deve retornar Collection ou Object[]");
    } catch (NoSuchMethodException e) {
      Set<String> actual = collectCamposConstants(camposType.get());
      assertThat(actual).containsAll(fields);
    }
  }

  public static void assertSearchRequest(Class<?> dtoType) throws Exception {
    Optional<Method> method = findMethod(dtoType, "getSearchRequest");
    if (method.isEmpty()) {
      assertThat(hasMethod(dtoType, "getSearchRequest")).as("getSearchRequest").isFalse();
      return;
    }

    Object searchRequest = method.get().invoke(null);
    assertThat(searchRequest).isNotNull();
  }

  public static Object getValue(Object target, String field) throws Exception {
    Field declaredField = findField(target.getClass(), field);
    Class<?> type = declaredField.getType();
    Method getter = findGetter(target.getClass(), field, type);
    return getter.invoke(target);
  }

  public static List<String> fields(Class<?> dtoType) {
    if (dtoType.isRecord()) {
      List<String> fields = new ArrayList<>();
      for (var component : dtoType.getRecordComponents()) {
        fields.add(component.getName());
      }
      return fields;
    }

    List<String> fields = new ArrayList<>();
    for (Field field : dtoType.getDeclaredFields()) {
      fields.add(field.getName());
    }
    return fields;
  }

  private static Object createRecordDto(Class<?> dtoType) throws Exception {
    var components = dtoType.getRecordComponents();
    Class<?>[] parameterTypes = new Class<?>[components.length];
    Object[] values = new Object[components.length];

    for (int i = 0; i < components.length; i++) {
      parameterTypes[i] = components[i].getType();
      values[i] = valueForField(parameterTypes[i]);
    }

    Constructor<?> constructor = dtoType.getDeclaredConstructor(parameterTypes);
    constructor.setAccessible(true);
    return constructor.newInstance(values);
  }

  private static void setValue(Object target, String field, Object value) throws Exception {
    Class<?> type = target.getClass().getDeclaredField(field).getType();
    String setterName = "set" + capitalize(field);
    Method setter = target.getClass().getMethod(setterName, type);
    setter.invoke(target, value);
  }

  private static Method findGetter(Class<?> type, String field, Class<?> fieldType) throws NoSuchMethodException {
    try {
      return type.getMethod(field);
    } catch (NoSuchMethodException e) {
      // continua para getter JavaBean
    }

    String capitalized = capitalize(field);
    if (boolean.class.equals(fieldType) || Boolean.class.equals(fieldType)) {
      try {
        return type.getMethod("is" + capitalized);
      } catch (NoSuchMethodException ignored) {
        return type.getMethod("get" + capitalized);
      }
    }
    return type.getMethod("get" + capitalized);
  }

  private static Field findField(Class<?> type, String fieldName) throws NoSuchFieldException {
    Class<?> current = type;
    while (current != null) {
      try {
        return current.getDeclaredField(fieldName);
      } catch (NoSuchFieldException ignored) {
        current = current.getSuperclass();
      }
    }
    throw new NoSuchFieldException(fieldName);
  }

  private static Set<String> collectCamposConstants(Class<?> type) {
    Set<String> constants = new HashSet<>();
    Class<?> current = type;
    while (current != null) {
      for (Field field : current.getDeclaredFields()) {
        int modifiers = field.getModifiers();
        if (Modifier.isStatic(modifiers) && Modifier.isFinal(modifiers) && field.getType() == String.class) {
          constants.add(field.getName());
          try {
            field.setAccessible(true);
            Object value = field.get(null);
            if (value instanceof String stringValue) {
              constants.add(stringValue);
            }
          } catch (IllegalAccessException ignored) {
            // ignora constantes que não puderem ser acessadas
          }
        }
      }
      current = current.getSuperclass();
    }
    return constants;
  }

  private static Optional<Method> findMethod(Class<?> type, String methodName) {
    try {
      return Optional.of(type.getMethod(methodName));
    } catch (NoSuchMethodException e) {
      return Optional.empty();
    }
  }

  private static boolean hasMethod(Class<?> type, String methodName) {
    return findMethod(type, methodName).isPresent();
  }

  private static Optional<Class<?>> findCampos(Class<?> type) {
    try {
      return Optional.of(Class.forName(type.getName() + "$CAMPOS"));
    } catch (ClassNotFoundException e) {
      return Optional.empty();
    }
  }

  private static boolean hasCampos(Class<?> type) {
    return findCampos(type).isPresent();
  }

  private static String capitalize(String value) {
    return value.substring(0, 1).toUpperCase() + value.substring(1);
  }
}
