package com.ia.core.quartz.model.periodicidade;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * Enum representando as frequências de recorrência conforme RFC 5545.
 * Mantém compatibilidade com nomes anteriores em português.
 *
 * @author Israel Araújo
 */
public enum Frequencia {
  // Nomes em português (para compatibilidade)
  DIARIAMENTE("DAILY", 1),
  SEMANALMENTE("WEEKLY", 2),
  MENSALMENTE("MONTHLY", 3),
  ANUALMENTE("YEARLY", 4),

  // Nomes RFC 5545 (recomendados para novos desenvolvimentos)
  DAILY("DAILY", 1),
  WEEKLY("WEEKLY", 2),
  MONTHLY("MONTHLY", 3),
  YEARLY("YEARLY", 4);

  @jakarta.persistence.Transient
  private final String rfcName;

  @jakarta.persistence.Transient
  private final int codigo;

  /**
   * @param rfcName Nome RFC 5545 da frequência
   * @param codigo   Código numérico para compatibilidade
   */
  private Frequencia(String rfcName, int codigo) {
    this.rfcName = rfcName;
    this.codigo = codigo;
  }

  /**
   * @return Nome RFC 5545 da frequência
   */
  public String getRfcName() {
    return rfcName;
  }

  /**
   * @return {@link #codigo}
   */
  public int getCodigo() {
    return codigo;
  }

  /**
   * @param codigo Código numérico
   * @return Frequencia correspondente
   */
  public static Frequencia of(int codigo) {
    return Stream.of(values())
        .filter(tipo -> Objects.equals(codigo, tipo.codigo)).findFirst()
        .orElse(null);
  }

  /**
   * Converte nome RFC 5545 para enum.
   *
   * @param rfcName Nome RFC (ex: "DAILY", "WEEKLY")
   * @return Enum correspondente
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
