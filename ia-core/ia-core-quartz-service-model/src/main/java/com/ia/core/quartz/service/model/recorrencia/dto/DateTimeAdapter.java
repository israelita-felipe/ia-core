package com.ia.core.quartz.service.model.recorrencia.dto;

import com.ia.core.quartz.service.model.periodicidade.dto.IntervaloTemporalDTO;
import org.dmfs.rfc5545.DateTime;

import java.time.*;
/**
 * Adaptador para conversão entre Java DateTime e RFC 5545 DateTime.
 * <p>
 * Fornece métodos utilitários para transformar objetos Java {@link LocalDate},
 * {@link LocalTime}, {@link ZonedDateTime} em objetos {@link DateTime} da
 * biblioteca RFC 5545 (iCalendar) e vice-versa.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
public class DateTimeAdapter {

  /**
   * Converte data, hora e zona para DateTime UTC.
   *
   * @param date a data local
   * @param time a hora local
   * @param zone a zona horária
   * @return DateTime em UTC
   */
  public static DateTime toUtcDateTime(LocalDate date, LocalTime time,
                                       ZoneId zone) {
    ZonedDateTime zdt = ZonedDateTime.of(date, time, zone);
    return new DateTime(DateTime.UTC, zdt.getYear(),
                        zdt.getMonthValue() - 1, zdt.getDayOfMonth(),
                        zdt.getHour(), zdt.getMinute(), zdt.getSecond());
  }

  /**
   * Converte ZonedDateTime para DateTime UTC.
   *
   * @param zdt o ZonedDateTime a ser convertido
   * @return DateTime em UTC
   */
  public static DateTime toUtcDateTime(ZonedDateTime zdt) {
    ZonedDateTime utc = zdt.withZoneSameInstant(ZoneId.of("UTC"));
    return new DateTime(DateTime.UTC, utc.getYear(),
                        utc.getMonthValue() - 1, utc.getDayOfMonth(),
                        utc.getHour(), utc.getMinute(), utc.getSecond());
  }

  /**
   * Converte data para DateTime all-day UTC.
   *
   * @param date a data a ser convertida
   * @return DateTime all-day em UTC
   */
  public static DateTime toAllDayUtcDateTime(LocalDate date) {
    return new DateTime(DateTime.UTC, date.getYear(),
                        date.getMonthValue() - 1, date.getDayOfMonth(), 0,
                        0, 0);
  }

  /**
   * Converte DateTime UTC para ZonedDateTime na zona alvo.
   *
   * @param dt o DateTime UTC
   * @param targetZone a zona horária alvo
   * @return ZonedDateTime na zona especificada
   */
  public static ZonedDateTime fromUtcDateTime(DateTime dt,
                                              ZoneId targetZone) {
    long millis = dt.getTimestamp();
    Instant instant = Instant.ofEpochMilli(millis);
    LocalDateTime localDateTime = instant.atZone(ZoneOffset.UTC)
        .toLocalDateTime();
    return ZonedDateTime.of(localDateTime, targetZone);
  }

  /**
   * Converte DateTime UTC e duração para IntervaloTemporalDTO.
   *
   * @param startUtc o DateTime de início em UTC
   * @param duration a duração do intervalo
   * @param zone a zona horária
   * @return IntervaloTemporalDTO configurado
   */
  public static IntervaloTemporalDTO toIntervaloTemporalDTO(DateTime startUtc,
                                                            Duration duration,
                                                            ZoneId zone) {
    ZonedDateTime start = fromUtcDateTime(startUtc, zone);
    ZonedDateTime end = start.plus(duration);
    return IntervaloTemporalDTO.builder().startDate(start.toLocalDate())
        .startTime(start.toLocalTime()).endDate(end.toLocalDate())
        .endTime(end.toLocalTime()).build();
  }

  /**
   * Verifica se duas DateTimes representam o mesmo dia all-day.
   * <p>
   * Compara apenas ano, mês e dia, ignorando hora e minuto.
   *
   * @param dt1 primeira DateTime
   * @param dt2 segunda DateTime
   * @return true se representarem o mesmo dia, false caso contrário
   */
  public static boolean isSameAllDay(DateTime dt1, DateTime dt2) {
    return dt1.getYear() == dt2.getYear()
        && dt1.getMonth() == dt2.getMonth()
        && dt1.getDayOfMonth() == dt2.getDayOfMonth();
  }
}
