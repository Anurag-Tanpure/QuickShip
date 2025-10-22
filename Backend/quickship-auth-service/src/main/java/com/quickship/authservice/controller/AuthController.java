package com.quickship.authservice.controller;

import com.quickship.authservice.dto.AuthResponse;
import com.quickship.authservice.dto.RegisterRequest;
import com.quickship.authservice.dto.LoginRequest;

import com.quickship.authservice.service.AuthService;
import com.quickship.authservice.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

   private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/test")
    public String test()
    {

        return "hellow');";
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> registerUser(@RequestBody RegisterRequest request)
    {
        try{
            AuthResponse authResponse=  authService.register(request);
            return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
        }catch (IllegalArgumentException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (NoSuchElementException e) {
            return new ResponseEntity("Invalid role specified.", HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> loginUser(@RequestBody LoginRequest request) {
        try {
            System.out.println(request.toString());
            AuthResponse response = authService.login(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // Catches UsernameNotFoundException or BadCredentialsException from Spring Security
            return new ResponseEntity("Invalid credentials or user not found.", HttpStatus.UNAUTHORIZED);
        }
    }

    // An optional secure endpoint for testing authentication
    @GetMapping("/validate")
    public ResponseEntity<String> validateToken() {
        // This endpoint will be protected by the API Gateway later.
        // If a request reaches here, the token is valid.
        return ResponseEntity.ok("Token is valid. User is authenticated.");
    }

}
