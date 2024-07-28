package com.example.ooppractice.generator;

public class WrongFixedPasswordGenerator implements PasswordGenerator {
    @Override
    public String generatePassword() {
        return "ab";
    }
}
