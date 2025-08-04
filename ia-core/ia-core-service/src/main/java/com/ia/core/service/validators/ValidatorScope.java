package com.ia.core.service.validators;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Anotação que define o escopo de um validador
 *
 * @author Israel Araújo
 */
@Retention(RUNTIME)
@Target(TYPE)
@Component
@Scope("singleton")
public @interface ValidatorScope {

}
