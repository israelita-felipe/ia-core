package com.ia.core.security.service.model.user;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Utilitários de criptografia para senhas.
 * <p>
 * Fornece métodos estáticos para criptografia/descriptografia AES
 * usando PBKDF2 para derivação de chave.
 *
 * @author IA
 */
public class CryptUtils {

  private static final String SECRET_KEY = System.getenv().getOrDefault("USER_ENCRYPTION_KEY", "5s6ad4f&%$#.");
  private static final String ALGORITHM = "AES/CBC/PKCS5Padding";
  private static final byte[] IV = new byte[] { 0, 1, 1, 1, 0, 0, 0, 1, 1, 0, 0, 1, 1, 1, 0, 0 };

  // Password generation constants
  public static final int DEFAULT_QTD_LOWERCASE = 2;
  public static final int DEFAULT_QTD_UPPERCASE = 4;
  public static final int DEFAULT_QTD_SPECIALS = 2;
  public static final int DEFAULT_QTD_NUMBERS = 2;

  /**
   * Descriptografa um texto usando AES com salt.
   *
   * @param cipherText texto criptografado
   * @param salt salt para geração da chave
   * @return texto descriptografado
   */
  public static String decrypt(String cipherText, String salt) {
    try {
      IvParameterSpec ivParameterSpec = new IvParameterSpec(IV);
      SecretKey key = getKeyFromPassword(SECRET_KEY, salt);
      return decrypt(ALGORITHM, cipherText, key, ivParameterSpec);
    } catch (Exception e) {
      throw new RuntimeException(e.getLocalizedMessage(), e);
    }
  }

  public static String decrypt(String algorithm, String cipherText,
                               SecretKey key, IvParameterSpec iv)
    throws NoSuchPaddingException, NoSuchAlgorithmException,
    InvalidAlgorithmParameterException, InvalidKeyException,
    BadPaddingException, IllegalBlockSizeException {

    Cipher cipher = Cipher.getInstance(algorithm);
    cipher.init(Cipher.DECRYPT_MODE, key, iv);
    byte[] plainText = cipher.doFinal(Base64.getDecoder().decode(cipherText));
    return new String(plainText);
  }

  /**
   * Criptografa um texto usando AES com salt.
   *
   * @param plainText texto em claro
   * @param salt salt para geração da chave
   * @return texto criptografado
   */
  public static String encrypt(String plainText, String salt) {
    try {
      IvParameterSpec ivParameterSpec = new IvParameterSpec(IV);
      SecretKey key = getKeyFromPassword(SECRET_KEY, salt);
      return encrypt(ALGORITHM, plainText, key, ivParameterSpec);
    } catch (Exception e) {
      throw new RuntimeException(e.getLocalizedMessage(), e);
    }
  }

  public static String encrypt(String algorithm, String input,
                               SecretKey key, IvParameterSpec iv)
    throws NoSuchAlgorithmException, NoSuchPaddingException,
    InvalidKeyException, InvalidAlgorithmParameterException,
    IllegalBlockSizeException, BadPaddingException {

    Cipher cipher = Cipher.getInstance(algorithm);
    cipher.init(Cipher.ENCRYPT_MODE, key, iv);
    byte[] cipherText = cipher.doFinal(input.getBytes());
    return Base64.getEncoder().encodeToString(cipherText);
  }

  public static IvParameterSpec generateIv() {
    return new IvParameterSpec(IV);
  }

  public static SecretKey getKeyFromPassword(String password, String salt)
    throws NoSuchAlgorithmException, InvalidKeySpecException {

    SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
    KeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), 65536, 256);
    SecretKey secret = new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");
    return secret;
  }

  /**
   * Gera uma senha aleatória segura com caracteres padrão.
   *
   * @return senha gerada
   */
  public static String generateDefaultSecureRandomPassword() {
    return generateSecureRandomPassword(DEFAULT_QTD_NUMBERS,
                                        DEFAULT_QTD_SPECIALS,
                                        DEFAULT_QTD_UPPERCASE,
                                        DEFAULT_QTD_LOWERCASE);
  }

  /**
   * Gera uma senha aleatória com os parâmetros especificados.
   *
   * @param numbers quantidade de números
   * @param specials quantidade de caracteres especiais
   * @param lowercase quantidade de minúsculas
   * @param uppercase quantidade de maiúsculas
   * @return senha gerada
   */
  public static String generateSecureRandomPassword(int numbers,
                                                    int specials,
                                                    int lowercase,
                                                    int uppercase) {
    Stream<Character> pwdStream = Stream
        .concat(getRandomNumbers(numbers),
                Stream.concat(getRandomSpecialChars(specials), Stream
                    .concat(getRandomAlphabets(uppercase, true),
                            getRandomAlphabets(lowercase, false))));
    List<Character> charList = pwdStream.collect(Collectors.toList());
    java.util.Collections.shuffle(charList);
    String password = charList.stream()
        .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
        .toString();
    return password;
  }

  private static Stream<Character> getRandomAlphabets(int count, boolean uppercase) {
    Random random = new SecureRandom();
    IntStream stream = uppercase
        ? random.ints(count, 'A', 'Z')
        : random.ints(count, 'a', 'z');
    return stream.mapToObj(data -> (char) data);
  }

  private static Stream<Character> getRandomNumbers(int count) {
    Random random = new SecureRandom();
    IntStream stream = random.ints(count, 48, 57);
    return stream.mapToObj(data -> (char) data);
  }

  private static Stream<Character> getRandomSpecialChars(int count) {
    Random random = new SecureRandom();
    IntStream stream = random.ints(count, 33, 45);
    return stream.mapToObj(data -> (char) data);
  }
}
