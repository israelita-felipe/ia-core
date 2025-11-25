package com.ia.core.service.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.zip.Deflater;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * Implementação para zipagem de arquivo. Utiliza por padrão gzip e base64
 *
 * @author Israel Araújo
 */
public class ZipUtil {

  /**
   * Realiza a descompressão de um arquivo em base64
   *
   * @param base64Dto Conteúdo zipado em gzip e depois base64
   * @return array de bytes do arquivo descompactado
   * @throws IOException caso ocorra erro de leitura ou escrita
   */
  public static byte[] unzip(String base64Dto)
    throws IOException {
    ByteArrayInputStream input = new ByteArrayInputStream(Base64
        .getDecoder().decode(base64Dto));
    GZIPInputStream zipInput = new GZIPInputStream(input);
    return zipInput.readAllBytes();
  }

  /**
   * Realiza a descompressao de um arquivo em base64 e retorna sua representação
   * em base 64
   *
   * @param base64Dto Conteúdo zipado em gzip e depois base64
   * @return Arquivo descompactado e em base64
   * @throws IOException caso haja erro de leitura ou escrita
   */
  public static String unZipBase64(String base64Dto)
    throws IOException {
    return Base64.getEncoder().encodeToString(unzip(base64Dto));
  }

  /**
   * Comprime um conteúdo representado em base64
   *
   * @param base64Dto Conteúdo em base64
   * @return array de bytes do conteúdo zipado com gzip
   * @throws IOException caso ocorra erro de leitura ou escrita
   */
  public static byte[] zip(String base64Dto)
    throws IOException {
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    GZIPOutputStream zipOut = new GZIPOutputStream(out) {
      {
        this.def = new Deflater(Deflater.BEST_COMPRESSION, true);
      }
    };
    zipOut.write(Base64.getDecoder().decode(base64Dto));
    zipOut.flush();
    zipOut.close();
    out.close();
    return out.toByteArray();
  }

  /**
   * Comprime um conteúdo em base64 utilizando gzip
   *
   * @param base64Dto Conteúdo em base64
   * @return Representação em base64 do arquivo comprimido
   * @throws IOException caso ocorra erro de leitura ou escrita
   */
  public static String zipBase64(String base64Dto)
    throws IOException {
    return Base64.getEncoder().encodeToString(zip(base64Dto));
  }
}
