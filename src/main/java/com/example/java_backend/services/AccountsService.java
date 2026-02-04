package com.example.java_backend.services;

import org.springframework.stereotype.Service;

import com.example.java_backend.DTO.AccountsDTO;
import com.example.java_backend.entities.Accounts;
import com.example.java_backend.repositories.AccountsRepository;

@Service
public class AccountsService {
        private final AccountsRepository accountsRepository;

        public AccountsService(AccountsRepository accountsRepository){
                this.accountsRepository = accountsRepository;
        } 

        public AccountsDTO createAccounts(AccountsDTO accountsDTO){
            Accounts accounts = new Accounts(accountsDTO.getName(), accountsDTO.getRole());
            Accounts savedAccounts = accountsRepository.save(accounts);
            return toAccountsDTO(savedAccounts);
        }

        private AccountsDTO toAccountsDTO(Accounts accounts){
                return new AccountsDTO(accounts.getId(), accounts.getName());
        }
}
