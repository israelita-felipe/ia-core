package com.ia.core.quartz.model.periodicidade;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * Enum representando as frequências de recorrência conforme RFC 5545.
 * <p>
 * Mantém compatibilidade com nomes anteriores em português, mapeando cada
 * valor para seu correspondente na especificação RFC 5545 (iCalendar).
 * <p>
 * Esta enumeração segue o padrão Value Object do DDD, sendo imutável e
 * representando um conceito atômico do domínio de periodicidade.
 *
 * @author Israel Araújo
 * @since 1.0.0
 * @see Recorrencia
 * @see Periodicidade
 */
public enum Frequencia {
  /**
   * Frequência diária - evento que se repete todos os dias.
   * <p>
   * Equivalente a {@code FREQ=DAILY} na RFC 5545.
   *
   * @see <a href="https://tools.ietf.org/html/rfc5545#section-3.3.10">RFC 5545 Section 3.3.10</a>
   */
  DIARIAMENTE("DAILY", 1),

  /**
   * Frequência semanal - evento que se repete em intervalos de semanas.
   * <p>
   * Equivalente a {@code FREQ=WEEKLY} na RFC 5545.
   *
   * @see <a href="https://tools.ietf.org/html/rfc5545#section-3.3.10">RFC 5545 Section 3.3.10</a>
   */
  SEMANALMENTE("WEEKLY", 2),

  /**
   * Frequência mensal - evento que se repete em intervalos de meses.
   * <p>
   * Equivalente a {@code FREQ=MONTHLY} na RFC 5545.
   *
   * @see <a href="https://tools.ietf.org/html/rfc5545#section-3.3.10">RFC 5545 Section 3.3.10</a>
   */
  MENSALMENTE("MONTHLY", 3),

  /**
   * Frequência anual - evento que se repete em intervalos de anos.
   * <p>
   * Equivalente a {@code FREQ=YEARLY} na RFC 5545.
   *
   * @see <a href="https://tools.ietf.org/html/rfc5545#section-3.3.10">RFC 5545 Section 3.3.10</a>
   */
  ANUALMENTE("YEARLY", 4);

  /**
   * Nome oficial da frequência conforme RFC 5545.
   * <p>
   * Este valor é utilizado para serialização em formatos compatíveis com
   * a especificação iCalendar (RFC 5545).
   */
  private final String rfcName;

  /**
   * Código numérico para compatibilidade com sistemas legados.
   * <p>
   * Utilizado em situações onde é necessário armazenar a frequência
   * como um valor numérico no banco de dados.
   */
  private final int codigo;

  /**
   * Construtor privado para inicializar os campos da frequência.
   *
   * @param rfcName Nome oficial da frequência conforme RFC 5545 (não pode ser {@code null})
   * @param codigo  Código numérico para compatibilidade com sistemas legados
   * @since 1.0.0
   */
  private Frequencia(String rfcName, int codigo) {
    this.rfcName = rfcName;
    this.codigo = codigo;
  }

  /**
   * Obtém o nome oficial da frequência conforme RFC 5545.
   * <p>
   * Este valor é utilizado para serialização em formatos compatíveis com
   * a especificação iCalendar (RFC 5545), como na geração de strings RRULE.
   *
   * @return Nome RFC 5545 da frequência (ex: "DAILY", "WEEKLY", "MONTHLY", "YEARLY")
   * @since 1.0.0
   * @see #fromRfcName(String)
   */
  public String getRfcName() {
    return rfcName;
  }

  /**
   * Obtém o código numérico da frequência.
   * <p>
   * O código é utilizado para compatibilidade com sistemas legados que
   * armazenam a frequência como um valor numérico no banco de dados.
   *
   * @return Código numérico da frequência (1-4)
   * @since 1.0.0
   * @see #of(int)
   */
  public int getCodigo() {
    return codigo;
  }

  /**
   * Obtém uma frequência a partir do seu código numérico.
   * <p>
   * Utilizado para reconstrução de objetos {@link Frequencia} a partir
   * de dados armazenados em sistemas legados que utilizam códigos numéricos.
   *
   * @param codigo Código numérico da frequência (1-4)
   * @return Instância de {@link Frequencia} correspondente ao código, ou {@code null} se não encontrada
   * @since 1.0.0
   * @see #getCodigo()
   */
  public static Frequencia of(int codigo) {
    return Stream.of(values())
        .filter(tipo -> Objects.equals(codigo, tipo.codigo)).findFirst()
        .orElse(null);
  }

  /**
   * Converte um nome RFC 5545 para o enum correspondente.
   * <p>
   * Utilizado para parsing de strings RRULE geradas por sistemas externos
   * que seguem a especificação RFC 5545.
   *
   * @param rfcName Nome RFC da frequência (ex: "DAILY", "WEEKLY", "MONTHLY", "YEARLY").
   *                Pode ser {@code null}, caso em que retorna {@code null}
   * @return Enum correspondente ao nome RFC, ou {@code null} se o nome for inválido ou {@code null}
   * @since 1.0.0
   * @see #getRfcName()
   */
  public static Frequencia fromRfcName(String rfcName) {
    if (rfcName == null) {
      return null;
    }
    for (Frequencia f : values()) {
      if (f.rfcName.equalsIgnoreCase(rfcName)) {
        return f;
      }
    }
    return null;
  }
}
