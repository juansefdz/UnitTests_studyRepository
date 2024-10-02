package org.juansefdz.services;

import org.juansefdz.entities.Test;

public interface TestService {
    Test findTestByName(String name);
}
