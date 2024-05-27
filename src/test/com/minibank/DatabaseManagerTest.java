package com.minibank;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DatabaseManagerTest {
    private DatabaseManager dbManager = new DatabaseManager();

    @Test
    public void testCreateAccount() {
        int accountNumber = dbManager.createAccount("Test User", "password", 1000.0);
        assertNotEquals(-1, accountNumber);
    }

    @Test
    public void testLogin() {
        Account account = dbManager.login(1, "password");
        assertNotNull(account);
    }

    @Test
    public void testTransferMoney() {
        boolean success = dbManager.transferMoney(1, 2, 500.0);
        assertTrue(success);
    }
}
