package com.ia.core.communication.model.mensagem;

import com.ia.test.CoreBaseUnitTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test class for Mensagem entity.
 * <p>
 * Tests the Mensagem entity following ADR-012 testing patterns.
 * </p>
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@DisplayName("Testes de Mensagem")
class MensagemTestCore extends CoreBaseUnitTest {

  @Nested
  @DisplayName("criação de mensagem")
  class CriacaoMensagem {

    @Test
    @DisplayName("CT001 - Deve criar mensagem com campos obrigatórios")
    void deveCriarMensagemComCamposObrigatorios() {
      // Arrange & Act
      Mensagem mensagem = Mensagem.builder()
          .telefoneDestinatario("11999999999")
          .corpoMensagem("Conteúdo da mensagem")
          .tipoCanal(TipoCanal.WHATSAPP)
          .statusMensagem(StatusMensagem.PENDENTE)
          .build();

      // Assert
      assertThat(mensagem).isNotNull();
      assertThat(mensagem.getTelefoneDestinatario()).isEqualTo("11999999999");
      assertThat(mensagem.getCorpoMensagem()).isEqualTo("Conteúdo da mensagem");
      assertThat(mensagem.getTipoCanal()).isEqualTo(TipoCanal.WHATSAPP);
      assertThat(mensagem.getStatusMensagem()).isEqualTo(StatusMensagem.PENDENTE);
    }

    @Test
    @DisplayName("CT006 - Deve criar mensagem com campos opcionais")
    void deveCriarMensagemComCamposOpcionais() {
      // Arrange & Act
      Mensagem mensagem = Mensagem.builder()
          .telefoneDestinatario("11999999999")
          .corpoMensagem("Conteúdo da mensagem")
          .tipoCanal(TipoCanal.WHATSAPP)
          .statusMensagem(StatusMensagem.PENDENTE)
          .nomeDestinatario("João Silva")
          .idExterno("msg123")
          .build();

      // Assert
      assertThat(mensagem).isNotNull();
      assertThat(mensagem.getNomeDestinatario()).isEqualTo("João Silva");
      assertThat(mensagem.getIdExterno()).isEqualTo("msg123");
    }

    @Test
    @DisplayName("CT007 - Deve criar mensagem usando builder")
    void deveCriarMensagemUsandoBuilder() {
      // Arrange & Act
      Mensagem mensagem = Mensagem.builder()
          .telefoneDestinatario("11999999999")
          .corpoMensagem("Conteúdo da mensagem")
          .tipoCanal(TipoCanal.SMS)
          .statusMensagem(StatusMensagem.ENVIADA)
          .build();

      // Assert
      assertThat(mensagem).isNotNull();
      assertThat(mensagem.getTelefoneDestinatario()).isEqualTo("11999999999");
      assertThat(mensagem.getTipoCanal()).isEqualTo(TipoCanal.SMS);
      assertThat(mensagem.getStatusMensagem()).isEqualTo(StatusMensagem.ENVIADA);
    }

  }

  @Nested
  @DisplayName("herança e constantes")
  class HerancaEConstantes {

    @Test
    @DisplayName("CT009 - Deve herdar de BaseEntity")
    void deveHerdarDeBaseEntity() {
      // Arrange & Act
      Mensagem mensagem = Mensagem.builder().build();

      // Assert
      assertThat(mensagem).isInstanceOf(com.ia.core.model.BaseEntity.class);
    }

    @Test
    @DisplayName("CT010 - Deve ter nome de tabela correto")
    void deveTerNomeDeTabelaCorreto() {
      // Arrange & Act & Assert
      assertThat(Mensagem.TABLE_NAME).isEqualTo("COM_MENSAGEM");
    }

    @Test
    @DisplayName("CT011 - Deve ter nome de schema correto")
    void deveTerNomeDeSchemaCorreto() {
      // Arrange & Act & Assert
      assertThat(Mensagem.SCHEMA_NAME).isEqualTo("COMMUNICATION");
    }
  }
}
