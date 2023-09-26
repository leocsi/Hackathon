package com.example.DialBank.controller;

import com.example.DialBank.exceptions.AccountNotFoundException;

import com.example.DialBank.exceptions.InsufficientFundsException;

import com.example.DialBank.model.Account;
import com.example.DialBank.model.ExceptionJSON;
import com.example.DialBank.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("api/")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/accounts")
    List<Account> getAll() {
        return accountService.readAllAccounts();
    }

    @GetMapping(value= "/account/{id}", produces= MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ResponseEntity<String> getAccount(@PathVariable Long id){
        try {
            Account requestedAccount = accountService.readAccountByID(id);
            return ResponseEntity.status(HttpStatus.OK).body(requestedAccount.json()); //Code here
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionJSON(e.getMessage()).json());
        }
    }

    @PostMapping(value="/account/add", produces= MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> createAccount(@RequestBody Account account){
        try {
            Account createdAccount = accountService.addNewAccount(account);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdAccount.json()); //Code here
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new ExceptionJSON(e.getMessage()).json());
        }
    }

    @DeleteMapping("account/delete/{id}")
    ResponseEntity<String> deleteAccount(@PathVariable Long id){
        try {
            accountService.deleteAccount(id);
            return ResponseEntity.ok("Account #" + id + " deleted successfully.");
        } catch (Exception ex) {
            //return ResponseEntity.badRequest().body("Error: Account #" + id + " could not be deleted.");
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new ExceptionJSON(ex.getMessage()).json());
        }

    }

    @PutMapping("account/")
    Account changeAccount(@PathVariable Long id, @RequestBody Account account){
        try {
            return accountService.updateAccount(id, account);
        } catch (AccountNotFoundException e) {
            throw new ResponseStatusException(NOT_FOUND, e.getMessage());
        }
    }

    @PutMapping(value = "account/deposit/{id}-{amount}", produces= MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> depositMoney(@PathVariable Long id, @PathVariable Float amount){
        try {
            accountService.depositToAccount(id, amount);
            return ResponseEntity.status(HttpStatus.OK).body("Money has been deposited");
        } catch (AccountNotFoundException e) {
            throw new ResponseStatusException(NOT_FOUND, e.getMessage());
        }
    }

    @PutMapping(value = "account/withdraw/{id}-{amount}", produces= MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> withdrawMoney(@PathVariable Long id, @PathVariable Float amount){
        try {
            accountService.withdrawFromAccount(id, amount);
            return ResponseEntity.status(HttpStatus.OK).body("Money has been withdrawn");
        } catch (InsufficientFundsException e) {
            return ResponseEntity.status(BAD_REQUEST).body("Insufficient funds");
        } catch (AccountNotFoundException e) {
            throw new ResponseStatusException(NOT_FOUND, e.getMessage());
        }
    }
}

