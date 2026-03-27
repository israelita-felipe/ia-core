package com.ia.core.communication.service.email;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mail.javamail.JavaMailSender;

/**
 * Testes unitários para EmailConfig.
 *
 * @author Israel Araújo
 */
class EmailConfigTest {

  private EmailConfig emailConfig;
  private JavaMailSender sender;

  @BeforeEach
  void setUp() {
    sender = mock(JavaMailSender.class);
    emailConfig = new EmailConfig(sender);
  }

  @Test
  @DisplayName("Deve ter valores padrão corretos")
  void deveTerValoresPadraoCorretos() {
    assertEquals("smtp.gmail.com", emailConfig.getHost());
    assertEquals(587, emailConfig.getPort());
    assertTrue(emailConfig.isAuth());
    assertTrue(emailConfig.isStarttls());
    assertEquals(30000, emailConfig.getTimeout());
  }

  @Test
  @DisplayName("Deve permitir configuração de SMTP Gmail com SSL")
  void devePermitirConfiguracaoSmtpGmailComSsl() {
    emailConfig.setHost("smtp.gmail.com");
    emailConfig.setPort(465);
    emailConfig.setUsername("test@gmail.com");
    emailConfig.setPassword("password");
    emailConfig.setAuth(true);
    emailConfig.setStarttls(false);
    emailConfig.setFromAddress("test@gmail.com");
    emailConfig.setFromName("Test Sender");

    assertEquals("smtp.gmail.com", emailConfig.getHost());
    assertEquals(465, emailConfig.getPort());
    assertEquals("test@gmail.com", emailConfig.getUsername());
    assertTrue(emailConfig.isAuth());
    assertFalse(emailConfig.isStarttls());
    assertEquals("test@gmail.com", emailConfig.getFromAddress());
    assertEquals("Test Sender", emailConfig.getFromName());
  }

  @Test
  @DisplayName("Deve permitir configuração de SMTP Gmail com STARTTLS")
  void devePermitirConfiguracaoSmtpGmailComStarttls() {
    emailConfig.setHost("smtp.gmail.com");
    emailConfig.setPort(587);
    emailConfig.setUsername("test@gmail.com");
    emailConfig.setPassword("password");
    emailConfig.setAuth(true);
    emailConfig.setStarttls(true);

    assertEquals("smtp.gmail.com", emailConfig.getHost());
    assertEquals(587, emailConfig.getPort());
    assertTrue(emailConfig.isStarttls());
  }

  @Test
  @DisplayName("Deve permitir configuração de SMTP Outlook")
  void devePermitirConfiguracaoSmtpOutlook() {
    emailConfig.setHost("smtp.outlook.com");
    emailConfig.setPort(587);
    emailConfig.setUsername("test@outlook.com");
    emailConfig.setPassword("password");
    emailConfig.setAuth(true);
    emailConfig.setStarttls(true);

    assertEquals("smtp.outlook.com", emailConfig.getHost());
    assertEquals(587, emailConfig.getPort());
    assertTrue(emailConfig.isStarttls());
  }

  @Test
  @DisplayName("Deve permitir configuração de timeout customizado")
  void devePermitirConfiguracaoTimeoutCustomizado() {
    emailConfig.setTimeout(60000);

    assertEquals(60000, emailConfig.getTimeout());
  }

  @Test
  @DisplayName("Deve permitir configuração sem autenticação (servidor SMTP público)")
  void devePermitirConfiguracaoSemAutenticacao() {
    emailConfig.setAuth(false);
    emailConfig.setStarttls(false);

    assertFalse(emailConfig.isAuth());
    assertFalse(emailConfig.isStarttls());
  }
}
