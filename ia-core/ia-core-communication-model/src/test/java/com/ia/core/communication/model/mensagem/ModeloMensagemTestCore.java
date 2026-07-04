package com.ia.core.communication.model.mensagem;

import com.ia.test.CoreBaseUnitTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Testes de ModeloMensagem")
class ModeloMensagemTestCore extends CoreBaseUnitTest {

  @Nested
  @DisplayName("criação")
  class Criacao {

    @Test
    @DisplayName("CT001 - Deve criar modelo com campos obrigatórios")
    void deveCriarModeloComCamposObrigatorios() {
      ModeloMensagem modelo = ModeloMensagem.builder()
          .nome("Modelo Teste")
          .corpoModelo("Conteúdo")
          .tipoCanal(TipoCanal.WHATSAPP)
          .build();
      assertThat(modelo).isNotNull();
      assertThat(modelo.getNome()).isEqualTo("Modelo Teste");
    }

    @Test
    @DisplayName("CT006 - Deve permitir definir ativo")
    void devePermitirDefinirAtivo() {
      ModeloMensagem modelo = ModeloMensagem.builder()
          .nome("Modelo Teste")
          .corpoModelo("Conteúdo")
          .tipoCanal(TipoCanal.WHATSAPP)
          .ativo(true)
          .build();
      assertThat(modelo.getAtivo()).isTrue();
    }
  }

  @Nested
  @DisplayName("constantes")
  class Constantes {

    @Test
    @DisplayName("CT010 - Deve ter nome de tabela correto")
    void deveTerNomeDeTabelaCorreto() {
      assertThat(ModeloMensagem.TABLE_NAME).isEqualTo("COM_MODELO_MENSAGEM");
    }
  }
}
