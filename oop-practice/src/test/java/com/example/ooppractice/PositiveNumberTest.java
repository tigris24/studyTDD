package com.example.ooppractice;

import com.example.ooppractice.arithmetic.PositiveNumber;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatCode;

public class PositiveNumberTest {

    @ParameterizedTest
    @ValueSource(ints = {0, -1})
    public void createTest(int value){
        assertThatCode(() -> new PositiveNumber(value))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("0 또는 음수를 전달할 수 없습니다.");

    }
}
