package com.ia.core.communication.service.estrategia;

import com.ia.core.communication.model.mensagem.StatusMensagem;
import com.ia.core.communication.model.mensagem.TipoCanal;
import com.ia.core.communication.service.email.EmailService;
import com.ia.core.communication.service.email.EstrategiaEmail;
import com.ia.core.communication.service.mensagem.ResultadoEnvio;
import com.ia.core.communication.service.model.mensagem.dto.MensagemDTO;
import com.ia.core.model.exception.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Testes unitários para EstrategiaEmail.
 *
 * @author Israel Araújo
 */
/**
 * Classe que representa os serviços de negócio para estrategia email test.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a EstrategiaEmailTest
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
 */
@ExtendWith(MockitoExtension.class)
class EstrategiaEmailTest {

  private EmailService mensagemProvider;
  private EstrategiaEmail estrategia;

  @BeforeEach
  void setUp() {
    mensagemProvider = mock(EmailService.class);
    estrategia = new EstrategiaEmail(mensagemProvider);
  }

  @Test
  @DisplayName("Deve executar estratégia com dados válidos")
  void deveExecutarEstrategiaComDadosValidos() {
    MensagemDTO mensagem = criarMensagemValida();
    when(mensagemProvider.enviar(any(MensagemDTO.class)))
        .thenReturn(ResultadoEnvio.sucesso("EMAIL_123456"));

    MensagemDTO resultado = estrategia.executar(mensagem);

    assertNotNull(resultado);
    assertEquals(StatusMensagem.ENVIADA, resultado.getStatusMensagem());
    assertEquals("EMAIL_123456", resultado.getIdExterno());
  }

  @Test
  @DisplayName("Deve falhar quando e-mail destinatário está vazio")
  void deveFalharQuandoEmailDestinatarioVazio() {
    MensagemDTO mensagem = MensagemDTO.builder().telefoneDestinatario("")
        .corpoMensagem("Test body").tipoCanal(TipoCanal.EMAIL)
        .statusMensagem(StatusMensagem.PENDENTE).build();

    assertThrows(ValidationException.class,
                 () -> estrategia.executar(mensagem));
  }

  @Test
  @DisplayName("Deve falhar quando corpo da mensagem está vazio")
  void deveFalharQuandoCorpoMensagemVazio() {
    MensagemDTO mensagem = MensagemDTO.builder()
        .telefoneDestinatario("test@example.com").corpoMensagem("")
        .tipoCanal(TipoCanal.EMAIL).statusMensagem(StatusMensagem.PENDENTE)
        .build();

    assertThrows(ValidationException.class,
                 () -> estrategia.executar(mensagem));
  }

  @Test
  @DisplayName("Deve falhar quando tipo canal é incompatível")
  void deveFalharQuandoTipoCanalIncompativel() {
    MensagemDTO mensagem = MensagemDTO.builder()
        .telefoneDestinatario("test@example.com").corpoMensagem("Test body")
        .tipoCanal(TipoCanal.WHATSAPP)
        .statusMensagem(StatusMensagem.PENDENTE).build();

    assertThrows(ValidationException.class,
                 () -> estrategia.executar(mensagem));
  }

  @Test
  @DisplayName("Deve processar resultado de envio com sucesso")
  void deveProcessarResultadoComSucesso() {
    MensagemDTO mensagem = criarMensagemValida();
    ResultadoEnvio resultado = ResultadoEnvio.sucesso("EMAIL_123456");

    when(mensagemProvider.enviar(any(MensagemDTO.class)))
        .thenReturn(resultado);

    MensagemDTO resultadoProcessado = estrategia.executar(mensagem);

    assertEquals("EMAIL_123456", resultadoProcessado.getIdExterno());
    assertEquals(StatusMensagem.ENVIADA,
                 resultadoProcessado.getStatusMensagem());
    assertNotNull(resultadoProcessado.getDataEnvio());
  }

  @Test
  @DisplayName("Deve processar resultado de envio com falha")
  void deveProcessarResultadoComFalha() {
    MensagemDTO mensagem = criarMensagemValida();
    ResultadoEnvio resultado = ResultadoEnvio.falha("Erro de conexão");

    when(mensagemProvider.enviar(any(MensagemDTO.class)))
        .thenReturn(resultado);

    MensagemDTO resultadoProcessado = estrategia.executar(mensagem);

    assertEquals(StatusMensagem.FALHA,
                 resultadoProcessado.getStatusMensagem());
    assertEquals("Erro de conexão", resultadoProcessado.getMotivoFalha());
  }

  @Test
  @DisplayName("Deve aceitar tipo canal EMAIL na validação")
  void deveAceitarTipoCanalEmail() {
    MensagemDTO mensagem = MensagemDTO.builder()
        .telefoneDestinatario("test@example.com").corpoMensagem("Test body")
        .tipoCanal(TipoCanal.EMAIL).statusMensagem(StatusMensagem.PENDENTE)
        .build();

    when(mensagemProvider.enviar(any(MensagemDTO.class)))
        .thenReturn(ResultadoEnvio.sucesso("EMAIL_123"));

    assertDoesNotThrow(() -> estrategia.executar(mensagem));
  }

  @Test
  @DisplayName("Deve aceitar tipo canal null na validação")
  void deveAceitarTipoCanalNull() {
    MensagemDTO mensagem = MensagemDTO.builder()
        .telefoneDestinatario("test@example.com").corpoMensagem("Test body")
        .tipoCanal(null).statusMensagem(StatusMensagem.PENDENTE).build();

    when(mensagemProvider.enviar(any(MensagemDTO.class)))
        .thenReturn(ResultadoEnvio.sucesso("EMAIL_123"));

    assertDoesNotThrow(() -> estrategia.executar(mensagem));
  }

  private MensagemDTO criarMensagemValida() {
    return MensagemDTO.builder().telefoneDestinatario("test@example.com")
        .nomeDestinatario("Test User").corpoMensagem("Test message body")
        .tipoCanal(TipoCanal.EMAIL).statusMensagem(StatusMensagem.PENDENTE)
        .build();
  }
}
