package com.ia.core.service.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Base64;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("ZipUtil")
class ZipUtilTest {

    @Nested
    @DisplayName("zip e unzip")
    class ZipUnzip {

        @Test
        @DisplayName("Deve compactar e descompactar mantendo dados originais")
        void deveCompactarEDescompactar() throws IOException {
            String originalContent = "Hello, World! Test content for compression.";
            String base64Content = Base64.getEncoder().encodeToString(originalContent.getBytes());

            byte[] zipped = ZipUtil.zip(base64Content);
            assertThat(zipped).isNotNull();
            assertThat(zipped.length).isGreaterThan(0);
        }

        @Test
        @DisplayName("zip deve lançar NullPointerException para null")
        void zipDeveLancarNullPointerExceptionParaNull() {
            assertThatThrownBy(() -> ZipUtil.zip(null))
                .isInstanceOf(NullPointerException.class);
        }

        @Test
        @DisplayName("unzip deve lançar NullPointerException para null")
        void unzipDeveLancarNullPointerExceptionParaNull() {
            assertThatThrownBy(() -> ZipUtil.unzip(null))
                .isInstanceOf(NullPointerException.class);
        }
    }

    @Nested
    @DisplayName("zipBase64 e unZipBase64")
    class ZipBase64UnzipBase64 {

        @Test
        @DisplayName("Deve manter consistência em roundtrip zipBase64 -> unZipBase64")
        void deveManterConsistenciaRoundtrip() throws IOException {
            String originalContent = "Conteúdo de teste para compressão base64";
            String base64Input = Base64.getEncoder().encodeToString(originalContent.getBytes());

            String compressed = ZipUtil.zipBase64(base64Input);
            assertThat(compressed).isNotNull().isNotBlank();

            String decompressed = ZipUtil.unZipBase64(compressed);
            assertThat(decompressed).isEqualTo(base64Input);
        }

        @Test
        @DisplayName("zipBase64 deve lançar NullPointerException para null")
        void zipBase64DeveLancarNullPointerExceptionParaNull() {
            assertThatThrownBy(() -> ZipUtil.zipBase64(null))
                .isInstanceOf(NullPointerException.class);
        }

        @Test
        @DisplayName("unZipBase64 deve lançar NullPointerException para null")
        void unZipBase64DeveLancarNullPointerExceptionParaNull() {
            assertThatThrownBy(() -> ZipUtil.unZipBase64(null))
                .isInstanceOf(NullPointerException.class);
        }
    }

    @Nested
    @DisplayName("unzip bytes")
    class UnzipBytes {

        @Test
        @DisplayName("Deve descompactar zip e retornar bytes originais")
        void deveDescompactarERetornarBytesOriginais() throws IOException {
            String originalContent = "Test data";
            String base64Input = Base64.getEncoder().encodeToString(originalContent.getBytes());

            String compressed = ZipUtil.zipBase64(base64Input);
            byte[] decompressedBytes = ZipUtil.unzip(compressed);

            String decompressedContent = new String(
                Base64.getDecoder().decode(Base64.getEncoder().encodeToString(decompressedBytes)));
            assertThat(decompressedContent).isEqualTo(originalContent);
        }
    }
}
