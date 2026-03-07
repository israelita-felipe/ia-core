package com.ia.core.model.exception;

/**
 * Exceção lançada quando ocorre um erro de validação de dados de entrada.
 *
 * <p>Usada para indicar que os dados fornecidos pelo usuário ou cliente da API
 * não passaram nas validações de negócio. Diferencia-se de {@link BusinessException}
 * pois refere-se especificamente a validações de campos e dados de entrada.
 *
 * <p><b>Exemplos de uso:</b></p>
 * <ul>
 *   <li>Email em formato inválido</li>
 *   <li>CPF com dígitos incorretos</li>
 *   <li>Data de nascimento no futuro</li>
 *   <li>Campo obrigatório não preenchido</li>
 * </ul>
 *
 * <p><b>Exemplo de lançamento:</b></p>
 * {@code
 * if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
 *     throw new ValidationException("email", email, "Email em formato inválido");
 * }
 * }
 *
 * @see DomainException
 * @see BusinessException
 * @since 1.0.0
 * @author Israel Araújo
 */
public class ValidationException
  extends DomainException {

  private static final long serialVersionUID = 1L;

  private static final String DEFAULT_MESSAGE = "Erro de validação";
  private static final String DEFAULT_ERROR_CODE = "VALIDATION_ERROR";

  /**
   * Armazena o nome do campo que falhou na validação.
   * Exemplo: "email", "cpf", "dataNascimento"
   */
  private final String field;

  /**
   * Armazena o valor que falhou na validação.
   * Útil para debugging e apresentação ao usuário.
   */
  private final Object rejectedValue;

  /**
   * Construtor com mensagem de erro.
   *
   * <p>Usa o código de erro padrão {@code "VALIDATION_ERROR"}.
   * Os campos {@code field} e {@code rejectedValue} serão {@code null}.
   *
   * @param message mensagem de erro descritiva (não pode ser {@code null} ou vazia)
   */
  public ValidationException(String message) {
    super(DEFAULT_ERROR_CODE, message);
    this.field = null;
    this.rejectedValue = null;
  }

  /**
   * Construtor com campo, valor rejeitado e mensagem.
   *
   * <p>Fornece informações detalhadas sobre o erro de validação,
   * útil para feedback ao usuário e debugging.
   *
   * @param field         nome do campo que falhou na validação (não pode ser {@code null})
   * @param rejectedValue o valor que falhou na validação (pode ser {@code null})
   * @param message       mensagem de erro descritiva (não pode ser {@code null} ou vazia)
   */
  public ValidationException(String field, Object rejectedValue, String message) {
    super(DEFAULT_ERROR_CODE, message);
    this.field = field;
    this.rejectedValue = rejectedValue;
  }

  /**
   * Obtém o nome do campo que falhou na validação.
   *
   * @return o nome do campo, ou {@code null} se não especificado no construtor
   */
  public String getField() {
    return field;
  }

  /**
   * Obtém o valor que falhou na validação.
   *
   * <p>Útil para debugging e para exibir o valor incorreto ao usuário
   * em formulários de correção.
   *
   * @return o valor rejeitado, ou {@code null} se não especificado
   */
  public Object getRejectedValue() {
    return rejectedValue;
  }
}
