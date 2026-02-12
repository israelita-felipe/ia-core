package com.ia.core.service.event;

import org.springframework.context.ApplicationEvent;

import com.ia.core.service.dto.DTO;

/**
 * Evento de domínio genérico para serviços.
 * <p>
 * Esta classe representa um evento que pode ser publicado por qualquer
 * serviço do sistema quando operações de negócio significativas ocorrem.
 * O evento carrega o DTO afetado e o tipo da operação realizada.
 * </p>
 * 
 * @param <D> Tipo do DTO que gerou o evento
 * @author Israel Araújo
 */
public class BaseServiceEvent<D extends DTO<?>> extends ApplicationEvent {

  private static final long serialVersionUID = 1L;

  private final D dto;
  private final EventType eventType;
  private final Object data;

  /**
   * Construtor para evento sem dados adicionais.
   * 
   * @param source    Origem do evento (normalmente o serviço)
   * @param dto       DTO afetado pela operação
   * @param eventType Tipo de evento
   */
  public BaseServiceEvent(Object source, D dto, EventType eventType) {
    super(source);
    this.dto = dto;
    this.eventType = eventType;
    this.data = null;
  }

  /**
   * Construtor para evento com dados adicionais.
   * 
   * @param source    Origem do evento
   * @param dto       DTO afetado
   * @param eventType Tipo de evento
   * @param data      Dados adicionais do evento
   */
  public BaseServiceEvent(Object source, D dto, EventType eventType, Object data) {
    super(source);
    this.dto = dto;
    this.eventType = eventType;
    this.data = data;
  }

  /**
   * Retorna o DTO afetado pelo evento.
   * 
   * @return DTO
   */
  public D getDto() {
    return dto;
  }

  /**
   * Retorna o tipo do evento.
   * 
   * @return Tipo do evento
   */
  public EventType getEventType() {
    return eventType;
  }

  /**
   * Retorna dados adicionais do evento, se houver.
   * 
   * @return Dados adicionais ou null
   */
  public Object getData() {
    return data;
  }

  /**
   * Verifica se o evento possui dados adicionais.
   * 
   * @return true se possui dados
   */
  public boolean hasData() {
    return data != null;
  }

  @Override
  public String toString() {
    return String.format("BaseServiceEvent[source=%s, dto=%s, type=%s, data=%s]",
        getSource(), dto, eventType, data);
  }
}
