package com.ia.core.security.service.model.privilege;

import com.ia.core.security.model.privilege.PrivilegeOperationContext;
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

/**
 * Classe que representa o objeto de transferência de dados para privilege operation context.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a PrivilegeOperationContextDTO
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
public class PrivilegeOperationContextDTO
    extends AbstractBaseEntityDTO<PrivilegeOperationContext> {

    /**
     * Chave do contexto que identifica o escopo da operação.
     * <p>
     * Deve ser não nula e representa o identificador do contexto
     * (ex: id de um registro específico).
     */
    @NotNull(message = PrivilegeOperationContextTranslator.VALIDATION.CONTEXT_KEY_REQUIRED)
    private String contextKey;

    /**
     * Valores permitidos no contexto.
     * <p>
     * Representa os valores específicos que este contexto permite.
     * O conjunto é inicializado como {@link HashSet} vazio e não pode ser null.
     */
    @Default
    private Set<String> values = new HashSet<>();

    /**
     * @return
     */
    public static SearchRequestDTO getSearchRequest() {
        return new SearchRequestDTO();
    }

    @Override
    public PrivilegeOperationContextDTO cloneObject() {
        return toBuilder().values(values != null ? new HashSet<>(getValues()) : new HashSet<>()).build();
    }

    @Override
    public PrivilegeOperationContextDTO copyObject() {
        return ((PrivilegeOperationContextDTO) super.copyObject()).toBuilder()
            .values(values != null ? new HashSet<>(getValues()) : new HashSet<>()).build();
    }

    @SuppressWarnings("javadoc")
    public static class CAMPOS
        extends AbstractBaseEntityDTO.CAMPOS {
        public static final String CONTEXT_KEY = "contextKey";
        public static final String VALUES = "values";

        /**
         * Retorna todos os nomes de campos deste DTO incluindo os da superclasse.
         *
         * @return conjunto de strings com os nomes dos campos
         */
        public static Set<String> values() {
            var baseValues = AbstractBaseEntityDTO.CAMPOS.values();
            var currentValues = Set.of(CONTEXT_KEY, VALUES);
            var allValues = new java.util.HashSet<String>();
            allValues.addAll(baseValues);
            allValues.addAll(currentValues);
            return java.util.Collections.unmodifiableSet(allValues);
        }
    }
}
