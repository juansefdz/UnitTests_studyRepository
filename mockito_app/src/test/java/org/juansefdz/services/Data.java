package org.juansefdz.services;

import org.juansefdz.entities.Exam;

import java.util.Arrays;
import java.util.List;

public class Data {
    public final static List<Exam> DATA_EXAMS = Arrays.asList(
            new Exam(1L, "Math"),
            new Exam(2L, "Languages"),
            new Exam(3L, "Chemistry")
    );

    public final static List<String> DATA_QUESTIONS = Arrays.asList(
            "What is the result of 2+2?",
            "What is the language of Colombia?",
            "What is the atomic number of Oxygen?"
    );

}
