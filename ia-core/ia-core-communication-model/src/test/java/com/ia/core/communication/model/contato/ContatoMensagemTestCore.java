package com.ia.core.communication.model.contato;

import com.ia.test.CoreBaseUnitTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test class for ContatoMensagem entity.
 * <p>
 * Tests the ContatoMensagem entity following ADR-012 testing patterns.
 * </p>
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@DisplayName("Testes de ContatoMensagem")
class ContatoMensagemTestCore extends CoreBaseUnitTest {

  @Nested
  @DisplayName("criação de contato")
  class CriacaoContato {

    @Test
    @DisplayName("CT001 - Deve criar contato com campos obrigatórios")
    void deveCriarContatoComCamposObrigatorios() {
      // Arrange
      GrupoContato grupo = GrupoContato.builder()
          .nome("Grupo Teste")
          .build();

      // Act
      ContatoMensagem contato = ContatoMensagem.builder()
          .grupoContato(grupo)
          .telefone("11999999999")
          .build();

      // Assert
      assertThat(contato).isNotNull();
      assertThat(contato.getGrupoContato()).isNotNull();
      assertThat(contato.getTelefone()).isEqualTo("11999999999");
    }

    @Test
    @DisplayName("CT004 - Deve criar contato com campo nome opcional")
    void deveCriarContatoComCampoNomeOpcional() {
      // Arrange
      GrupoContato grupo = GrupoContato.builder()
          .nome("Grupo Teste")
          .build();

      // Act
      ContatoMensagem contato = ContatoMensagem.builder()
          .grupoContato(grupo)
          .telefone("11999999999")
          .nome("João Silva")
          .build();

      // Assert
      assertThat(contato).isNotNull();
      assertThat(contato.getNome()).isEqualTo("João Silva");
    }

    @Test
    @DisplayName("CT005 - Deve criar contato usando builder")
    void deveCriarContatoUsandoBuilder() {
      // Arrange
      GrupoContato grupo = GrupoContato.builder()
          .nome("Grupo Teste")
          .build();

      // Act
      ContatoMensagem contato = ContatoMensagem.builder()
          .grupoContato(grupo)
          .telefone("11999999999")
          .nome("Maria Santos")
          .build();

      // Assert
      assertThat(contato).isNotNull();
      assertThat(contato.getTelefone()).isEqualTo("11999999999");
      assertThat(contato.getNome()).isEqualTo("Maria Santos");
    }

  }

  @Nested
  @DisplayName("herança e constantes")
  class HerancaEConstantes {

    @Test
    @DisplayName("CT007 - Deve herdar de BaseEntity")
    void deveHerdarDeBaseEntity() {
      // Arrange & Act
      ContatoMensagem contato = ContatoMensagem.builder().build();

      // Assert
      assertThat(contato).isInstanceOf(com.ia.core.model.BaseEntity.class);
    }

    @Test
    @DisplayName("CT008 - Deve ter nome de tabela correto")
    void deveTerNomeDeTabelaCorreto() {
      // Arrange & Act & Assert
      assertThat(ContatoMensagem.TABLE_NAME).isEqualTo("COM_CONTATO_MENSAGEM");
    }

    @Test
    @DisplayName("CT009 - Deve ter nome de schema correto")
    void deveTerNomeDeSchemaCorreto() {
      // Arrange & Act & Assert
      assertThat(ContatoMensagem.SCHEMA_NAME).isEqualTo("COMMUNICATION");
    }
  }
}
