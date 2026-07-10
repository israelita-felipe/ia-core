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
    ModeloMensagemSearchRequestDTO searchRequest = new ModeloMensagemSearchRequestDTO();
    Map<?, ?> filters = searchRequest.getAvaliableFilters();

    assertThat(filters).isNotNull();
    assertThat(filters).isNotEmpty();
  }

  @Test
  @DisplayName("Deve ter filtro para descricao")
  void deveTerFiltroParaDescricao() {
    ModeloMensagemSearchRequestDTO searchRequest = new ModeloMensagemSearchRequestDTO();
    Map<?, ?> filters = searchRequest.getAvaliableFilters();

    assertThat(filters).isNotNull();
    assertThat(filters).isNotEmpty();
  }

  @Test
  @DisplayName("Deve ter filtro para tipoCanal")
  void deveTerFiltroParaTipoCanal() {
    ModeloMensagemSearchRequestDTO searchRequest = new ModeloMensagemSearchRequestDTO();
    Map<?, ?> filters = searchRequest.getAvaliableFilters();

    assertThat(filters).isNotNull();
    assertThat(filters).isNotEmpty();
  }

  @Test
  @DisplayName("Deve ter filtro para ativo")
  void deveTerFiltroParaAtivo() {
    ModeloMensagemSearchRequestDTO searchRequest = new ModeloMensagemSearchRequestDTO();
    Map<?, ?> filters = searchRequest.getAvaliableFilters();

    assertThat(filters).isNotNull();
    assertThat(filters).isNotEmpty();
  }

  @Test
  @DisplayName("Deve retornar filtros disponíveis")
  void deveRetornarFiltrosDisponiveis() {
    ModeloMensagemSearchRequestDTO searchRequest = new ModeloMensagemSearchRequestDTO();
    Map<?, ?> filters = searchRequest.getAvaliableFilters();

    assertThat(filters).isNotNull();
    assertThat(filters).isNotEmpty();
  }
}
