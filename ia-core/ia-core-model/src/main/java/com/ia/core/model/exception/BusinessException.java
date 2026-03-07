package com.ia.core.model.exception;

/**
 * Exceção lançada quando ocorre uma violação de regra de negócio.
 *
 * <p>Representa erros que indicam que uma operação não pode ser executada
 * porque violaria uma regra de negócio estabelecida. Esta exceção é diferente
 * de {@link ValidationException}, pois refere-se a regras de negócio de mais
 * alto nível, não a simplesmente validação de campos.
 *
 * <p><b>Exemplos de uso:</b></p>
 * <ul>
 *   <li>Tentar cancelar um pedido já enviado</li>
 *   <li>Tentar aplicar um desconto maior que o permitido</li>
 *   <li>Tentar excluir um recurso com dependências</li>
 * </ul>
 *
 * <p><b>Exemplo de lançamento:</b></p>
 * {@code
 * if (pedido.getStatus().equals(StatusPedido.ENVIADO)) {
 *     throw new BusinessException("Não é possível cancelar um pedido já enviado");
 * }
 * }
 *
 * @see DomainException
 * @see ValidationException
 * @since 1.0.0
 * @author Israel Araújo
 */
public class BusinessException
  extends DomainException {

  private static final long serialVersionUID = 1L;

  private static final String DEFAULT_MESSAGE = "Violação de regra de negócio";
  private static final String DEFAULT_ERROR_CODE = "BUSINESS_RULE_VIOLATION";

  /**
   * Construtor com mensagem de erro.
   *
   * <p>Usa o código de erro padrão {@code "BUSINESS_RULE_VIOLATION"}.
   *
   * @param message mensagem de erro descritiva (não pode ser {@code null} ou vazia)
   */
  public BusinessException(String message) {
    super(DEFAULT_ERROR_CODE, message);
  }

  /**
   * Construtor com código de erro e mensagem.
   *
   * <p>Permite especificar um código de erro mais específico para
   * tratamento diferenciado no frontend ou em handlers.
   *
   * @param errorCode código de erro personalizado (não pode ser {@code null} ou vazio)
   * @param message   mensagem de erro descritiva (não pode ser {@code null} ou vazia)
   */
  public BusinessException(String errorCode, String message) {
    super(errorCode, message);
  }

  /**
   * Construtor com código de erro, mensagem e causa.
   *
   * <p>Usado quando a violação de regra de negócio é causada por outra exceção,
   * permitindo o encadeamento para debugging.
   *
   * @param errorCode código de erro personalizado (não pode ser {@code null} ou vazio)
   * @param message   mensagem de erro descritiva (não pode ser {@code null} ou vazia)
   * @param cause     causa original da violação
   */
  public BusinessException(String errorCode, String message, Throwable cause) {
    super(errorCode, message, cause);
  }
}
