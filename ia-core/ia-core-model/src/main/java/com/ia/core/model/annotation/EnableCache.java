package com.ia.core.model.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import jakarta.persistence.Cacheable;

/**
 * Habilitação de cache herdável
 *
 * @author Israel Araújo
 */
@Documented
@Retention(RUNTIME)
@Target(TYPE)
@Cacheable()
@Inherited
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "entityCache")
public @interface EnableCache {

}
