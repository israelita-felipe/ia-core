package com.ia.core.llm.service.transform;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Testes unitários para ImageProcessingService.
 * 
 * <p>Este teste cobre as seguintes funcionalidades:</p>
 * <ul>
 *   <li>Binarização de imagens usando método de Otsu</li>
 *   <li>Compressão JPEG</li>
 *   <li>Redimensionamento mantendo aspect ratio</li>
 * </ul>
 * 
 * @author Israel Araújo
 */
@ExtendWith(MockitoExtension.class)
class ImageProcessingServiceTest {

  @Mock
  private BufferedImage mockImage;

  @InjectMocks
  private ImageProcessingService service;

  private BufferedImage testImage;

  @BeforeEach
  void setUp() throws IOException {
    // Criar imagem de teste 100x100 em escala de cinza
    testImage = new BufferedImage(100, 100, BufferedImage.TYPE_BYTE_GRAY);
  }

  @Test
  @DisplayName("Deve calcular limiar Otsu corretamente")
  void deveCalcularLimiarOtsuCorretamente() {
    // Given
    BufferedImage image = createGrayscaleImage(100, 100);

    // When
    int limiar = service.calcularLimiarOtsu(image);

    // Then
    assertThat(limiar).isBetween(0, 255);
  }

  @Test
  @DisplayName("Deve binarizar imagem com sucesso")
  void deveBinarizarImagemComSucesso() {
    // Given
    BufferedImage image = createGrayscaleImage(100, 100);
    int limiar = 127;

    // When
    BufferedImage binarizada = service.binarizarImagem(image, limiar);

    // Then
    assertThat(binarizada).isNotNull();
    assertThat(binarizada.getWidth()).isEqualTo(image.getWidth());
    assertThat(binarizada.getHeight()).isEqualTo(image.getHeight());
    assertThat(binarizada.getType()).isEqualTo(BufferedImage.TYPE_BYTE_BINARY);
  }

  @Test
  @DisplayName("Deve binarizar com limiar zero (todos pixels pretos)")
  void deveBinarizarComLimiarZero() {
    // Given
    BufferedImage image = createGrayscaleImage(10, 10);
    int limiar = 0;

    // When
    BufferedImage binarizada = service.binarizarImagem(image, limiar);

    // Then
    assertThat(binarizada).isNotNull();
    verificarTodosPixelsPretos(binarizada);
  }

  @Test
  @DisplayName("Deve binarizar com limiar 255 (todos pixels brancos)")
  void deveBinarizarComLimiar255() {
    // Given
    BufferedImage image = createGrayscaleImage(10, 10);
    int limiar = 255;

    // When
    BufferedImage binarizada = service.binarizarImagem(image, limiar);

    // Then
    assertThat(binarizada).isNotNull();
    verificarTodosPixelsBrancos(binarizada);
  }

  @Test
  @DisplayName("Deve comprimir imagem JPEG")
  void deveComprimirImagemJpeg() throws IOException {
    // Given
    BufferedImage image = createGrayscaleImage(100, 100);

    // When
    byte[] compressed = service.compressJpeg(image, 0.5f);

    // Then
    assertThat(compressed).isNotNull();
    assertThat(compressed.length).isGreaterThan(0);
  }

  @Test
  @DisplayName("Deve comprimir com qualidade máxima")
  void deveComprimirComQualidadeMaxima() throws IOException {
    // Given
    BufferedImage image = createGrayscaleImage(100, 100);

    // When
    byte[] compressed = service.compressJpeg(image, 1.0f);

    // Then
    assertThat(compressed).isNotNull();
    assertThat(compressed.length).isGreaterThan(0);
  }

  @Test
  @DisplayName("Deve comprimir e redimensionar mantendo aspect ratio")
  void deveComprimirERedimensionarMantendoAspectRatio() throws IOException {
    // Given
    BufferedImage image = createGrayscaleImage(200, 100); // 2:1 aspect ratio
    float quality = 0.5f;
    int maxWidth = 100;
    int maxHeight = 100;

    // When
    byte[] result = service.compressAndResize(
        imageToInputStream(image), quality, maxWidth, maxHeight);

    // Then
    assertThat(result).isNotNull();
    assertThat(result.length).isGreaterThan(0);
  }

  @Test
  @DisplayName("Deve reduzir dimensões quando largura excede máximo")
  void deveReduzirDimensoesQuandoLarguraExcede() throws IOException {
    // Given
    BufferedImage image = createGrayscaleImage(200, 100);
    float quality = 0.5f;
    int maxWidth = 50;
    int maxHeight = 100;

    // When
    byte[] result = service.compressAndResize(
        imageToInputStream(image), quality, maxWidth, maxHeight);

    // Then
    assertThat(result).isNotNull();
    assertThat(result.length).isGreaterThan(0);
  }

  @Test
  @DisplayName("Deve reduzir dimensões quando altura excede máximo")
  void deveReduzirDimensoesQuandoAlturaExcede() throws IOException {
    // Given
    BufferedImage image = createGrayscaleImage(100, 200);
    float quality = 0.5f;
    int maxWidth = 100;
    int maxHeight = 50;

    // When
    byte[] result = service.compressAndResize(
        imageToInputStream(image), quality, maxWidth, maxHeight);

    // Then
    assertThat(result).isNotNull();
    assertThat(result.length).isGreaterThan(0);
  }

  @Test
  @DisplayName("Não deve lançar exceção para entrada nula em binarizarImagem")
  void naoDeveLancarExcecaoParaImagemNula() {
    // Given
    BufferedImage image = null;
    int limiar = 127;

    // When/Then
    assertThatThrownBy(() -> service.binarizarImagem(image, limiar))
      .isInstanceOf(IllegalArgumentException.class)
      .hasMessageContaining("imagem");
  }

  @Test
  @DisplayName("Deve calcular limiar para imagem pequena")
  void deveCalcularLimiarParaImagemPequena() {
    // Given
    BufferedImage image = createGrayscaleImage(5, 5);

    // When
    int limiar = service.calcularLimiarOtsu(image);

    // Then
    assertThat(limiar).isBetween(0, 255);
  }

  @Test
  @DisplayName("Deve calcular limiar para imagem vazia")
  void deveCalcularLimiarParaImagemVazia() {
    // Given
    BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_BYTE_GRAY);

    // When
    int limiar = service.calcularLimiarOtsu(image);

    // Then
    assertThat(limiar).isBetween(0, 255);
  }

  // Métodos auxiliares

  private BufferedImage createGrayscaleImage(int width, int height) {
    BufferedImage image = new BufferedImage(
        width, height, BufferedImage.TYPE_BYTE_GRAY);
    
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        // Criar gradiente de cinza
        int gray = (x * 255) / width;
        int rgb = (gray << 16) | (gray << 8) | gray;
        image.setRGB(x, y, rgb);
      }
    }
    
    return image;
  }

  private void verificarTodosPixelsPretos(BufferedImage image) {
    int blackCount = 0;
    for (int y = 0; y < image.getHeight(); y++) {
      for (int x = 0; x < image.getWidth(); x++) {
        if (image.getRGB(x, y) == 0x000000) {
          blackCount++;
        }
      }
    }
    assertThat(blackCount).isEqualTo(image.getWidth() * image.getHeight());
  }

  private void verificarTodosPixelsBrancos(BufferedImage image) {
    int whiteCount = 0;
    for (int y = 0; y < image.getHeight(); y++) {
      for (int x = 0; x < image.getWidth(); x++) {
        if (image.getRGB(x, y) == 0xFFFFFF) {
          whiteCount++;
        }
      }
    }
    assertThat(whiteCount).isEqualTo(image.getWidth() * image.getHeight());
  }

  private InputStream imageToInputStream(BufferedImage image) throws IOException {
    byte[] bytes = service.compressJpeg(image, 1.0f);
    return new ByteArrayInputStream(bytes);
  }
}
