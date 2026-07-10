package com.ia.core.security.service.model.log.operation;

import com.ia.core.security.model.functionality.OperationEnum;
import com.ia.core.security.model.log.operation.LogOperation;
import com.ia.core.service.dto.entity.AbstractBaseEntityDTO;
import com.ia.core.service.dto.request.SearchRequestDTO;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.Builder.Default;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * DTO (Data Transfer Object) para representar operações de log no sistema.
 * <p>
 * Esta classe é utilizada para transferir dados de operações de log entre as camadas
 * de apresentação e serviço, contendo informações sobre usuário, tipo, valores
 * e operações realizadas.
 *
 * @author Israel Araújo
 * @see LogOperation
 * @see AbstractBaseEntityDTO
 * @since 1.0.0
 */
@Getter
@Setter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class LogOperationDTO
  extends AbstractBaseEntityDTO<LogOperation> {

  /**
   * Retorna uma requisição de busca padrão para operações de log.
   *
   * @return instância de {@link SearchRequestDTO} configurada para busca de operações de log
   */
  public static SearchRequestDTO getSearchRequest() {
    return new LogOperationSearchRequestDTO();
  }

  /**
   * Retorna o conjunto de propriedades disponíveis para filtragem.
   *
   * @return conjunto de strings representando as propriedades que podem ser usadas como filtros
   */
  public static Set<String> propertyFilters() {
    return getSearchRequest().propertyFilters();
  }

  /**
   * Nome do usuário que realizou a operação.
   * <p>
   * Deve conter entre 3 e 100 caracteres e não pode ser nulo.
   */
  @NotNull(message = LogOperationTranslator.VALIDATION.USER_NAME_REQUIRED)
  private String userName;

  /**
   * Código do usuário que realizou a operação.
   * <p>
   * Deve conter entre 3 e 50 caracteres e não pode ser nulo.
   */
  @NotNull(message = LogOperationTranslator.VALIDATION.USER_CODE_REQUIRED)
  private String userCode;

  /**
   * Tipo da operação realizada.
   * <p>
   * Deve conter entre 3 e 100 caracteres e não pode ser nulo.
   */
  @NotNull(message = LogOperationTranslator.VALIDATION.TYPE_REQUIRED)
  private String type;

  /**
   * ID do valor modificado na operação.
   * <p>
   * Deve ser não nulo.
   */
  @NotNull(message = LogOperationTranslator.VALIDATION.VALUE_ID_REQUIRED)
  private Long valueId;

  /**
   * Valor antigo antes da operação.
   */
  private String oldValue;

  /**
   * Novo valor após a operação.
   */
  private String newValue;

  /**
   * Data e hora da operação.
   * <p>
   * Padrão é {@link LocalDateTime#now()} e não pode ser nula.
   */
  @Default
  @NotNull(message = LogOperationTranslator.VALIDATION.DATE_TIME_REQUIRED)
  private LocalDateTime dateTimeOperation = LocalDateTime.now();

  /**
   * Operação realizada (criar, ler, atualizar, excluir).
   * <p>
   * Deve ser não nula.
   */
  @NotNull(message = LogOperationTranslator.VALIDATION.OPERATION_REQUIRED)
  private OperationEnum operation;

  /**
   * Cria uma cópia superficial (clone) deste objeto DTO.
   *
   * @return novo objeto {@link LogOperationDTO} com os mesmos valores de atributos
   */
  @Override
  public LogOperationDTO cloneObject() {
    return toBuilder().build();
  }

  /**
   * Retorna uma representação em string deste objeto.
   *
   * @return string contendo informações da operação de log
   */
  @Override
  public String toString() {
    return String.format("%s - %s (%s)", userCode, operation, type);
  }

  @SuppressWarnings("javadoc")
  public static class CAMPOS extends AbstractBaseEntityDTO.CAMPOS {
    public static final String USER_CODE = "userCode";
    public static final String OPERATION = "operation";
    public static final String TYPE = "type";
    public static final String DATE_TIME_OPERATION = "dateTimeOperation";
    public static final String USER_NAME = "userName";
    public static final String VALUE_ID = "valueId";
    public static final String OLD_VALUE = "oldValue";
    public static final String NEW_VALUE = "newValue";

    /**
     * Retorna todos os nomes de campos deste DTO incluindo os da superclasse.
     *
     * @return conjunto de strings com os nomes dos campos
     */
    public static Set<String> values() {
      var baseValues = AbstractBaseEntityDTO.CAMPOS.values();
      var currentValues = Set.of(USER_CODE, OPERATION, TYPE, DATE_TIME_OPERATION, USER_NAME, VALUE_ID, OLD_VALUE, NEW_VALUE);
      var allValues = new java.util.HashSet<String>();
      allValues.addAll(baseValues);
      allValues.addAll(currentValues);
      return java.util.Collections.unmodifiableSet(allValues);
    }
  }
}
