package org.juansefdz.repositories;

import org.juansefdz.entities.Exam;

import java.util.List;

public interface TestRepository {

    List<Exam> findAll();
}
