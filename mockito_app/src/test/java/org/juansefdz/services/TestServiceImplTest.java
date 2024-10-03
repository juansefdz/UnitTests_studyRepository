package org.juansefdz.services;

import org.juansefdz.entities.Exam;
import org.juansefdz.repositories.OtherExamenRepository;
import org.juansefdz.repositories.TestRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;




// Clase de prueba TestServiceImplTest
@Nested
class TestServiceImplTest {
    TestRepository testRepository;
    TestService testService;

    @BeforeEach
    void setUp() { //preparar el entorno de pruebas
        // Crea un mock de TestRepository
         testRepository = mock(TestRepository.class);
        // Crea una instancia de TestServiceImpl usando el mock de TestRepository
         testService = new TestServiceImpl(testRepository);

    }

    @Test
    void findTestByName() {

        // Lista de exámenes de prueba
        List<Exam> data = Arrays.asList(
                new Exam(1L, "Math"),
                new Exam(2L, "Languages"),
                new Exam(3L, "Chemistry")
        );

        // Cuando se llame al método findAll() del mock de TestRepository, entonces retorna la lista de exámenes de prueba
        when(testRepository.findAll()).thenReturn(data);

        // Busca un examen por nombre
        Optional<Exam> exam = testService.findTestByName("Math");

        //verifica que el examen existe
        assertTrue(exam.isPresent());
        //verifica que el examen es el correcto por su id
        assertEquals(1L, exam.orElseThrow().getId());
        //verifica que el examen es el correcto por su nombre
        assertEquals("Math", exam.get().getName());
    }

    @Test
    void findTestByNameEmptyList() {

        // Lista de exámenes vacía
        List<Exam> data = Collections.emptyList();

        // Cuando se llame al método findAll() del mock de TestRepository, entonces retorna la lista de exámenes vacía
        when(testRepository.findAll()).thenReturn(data);
        Optional<Exam> exam = testService.findTestByName("Math");

        assertFalse(exam.isPresent());

    }
}
