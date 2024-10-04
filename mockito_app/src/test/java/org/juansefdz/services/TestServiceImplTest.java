package org.juansefdz.services;

import org.juansefdz.entities.Exam;
import org.juansefdz.repositories.OtherExamenRepository;
import org.juansefdz.repositories.QuestionsRepository;
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
    private TestRepository testRepository;
    TestService testService;
    private QuestionsRepository questionsRepository;

    @BeforeEach
    void setUp() { //preparar el entorno de pruebas
        // Crea un mock de TestRepository
        testRepository = mock(TestRepository.class);
        // Crea un mock de QuestionsRepository
        questionsRepository = mock(QuestionsRepository.class);
        // Crea una instancia de TestServiceImpl usando el mock de TestRepository y el mock de QuestionsRepository
        testService = new TestServiceImpl(testRepository, questionsRepository);

    }

    @Test
    void findTestByName() {

        // Cuando se llame al método findAll() del mock de TestRepository, entonces retorna la lista de exámenes de prueba
        when(testRepository.findAll()).thenReturn(Data.DATA_EXAMS);

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

    @Test
    void TestQuestionsExams() {

        when(testRepository.findAll()).thenReturn(Data.DATA_EXAMS);
        when(questionsRepository.findQuestionsByTestId(anyLong())).thenReturn(Data.DATA_QUESTIONS);

        Exam exam = testService.findTestByNameWithQuestions("Math");
        assertEquals(3, exam.getQuestions().size());
        assertTrue(exam.getQuestions().contains("What is the result of 2+2?"));
    }


    //VERIFY
    /*
     * El verify se utiliza para verificar que un método de un mock ha sido llamado con los argumentos correctos.
     *
     * */
    @Test
    void TestQuestionsExamsVerify() {

        when(testRepository.findAll()).thenReturn(Data.DATA_EXAMS);
        when(questionsRepository.findQuestionsByTestId(anyLong())).thenReturn(Data.DATA_QUESTIONS);

        Exam exam = testService.findTestByNameWithQuestions("Math");
        assertEquals(3, exam.getQuestions().size());
        assertTrue(exam.getQuestions().contains("What is the result of 2+2?"));

        //uso del verify
        verify(testRepository, times(1)).findAll();
        verify(questionsRepository, times(1)).findQuestionsByTestId(1L);
    }


    @Test
    void TestNotExistExamsVerify() {

        when(testRepository.findAll()).thenReturn(Collections.emptyList());
        when(questionsRepository.findQuestionsByTestId(anyLong())).thenReturn(Data.DATA_QUESTIONS);

        Exam exam = testService.findTestByNameWithQuestions("Math");
        assertNull(exam);
        //uso del verify
        verify(testRepository, times(1)).findAll();
        verify(questionsRepository, times(1)).findQuestionsByTestId(anyLong());
    }


}
