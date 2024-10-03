package org.juansefdz.services;

import org.juansefdz.entities.Exam;

public interface TestService {
    Exam findTestByName(String name);
}
