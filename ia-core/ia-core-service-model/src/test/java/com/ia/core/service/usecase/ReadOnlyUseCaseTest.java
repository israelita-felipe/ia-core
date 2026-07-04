package com.ia.core.service.usecase;

import com.ia.core.service.dto.request.SearchRequestDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Testes para ReadOnlyUseCase.
 */
@DisplayName("ReadOnlyUseCase Tests")
class ReadOnlyUseCaseTest {

  @Test
  @DisplayName("interface deve ser instanciável via implementação")
  void testInterfaceCanBeImplemented() {
    ReadOnlyUseCase<String> useCase = new TestReadOnlyUseCase();
    assertThat(useCase).isNotNull();
  }

  @Test
  @DisplayName("count deve retornar valor inteiro")
  void testCountReturnsInteger() {
    // Arrange
    ReadOnlyUseCase<String> useCase = new TestReadOnlyUseCase();
    SearchRequestDTO request = SearchRequestDTO.builder().build();

    // Act
    int count = useCase.count(request);

    // Assert
    assertThat(count).isGreaterThanOrEqualTo(0);
  }

  @Test
  @DisplayName("find deve retornar DTO ou null")
  void testFindReturnsDtoOrNull() {
    // Arrange
    ReadOnlyUseCase<String> useCase = new TestReadOnlyUseCase();

    // Act
    String result = useCase.find(1L);

    // Assert
    assertThat(result).isNull();
  }

  @Test
  @DisplayName("findAll deve retornar Page")
  void testFindAllReturnsPage() {
    // Arrange
    ReadOnlyUseCase<String> useCase = new TestReadOnlyUseCase();
    SearchRequestDTO request = SearchRequestDTO.builder().build();

    // Act
    org.springframework.data.domain.Page<String> page = useCase.findAll(request);

    // Assert
    assertThat(page).isNotNull();
    assertThat(page.getContent()).isEmpty();
  }

  static class TestReadOnlyUseCase implements ReadOnlyUseCase<String> {
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
