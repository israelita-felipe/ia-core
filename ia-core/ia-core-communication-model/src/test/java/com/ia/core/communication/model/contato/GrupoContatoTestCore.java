package com.ia.core.communication.model.contato;

import com.ia.test.CoreBaseUnitTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test class for GrupoContato entity.
 * <p>
 * Tests the GrupoContato entity following ADR-012 testing patterns.
 * </p>
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@DisplayName("Testes de GrupoContato")
class GrupoContatoTestCore extends CoreBaseUnitTest {

  @Nested
  @DisplayName("criação de grupo")
  class CriacaoGrupo {

    @Test
    @DisplayName("CT001 - Deve criar grupo com campos obrigatórios")
    void deveCriarGrupoComCamposObrigatorios() {
      // Arrange & Act
      GrupoContato grupo = GrupoContato.builder()
          .nome("Grupo Teste")
          .build();

      // Assert
      assertThat(grupo).isNotNull();
      assertThat(grupo.getNome()).isEqualTo("Grupo Teste");
    }

    @Test
    @DisplayName("CT003 - Deve criar grupo com campo descricao opcional")
    void deveCriarGrupoComCampoDescricaoOpcional() {
      // Arrange & Act
      GrupoContato grupo = GrupoContato.builder()
          .nome("Grupo Teste")
          .descricao("Descrição do grupo")
          .build();

      // Assert
      assertThat(grupo).isNotNull();
      assertThat(grupo.getDescricao()).isEqualTo("Descrição do grupo");
    }

    @Test
    @DisplayName("CT004 - Deve permitir definir campo ativo")
    void devePermitirDefinirCampoAtivo() {
      // Arrange & Act
      GrupoContato grupo = GrupoContato.builder()
          .nome("Grupo Teste")
          .ativo(true)
          .build();

      // Assert
      assertThat(grupo.getAtivo()).isTrue();
    }

    @Test
    @DisplayName("CT005 - Deve permitir definir lista de contatos")
    void devePermitirDefinirListaDeContatos() {
      // Arrange & Act
      GrupoContato grupo = GrupoContato.builder()
          .nome("Grupo Teste")
          .contatos(new java.util.ArrayList<>())
          .build();

      // Assert
      assertThat(grupo.getContatos()).isNotNull();
      assertThat(grupo.getContatos()).isEmpty();
    }

    @Test
    @DisplayName("CT006 - Deve criar grupo usando builder")
    void deveCriarGrupoUsandoBuilder() {
      // Arrange & Act
      GrupoContato grupo = GrupoContato.builder()
          .nome("Grupo Teste")
          .descricao("Descrição do grupo")
          .ativo(true)
          .build();

      // Assert
      assertThat(grupo).isNotNull();
      assertThat(grupo.getNome()).isEqualTo("Grupo Teste");
      assertThat(grupo.getDescricao()).isEqualTo("Descrição do grupo");
      assertThat(grupo.getAtivo()).isTrue();
    }


    @Test
    @DisplayName("CT011 - Deve adicionar contato à lista de contatos")
    void deveAdicionarContatoAListaDeContatos() {
      // Arrange
      GrupoContato grupo = GrupoContato.builder()
          .nome("Grupo Teste")
          .contatos(new java.util.ArrayList<>())
          .build();
      ContatoMensagem contato = ContatoMensagem.builder()
          .grupoContato(grupo)
          .telefone("11999999999")
          .build();

      // Act
      grupo.getContatos().add(contato);

      // Assert
      assertThat(grupo.getContatos()).hasSize(1);
      assertThat(grupo.getContatos()).contains(contato);
    }
  }

  @Nested
  @DisplayName("herança e constantes")
  class HerancaEConstantes {

    @Test
    @DisplayName("CT008 - Deve herdar de BaseEntity")
    void deveHerdarDeBaseEntity() {
      // Arrange & Act
      GrupoContato grupo = GrupoContato.builder().build();

      // Assert
      assertThat(grupo).isInstanceOf(com.ia.core.model.BaseEntity.class);
    }

    @Test
    @DisplayName("CT009 - Deve ter nome de tabela correto")
    void deveTerNomeDeTabelaCorreto() {
      // Arrange & Act & Assert
      assertThat(GrupoContato.TABLE_NAME).isEqualTo("COM_GRUPO_CONTATO");
    }

    @Test
    @DisplayName("CT010 - Deve ter nome de schema correto")
    void deveTerNomeDeSchemaCorreto() {
      // Arrange & Act & Assert
      assertThat(GrupoContato.SCHEMA_NAME).isEqualTo("COMMUNICATION");
    }
  }
}
