package org.juansefdz.repositories;

import java.util.List;

public interface QuestionsRepository {

    void saveQuestions(List<String> questions);
    List<String> findQuestionsByTestId(Long testId);
}
