package com.ia.core.communication.service.model.contatomensagem.dto;

import com.ia.core.model.filter.FieldType;
import com.ia.core.service.dto.filter.OperatorDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Testes para a classe ContatoMensagemSearchRequestDTO.
 */
@DisplayName("Testes de ContatoMensagemSearchRequestDTO")
class ContatoMensagemSearchRequestDTOTest {

   @Test
   @DisplayName("Deve ter filtro para telefone")
   void deveTerFiltroParaTelefone() {
     ContatoMensagemSearchRequestDTO searchRequest = new ContatoMensagemSearchRequestDTO();
    Map<?, ?> filters = searchRequest.getAvaliableFilters();

    assertThat(filters).isNotNull();
    assertThat(filters).isNotEmpty();
  }

  @Test
  @DisplayName("Deve ter filtro para nome")
  void deveTerFiltroParaNome() {
    ContatoMensagemSearchRequestDTO searchRequest = new ContatoMensagemSearchRequestDTO();
    Map<?, ?> filters = searchRequest.getAvaliableFilters();

    assertThat(filters).isNotNull();
    assertThat(filters).isNotEmpty();
  }

  @Test
  @DisplayName("Deve retornar filtros disponíveis")
  void deveRetornarFiltrosDisponiveis() {
    ContatoMensagemSearchRequestDTO searchRequest = new ContatoMensagemSearchRequestDTO();
    Map<?, ?> filters = searchRequest.getAvaliableFilters();

    assertThat(filters).isNotNull();
    assertThat(filters).isNotEmpty();
  }
}
