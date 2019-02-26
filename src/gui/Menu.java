package gui;
import java.io.*;
import java.util.*;

import data.Account;
import users.Patient;
import users.User;

public class Menu {


    /**
     * Menu for administration.
     * Can only currently create new accounts.
     */
    private static void adminMenu() {
        Scanner scan = new Scanner(System.in);
        int input = 1;
        while (input != 5) {
            System.out.println("Administrative Menu:\n" +
                    "1. Create a new account\n" +
                    "2. Edit account (Disabled)\n" +
                    "3. Edit appointment (Disabled)\n" +
                    "4. Get user info\n" +
                    "5. Exit\n");
            System.out.print("What would you like to do: ");
            input = scan.nextInt();
            while (input < 1 || input > 5) {
                System.out.print("\n");
                System.out.println("Please input a valid menu item: ");
            }
            if (input == 1) {
                System.out.println("\nCreating a new account.\n");
                System.out.print("Enter username: ");
                String username = scan.next();

                System.out.print("Enter password: ");
                String password = scan.next();

                System.out.print("Enter account type: ");
                int accountType = scan.nextInt();
                Account.createAccount(username, password, accountType);
            } else if (input == 4) {
                System.out.println("\nGetting user info.\n");
                System.out.print("Enter username: ");
                String username = scan.next();

                User acc = Account.getDictionary().get(username);
                while (acc == null) {
                    System.out.print("Please enter a valid username or enter 5 to return to main menu: ");
                    username = scan.next();
                    if (username.equals("5")) {
                        break;
                    }
                }
                acc = Account.getDictionary().get(username);
                if (Account.getDictionary().get(username) != null) {
                    if (!(acc instanceof Patient)) {
                        System.out.println("Account is not a patient account.");
                    } else {
                        ((Patient) acc).getInfo();
                    }
                    System.out.print("\n");
                }
            }
        }
        System.exit(0);
    }

    /**
     * Menu for doctors.
     * Currently has no functionality.
     */
    private static void doctorMenu() {
        Scanner scan = new Scanner(System.in);
        int input = 1;
        while (input != 4) {
            System.out.print("Doctor Menu:\n" +
                    "Work in progress. Enter 4 to exit: ");
            input = scan.nextInt();
            while (input != 4) {
                System.out.print("Please input 4 to exit: ");
                input = scan.nextInt();
            }
        }
        System.exit(0);
    }

    /**
     * Menu for patients.
     * Currently has no functionality.
     */
    private static void patientMenu() {
        Scanner scan = new Scanner(System.in);
        int input = 1;
        while (input != 4) {
            System.out.print("Patient Menu:\n" +
                    "Work in progress. Enter 4 to exit: ");
            input = scan.nextInt();
            while (input != 4) {
                System.out.print("Please input 4 to exit: ");
                input = scan.nextInt();
            }
        }
        System.exit(0);
    }

    /**
     * Opens a menu based on users account type
     * @param user
     */
    public static void prompt(int user) {
        if (user == 1) {
            adminMenu();
        } else if (user == 2) {
            doctorMenu();
        } else if (user == 3) {
            patientMenu();
        }

    }

    /**
     * Prompts user to input username and password
     * @return the account type of the user
     */
    public static int inputLogin() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Login...");
        System.out.print("Username: ");
        String username = scan.nextLine();

        System.out.print("Password: ");
        String password = scan.nextLine();
        System.out.print("\n");
        int accType = Account.login(username, password);

        return accType;
    }
}
