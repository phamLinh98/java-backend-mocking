package com.example.java_backend.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.java_backend.DTO.AccountsDTO;
import com.example.java_backend.services.AccountsService;

@RestController
@RequestMapping("/api/accounts")
public class AccountsController {

  private final AccountsService accountsService;

  public AccountsController(AccountsService accountsService) {
    this.accountsService = accountsService;
  }

  @GetMapping
  public ResponseEntity<List<AccountsDTO>> getAllAccounts() {
    List<AccountsDTO> accounts = accountsService.getAllAccounts();
    return ResponseEntity.ok(accounts);
  }
}
