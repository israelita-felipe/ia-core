package com.ia.core.security.service.model.user;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Encoder de password
 *
 * @author Israel Araújo
 */
public interface UserPasswordEncoder {

  final String SECRET_KEY = "5s6ad4f&%$#.";
  final String ALGORITHM = "AES/CBC/PKCS5Padding";
  static final byte[] iv = new byte[] { 0, 1, 1, 1, 0, 0, 0, 1, 1, 0, 0, 1,
      1, 1, 0, 0 };
  /**
   * Número de caracteres caixa baixa
   */
  public static final int DEFAULT_QTD_LOWERCASE = 2;
  /**
   * Número de caracteres caixa alta
   */
  public static final int DEFAULT_QTD_UPPERCASE = 4;
  /**
   * Número de caracteres especiais
   */
  public static final int DEFAULT_QTD_SPECIALS = 2;
  /**
   * Número de caracteres numéricos
   */
  public static final int DEFAULT_QTD_NUMBERS = 2;

  public static String decrypt(String cipherText, String salt) {
    try {
      IvParameterSpec ivParameterSpec = generateIv();
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
    byte[] plainText = cipher
        .doFinal(Base64.getDecoder().decode(cipherText));
    return new String(plainText);
  }

  public static String encrypt(String plainText, String salt) {
    try {
      IvParameterSpec ivParameterSpec = generateIv();
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

  /**
   * Gera uma senha padrão com os valores de {@link #DEFAULT_QTD_LOWERCASE}
   * {@link #DEFAULT_QTD_NUMBERS}
   * {@link #DEFAULT_QTD_SPECIALS}{@link #DEFAULT_QTD_UPPERCASE}
   *
   * @return a senha gerada
   */
  public static String generateDefaultSecureRandomPassword() {
    return generateSecureRandomPassword(DEFAULT_QTD_NUMBERS,
                                        DEFAULT_QTD_SPECIALS,
                                        DEFAULT_QTD_UPPERCASE,
                                        DEFAULT_QTD_LOWERCASE);
  }

  public static IvParameterSpec generateIv() {
    return new IvParameterSpec(iv);
  }

  public static SecretKey generateKey(int n)
    throws NoSuchAlgorithmException {
    KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
    keyGenerator.init(n);
    SecretKey key = keyGenerator.generateKey();
    return key;
  }

  /**
   * Gera uma senha
   *
   * @param numbers   número de caracteres numéricos
   * @param specials  número de caracteres especiais
   * @param lowercase número de caracteres caixa baixa
   * @param uppercase número de caracteres caixa alta
   * @return a senha gerada
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
    Collections.shuffle(charList);
    String password = charList.stream()
        .collect(StringBuilder::new, StringBuilder::append,
                 StringBuilder::append)
        .toString();
    return password;
  }

  public static SecretKey getKeyFromPassword(String password, String salt)
    throws NoSuchAlgorithmException, InvalidKeySpecException {

    SecretKeyFactory factory = SecretKeyFactory
        .getInstance("PBKDF2WithHmacSHA256");
    KeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(),
                                  65536, 256);
    SecretKey secret = new SecretKeySpec(factory.generateSecret(spec)
        .getEncoded(), "AES");
    return secret;
  }

  /**
   * Caracteres alfabéticos
   *
   * @param count quantidade
   * @param check caixa alta
   * @return {@link Stream}
   */
  public static Stream<Character> getRandomAlphabets(int count,
                                                     boolean check) {

    Stream<Character> lowerUpperStream;

    // for lower case stream
    if (check == true) {
      // create instance of SecureRandom
      Random random = new SecureRandom();

      // use ints() method of random to get IntStream of lower case letters of
      // the specified length
      IntStream lCaseStream = random.ints(count, 'a', 'z');
      lowerUpperStream = lCaseStream.mapToObj(data -> (char) data);
    }
    // for upper case stream
    else {
      // create instance of SecureRandom
      Random random = new SecureRandom();

      // use ints() method of random to get IntStream of upper case letters of
      // the specified length
      IntStream uCaseStream = random.ints(count, 'A', 'Z');
      lowerUpperStream = uCaseStream.mapToObj(data -> (char) data);
    }

    // return lowerUpperStream to main() method
    return lowerUpperStream;

  }

  /**
   * Caracteres numéricos
   *
   * @param count quantidade
   * @return {@link Stream}
   */
  public static Stream<Character> getRandomNumbers(int count) {

    Stream<Character> numberStream;

    // create instance of SecureRandom
    Random random = new SecureRandom();

    // use ints() method of random to get IntStream of number of the specified
    // length
    IntStream numberIntStream = random.ints(count, 48, 57);
    numberStream = numberIntStream.mapToObj(data -> (char) data);

    // return number stream to main() method
    return numberStream;
  }

  /**
   * Caracteres especiais
   *
   * @param count quantidade
   * @return {@link Stream}
   */
  public static Stream<Character> getRandomSpecialChars(int count) {
    Random random = new SecureRandom();
    IntStream specialChars = random.ints(count, 33, 45);
    return specialChars.mapToObj(data -> (char) data);
  }

  /**
   * Encode the raw password. Generally, a good encoding algorithm applies a
   * SHA-1 or greater hash combined with an 8-byte or greater randomly generated
   * salt.
   *
   * @param rawPassword Senha que deve ser encodada
   * @return {@link String} contendo a senha encodada
   */
  String encode(CharSequence rawPassword);

  /**
   * Verify the encoded password obtained from storage matches the submitted raw
   * password after it too is encoded. Returns true if the passwords match,
   * false if they do not. The stored password itself is never decoded.
   *
   * @param rawPassword     the raw password to encode and match
   * @param encodedPassword the encoded password from storage to compare with
   * @return true if the raw password, after encoding, matches the encoded
   *         password from storage
   */
  boolean matches(CharSequence rawPassword, String encodedPassword);

  /**
   * Returns true if the encoded password should be encoded again for better
   * security, else false. The default implementation always returns false.
   *
   * @param encodedPassword the encoded password to check
   * @return true if the encoded password should be encoded again for better
   *         security, else false.
   */
  default boolean upgradeEncoding(String encodedPassword) {
    return false;
  }
}
