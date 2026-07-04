package com.ia.core.service.specification;

import com.ia.core.service.dto.filter.FilterRequestDTO;
import com.ia.core.service.dto.filter.OperatorDTO;
import com.ia.core.service.dto.request.SearchRequestDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Testes para CollectionSearchSpecification.
 */
@DisplayName("CollectionSearchSpecification Tests")
class CollectionSearchSpecificationTest {

  @Test
  @DisplayName("toPredicate deve retornar predicado verdadeiro quando não há filtros")
  void testToPredicateReturnsTrueWhenNoFilters() {
    SearchRequestDTO request = new SearchRequestDTO();
    request.setFilters(List.of());

    CollectionSearchSpecification<String> spec = new CollectionSearchSpecification<>(request);

    assertThat(spec.toPredicate().test("any")).isTrue();
  }

  @Test
  @DisplayName("toPredicate deve aplicar filtros quando presentes")
  void testToPredicateAppliesFiltersWhenPresent() {
    SearchRequestDTO request = new SearchRequestDTO();
    FilterRequestDTO filter = new FilterRequestDTO();
    filter.setOperator(OperatorDTO.EQUAL);
    filter.setKey("value");
    filter.setValue("com/ia/test");
    request.setFilters(List.of(filter));

    CollectionSearchSpecification<TestObject> spec = new CollectionSearchSpecification<>(request);

    assertThat(spec.toPredicate().test(new TestObject("com/ia/test"))).isTrue();
  }

  static class TestObject {
    private final String value;

    TestObject(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }
  }

  @Test
  @DisplayName("toPredicate deve ignorar filtros com operador nulo")
  void testToPredicateIgnoresFiltersWithNullOperator() {
    SearchRequestDTO request = new SearchRequestDTO();
    FilterRequestDTO filter = new FilterRequestDTO();
    filter.setOperator(null);
    filter.setValue("com/ia/test");
    request.setFilters(List.of(filter));

    CollectionSearchSpecification<String> spec = new CollectionSearchSpecification<>(request);

    assertThat(spec.toPredicate().test("any")).isTrue();
  }
}
