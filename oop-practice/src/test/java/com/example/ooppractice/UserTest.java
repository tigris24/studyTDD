package com.example.ooppractice;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

class UserTest {

    @DisplayName("패스워드를 초기화한다.")
    @Test
    void passwordTest() {
        User user = new User();
        user.initPassword(() -> "abcdefgh");
        assertThat(user.getPassword()).isNotNull();
    }

    @DisplayName("패스워드가 요구사항에 부합하지 않아 초기화되지 않는다.")
    @Test
    void passwordTest2() {
        User user = new User();
        user.initPassword(() -> "ab");
        assertThat(user.getPassword()).isNull();
    }
}