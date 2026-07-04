package com.ia.core.communication.model.mensagem;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class MensagemTest {

    @Test
    void testMensagemConstruction() {
        // Given
        Mensagem mensagem = new Mensagem();

        // When
        mensagem.setId(1L);
        mensagem.setCorpoMensagem("Conteúdo teste");
        mensagem.setStatusMensagem(StatusMensagem.PENDENTE);
        mensagem.setTipoCanal(TipoCanal.EMAIL);

        // Then
        assertThat(mensagem.getId()).isEqualTo(1L);
        assertThat(mensagem.getCorpoMensagem()).isEqualTo("Conteúdo teste");
        assertThat(mensagem.getStatusMensagem()).isEqualTo(StatusMensagem.PENDENTE);
        assertThat(mensagem.getTipoCanal()).isEqualTo(TipoCanal.EMAIL);
    }

    @Test
    void testMensagemStatusTransitions() {
        // Given
        Mensagem mensagem = new Mensagem();
        mensagem.setStatusMensagem(StatusMensagem.PENDENTE);

        // When
        mensagem.setStatusMensagem(StatusMensagem.ENVIADA);

        // Then
        assertThat(mensagem.getStatusMensagem()).isEqualTo(StatusMensagem.ENVIADA);
    }
}
