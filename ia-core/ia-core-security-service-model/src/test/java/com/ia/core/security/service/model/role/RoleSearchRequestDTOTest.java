package com.ia.core.security.service.model.role;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("RoleSearchRequestDTO")
class RoleSearchRequestDTOTest {

   @Test
   @DisplayName("Deve expor filtro de nome")
   void deveExporFiltroDeNome() {
     RoleSearchRequestDTO request = new RoleSearchRequestDTO();

     assertThat(request.getAvaliableFilters()).hasSize(1);
     assertThat(request.propertyFilters()).containsExactly("name");
   }
}
