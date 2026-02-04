package com.example.java_backend.DTO;

public class AccountsDTO {
        
        private Long id;
        private String name;

        public AccountsDTO() {}

        public AccountsDTO(Long id, String name){
                this.id = id;
                this.name = name;
        }

        public Long getId(){
                return id;
        }

        public void setId(Long id){
                this.id = id;
        }

        public String getName() {
                return name;
        }

        public void setName(String name){
                this.name = name;
        }
}
