package org.juansefdz.repositories;

import java.util.List;

public interface QuestionsRepository {
    List<String> findQuestionsByTestId(Long testId);
}
