package com.quickship.authservice.service;

import com.quickship.authservice.dto.AuthResponse;
import com.quickship.authservice.dto.LoginRequest;
import com.quickship.authservice.dto.RegisterRequest;
import com.quickship.authservice.entities.Role;
import com.quickship.authservice.entities.User;
import com.quickship.authservice.repository.RoleRepository;
import com.quickship.authservice.repository.UserRepository;
import com.quickship.authservice.util.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;


    public AuthService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    public AuthResponse register(RegisterRequest request)
    {
        if(userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("email already registered");

        }
        Role requestedRole = roleRepository.findByRoleName(request.getRole().toUpperCase())
                    .orElseThrow(() -> new NoSuchElementException("Role not found: " + request.getRole()));

            // 3. Create User Entity
            User user = new User();
            user.setEmail(request.getEmail());
            user.setPassword(passwordEncoder.encode(request.getPassword())); // HASH password
            user.setName(request.getName());
            user.setPhoneNumber(request.getPhoneNumber());
            user.addRole(requestedRole);

            User savedUser = userRepository.save(user);
            Set<String> roles = savedUser.getRoles().stream().map(Role::getRoleName).collect(Collectors.toSet());
            String token = jwtUtil.generateToken(savedUser.getEmail(), savedUser.getId(), roles);

            return new AuthResponse(
                    token,
                    savedUser.getId(),
                    savedUser.getEmail(),
                    requestedRole.getRoleName() // Return the primary role
            );
        }

    public AuthResponse login(LoginRequest request) {
        // 1. Authenticate using Spring Security's AuthenticationManager
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        System.out.println("code chala 3");

        // 2. If authentication succeeds, load the full user object
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found after successful authentication."));

        System.out.println("code chaal 4");
        // 3. Generate Token
        Set<String> roles = user.getRoles().stream().map(Role::getRoleName).collect(Collectors.toSet());


        System.out.println("code chala 5");
        System.out.println(roles);
        String token = jwtUtil.generateToken(user.getEmail(), user.getId(), roles);

        System.out.println("code chala 6");
        // Find the "highest" role to return (e.g., Rider over Customer) for response
        String primaryRole = roles.contains("RIDER") ? "RIDER" : roles.contains("CUSTOMER") ? "CUSTOMER" : "ADMIN";

        return new AuthResponse(
                token,
                user.getId(),
                user.getEmail(),
                primaryRole
        );
    }
}
