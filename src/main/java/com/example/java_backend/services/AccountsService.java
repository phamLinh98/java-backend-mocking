package com.example.java_backend.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.java_backend.DTO.AccountsDTO;
import com.example.java_backend.entities.Accounts;
import com.example.java_backend.repositories.AccountsRepository;

@Service
public class AccountsService {
        private final AccountsRepository accountsRepository;

        public AccountsService(AccountsRepository accountsRepository) {
                this.accountsRepository = accountsRepository;
        }

        // public AccountsDTO createAccounts(AccountsDTO accountsDTO){
        // Accounts accounts = new Accounts(accountsDTO.getName(),
        // accountsDTO.getRole());
        // Accounts savedAccounts = accountsRepository.save(accounts);
        // return toAccountsDTO(savedAccounts);
        // }

        public List<AccountsDTO> getAllAccounts() {
                return accountsRepository.findAll()
                                .stream()
                                .map(this::toAccountsDTO)
                                // .collect(Collectors.toList());
                                .toList();
        }

        private AccountsDTO toAccountsDTO(Accounts accounts) {
                return new AccountsDTO(accounts.getId(), accounts.getName());
        }
}
