
import java.util.HashMap;
import java.util.Scanner;

import data.*;
import gui.Login;
import gui.Menu;
import users.*;

import java.io.*;

public class ManagementSystem implements Serializable {

    @SuppressWarnings("unchecked")
	public static void main(String[] args) {
        Login log = new Login();
        log.init();

        // On program startup, the program tries to load the accounts.ser file, if it doesn't exist then it will create
        // a new admin account with username: admin, password: 123.
        HashMap<String, User> accDictionary = null;
        try {
            FileInputStream fileIn = new FileInputStream("accounts.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            // if you get a class not found error at the line below, try deleting/recreating the .ser file and run main again.
            accDictionary = (HashMap<String, User>)in.readObject();
            Account.setDictionary(accDictionary);
            in.close();
            fileIn.close();
        } catch (IOException i) {
            System.out.println("accounts.ser not found.\nCreating a new account dictionary...");
            accDictionary = new HashMap<String, User>();
            Account.setDictionary(accDictionary);
            Account.createAccount("admin", "123", 1);
        } catch (ClassNotFoundException c) {
            System.out.println("Accounts class not found.\nExiting program.");
            c.printStackTrace();
            System.exit(1);
        }

        Scanner scan = new Scanner(System.in);

        /** 0 = Not a user
         * 1 = Admin
         * 2 = Doctor
         * 3 = Patient
         */

        User user = null;

        // Prompts user to input username and password. Will keep looping until user no longer wants to attempt
        // logging in.
        while (user == null) {

            user = Account.inputLogin();           // Prompt user to input log in information

            // If username/password is incorrect loop if user wants to try again
            while (user == null) {
                System.out.println("Incorrect username or password.");
                System.out.print("Would you like to try again (y or n)? ");
                String input = scan.next();

                // Loop to get the correct "y" or "n" input
                while (!input.equals("y") && !input.equals("n")) {
                    System.out.print("Please input y or n: ");
                    input = scan.next();
                }

                // Exit program if user inputs "n"
                if (input.equals("n")) {
                    System.exit(0);
                } else if (input.equals("y")) {
                    user = Account.inputLogin();
                }
            }
        }

        prompt(user);
    }


    /**
     * Opens a menu based on users account type
     * @param user is the user object
     */
    private static void prompt(User user) {
        switch (user.getAccountType()) {
            case 1:
                Admin.adminMenu();
                break;
            case 2:
                Doctor.doctorMenu();
                break;
            case 3:
                Patient.patientMenu();
                break;
        }
    }
}
