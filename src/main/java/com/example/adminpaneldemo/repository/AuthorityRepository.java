package com.example.adminpaneldemo.repository;

import com.example.adminpaneldemo.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
    Optional<Object> findByName(String name);
}
