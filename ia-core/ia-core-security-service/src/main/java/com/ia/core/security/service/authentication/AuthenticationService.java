package com.ia.core.security.service.authentication;

import com.ia.core.security.model.authentication.AuthenticationResponse;
import com.ia.core.security.service.exception.InvalidPasswordException;
import com.ia.core.security.service.exception.UserNotFountException;
import com.ia.core.security.service.model.user.UserDTO;
import com.ia.core.service.exception.ServiceException;

import java.util.function.BiFunction;
/**
 * Serviço de negócio para gerenciamento de authentication.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a AuthenticationService
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
 */
public interface AuthenticationService<R> {

  AuthenticationResponse login(R request,
                               BiFunction<String, String, Boolean> passwordChecker)
    throws InvalidPasswordException, UserNotFountException;

  boolean initializeSecurity();

  UserDTO createFirstUser(R request)
    throws ServiceException;

}
