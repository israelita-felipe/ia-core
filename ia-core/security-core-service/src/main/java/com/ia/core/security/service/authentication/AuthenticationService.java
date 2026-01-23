package com.ia.core.security.service.authentication;

import java.util.function.BiFunction;

import com.ia.core.security.model.authentication.AuthenticationResponse;
import com.ia.core.security.service.exception.InvalidPasswordException;
import com.ia.core.security.service.exception.UserNotFountException;
import com.ia.core.security.service.model.user.UserDTO;
import com.ia.core.service.exception.ServiceException;

/**
 * @author Israel Araújo
 * @param <R>
 * @param <S>
 */
public interface AuthenticationService<R> {

  AuthenticationResponse login(R request,
                               BiFunction<String, String, Boolean> passwordChecker)
    throws InvalidPasswordException, UserNotFountException;

  boolean initializeSecurity();

  UserDTO createFirstUser(R request)
    throws ServiceException;

}
