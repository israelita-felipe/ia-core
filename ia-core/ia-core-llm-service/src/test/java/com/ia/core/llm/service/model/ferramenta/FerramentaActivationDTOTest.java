package com.ia.core.llm.service.model.ferramenta;

import com.ia.core.llm.test.support.DtoTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("unit")
@DisplayName("FerramentaActivationDTO Tests")
class FerramentaActivationDTOTest {
  private static final List<String> FIELDS = List.of("id", "titulo", "descricao", "instrucoes", "template", "subFerramentas");

  @Test
  @DisplayName("Should create DTO with declared fields")
  void shouldCreateDtoWithDeclaredFields() throws Exception {
    Object dto = DtoTestSupport.createDto(FerramentaActivationDTO.class, FIELDS);

    assertThat(dto).isNotNull();
    for (String field : FIELDS) {
      assertThat(DtoTestSupport.getValue(dto, field)).as(field).isEqualTo(DtoTestSupport.valueForField(FerramentaActivationDTO.class.getDeclaredField(field).getType()));
    }
  }

  @Test
  @DisplayName("Should expose cloneObject contract when supported")
  void shouldExposeCloneObjectContractWhenSupported() throws Exception {
    Object dto = DtoTestSupport.createDto(FerramentaActivationDTO.class, FIELDS);

    DtoTestSupport.assertCloneObject(FerramentaActivationDTO.class, dto, FIELDS);
  }

  @Test
  @DisplayName("Should expose copyObject contract when supported")
  void shouldExposeCopyObjectContractWhenSupported() throws Exception {
    Object dto = DtoTestSupport.createDto(FerramentaActivationDTO.class, FIELDS);

    DtoTestSupport.assertCopyObject(FerramentaActivationDTO.class, dto, FIELDS);
  }

  @Test
  @DisplayName("Should expose CAMPOS constants when present")
  void shouldExposeCamposConstantsWhenPresent() throws Exception {
    DtoTestSupport.assertCampos(FerramentaActivationDTO.class, FIELDS);
  }

  @Test
  @DisplayName("Should expose search request factory when supported")
  void shouldExposeSearchRequestFactoryWhenSupported() throws Exception {
    DtoTestSupport.assertSearchRequest(FerramentaActivationDTO.class);
  }
}
