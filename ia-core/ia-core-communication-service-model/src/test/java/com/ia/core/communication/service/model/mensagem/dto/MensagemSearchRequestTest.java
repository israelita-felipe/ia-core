package com.ia.core.communication.service.model.mensagem.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Testes para a classe MensagemSearchRequest.
 */
@DisplayName("Testes de MensagemSearchRequest")
class MensagemSearchRequestTest {

  @Test
  @DisplayName("Deve ter filtro para telefoneDestinatario")
  void deveTerFiltroParaTelefoneDestinatario() {
    MensagemSearchRequest searchRequest = new MensagemSearchRequest();
    Map<?, ?> filters = searchRequest.getAvaliableFilters();

    assertThat(filters).isNotNull();
    assertThat(filters).isNotEmpty();
  }

  @Test
  @DisplayName("Deve ter filtro para nomeDestinatario")
  void deveTerFiltroParaNomeDestinatario() {
    MensagemSearchRequest searchRequest = new MensagemSearchRequest();
    Map<?, ?> filters = searchRequest.getAvaliableFilters();

    assertThat(filters).isNotNull();
    assertThat(filters).isNotEmpty();
  }

  @Test
  @DisplayName("Deve ter filtro para tipoCanal")
  void deveTerFiltroParaTipoCanal() {
    MensagemSearchRequest searchRequest = new MensagemSearchRequest();
    Map<?, ?> filters = searchRequest.getAvaliableFilters();

    assertThat(filters).isNotNull();
    assertThat(filters).isNotEmpty();
  }

  @Test
  @DisplayName("Deve ter filtro para statusMensagem")
  void deveTerFiltroParaStatusMensagem() {
    MensagemSearchRequest searchRequest = new MensagemSearchRequest();
    Map<?, ?> filters = searchRequest.getAvaliableFilters();

    assertThat(filters).isNotNull();
    assertThat(filters).isNotEmpty();
  }

  @Test
  @DisplayName("Deve retornar filtros disponíveis")
  void deveRetornarFiltrosDisponiveis() {
    MensagemSearchRequest searchRequest = new MensagemSearchRequest();
    Map<?, ?> filters = searchRequest.getAvaliableFilters();

    assertThat(filters).isNotNull();
    assertThat(filters).isNotEmpty();
  }
}
