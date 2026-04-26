package com.ia.core.service.event;

import com.ia.core.service.dto.DTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.Serializable;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Testes para {@link BaseServiceEvent}.
 *
 * @author Israel Araújo
 */
@DisplayName("BaseServiceEvent")
class BaseServiceEventTest {

    @Nested
    @DisplayName("Construtor sem dados")
    class TestesConstrutorSemDados {

        @Test
        @DisplayName("Deve criar evento sem dados")
        void deveCriarEventoSemDados() {
            // Dado
            Object source = new Object();
            TestDTO dto = new TestDTO();
            EventType eventType = EventType.CRIADA;

            // Quando
            BaseServiceEvent<TestDTO> event = new BaseServiceEvent<>(source, dto, eventType);

            // Então
            assertThat(event.getSource()).isEqualTo(source);
            assertThat(event.getDto()).isEqualTo(dto);
            assertThat(event.getEventType()).isEqualTo(eventType);
            assertThat(event.getData()).isNull();
            assertThat(event.hasData()).isFalse();
        }
    }

    @Nested
    @DisplayName("Construtor com dados")
    class TestesConstrutorComDados {

        @Test
        @DisplayName("Deve criar evento com dados")
        void deveCriarEventoComDados() {
            // Dado
            Object source = new Object();
            TestDTO dto = new TestDTO();
            EventType eventType = EventType.ATUALIZADA;
            Object data = "dados adicionais";

            // Quando
            BaseServiceEvent<TestDTO> event = new BaseServiceEvent<>(source, dto, eventType, data);

            // Então
            assertThat(event.getSource()).isEqualTo(source);
            assertThat(event.getDto()).isEqualTo(dto);
            assertThat(event.getEventType()).isEqualTo(eventType);
            assertThat(event.getData()).isEqualTo(data);
            assertThat(event.hasData()).isTrue();
        }
    }

    @Nested
    @DisplayName("getDto")
    class TestesGetDto {

        @Test
        @DisplayName("Deve retornar o DTO")
        void deveRetornarODTO() {
            // Dado
            TestDTO dto = new TestDTO();
            BaseServiceEvent<TestDTO> event = new BaseServiceEvent<>(new Object(), dto, EventType.CRIADA);

            // Quando
            TestDTO result = event.getDto();

            // Então
            assertThat(result).isEqualTo(dto);
        }
    }

    @Nested
    @DisplayName("getEventType")
    class TestesGetEventType {

        @Test
        @DisplayName("Deve retornar o tipo do evento")
        void deveRetornarOTipoDoEvento() {
            // Dado
            EventType eventType = EventType.EXCLUIDA;
            BaseServiceEvent<TestDTO> event = new BaseServiceEvent<>(new Object(), new TestDTO(), eventType);

            // Quando
            EventType result = event.getEventType();

            // Então
            assertThat(result).isEqualTo(eventType);
        }
    }

    @Nested
    @DisplayName("hasData")
    class TestesHasData {

        @Test
        @DisplayName("Deve retornar true quando tem dados")
        void deveRetornarTrueQuandoTemDados() {
            // Dado
            BaseServiceEvent<TestDTO> event = new BaseServiceEvent<>(new Object(), new TestDTO(), EventType.CRIADA, "data");

            // Quando
            boolean result = event.hasData();

            // Então
            assertThat(result).isTrue();
        }

        @Test
        @DisplayName("Deve retornar false quando não tem dados")
        void deveRetornarFalseQuandoNaoTemDados() {
            // Dado
            BaseServiceEvent<TestDTO> event = new BaseServiceEvent<>(new Object(), new TestDTO(), EventType.CRIADA);

            // Quando
            boolean result = event.hasData();

            // Então
            assertThat(result).isFalse();
        }
    }

    @Nested
    @DisplayName("toString")
    class TestesToString {

        @Test
        @DisplayName("Deve retornar string formatada")
        void deveRetornarStringFormatada() {
            // Dado
            Object source = new Object();
            TestDTO dto = new TestDTO();
            BaseServiceEvent<TestDTO> event = new BaseServiceEvent<>(source, dto, EventType.CRIADA, "data");

            // Quando
            String result = event.toString();

            // Então
            assertThat(result).contains("BaseServiceEvent");
        }
    }

    // DTO de teste
    static class TestDTO implements DTO<Serializable> {
        @Override
        public DTO<Serializable> cloneObject() {
            return this;
        }
    }
}
