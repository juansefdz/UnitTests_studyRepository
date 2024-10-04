package org.juansefdz.services;

import org.juansefdz.entities.Exam;
import org.juansefdz.repositories.OtherExamenRepository;
import org.juansefdz.repositories.QuestionsRepository;
import org.juansefdz.repositories.TestRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import static org.mockito.Mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;


// Clase de prueba TestServiceImplTest
@ExtendWith(MockitoExtension.class)
class TestServiceImplTest {

    @Mock
    TestRepository testRepository;

    @Mock
    QuestionsRepository questionsRepository;

    @InjectMocks
    TestServiceImpl testServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


/*
    CODIGO SIN INJECTMOKS
    @BeforeEach
    void setUp() { //preparar el entorno de pruebas
        // Crea un mock de TestRepository
        testRepository = mock(TestRepository.class);
        // Crea un mock de QuestionsRepository
        questionsRepository = mock(QuestionsRepository.class);
        // Crea una instancia de TestServiceImpl usando el mock de TestRepository y el mock de QuestionsRepository
        testService = new TestServiceImpl(testRepository, questionsRepository);

    }*/


    @Test
    void findTestByName() {

        // Cuando se llame al método findAll() del mock de TestRepository, entonces retorna la lista de exámenes de prueba
        when(testRepository.findAll()).thenReturn(Data.DATA_EXAMS);

        // Busca un examen por nombre
        Optional<Exam> exam = testServiceImpl.findTestByName("Math");

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
        Optional<Exam> exam = testServiceImpl.findTestByName("Math");
        assertFalse(exam.isPresent());

    }

    @Test
    void TestQuestionsExams() {

        when(testRepository.findAll()).thenReturn(Data.DATA_EXAMS);
        when(questionsRepository.findQuestionsByTestId(anyLong())).thenReturn(Data.DATA_QUESTIONS);

        Exam exam = testServiceImpl.findTestByNameWithQuestions("Math");
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

        Exam exam = testServiceImpl.findTestByNameWithQuestions("Math");
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

        Exam exam = testServiceImpl.findTestByNameWithQuestions("Math");
        assertNull(exam);
        //uso del verify
        verify(testRepository, times(1)).findAll();
        verify(questionsRepository, times(1)).findQuestionsByTestId(anyLong());
    }

    @Test
    void saveTest() {

        //GIVEN - Preparar el entorno de pruebas
        Exam newExam = Data.exam;
        newExam.setQuestions(Data.DATA_QUESTIONS);

        when(testRepository.saveTest(any(Exam.class))).then(new Answer<Exam>() { //clases anonimas

            Long idSequence = 4L;
            @Override
            public Exam answer(InvocationOnMock invocationOnMock) throws Throwable {
                Exam exam = invocationOnMock.getArgument(0);
                exam.setId(idSequence++);
                return exam;
            } //se hace para que el examen tenga un id autoincremental
        });

        //WHEN - Ejecutar el método a probar

        Exam exam = testServiceImpl.saveTest(newExam);

        //THEN - Verificar el resultado

        assertNotNull(exam.getId());
        assertEquals(4L, exam.getId());
        assertEquals("Physics", exam.getName());

        verify(testRepository, times(1)).saveTest(any(Exam.class));
        verify(questionsRepository).saveQuestions(anyList());
    }

    /*
    *   DESARROLLO IMPULSADO AL COMPORTAMIENTO (BDD)- BEHAVIOR DRIVEN DEVELOPMENT
    *
    *  BDD es una técnica de desarrollo de software que se centra en el comportamiento
    * del software en lugar de en su estructura.
    *
    *   GIVEN - precondiciones o condiciones iniciales del entorno de pruebas
    *   WHEN - ejecutar el método a probar
    *   THEN - verificar el resultado
    *
    * */

}
