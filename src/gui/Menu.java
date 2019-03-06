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
        while (input != 0) {
            System.out.println("Administrative Menu:\n" +
                    "1. Create a new account\n" +
                    "2. Edit account\n" +
                    "3. Add appointment\n" +
                    "4. Get user info\n" +
                    "5. Assign patient to Doctor\n"+
                    "6. List Appointments for User\n"+
                    "0. Exit\n");
            System.out.print("What would you like to do: ");
            input = scan.nextInt();
            while (input < 0 || input > 6) {
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
                    
            } else if (input == 2) {
            	System.out.println("Editing Info\n ...press enter 0 to exit");
            	System.out.print("Enter Username: ");
            	String userName = scan.next();
            	User acc = Account.getDictionary().get(userName);
            	while (acc == null) {
            		if (userName.equals("0")) break;
                    System.out.print("Please enter a valid username or press 0:");
                    userName = scan.next();
            	}
            	if (!(userName.equals("0"))) acc.setName();
            // figure out how to change specialty
            
        	} else if (input == 3) {
        		System.out.println("Adding appointment for user\n ...press 0 to exit");
        		System.out.print("Enter username: ");
        		String userName = scan.next();
        		User acc = Account.getDictionary().get(userName);
        		while (acc == null) {
            		if (userName.equals("0")) break;
            		System.out.print("Enter Username: ");
            		userName = scan.next();
            		acc = Account.getDictionary().get(userName);
        		 } 	 
        		if((userName.equals("0")) == false) {
        			acc.schedule.addAppointment();
        		}
            
            
    		} else if (input == 4) {
                System.out.println("\nGetting user info.\n");
                System.out.print("Enter username: ");
                String username = scan.next();

                User acc1 = Account.getDictionary().get(username);
                while (acc1 == null) {
                    System.out.print("Please enter a valid username or enter 5 to return to main menu: ");
                    username = scan.next();
                    if (username.equals("5")) {
                        break;
                    }
                }
                acc1 = Account.getDictionary().get(username);
                if (Account.getDictionary().get(username) != null) {
                    acc1.displayAccountType();
                    acc1.displayName();
                    System.out.print("\n");
                }
            } else if (input == 5) {
            	System.out.println("Assigning Patient to Doctor, press 0 (for both) to Exit");
            	System.out.print("Enter Doctor's Username: ");
            	String patientUsername = "";
            	String doctorUsername = scan.next();
            	User doctor = Account.getDictionary().get(doctorUsername);
            	while (doctor.getAccountType() != 2) {
            		if (doctorUsername == "0") {
            			patientUsername = "0";
            			break;
            		} else {
            			doctorUsername = scan.next();
            			doctor = Account.getDictionary().get(doctorUsername);
            		}
            	}
            	System.out.print("Enter Patient's Username: ");
            	patientUsername = scan.next();
            	User patient = Account.getDictionary().get(patientUsername);
            	while(patient.getAccountType() != 3) {
            		patientUsername = scan.next();
            		if (patientUsername == "0") {
            			doctorUsername = "0";
            			break;
            		} else {
            			patientUsername = scan.next();
            			patient = Account.getDictionary().get(patientUsername);
            		}
            	} if (!(doctorUsername.equals("0") || patientUsername.equals("0"))) { 
	            	doctor.list.add(patient);
	            	System.out.print("Assigned patient: ");
	            	patient.displayName();
	            	System.out.print("To Doctor: ");
	            	doctor.displayName();
            	}
            } else if (input == 6) {
            	System.out.println("Listing Appointments, enter in the name of the user or press 0");
            	System.out.print("Enter Username: ");
            	String userName = scan.next();
            	while(!(userName.contentEquals("0"))) {
            		User account = Account.getDictionary().get(userName);
            		if (account != null) {
            			account.schedule.listAppointments();
            			break;
            		} else {
            			System.out.println("Please enter a valid username, or press 0 to exit");
            			userName = scan.next();
            		}
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
                    "0. Exit");
            input = scan.nextInt();
            if (input == 0) break;
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
                    "4. Exit");
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
