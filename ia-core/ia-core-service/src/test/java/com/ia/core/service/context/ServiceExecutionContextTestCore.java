package com.ia.core.service.context;

import com.ia.core.model.BaseEntity;
import com.ia.core.service.dto.DTO;
import com.ia.test.CoreBaseUnitTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Testes para ServiceExecutionContext baseados nos casos de teste documentados.
 */
class ServiceExecutionContextTestCore extends CoreBaseUnitTest {

    @Test
    void deveCriarContextoComDTO() {
        // Arrange
        TestDTO dto = new TestDTO();

        // Act
        ServiceExecutionContext<TestEntity, TestDTO> context = new ServiceExecutionContext<>(dto);

        // Assert
        assertThat(context.getToSave()).isEqualTo(dto);
        assertThat(context.getModel()).isNull();
        assertThat(context.getSavedEntity()).isNull();
        assertThat(context.getResult()).isNull();
        assertThat(context.isCancelled()).isFalse();
    }

    @Test
    void deveDefinirEObterModel() {
        // Arrange
        TestDTO dto = new TestDTO();
        ServiceExecutionContext<TestEntity, TestDTO> context = new ServiceExecutionContext<>(dto);
        TestEntity model = new TestEntity();

        // Act
        ServiceExecutionContext<TestEntity, TestDTO> result = context.setModel(model);

        // Assert
        assertThat(context.getModel()).isEqualTo(model);
        assertThat(result).isSameAs(context);
    }

    @Test
    void deveDefinirEObterSavedEntity() {
        // Arrange
        TestDTO dto = new TestDTO();
        ServiceExecutionContext<TestEntity, TestDTO> context = new ServiceExecutionContext<>(dto);
        TestEntity savedEntity = new TestEntity();

        // Act
        ServiceExecutionContext<TestEntity, TestDTO> result = context.setSavedEntity(savedEntity);

        // Assert
        assertThat(context.getSavedEntity()).isEqualTo(savedEntity);
        assertThat(result).isSameAs(context);
    }

    @Test
    void deveDefinirEObterResult() {
        // Arrange
        TestDTO dto = new TestDTO();
        ServiceExecutionContext<TestEntity, TestDTO> context = new ServiceExecutionContext<>(dto);
        TestDTO result = new TestDTO();

        // Act
        ServiceExecutionContext<TestEntity, TestDTO> returned = context.setResult(result);

        // Assert
        assertThat(context.getResult()).isEqualTo(result);
        assertThat(returned).isSameAs(context);
    }

    @Test
    void deveCancelarOperacaoComMotivo() {
        // Arrange
        TestDTO dto = new TestDTO();
        ServiceExecutionContext<TestEntity, TestDTO> context = new ServiceExecutionContext<>(dto);
        String motivo = "Validação falhou";

        // Act
        context.cancel(motivo);

        // Assert
        assertThat(context.isCancelled()).isTrue();
        assertThat(context.getCancelReason()).isEqualTo(motivo);
    }

    @Test
    void deveCancelarOperacaoSemMotivo() {
        // Arrange
        TestDTO dto = new TestDTO();
        ServiceExecutionContext<TestEntity, TestDTO> context = new ServiceExecutionContext<>(dto);

        // Act
        context.cancel(null);

        // Assert
        assertThat(context.isCancelled()).isTrue();
        assertThat(context.getCancelReason()).isNull();
    }

    @Test
    void deveVerificarSeEAtualizacaoComID() {
        // Arrange
        TestDTO dto = new TestDTO();
        ServiceExecutionContext<TestEntity, TestDTO> context = new ServiceExecutionContext<>(dto);
        TestEntity model = new TestEntity();
        model.setId(1L);
        context.setModel(model);

        // Act
        boolean isUpdate = context.isUpdate();

        // Assert
        assertThat(isUpdate).isTrue();
    }

    @Test
    void deveVerificarSeEAtualizacaoSemID() {
        // Arrange
        TestDTO dto = new TestDTO();
        ServiceExecutionContext<TestEntity, TestDTO> context = new ServiceExecutionContext<>(dto);
        TestEntity model = new TestEntity();
        context.setModel(model);

        // Act
        boolean isUpdate = context.isUpdate();

        // Assert
        assertThat(isUpdate).isFalse();
    }

    @Test
    void deveVerificarSeEAtualizacaoComModelNull() {
        // Arrange
        TestDTO dto = new TestDTO();
        ServiceExecutionContext<TestEntity, TestDTO> context = new ServiceExecutionContext<>(dto);

        // Act
        boolean isUpdate = context.isUpdate();

        // Assert
        assertThat(isUpdate).isFalse();
    }

    static class TestEntity extends BaseEntity {
    }

    static class TestDTO implements DTO<TestEntity> {
        @Override
        public TestDTO cloneObject() {
            return new TestDTO();
        }
    }
}
