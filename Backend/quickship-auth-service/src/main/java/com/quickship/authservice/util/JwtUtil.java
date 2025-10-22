package com.quickship.authservice.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


@Component
public class JwtUtil {
    @Value("${quickship.security.jwt.secret}")
    private String SECRET;

    @Value("${quickship.security.jwt.expiration}")
    private long EXPIRATION_TIME;


    public String generateToken(String email,Long userId, Set<String> roles)
    {
        System.out.println(SECRET+EXPIRATION_TIME);
        Map<String,Object> claims = new HashMap<>();
         claims.put("userId",userId);
         claims.put("roles",roles);

         return createToken(claims,email);
    }

    public String createToken(Map<String,Object> claims,String email)
    {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+EXPIRATION_TIME))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getSignKey()
    {
        byte[] keyBytes  = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public Claims extractAllClaims(String token)
    {
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJwt(token)
                .getBody();
    }
}
