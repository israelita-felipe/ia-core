package com.ia.core.security.service.model.privilege;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("PrivilegeSearchRequest")
class PrivilegeSearchRequestTest {

  @Test
  @DisplayName("Deve expor filtro de nome")
  void deveExporFiltroDeNome() {
    PrivilegeSearchRequest request = new PrivilegeSearchRequest();

    assertThat(request.getAvaliableFilters()).hasSize(1);
    assertThat(request.propertyFilters()).containsExactly("name");
  }
}
