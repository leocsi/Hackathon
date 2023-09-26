CREATE DATABASE IF NOT EXISTS test_dial_bank;

USE test_dial_bank;

CREATE TABLE IF NOT EXISTS accounts
(user_id BigInt NOT NULL AUTO_INCREMENT,
first_name VARCHAR(40) NOT NULL,
last_name VARCHAR(40) NOT NULL,
balance FLOAT(40) NOT NULL,
phone_number VARCHAR(40) NOT NULL,
PRIMARY KEY(user_id));