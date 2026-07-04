package com.ia.core.communication.model.mensagem;

import com.ia.test.CoreBaseUnitTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Testes de TipoCanal")
class TipoCanalTestCore extends CoreBaseUnitTest {

  @Test
  @DisplayName("CT006 - Deve ter 5 constantes")
  void deveTer5Constantes() {
    assertThat(TipoCanal.values()).hasSize(5);
  }

  @Test
  @DisplayName("CT007 - Deve retornar descrição correta")
  void deveRetornarDescricaoCorreta() {
    assertThat(TipoCanal.WHATSAPP.getDescricao()).isEqualTo("WhatsApp");
    assertThat(TipoCanal.SMS.getDescricao()).isEqualTo("SMS");
    assertThat(TipoCanal.EMAIL.getDescricao()).isEqualTo("E-mail");
  }
}
