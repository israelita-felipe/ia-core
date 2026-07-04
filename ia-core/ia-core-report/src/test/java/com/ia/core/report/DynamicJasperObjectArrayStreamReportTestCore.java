package com.ia.core.report;

import com.ia.test.CoreBaseUnitTest;
import org.junit.jupiter.api.Test;

import java.util.function.Supplier;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Testes para DynamicJasperObjectArrayStreamReport baseados nos casos de teste documentados.
 */
class DynamicJasperObjectArrayStreamReportTestCore extends CoreBaseUnitTest {

    @Test
    void deveLancarExcecaoAoChamarGetReportPath() {
        // Arrange
        String[] cabecalhos = {"nome"};
        Supplier<Stream<Object[]>> dataSupplier = () -> Stream.<Object[]>of(new Object[]{"Teste"});
        DynamicJasperObjectArrayStreamReport report = new DynamicJasperObjectArrayStreamReport("Teste", cabecalhos, dataSupplier);

        // Act & Assert
        assertThatThrownBy(() -> report.getReportPath())
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("gerado dinamicamente");
    }

    @Test
    void deveTerConfiguracaoDePaginaCorreta() {
        // Arrange
        String[] cabecalhos = {"nome"};
        Supplier<Stream<Object[]>> dataSupplier = () -> Stream.<Object[]>of(new Object[]{"Teste"});
        DynamicJasperObjectArrayStreamReport report = new DynamicJasperObjectArrayStreamReport("Teste", cabecalhos, dataSupplier);

        // Act & Assert
        assertThat(report.getPageWidth()).isEqualTo(595);
        assertThat(report.getPageHeight()).isEqualTo(842);
        assertThat(report.getColumnWidth()).isEqualTo(555);
    }
}
