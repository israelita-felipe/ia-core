package com.ia.core.communication.service.model.contatomensagem.dto;

import com.ia.core.model.filter.FieldType;
import com.ia.core.service.dto.filter.OperatorDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Testes para a classe ContatoMensagemSearchRequest.
 */
@DisplayName("Testes de ContatoMensagemSearchRequest")
class ContatoMensagemSearchRequestTest {

  @Test
  @DisplayName("Deve ter filtro para telefone")
  void deveTerFiltroParaTelefone() {
    ContatoMensagemSearchRequest searchRequest = new ContatoMensagemSearchRequest();
    Map<?, ?> filters = searchRequest.getAvaliableFilters();

    assertThat(filters).isNotNull();
    assertThat(filters).isNotEmpty();
  }

  @Test
  @DisplayName("Deve ter filtro para nome")
  void deveTerFiltroParaNome() {
    ContatoMensagemSearchRequest searchRequest = new ContatoMensagemSearchRequest();
    Map<?, ?> filters = searchRequest.getAvaliableFilters();

    assertThat(filters).isNotNull();
    assertThat(filters).isNotEmpty();
  }

  @Test
  @DisplayName("Deve retornar filtros disponíveis")
  void deveRetornarFiltrosDisponiveis() {
    ContatoMensagemSearchRequest searchRequest = new ContatoMensagemSearchRequest();
    Map<?, ?> filters = searchRequest.getAvaliableFilters();

    assertThat(filters).isNotNull();
    assertThat(filters).isNotEmpty();
  }
}
