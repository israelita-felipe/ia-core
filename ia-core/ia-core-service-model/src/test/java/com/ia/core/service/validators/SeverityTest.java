package com.ia.core.service.validators;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Severity")
class SeverityTest {

    @Test
    @DisplayName("ERROR deve ter ordem 0")
    void errorDeveOrdem0() {
        assertThat(Severity.ERROR.getOrder()).isEqualTo(0);
    }

    @Test
    @DisplayName("WARNING deve ter ordem 1")
    void warningDeveOrdem1() {
        assertThat(Severity.WARNING.getOrder()).isEqualTo(1);
    }

    @Test
    @DisplayName("INFO deve ter ordem 2")
    void infoDeveOrdem2() {
        assertThat(Severity.INFO.getOrder()).isEqualTo(2);
    }

    @Test
    @DisplayName("ERROR deve ser mais severo que WARNING e INFO")
    void errorDeveMaisSevero() {
        assertThat(Severity.ERROR.getOrder()).isLessThan(Severity.WARNING.getOrder());
        assertThat(Severity.WARNING.getOrder()).isLessThan(Severity.INFO.getOrder());
    }

    @Test
    @DisplayName("Deve ter 3 valores")
    void deveTerTresValores() {
        assertThat(Severity.values()).hasSize(3);
    }
}
