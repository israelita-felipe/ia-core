package com.ia.core.security.model.authentication;

import java.util.Collection;
import java.util.Date;

import javax.crypto.SecretKey;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;

/**
 * @author Israel Araújo
 */
public class JwtCoreManager
  implements JwtManager {

  private static final String SECRET = "hvIRaPetw4yPjt65kITgF45T6UJ21ss7ppYhBnff55Tttredc9tty";
  private static final String CLAIM_FUNCTIONALITIES_KEY = "claim.functionalities";
  private static final String CLAIM_USER_NAME_KEY = "claim.userName";
  private static final String CLAIM_FUNCTIONALITIES_CONTEXT = "claim.functionalities.context";

  private static JwtCoreManager INSTANCE = null;

  public static JwtCoreManager get() {
    if (INSTANCE == null) {
      INSTANCE = new JwtCoreManager();
    }
    return INSTANCE;
  }

  /**
   *
   */
  private JwtCoreManager() {
  }

  @Override
  public <T> String generateToken(String userCode, String userName,
                                  long expiration,
                                  Collection<String> functionalities,
                                  Context<T> context) {

    Date currentDate = new Date();
    Date expirationDate = new Date(currentDate.getTime() + expiration);
    String token = Jwts.builder()
        .claim(getClaimFunctionalitiesKey(), functionalities)
        .claim(getClaimFunctionalitiesContextKey(), context)
        .claim(getClaimUserCodeKey(), userName).subject(userCode)
        .issuedAt(currentDate).expiration(expirationDate)
        .signWith(getKey(getSecret())).compact();
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
    return Jwts.parser().verifyWith(getKey(getSecret())).build()
        .parseSignedClaims(token).getPayload()
        .get(getClaimFunctionalitiesKey(), Collection.class);
  }

  @Override
  public <T> T getFunctionalitiesContextFromJWT(String token,
                                                Class<T> type) {
    Object context = Jwts.parser().verifyWith(getKey(getSecret())).build()
        .parseSignedClaims(token).getPayload()
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
    return Jwts.parser().verifyWith(getKey(getSecret())).build()
        .parseSignedClaims(token).getPayload().getSubject();
  }

  @Override
  public String getUserNameFromJWT(String token) {
    return Jwts.parser().verifyWith(getKey(getSecret())).build()
        .parseSignedClaims(token).getPayload()
        .get(getClaimUserCodeKey(), String.class);
  }

  @Override
  public boolean validateToken(String token) {
    try {
      Jwts.parser().verifyWith(getKey(getSecret())).build()
          .parseSignedClaims(token);
      return true;
    } catch (Exception e) {
      return false;
    }
  }
}
