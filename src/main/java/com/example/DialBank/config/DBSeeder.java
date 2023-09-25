package com.example.DialBank.config;
import com.example.DialBank.model.Account;
import com.example.DialBank.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;

@Configuration
@Profile({"mysql"})
public class DBSeeder implements CommandLineRunner {
    public static final List<Account> DB_SEEDER_ACCOUNTS = List.of(
            new Account(43625L, "Jane", "Doe", 250000f, "2536-12764"),
            new Account(15625L, "John", "Smith", 6000000f, "4862-76884")

    );
    @Autowired
    private AccountRepository accountRepository;

    @Override
    public void run(String... args) throws Exception{
        accountRepository.saveAll(DB_SEEDER_ACCOUNTS);
    }
}
