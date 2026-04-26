package com.ia.core.service.rules;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Testes para {@link BusinessRuleChain}.
 *
 * @author Israel Araújo
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("BusinessRuleChain")
class BusinessRuleChainTest {

    @Mock
    private BusinessRule<java.io.Serializable> rule1;

    @Mock
    private BusinessRule<java.io.Serializable> rule2;

    @Mock
    private BusinessRule<java.io.Serializable> rule3;

    private BusinessRuleChain<java.io.Serializable> chain;

    @BeforeEach
    void setUp() {
        chain = new BusinessRuleChain<>();
    }

    @Nested
    @DisplayName("Construtor")
    class TestesConstrutor {

        @Test
        @DisplayName("Deve criar cadeia vazia")
        void deveCriarCadeiaVazia() {
            // Então
            assertThat(chain.isEmpty()).isTrue();
            assertThat(chain.size()).isEqualTo(0);
        }
    }

    @Nested
    @DisplayName("addRule")
    class TestesAddRule {

        @Test
        @DisplayName("Deve adicionar regra única à cadeia")
        void deveAdicionarRegraUnicaACadeia() {
            // Quando
            chain.addRule(rule1);

            // Então
            assertThat(chain.size()).isEqualTo(1);
            assertThat(chain.isEmpty()).isFalse();
        }

        @Test
        @DisplayName("Deve retornar cadeia para encadeamento fluente")
        void deveRetornarCadeiaParaEncadeamentoFluente() {
            // Quando
            BusinessRuleChain<java.io.Serializable> result = chain.addRule(rule1);

            // Então
            assertThat(result).isSameAs(chain);
        }

        @Test
        @DisplayName("Deve adicionar múltiplas regras via varargs")
        void deveAdicionarMultiplasRegrasViaVarargs() {
            // Quando
            chain.addRules(rule1, rule2, rule3);

            // Então
            assertThat(chain.size()).isEqualTo(3);
        }
    }

    @Nested
    @DisplayName("size e isEmpty")
    class TestesSize {

        @Test
        @DisplayName("Deve retornar true para isEmpty em cadeia vazia")
        void deveRetornarTrueParaIsEmptyEmCadeiaVazia() {
            // Quando
            boolean result = chain.isEmpty();

            // Então
            assertThat(result).isTrue();
        }

        @Test
        @DisplayName("Deve retornar false para isEmpty em cadeia não vazia")
        void deveRetornarFalseParaIsEmptyEmCadeiaNaoVazia() {
            // Dado
            chain.addRule(rule1);

            // Quando
            boolean result = chain.isEmpty();

            // Então
            assertThat(result).isFalse();
        }

        @Test
        @DisplayName("Deve retornar tamanho correto")
        void deveRetornarTamanhoCorreto() {
            // Dado
            chain.addRule(rule1).addRule(rule2).addRule(rule3);

            // Quando
            int result = chain.size();

            // Então
            assertThat(result).isEqualTo(3);
        }
    }
}
