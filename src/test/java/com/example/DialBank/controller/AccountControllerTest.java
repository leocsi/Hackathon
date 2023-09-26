package com.example.DialBank.controller;

import com.example.DialBank.model.Account;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles({"test"})

class AccountControllerTest {
    private static final String ACCOUNTS_ENDPOINT_URL = "/api";
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    public final List<Account> SAMPLE_ACCOUNTS = List.of(
            new Account(3L, "a", "b", 250000f, "2536-12764"),
            new Account(4L, "c", "d", 6000000f, "4862-76884")

    );
    @Test
    void testGetAll() {
        //populate test database
        populateDB();
        try {
            //check for size and content equality
            List<Account> accounts = getAllAccounts();
            assertEquals(accounts.size(),SAMPLE_ACCOUNTS.size());

            List<Account> accountsCopy = new ArrayList<>(SAMPLE_ACCOUNTS);
            for (int i = 0; i<accounts.size(); i++){
                accountsCopy.get(i).setUser_id(accounts.get(i).getUser_id());
                assertEquals(accountsCopy.get(i).json(), accounts.get(i).json());
            }

            clearDB(accounts);
        } catch (Exception e) {
            assert false;
        }
    }

    @Test
    void testGetAccount() {
        try {
            Long id = addNewAccount(SAMPLE_ACCOUNTS.get(0).json());
            List<Account> accounts = getAllAccounts();

            String JSON = mockMvc.perform(get(ACCOUNTS_ENDPOINT_URL+ "/account/"+id)).andExpect(status().isOk())
                    .andReturn()
                    .getResponse()
                    .getContentAsString();

            assertEquals(JSON, accounts.get(0).json());
            deleteAccountByID(id);
        }catch (Exception e){
            assert false;
        }
    }

    @Test
    void testCreateAccount() {
        try {
            Account newAcc = SAMPLE_ACCOUNTS.get(0);
            Long id = addNewAccount(newAcc.json());
            List<Account> accounts = getAllAccounts();
            assertEquals(accounts.size(), 1);

            newAcc.setUser_id(accounts.get(0).getUser_id());

            assertEquals(newAcc.json(), accounts.get(0).json());
            deleteAccountByID(id);
        } catch (Exception e) {
            assert false;
        }
    }

    private List<Account> getAllAccounts() throws Exception {
        String JSON = mockMvc.perform(get(ACCOUNTS_ENDPOINT_URL+ "/accounts"))
                .andReturn()
                .getResponse()
                .getContentAsString();
        return objectMapper.readValue(JSON, new TypeReference<>() {});
    }

    private Long addNewAccount(String body) throws Exception{
        String responseJSON = this.mockMvc.perform(post(ACCOUNTS_ENDPOINT_URL + "/account/add")
                        .header("Content-Type", "application/json")
                        .content(body))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();
        Account acc = objectMapper.readValue(responseJSON, new TypeReference<>() {});
        return acc.getUser_id();
    }

    private void deleteAccountByID(Long id)throws Exception{
        this.mockMvc.perform(delete(ACCOUNTS_ENDPOINT_URL + "/account/delete/"+id)
                        .header("Content-Type", "application/json"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }
    private Long[] populateDB(){
        Long[] ids = new Long[SAMPLE_ACCOUNTS.size()];
        for(int i = 0; i<SAMPLE_ACCOUNTS.size(); i++){
            Account acc = SAMPLE_ACCOUNTS.get(i);
            try {
                ids[i] = addNewAccount(acc.json());
            } catch (Exception e) {
                System.out.println("Error in populating the database");
                throw new RuntimeException(e.getMessage());
            }
        }
        return ids;
    }
    private void clearDB(List<Account> accounts){
        for(Account acc : accounts){
            try {
                deleteAccountByID(acc.getUser_id());
            } catch (Exception e) {
                System.out.println("Error in clearing the database");
                throw new RuntimeException(e.getMessage());
            }
        }
    }
}