package org.juansefdz.repositories;

import org.juansefdz.entities.Test;

import java.util.Arrays;
import java.util.List;

public class TestRepositoryImpl implements TestRepository {
    @Override
    public List<Test> findAll() {
        return Arrays.asList(new Test(1L, "Test 1"), new Test(2L, "Test 2"), new Test(3L, "Test 3"));
    }
}
