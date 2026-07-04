package com.ia.core.resilience4j.dto;

import com.ia.core.resilience4j.annotation.Resilient;
import com.ia.core.resilience4j.config.ResilienceProperties;
import com.ia.core.resilience4j.profile.ResilienceProfile;
import com.ia.core.resilience4j.registry.ResilienceRegistry;
import com.ia.test.CoreBaseUnitTest;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Testes para ResilienceContext baseados nos casos de teste documentados.
 */
class ResilienceContextTestCore extends CoreBaseUnitTest {

    @Test
    void deveCriarContextoComBuilder() {
        // Arrange
        ResilienceProfile profile = ResilienceProfile.DEFAULT;
        Method method = getTestMethod();
        Resilient annotation = method.getAnnotation(Resilient.class);
        Object[] args = new Object[]{"arg1", "arg2"};
        ResilienceProperties properties = new ResilienceProperties();
        ResilienceRegistry registry = new ResilienceRegistry(properties);

        // Act
        ResilienceContext context = ResilienceContext.builder()
            .profile(profile)
            .method(method)
            .annotation(annotation)
            .args(args)
            .resilienceRegistry(registry)
            .build();

        // Assert
        assertThat(context).isNotNull();
        assertThat(context.getProfile()).isEqualTo(profile);
        assertThat(context.getMethod()).isEqualTo(method);
        assertThat(context.getAnnotation()).isEqualTo(annotation);
        assertThat(context.getArgs()).isEqualTo(args);
        assertThat(context.getResilienceRegistry()).isEqualTo(registry);
    }

    @Test
    void deveVerificarCampoProfile() {
        // Arrange
        ResilienceProfile profile = ResilienceProfile.EXTERNAL_API;
        ResilienceContext context = ResilienceContext.builder()
            .profile(profile)
            .build();

        // Act
        ResilienceProfile result = context.getProfile();

        // Assert
        assertThat(result).isEqualTo(profile);
    }

    @Test
    void deveVerificarCampoMethod() {
        // Arrange
        Method method = getTestMethod();
        ResilienceContext context = ResilienceContext.builder()
            .method(method)
            .build();

        // Act
        Method result = context.getMethod();

        // Assert
        assertThat(result).isEqualTo(method);
    }

    @Test
    void deveVerificarCampoAnnotation() {
        // Arrange
        Resilient annotation = getTestMethod().getAnnotation(Resilient.class);
        ResilienceContext context = ResilienceContext.builder()
            .annotation(annotation)
            .build();

        // Act
        Resilient result = context.getAnnotation();

        // Assert
        assertThat(result).isEqualTo(annotation);
    }

    @Test
    void deveVerificarCampoArgs() {
        // Arrange
        Object[] args = new Object[]{"arg1", "arg2"};
        ResilienceContext context = ResilienceContext.builder()
            .args(args)
            .build();

        // Act
        Object[] result = context.getArgs();

        // Assert
        assertThat(result).isEqualTo(args);
    }

    @Test
    void deveVerificarCampoResilienceRegistry() {
        // Arrange
        ResilienceProperties properties = new ResilienceProperties();
        ResilienceRegistry registry = new ResilienceRegistry(properties);
        ResilienceContext context = ResilienceContext.builder()
            .resilienceRegistry(registry)
            .build();

        // Act
        ResilienceRegistry result = context.getResilienceRegistry();

        // Assert
        assertThat(result).isEqualTo(registry);
    }

    private Method getTestMethod() {
        try {
            return TestClass.class.getMethod("testMethod");
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }


    static class TestClass {
        @Resilient
        public void testMethod() {}
    }
}
