package com.ia.core.security.service.authentication;

import java.util.function.BiFunction;

import com.ia.core.security.model.authentication.AuthenticationResponse;
import com.ia.core.security.service.exception.InvalidPasswordException;
import com.ia.core.security.service.exception.UserNotFountException;

/**
 * @author Israel Araújo
 * @param <R>
 * @param <S>
 */
public interface AuthenticationService<R> {

  AuthenticationResponse login(R request,
                               BiFunction<String, String, Boolean> passwordChecker)
    throws InvalidPasswordException, UserNotFountException;

}
