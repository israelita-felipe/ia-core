package com.ia.core.communication.model.contato;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ContatoMensagemTest {

    @Test
    void testContatoMensagemConstruction() {
        // Given
        ContatoMensagem contatoMensagem = new ContatoMensagem();

        // When
        contatoMensagem.setId(1L);
        contatoMensagem.setTelefone("11999999999");

        // Then
        assertThat(contatoMensagem.getId()).isEqualTo(1L);
        assertThat(contatoMensagem.getTelefone()).isEqualTo("11999999999");
    }

    @Test
    void testContatoMensagemWithValidTelefone() {
        // Given
        ContatoMensagem contatoMensagem = new ContatoMensagem();
        contatoMensagem.setTelefone("11999999999");

        // Then
        assertThat(contatoMensagem.getTelefone()).hasSize(11);
    }
}
