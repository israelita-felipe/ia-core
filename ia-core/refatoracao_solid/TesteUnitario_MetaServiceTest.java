package refatoracao_solid;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Exemplo de teste unitário para MetaService.
 */
@DisplayName("MetaService Tests")
class MetaServiceTest {
    @Mock private MetaTotalCalculator calculator;
    @Mock private MetaServiceConfig config;
    @InjectMocks private MetaService service;
    @Test
    @DisplayName("should calculate totals correctly when converting to DTO")
    void testToDTOCalculatesCorrectly() {
        Meta meta = new Meta();
        MetaDTO expectedDTO = new MetaDTO();
        when(calculator.calculateTotalReceita(meta)).thenReturn(expectedDTO.getTotalReceita());
        when(calculator.calculateTotalDespesa(meta)).thenReturn(expectedDTO.getTotalDespesa());
        MetaDTO actualDTO = service.toDTO(meta);
        assertThat(actualDTO)
            .isNotNull()
            .extracting(MetaDTO::getTotalReceita, MetaDTO::getTotalDespesa)
            .contains(expectedDTO.getTotalReceita(), expectedDTO.getTotalDespesa());
        verify(calculator).calculateTotalReceita(meta);
        verify(calculator).calculateTotalDespesa(meta);
    }
    @Test
    @DisplayName("should throw exception when model is null")
    void testToDTOThrowsExceptionWhenModelNull() {
        assertThatThrownBy(() -> service.toDTO(null))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Model não pode ser null");
    }
}

interface MetaTotalCalculator {
    java.math.BigDecimal calculateTotalReceita(Meta meta);
    java.math.BigDecimal calculateTotalDespesa(Meta meta);
}
class MetaServiceConfig {}
class MetaService {
    private final MetaTotalCalculator calculator;
    public MetaService(MetaTotalCalculator calculator) { this.calculator = calculator; }
    public MetaDTO toDTO(Meta meta) {
        if (meta == null) throw new IllegalArgumentException("Model não pode ser null");
        MetaDTO dto = new MetaDTO();
        dto.setTotalReceita(calculator.calculateTotalReceita(meta));
        dto.setTotalDespesa(calculator.calculateTotalDespesa(meta));
        return dto;
    }
}
class Meta {}
class MetaDTO {
    private java.math.BigDecimal totalReceita = java.math.BigDecimal.ZERO;
    private java.math.BigDecimal totalDespesa = java.math.BigDecimal.ZERO;
    public java.math.BigDecimal getTotalReceita() { return totalReceita; }
    public void setTotalReceita(java.math.BigDecimal v) { totalReceita = v; }
    public java.math.BigDecimal getTotalDespesa() { return totalDespesa; }
    public void setTotalDespesa(java.math.BigDecimal v) { totalDespesa = v; }
}
