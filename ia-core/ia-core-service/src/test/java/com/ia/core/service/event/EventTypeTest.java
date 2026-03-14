package com.ia.core.service.event;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * Testes para {@link EventType}.
 *
 * @author Israel Araújo
 */
@DisplayName("EventType")
class EventTypeTest {

    @Nested
    @DisplayName("Tipos padrão")
    class TestesTiposPadrao {

        @Test
        @DisplayName("Deve ter tipo CRIADA")
        void deveTerTipoCriada() {
            // Então
            assertThat(EventType.CRIADA).isNotNull();
            assertThat(EventType.CRIADA.name()).isEqualTo("CRIADA");
        }

        @Test
        @DisplayName("Deve ter tipo ATUALIZADA")
        void deveTerTipoAtualizada() {
            // Então
            assertThat(EventType.ATUALIZADA).isNotNull();
            assertThat(EventType.ATUALIZADA.name()).isEqualTo("ATUALIZADA");
        }

        @Test
        @DisplayName("Deve ter tipo EXCLUIDA")
        void deveTerTipoExcluida() {
            // Então
            assertThat(EventType.EXCLUIDA).isNotNull();
            assertThat(EventType.EXCLUIDA.name()).isEqualTo("EXCLUIDA");
        }
    }

    @Nested
    @DisplayName("name")
    class TestesName {

        @Test
        @DisplayName("Deve retornar CRIADA para tipo CRIADA")
        void deveRetornarCriadaParaTipoCriada() {
            // Quando
            String result = EventType.CRIADA.name();

            // Então
            assertThat(result).isEqualTo("CRIADA");
        }

        @Test
        @DisplayName("Deve retornar ATUALIZADA para tipo ATUALIZADA")
        void deveRetornarAtualizadaParaTipoAtualizada() {
            // Quando
            String result = EventType.ATUALIZADA.name();

            // Então
            assertThat(result).isEqualTo("ATUALIZADA");
        }

        @Test
        @DisplayName("Deve retornar EXCLUIDA para tipo EXCLUIDA")
        void deveRetornarExcluidaParaTipoExcluida() {
            // Quando
            String result = EventType.EXCLUIDA.name();

            // Então
            assertThat(result).isEqualTo("EXCLUIDA");
        }

        @Test
        @DisplayName("Tipo de evento customizado deve retornar nome customizado")
        void tipoDeEventoCustomizadoDeveRetornarNomeCustomizado() {
            // Dado
            EventType customType = () -> "CUSTOM";

            // Quando
            String result = customType.name();

            // Então
            assertThat(result).isEqualTo("CUSTOM");
        }
    }
}
