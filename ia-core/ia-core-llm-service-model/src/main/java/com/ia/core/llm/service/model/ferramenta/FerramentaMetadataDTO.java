package com.ia.core.llm.service.model.ferramenta;

import com.ia.core.llm.model.ferramenta.TipoFerramentaEnum;
import com.ia.core.service.dto.DTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.Set;

/**
 * DTO para metadata de Ferramenta.
 * <p>
 * Contém informações resumidas para listagem de ferramentas.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Data
@Builder(toBuilder = true)
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class FerramentaMetadataDTO implements DTO<Long> {

    /** Serial UID */
    private static final long serialVersionUID = 874920123456789123L;

    private Long id;
    private String titulo;
    private String descricao;
    private TipoFerramentaEnum tipo;
    private int subFerramentaCount;
    private boolean ativo;

    /**
     * Cria uma cópia superficial (clone) deste DTO.
     *
     * @return nova instância com os mesmos valores
     */
    @Override
    public FerramentaMetadataDTO cloneObject() {
        return toBuilder().build();
    }

    /**
     * Retorna uma representação em string deste objeto.
     *
     * @return string contendo o título
     */
    @Override
    public String toString() {
        return String.format("FerramentaMetadataDTO{titulo=%s}", titulo);
    }

    /**
     * Constantes dos campos do DTO para uso type-safe.
     */
    @SuppressWarnings("javadoc")
    public static class CAMPOS {
        public static final String ID = "id";
        public static final String TITULO = "titulo";
        public static final String DESCRICAO = "descricao";
        public static final String TIPO = "tipo";
        public static final String SUB_FERRAMENTA_COUNT = "subFerramentaCount";
        public static final String ATIVO = "ativo";

        public static Set<String> values() {
            return Collections.unmodifiableSet(Set.of(ID, TITULO, DESCRICAO, TIPO, SUB_FERRAMENTA_COUNT, ATIVO));
        }
    }
}