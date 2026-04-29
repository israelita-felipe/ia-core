package com.ia.core.service.event;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Testes para {@link CrudOperationType}.
 *
 * @author Israel Araújo
 */
/**
 * Classe que representa os serviços de negócio para crud operation type test.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a CrudOperationTypeTest
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
 */
@DisplayName("CrudOperationType")
class CrudOperationTypeTest {

    @Nested
    @DisplayName("values")
    class TestesValues {

        @Test
        @DisplayName("Deve ter valor CREATED")
        void deveTerValorCreated() {
            // Então
            assertThat(CrudOperationType.values()).contains(CrudOperationType.CREATED);
        }

        @Test
        @DisplayName("Deve ter valor UPDATED")
        void deveTerValorUpdated() {
            // Então
            assertThat(CrudOperationType.values()).contains(CrudOperationType.UPDATED);
        }

        @Test
        @DisplayName("Deve ter valor DELETED")
        void deveTerValorDeleted() {
            // Então
            assertThat(CrudOperationType.values()).contains(CrudOperationType.DELETED);
        }

        @Test
        @DisplayName("Deve ter 3 valores no total")
        void deveTer3ValoresNoTotal() {
            // Então
            assertThat(CrudOperationType.values()).hasSize(3);
        }
    }

    @Nested
    @DisplayName("valueOf")
    class TestesValueOf {

        @Test
        @DisplayName("Deve retornar CREATED para string CREATED")
        void deveRetornarCreatedParaStringCreated() {
            // Quando
            CrudOperationType result = CrudOperationType.valueOf("CREATED");

            // Então
            assertThat(result).isEqualTo(CrudOperationType.CREATED);
        }

        @Test
        @DisplayName("Deve retornar UPDATED para string UPDATED")
        void deveRetornarUpdatedParaStringUpdated() {
            // Quando
            CrudOperationType result = CrudOperationType.valueOf("UPDATED");

            // Então
            assertThat(result).isEqualTo(CrudOperationType.UPDATED);
        }

        @Test
        @DisplayName("Deve retornar DELETED para string DELETED")
        void deveRetornarDeletedParaStringDeleted() {
            // Quando
            CrudOperationType result = CrudOperationType.valueOf("DELETED");

            // Então
            assertThat(result).isEqualTo(CrudOperationType.DELETED);
        }
    }
}
