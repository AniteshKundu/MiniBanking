CREATE DATABASE IF NOT EXISTS bank;
USE bank;

CREATE TABLE IF NOT EXISTS Accounts (
    accountNumber INT PRIMARY KEY AUTO_INCREMENT,
    userName VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    balance DECIMAL(15, 2) NOT NULL
);
