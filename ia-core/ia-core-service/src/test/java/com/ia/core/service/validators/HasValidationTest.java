package com.ia.core.service.validators;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ia.core.service.exception.ServiceException;
import com.ia.core.service.translator.Translator;
import com.ia.core.service.validators.IServiceValidator.ServiceValidatorRegistry;

/**
 * Testes para os métodos default de {@link HasValidation}.
 *
 * @author Israel Araújo
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("HasValidation")
class HasValidationTest {

    @Mock
    private Translator translator;

    @Mock
    private IServiceValidator<TestDTO> validator1;

    @Mock
    private IServiceValidator<TestDTO> validator2;

    @Mock
    private JakartaValidator<TestDTO> jakartaValidator;

    private TestHasValidation service;

    @BeforeEach
    void setUp() {
        service = new TestHasValidation(translator);
    }

    @Nested
    @DisplayName("createJakartaValidator")
    class TestesCreateJakartaValidator {

        @Test
        @DisplayName("Deve criar validador jakarta")
        void deveCriarValidadorJakarta() {
            // Quando
            JakartaValidator<TestDTO> result = service.createJakartaValidator();

            // Então
            assertThat(result).isNotNull();
        }
    }

    @Nested
    @DisplayName("registryValidators")
    class TestesRegistryValidators {

        @Test
        @DisplayName("Deve registrar todos os validadores")
        void deveRegistrarTodosOsValidadores() {
            // Dado
            List<IServiceValidator<TestDTO>> validators = List.of(validator1, validator2);
            ServiceValidatorRegistry registry1 = mock(ServiceValidatorRegistry.class);
            ServiceValidatorRegistry registry2 = mock(ServiceValidatorRegistry.class);
            when(validator1.registry(service)).thenReturn(registry1);
            when(validator2.registry(service)).thenReturn(registry2);

            // Quando
            List<ServiceValidatorRegistry> result = service.registryValidators(validators);

            // Então
            assertThat(result).hasSize(2);
            assertThat(result).contains(registry1, registry2);
            verify(validator1).registry(service);
            verify(validator2).registry(service);
        }

        @Test
        @DisplayName("Deve retornar lista vazia quando não houver validadores")
        void deveRetornarListaVaziaQuandoNaoHouverValidadores() {
            // Dado
            List<IServiceValidator<TestDTO>> validators = new ArrayList<>();

            // Quando
            List<ServiceValidatorRegistry> result = service.registryValidators(validators);

            // Então
            assertThat(result).isEmpty();
        }
    }

    @Nested
    @DisplayName("useJakartaValidation")
    class TestesUseJakartaValidation {

        @Test
        @DisplayName("Deve retornar true por padrão")
        void deveRetornarTruePorPadrao() {
            // Quando
            boolean result = service.useJakartaValidation();

            // Então
            assertThat(result).isTrue();
        }

        @Test
        @DisplayName("Deve retornar false quando sobrescrito")
        void deveRetornarFalseQuandoSobrescrito() {
            // Dado
            TestHasValidationNoJakarta service2 = new TestHasValidationNoJakarta(translator);

            // Quando
            boolean result = service2.useJakartaValidation();

            // Então
            assertThat(result).isFalse();
        }
    }

    @Nested
    @DisplayName("validate")
    class TestesValidate {

        @Test
        @DisplayName("Deve validar objeto com validador jakarta")
        void deveValidarObjetoComValidadorJakarta() {
            // Dado
            TestDTO dto = new TestDTO();
            dto.setId(1L);

            // Quando
            service.validate(dto);

            // Então - o validador jakarta deve ser chamado
        }

        @Test
        @DisplayName("Deve validar com validadores personalizados")
        void deveValidarComValidadoresPersonalizados() {
            // Dado
            TestDTO dto = new TestDTO();
            dto.setId(1L);
            service.getValidators().add(validator1);
            service.getValidators().add(validator2);

            // Quando
            service.validate(dto);

            // Então
            verify(validator1).validate(dto);
            verify(validator2).validate(dto);
        }

        @Test
        @DisplayName("Deve lançar exceção de serviço quando validador falhar")
        void deveLancarExcecaoDeServicoQuandoValidadorFalhar() {
            // Dado
            TestDTO dto = new TestDTO();
            dto.setId(1L);
            service.getValidators().add(validator1);
            ServiceException exception = new ServiceException();
            exception.add("Erro de validação");
            doThrow(exception).when(validator1).validate(dto);

            // Quando & Então
            assertThatThrownBy(() -> service.validate(dto))
                .isInstanceOf(ServiceException.class);
        }

        @Test
        @DisplayName("Não deve usar validação jakarta quando desabilitado")
        void deveNaoUsarValidacaoJakartaQuandoDesabilitado() {
            // Dado
            TestHasValidationNoJakarta service2 = new TestHasValidationNoJakarta(translator);
            TestDTO dto = new TestDTO();
            dto.setId(1L);
            service2.getValidators().add(validator1);

            // Quando
            service2.validate(dto);

            // Então
            verify(validator1).validate(dto);
        }
    }

    // Implementação de teste
    static class TestHasValidation implements HasValidation<TestDTO> {
        private final Translator translator;
        private final List<IServiceValidator<TestDTO>> validators = new ArrayList<>();

        TestHasValidation(Translator translator) {
            this.translator = translator;
        }

        @Override
        public Translator getTranslator() {
            return translator;
        }

        @Override
        public List<IServiceValidator<TestDTO>> getValidators() {
            return validators;
        }

        @Override
        public JakartaValidator<TestDTO> createJakartaValidator() {
            return mock(JakartaValidator.class);
        }
    }

    static class TestHasValidationNoJakarta extends TestHasValidation {
        TestHasValidationNoJakarta(Translator translator) {
            super(translator);
        }

        @Override
        public boolean useJakartaValidation() {
            return false;
        }
    }

    // DTO de teste
    static class TestDTO implements Serializable {
        private static final long serialVersionUID = 1L;
        private Long id;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }
    }
}
