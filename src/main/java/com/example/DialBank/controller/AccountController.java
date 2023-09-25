package com.example.DialBank.controller;

import com.example.DialBank.model.Account;
import com.example.DialBank.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping
    List<Account> getAll() {
        return accountService.readAllShippers();
    }

}
