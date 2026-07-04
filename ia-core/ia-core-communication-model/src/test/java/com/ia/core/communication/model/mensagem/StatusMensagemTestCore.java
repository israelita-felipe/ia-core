package com.ia.core.communication.model.mensagem;

import com.ia.test.CoreBaseUnitTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Testes de StatusMensagem")
class StatusMensagemTestCore extends CoreBaseUnitTest {

  @Test
  @DisplayName("CT007 - Deve ter 6 constantes")
  void deveTer6Constantes() {
    assertThat(StatusMensagem.values()).hasSize(6);
  }

  @Test
  @DisplayName("CT008 - Deve retornar descrição correta")
  void deveRetornarDescricaoCorreta() {
    assertThat(StatusMensagem.PENDENTE.getDescricao()).isEqualTo("Pendente");
    assertThat(StatusMensagem.ENVIADA.getDescricao()).isEqualTo("Enviada");
    assertThat(StatusMensagem.ENTREGUE.getDescricao()).isEqualTo("Entregue");
  }
}
