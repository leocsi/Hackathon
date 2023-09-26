package com.example.DialBank.controller;

import com.example.DialBank.model.Account;
import com.example.DialBank.service.AccountService;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/accounts")
    List<Account> getAll() {
        return accountService.readAllAccounts();
    }

    @GetMapping("/account/{id}")
    Account getAccount(@PathVariable Long id){
        return new Account(); //Code here
    }

    @PostMapping("/account/add")
    Account createAccount(@RequestBody Account account){
        return new Account();
    }

    @DeleteMapping("account/delete/{id}")
    Account deleteAccount(@PathVariable Long id){
        return new Account();
    }

    @PutMapping("account/")
    Account changeAccount(@RequestBody Account account){
        return new Account();
    }

    @PutMapping("account/deposit/{id}-{amount}")
    Account depositMoney(@PathVariable Long id, @PathVariable Float amount){
        return new Account();
    }

    @PutMapping("account/withdraw/{id}-{amount}")
    Account withdrawMoney(@PathVariable Long id, @PathVariable Float amount){
        return new Account();
    }
}

