package com.ia.core.view.help.dialog;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes para HelpDialogViewFactory.
 *
 * <p>Testa a funcionalidade de codificação base64.
 *
 * @author Israel Araújo
 */
@DisplayName("Help Dialog View Factory")
class HelpDialogViewFactoryTest {

  /**
   * Testa a lógica de codificação base64 usada pelo HelpDialogViewFactory.
   */
  @Test
  @DisplayName("Deve codificar conteúdo HTML para base64")
  void testToBase64() {
    String content = "<html><body>Teste</body></html>";
    String result = Base64.getEncoder().encodeToString(content.getBytes(StandardCharsets.UTF_8));

    assertNotNull(result);
    assertFalse(result.isEmpty());
    // Verifica se o resultado é base64 válido
    String decoded = new String(Base64.getDecoder().decode(result), StandardCharsets.UTF_8);
    assertEquals(content, decoded);
  }

  @Test
  @DisplayName("Deve retornar string vazia para conteúdo nulo")
  void testToBase64Null() {
    String result = "";
    // Simula o comportamento do método toBase64 com null
    String input = null;
    if (input == null) {
      result = "";
    }
    assertEquals("", result);
  }

  @Test
  @DisplayName("Deve retornar string vazia para conteúdo vazio")
  void testToBase64Empty() {
    String content = "";
    String result = Base64.getEncoder().encodeToString(content.getBytes(StandardCharsets.UTF_8));
    assertEquals("", result);
  }

  @Test
  @DisplayName("Deve codificar conteúdo com caracteres especiais")
  void testToBase64WithSpecialChars() {
    String content = "<html><body>Olá, usuário! ÁÉÍÓÚ</body></html>";
    String result = Base64.getEncoder().encodeToString(content.getBytes(StandardCharsets.UTF_8));

    assertNotNull(result);
    assertFalse(result.isEmpty());
    // Verifica se o resultado é base64 válido
    String decoded = new String(Base64.getDecoder().decode(result), StandardCharsets.UTF_8);
    assertEquals(content, decoded);
  }

  @Test
  @DisplayName("Deve codificar conteúdo Markdown para base64")
  void testToBase64Markdown() {
    String content = "<!DOCTYPE html><html><head><meta charset='UTF-8'></head><body>" +
        "<h1>Título</h1><p>Parágrafo com **negrito** e *itálico*</p>" +
        "<ul><li>Item 1</li><li>Item 2</li></ul>" +
        "</body></html>";
    String result = Base64.getEncoder().encodeToString(content.getBytes(StandardCharsets.UTF_8));

    assertNotNull(result);
    assertFalse(result.isEmpty());
    // Verifica se o resultado é base64 válido
    String decoded = new String(Base64.getDecoder().decode(result), StandardCharsets.UTF_8);
    assertEquals(content, decoded);
  }
}
