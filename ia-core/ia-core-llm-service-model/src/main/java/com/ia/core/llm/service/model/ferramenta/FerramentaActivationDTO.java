package com.ia.core.llm.service.model.ferramenta;

import com.ia.core.llm.service.model.template.TemplateDTO;
import com.ia.core.service.dto.DTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * DTO para ativação de Ferramenta.
 * <p>
 * Contém os dados necessários para ativação de uma ferramenta.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Data
@Builder(toBuilder = true)
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class FerramentaActivationDTO implements DTO<Long> {

    /** Serial UID */
    private static final long serialVersionUID = 974920123456789123L;

    private Long id;
    private String titulo;
    private String descricao;
    private String instrucoes;
    private TemplateDTO template;
    @Builder.Default
    private List<FerramentaDTO> subFerramentas = new ArrayList<>();

    /**
     * Cria uma cópia superficial (clone) deste DTO.
     *
     * @return nova instância com os mesmos valores
     */
    @Override
    public FerramentaActivationDTO cloneObject() {
        return toBuilder()
            .subFerramentas(new ArrayList<>(subFerramentas))
            .build();
    }

    /**
     * Retorna uma representação em string deste objeto.
     *
     * @return string contendo o título
     */
    @Override
    public String toString() {
        return String.format("FerramentaActivationDTO{titulo=%s}", titulo);
    }

    /**
     * Constantes dos campos do DTO para uso type-safe.
     */
    @SuppressWarnings("javadoc")
    public static class CAMPOS {
        public static final String ID = "id";
        public static final String TITULO = "titulo";
        public static final String DESCRICAO = "descricao";
        public static final String INSTRUCOES = "instrucoes";
        public static final String TEMPLATE = "template";
        public static final String SUB_FERRAMENTAS = "subFerramentas";

        public static Set<String> values() {
            return Collections.unmodifiableSet(Set.of(ID, TITULO, DESCRICAO, INSTRUCOES, TEMPLATE, SUB_FERRAMENTAS));
        }
    }
}