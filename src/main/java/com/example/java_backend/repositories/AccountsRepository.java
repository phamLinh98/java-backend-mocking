package com.example.java_backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.java_backend.entities.Accounts;

@Repository
public interface AccountsRepository extends JpaRepository<Accounts, Long> {

}
