package com.ia.core.model.exception;

/**
 * Exceção base abstrata para todas as exceções do domínio da aplicação.
 *
 * <p>Fornece a estrutura comum para tratamento de erros no domínio, incluindo:
 * <ul>
 *   <li>Código de erro padronizado para identificação do tipo de erro</li>
 *   <li>Mensagem descritiva para apresentação ao usuário</li>
 *   <li>Causa original para debugging e rastreamento de exceções</li>
 * </ul>
 *
 * <p><b>Por quê usar DomainException?</b></p>
 * <ul>
 *   <li>Padroniza o tratamento de erros em toda a camada de domínio</li>
 *   <li>Permite identificação programática do tipo de erro via código</li>
 *   <li>Integra-se com handlers globais de exceção (ControllerAdvice)</li>
 * </ul>
 *
 * @see BusinessException
 * @see ValidationException
 * @see ResourceNotFoundException
 * @since 1.0.0
 * @author Israel Araújo
 */
public abstract class DomainException
  extends RuntimeException {

  private static final long serialVersionUID = 1L;

  /**
   * Campo que armazena o código de erro associado à exceção.
   * Formato típico: {@code "ERROR_CODE"} ou {@code "DOMAIN_SPECIFIC_ERROR"}
   */
  private final String errorCode;

  /**
   * Construtor padrão que gera um código de erro automaticamente.
   *
   * <p>O código de erro é derivado do nome da classe em formato snake_case,
   * por exemplo: {@code "BusinessException"} resulta em {@code "BUSINESS_EXCEPTION"}.
   *
   * @param message a mensagem de detalhe que descreve o erro
   */
  protected DomainException(String message) {
    super(message);
    this.errorCode = determineErrorCode();
  }

  /**
   * Construtor com mensagem e causa original.
   *
   * <p>Usado quando a exceção é causada por outra exceção, permitindo
   * o encadeamento de exceções para debugging.
   *
   * @param message a mensagem de detalhe que descreve o erro
   * @param cause   a causa original que originou esta exceção
   */
  protected DomainException(String message, Throwable cause) {
    super(message, cause);
    this.errorCode = determineErrorCode();
  }

  /**
   * Construtor com código de erro explícito e mensagem.
   *
   * <p>Permite especificar um código de erro personalizado para maior
   * granularidade no tratamento de erros.
   *
   * @param errorCode código de erro personalizado (não pode ser {@code null} ou vazio)
   * @param message   a mensagem de detalhe que descreve o erro
   */
  protected DomainException(String errorCode, String message) {
    super(message);
    this.errorCode = errorCode;
  }

  /**
   * Construtor completo com código de erro, mensagem e causa.
   *
   * <p>Fornece total flexibilidade para tratamento de erros complexos
   * com encadeamento de exceções.
   *
   * @param errorCode código de erro personalizado (não pode ser {@code null} ou vazio)
   * @param message   a mensagem de detalhe que descreve o erro
   * @param cause     a causa original que originou esta exceção
   */
  protected DomainException(String errorCode, String message, Throwable cause) {
    super(message, cause);
    this.errorCode = errorCode;
  }

  /**
   * Determina o código de erro automaticamente baseado no nome da classe.
   *
   * <p>Converte o nome simples da classe de CamelCase para SNAKE_CASE,
   * por exemplo: {@code "MyCustomException"} resulta em {@code "MY_CUSTOM_EXCEPTION"}.
   *
   * @return código de erro gerado automaticamente em formato snake_case
   */
  private String determineErrorCode() {
    String className = getClass().getSimpleName();
    return className.replaceAll("(?<!^)(?=[A-Z])", "_").toUpperCase();
  }

  /**
   * Obtém o código de erro associado a esta exceção.
   *
   * <p>O código de erro pode ser usado para:
   * <ul>
   *   <li>Identificação programática do tipo de erro</li>
   *   <li>Tradução para mensagens localizationadas</li>
   *   <li>Tratamento específico baseado no tipo de erro</li>
   * </ul>
   *
   * @return o código de erro da exceção, nunca {@code null}
   */
  public String getErrorCode() {
    return errorCode;
  }
}
