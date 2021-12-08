package com.example.Library;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<TableUser, Integer> {
}
