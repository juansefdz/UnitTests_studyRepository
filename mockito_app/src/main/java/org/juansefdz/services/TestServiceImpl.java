package org.juansefdz.services;

import org.juansefdz.entities.Exam;
import org.juansefdz.repositories.TestRepository;

import java.util.Optional;

public class TestServiceImpl implements TestService {

    private TestRepository testRepository;

    public TestServiceImpl(TestRepository testRepository) {
        this.testRepository = testRepository;
    }

    @Override
    public Optional<Exam> findTestByName(String name) {

        return testRepository.findAll()
                .stream()
                .filter(test -> test.getName().equals(name))
                .findFirst();
    }
}
