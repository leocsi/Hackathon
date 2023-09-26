package com.example.DialBank.service;

import com.example.DialBank.exceptions.AccountAlreadyExistsException;
import com.example.DialBank.model.Account;
import com.example.DialBank.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public List<Account> readAllAccounts() {
        return accountRepository.findAll();
    }

    public Account readAccountByID(Long id) throws AccountNotFoundException{
        return accountRepository.findById(id).orElseThrow(()-> new AccountNotFoundException("Could not find specified account. Try a valid ID.")
        );
    }

    //Ciaran
    public Account addNewAccount(Account account){
        if (accountRepository.existsById(account.getUser_id())){
            throw new AccountAlreadyExistsException("This account already exists!");
        }
        return accountRepository.save(account);
    }

    //Ciaran
    public Long deleteAccount(Long id) throws AccountNotFoundException {
        accountRepository.findById(id).orElseThrow(()-> new AccountNotFoundException("Could not find an account #" + id + ". Try a valid ID."));
        accountRepository.deleteById(id);
        return id;
    }

    public Account updateAccount(Long id, Account account) throws AccountNotFoundException{
        Account goalAccount = this.readAccountByID(id);
        account.setUser_id(goalAccount.getUser_id());
        return accountRepository.save(account);
    }
}
