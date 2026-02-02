package com.example.java_backend.services;

import com.example.java_backend.DTO.UserDTO;
import com.example.java_backend.entities.User;
import com.example.java_backend.repositories.UserRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

// Spring bean - inject vào Controller bằng DI
@Service
public class UserService {
    
    private final UserRepository userRepository;
    
    // Constructor Injection - cách DI được khuyên dùng
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    // CREATE
    public UserDTO createUser(UserDTO userDTO) {
        // Sử dụng "new" cho Entity và DTO (POJO objects)
        User user = new User(userDTO.getName(), userDTO.getAge());
        User savedUser = userRepository.save(user);
        return toDTO(savedUser);
    }
    
    // READ - Get all
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
    
    // READ - Get by ID
    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found: " + id));
        return toDTO(user);
    }
    
    // UPDATE
    public UserDTO updateUser(Long id, UserDTO userDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found: " + id));
        
        user.setName(userDTO.getName());
        user.setAge(userDTO.getAge());
        
        User updatedUser = userRepository.save(user);
        return toDTO(updatedUser);
    }
    
    // DELETE
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found: " + id);
        }
        userRepository.deleteById(id);
    }
    
    // Helper: Entity -> DTO (sử dụng "new" cho DTO)
    private UserDTO toDTO(User user) {
        return new UserDTO(user.getId(), user.getName(), user.getAge());
    }
}
