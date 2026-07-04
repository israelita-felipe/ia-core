package com.ia.core.security.service.model.role;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("RoleSearchRequest")
class RoleSearchRequestTest {

  @Test
  @DisplayName("Deve expor filtro de nome")
  void deveExporFiltroDeNome() {
    RoleSearchRequest request = new RoleSearchRequest();

    assertThat(request.getAvaliableFilters()).hasSize(1);
    assertThat(request.propertyFilters()).containsExactly("name");
  }
}
