package com.example.auth.controller;

import com.example.auth.Entites.LoginRequest;
import com.example.auth.service.authService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/API")
public class authController {

    private authService authServic;

    public authController(authService authServic) {
        this.authServic = authServic;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {

            Map<String, String> tokens = authServic.login(loginRequest);

            return ResponseEntity.ok(tokens);

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Invalid username or password"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Something went wrong"));
        }
    }



    @PostMapping("/refresh")
    public ResponseEntity<Map<String, String>> refreshToken(@RequestHeader("Authorization") String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("Error", "Refresh token missing or invalid"));
        }

        String refreshToken = authorizationHeader.substring(7);
        Map<String, String> tokens =  authServic.refrech(refreshToken);

        if (tokens.containsKey("Error")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(tokens);
        }

        return ResponseEntity.ok(tokens);
    }
}
