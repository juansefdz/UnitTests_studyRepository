package org.juansefdz.services;

import org.juansefdz.entities.Exam;
import org.juansefdz.repositories.TestRepository;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TestServiceImplTest {

    @Test
    void findTestByName() {
        TestRepository testRepository = mock(TestRepository.class);
        TestService testService = new TestServiceImpl(testRepository);
        List<Exam> data = Arrays.asList(
                new Exam(1L, "Math"),
                new Exam(2L, "Languages"),
                new Exam(3L, "Chemistry")
        );

        when(testRepository.findAll()).thenReturn(data);

        Exam exam = testService.findTestByName("Math");


        assertNotNull(exam);
        assertEquals(1L, exam.getId());
        assertEquals("Math", exam.getName());


    }
}