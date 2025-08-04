package com.ia.core.security.model.authentication;

import java.util.Collection;
import java.util.Date;

import javax.crypto.SecretKey;

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
  public String generateToken(String userCode, String userName,
                              long expiration,
                              Collection<String> functionalities) {

    Date currentDate = new Date();
    Date expirationDate = new Date(currentDate.getTime() + expiration);
    String token = Jwts.builder()
        .claim(getClaimFunctionalitiesKey(), functionalities)
        .claim(getClaimUserCodeKey(), userName).subject(userCode)
        .issuedAt(currentDate).expiration(expirationDate)
        .signWith(getKey(getSecret())).compact();
    return token;
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
