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
    public Exam findTestByName(String name) {

        Optional<Exam> testOptional = testRepository.findAll()
                .stream()
                .filter(test -> test.getName().equals(name))
                .findFirst();

        return testOptional.orElse(null);
    }
}
