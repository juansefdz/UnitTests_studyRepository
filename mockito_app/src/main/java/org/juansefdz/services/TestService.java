package org.juansefdz.services;

import org.juansefdz.entities.Exam;

import java.util.Optional;

public interface TestService {
    Optional<Exam> findTestByName(String name);
    Exam findTestByNameWithQuestions(String name);
    Exam saveTest(Exam exam);
}
