package com.example.Student.shared;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomResponceExcption  extends RuntimeException {

    private int code;
    private String message;

    public static CustomResponceExcption ResourceNotFound(String message) {
        return new CustomResponceExcption(404, message);
    }

    public static CustomResponceExcption BadRequest(String message) {
        return new CustomResponceExcption(400, message);
    }

    public static CustomResponceExcption BadCredentials() {
        return new CustomResponceExcption(401, "Bad Credentials");
    }
}
