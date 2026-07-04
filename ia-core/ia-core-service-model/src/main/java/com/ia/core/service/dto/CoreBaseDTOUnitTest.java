package com.ia.core.service.dto;


import com.ia.test.CoreBaseUnitTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Set;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public abstract class CoreBaseDTOUnitTest<T> extends CoreBaseUnitTest {
    public  T createDTO(){
        return createFixture(getDtoClass());
    }
    public Class<T> getDtoClass() {
        return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    /**
     * Método que verifica se a classe DTO implementa a interface DTO<?>
     */
    @Test
    @DisplayName("Deve implementar interface DTO<?>")
    public void deveImplementarInterfaceDTO() {
        Class<T> dtoClass = getDtoClass();

        // Verifica se a classe implementa DTO<?> diretamente ou indiretamente
        boolean implementsDTO = false;
        Class<?> currentClass = dtoClass;

        while (currentClass != null && currentClass != Object.class) {
            for (Class<?> interfaceClass : currentClass.getInterfaces()) {
                if (interfaceClass.getName().equals(getDtoInterface().getName())) {
                    implementsDTO = true;
                    break;
                }
            }
            if (implementsDTO) {
                break;
            }
            currentClass = currentClass.getSuperclass();
        }

        assertThat(implementsDTO)
                .withFailMessage("A classe " + dtoClass.getName() +
                        " deve implementar a interface DTO<?>")
                .isTrue();

        // Verifica se o tipo genérico é Serializable quando não é uma entidade
        if (!implementsDTO) {
            return;
        }

        // Tenta obter o tipo genérico da interface DTO
        Type genericSuperclass = dtoClass.getGenericSuperclass();
        if (genericSuperclass instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) genericSuperclass;
            Type[] typeArguments = parameterizedType.getActualTypeArguments();

            // Verifica se o tipo genérico é Serializable
            for (Type typeArg : typeArguments) {
                if (typeArg instanceof Class) {
                    Class<?> typeClass = (Class<?>) typeArg;
                    if (typeClass.equals(Serializable.class)) {
                        return; // OK, implementa DTO<Serializable>
                    }
                }
            }
        }
    }

    protected abstract Class<?> getDtoInterface();

    /**
     * Método que verifica se a classe DTO possui uma innerclass estática chamada CAMPOS
     * que lista todos os campos da classe DTO como constantes estáticas públicas,
     * onde o valor de cada constante é o nome do campo em camelCase.
     * Considera herança tanto da classe DTO quanto da inner class CAMPOS.
     */
    @Test
    @DisplayName("Deve possuir innerclass CAMPOS com todos os campos da classe DTO")
    public void devePossuirInnerClassCampos() {
        Class<T> dtoClass = getDtoClass();
        try {
            Class innerClass = Stream.of(dtoClass.getDeclaredClasses())
                    .filter(c -> "CAMPOS".equals(c.getSimpleName()))
                    .findFirst()
                    .orElseThrow(() -> new NoSuchFieldException("Inner class CAMPOS not found"));

            // Obter todos os campos da classe DTO, incluindo campos herdados
            Set<String> allFieldNames = getAllFieldNames(dtoClass);

            // Obter todos os valores das constantes em CAMPOS, incluindo herdados
            Set<String> camposFieldValues = getAllCamposValues(innerClass);

            // Verificar se todos os campos da DTO têm constante correspondente em CAMPOS
            for (String fieldName : allFieldNames) {
                assertThat(camposFieldValues)
                        .withFailMessage("A classe " + dtoClass.getName() +
                                " não possui a constante para o campo '" + fieldName +
                                "' na innerclass CAMPOS.")
                        .contains(fieldName);
            }
        } catch (NoSuchFieldException e) {
            assertThat(false)
                    .withFailMessage("A classe " + dtoClass.getName() +
                            " não possui a innerclass CAMPOS.")
                    .isTrue();
        }
    }

    /**
     * Obtém todos os nomes de campos da classe DTO, incluindo campos herdados.
     */
    private Set<String> getAllFieldNames(Class<?> clazz) {
        Set<String> fieldNames = new java.util.HashSet<>();
        Class<?> currentClass = clazz;

        while (currentClass != null && currentClass != Object.class) {
            for (java.lang.reflect.Field field : currentClass.getDeclaredFields()) {
                // Ignorar campos estáticos
                if (!java.lang.reflect.Modifier.isStatic(field.getModifiers())) {
                    fieldNames.add(field.getName());
                }
            }
            currentClass = currentClass.getSuperclass();
        }

        return fieldNames;
    }

    /**
     * Obtém todos os valores das constantes estáticas em CAMPOS, incluindo herdados.
     */
    private Set<String> getAllCamposValues(Class<?> camposClass) {
        Set<String> values = new java.util.HashSet<>();
        Class<?> currentClass = camposClass;

        while (currentClass != null && currentClass != Object.class) {
            for (java.lang.reflect.Field field : currentClass.getDeclaredFields()) {
                if (java.lang.reflect.Modifier.isStatic(field.getModifiers()) &&
                    java.lang.reflect.Modifier.isFinal(field.getModifiers())) {
                    try {
                        Object value = field.get(null);
                        if (value instanceof String) {
                            values.add((String) value);
                        }
                    } catch (IllegalAccessException e) {
                        // Ignorar campos não acessíveis
                    }
                }
            }
            currentClass = currentClass.getSuperclass();
        }

        return values;
    }
}
