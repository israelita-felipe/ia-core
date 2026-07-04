package com.ia.core.communication.model.mensagem;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class TipoCanalTest {

    @Test
    void testTipoCanalValues() {
        // Then
        assertThat(TipoCanal.values()).containsExactlyInAnyOrder(
            TipoCanal.EMAIL,
            TipoCanal.SMS,
            TipoCanal.WHATSAPP,
            TipoCanal.TELEGRAM,
            TipoCanal.WEBHOOK
        );
    }

    @Test
    void testTipoCanalValueOf() {
        // When & Then
        assertThat(TipoCanal.valueOf("EMAIL")).isEqualTo(TipoCanal.EMAIL);
        assertThat(TipoCanal.valueOf("SMS")).isEqualTo(TipoCanal.SMS);
        assertThat(TipoCanal.valueOf("WHATSAPP")).isEqualTo(TipoCanal.WHATSAPP);
    }
}
