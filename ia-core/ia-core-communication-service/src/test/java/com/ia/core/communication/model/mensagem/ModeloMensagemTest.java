package com.ia.core.communication.model.mensagem;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class ModeloMensagemTest {

    @Test
    void testModeloMensagemConstruction() {
        // Given
        ModeloMensagem modelo = new ModeloMensagem();

        // When
        modelo.setId(1L);
        modelo.setNome("Modelo Teste");
        modelo.setCorpoModelo("Olá {nome}, bem-vindo!");

        // Then
        assertThat(modelo.getId()).isEqualTo(1L);
        assertThat(modelo.getNome()).isEqualTo("Modelo Teste");
        assertThat(modelo.getCorpoModelo()).isEqualTo("Olá {nome}, bem-vindo!");
    }

    @Test
    void testModeloMensagemWithVariaveis() {
        // Given
        ModeloMensagem modelo = new ModeloMensagem();
        modelo.setCorpoModelo("Olá {nome}, seu código é {codigo}");

        // Then
        assertThat(modelo.getCorpoModelo()).contains("{nome}");
        assertThat(modelo.getCorpoModelo()).contains("{codigo}");
    }
}
