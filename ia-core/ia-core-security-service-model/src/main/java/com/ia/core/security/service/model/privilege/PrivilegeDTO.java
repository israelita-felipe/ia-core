package com.ia.core.security.service.model.privilege;

import com.ia.core.security.model.privilege.Privilege;
import com.ia.core.security.model.privilege.PrivilegeType;
import com.ia.core.service.dto.entity.AbstractBaseEntityDTO;
import com.ia.core.service.dto.request.SearchRequestDTO;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * DTO (Data Transfer Object) para representar privilégios no sistema.
 * <p>
 * Esta classe é utilizada para transferir dados de privilégios entre as camadas
 * de apresentação e serviço, contendo informações sobre nome, tipo e valores
 * associados a um privilégio específico.
 *
 * @author Israel Araújo
 * @since 1.0.0
 * @see Privilege
 * @see PrivilegeType
 * @see AbstractBaseEntityDTO
 */
@Getter
@Setter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class PrivilegeDTO
  extends AbstractBaseEntityDTO<Privilege> {
  /** Serial UID para controle de versão da serialização. */
  private static final long serialVersionUID = -19560738760061623L;

  /**
   * Retorna uma requisição de busca padrão para privilégios.
   *
   * @return instância de {@link SearchRequestDTO} configurada para busca de privilégios
   */
  public static SearchRequestDTO getSearchRequest() {
    return new PrivilegeSearchRequest();
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
   * Nome do privilégio.
   * <p>
   * Deve conter entre 3 e 100 caracteres e não pode ser nulo.
   */
  @NotNull(message = PrivilegeTranslator.VALIDATION.NAME_REQUIRED)
  @Size(min = 3, max = 100, message = PrivilegeTranslator.VALIDATION.NAME_SIZE)
  private String name;

  /**
   * Tipo do privilégio. Define a categoria e o escopo de aplicação do privilégio
   * no sistema. O tipo determina quais operações estão disponíveis para este
   * privilégio.
   * <p>
   * Valores possíveis:
   * <ul>
   *   <li>{@link PrivilegeType#SYSTEM} - privilégio de sistema, aplicável a
   *       funcionalidades globais</li>
   *   <li>{@link PrivilegeType#USER} - privilégio específico de usuário,
   *       aplicável a funcionalidades individuais</li>
   * </ul>
   * <p>
   * O valor padrão é {@link PrivilegeType#SYSTEM}.
   */
  @Default
  @NotNull(message = PrivilegeTranslator.VALIDATION.TYPE_REQUIRED)
  private PrivilegeType type = PrivilegeType.SYSTEM;

  /**
   * Conjunto de valores associados ao privilégio.
   * <p>
   * Representa os valores específicos ou identificadores aos quais este
   * privilégio se aplica. Por exemplo, para um privilégio de visualização
   * de módulo, pode conter os identificadores dos módulos permitidos.
   * <p>
   * O conjunto é inicializado como {@link HashSet} vazio e não pode ser null.
   * Recomenda-se usar {@link #addValue(String)} ou {@link #removeValue(String)}
   * para manipulação individual dos valores.
   * <p>
   * @bugfix SECURITY: Getter retorna coleção imutável para evitar modificação
   *         externa não controlada do estado interno.
   */
  @Default
  private Set<String> values = new HashSet<>();

  /**
   * Retorna uma visão imutável do conjunto de valores.
   *
   * @return conjunto de valores não modificável
   */
  public Set<String> getValues() {
    return Collections.unmodifiableSet(values);
  }

  /**
   * Adiciona um valor ao conjunto de valores do privilégio.
   *
   * @param value valor a ser adicionado (não pode ser null)
   * @throws NullPointerException se value for null
   */
  public void addValue(String value) {
    Objects.requireNonNull(value, "Value cannot be null");
    this.values.add(value);
  }

  /**
   * Remove um valor do conjunto de valores do privilégio.
   *
   * @param value valor a ser removido
   * @return true se o valor foi removido, false caso contrário
   */
  public boolean removeValue(String value) {
    return this.values.remove(value);
  }

  /**
   * Define o conjunto de valores (faz uma cópia defensiva).
   *
   * @param values novo conjunto de valores (não pode ser null)
   * @throws NullPointerException se values for null
   */
  public void setValues(Set<String> values) {
    Objects.requireNonNull(values, "Values cannot be null");
    this.values = new HashSet<>(values);
  }

  /**
   * Cria uma cópia superficial (clone) deste objeto DTO.
   *
   * @return novo objeto {@link PrivilegeDTO} com os mesmos valores de atributos
   */
  @Override
  public PrivilegeDTO cloneObject() {
    return toBuilder().build();
  }

  /**
   * Cria uma cópia deste objeto DTO utilizando a implementação da classe pai.
   *
   * @return cópia do objeto {@link PrivilegeDTO}
   */
  @Override
  public PrivilegeDTO copyObject() {
    return (PrivilegeDTO) super.copyObject();
  }

  /**
   * Verifica a igualdade entre este objeto e outro objeto.
   * <p>
   * Dois privilégios são considerados iguais se possuírem o mesmo identificador.
   * Se o identificador for nulo, utiliza a comparação de referência.
   *
   * @param obj o objeto a ser comparado
   * @return true se os objetos forem iguais, false caso contrário
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (id == null) {
      return this == obj;
    }
    if (!(getClass().isInstance(obj))) {
      return false;
    }
    PrivilegeDTO other = (PrivilegeDTO) obj;
    return Objects.equals(id, other.id);
  }

  /**
   * Calcula o código hash para este objeto.
   * <p>
   * O código hash é baseado no identificador do privilégio, se disponível.
   *
   * @return código hash calculado
   */
  @Override
  public int hashCode() {
    if (id != null) {
      return Objects.hash(id);
    }
    return super.hashCode();
  }

  /**
   * Retorna uma representação em string deste objeto.
   *
   * @return string contendo o nome do privilégio
   */
  @SuppressWarnings("javadoc")
  public static class CAMPOS
    extends AbstractBaseEntityDTO.CAMPOS {
    public static final String NAME = "name";
    public static final String TYPE = "type";
    public static final String VALUES = "values";
  }

  @Override
  public String toString() {
    return String.format("%s", name);
  }
}
