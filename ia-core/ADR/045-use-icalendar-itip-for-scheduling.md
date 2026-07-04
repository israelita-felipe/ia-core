# ADR 045: Use iCalendar/iTIP for Scheduling

## Status
Accepted

## Context
Os módulos de agendamento (quartz) precisam seguir padrões estabelecidos para representação e troca de dados de calendário e agendamento. Precisamos garantir conformidade com as especificações iCalendar e iTIP para interoperabilidade com sistemas de calendário padrão.

## Decision
Vamos utilizar iCalendar (RFC 5545) e iTIP (RFC 5546) como especificações vigentes para todos os módulos de agendamento no sistema, incluindo:
- Representação de eventos, tarefas e diários em formato iCalendar
- Troca de solicitações de agendamento usando iTIP (invocações, respostas, cancelamentos)
- Suporte a propriedades essenciais como DTSTART, DTEND, SUMMARY, DESCRIPTION, UID, etc.
- Implementação de métodos iTIP como REQUEST, REPLY, CANCEL, ADD, etc.
- Tratamento de RFC 2445 e RFC 2446 como referências históricas, preservando RFC 5545/RFC 5546 como padrão primário

## Consequences
### Positivos
- Interoperabilidade com sistemas de calendário padrão (Google Calendar, Outlook, Apple Calendar, etc.)
- Formato amplamente adotado e bem estabelecido
- Suporte a recursos complexos de recorrência, fusos horários e participantes
- Facilidade de integração com ferramentas de terceiros

### Negativos
- Complexidade adicional no parsing e geração de dados iCalendar
- Verbosidade do formato texto em comparação a alternativas binárias
- Necessidade de tratamento cuidadoso de fusos horários e recorrência

## Implementation Plan
1. Utilizar biblioteca iCalendar estabelecida (como iCal4j) para parsing e geração
2. Mapear entidades de agendamento para propriedades iCalendar padrão
3. Implementar métodos iTIP para troca de solicitações de agendamento
4. Suporte a recorrência conforme RFC 5545 (RRULE, EXDATE, etc.)
5. Tratamento adequado de fusos horários usando TZID e VTIMEZONE
6. Validação de dados iCalendar de entrada e saída

## Related Documents
- REFACTORING.md (seção 8 sobre padrões RFC relevantes)
- RFC 5545: Internet Calendaring and Scheduling Core Object Specification (iCalendar)
- RFC 5546: iCalendar Transport-Independent Interoperability Protocol (iTIP)
- RFC 2445, RFC 2446 (versões antigas, para referência histórica)
