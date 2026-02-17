package com.ia.core.quartz.model.periodicidade;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Intervalo temporal para eventos.
 * <p>
 * Representa o período de um evento com suporte a datas e horários separados,
 * permitindo modelar eventos que:
 * <ul>
 * <li>Começam e terminam no mesmo dia</li>
 * <li>Passam para o próximo dia (ex: 8h do dia 12 até 18h do dia 13)</li>
 * <li>Têm horários específicos sem data (para recorrências)</li>
 * </ul>
 *
 * @author Israel Araújo
 */
@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class IntervaloTemporal
  implements Serializable {

  /** Serial UID */
  private static final long serialVersionUID = 1L;

  /**
   * Data de início do evento.
   * <p>
   * Equivalente ao parâmetro DTSTART da RFC 5545 (componente de data).
   */
  @Column(name = "start_date")
  private LocalDate startDate;

  /**
   * Hora de início do evento.
   * <p>
   * Equivalente ao parâmetro DTSTART da RFC 5545 (componente de hora).
   */
  @Column(name = "start_time")
  private LocalTime startTime;

  /**
   * Data de fim do evento.
   * <p>
   * Equivalente ao parâmetro DTEND da RFC 5545 (componente de data).
   * Se não informado, assume o mesmo dia de startDate.
   */
  @Column(name = "end_date")
  private LocalDate endDate;

  /**
   * Hora de fim do evento.
   * <p>
   * Equivalente ao parâmetro DTEND da RFC 5545 (componente de hora).
   */
  @Column(name = "end_time")
  private LocalTime endTime;

  /**
   * Construtor com data e hora.
   *
   * @param startDate data de início
   * @param startTime hora de início
   * @param endDate   data de fim
   * @param endTime   hora de fim
   */
  public IntervaloTemporal(LocalDate startDate, LocalTime startTime,
                          LocalDate endDate, LocalTime endTime) {
    this.startDate = startDate;
    this.startTime = startTime;
    this.endDate = endDate;
    this.endTime = endTime;
  }

  /**
   * Construtor para evento no mesmo dia.
   *
   * @param date  data do evento
   * @param startTime hora de início
   * @param endTime   hora de fim
   */
  public IntervaloTemporal(LocalDate date, LocalTime startTime,
                          LocalTime endTime) {
    this.startDate = date;
    this.startTime = startTime;
    this.endDate = date;
    this.endTime = endTime;
  }
}
