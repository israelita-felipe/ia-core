package com.ia.core.service.strategy;

import com.ia.core.model.BaseEntity;
import com.ia.core.service.dto.entity.AbstractBaseEntityDTO;
import com.ia.core.service.event.CrudOperationType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Testes para OperationTypeStrategy.
 */
@DisplayName("OperationTypeStrategy Tests")
class OperationTypeStrategyTest {

  @Test
  @DisplayName("defaultStrategy deve retornar CREATED quando saved for null")
  void testDefaultStrategyReturnsCreatedWhenSavedIsNull() {
    OperationTypeStrategy<TestDTO> strategy = OperationTypeStrategy.defaultStrategy();
    TestDTO original = new TestDTO();

    CrudOperationType result = strategy.determine(original, null);

    assertThat(result).isEqualTo(CrudOperationType.CREATED);
  }

  @Test
  @DisplayName("defaultStrategy deve retornar CREATED quando saved.getId() for null")
  void testDefaultStrategyReturnsCreatedWhenSavedIdIsNull() {
    OperationTypeStrategy<TestDTO> strategy = OperationTypeStrategy.defaultStrategy();
    TestDTO original = new TestDTO();
    TestDTO saved = new TestDTO();
    saved.setId(null);

    CrudOperationType result = strategy.determine(original, saved);

    assertThat(result).isEqualTo(CrudOperationType.CREATED);
  }

  @Test
  @DisplayName("defaultStrategy deve retornar UPDATED quando saved.getId() não for null")
  void testDefaultStrategyReturnsUpdatedWhenSavedIdIsNotNull() {
    OperationTypeStrategy<TestDTO> strategy = OperationTypeStrategy.defaultStrategy();
    TestDTO original = new TestDTO();
    TestDTO saved = new TestDTO();
    saved.setId(1L);

    CrudOperationType result = strategy.determine(original, saved);

    assertThat(result).isEqualTo(CrudOperationType.UPDATED);
  }

  static class TestDTO extends AbstractBaseEntityDTO<TestEntity> {
    @Override
    public TestDTO cloneObject() {
      return new TestDTO();
    }
  }

  static class TestEntity extends BaseEntity {
  }
}
