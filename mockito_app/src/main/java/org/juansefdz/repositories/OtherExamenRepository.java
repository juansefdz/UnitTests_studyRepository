package org.juansefdz.repositories;

import org.juansefdz.entities.Exam;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class OtherExamenRepository implements TestRepository {
    @Override
    public List<Exam> findAll() {
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        return null;
    }
}
