package com.example.adminpaneldemo.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@ToString
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String surName;
    private String password;
    private String email;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "authority_name", referencedColumnName = "name")
    private Authority authority;

    public Users(String email, String password, Authority authority) {
        this.email = email;
        this.password = password;
        this.authority = authority;
    }
}
