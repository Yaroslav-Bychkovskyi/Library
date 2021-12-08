package com.example.Library;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<TableBook, Integer> {
}
