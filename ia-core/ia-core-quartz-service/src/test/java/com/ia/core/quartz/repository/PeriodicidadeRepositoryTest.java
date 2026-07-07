package com.ia.core.quartz.repository;

import com.ia.core.quartz.model.periodicidade.repository.PeriodicidadeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Method;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes para a interface PeriodicidadeRepository.
 */
@DisplayName("Testes de PeriodicidadeRepository")
class PeriodicidadeRepositoryTest {

    @Test
    @DisplayName("CT005 - Verificar herança de JpaRepository")
    void testHerancaJpaRepository() {
        assertTrue(JpaRepository.class.isAssignableFrom(PeriodicidadeRepository.class));
    }

    @Test
    @DisplayName("CT006 - Verificar anotação @Repository")
    void testAnotacaoRepository() {
        assertTrue(PeriodicidadeRepository.class.isAnnotationPresent(Repository.class));
    }

    @Test
    @DisplayName("CT001 - Verificar método findByAtivoTrue")
    void testMetodoFindByAtivoTrue() throws NoSuchMethodException {
        Method method = PeriodicidadeRepository.class.getMethod("findByAtivoTrue");
        assertNotNull(method);
        assertEquals(List.class, method.getReturnType());
    }

    @Test
    @DisplayName("CT002 - Verificar método findAtivasAteData")
    void testMetodoFindAtivasAteData() throws NoSuchMethodException {
        Method method = PeriodicidadeRepository.class.getMethod("findAtivasAteData", java.time.LocalDate.class);
        assertNotNull(method);
        assertEquals(List.class, method.getReturnType());
    }

    @Test
    @DisplayName("CT003 - Verificar método findAllWithRecurrence")
    void testMetodoFindAllWithRecurrence() throws NoSuchMethodException {
        Method method = PeriodicidadeRepository.class.getMethod("findAllWithRecurrence");
        assertNotNull(method);
        assertEquals(List.class, method.getReturnType());
    }

    @Test
    @DisplayName("CT004 - Verificar método findAtivasBetweenDates")
    void testMetodoFindAtivasBetweenDates() throws NoSuchMethodException {
        Method method = PeriodicidadeRepository.class.getMethod("findAtivasBetweenDates",
            java.time.LocalDate.class, java.time.LocalDate.class);
        assertNotNull(method);
        assertEquals(List.class, method.getReturnType());
    }
}
