package com.ia.core.resilience4j.annotation;

import com.ia.core.resilience4j.config.ResilienceAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * Habilita o Resilience4j com aspecto AOP para métodos anotados com {@link Resilient}.
 *
 * <p>Esta anotação deve ser aplicada em uma classe de configuração ou na classe principal da aplicação.
 * Ela importa automaticamente a configuração {@link ResilienceAutoConfiguration} e ativa o processamento
 * de métodos anotados com {@link Resilient} via AOP.</p>
 *
 * @see Resilient
 * @see ResilienceAutoConfiguration
 * @author Israel Araújo
 * @since 1.0.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import(ResilienceAutoConfiguration.class)
public @interface EnableResilience {
}
