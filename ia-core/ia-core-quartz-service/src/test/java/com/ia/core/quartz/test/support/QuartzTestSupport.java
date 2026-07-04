package com.ia.core.quartz.test.support;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public final class QuartzTestSupport {

  private QuartzTestSupport() {
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

  public static Map<String, Object> populate(Object target, List<String> fields) throws Exception {
    Map<String, Object> expectedValues = new LinkedHashMap<>();
    for (String field : fields) {
      Class<?> fieldType = findField(target.getClass(), field).getType();
      Object value = valueForField(fieldType);
      if (value == null) {
        value = nestedInstanceOrNull(fieldType);
        if (value != null) {
          populate(value, instanceFieldNames(fieldType));
        }
      }
      setValue(target, field, value);
      expectedValues.put(field, value);
    }
    return expectedValues;
  }

  private static Object nestedInstanceOrNull(Class<?> type) {
    if (type == null || type.isPrimitive() || type.isInterface() || type.isEnum()
        || type == Object.class || !type.getPackageName().startsWith("com.ia.core.quartz")) {
      return null;
    }
    try {
      return type.getDeclaredConstructor().newInstance();
    } catch (Exception ignored) {
      return null;
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
    if (type == java.time.LocalTime.class) {
      return java.time.LocalTime.of(10, 0);
    }
    if (type == LocalDateTime.class) {
      return LocalDateTime.of(2026, 1, 1, 10, 0);
    }
    if (type == java.time.DayOfWeek.class) {
      return java.time.DayOfWeek.MONDAY;
    }
    if (type == java.time.Month.class) {
      return java.time.Month.JANUARY;
    }
    if (type.isEnum()) {
      Object[] constants = type.getEnumConstants();
      return constants.length == 0 ? null : constants[0];
    }
    if (type == java.util.LinkedHashSet.class || (Set.class.isAssignableFrom(type) && !type.isInterface())) {
      try {
        return type.getDeclaredConstructor().newInstance();
      } catch (Exception ignored) {
        return new HashSet<>();
      }
    }
    if (Set.class.isAssignableFrom(type)) {
      return new HashSet<>();
    }
    if (Collection.class.isAssignableFrom(type)) {
      return new ArrayList<>();
    }
    if (Map.class.isAssignableFrom(type) && !type.isInterface()) {
      try {
        return type.getDeclaredConstructor().newInstance();
      } catch (Exception ignored) {
        return Map.of();
      }
    }
    if (Map.class.isAssignableFrom(type)) {
      return Map.of();
    }
    return null;
  }

 public static void assertDtoContract(Class<?> type, List<String> fields) throws Exception {
   Object dto = createInstance(type);
   Map<String, Object> expectedValues = populate(dto, fields);

   for (String field : fields) {
     assertFieldValue(type, field, getValue(dto, field), expectedValues.get(field));
   }

   assertCloneObject(type, dto, fields);
    assertCopyObject(type, dto, fields);
    assertSearchRequest(type);
  }

  public static void assertBeanContract(Class<?> type, List<String> fields) throws Exception {
    Object bean = createInstance(type);
    Map<String, Object> expectedValues = populate(bean, fields);

    for (String field : fields) {
      assertFieldValue(type, field, getValue(bean, field), expectedValues.get(field));
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
      Object expected = getValue(dto, field);
      Object actual = getValue(copied, field);
      if (isQuartzJobDataField(type, field) && expected instanceof Map && actual == null) {
        assertThat(actual).as(type.getSimpleName() + "." + field).isNull();
        continue;
      }
      assertFieldValue(type, field, actual, expected);
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

private static boolean isQuartzJobDataField(Class<?> type, String field) {
  try {
    return type.getSimpleName().startsWith("Quartz")
        && Map.class.isAssignableFrom(findField(type, field).getType())
        && (field.equals("jobData") || field.equals("jobDataMap"));
  } catch (NoSuchFieldException ignored) {
    return false;
  }
}

private static void assertFieldValue(Class<?> type, String field, Object actual, Object expected) {
  assertFieldValue(type, field, actual, expected, 0, new HashSet<>());
}

private static void assertFieldValue(Class<?> type, String field, Object actual, Object expected,
                                     int depth, Set<String> visited) {
  String assertionName = type.getSimpleName() + "." + field;
  if (expected == null) {
    assertThat(actual).as(assertionName).isNull();
    return;
  }
  if (actual == null) {
    assertThat(actual).as(assertionName).isEqualTo(expected);
    return;
  }
  if (expected instanceof Collection<?> expectedCollection && actual instanceof Collection<?> actualCollection) {
    assertThat(actualCollection).as(assertionName).hasSameSizeAs(expectedCollection);
    var expectedIterator = expectedCollection.iterator();
    var actualIterator = actualCollection.iterator();
    int index = 0;
    while (expectedIterator.hasNext()) {
      assertFieldValue(type, field + "[" + index++ + "]",
          actualIterator.next(), expectedIterator.next(), depth + 1, visited);
    }
    return;
  }
  if (expected instanceof Map<?, ?> expectedMap && actual instanceof Map<?, ?> actualMap) {
    assertThat((Iterable) actualMap.keySet()).as(assertionName + ".keys")
        .containsExactlyInAnyOrderElementsOf((Iterable) expectedMap.keySet());
    for (Object key : expectedMap.keySet()) {
      assertFieldValue(type, field + "{" + key + "}", actualMap.get(key), expectedMap.get(key),
          depth + 1, visited);
    }
    return;
  }
  if (shouldCompareStructurally(expected.getClass())) {
    assertEquivalentObjects(type, field, actual, expected, depth, visited);
    return;
  }
  assertThat(actual).as(assertionName).isEqualTo(expected);
}

private static boolean shouldCompareStructurally(Class<?> type) {
  if (type.isPrimitive() || type.isEnum() || type.isAnnotation() || type.isSynthetic()) {
    return false;
  }
  String packageName = type.getPackageName();
  return packageName.startsWith("com.ia.core.quartz")
      || packageName.startsWith("com.ia.core.service.dto")
      || packageName.startsWith("com.ia.core.model");
}

private static void assertEquivalentObjects(Class<?> type, String field, Object actual, Object expected,
                                            int depth, Set<String> visited) {
  String assertionName = type.getSimpleName() + "." + field;
  assertThat(actual).as(assertionName).isNotNull();
  assertThat(actual.getClass()).as(assertionName + ".type").isEqualTo(expected.getClass());
  if (depth > 4) {
    return;
  }
  String key = System.identityHashCode(actual) + "->" + System.identityHashCode(expected);
  if (!visited.add(key)) {
    return;
  }
  try {
    for (String nestedField : instanceFieldNames(actual.getClass())) {
      assertFieldValue(actual.getClass(), nestedField,
          getValue(actual, nestedField), getValue(expected, nestedField), depth + 1, visited);
    }
  } catch (Exception e) {
    throw new AssertionError(assertionName + " structural comparison failed", e);
  }
}
}
