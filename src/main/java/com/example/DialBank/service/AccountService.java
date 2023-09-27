package com.example.DialBank.service;

import com.example.DialBank.exceptions.AccountAlreadyExistsException;
import com.example.DialBank.exceptions.AccountNotFoundException;
import com.example.DialBank.exceptions.InsufficientFundsException;
import com.example.DialBank.model.Account;
import com.example.DialBank.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public List<Account> readAllAccounts() {
        return accountRepository.findAll();
    }

    public Account readAccountByID(Long id) {
        return accountRepository.findById(id).orElseThrow(()-> new AccountNotFoundException("Could not find specified account. Try a valid ID.")
        );
    }

    public Account addNewAccount(Account account){
        if (accountRepository.existsById(account.getUser_id())){
            throw new AccountAlreadyExistsException("This account already exists!");
        }
        return accountRepository.save(account);
    }

    public Long deleteAccount(Long id){
        accountRepository.findById(id).orElseThrow(()-> new AccountNotFoundException("Could not find an account #" + id + ". Try a valid ID."));
        accountRepository.deleteById(id);
        return id;
    }

    public Account updateAccount(Long id, Account account) {
        Account goalAccount = this.readAccountByID(id);
        account.setUser_id(goalAccount.getUser_id());
        return accountRepository.save(account);
    }

    public Account withdrawFromAccount(Long id, Float amount) throws InsufficientFundsException {
        Account account = readAccountByID(id);
        Float updatedBalance = account.getBalance() - amount;
        if (updatedBalance < 0) {
            throw new InsufficientFundsException("Insufficient funds");
        }
        account.setBalance(account.getBalance() - amount);
        return accountRepository.save(account);
    }

    public Account depositToAccount(Long id, Float amount) {
        Account account = readAccountByID(id);
        account.setBalance(account.getBalance() + amount);
        return accountRepository.save(account);
    }
}
