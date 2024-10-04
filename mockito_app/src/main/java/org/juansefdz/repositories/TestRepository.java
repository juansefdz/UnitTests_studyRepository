package org.juansefdz.repositories;

import org.juansefdz.entities.Exam;

import java.util.List;

public interface TestRepository {
    Exam saveTest(Exam exam);
    List<Exam> findAll();
}
