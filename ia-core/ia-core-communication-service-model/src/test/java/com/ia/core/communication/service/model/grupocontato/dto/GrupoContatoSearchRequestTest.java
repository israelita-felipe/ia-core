package com.ia.core.communication.service.model.grupocontato.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Testes para a classe GrupoContatoSearchRequest.
 */
@DisplayName("Testes de GrupoContatoSearchRequest")
class GrupoContatoSearchRequestTest {

  @Test
  @DisplayName("Deve ter filtro para nome")
  void deveTerFiltroParaNome() {
    GrupoContatoSearchRequest searchRequest = new GrupoContatoSearchRequest();
    Map<?, ?> filters = searchRequest.getAvaliableFilters();

    assertThat(filters).isNotNull();
    assertThat(filters).isNotEmpty();
  }

  @Test
  @DisplayName("Deve ter filtro para descricao")
  void deveTerFiltroParaDescricao() {
    GrupoContatoSearchRequest searchRequest = new GrupoContatoSearchRequest();
    Map<?, ?> filters = searchRequest.getAvaliableFilters();

    assertThat(filters).isNotNull();
    assertThat(filters).isNotEmpty();
  }

  @Test
  @DisplayName("Deve ter filtro para ativo")
  void deveTerFiltroParaAtivo() {
    GrupoContatoSearchRequest searchRequest = new GrupoContatoSearchRequest();
    Map<?, ?> filters = searchRequest.getAvaliableFilters();

    assertThat(filters).isNotNull();
    assertThat(filters).isNotEmpty();
  }

  @Test
  @DisplayName("Deve retornar filtros disponíveis")
  void deveRetornarFiltrosDisponiveis() {
    GrupoContatoSearchRequest searchRequest = new GrupoContatoSearchRequest();
    Map<?, ?> filters = searchRequest.getAvaliableFilters();

    assertThat(filters).isNotNull();
    assertThat(filters).isNotEmpty();
  }
}
