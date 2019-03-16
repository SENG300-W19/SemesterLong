package gui;

import java.io.*;
import java.util.*;

import data.Account;
import users.Patient;
import users.*;

public class Menu {


    /**
     * gui.Menu for administration.
     * Can only currently create new accounts.
     */
    private static void adminMenu() {
        Scanner scan = new Scanner(System.in);
        int input = 1;
        while (input != 0) {
            System.out.println("Administrative gui.Menu:\n" +
                    "1. Create a new account\n" +
                    "2. Edit account\n" +
                    "3. Add appointment\n" +
                    "4. Get user info\n" +
                    "5. List Appointments for User\n"+
                    "6. List Users\n" +
                    "0. Exit\n");
            System.out.print("What would you like to do: ");
            input = scan.nextInt();
            while (input < 0 || input > 6) {
                System.out.print("\n");
                System.out.print("Please input a valid menu item: ");
                input = scan.nextInt();
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
                /*
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
            	*/
                User user = userExists();
                if (user != null) {
                    editUser(user);
                }
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
        			acc.getSchedule().addAppointment();
        		}
            
            
    		} else if (input == 4) {
                User user = userExists();
                if (user != null) {
                    reviewUser(user);
                }
                /*
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
                } */
            } else if (false) {
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
            		if (patientUsername.equals("0")) {
            			doctorUsername = "0";
            			break;
            		} else {
            			patientUsername = scan.next();
            			patient = Account.getDictionary().get(patientUsername);
            		}
            	} if (!(doctorUsername.equals("0") || patientUsername.equals("0"))) { 
	            //	doctor.;
	            	System.out.print("Assigned patient: ");
	            	patient.displayName();
	            	System.out.print("To Doctor: ");
	            	doctor.displayName();
            	}
            } else if (input == 5) {
            	System.out.println("Listing Appointments, enter in the name of the user or press 0");
            	System.out.print("Enter Username: ");
            	String userName = scan.next();
            	while(!(userName.contentEquals("0"))) {
            		User account = Account.getDictionary().get(userName);
            		if (account != null) {
            			account.getSchedule().listAppointments();
            			break;
            		} else {
            			System.out.println("Please enter a valid username, or press 0 to exit");
            			userName = scan.next();
            		}
            	}
            } else if (input == 6) {
                listUsers();
            }
            /*else if (input == 7) {
                Patient patient = (Patient) Account.getDictionary().get("patient1");
                patient.listDoctors();
            }*/
        }
        System.exit(0);
    }


    /**
     * Method so that the admin can list all the patient and doctor accounts currently in the HashMap
     */
    private static void listUsers() {
        Scanner scan = new Scanner(System.in);
        System.out.println("List Users:\n" +
                "1. List Patients\n" +
                "2. List Doctors\n" +
                "3. List All\n" +
                "0. Return to Main gui.Menu");
        System.out.print("What would you like to do: ");
        int input = scan.nextInt();

        while (input < 0 || input > 3) {
            System.out.print("\n");
            System.out.print("Please input a valid menu item: ");
            input = scan.nextInt();
        }

        switch (input) {
            case 1:
                System.out.println("Listing Patients...");
                HashMap<String, User> dictionary = Account.getDictionary();
                for (Map.Entry<String, User> entry : dictionary.entrySet()) {
                    if (entry.getValue() instanceof Patient) {
                        System.out.println(entry.getValue().getUsername() + ": " +
                                entry.getValue().getFirstName() + " " +
                                entry.getValue().getLastName());
                    }
                }
                break;
            case 2:
                System.out.println("Listing Doctors...");
                HashMap<String, User> dictionary1 = Account.getDictionary();
                for (Map.Entry<String, User> entry : dictionary1.entrySet()) {
                    if (entry.getValue() instanceof Doctor) {
                        System.out.println(entry.getValue().getUsername() + ": " +
                                entry.getValue().getFirstName() + " " +
                                entry.getValue().getLastName());
                    }
                }
                break;
            case 3:
                System.out.println("Listing All Users...");
                HashMap<String, User> dictionary2 = Account.getDictionary();
                for (Map.Entry<String, User> entry : dictionary2.entrySet()) {
                    if (!(entry.getValue() instanceof Admin)) {
                        System.out.println(entry.getValue().getUsername() + ": " +
                                entry.getValue().getFirstName() + " " +
                                entry.getValue().getLastName());
                    }
                }
                break;
            default:
                break;
        }
        System.out.println(" ");
    }

    private static void reviewUser(User username) {
        System.out.println("User " + username.getUsername() + " information:");
        System.out.print("Name: " + username.getFirstName() + " " +
                username.getLastName() + "\n");
        switch(username.getAccountType()) {
            case 1:
                break;
            case 2:
                ((Doctor) username).listPatients();
                System.out.print("Specialty: " + ((Doctor) username).getSpecialty() + "\n");
                System.out.print("Surgeon status: " + ((Doctor) username).isSurgeon());
                break;
            case 3:
                ((Patient) username).listDoctors();
                break;
            default:
                break;
        }
        Scanner scan = new Scanner(System.in);
        System.out.print("Would you like to edit user information (y or n)? ");
        String input = scan.next();

        // Loop to get the correct "y" or "n" input
        while (!input.equals("y") && !input.equals("n")) {
            System.out.print("Please input y or n: ");
            input = scan.next();
        }

        if (input.equals("y")) {
            editUser(username);
        } else if (input.equals("n")) {
            System.out.println("Returning to main menu.");
        }
    }

    private static void editUser(User username) {
        switch(username.getAccountType()) {
            case 1:
                editAdmin(username);
                break;
            case 2:
                editDoctor(username);
                break;
            case 3:
                editPatient(username);
                    break;
        }
    }

    private static void editAdmin(User username) {
        Scanner scan = new Scanner(System.in);
        int input = -1;
        while (input != 0) {
            System.out.println("Edit " + username.getUsername() + " information:\n" +
                    "1. Edit first name\n" +
                    "2. Edit last name\n" +
                    "0. Return to main menu");
            input = scan.nextInt();
            while (input < 0 || input > 2) {
                System.out.print("Please input a valid menu option: ");
                input = scan.nextInt();
            }
            switch (input) {
                case 1:
                    editFirstName(username);
                    break;
                case 2:
                   editLastName(username);
                    break;
                default:
                    break;
            }
        }
    }

    private static void editDoctor(User username) {
        Scanner scan = new Scanner(System.in);
        int input = -1;
        while (input != 0) {
            System.out.println("Edit " + username.getUsername() + " information:\n" +
                    "1. Edit first name\n" +
                    "2. Edit last name\n" +
                    "3. Add patient\n" +
                    "4. Remove patient\n" +
                    "5. Change specialty\n" +
                    "6. Toggle Surgeon Status\n" +
                    "0. Return to main menu");
            input = scan.nextInt();
            while (input < 0 || input > 6) {
                System.out.print("Please input a valid menu option: ");
                input = scan.nextInt();
            }
            switch (input) {
                case 1:
                    editFirstName(username);
                    break;
                case 2:
                    editLastName(username);
                    break;
                case 3:
                    addPatient(username);
                    break;
                case 4:
                    removePatient(username);
                    break;
                case 5:
                    changeSpecialty(username);
                    break;
                case 6:
                    toggleSurgeon(username);
                    break;
                default:
                    break;
            }
        }
    }

    private static void editPatient(User username) {
        Scanner scan = new Scanner(System.in);
        int input = -1;
        while (input != 0) {
            System.out.println("Edit " + username.getUsername() + " information:\n" +
                    "1. Edit first name\n" +
                    "2. Edit last name\n" +
                    "3. Add doctor (Disabled)\n" +
                    "4. Remove doctor (Disabled)\n" +
                    "0. Return to main menu");
            input = scan.nextInt();
            while (input < 0 || input > 2) {
                System.out.print("Please input a valid menu option: ");
                input = scan.nextInt();
            }
            switch (input) {
                case 1:
                    editFirstName(username);
                    break;
                case 2:
                    editLastName(username);
                    break;
                case 3:
                    addDoctor(username);
                    break;
                case 4:
                    removeDoctor(username);
                    break;
                default:
                    break;
            }
        }
    }

    private static void editFirstName(User username) {
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter new first name: ");
        String str = scan.next();
        System.out.print("Change first name from " + username.getFirstName()
                + " to " + str + "? (y or n)");
        String yn = scan.next();
        while (!yn.equals("y") && !yn.equals("n")) {
            System.out.print("Please input y or n: ");
            yn = scan.next();
        }
        if (yn.equals("y")) {
            username.setFirstName(str);
        }
    }

    private static void editLastName(User username) {
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter new last name: ");
        String str2 = scan.next();
        System.out.print("Change last name from " + username.getFirstName()
                + " to " + str2 + "? (y or n)");
        String yn2 = scan.next();
        while (!yn2.equals("y") && !yn2.equals("n")) {
            System.out.print("Please input y or n: ");
            yn2 = scan.next();
        }
        if (yn2.equals("y")) {
            username.setFirstName(str2);
        }
    }

    private static void addDoctor(User username) {
        User doctor = userExists();
        if (doctor instanceof Doctor) {
            ((Patient) username).addDoctor((Doctor) doctor);
        } else {
            System.out.println("User is not a patient. Returning to edit menu.");
        }
    }

    private static void removeDoctor(User username) {
        ((Doctor) username).removePatient();
    }

    private static void addPatient(User username) {
        User patient = userExists();
        if (patient instanceof Patient) {
            ((Doctor) username).addPatient((Patient) patient);
        } else {
            System.out.println("User is not a doctor. Returning to edit menu.");
        }
    }

    private static void removePatient(User username) {
        ((Patient) username).removeDoctor();
    }

    private static void changeSpecialty(User username) {
        ((Doctor) username).setSpecialty();
    }

    private static void toggleSurgeon(User username) {
        ((Doctor) username).toggleSurgeon();
    }




    /**
     * Method to check if a username exists
     * @return boolean for if a username exists
     */
    private static User userExists() {
        Scanner scan = new Scanner(System.in);
        System.out.print("Please input username: ");
        String username = scan.next();
        boolean exists = Account.getDictionary().containsKey(username);
        while (!exists) {
            System.out.println("That username does not exist.");
            System.out.print("Would you like to try again (y or n)? ");
            String input = scan.next();

            // Loop to get the correct "y" or "n" input
            while (!input.equals("y") && !input.equals("n")) {
                System.out.print("Please input y or n: ");
            }

            if (input.equals("y")) {
                System.out.print("Please input a valid username: ");
                username = scan.next();
                exists = Account.getDictionary().containsKey(username);
            } else if (input.equals("n")) {
                return null;
            }
        }
        return Account.getDictionary().get(username);
    }

    /**
     * gui.Menu for doctors.
     * Currently has no functionality.
     */
    private static void doctorMenu() {
        Scanner scan = new Scanner(System.in);
        int input = 1;
        while (input != 4) {
            System.out.print("Doctor gui.Menu:\n" +
                    "0. Exit");
            input = scan.nextInt();
            if (input == 0) break;
        }
        System.exit(0);
    }

    /**
     * gui.Menu for patients.
     * Currently has no functionality.
     */
    private static void patientMenu() {
        Scanner scan = new Scanner(System.in);
        int input = 1;
        while (input != 4) {
            System.out.print("Patient gui.Menu:\n" +
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
