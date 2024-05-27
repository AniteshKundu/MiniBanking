package com.minibank;

public class Account {
    private int accountNumber;
    private String userName;
    private String password;
    private double balance;

    public Account(int accountNumber, String userName, String password, double balance) {
        this.accountNumber = accountNumber;
        this.userName = userName;
        this.password = password;
        this.balance = balance;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}

