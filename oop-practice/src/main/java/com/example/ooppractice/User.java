package com.example.ooppractice;

import com.example.ooppractice.generator.PasswordGenerator;

public class User {

    private String password;

    public void initPassword(PasswordGenerator passwordGenerator){
//        RandomPasswordGenerator rp = new RandomPasswordGenerator();
        String password = passwordGenerator.generatePassword();

        if(password.length()>=8 && password.length()<=12){
            this.password = password;
        }
    }
    public String getPassword() {
        return password;
    }

}
