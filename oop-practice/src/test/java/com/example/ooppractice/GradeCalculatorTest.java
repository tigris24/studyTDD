package com.example.ooppractice;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

public class GradeCalculatorTest {


    @DisplayName("평균 학점을 계산한다.")
    @Test
    void calculateGradeTest() {
        List<Course> courses = List.of(new Course("OOP", 3, "A+"),
                new Course("자료구조", 3, "A+"));

        GradeCalculator gradeCalculator = new GradeCalculator(new Courses(courses));
        double gradeResult = gradeCalculator.calculatorGrade();

        Assertions.assertThat(gradeResult).isEqualTo(4.5);

    }


}
