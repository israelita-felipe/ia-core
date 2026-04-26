package com.ia.core.service.validators;

import com.ia.core.service.translator.Translator;
import com.ia.core.service.validators.IServiceValidator.ValidatorRegistration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.Serializable;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

/**
 * Testes para {@link ServiceValidator}.
 *
 * @author Israel Araújo
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("ServiceValidator")
class ServiceValidatorTest {

  @Mock
  private Translator translator;

  private TestServiceValidator validator;

  @BeforeEach
  void setUp() {
    validator = new TestServiceValidator(translator);
  }

  @Nested
  @DisplayName("Construtor")
  class TestesConstrutor {

    @Test
    @DisplayName("Deve criar validador com tradutor")
    void deveCriarValidadorComTradutor() {
      // Quando
      Translator result = validator.getTranslator();

      // Então
      assertThat(result).isEqualTo(translator);
    }

    @Test
    @DisplayName("Deve inicializar hasValidation como conjunto vazio")
    void deveInicializarHasValidationComoConjuntoVazio() {
      // Quando
      Set<?> result = validator.getHasValidation();

      // Então
      assertThat(result).isEmpty();
    }
  }

  @Nested
  @DisplayName("registry")
  class TestesRegistry {

    @Test
    @DisplayName("Deve registrar has validation")
    void deveRegistrarHasValidation() {
      // Dado
      HasValidation<TestDTO> hasValidation = mock(HasValidation.class);

      // Quando
      ValidatorRegistration result = validator.registry(hasValidation);

      // Então
      assertThat(result).isNotNull();
      assertThat(validator.getHasValidation()).contains(hasValidation);
    }

    @Test
    @DisplayName("Deve retornar ação de remoção")
    void deveRetornarAcaoDeRemocao() {
      // Dado
      HasValidation<TestDTO> hasValidation = mock(HasValidation.class);
      ValidatorRegistration registry = validator.registry(hasValidation);

      // Quando
      registry.remove();

      // Então
      assertThat(validator.getHasValidation())
          .doesNotContain(hasValidation);
    }
  }

  // Implementação de teste
  static class TestServiceValidator
    extends ServiceValidator<TestDTO> {
    TestServiceValidator(Translator translator) {
      super(translator);
    }

    @Override
    public void validate(TestDTO object,
                         ValidationResult result) {
      // Não implementado para teste
    }
  }

  // DTO de teste
  static class TestDTO
    implements Serializable {
    private static final long serialVersionUID = 1L;
  }
}
