package org.juansefdz.services;

import org.juansefdz.entities.Exam;
import org.juansefdz.repositories.QuestionsRepository;
import org.juansefdz.repositories.TestRepository;

import java.util.List;
import java.util.Optional;

public class TestServiceImpl implements TestService {

    private TestRepository testRepository;
    private QuestionsRepository questionsRepository;

    public TestServiceImpl(TestRepository testRepository, QuestionsRepository questionsRepository) {
        this.testRepository = testRepository;
        this.questionsRepository = questionsRepository;
    }

    @Override
    public Optional<Exam> findTestByName(String name) {

        return testRepository.findAll()
                .stream()
                .filter(test -> test.getName().equals(name))
                .findFirst();
    }

    @Override
    public Exam findTestByNameWithQuestions(String testName) {

        Optional<Exam> examOptional = findTestByName(testName);
        Exam exam = null;
        if (examOptional.isPresent()) {
            exam = examOptional.orElseThrow();
            List<String> questions= questionsRepository.findQuestionsByTestId(exam.getId());
            exam.setQuestions(questions);
        }
        return exam;
    }
}
