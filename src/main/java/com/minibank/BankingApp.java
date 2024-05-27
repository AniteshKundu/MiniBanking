package com.minibank;

import java.util.Scanner;

public class BankingApp {
    private static DatabaseManager dbManager = new DatabaseManager();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Welcome to Mini Banking Application");
            System.out.println("1. Create Account");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    createAccount(scanner);
                    break;
                case 2:
                    login(scanner);
                    break;
                case 3:
                    System.out.println("Thank you for using Mini Banking Application!");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }
    }

    private static void createAccount(Scanner scanner) {
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();
        System.out.print("Enter initial deposit: ");
        double initialDeposit = scanner.nextDouble();

        int accountNumber = dbManager.createAccount(name, password, initialDeposit);
        if (accountNumber != -1) {
            System.out.println("Account created successfully! Your account number is: " + accountNumber);
        } else {
            System.out.println("Failed to create account. Please try again.");
        }
    }

    private static void login(Scanner scanner) {
        System.out.print("Enter your account number: ");
        int accountNumber = scanner.nextInt();
        scanner.nextLine();  // Consume newline
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        Account account = dbManager.login(accountNumber, password);
        if (account != null) {
            System.out.println("Login successful! Welcome, " + account.getUserName());
            userMenu(scanner, account);
        } else {
            System.out.println("Invalid account number or password. Please try again.");
        }
    }

    private static void userMenu(Scanner scanner, Account account) {
        while (true) {
            System.out.println("1. View Balance");
            System.out.println("2. Transfer Money");
            System.out.println("3. Logout");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    System.out.println("Your current balance is: " + account.getBalance());
                    break;
                case 2:
                    transferMoney(scanner, account);
                    break;
                case 3:
                    System.out.println("Logged out successfully.");
                    return;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }
    }

    private static void transferMoney(Scanner scanner, Account account) {
        System.out.print("Enter recipient's account number: ");
        int recipientAccountNumber = scanner.nextInt();
        System.out.print("Enter amount to transfer: ");
        double amount = scanner.nextDouble();

        boolean success = dbManager.transferMoney(account.getAccountNumber(), recipientAccountNumber, amount);
        if (success) {
            account.setBalance(account.getBalance() - amount);
            System.out.println("Money transferred successfully!");
        } else {
            System.out.println("Failed to transfer money. Please check your balance or recipient's account number.");
        }
    }
}
