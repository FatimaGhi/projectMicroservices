package com.example.auth;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class CreatpasswordForMe {

    public static void main(String[] args) {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawPassword = "12345";
        String hash = encoder.encode(rawPassword);
        System.out.println("Hash: " + hash);
    }
}

