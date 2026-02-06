package com.example.java_backend.DTO;

public class RoleDTO {
    private Long id;
    private String role;

    // Constructor mặc định
    public RoleDTO() {
    }

    // Constructor với tham số
    public RoleDTO(Long id, String role) {
        this.id = id;
        this.role = role;
    }

    public RoleDTO(String role) {
        this.role = role;
    }

    // Getters & Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "RoleDTO{" +
                "id=" + id +
                ", role='" + role + '\'' +
                '}';
    }
}
