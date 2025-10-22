package com.quickship.authservice.util;

import io.jsonwebtoken.Jwt;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Test {

    public static void main(String[] args) {
        JwtUtil j = new JwtUtil();
        String token = j.generateToken("test", 1L, Set.of("CUSTOMER"));
        System.out.println(token);

    }
}
