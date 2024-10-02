package org.juansefdz.repositories;

import org.juansefdz.entities.Test;

import java.util.List;

public interface TestRepository {

    List<Test> findAll();
}
