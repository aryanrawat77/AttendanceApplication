package com.example.attendance.repository;

import com.example.attendance.model.User; // Ensure this import is present
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
