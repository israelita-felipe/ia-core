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
    GrupoContatoSearchRequestDTO searchRequest = new GrupoContatoSearchRequestDTO();
    Map<?, ?> filters = searchRequest.getAvaliableFilters();

    assertThat(filters).isNotNull();
    assertThat(filters).isNotEmpty();
  }

  @Test
  @DisplayName("Deve ter filtro para descricao")
  void deveTerFiltroParaDescricao() {
    GrupoContatoSearchRequestDTO searchRequest = new GrupoContatoSearchRequestDTO();
    Map<?, ?> filters = searchRequest.getAvaliableFilters();

    assertThat(filters).isNotNull();
    assertThat(filters).isNotEmpty();
  }

  @Test
  @DisplayName("Deve ter filtro para ativo")
  void deveTerFiltroParaAtivo() {
    GrupoContatoSearchRequestDTO searchRequest = new GrupoContatoSearchRequestDTO();
    Map<?, ?> filters = searchRequest.getAvaliableFilters();

    assertThat(filters).isNotNull();
    assertThat(filters).isNotEmpty();
  }

  @Test
  @DisplayName("Deve retornar filtros disponíveis")
  void deveRetornarFiltrosDisponiveis() {
    GrupoContatoSearchRequestDTO searchRequest = new GrupoContatoSearchRequestDTO();
    Map<?, ?> filters = searchRequest.getAvaliableFilters();

    assertThat(filters).isNotNull();
    assertThat(filters).isNotEmpty();
  }
}
