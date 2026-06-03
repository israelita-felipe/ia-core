package com.ia.core.llm.service.model.agente;

import com.ia.core.owl.service.model.axioma.AxiomaDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * DTO para representar um turno de conversação.
 * <p>
 * Contém a mensagem do usuário, resposta do agente e axiomas extraídos.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TurnoConversacao {

  /**
   * Número sequencial do turno.
   */
  private int numeroTurno;

  /**
   * Mensagem do usuário.
   */
  private String mensagemUsuario;

  /**
   * Resposta do agente.
   */
  private String respostaAgente;

  /**
   * Axiomas extraídos neste turno.
   */
  @Builder.Default
  private List<AxiomaDTO> axiomasExtraidos = new ArrayList<>();

  /**
   * Construtor OWL identificado (SubClassOf, ObjectPropertyDomain, etc).
   */
  private String construtorIdentificado;

  /**
   * Timestamp do turno.
   */
  private LocalDateTime timestamp;

  /**
   * Indica se a validação foi bem-sucedida.
   */
  private boolean validacaoSucesso;

  /**
   * Número de iterações usadas para validação (se houve loop).
   */
  private int iteracoesValidacao;
}
