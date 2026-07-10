package com.ia.core.security.service.model.user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("UserSearchRequestDTO")
class UserSearchRequestDTOTest {

  @Test
  @DisplayName("Deve expor filtros de nome e código")
  void deveExporFiltrosDeNomeECodigo() {
    UserSearchRequestDTO request = new UserSearchRequestDTO();

    assertThat(request.getAvaliableFilters()).hasSize(2);
    assertThat(request.propertyFilters()).containsExactlyInAnyOrder("userName", "userCode");
  }
}
