package com.minibank;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AccountTest {
    @Test
    public void testAccountCreation() {
        Account account = new Account(1, "Test User", "password", 1000.0);
        assertEquals(1, account.getAccountNumber());
        assertEquals("Test User", account.getUserName());
        assertEquals("password", account.getPassword());
        assertEquals(1000.0, account.getBalance());
    }

    @Test
    public void testSetBalance() {
        Account account = new Account(1, "Test User", "password", 1000.0);
        account.setBalance(500.0);
        assertEquals(500.0, account.getBalance());
    }
}
