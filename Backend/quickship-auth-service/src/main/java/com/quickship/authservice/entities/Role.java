package com.quickship.authservice.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "roles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // roleName stores the constant string values: "CUSTOMER", "RIDER", "ADMIN"
    @Column(name = "role_name", unique = true, nullable = false)
    private String roleName;

    public Role(String roleName) {
        this.roleName = roleName;
    }
}