package com.example.ooppractice.generator;

public class CorrectFixedPasswordGenerator implements PasswordGenerator {
    @Override
    public String generatePassword() {
        return "abcdefgh";
    }
}
