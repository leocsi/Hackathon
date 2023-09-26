package com.example.DialBank.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Long user_id;
    @Column(name = "first_name", nullable = false)
    @NotBlank(message = "'first name' is mandatory!")
    private String first_name;
    @Column(name = "last_name", nullable = false)
    @NotBlank(message = "'last name' is mandatory!")
    private String last_name;
    @Column(name = "balance", nullable = false)
    @NotNull(message = "'balance' is mandatory!")
    private Float balance;
    @Column(name = "phone_number", nullable = false)
    @NotBlank(message = "'phone_number' is mandatory!")
    private String phone_number;

    public Account() {
    }

    public Account(Long user_id, String first_name, String last_name, Float balance, String phone_number) {
        this.user_id = user_id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.balance = balance;
        this.phone_number = phone_number;
    }

    public Long getUser_id() {
        return user_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public Float getBalance() {
        return balance;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public void setBalance(Float balance) {
        this.balance = balance;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String json() throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        return ow.writeValueAsString(this);
    }
}
