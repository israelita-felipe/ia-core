package com.ia.core.model.exception;

/**
 * Exceção lançada quando um recurso solicitado não é encontrado.
 *
 * @author Israel Araújo
 */
public class ResourceNotFoundException
  extends DomainException {

  private static final long serialVersionUID = 1L;

  private static final String DEFAULT_MESSAGE = "Recurso não encontrado";
  private static final String DEFAULT_ERROR_CODE = "RESOURCE_NOT_FOUND";

  /**
   * Identificador do recurso não encontrado.
   */
  private final String resourceId;

  /**
   * Tipo do recurso não encontrado.
   */
  private final String resourceType;

  /**
   * Construtor com tipo e ID do recurso.
   *
   * @param resourceType tipo do recurso
   * @param resourceId   identificador do recurso
   */
  public ResourceNotFoundException(String resourceType, String resourceId) {
    super(DEFAULT_ERROR_CODE,
          String.format("%s: %s com ID '%s'", DEFAULT_MESSAGE, resourceType, resourceId));
    this.resourceType = resourceType;
    this.resourceId = resourceId;
  }

  /**
   * Construtor com mensagem personalizada.
   *
   * @param message mensagem de erro
   */
  public ResourceNotFoundException(String message) {
    super(DEFAULT_ERROR_CODE, message);
    this.resourceId = null;
    this.resourceType = null;
  }

  /**
   * @return identificador do recurso não encontrado
   */
  public String getResourceId() {
    return resourceId;
  }

  /**
   * @return tipo do recurso não encontrado
   */
  public String getResourceType() {
    return resourceType;
  }
}
