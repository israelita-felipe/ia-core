package com.ia.core.service.event;

/**
 * Interface para tipos de eventos de domínio.
 * <p>
 * Esta interface permite que serviços específicos definam seus próprios
 * tipos de eventos mantendo compatibilidade com o sistema genérico.
 * </p>
 * 
 * @author Israel Araújo
 */
public interface EventType {

  /**
   * Tipo padrão para evento de criação
   */
  EventType CRIADA = () -> "CRIADA";

  /**
   * Tipo padrão para evento de atualização
   */
  EventType ATUALIZADA = () -> "ATUALIZADA";

  /**
   * Tipo padrão para evento de exclusão
   */
  EventType EXCLUIDA = () -> "EXCLUIDA";

  /**
   * Retorna o nome do tipo de evento.
   * 
   * @return Nome do tipo
   */
  String name();
}
