package com.ia.core.security.service.model.user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("UserSearchRequest")
class UserSearchRequestTest {

  @Test
  @DisplayName("Deve expor filtros de nome e código")
  void deveExporFiltrosDeNomeECodigo() {
    UserSearchRequest request = new UserSearchRequest();

    assertThat(request.getAvaliableFilters()).hasSize(2);
    assertThat(request.propertyFilters()).containsExactlyInAnyOrder("userName", "userCode");
  }
}
