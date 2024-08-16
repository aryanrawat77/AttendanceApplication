package com.example.attendance.service;

import com.example.attendance.model.User;
import com.example.attendance.repository.UserRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public User createUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User must not be null");
        }
        return userRepository.save(user);
    }

    @Transactional
    public User checkIn(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setCheckInTimestamp(LocalDateTime.now());
            return userRepository.save(user);
        }
        throw new EntityNotFoundException("User not found with id: " + id);
    }

    @Transactional
    public User checkOut(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setCheckOutTimestamp(LocalDateTime.now());
            return userRepository.save(user);
        }
        throw new EntityNotFoundException("User not found with id: " + id);
    }

    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public boolean deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Transactional
    public User updateUser(Long id, User userDetails) {
        if (userDetails == null) {
            throw new IllegalArgumentException("User details must not be null");
        }
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setName(userDetails.getName());
            user.setLocation(userDetails.getLocation());
            user.setCheckInTimestamp(userDetails.getCheckInTimestamp());
            user.setCheckOutTimestamp(userDetails.getCheckOutTimestamp());
            return userRepository.save(user);
        }
        throw new EntityNotFoundException("User not found with id: " + id);
    }

    public byte[] exportToCsv(List<User> users) throws IOException {
        if (users == null) {
            throw new IllegalArgumentException("User list must not be null");
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try (CSVPrinter printer = new CSVPrinter(new OutputStreamWriter(out, StandardCharsets.UTF_8),
                CSVFormat.DEFAULT.withHeader("ID", "Name", "Location", "CheckInTimestamp", "CheckOutTimestamp"))) {
            for (User user : users) {
                printer.printRecord(user.getId(), user.getName(), user.getLocation(),
                        user.getCheckInTimestamp(), user.getCheckOutTimestamp());
            }
        }
        return out.toByteArray();
    }
}
