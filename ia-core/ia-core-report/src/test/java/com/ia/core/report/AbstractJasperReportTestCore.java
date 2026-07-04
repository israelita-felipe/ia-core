package com.ia.core.report;

import com.ia.test.CoreBaseUnitTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Testes para AbstractJasperReport.ExportType baseados nos casos de teste documentados.
 */
class AbstractJasperReportTestCore extends CoreBaseUnitTest {

    @Test
    void deveTerValoresDoEnumExportType() {
        // Act & Assert
        assertThat(AbstractJasperReport.ExportType.values()).isNotEmpty();
        assertThat(AbstractJasperReport.ExportType.values()).contains(
            AbstractJasperReport.ExportType.PDF,
            AbstractJasperReport.ExportType.XLS
        );
    }

    @Test
    void deveTerContentTypePDF() {
        // Act
        String contentType = AbstractJasperReport.ExportType.PDF.getContentType();

        // Assert
        assertThat(contentType).isEqualTo("application/pdf");
    }

    @Test
    void deveTerExtensaoPDF() {
        // Act
        String extension = AbstractJasperReport.ExportType.PDF.getExtension();

        // Assert
        assertThat(extension).isEqualTo("pdf");
    }

    @Test
    void deveTerContentTypeXLS() {
        // Act
        String contentType = AbstractJasperReport.ExportType.XLS.getContentType();

        // Assert
        assertThat(contentType).isEqualTo("application/vnd.ms-excel");
    }

    @Test
    void deveTerExtensaoXLS() {
        // Act
        String extension = AbstractJasperReport.ExportType.XLS.getExtension();

        // Assert
        assertThat(extension).isEqualTo("xls");
    }
}
