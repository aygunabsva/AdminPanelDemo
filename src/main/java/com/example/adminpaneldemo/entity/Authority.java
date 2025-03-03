package com.example.adminpaneldemo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Authority {
    @Id
    private String name;
}
