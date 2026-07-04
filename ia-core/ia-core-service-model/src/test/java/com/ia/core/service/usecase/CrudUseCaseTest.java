package com.ia.core.service.usecase;

import com.ia.core.service.dto.request.SearchRequestDTO;
import com.ia.core.service.exception.ServiceException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Testes para CrudUseCase.
 */
@DisplayName("CrudUseCase Tests")
class CrudUseCaseTest {

  @Test
  @DisplayName("interface deve ser instanciável via implementação")
  void testInterfaceCanBeImplemented() {
    CrudUseCase<String> useCase = new TestCrudUseCase();
    assertThat(useCase).isNotNull();
  }

  @Test
  @DisplayName("save deve retornar DTO salvo")
  void testSaveReturnsDto() throws ServiceException {
    // Arrange
    CrudUseCase<String> useCase = new TestCrudUseCase();
    String toSave = "com/ia/test";

    // Act
    String saved = useCase.save(toSave);

    // Assert
    assertThat(saved).isEqualTo(toSave);
  }

  @Test
  @DisplayName("delete deve executar sem exceção")
  void testDeleteExecutes() throws ServiceException {
    // Arrange
    CrudUseCase<String> useCase = new TestCrudUseCase();

    // Act & Assert
    useCase.delete(1L);
  }

  static class TestCrudUseCase implements CrudUseCase<String> {
    @Override
    public String save(String toSave) throws ServiceException {
      return toSave;
    }

    @Override
    public void delete(Long id) throws ServiceException {
      // No-op
    }

    @Override
    public int count(SearchRequestDTO requestDTO) {
      return 0;
    }

    @Override
    public String find(Long id) {
      return null;
    }

    @Override
    public org.springframework.data.domain.Page<String> findAll(SearchRequestDTO requestDTO) {
      return org.springframework.data.domain.Page.empty();
    }
  }
}
