package com.ia.core.model.filter;

import com.ia.test.CoreBaseUnitTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Testes para SortDirection baseados nos casos de teste documentados.
 */
class SortDirectionTestCore extends CoreBaseUnitTest {

    @Test
    void deveTerDirecoesDisponiveis() {
        // Act & Assert
        assertThat(SortDirection.values()).isNotEmpty();
        assertThat(SortDirection.values()).contains(
            SortDirection.ASC,
            SortDirection.DESC
        );
    }

    @Test
    void deveTerDirecaoAscendente() {
        // Act
        SortDirection direcao = SortDirection.ASC;

        // Assert
        assertThat(direcao).isNotNull();
        assertThat(direcao.name()).isEqualTo("ASC");
    }

    @Test
    void deveTerDirecaoDescendente() {
        // Act
        SortDirection direcao = SortDirection.DESC;

        // Assert
        assertThat(direcao).isNotNull();
        assertThat(direcao.name()).isEqualTo("DESC");
    }
}
