package com.ia.core.communication.service.model.modelomensagem.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Testes para o enum VariavelTemplate.
 */
@DisplayName("Testes de VariavelTemplate")
class VariavelTemplateTest {

  @Test
  @DisplayName("Deve ter constante CRIADO_EM")
  void deveTerConstanteCRIADO_EM() {
    assertThat(VariavelTemplate.CRIADO_EM).isNotNull();
    assertThat(VariavelTemplate.CRIADO_EM.getChave()).isEqualTo("{{criadoEm}}");
    assertThat(VariavelTemplate.CRIADO_EM.getDescricao()).isEqualTo("Data de criação");
    assertThat(VariavelTemplate.CRIADO_EM.getTipo()).isEqualTo("data");
    assertThat(VariavelTemplate.CRIADO_EM.getCategoria()).isEqualTo("criacao");
    assertThat(VariavelTemplate.CRIADO_EM.isObrigatoria()).isTrue();
  }

  @Test
  @DisplayName("Deve ter constante ATUALIZADO_EM")
  void deveTerConstanteATUALIZADO_EM() {
    assertThat(VariavelTemplate.ATUALIZADO_EM).isNotNull();
    assertThat(VariavelTemplate.ATUALIZADO_EM.getChave()).isEqualTo("{{atualizadoEm}}");
    assertThat(VariavelTemplate.ATUALIZADO_EM.getDescricao()).isEqualTo("Data de atualização");
    assertThat(VariavelTemplate.ATUALIZADO_EM.getTipo()).isEqualTo("data");
    assertThat(VariavelTemplate.ATUALIZADO_EM.getCategoria()).isEqualTo("criacao");
    assertThat(VariavelTemplate.ATUALIZADO_EM.isObrigatoria()).isTrue();
  }

  @Test
  @DisplayName("Deve ter constantes de contato")
  void deveTerConstantesDeContato() {
    assertThat(VariavelTemplate.NOME).isNotNull();
    assertThat(VariavelTemplate.TELEFONE).isNotNull();
    assertThat(VariavelTemplate.EMAIL).isNotNull();
    assertThat(VariavelTemplate.NOME.getCategoria()).isEqualTo("contato");
  }

  @Test
  @DisplayName("Deve ter constantes de mensagem")
  void deveTerConstantesDeMensagem() {
    assertThat(VariavelTemplate.CORPO_MENSAGEM).isNotNull();
    assertThat(VariavelTemplate.DATA_ENVIO).isNotNull();
    assertThat(VariavelTemplate.STATUS).isNotNull();
    assertThat(VariavelTemplate.CORPO_MENSAGEM.getCategoria()).isEqualTo("mensagem");
  }

  @Test
  @DisplayName("Deve ter constantes de modelo")
  void deveTerConstantesDeModelo() {
    assertThat(VariavelTemplate.NOME_MODELO).isNotNull();
    assertThat(VariavelTemplate.DESCRICAO_MODELO).isNotNull();
    assertThat(VariavelTemplate.CORPO_MODELO).isNotNull();
    assertThat(VariavelTemplate.TIPO_CANAL).isNotNull();
    assertThat(VariavelTemplate.ATIVO_MODELO).isNotNull();
    assertThat(VariavelTemplate.NOME_MODELO.getCategoria()).isEqualTo("modelo");
  }

  @Test
  @DisplayName("Deve ter constantes de grupo")
  void deveTerConstantesDeGrupo() {
    assertThat(VariavelTemplate.NOME_GRUPO).isNotNull();
    assertThat(VariavelTemplate.DESCRICAO_GRUPO).isNotNull();
    assertThat(VariavelTemplate.ATIVO_GRUPO).isNotNull();
    assertThat(VariavelTemplate.NOME_GRUPO.getCategoria()).isEqualTo("grupo");
  }

  @Test
  @DisplayName("Deve ter constantes de evento")
  void deveTerConstantesDeEvento() {
    assertThat(VariavelTemplate.NOME_EVENTO).isNotNull();
    assertThat(VariavelTemplate.DATA_EVENTO).isNotNull();
    assertThat(VariavelTemplate.HORA_EVENTO).isNotNull();
    assertThat(VariavelTemplate.NOME_EVENTO.getCategoria()).isEqualTo("evento");
  }

  @Test
  @DisplayName("Deve ter constantes de sistema")
  void deveTerConstantesDeSistema() {
    assertThat(VariavelTemplate.DATA_ATUAL).isNotNull();
    assertThat(VariavelTemplate.HORA_ATUAL).isNotNull();
    assertThat(VariavelTemplate.DATA_ATUAL.getCategoria()).isEqualTo("sistema");
  }

  @Test
  @DisplayName("Deve ter constantes de igreja")
  void deveTerConstantesDeIgreja() {
    assertThat(VariavelTemplate.NOME_IGREJA).isNotNull();
    assertThat(VariavelTemplate.ENDERECO_IGREJA).isNotNull();
    assertThat(VariavelTemplate.TELEFONE_IGREJA).isNotNull();
    assertThat(VariavelTemplate.EMAIL_IGREJA).isNotNull();
    assertThat(VariavelTemplate.NOME_IGREJA.getCategoria()).isEqualTo("igreja");
  }

  @Test
  @DisplayName("Deve buscar por chave")
  void deveBuscarPorChave() {
    assertThat(VariavelTemplate.buscarPorChave("nome")).isPresent();
    assertThat(VariavelTemplate.buscarPorChave("nome").get()).isEqualTo(VariavelTemplate.NOME);
  }

  @Test
  @DisplayName("Deve retornar Optional vazio quando chave não encontrada")
  void deveRetornarOptionalVazioQuandoChaveNaoEncontrada() {
    assertThat(VariavelTemplate.buscarPorChave("chave_inexistente")).isNotPresent();
  }

  @Test
  @DisplayName("Deve listar todas as chaves")
  void deveListarTodasAsChaves() {
    assertThat(VariavelTemplate.listarChaves()).isNotEmpty();
    assertThat(VariavelTemplate.listarChaves()).contains("{{nome}}");
    assertThat(VariavelTemplate.listarChaves()).contains("{{telefone}}");
  }
}
