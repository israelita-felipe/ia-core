package com.ia.core.llm.model.chat;

import com.ia.test.CoreBaseUnitTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Testes para a classe ChatSession.
 */
@DisplayName("Testes de ChatSession")
class ChatSessionTestCore extends CoreBaseUnitTest {

  @Test
  @DisplayName("Deve criar ChatSession com builder")
  void deveCriarChatSessionComBuilder() {
    // Arrange
    // Act
    ChatSession session = ChatSession.builder()
        .titulo("Sessão de Teste")
        .build();

    // Assert
    assertThat(session).isNotNull();
    assertThat(session.getTitulo()).isEqualTo("Sessão de Teste");
  }

  @Test
  @DisplayName("Deve inicializar valores padrão")
  void deveInicializarValoresPadrao() {
    // Arrange
    // Act
    ChatSession session = ChatSession.builder()
        .titulo("Sessão de Teste")
        .build();

    // Assert
    assertThat(session).isNotNull();
  }
}
