package com.example.DialBank.controller;

import com.example.DialBank.exceptions.AccountNotFoundException;
import com.example.DialBank.model.Account;
import com.example.DialBank.model.ExceptionJSON;
import com.example.DialBank.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
<<<<<<< Updated upstream
import org.springframework.http.MediaType;
=======
>>>>>>> Stashed changes
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.print.attribute.standard.Media;
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

    //Ciaran
    @DeleteMapping("account/delete/{id}")
    ResponseEntity<String> deleteAccount(@PathVariable Long id){
        //return new Account();
        try {
            accountService.deleteAccount(id);
            return ResponseEntity.ok("Account #" + id + " deleted successfully.");
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body("Error: Account #" + id + " could not be deleted.");
        }

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

