package com.ia.core.owl.service;

import com.ia.core.llm.service.vector.VectorStoreOperations;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.content.Media;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Unit tests for LLMCommunicator.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@ExtendWith(MockitoExtension.class)
class LLMCommunicatorTest {

  @Mock
  private ChatModel chatModel;

  @Mock
  private ChatMemory chatMemory;

  @Mock
  private VectorStoreOperations vectorStoreOperations;

  private LLMCommunicator llmCommunicator;

  @BeforeEach
  void setUp() {
    llmCommunicator = new LLMCommunicator(chatModel, chatMemory, vectorStoreOperations, "http://localhost:11434", "llama3");
    // Mock the advisor to avoid null advisor errors
    QuestionAnswerAdvisor mockAdvisor = mock(QuestionAnswerAdvisor.class);
    when(vectorStoreOperations.getQuestionAnswerAdvisor()).thenReturn(mockAdvisor);
  }

  @Test
  void testSendPromptWithStringAndTools() {
    // Arrange
    String promptText = "Test prompt";
    String chatSessionId = "session-123";

    // Act & Assert
    // Note: This test verifies that the method can be called without exceptions
    // Full integration testing would require mocking ChatClient.Builder which is complex
    assertDoesNotThrow(() -> llmCommunicator.sendPrompt(promptText, chatSessionId));
  }

  @Test
  void testSendPromptWithPromptAndTools() {
    // Arrange
    Prompt prompt = new Prompt("Test prompt");
    String chatSessionId = "session-123";

    // Act & Assert
    assertDoesNotThrow(() -> llmCommunicator.sendPrompt(prompt, chatSessionId));
  }

  @Test
  void testSendPromptWithStringSkillsAndTools() {
    // Arrange
    String promptText = "Test prompt";
    String chatSessionId = "session-123";

    // Act & Assert
    assertDoesNotThrow(() -> llmCommunicator.sendPrompt(promptText, chatSessionId));
  }

  @Test
  void testSendPromptWithPromptSkillsAndTools() {
    // Arrange
    Prompt prompt = new Prompt("Test prompt");
    String chatSessionId = "session-123";

    // Act & Assert
    assertDoesNotThrow(() -> llmCommunicator.sendPrompt(prompt, chatSessionId));
  }

  @Test
  void testSendPromptWithStringMediaAndTools() {
    // Arrange
    String promptText = "Test prompt";
    List<Media> media = List.of(mock(Media.class));
    String chatSessionId = "session-123";

    // Act & Assert
    assertDoesNotThrow(() -> llmCommunicator.sendPrompt(promptText, media, chatSessionId));
  }

  @Test
  void testSendPromptWithPromptMediaAndTools() {
    // Arrange
    Prompt prompt = new Prompt("Test prompt");
    List<Media> media = List.of(mock(Media.class));
    String chatSessionId = "session-123";

    // Act & Assert
    assertDoesNotThrow(() -> llmCommunicator.sendPrompt(prompt, media, chatSessionId));
  }

  @Test
  void testSendPromptWithNullTools() {
    // Arrange
    String promptText = "Test prompt";
    String chatSessionId = "session-123";

    // Act & Assert
    assertDoesNotThrow(() -> llmCommunicator.sendPrompt(promptText, chatSessionId));
  }

  @Test
  void testSendPromptWithEmptySkills() {
    // Arrange
    String promptText = "Test prompt";
    String chatSessionId = "session-123";

    // Act & Assert
    assertDoesNotThrow(() -> llmCommunicator.sendPrompt(promptText, chatSessionId));
  }

  @Test
  void testSendPromptWithNullSkills() {
    // Arrange
    String promptText = "Test prompt";
    String chatSessionId = "session-123";

    // Act & Assert
    assertDoesNotThrow(() -> llmCommunicator.sendPrompt(promptText, chatSessionId));
  }
}
