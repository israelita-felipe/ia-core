package com.ia.core.report;

import com.ia.test.CoreBaseUnitTest;
import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Testes para DynamicJasperJsonObjectStreamReport baseados nos casos de teste documentados.
 */
class DynamicJasperJsonObjectStreamReportTestCore extends CoreBaseUnitTest {

    @Test
    void deveLancarExcecaoAoChamarGetReportPath() {
        // Arrange
        Stream<String> data = Stream.of("{\"nome\":\"Teste\"}");
        String[] cabecalhos = {"nome"};
        DynamicJasperJsonObjectStreamReport report = new DynamicJasperJsonObjectStreamReport("Teste", cabecalhos, data);

        // Act & Assert
        assertThatThrownBy(() -> report.getReportPath())
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("gerado dinamicamente");
    }

    @Test
    void deveTerConfiguracaoDePaginaCorreta() {
        // Arrange
        Stream<String> data = Stream.of("{\"nome\":\"Teste\"}");
        String[] cabecalhos = {"nome"};
        DynamicJasperJsonObjectStreamReport report = new DynamicJasperJsonObjectStreamReport("Teste", cabecalhos, data);

        // Act & Assert
        assertThat(report.getPageWidth()).isEqualTo(595);
        assertThat(report.getPageHeight()).isEqualTo(842);
        assertThat(report.getColumnWidth()).isEqualTo(555);
    }
}
