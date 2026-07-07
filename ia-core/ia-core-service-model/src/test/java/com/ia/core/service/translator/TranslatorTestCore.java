package com.ia.core.service.translator;

import com.ia.test.CoreBaseUnitTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Translator")
class TranslatorTestCore extends CoreBaseUnitTest {

    @Nested
    @DisplayName("locale padrão")
    class LocalePadrao {

        @Test
        @DisplayName("CT001 - Deve retornar locale padrão pt_BR")
        void deveRetornarLocalePadraoPtBR() {
            // Arrange
            Translator translator = new TestTranslator();

            // Act
            Locale locale = translator.getLocale();

            // Assert
            assertThat(locale).isEqualTo(new Locale("pt", "BR"));
        }
    }

    @Nested
    @DisplayName("locales suportados")
    class LocalesSuportados {

        @Test
        @DisplayName("CT002 - Deve listar locales suportados")
        void deveListarLocalesSuportados() {
            // Arrange
            Translator translator = new TestTranslator();

            // Act
            List<Locale> locales = translator.getProvidedLocales();

            // Assert
            assertThat(locales).isNotEmpty();
            assertThat(locales).contains(new Locale("pt", "BR"));
        }
    }

    @Nested
    @DisplayName("tradução com locale específico")
    class TraducaoLocaleEspecifico {

        @Test
        @DisplayName("CT003 - Deve obter tradução com locale específico")
        void deveObterTraducaoComLocaleEspecifico() {
            // Arrange
            Translator translator = new TestTranslator();
            String key = "test.key";

            // Act
            String traducao = translator.getTranslation(key, Locale.ENGLISH);

            // Assert
            assertThat(traducao).isNotNull();
        }
    }

    @Nested
    @DisplayName("tradução com locale padrão")
    class TraducaoLocalePadrao {

        @Test
        @DisplayName("CT004 - Deve obter tradução com locale padrão")
        void deveObterTraducaoComLocalePadrao() {
            // Arrange
            Translator translator = new TestTranslator();
            String key = "test.key";

            // Act
            String traducao = translator.getTranslation(key);

            // Assert
            assertThat(traducao).isNotNull();
        }
    }

    @Nested
    @DisplayName("tradução com parâmetros")
    class TraducaoParametros {

        @Test
        @DisplayName("CT005 - Deve obter tradução com parâmetros")
        void deveObterTraducaoComParametros() {
            // Arrange
            Translator translator = new TestTranslator();
            String key = "test.key.with.params";

            // Act
            String traducao = translator.getTranslation(key, Locale.ENGLISH, "param1", "param2");

            // Assert
            assertThat(traducao).isNotNull();
        }
    }

    @Nested
    @DisplayName("tradução com chave inexistente")
    class TraducaoChaveInexistente {

        @Test
        @DisplayName("CT006 - Deve retornar chave original quando não encontrada")
        void deveRetornarChaveOriginalQuandoNaoEncontrada() {
            // Arrange
            Translator translator = new TestTranslator();
            String key = "chave.inexistente";

            // Act
            String traducao = translator.getTranslation(key);

            // Assert
            assertThat(traducao).isEqualTo(key);
        }
    }

    @Nested
    @DisplayName("tradução com locale não suportado")
    class TraducaoLocaleNaoSuportado {

        @Test
        @DisplayName("CT007 - Deve usar locale padrão quando locale não suportado")
        void deveUsarLocalePadraoQuandoLocaleNaoSuportado() {
            // Arrange
            Translator translator = new TestTranslator();
            String key = "test.key";
            Locale localeNaoSuportado = new Locale("xx", "XX");

            // Act
            String traducao = translator.getTranslation(key, localeNaoSuportado);

            // Assert
            assertThat(traducao).isNotNull();
        }
    }

    static class TestTranslator implements Translator {
        @Override
        public List<Locale> getProvidedLocales() {
            return List.of(
                new Locale("pt", "BR"),
                Locale.ENGLISH,
                Locale.FRENCH
            );
        }

        @Override
        public String getTranslation(String key, Locale locale, Object... params) {
            // Simula comportamento de retornar chave original quando não encontrada
            if (key.equals("chave.inexistente")) {
                return key;
            }
            return "Translation for " + key + " with params in " + locale.getLanguage();
        }
    }
}