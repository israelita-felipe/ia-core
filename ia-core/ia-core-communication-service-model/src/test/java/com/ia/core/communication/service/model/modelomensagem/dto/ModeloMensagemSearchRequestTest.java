package com.ia.core.communication.service.model.modelomensagem.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Testes para a classe ModeloMensagemSearchRequest.
 */
@DisplayName("Testes de ModeloMensagemSearchRequest")
class ModeloMensagemSearchRequestTest {

  @Test
  @DisplayName("Deve ter filtro para nome")
  void deveTerFiltroParaNome() {
    ModeloMensagemSearchRequest searchRequest = new ModeloMensagemSearchRequest();
    Map<?, ?> filters = searchRequest.getAvaliableFilters();

    assertThat(filters).isNotNull();
    assertThat(filters).isNotEmpty();
  }

  @Test
  @DisplayName("Deve ter filtro para descricao")
  void deveTerFiltroParaDescricao() {
    ModeloMensagemSearchRequest searchRequest = new ModeloMensagemSearchRequest();
    Map<?, ?> filters = searchRequest.getAvaliableFilters();

    assertThat(filters).isNotNull();
    assertThat(filters).isNotEmpty();
  }

  @Test
  @DisplayName("Deve ter filtro para tipoCanal")
  void deveTerFiltroParaTipoCanal() {
    ModeloMensagemSearchRequest searchRequest = new ModeloMensagemSearchRequest();
    Map<?, ?> filters = searchRequest.getAvaliableFilters();

    assertThat(filters).isNotNull();
    assertThat(filters).isNotEmpty();
  }

  @Test
  @DisplayName("Deve ter filtro para ativo")
  void deveTerFiltroParaAtivo() {
    ModeloMensagemSearchRequest searchRequest = new ModeloMensagemSearchRequest();
    Map<?, ?> filters = searchRequest.getAvaliableFilters();

    assertThat(filters).isNotNull();
    assertThat(filters).isNotEmpty();
  }

  @Test
  @DisplayName("Deve retornar filtros disponíveis")
  void deveRetornarFiltrosDisponiveis() {
    ModeloMensagemSearchRequest searchRequest = new ModeloMensagemSearchRequest();
    Map<?, ?> filters = searchRequest.getAvaliableFilters();

    assertThat(filters).isNotNull();
    assertThat(filters).isNotEmpty();
  }
}
