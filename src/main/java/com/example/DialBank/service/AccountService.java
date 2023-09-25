package com.example.DialBank.service;

import com.example.DialBank.model.Account;
import com.example.DialBank.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public List<Account> readAllShippers() {
        return accountRepository.findAll();
    }

}
