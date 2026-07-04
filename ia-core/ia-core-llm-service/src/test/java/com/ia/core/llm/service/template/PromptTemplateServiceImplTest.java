package com.ia.core.llm.service.template;

import com.ia.core.llm.model.prompt.FinalidadePromptEnum;
import com.ia.core.llm.model.template.Template;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;

import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit tests for PromptTemplateServiceImpl.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@ExtendWith(MockitoExtension.class)
class PromptTemplateServiceImplTest {

  @Mock
  private TemplateRepository templateRepository;

  private PromptTemplateServiceImpl promptTemplateService;

  @BeforeEach
  void setUp() {
    promptTemplateService = new PromptTemplateServiceImpl(templateRepository);
  }

  @Test
  void testCreateSimplePrompt() {
    // Arrange
    String document = "Test document";
    String text = "Test question";

    // Act
    Prompt result = promptTemplateService.createSimplePrompt(document, text);

    // Assert
    assertNotNull(result);
    assertNotNull(result.getInstructions());
    // The prompt template substitutes the placeholders, so the instructions should contain the actual values
    assertTrue(result.getInstructions().stream().anyMatch(msg -> msg.getText().contains(document)));
    assertTrue(result.getInstructions().stream().anyMatch(msg -> msg.getText().contains(text)));
  }

  @Test
  void testCreateSystemPrompt() {
    // Arrange
    String document = "Test document";
    String text = "Test question";
    String systemTemplate = "You are a helpful assistant. Document: {documento}";
    FinalidadePromptEnum finalidade = FinalidadePromptEnum.RESPOSTA_TEXTUAL;
    Map<String, Object> params = Map.of("key", "value");

    // Act
    Prompt result = promptTemplateService.createSystemPrompt(document, text, systemTemplate, finalidade, params);

    // Assert
    assertNotNull(result);
    assertNotNull(result.getInstructions());
    assertEquals(2, result.getInstructions().size());
  }

  @Test
  void testCreateSystemMessage() {
    // Arrange
    String systemTemplate = "You are a helpful assistant. Context: {context}";
    Map<String, Object> params = Map.of("context", "test context");

    // Act
    Message result = promptTemplateService.createSystemMessage(systemTemplate, params);

    // Assert
    assertNotNull(result);
    assertNotNull(result.getText());
    assertTrue(result.getText().contains("test context"));
  }

  @Test
  void testCreatePromptTemplate() {
    // Arrange
    String templateString = "Hello {name}, welcome to {place}";

    // Act
    PromptTemplate result = promptTemplateService.createPromptTemplate(templateString);

    // Assert
    assertNotNull(result);
    assertEquals(templateString, result.getTemplate());
  }

  @Test
  void testProcessTemplate() {
    // Arrange
    String templateId = "template-123";
    String templateContent = "Hello {name}, welcome to {place}";
    Map<String, Object> params = Map.of("name", "John", "place", "World");
    Template template = new Template();
    template.setIdentificador(templateId);
    template.setConteudo(templateContent);

    when(templateRepository.findByIdentificador(templateId)).thenReturn(Optional.of(template));

    // Act
    String result = promptTemplateService.processTemplate(templateId, params);

    // Assert
    assertNotNull(result);
    assertTrue(result.contains("John"));
    assertTrue(result.contains("World"));
    verify(templateRepository).findByIdentificador(templateId);
  }

  @Test
  void testGetTemplateById() {
    // Arrange
    String templateId = "template-123";
    String templateContent = "Test template content";
    Template template = new Template();
    template.setIdentificador(templateId);
    template.setConteudo(templateContent);

    when(templateRepository.findByIdentificador(templateId)).thenReturn(Optional.of(template));

    // Act
    String result = promptTemplateService.getTemplateById(templateId);

    // Assert
    assertEquals(templateContent, result);
    verify(templateRepository).findByIdentificador(templateId);
  }

  @Test
  void testGetTemplateById_NotFound() {
    // Arrange
    String templateId = "non-existent-template";

    when(templateRepository.findByIdentificador(templateId)).thenReturn(Optional.empty());

    // Act & Assert
    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> promptTemplateService.getTemplateById(templateId)
    );

    assertEquals("Template não encontrado: " + templateId, exception.getMessage());
    verify(templateRepository).findByIdentificador(templateId);
  }

  @Test
  void testProcessTemplate_TemplateNotFound() {
    // Arrange
    String templateId = "non-existent-template";
    Map<String, Object> params = Map.of("key", "value");

    when(templateRepository.findByIdentificador(templateId)).thenReturn(Optional.empty());

    // Act & Assert
    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> promptTemplateService.processTemplate(templateId, params)
    );

    assertEquals("Template não encontrado: " + templateId, exception.getMessage());
    verify(templateRepository).findByIdentificador(templateId);
  }

  @Test
  void testCreateSimplePrompt_WithEmptyStrings() {
    // Arrange
    String document = "";
    String text = "";

    // Act
    Prompt result = promptTemplateService.createSimplePrompt(document, text);

    // Assert
    assertNotNull(result);
    assertNotNull(result.getInstructions());
  }

  @Test
  void testCreateSystemPrompt_WithEmptyParams() {
    // Arrange
    String document = "Test document";
    String text = "Test question";
    String systemTemplate = "You are a helpful assistant";
    FinalidadePromptEnum finalidade = FinalidadePromptEnum.RESPOSTA_TEXTUAL;
    Map<String, Object> params = Map.of();

    // Act
    Prompt result = promptTemplateService.createSystemPrompt(document, text, systemTemplate, finalidade, params);

    // Assert
    assertNotNull(result);
    assertEquals(2, result.getInstructions().size());
  }
}
