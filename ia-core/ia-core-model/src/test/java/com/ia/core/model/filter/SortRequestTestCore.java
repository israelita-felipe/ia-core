package com.ia.core.model.filter;

import com.ia.test.CoreBaseUnitTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Testes para SortRequest baseados nos casos de teste documentados.
 */
class SortRequestTestCore extends CoreBaseUnitTest {

    @Test
    void deveCriarSortRequestComCampoEDirecao() {
        // Arrange
        String campo = "nome";
        SortDirection direcao = SortDirection.ASC;

        // Act
        SortRequest sortRequest = new SortRequest(campo, direcao);

        // Assert
        assertThat(sortRequest).isNotNull();
        assertThat(sortRequest.getKey()).isEqualTo(campo);
        assertThat(sortRequest.getDirection()).isEqualTo(direcao);
    }

    @Test
    void deveCriarSortRequestComDirecaoDescendente() {
        // Arrange
        String campo = "dataCriacao";
        SortDirection direcao = SortDirection.DESC;

        // Act
        SortRequest sortRequest = new SortRequest(campo, direcao);

        // Assert
        assertThat(sortRequest).isNotNull();
        assertThat(sortRequest.getKey()).isEqualTo(campo);
        assertThat(sortRequest.getDirection()).isEqualTo(direcao);
    }

    @Test
    void deveRetornarCampo() {
        // Arrange
        String campo = "email";
        SortRequest sortRequest = new SortRequest(campo, SortDirection.ASC);

        // Act
        String campoRetornado = sortRequest.getKey();

        // Assert
        assertThat(campoRetornado).isEqualTo(campo);
    }

    @Test
    void deveRetornarDirecao() {
        // Arrange
        SortDirection direcao = SortDirection.ASC;
        SortRequest sortRequest = new SortRequest("nome", direcao);

        // Act
        SortDirection direcaoRetornada = sortRequest.getDirection();

        // Assert
        assertThat(direcaoRetornada).isEqualTo(direcao);
    }

    @Test
    void deveAtualizarCampo() {
        // Arrange
        SortRequest sortRequest = new SortRequest("campoAntigo", SortDirection.ASC);
        String novoCampo = "novoCampo";

        // Act
        sortRequest.setKey(novoCampo);

        // Assert
        assertThat(sortRequest.getKey()).isEqualTo(novoCampo);
    }

    @Test
    void deveAtualizarDirecao() {
        // Arrange
        SortRequest sortRequest = new SortRequest("nome", SortDirection.ASC);

        // Act
        sortRequest.setDirection(SortDirection.DESC);

        // Assert
        assertThat(sortRequest.getDirection()).isEqualTo(SortDirection.DESC);
    }
}
