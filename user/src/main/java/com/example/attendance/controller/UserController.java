package com.example.attendance.controller;

import com.example.attendance.model.User;
import com.example.attendance.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        try {
            User createdUser = userService.createUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
        } catch (Exception e) {
            // Log exception (using a logging framework like SLF4J or Log4j)
            // e.g., logger.error("Error creating user", e);
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        try {
            User updatedUser = userService.updateUser(id, userDetails);
            if (updatedUser != null) {
                return ResponseEntity.ok(updatedUser);
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            // Log exception
            // e.g., logger.error("Error updating user with id: " + id, e);
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/{id}/checkin")
    public ResponseEntity<User> checkIn(@PathVariable Long id) {
        try {
            User user = userService.checkIn(id);
            if (user != null) {
                return ResponseEntity.ok(user);
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            // Log exception
            // e.g., logger.error("Error checking in user with id: " + id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/{id}/checkout")
    public ResponseEntity<User> checkOut(@PathVariable Long id) {
        try {
            User user = userService.checkOut(id);
            if (user != null) {
                return ResponseEntity.ok(user);
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            // Log exception
            // e.g., logger.error("Error checking out user with id: " + id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        try {
            List<User> users = userService.getAllUsers();
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            // Log exception
            // e.g., logger.error("Error retrieving users", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        try {
            boolean isRemoved = userService.deleteUser(id);
            if (isRemoved) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            // Log exception
            // e.g., logger.error("Error deleting user with id: " + id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/export/csv")
    public ResponseEntity<byte[]> exportToCsv() {
        try {
            List<User> users = userService.getAllUsers();
            byte[] csvData = userService.exportToCsv(users);
            return ResponseEntity.ok()
                    .header("Content-Disposition", "attachment; filename=users.csv")
                    .body(csvData);
        } catch (Exception e) {
            // Log exception
            // e.g., logger.error("Error exporting users to CSV", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
