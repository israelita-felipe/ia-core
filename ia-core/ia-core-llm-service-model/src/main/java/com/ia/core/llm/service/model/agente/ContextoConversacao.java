package com.ia.core.llm.service.model.agente;

import com.ia.core.llm.service.model.ontologia.OntologiaDTO;
import com.ia.core.service.dto.DTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * DTO para contexto de conversação com agente guiado por ontologia.
 * <p>
 * Mantém o estado da conversa, ontologia incremental e histórico de interações.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class ContextoConversacao implements DTO<String> {

  @Override
  public DTO<String> cloneObject() {
    return toBuilder()
        .historico(new ArrayList<>(historico))
        .build();
  }

  /**
   * ID único da sessão de conversação.
   */
  private String sessionId;

  /**
   * ID do usuário.
   */
  private String userId;

  /**
   * Domínio da conversação (ex: biologia, biblioteca, medicina).
   */
  private String dominio;

  /**
   * Ontologia construída incrementalmente durante a conversa.
   */
  private OntologiaDTO ontologia;

  /**
   * Histórico de turnos da conversação.
   */
  @Builder.Default
  private List<TurnoConversacao> historico = new ArrayList<>();

  /**
   * Data e hora de início da sessão.
   */
  private LocalDateTime dataInicio;

  /**
   * Data e hora da última atividade.
   */
  private LocalDateTime ultimaAtividade;

  /**
   * Número total de axiomas extraídos até o momento.
   */
  private int totalAxiomasExtraidos;

  /**
   * Indica se a ontologia atual está consistente.
   */
  private boolean ontologiaConsistente;

  /**
   * Número de inconsistências corrigidas durante a conversa.
   */
  private int inconsistenciasCorrigidas;

  /**
   * Constantes dos campos do DTO para uso type-safe.
   */
  @SuppressWarnings("javadoc")
  public static class CAMPOS {
    public static final String SESSION_ID = "sessionId";
    public static final String USER_ID = "userId";
    public static final String DOMINIO = "dominio";
    public static final String ONTOLOGIA = "ontologia";
    public static final String HISTORICO = "historico";
    public static final String DATA_INICIO = "dataInicio";
    public static final String ULTIMA_ATIVIDADE = "ultimaAtividade";
    public static final String TOTAL_AXIOMAS_EXTRAIDOS = "totalAxiomasExtraidos";
    public static final String ONTOLOGIA_CONSISTENTE = "ontologiaConsistente";
    public static final String INCONSISTENCIAS_CORRIGIDAS = "inconsistenciasCorrigidas";

    public static java.util.Set<String> values() {
      return java.util.Set.of(
          SESSION_ID, USER_ID, DOMINIO, ONTOLOGIA, HISTORICO,
          DATA_INICIO, ULTIMA_ATIVIDADE, TOTAL_AXIOMAS_EXTRAIDOS,
          ONTOLOGIA_CONSISTENTE, INCONSISTENCIAS_CORRIGIDAS
      );
    }
  }
}
