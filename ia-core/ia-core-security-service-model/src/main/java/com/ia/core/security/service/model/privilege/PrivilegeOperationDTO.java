package com.ia.core.security.service.model.privilege;

import com.ia.core.security.model.functionality.OperationEnum;
import com.ia.core.security.model.privilege.PrivilegeOperation;
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
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Classe que representa o objeto de transferência de dados para privilege operation.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a PrivilegeOperationDTO
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
 */

@Data
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class PrivilegeOperationDTO
    extends AbstractBaseEntityDTO<PrivilegeOperation> {

    /**
     * Operação a ser realizada.
     * <p>
     * Deve ser não nula e representa a operação que pode ser realizada
     * sobre o privilégio (criar, ler, atualizar, excluir).
     */
    @NotNull(message = PrivilegeOperationTranslator.VALIDATION.OPERATION_REQUIRED)
    private OperationEnum operation;

    @Default
    private Set<PrivilegeOperationContextDTO> context = new HashSet<>();

    /**
     * @return
     */
    public static SearchRequestDTO getSearchRequest() {
        return SearchRequestDTO.builder().build();
    }

    @Override
    public PrivilegeOperationDTO cloneObject() {
        return toBuilder().context(context != null ? new HashSet<>(getContext().stream()
            .filter(java.util.Objects::nonNull)
            .map(PrivilegeOperationContextDTO::cloneObject)
            .collect(Collectors.toSet())) : new HashSet<>()).build();
    }

    @Override
    public PrivilegeOperationDTO copyObject() {
        return ((PrivilegeOperationDTO) super.copyObject()).toBuilder()
            .context(context != null ? new HashSet<>(getContext().stream()
                .filter(java.util.Objects::nonNull)
                .map(PrivilegeOperationContextDTO::copyObject)
                .collect(Collectors.toSet())) : new HashSet<>())
            .build();
    }

    @SuppressWarnings("javadoc")
    public static class CAMPOS
        extends AbstractBaseEntityDTO.CAMPOS {
        public static final String OPERATION = "operation";
        public static final String CONTEXT = "context";

        /**
         * Retorna todos os nomes de campos deste DTO incluindo os da superclasse.
         *
         * @return conjunto de strings com os nomes dos campos
         */
        public static Set<String> values() {
            var baseValues = AbstractBaseEntityDTO.CAMPOS.values();
            var currentValues = Set.of(OPERATION, CONTEXT);
            var allValues = new java.util.HashSet<String>();
            allValues.addAll(baseValues);
            allValues.addAll(currentValues);
            return java.util.Collections.unmodifiableSet(allValues);
        }
    }
}
