package com.ia.core.view.utils;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.cloud.openfeign.support.SpringMvcContract;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import feign.MethodMetadata;
import feign.Util;

/**
 * Contrato de hierarquia do feign para que seja possível utilizar herança e
 * polimorfismo
 */
public class FeignHierarchicalContract
  extends SpringMvcContract {
  /** Carregador de recursos */
  private ResourceLoader resourceLoader;

  /**
   * Encontra uma classe dado a classe aual e a de destino
   *
   * @param currentClass   classe atual
   * @param searchClass    classe de destino
   * @param classHierarchy hierarquia de classes
   * @return <code>true</code> se for possível encontrar a classe a partir da
   *         classe atual
   */
  private boolean findClass(final Class<?> currentClass,
                            final Class<?> searchClass,
                            final LinkedList<Class<?>> classHierarchy) {
    if (currentClass == searchClass) {
      return true;
    }
    final Class<?>[] interfaces = currentClass.getInterfaces();
    for (final Class<?> currentInterface : interfaces) {
      classHierarchy.add(currentInterface);
      final boolean findClass = this.findClass(currentInterface,
                                               searchClass, classHierarchy);
      if (findClass) {
        return true;
      }
      classHierarchy.removeLast();
    }
    return false;
  }

  @Override
  public List<MethodMetadata> parseAndValidateMetadata(Class<?> targetType) {
    Util.checkState(targetType.getTypeParameters().length == 0,
                    "Parameterized types unsupported: %s",
                    targetType.getSimpleName());
    final Map<String, MethodMetadata> result = new LinkedHashMap<>();
    for (final Method method : targetType.getMethods()) {
      if (method.getDeclaringClass() == Object.class
          || (method.getModifiers() & Modifier.STATIC) != 0
          || Util.isDefault(method)) {
        continue;
      }
      final MethodMetadata metadata = this
          .parseAndValidateMetadata(targetType, method);
      Util.checkState(!result.containsKey(metadata.configKey()),
                      "Overrides unsupported: %s", metadata.configKey());
      result.put(metadata.configKey(), metadata);
    }
    return new ArrayList<>(result.values());
  }

  @Override
  public MethodMetadata parseAndValidateMetadata(final Class<?> targetType,
                                                 final Method method) {
    final MethodMetadata methodMetadata = super.parseAndValidateMetadata(targetType,
                                                                         method);

    final LinkedList<Class<?>> classHierarchy = new LinkedList<>();
    classHierarchy.add(targetType);
    this.findClass(targetType, method.getDeclaringClass(), classHierarchy);
    classHierarchy.stream().map(this::processPathValue)
        .filter(Optional::isPresent).map(Optional::get).findFirst()
        .ifPresent((path) -> methodMetadata.template().target(path));
    return methodMetadata;
  }

  @Override
  protected void processAnnotationOnClass(final MethodMetadata data,
                                          final Class<?> clz) {
    // skip this step
  }

  /**
   * Processa o valor dos endpoints do caminho da classe
   *
   * @param clz Classe
   * @return Caminho
   */
  private Optional<String> processPathValue(final Class<?> clz) {
    Optional<String> result = Optional.empty();
    final RequestMapping classAnnotation = clz
        .getAnnotation(RequestMapping.class);
    if (classAnnotation != null) {
      final RequestMapping synthesizeAnnotation = AnnotationUtils
          .synthesizeAnnotation(classAnnotation, clz);
      // Prepend path from class annotation if specified
      if (synthesizeAnnotation.value().length > 0) {
        String pathValue = Util
            .emptyToNull(synthesizeAnnotation.value()[0]);
        pathValue = this.resolveValue(pathValue);
        if (!pathValue.startsWith("/")) {
          pathValue = "/" + pathValue;
        }
        result = Optional.of(pathValue);
      }
    }
    return result;
  }

  /**
   * Resolve os placeholders de um dado caminho
   *
   * @param value placeholder
   * @return valor do placeholder
   */
  private String resolveValue(final String value) {
    if (StringUtils.hasText(value)
        && this.resourceLoader instanceof ConfigurableApplicationContext) {
      return ((ConfigurableApplicationContext) this.resourceLoader)
          .getEnvironment().resolvePlaceholders(value);
    }
    return value;
  }

  @Override
  public void setResourceLoader(final ResourceLoader resourceLoader) {
    this.resourceLoader = resourceLoader;
    super.setResourceLoader(resourceLoader);
  }
}
