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

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phoneNumber;
    private String workplace;
    private String photoUrl;
    private boolean termsAccepted;
    private String verificationToken;
    private boolean verified;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "authority")
    private Authority authority;

    public Users(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
