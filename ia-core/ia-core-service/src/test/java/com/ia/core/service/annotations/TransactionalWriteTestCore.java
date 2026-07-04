package com.ia.core.service.annotations;

import com.ia.test.CoreBaseUnitTest;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Testes para TransactionalWrite baseados nos casos de teste documentados.
 */
class TransactionalWriteTestCore extends CoreBaseUnitTest {

    @Test
    void deveTerTargetMethod() {
        // Arrange
        Target targetAnnotation = TransactionalWrite.class.getAnnotation(Target.class);

        // Act
        ElementType[] targets = targetAnnotation.value();

        // Assert
        assertThat(targets).containsExactly(ElementType.METHOD);
    }

    @Test
    void deveTerRetentionRuntime() {
        // Arrange
        Retention retentionAnnotation = TransactionalWrite.class.getAnnotation(Retention.class);

        // Act
        RetentionPolicy retention = retentionAnnotation.value();

        // Assert
        assertThat(retention).isEqualTo(RetentionPolicy.RUNTIME);
    }

    @Test
    void deveTerTransactionalComReadOnlyFalse() {
        // Arrange
        Transactional transactionalAnnotation = TransactionalWrite.class.getAnnotation(Transactional.class);

        // Act
        boolean readOnly = transactionalAnnotation.readOnly();

        // Assert
        assertThat(readOnly).isFalse();
    }

    @Test
    void deveTerTransactionalComPropagationRequired() {
        // Arrange
        Transactional transactionalAnnotation = TransactionalWrite.class.getAnnotation(Transactional.class);

        // Act
        Propagation propagation = transactionalAnnotation.propagation();

        // Assert
        assertThat(propagation).isEqualTo(Propagation.REQUIRED);
    }

    @Test
    void deveTerTransactionalComIsolationDefault() {
        // Arrange
        Transactional transactionalAnnotation = TransactionalWrite.class.getAnnotation(Transactional.class);

        // Act
        Isolation isolation = transactionalAnnotation.isolation();

        // Assert
        assertThat(isolation).isEqualTo(Isolation.DEFAULT);
    }

    @Test
    void deveTerTransactionalComTimeout30() {
        // Arrange
        Transactional transactionalAnnotation = TransactionalWrite.class.getAnnotation(Transactional.class);

        // Act
        int timeout = transactionalAnnotation.timeout();

        // Assert
        assertThat(timeout).isEqualTo(30);
    }
}
