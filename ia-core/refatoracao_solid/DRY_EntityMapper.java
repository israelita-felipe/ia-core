package refatoracao_solid;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Exemplo de DRY com anotação personalizada para mappers.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface EntityMapper {
    Class<?>[] uses() default {};
}

@EntityMapper(uses = {ContaMapper.class})
public interface MetaMapper extends BaseEntityMapper<Meta, MetaDTO> {}

interface ContaMapper {}
interface BaseEntityMapper<T, D> {}
class Meta {}
class MetaDTO {}
