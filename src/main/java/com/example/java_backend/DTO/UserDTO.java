package com.example.java_backend.DTO;

// DTO = Data Transfer Object - sử dụng "new" để tạo instance
public class UserDTO {
    
    private Long id;
    private String name;
    private Integer age;
    
    // Constructor rỗng
    public UserDTO() {}
    
    // Constructor đầy đủ
    public UserDTO(Long id, String name, Integer age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }
    
    // Getters & Setters
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
    
    public Integer getAge() {
        return age;
    }
    
    public void setAge(Integer age) {
        this.age = age;
    }
}
