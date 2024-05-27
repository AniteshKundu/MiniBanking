package com.minibank;

import java.sql.*;

public class DatabaseManager {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/bank";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "password";

    public int createAccount(String name, String password, double initialDeposit) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "INSERT INTO Accounts (userName, password, balance) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, name);
            stmt.setString(2, password);
            stmt.setDouble(3, initialDeposit);
            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public Account login(int accountNumber, String password) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "SELECT * FROM Accounts WHERE accountNumber = ? AND password = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, accountNumber);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Account(rs.getInt("accountNumber"), rs.getString("userName"), rs.getString("password"), rs.getDouble("balance"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean transferMoney(int fromAccountNumber, int toAccountNumber, double amount) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            conn.setAutoCommit(false);

            String checkBalanceSql = "SELECT balance FROM Accounts WHERE accountNumber = ?";
            PreparedStatement checkBalanceStmt = conn.prepareStatement(checkBalanceSql);
            checkBalanceStmt.setInt(1, fromAccountNumber);
            ResultSet balanceResult = checkBalanceStmt.executeQuery();
            if (balanceResult.next() && balanceResult.getDouble("balance") >= amount) {
                String debitSql = "UPDATE Accounts SET balance = balance - ? WHERE accountNumber = ?";
                PreparedStatement debitStmt = conn.prepareStatement(debitSql);
                debitStmt.setDouble(1, amount);
                debitStmt.setInt(2, fromAccountNumber);
                debitStmt.executeUpdate();

                String creditSql = "UPDATE Accounts SET balance = balance + ? WHERE accountNumber = ?";
                PreparedStatement creditStmt = conn.prepareStatement(creditSql);
                creditStmt.setDouble(1, amount);
                creditStmt.setInt(2, toAccountNumber);
                creditStmt.executeUpdate();

                conn.commit();
                return true;
            } else {
                conn.rollback();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
