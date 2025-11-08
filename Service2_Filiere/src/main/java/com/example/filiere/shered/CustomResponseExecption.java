package com.example.filiere.shered;

import io.swagger.v3.oas.annotations.callbacks.Callback;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor

public class CustomResponseExecption extends RuntimeException{
    private int code;
    private String message;

    public static CustomResponseExecption ResourceNotFound(String message) {
        return new CustomResponseExecption(404, message);
    }

    public static CustomResponseExecption BadRequest(String message) {
        return new CustomResponseExecption(400, message);
    }

    public static CustomResponseExecption BadCredentials() {
        return new CustomResponseExecption(401, "Bad Credentials");
    }

}
