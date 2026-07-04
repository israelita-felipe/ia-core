package com.ia.core.communication.model.mensagem;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class StatusMensagemTest {

    @Test
    void testStatusMensagemValues() {
        // Then
        assertThat(StatusMensagem.values()).containsExactlyInAnyOrder(
            StatusMensagem.PENDENTE,
            StatusMensagem.ENVIADA,
            StatusMensagem.ENTREGUE,
            StatusMensagem.LIDA,
            StatusMensagem.FALHA,
            StatusMensagem.CANCELADA
        );
    }

    @Test
    void testStatusMensagemValueOf() {
        // When & Then
        assertThat(StatusMensagem.valueOf("PENDENTE")).isEqualTo(StatusMensagem.PENDENTE);
        assertThat(StatusMensagem.valueOf("ENVIADA")).isEqualTo(StatusMensagem.ENVIADA);
        assertThat(StatusMensagem.valueOf("FALHA")).isEqualTo(StatusMensagem.FALHA);
    }
}
