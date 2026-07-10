package com.ia.core.security.service.model.privilege;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("PrivilegeSearchRequestDTO")
class PrivilegeSearchRequestDTOTest {

   @Test
   @DisplayName("Deve expor filtros de nome e tipo")
   void deveExporFiltrosDeNomeETipo() {
     PrivilegeSearchRequestDTO request = new PrivilegeSearchRequestDTO();

     assertThat(request.getAvaliableFilters()).hasSize(2);
     // propertyFilters() só retorna campos de tipo STRING (não ENUM)
     assertThat(request.propertyFilters()).containsExactly("name");
   }
}
