# Periodicidade RFC 5545 - Documentação de Uso

## Visão Geral

Este documento descreve como utilizar as classes de periodicidade para criar e gerenciar eventos recorrentes conforme a especificação RFC 5545 (iCalendar).

## Arquitetura

### Entidades Principais

| Classe | Descrição |
|--------|-----------|
| `Periodicidade` | Entidade JPA que representa um evento periódico |
| `PeriodicidadeDTO` | DTO para transferência de dados |
| `IntervaloTemporal` | Embeddable para período (data/hora início e fim) |
| `Recorrencia` | Value Object com regra de recorrência |
| `ExclusaoRecorrencia` | Value Object para regras de exclusão (EXRULE) |
| `Frequencia` | Enum com frequências suportadas |

## Uso Básico

### Criando um Evento Diário

```java
// Criar intervalo temporal
IntervaloTemporalDTO intervalo = IntervaloTemporalDTO.builder()
    .startDate(LocalDate.now())
    .startTime(LocalTime.of(9, 0))
    .endDate(LocalDate.now())
    .endTime(LocalTime.of(10, 0))
    .build();

// Criar regra de recorrência diária
RecorrenciaDTO regra = RecorrenciaDTO.builder()
    .frequency(Frequencia.DAILY)
    .countLimit(10) // Limite de 10 ocorrências
    .build();

// Criar periodicidade
PeriodicidadeDTO periodicidade = PeriodicidadeDTO.builder()
    .intervaloBase(intervalo)
    .regra(regra)
    .zoneId("America/Recife")
    .build();
```

### Criando um Evento Semanal

```java
Set<DayOfWeek> diasSemana = new HashSet<>();
diasSemana.add(DayOfWeek.MONDAY);
diasSemana.add(DayOfWeek.WEDNESDAY);
diasSemana.add(DayOfWeek.FRIDAY);

RecorrenciaDTO regra = RecorrenciaDTO.builder()
    .frequency(Frequencia.WEEKLY)
    .byDay(diasSemana)
    .intervalValue(1)
    .build();
```

### Criando um Evento Mensal

```java
Set<Integer> diasMes = new HashSet<>();
diasMes.add(1);   // Primeiro dia do mês
diasMes.add(15);  // Dia 15 do mês

RecorrenciaDTO regra = RecorrenciaDTO.builder()
    .frequency(Frequencia.MONTHLY)
    .byMonthDay(diasMes)
    .build();
```

### Usando o Calculador de Ocorrências

```java
OccurrenceCalculator calculator = new OccurrenceCalculator();

// Obter próxima ocorrência
ZonedDateTime agora = ZonedDateTime.now();
Optional<IntervaloTemporalDTO> proxima = calculator.nextOccurrence(
    periodicidade, agora);

if (proxima.isPresent()) {
    System.out.println("Próxima ocorrência: " + proxima.get().getStartDateTime());
}

// Gerar múltiplas ocorrências
List<IntervaloTemporalDTO> medicoes = calculator.generateOccurrences(
    periodicidade, agora, 5);
```

## Parâmetros RFC 5545 Suportados

| Parâmetro | Descrição | Exemplo |
|-----------|-----------|---------|
| FREQ | Frequência base | DAILY, WEEKLY, MONTHLY, YEARLY |
| INTERVAL | Intervalo multiplicador | 2 (a cada 2 semanas) |
| UNTIL | Data limite | 2025-12-31 |
| COUNT | Número de ocorrências | 10 |
| BYMONTH | Mês do ano | 1, 6, 12 (Jan, Jun, Dec) |
| BYMONTHDAY | Dia do mês | 1, 15, -1 (último dia) |
| BYDAY | Dia da semana | MO, TU, WE, TH, FR |
| BYSETPOS | Posição no conjunto | 1, -1 |
| WKST | Início da semana | SU (domingo) |
| BYYEARDAY | Dia do ano | 1, 100, 200 |
| BYWEEKNO | Semana do ano | 1, 52 |
| BYHOUR | Hora do dia | 9, 12, 18 |
| BYMINUTE | Minuto da hora | 0, 30 |
| BYSECOND | Segundo do minuto | 0, 30 |

## Serialização iCalendar

### Convertendo para RRULE

```java
// Serializar para formato RRULE
String rrule = ICalendarSerializer.toRRule(regra);
System.out.println(rrule);
// Output: FREQ=WEEKLY;BYDAY=MO,WE,FR;COUNT=10
```

### Validando RRULE

```java
// Validar formato RRULE
boolean valido = RRuleValidator.isValidRRule(rrule);
if (!valido) {
    List<String> erros = RRuleValidator.validateRRule(rrule);
    erros.forEach(System.out::println);
}
```

## Exportando para iCalendar

```java
// Criar evento para exportação
PeriodicidadeDTO periodicidade = ...;

// Exportar para string ICS
String ics = ICalendarSerializer.toICS(periodicidade);

// Exportar para arquivo
ICalendarSerializer.exportToICSFile(periodicidade, "/path/to/event.ics");
```

## Integração com Quartz

A classe `PeriodicidadeTrigger` integra eventos periódicos com o scheduler Quartz:

```java
PeriodicidadeTrigger trigger = PeriodicidadeTrigger.builder()
    .withIdentity("evento-1", "grupo-eventos")
    .usingJobData("periodicidadeId", periodicidade.getId())
    .build();
```

## Configuração de Banco de Dados

As tabelas são criadas no schema QUARTZ com o prefixo QRTZ_:

- `QUARTZ.QRTZ_PERIODICIDADE` - Tabela principal
- `QUARTZ.QRTZ_PERIODICIDADE_REGRA` - Regras de recorrência
- `QUARTZ.QRTZ_PERIODICIDADE_EXCLUSAO_REGRA` - Regras de exclusão (EXRULE)
- `QUARTZ.QRTZ_PERIODICIDADE_DIA_ANO` - BYYEARDAY
- `QUARTZ.QRTZ_PERIODICIDADE_SEMANA_ANO` - BYWEEKNO

## Boas Práticas

1. **Sempre defina timezone**: O campo `zoneId` é obrigatório para cálculos corretos
2. **Use COUNT ou UNTIL**: Sempre defina um limite para evitar recorrências infinitas
3. **Valide antes de persistir**: Use `RRuleValidator` para validar RRULEs
4. **Teste os cálculos**: Use os testes unitários para validar cenários complexos

## Exemplos Completos

### Reunião Semanal às Segundas e Quartas

```java
IntervaloTemporalDTO intervalo = IntervaloTemporalDTO.builder()
    .startDate(LocalDate.of(2024, 1, 1))
    .startTime(LocalTime.of(14, 0))
    .endDate(LocalDate.of(2024, 1, 1))
    .endTime(LocalTime.of(15, 0))
    .build();

Set<DayOfWeek> dias = new HashSet<>();
dias.add(DayOfWeek.MONDAY);
dias.add(DayOfWeek.WEDNESDAY);

RecorrenciaDTO regra = RecorrenciaDTO.builder()
    .frequency(Frequencia.WEEKLY)
    .byDay(dias)
    .intervalValue(1)
    .untilDate(LocalDate.of(2024, 12, 31))
    .build();

PeriodicidadeDTO periodicidade = PeriodicidadeDTO.builder()
    .intervaloBase(intervalo)
    .regra(regra)
    .zoneId("America/Recife")
    .ativo(true)
    .build();
```

### Aula aos Sábados a cada 2 semanas

```java
RecorrenciaDTO regra = RecorrenciaDTO.builder()
    .frequency(Frequencia.WEEKLY)
    .byDay(Set.of(DayOfWeek.SATURDAY))
    .intervalValue(2)
    .bySetPosition(1)  // Primeiro sábado do período
    .countLimit(20)
    .build();
```
