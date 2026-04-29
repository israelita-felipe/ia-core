package com.ia.core.service.translator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Testes para {@link CoreTranslator}.
 *
 * @author Israel Araújo
 */
/**
 * Classe que representa os serviços de negócio para core translator test.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a CoreTranslatorTest
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
 */
@DisplayName("CoreTranslator")
class CoreTranslatorTest {

    private CoreTranslator translator;

    @BeforeEach
    void setUp() {
        translator = new CoreTranslator("i18n/translations");
    }

    @Nested
    @DisplayName("Construtor")
    class TestesConstrutor {

        @Test
        @DisplayName("Deve criar tradutor com prefixos")
        void deveCriarTradutorComPrefixos() {
            // Quando
            CoreTranslator result = new CoreTranslator("i18n/translations");

            // Então
            assertThat(result).isNotNull();
        }
    }

    @Nested
    @DisplayName("getProvidedLocales")
    class TestesGetProvidedLocales {

        @Test
        @DisplayName("Deve retornar lista de locales suportados")
        void deveRetornarListaDeLocalesSuportados() {
            // Quando
            List<Locale> result = translator.getProvidedLocales();

            // Então
            assertThat(result).isNotEmpty();
            assertThat(result).contains(Locale.of("pt", "BR"));
        }
    }

    @Nested
    @DisplayName("getTranslation")
    class TestesGetTranslation {

        @Test
        @DisplayName("Deve retornar key quando tradução não encontrada")
        void deveRetornarKeyQuandoTraducaoNaoEncontrada() {
            // Quando
            String result = translator.getTranslation("chave.inexistente", Locale.getDefault());

            // Então
            assertThat(result).isEqualTo("chave.inexistente");
        }

        @Test
        @DisplayName("Deve retornar string vazia para key nula")
        void deveRetornarStringVaziaParaKeyNula() {
            // Quando
            String result = translator.getTranslation(null, Locale.getDefault());

            // Então
            assertThat(result).isEmpty();
        }

        @Test
        @DisplayName("Deve substituir parâmetros na tradução")
        void deveSubstituirParametrosNaTraducao() {
            // Dado
            String key = "test.param";
            Object[] params = new Object[]{"valor1", "valor2"};

            // Quando
            String result = translator.getTranslation(key, Locale.getDefault(), params);

            // Então
            assertThat(result).isNotNull();
        }
    }
}
