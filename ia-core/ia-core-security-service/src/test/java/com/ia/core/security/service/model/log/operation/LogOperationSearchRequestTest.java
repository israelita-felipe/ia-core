package com.ia.core.security.service.model.log.operation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("LogOperationSearchRequest")
class LogOperationSearchRequestTest {

  @Test
  @DisplayName("Deve expor filtros de tipo, id e usuário")
  void deveExporFiltrosDeTipoIdEUsuario() {
    LogOperationSearchRequest request = new LogOperationSearchRequest();

    assertThat(request.getAvaliableFilters()).hasSize(3);
    assertThat(request.getAvaliableFilters().keySet())
        .extracting(filterProperty -> filterProperty.getProperty())
        .containsExactlyInAnyOrder("type", "valueId", "userCode");
    assertThat(request.propertyFilters()).containsExactlyInAnyOrder("type", "userCode");
  }
}
