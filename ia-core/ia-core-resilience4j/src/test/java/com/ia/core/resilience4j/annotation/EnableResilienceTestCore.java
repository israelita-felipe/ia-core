package com.ia.core.resilience4j.annotation;

import com.ia.core.resilience4j.config.ResilienceAutoConfiguration;
import com.ia.test.CoreBaseUnitTest;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Testes para a anotação EnableResilience baseados nos casos de teste documentados.
 */
class EnableResilienceTestCore extends CoreBaseUnitTest {

    @Test
    void deveTerTargetType() {
        // Arrange
        Target targetAnnotation = EnableResilience.class.getAnnotation(Target.class);

        // Act
        ElementType[] targets = targetAnnotation.value();

        // Assert
        assertThat(targets).containsExactly(ElementType.TYPE);
    }

    @Test
    void deveTerRetentionRuntime() {
        // Arrange
        Retention retentionAnnotation = EnableResilience.class.getAnnotation(Retention.class);

        // Act
        RetentionPolicy retention = retentionAnnotation.value();

        // Assert
        assertThat(retention).isEqualTo(RetentionPolicy.RUNTIME);
    }

    @Test
    void deveImportarResilienceAutoConfiguration() {
        // Arrange
        Import importAnnotation = EnableResilience.class.getAnnotation(Import.class);

        // Act
        Class<?>[] importedClasses = importAnnotation.value();

        // Assert
        assertThat(importedClasses).containsExactly(ResilienceAutoConfiguration.class);
    }
}
