package com.quickship.authservice.entities;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // The primary ID used across all services (Customer/Rider FK)

    // --- Authentication/Identity Fields ---
    @Column(unique = true, nullable = false)
    private String email; // Used as the principal/username

    @Column(nullable = false)
    private String password; // Will store the HASHED password

    // --- Basic Profile Fields (for Auth/Auditing) ---
    @Column(nullable = false)
    private String name;

    @Column(name = "phone_number", unique = true)
    private String phoneNumber;

    // --- Role Management (Many-to-Many Relationship) ---
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles", // The required junction table
            joinColumns = @JoinColumn(name = "user_id"), // FK in user_roles pointing to 'users'
            inverseJoinColumns = @JoinColumn(name = "role_id") // FK in user_roles pointing to 'roles'
    )
    private Set<Role> roles = new HashSet<>();

    // --- Auditing/State Fields ---
    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted = false; // Soft delete flag

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    // Helper method to add a role
    public void addRole(Role role) {
        this.roles.add(role);
    }
}