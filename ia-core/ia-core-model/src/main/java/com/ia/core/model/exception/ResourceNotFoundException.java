package com.ia.core.model.exception;

/**
 * Exceção lançada quando um recurso solicitado não é encontrado no sistema.
 *
 * <p>Usada para indicar que uma operação tentou acessar um recurso que não existe
 * ou foi removido do banco de dados. Diferencia-se de {@link ValidationException}
 * pois refere-se especificamente a entidades de domínio, não a dados de entrada inválidos.
 *
 * <p><b>Exemplos de uso:</b></p>
 * <ul>
 *   <li>Buscar um usuário por ID que não existe</li>
 *   <li>Acessar um relatório que foi deletado</li>
 *   <li>Atualizar uma entidade que foi removida</li>
 * </ul>
 *
 * <p><b>Exemplo de lançamento:</b></p>
 * {@code
 * public Usuario buscarPorId(Long id) {
 *     return usuarioRepository.findById(id)
 *         .orElseThrow(() -> new ResourceNotFoundException("Usuario", id.toString()));
 * }
 * }
 *
 * @see DomainException
 * @see ValidationException
 * @since 1.0.0
 * @author Israel Araújo
 */
public class ResourceNotFoundException
  extends DomainException {

  private static final long serialVersionUID = 1L;

  private static final String DEFAULT_MESSAGE = "Recurso não encontrado";
  private static final String DEFAULT_ERROR_CODE = "RESOURCE_NOT_FOUND";

  /**
   * Armazena o identificador do recurso que não foi encontrado.
   * Pode ser {@code null} se o construtor de mensagem personalizada for usado.
   */
  private final String resourceId;

  /**
   * Armazena o tipo do recurso que não foi encontrado.
   * Exemplo: "Usuario", "Pedido", "Produto"
   */
  private final String resourceType;

  /**
   * Construtor com tipo e ID do recurso.
   *
   * <p>Gera automaticamente uma mensagem descritiva no formato:
   * {@code "Recurso não encontrado: [tipo] com ID '[id]'}"
   *
   * @param resourceType tipo do recurso (não pode ser {@code null}, ex: "Usuario", "Pedido")
   * @param resourceId   identificador do recurso (pode ser {@code null})
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
   * <p>Usado quando é necessário fornecer uma mensagem mais específica
   * que o formato padrão gerado pelo outro construtor.
   *
   * @param message mensagem de erro personalizada
   */
  public ResourceNotFoundException(String message) {
    super(DEFAULT_ERROR_CODE, message);
    this.resourceId = null;
    this.resourceType = null;
  }

  /**
   * Obtém o identificador do recurso que não foi encontrado.
   *
   * @return o identificador do recurso, ou {@code null} se não disponível
   */
  public String getResourceId() {
    return resourceId;
  }

  /**
   * Obtém o tipo do recurso que não foi encontrado.
   *
   * @return o tipo do recurso (ex: "Usuario", "Pedido"), ou {@code null} se não disponível
   */
  public String getResourceType() {
    return resourceType;
  }
}
