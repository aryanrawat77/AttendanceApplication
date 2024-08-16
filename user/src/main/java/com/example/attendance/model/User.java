package com.example.attendance.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")  // Optional: Define a table name explicitly
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = false)
    private String location;

    @Column(name = "check_in_timestamp")  // Optional: Define column names explicitly
    private LocalDateTime checkInTimestamp;

    @Column(name = "check_out_timestamp")  // Optional: Define column names explicitly
    private LocalDateTime checkOutTimestamp;

    // Default constructor
    public User() {
    }

    // Parameterized constructor (Optional, for convenience)
    public User(String name, String location, LocalDateTime checkInTimestamp, LocalDateTime checkOutTimestamp) {
        this.name = name;
        this.location = location;
        this.checkInTimestamp = checkInTimestamp;
        this.checkOutTimestamp = checkOutTimestamp;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDateTime getCheckInTimestamp() {
        return checkInTimestamp;
    }

    public void setCheckInTimestamp(LocalDateTime checkInTimestamp) {
        this.checkInTimestamp = checkInTimestamp;
    }

    public LocalDateTime getCheckOutTimestamp() {
        return checkOutTimestamp;
    }

    public void setCheckOutTimestamp(LocalDateTime checkOutTimestamp) {
        this.checkOutTimestamp = checkOutTimestamp;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", checkInTimestamp=" + checkInTimestamp +
                ", checkOutTimestamp=" + checkOutTimestamp +
                '}';
    }
}
