package com.ia.core.quartz.service.model.recorrencia.dto;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import org.dmfs.rfc5545.DateTime;

import com.ia.core.quartz.service.model.periodicidade.dto.IntervaloTemporalDTO;

public class DateTimeAdapter {

  public static DateTime toUtcDateTime(LocalDate date, LocalTime time,
                                       ZoneId zone) {
    ZonedDateTime zdt = ZonedDateTime.of(date, time, zone);
    return new DateTime(DateTime.UTC, zdt.getYear(),
                        zdt.getMonthValue() - 1, zdt.getDayOfMonth(),
                        zdt.getHour(), zdt.getMinute(), zdt.getSecond());
  }

  public static DateTime toUtcDateTime(ZonedDateTime zdt) {
    ZonedDateTime utc = zdt.withZoneSameInstant(ZoneId.of("UTC"));
    return new DateTime(DateTime.UTC, utc.getYear(),
                        utc.getMonthValue() - 1, utc.getDayOfMonth(),
                        utc.getHour(), utc.getMinute(), utc.getSecond());
  }

  public static DateTime toAllDayUtcDateTime(LocalDate date) {
    return new DateTime(DateTime.UTC, date.getYear(),
                        date.getMonthValue() - 1, date.getDayOfMonth(), 0,
                        0, 0);
  }

  public static ZonedDateTime fromUtcDateTime(DateTime dt,
                                              ZoneId targetZone) {
    long millis = dt.getTimestamp();
    Instant instant = Instant.ofEpochMilli(millis);
    LocalDateTime localDateTime = instant.atZone(ZoneOffset.UTC)
        .toLocalDateTime();
    return ZonedDateTime.of(localDateTime, targetZone);
  }

  public static IntervaloTemporalDTO toIntervaloTemporalDTO(DateTime startUtc,
                                                            Duration duration,
                                                            ZoneId zone) {
    ZonedDateTime start = fromUtcDateTime(startUtc, zone);
    ZonedDateTime end = start.plus(duration);
    return IntervaloTemporalDTO.builder().startDate(start.toLocalDate())
        .startTime(start.toLocalTime()).endDate(end.toLocalDate())
        .endTime(end.toLocalTime()).build();
  }

  // Para verificar se duas DateTime representam o mesmo dia all-day (ignorando
  // hora)
  public static boolean isSameAllDay(DateTime dt1, DateTime dt2) {
    return dt1.getYear() == dt2.getYear()
        && dt1.getMonth() == dt2.getMonth()
        && dt1.getDayOfMonth() == dt2.getDayOfMonth();
  }
}
