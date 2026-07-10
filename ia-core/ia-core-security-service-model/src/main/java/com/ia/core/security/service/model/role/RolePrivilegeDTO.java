package com.ia.core.security.service.model.role;

import com.ia.core.security.model.role.RolePrivilege;
import com.ia.core.security.service.model.privilege.PrivilegeDTO;
import com.ia.core.security.service.model.privilege.PrivilegeOperationDTO;
import com.ia.core.service.dto.entity.AbstractBaseEntityDTO;
import com.ia.core.service.dto.request.SearchRequestDTO;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder.Default;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * DTO (Data Transfer Object) para representar privilégios associados a um papel.
 * <p>
 * Esta classe é utilizada para transferir dados de privilégios de papéis entre as camadas
 * de apresentação e serviço, contendo informações sobre privilégio e operações
 * associadas a um papel específico.
 *
 * @author Israel Araújo
 * @see RolePrivilege
 * @see AbstractBaseEntityDTO
 * @since 1.0.0
 */

@Data
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class RolePrivilegeDTO
    extends AbstractBaseEntityDTO<RolePrivilege> {

    /**
     * Privilégio associado ao papel.
     * <p>
     * Deve ser não nulo e representa o privilégio que será atribuído ao papel.
     */
    @NotNull(message = RolePrivilegeTranslator.VALIDATION.PRIVILEGE_REQUIRED)
    private PrivilegeDTO privilege;

    @Default
    private Set<PrivilegeOperationDTO> operations = new HashSet<>();

    /**
     * Retorna uma requisição de busca padrão para privilégios de papel.
     *
     * @return instância de {@link SearchRequestDTO} configurada para busca
     */
    public static SearchRequestDTO getSearchRequest() {
        return SearchRequestDTO.builder().build();
    }

    /**
     * Cria uma cópia superficial (clone) deste objeto DTO.
     *
     * @return novo objeto {@link RolePrivilegeDTO} com os mesmos valores de atributos
     */
    @Override
    public RolePrivilegeDTO cloneObject() {
        return toBuilder()
            .privilege(privilege != null ? privilege.cloneObject() : null)
            .operations(operations != null ? new HashSet<>(operations.stream()
                .filter(Objects::nonNull)
                .map(PrivilegeOperationDTO::cloneObject).toList()) : new HashSet<>())
            .build();
    }

    /**
     * Cria uma cópia deste objeto DTO utilizando a implementação da classe pai.
     *
     * @return cópia do objeto {@link RolePrivilegeDTO}
     */
    @Override
    public RolePrivilegeDTO copyObject() {
        return ((RolePrivilegeDTO) super.copyObject()).toBuilder()
            .privilege(privilege != null ? privilege.copyObject() : null)
            .operations(operations != null ? new HashSet<>(operations.stream()
                .filter(Objects::nonNull)
                .map(PrivilegeOperationDTO::copyObject).toList()) : new HashSet<>())
            .build();
    }

    /**
     * Verifica a igualdade entre este objeto e outro objeto.
     * <p>
     * Dois privilégios de papel são considerados iguais se possuírem o mesmo
     * identificador. Se o identificador for nulo, utiliza a comparação de referência.
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
        RolePrivilegeDTO other = (RolePrivilegeDTO) obj;
        return Objects.equals(id, other.id);
    }

    /**
     * Calcula o código hash para este objeto.
     * <p>
     * O código hash é baseado no identificador do privilégio de papel, se disponível.
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
     * @return string contendo informações do privilégio
     */
    @Override
    public String toString() {
        return String.format("%s", privilege);
    }

    @SuppressWarnings("javadoc")
    public static class CAMPOS
        extends AbstractBaseEntityDTO.CAMPOS {
        public static final String PRIVILEGE = "privilege";
        public static final String OPERATIONS = "operations";

        /**
         * Retorna todos os nomes de campos deste DTO incluindo os da superclasse.
         *
         * @return conjunto de strings com os nomes dos campos
         */
        public static Set<String> values() {
            var baseValues = AbstractBaseEntityDTO.CAMPOS.values();
            var currentValues = Set.of(PRIVILEGE, OPERATIONS);
            var allValues = new java.util.HashSet<String>();
            allValues.addAll(baseValues);
            allValues.addAll(currentValues);
            return java.util.Collections.unmodifiableSet(allValues);
        }
    }
}
