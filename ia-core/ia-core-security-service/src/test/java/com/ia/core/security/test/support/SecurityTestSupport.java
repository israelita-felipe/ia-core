package com.ia.core.security.test.support;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public final class SecurityTestSupport {

  private SecurityTestSupport() {
  }

  public static List<String> instanceFieldNames(Class<?> type) {
    List<String> fields = new ArrayList<>();
    for (Field field : type.getDeclaredFields()) {
      int modifiers = field.getModifiers();
      if (!Modifier.isStatic(modifiers) && !Modifier.isFinal(modifiers)) {
        fields.add(field.getName());
      }
    }
    return fields;
  }

  public static Object createInstance(Class<?> type) throws Exception {
    return type.getDeclaredConstructor().newInstance();
  }

  public static void populate(Object target, List<String> fields) throws Exception {
    for (String field : fields) {
      Class<?> fieldType = findField(target.getClass(), field).getType();
      setValue(target, field, valueForField(fieldType));
    }
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
    if (type == int.class || type == Integer.class) {
      return 1;
    }
    if (type == long.class || type == Long.class) {
      return 1L;
    }
    if (type == double.class || type == Double.class) {
      return 1.0d;
    }
    if (type == float.class || type == Float.class) {
      return 1.0f;
    }
    if (type == short.class || type == Short.class) {
      return (short) 1;
    }
    if (type == byte.class || type == Byte.class) {
      return (byte) 1;
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
    if (Collection.class.isAssignableFrom(type)) {
      return new ArrayList<>();
    }
    if (Map.class.isAssignableFrom(type)) {
      return Map.of();
    }
    return null;
  }

 public static void assertDtoContract(Class<?> type, List<String> fields) throws Exception {
   Object dto = createInstance(type);
   populate(dto, fields);

   for (String field : fields) {
     Object expected = valueForField(findField(type, field).getType());
     assertFieldValue(type, field, getValue(dto, field), expected);
   }

   assertCloneObject(type, dto, fields);
    assertCopyObject(type, dto, fields);
    assertSearchRequest(type);
  }

  public static void assertBeanContract(Class<?> type, List<String> fields) throws Exception {
    Object bean = createInstance(type);
    populate(bean, fields);

    for (String field : fields) {
      Object expected = valueForField(findField(type, field).getType());
      assertFieldValue(type, field, getValue(bean, field), expected);
    }
  }

  public static void assertCampos(Class<?> type, List<String> fields) throws Exception {
    Optional<Class<?>> camposType = findCampos(type);
    if (camposType.isEmpty()) {
      assertThat(hasCampos(type)).as(type.getSimpleName() + ".CAMPOS").isFalse();
      return;
    }

    Set<String> actual = collectCamposConstants(camposType.get());
    try {
      Method values = camposType.get().getMethod("values");
      Object rawValues = values.invoke(null);
      if (rawValues instanceof Collection<?> collection) {
        actual.addAll(collection.stream().map(String::valueOf).toList());
      } else if (rawValues instanceof Object[] array) {
        actual.addAll(Arrays.stream(array).map(String::valueOf).toList());
      }
    } catch (NoSuchMethodException ignored) {
      // CAMPOS without values() is valid; constants are enough.
    }
    assertThat(actual).as(type.getSimpleName() + ".CAMPOS").containsAll(fields);
  }

  public static void assertSearchRequest(Class<?> type) throws Exception {
    Optional<Method> method = findMethod(type, "getSearchRequest");
    if (method.isEmpty()) {
      assertThat(hasMethod(type, "getSearchRequest")).as(type.getSimpleName() + ".getSearchRequest").isFalse();
      return;
    }

    Object searchRequest = method.get().invoke(null);
    assertThat(searchRequest).as(type.getSimpleName() + ".getSearchRequest()").isNotNull();
  }

  public static void assertPropertyFilters(Class<?> type, List<String> filters) throws Exception {
    Optional<Method> method = findMethod(type, "propertyFilters");
    if (method.isEmpty()) {
      assertThat(hasMethod(type, "propertyFilters")).as(type.getSimpleName() + ".propertyFilters").isFalse();
      return;
    }

    Object propertyFilters = method.get().invoke(null);
    assertThat(propertyFilters).as(type.getSimpleName() + ".propertyFilters()").isInstanceOf(Collection.class);
    assertThat((Collection<String>) propertyFilters).as(type.getSimpleName() + ".propertyFilters()").containsAll(filters);
  }

  public static Object getValue(Object target, String field) throws Exception {
    Class<?> type = target.getClass();
    Class<?> fieldType = findField(type, field).getType();
    Method getter = findGetter(type, field, fieldType);
    return getter.invoke(target);
  }

  private static void assertCloneObject(Class<?> type, Object dto, List<String> fields) throws Exception {
    Optional<Method> cloneMethod = findMethod(type, "cloneObject");
    if (cloneMethod.isEmpty()) {
      assertThat(hasMethod(type, "cloneObject")).as(type.getSimpleName() + ".cloneObject").isFalse();
      return;
    }

    Object cloned = cloneMethod.get().invoke(dto);
    assertThat(cloned).isNotNull().isNotSameAs(dto);
    for (String field : fields) {
      assertFieldValue(type, field, getValue(cloned, field), getValue(dto, field));
    }
  }

  private static void assertCopyObject(Class<?> type, Object dto, List<String> fields) throws Exception {
    Optional<Method> copyMethod = findMethod(type, "copyObject");
    if (copyMethod.isEmpty()) {
      assertThat(hasMethod(type, "copyObject")).as(type.getSimpleName() + ".copyObject").isFalse();
      return;
    }

    Object copied = copyMethod.get().invoke(dto);
    assertThat(copied).isNotNull().isNotSameAs(dto);
    for (String field : fields) {
      assertFieldValue(type, field, getValue(copied, field), getValue(dto, field));
    }
  }

  private static void setValue(Object target, String field, Object value) throws Exception {
    Class<?> type = target.getClass();
    Class<?> fieldType = findField(type, field).getType();
    String setterName = "set" + capitalize(field);
    Method setter = type.getMethod(setterName, fieldType);
    setter.invoke(target, value);
  }

  private static Method findGetter(Class<?> type, String field, Class<?> fieldType) throws NoSuchMethodException {
    try {
      return type.getMethod(field);
    } catch (NoSuchMethodException ignored) {
      // continua para JavaBean getter
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
    throw new NoSuchFieldException(type.getSimpleName() + "." + fieldName);
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
            // ignora constantes inacessíveis
          }
        }
      }
      current = current.getSuperclass();
    }
    return constants;
  }

 private static String capitalize(String value) {
   return value.substring(0, 1).toUpperCase() + value.substring(1);
 }

private static void assertFieldValue(Class<?> type, String field, Object actual, Object expected) {
 if (expected instanceof Collection<?> expectedCollection && actual instanceof Collection<?> actualCollection) {
   assertThat((Collection) actualCollection).as(type.getSimpleName() + "." + field)
       .containsExactlyElementsOf((Collection) expectedCollection);
   return;
 }
  assertThat(actual).as(type.getSimpleName() + "." + field).isEqualTo(expected);
}
}
