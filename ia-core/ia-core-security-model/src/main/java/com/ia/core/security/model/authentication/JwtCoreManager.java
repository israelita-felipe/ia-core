package com.ia.core.security.model.authentication;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Collection;
import java.util.Date;
import java.util.Objects;
/**
 * Gerenciador de jwt core.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a JwtCoreManager
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
 */
public class JwtCoreManager
  implements JwtManager {

  private static final String SECRET = "hvIRaPetw4yPjt65kITgF45T6UJ21ss7ppYhBnff55Tttredc9tty";
  private static final String CLAIM_FUNCTIONALITIES_KEY = "claim.functionalities";
  private static final String CLAIM_USER_NAME_KEY = "claim.userName";
  private static final String CLAIM_FUNCTIONALITIES_CONTEXT = "claim.functionalities.context";

  /**
   * Instância singleton com thread-safety garantida via volatile e double-checked locking.
   * <p>
   * O modificador volatile garante visibilidade entre threads. O double-checked locking
   * evita a criação de múltiplas instâncias em ambientes concorrentes.
   *
   * @bugfix SECURITY: Tornado thread-safe com volatile + double-checked locking
   *         (era race condition em get())
   */
  private static volatile JwtCoreManager INSTANCE = null;

  /**
   * Retorna a instância singleton de forma thread-safe.
   * <p>
   * Usa double-checked locking para performance e segurança de threads.
   *
   * @return instância única de JwtCoreManager
   */
  public static JwtCoreManager get() {
    if (INSTANCE == null) {
      synchronized (JwtCoreManager.class) {
        if (INSTANCE == null) {
          INSTANCE = new JwtCoreManager();
        }
      }
    }
    return INSTANCE;
  }

  /**
   *
   */
  /**
   * Construtor privado para singleton.
   * <p>
   * Impede instanciação externa. A inicialização é lazy (na primeira chamada a get()).
   *
   * @bugfix SECURITY: Adicionada documentação de segurança para construtor privado
   */
  private JwtCoreManager() {
    // Construtor privado para singleton
  }

  @Override
  public <T> String generateToken(String userCode, String userName,
                                   long expiration,
                                   Collection<String> functionalities,
                                   Context<T> context) {

    Objects.requireNonNull(userCode, "userCode não pode ser null");
    Objects.requireNonNull(userName, "userName não pode ser null");
    Objects.requireNonNull(functionalities, "functionalities não pode ser null");
    // context pode ser null - é opcional

    Date currentDate = new Date();
    Date expirationDate = new Date(currentDate.getTime() + expiration);

    String token = Jwts.builder()
        .claim(getClaimFunctionalitiesKey(), functionalities)
        .claim(getClaimFunctionalitiesContextKey(), context)
        .claim(getClaimUserCodeKey(), userName)
        .subject(userCode)
        .issuedAt(currentDate)
        .expiration(expirationDate)
        .signWith(getKey(getSecret()))
        .compact();
    return token;
  }

  /**
   * @return {@link #claimFunctionalitiesContext}
   */
  public String getClaimFunctionalitiesContextKey() {
    return CLAIM_FUNCTIONALITIES_CONTEXT;
  }

  /**
   * @return {@link #claimFunctionalitiesKey}
   */
  public String getClaimFunctionalitiesKey() {
    return CLAIM_FUNCTIONALITIES_KEY;
  }

  /**
   * @return
   */
  public String getClaimUserCodeKey() {
    return CLAIM_USER_NAME_KEY;
  }

  @Override
  public Collection<String> getFunctionalitiesFromJWT(String token) {
    Objects.requireNonNull(token, "Token não pode ser null");

    return Jwts.parser()
        .verifyWith(getKey(getSecret()))
        .build()
        .parseSignedClaims(token)
        .getPayload()
        .get(getClaimFunctionalitiesKey(), Collection.class);
  }

  @Override
  public <T> T getFunctionalitiesContextFromJWT(String token,
                                                Class<T> type) {
    Objects.requireNonNull(token, "Token não pode ser null");
    Objects.requireNonNull(type, "Type não pode ser null");

    Object context = Jwts.parser()
        .verifyWith(getKey(getSecret()))
        .build()
        .parseSignedClaims(token)
        .getPayload()
        .get(getClaimFunctionalitiesContextKey(), Object.class);

    Gson gson = new Gson();
    JsonElement jsonElement = gson.toJsonTree(context);

    return gson.fromJson(jsonElement, type);
  }

  public SecretKey getKey(String secret) {
    byte[] bytes = Decoders.BASE64.decode(secret);
    return Keys.hmacShaKeyFor(bytes);
  }

  /**
   * @return {@link #secret}
   */
  public String getSecret() {
    return Encoders.BASE64.encode(SECRET.getBytes());
  }

  @Override
  public String getUserCodeFromJWT(String token) {
    Objects.requireNonNull(token, "Token não pode ser null");

    return Jwts.parser()
        .verifyWith(getKey(getSecret()))
        .build()
        .parseSignedClaims(token)
        .getPayload()
        .getSubject();
  }

  @Override
  public String getUserNameFromJWT(String token) {
    Objects.requireNonNull(token, "Token não pode ser null");

    return Jwts.parser()
        .verifyWith(getKey(getSecret()))
        .build()
        .parseSignedClaims(token)
        .getPayload()
        .get(getClaimUserCodeKey(), String.class);
  }

  @Override
  public boolean validateToken(String token) {
    if (token == null) {
      return false;
    }

    try {
      Jwts.parser()
          .verifyWith(getKey(getSecret()))
          .build()
          .parseSignedClaims(token);
      return true;
    } catch (Exception e) {
      // Log da exceção em produção (não exponha detalhes ao cliente)
      // logger.warn("Token validation failed: {}", e.getMessage());
      return false;
    }
  }
}
